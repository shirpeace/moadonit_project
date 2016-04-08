var grades = "";  
function loadPupilSearch() {
	loadGrid();
	$("#resetBtn").click(function() {
		$(".ui-reset").click();
		/*var grid = $("#list");
	    grid.jqGrid('setGridParam',{search:false});

	    var postData = grid.jqGrid('getGridParam','postData');
	    $.extend(postData,{filters:""});
	    // for singe search you should replace the line with
	    // $.extend(postData,{searchField:"",searchString:"",searchOper:""});

	    grid.trigger("reloadGrid",[{page:1}]);*/
		return false;
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
                  searchoptions: { value: ":" + grades}
          }, {
              name : 'isReg', 
              index : 'isReg',
              width : 100,
              editable : false,
              stype: "select",
              searchoptions: { value: ":;1:רשום;2:לא רשום"},
             /* edittype:'checkbox',
              editoptions: { value:"True:False"}, */
              formatter: "checkbox",
            /*  formatoptions: {disabled : false} */
          } ],
          pager : '#pager',
          rowNum : 30,
          rowList : [ ],
          sortname : 'gradeName',
         /* scroll: true,*/
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
          recreateFilter:true
  });
  jQuery("#list").jqGrid('navGrid', '#pager', {
          edit : false,
          add : false,
          del : false,
          search : true
  });
  

  jQuery("#list").jqGrid('filterToolbar',{autosearch:true/*, stringResult: true*/});
  

}	 	