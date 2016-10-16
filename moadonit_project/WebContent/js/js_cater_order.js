var sunDate;
$().ready(function (){
	 $('#weekPick').datepicker({
		    format: "dd/mm/yyyy",
		    language: "he" ,
		     startDate: '01/01/2000',
		    maxViewMode: 0,
		    minViewMode: 0,
		    todayBtn: true,
		    keyboardNavigation: false,
		    daysOfWeekDisabled: "1,2,3,4,5,6",
		    todayHighlight: true,
		    toggleActive: true,
		    autoclose: true
		   
		});
	 
	 $('#weekPick').datepicker('setDate', nextSunday());
	 sunDate = getDateFromValue($('#weekPick').val());
	 loadGrid();
	 
	 $('#weekPick').change(function(){
		sunDate = getDateFromValue(this.value);
		//loadGrid();
		$("#list").jqGrid("setGridParam",{url : "LogisticsController?action=getAmounts&sunday="+sunDate.getTime()}).trigger('reloadGrid');
	});
	 
		
});  


function nextSunday() {
    var d = new Date();
    var n = d.getDay();
    d.setDate(d.getDate() + (7-n));
    return d;
   // document.getElementById("demo").innerHTML = d ;
}


function loadGrid(){
	var grid = $("#list");
	grid.jqGrid({
          url : "LogisticsController?action=getAmounts&sunday="+sunDate.getTime(),
          datatype : "json",
          mtype : 'GET',
          colNames : ['מספר','סוג מנה','ראשון' , 'שני' , 'שלישי', 'רביעי', 'חמישי'],
          colModel : [ {
                  name : 'typeID',
                  index : 'typeID',
                  hidden: true
          }, {
	              name : 'typeName',
	              index : 'typeName',
	              width : '100%',
	              editable : false,
	              /*stype: "select",
	              searchoptions: { value: ":;2:רשום;1:לא רשום"},
	              formatter: "checkbox"*/
	      },  {
                  name : 'sunday',
                  index : 'sunday',
                  width : 80,
                  editable : false
          },  {
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
          width: "100%",
         
          jsonReader : {
                  repeatitems : false,
          },
          editurl : "FullPupilCardController",
     //     recreateFilter:true,
          
          rowList: [],        // disable page size dropdown
          pgbuttons: false,     // disable page control like next, back button
          pgtext: null ,
          footerrow: true,
          // disable pager text like 'Page 0 of 10'
          /*viewrecords: false*/
          ajaxSelectOptions: {
              dataType: 'json',
              cache: false
          },
          loadComplete: function(){
        	  var sum1 = grid.jqGrid('getCol','sunday', false, 'sum');
        	  var sum2 = grid.jqGrid('getCol','monday', false, 'sum');
        	  var sum3 = grid.jqGrid('getCol','tuesday', false, 'sum');
        	  var sum4 = grid.jqGrid('getCol','wednesday', false, 'sum');
        	  var sum5 = grid.jqGrid('getCol','thursday', false, 'sum');
        	  
        	  grid.jqGrid('footerData', 'set', { sunday : sum1, monday : sum2,
        		  tuesday: sum3, wednesday : sum4 , thursday : sum5});
          }
  });

  

}	 	