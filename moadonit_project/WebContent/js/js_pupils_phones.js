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
/**
 * format the cell of grade, add
 * @param rowId
 * @param val
 * @param rawObject
 * @param cm
 * @param rdata
 */
function formatGradeCell(rowId, val, rawObject, cm, rdata){
	//"style" : "background:"+colors.future+";","data-isHistory": false
	var cellVal='';
	if(grades){
		var gradesCopy = grades.value.split(";");
	    $.each(gradesCopy, function(key, value) {  
	    	value  = value.split(":");
	    	if(key != 0){
	    		if(val == value[1]){
	    			cellVal =  'style="border-color:'+ value[2]+'; border-width: 3px;"';	    			
	    		//$select.append('<option style="background-color:'+ value[2]+'" value=' + value[0] + '>' + value[1] + '</option>');
	    		}
	    	}
	    });
	}
	
	return cellVal;
}
function loadGrid(){
	  $("#contact").jqGrid({
          url : "FullPupilCardController?action=contactPage",
          datatype : "json",
          mtype : 'POST',
          colNames : ['מספר','רשום','שם משפחה' , 'שם פרטי' , 'מגדר', 'כיתה', 'סלולר תלמיד', 'טלפון בבית', 'שם ההורה', 'טלפון','שם ההורה','טלפון'],
          colModel : [ {
                  name : 'id',
                  index : 'id',
                  hidden: true
          }, {
	              name : 'isReg',
	              index : 'isReg',
	              width : 60,
	              editable : false,
	              stype: "select",
	              searchoptions: { value: ":;1:רשום;2:לא רשום"},
	              formatter: "checkbox"
	      },  {
                  name : 'lastName',
                  index : 'lastName',
                  width : 80,
                  editable : false
          }, {
              name : 'firstName',
              index : 'firstName',
              width : 80,
              editable : true
          },{
                  name : 'gender',
                  index : 'gender',
                  width : 60,
                  editable : false,
                  stype: "select",
                  searchoptions: { value: ":;1:בן;2:בת"}
          }, {
                  name : 'gradeName',
                  index : 'gradeName',
                  width : 60,
                  editable : false,
                  stype: "select",
                  searchoptions:  grades,
                  cellattr : formatGradeCell,
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