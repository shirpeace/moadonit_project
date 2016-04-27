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
		{
		$('#down').append('<option value=' + 11 + '>אא</option>');
		selectedGrade=11;
		}
	
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
	loadGrid(selectedGrade);
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
	var colN=['שם','מגדר','ימים במועדונית'];
	for(var i=0;i<14;i++)
		colN.push(addDays(today, i).toDateString().slice(4,10));
	  $("#list").jqGrid({
          url : "PupilAttendanceController?action=loadGrid",
          datatype : "json",
          mtype : 'POST',
          colNames : colN,//create an array before this call
          colModel : [ {
                  name : 'name',
                  index : 'name',
                  width : 100/*,
                  hidden: true*/
          }, {
                  name : 'gender',
                  index : 'gender',
                  width : 30,
                  editable : true
          }, {
                  name : 'regDays',
                  index : 'lastName',
                  width : 100,
                  editable : true
          }, {
	              name : 'today',
	              index : 'today',
	              width : 30,
	              editable : true
          }, {
		          name : 'tommorow',
		          index : 'tommorow',
		          width : 30,
		          editable : true
          } , {
	          name : 'day3',
	          index : 'day3',
	          width : 30,
	          editable : true
      }, {
    name : 'day4',
    index : 'day4',
    width : 30,
    editable : true
}, {
    name : 'day5',
    index : 'day5',
    width : 30,
    editable : true
}, {
    name : 'day6',
    index : 'day6',
    width : 30,
    editable : true
}, {
    name : 'day7',
    index : 'day7',
    width : 30,
    editable : true
}, {
    name : 'day8',
    index : 'day8',
    width : 30,
    editable : true
}, {
    name : 'day9',
    index : 'day9',
    width : 30,
    editable : true
}, {
    name : 'day10',
    index : 'day10',
    width : 30,
    editable : true
}, {
    name : 'day11',
    index : 'day11',
    width : 30,
    editable : true
}, {
    name : 'day12',
    index : 'day12',
    width : 30,
    editable : true
}, {
    name : 'day13',
    index : 'day13',
    width : 30,
    editable : true
}, {
    name : 'day14',
    index : 'day14',
    width : 30,
    editable : true
}],
         /* pager : '#pager',*/
          rowNum : 40,
          rowList : [ ],
          sortname : 'name',
          /*scroll: true,*/
          direction:"rtl",
          viewrecords : true,
          gridview : true,
          height: "100%",
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