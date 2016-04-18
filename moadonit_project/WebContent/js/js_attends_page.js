var grades; 
var selectedGrade;
jQuery(document).ready(function() {	
	//populate grades combo
	$.ajax({
  		async: false,
		type: 'GET',
		datatype: 'json',
        url: "FullPupilCardController",
        data: "action=getGrades",
        success: function(data) {
        	if(data != undefined){
        		grades=data.grades;
        		
        		console.log("grades"+grades);
        	}
        	else
        		console.log("no data");
        },
        error: function(e) {
        	console.log("error loading grades");
        	
			
        }
        
      }); 
	if(grades!=undefined){
		var listitems="";
		$.each(grades, function(key, value){
		    listitems += '<option value=' + key + '>' + value + '</option>';
		});
		$('#down').append(listitems);
		}
	else //for demo only
		selectedGrade=11;
	
	//generating attendances record in DB
	$.ajax({
			async: false,
		type: 'POST',
		datatype: 'json',
	    url: "PupilAttendanceController",
	    data: { action: "getBlankAttend" },
	    success: function(data) {
	    	if(data != undefined){
	    		
	    		console.log("success");
	    	}
	    	else
	    		console.log("no data");
	    },
	    error: function(e) {
	    	console.log("error loading");
	    }
	    
	  }); 
	loadGrid();
});

function actionChanged(){
	loadGrid($("#down").val());
	
}


/*function loadPupilSearch() {

	loadGrid();
	$("#resetBtn").click(function() {
		var grid = $("#list");
		grid.jqGrid('setGridParam',{search:false});
		var postData = grid.jqGrid('getGridParam','postData');
		$.extend(postData,{filters:""});
		$(".ui-widget-content").val("");
		grid.trigger("reloadGrid",[{page:1}]);
		
	});
}*/
function addDays(date, days) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}
function loadGrid(){
	var today = new(Date);
	var colN=['שם','מגדר','ימים במועדונית',today.toDateString(), addDays(today, 1).toDateString()];
	  $("#list").jqGrid({
          url : "PupilAttendanceController?action=loadGrid",
          datatype : "json",
          mtype : 'POST',
          colNames : colN,//create an array before this call
          colModel : [ {
                  name : 'id',
                  index : 'id',
                  width : 100/*,
                  hidden: true*/
          }, {
                  name : 'firstName',
                  index : 'firstName',
                  width : 150,
                  editable : true
          }, {
                  name : 'lastName',
                  index : 'lastName',
                  width : 150,
                  editable : true
          }, {
	              name : 'today',
	              index : 'today',
	              width : 150,
	              editable : true
          }, {
		          name : 'tommorow',
		          index : 'tommorow',
		          width : 150,
		          editable : true
          } ],
         /* pager : '#pager',*/
          rowNum : 40,
          rowList : [ ],
          sortname : 'lastName',
          /*scroll: true,*/
          direction:"rtl",
          viewrecords : true,
          gridview : true,
          height: "100%",
         /* ondblClickRow: function(rowId) {
              var rowData = jQuery(this).getRowData(rowId); 
              var pupilID = rowData.id;
              window.location.href = "pupil_card_view.jsp?pupil="+pupilID+"";
          },*/
          jsonReader : {
                  repeatitems : false,
          },
          editurl : "FullPupilCardController",
          recreateFilter:true,
          
          rowList: [],        // disable page size dropdown
          pgbuttons: false,     // disable page control like next, back button
          pgtext: null         // disable pager text like 'Page 0 of 10'
          /*viewrecords: false*/
          
  });
  /*jQuery("#list").jqGrid('navGrid', '#pager', {
          edit : false,
          add : false,
          del : false,
          search : false,
          refresh: false
  });*/
  

  
  

}	 	