var grades;  
function loadPupilSearch() {
	$.ajax({
  		async: false,
		type: 'GET',
		datatype: 'json',
        url: "FullPupilCardController?action=getGrades",
        
        success: function(data) {
        	if(data != undefined){
        		grades=data;
        		console.log("grades"+grades);
        	}
        	else
        		console.log("no data");
        },
        error: function(e) {
        	console.log("error loading grades");
        	
			
        }
        
      }); 
	loadGrid();
	$("#resetBtn").click(function() {
		var grid = $("#contact");
		grid.jqGrid('setGridParam',{search:false});
		var postData = grid.jqGrid('getGridParam','postData');
		$.extend(postData,{filters:""});
		$(".ui-widget-content").val("");
		grid.trigger("reloadGrid",[{page:1}]);
		
	});
}
function loadGrid(){
	  $("#contact").jqGrid({
          url : "FullPupilCardController?action=contactPage",
          datatype : "json",
          mtype : 'POST',
          colNames : ['מספר','שם פרטי' , 'שם משפחה' , 'מגדר', 'כיתה', 'סלולר תלמיד', 'טלפון בבית', 'שם ההורה', 'טלפון','שם ההורה','טלפון'],
          colModel : [ {
                  name : 'id',
                  index : 'id',
                  width : 100,
                  hidden: true
          }, {
                  name : 'firstName',
                  index : 'firstName',
                  width : 80,
                  editable : true
          }, {
                  name : 'lastName',
                  index : 'lastName',
                  width : 80,
                  editable : true
          }, {
                  name : 'gender',
                  index : 'gender',
                  width : 60,
                  editable : true,
                  stype: "select",
                  searchoptions: { value: ":;1:בן;2:בת"}
          }, {
                  name : 'gradeName',
                  index : 'gradeName',
                  width : 60,
                  editable : true,
                  stype: "select",
                  searchoptions:  grades
          }, {
              name : 'pupilCell', 
              index : 'pupilCell',
              width : 100,
              editable : false,
          }, {
              name : 'homePhone', 
              index : 'homePhone',
              width : 100,
              editable : false
          }, {
              name : 'p1Name', 
              index : 'p1Name',
              width : 100,
              editable : false
          }, {
              name : 'p1Cell', 
              index : 'p1Cell',
              width : 100,
              editable : false
          }, {
              name : 'p2Name', 
              index : 'p2Name',
              width : 100,
              editable : false
          }, {
              name : 'p2Cell', 
              index : 'p2Cell',
              width : 100,
              editable : false
          } ],
          pager : '#cont_page',
          rowNum : 30,
          rowList : [ ],
          sortname : 'gradeName',
          /*scroll: true,*/
          direction:"rtl",
          viewrecords : true,
          gridview : true,
          height: "100%",
          ondblClickRow: function(rowId) {
              var rowData = jQuery(this).getRowData(rowId); 
              var pupilID = rowData.id;
              window.location.href = "pupil_card_view.jsp?pupil="+pupilID+"";
          },
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
  jQuery("#contact").jqGrid('navGrid', '#cont_page', {
          edit : false,
          add : false,
          del : false,
          search : false,
          refresh: false
  });
  

  jQuery("#contact").jqGrid('filterToolbar',{autosearch:true/*, stringResult: true*/});
  

}	 	