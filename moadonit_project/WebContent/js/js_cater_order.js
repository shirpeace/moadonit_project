/*var grades;  
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

}*/
$().ready(function (){
	loadGrid();	
});  

/**
 * format the cell of grade, add
 */

function loadGrid(){
	  $("#list").jqGrid({
          url : "LogisticsController?action=cateringOrder",
          datatype : "json",
          mtype : 'POST',
          colNames : ['מספר','סוג מנה','ראשון' , 'שני' , 'שלישי', 'רביעי', 'חמישי'],
          colModel : [ {
                  name : 'typeID',
                  index : 'typeID',
                  hidden: true
          }, {
	              name : 'typeName',
	              index : 'typeName',
	              width : 60,
	              editable : false,
	              /*stype: "select",
	              searchoptions: { value: ":;2:רשום;1:לא רשום"},
	              formatter: "checkbox"*/
	      },  {
                  name : 'sunday',
                  index : 'sunday',
                  width : 80,
                  editable : false
          },,  {
              name : 'monday',
              index : 'monday',
              width : 80,
              editable : false
	      },  {
	          name : 'tuesday',
	          index : 'tuesday',
	          width : 80,
	          editable : false
		  },  {
		      name : 'wednesday',
		      index : 'wednesday',
		      width : 80,
		      editable : false
		  },  {
			    name : 'thursday',
			    index : 'thursday',
			    width : 80,
			    editable : false
		   } ],
       //   pager : '#cont_page',
          rowNum : 100,
          sortname : 'typeID',
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
          pgtext: null ,
          // disable pager text like 'Page 0 of 10'
          /*viewrecords: false*/
          ajaxSelectOptions: {
              dataType: 'json',
              cache: false
          }
  });
  jQuery("#list").jqGrid('navGrid', '#cont_page', {
          edit : false,
          add : false,
          del : false,
          search : false,
          refresh: false
  });
  

}	 	