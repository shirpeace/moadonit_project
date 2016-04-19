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
		var grid = $("#list");
		grid.jqGrid('setGridParam',{search:false});
		var postData = grid.jqGrid('getGridParam','postData');
		$.extend(postData,{filters:""});
		$(".ui-widget-content").val("");
		grid.trigger("reloadGrid",[{page:1}]);
		
	});
}
function loadGrid(){
	  $("#list").jqGrid({
          url : "FullPupilCardController?action=pupilSearch",
          datatype : "json",
          mtype : 'POST',
          colNames : ['מספר','שם פרטי' , 'שם משפחה' , 'מגדר', 'כיתה', 'רשום'],
          colModel : [ {
                  name : 'id',
                  index : 'id',
                  width : 100,
                  hidden: true
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
                  name : 'gender',
                  index : 'gender',
                  width : 100,
                  editable : true,
                  stype: "select",
                  searchoptions: { value: ":;1:בן;2:בת"}
          }, {
                  name : 'gradeName',
                  index : 'gradeName',
                  width : 100,
                  editable : true,
                  stype: "select",
                  searchoptions:  grades
          }, {
              name : 'isReg', 
              index : 'isReg',
              width : 100,
              editable : false,
              stype: "select",
              searchoptions: { value: ":;1:רשום;2:לא רשום"},
              formatter: "checkbox",
          } ],
          pager : '#pager',
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
  jQuery("#list").jqGrid('navGrid', '#pager', {
          edit : false,
          add : false,
          del : false,
          search : false,
          refresh: false
  });
  

  jQuery("#list").jqGrid('filterToolbar',{autosearch:true/*, stringResult: true*/});
  

}	 	