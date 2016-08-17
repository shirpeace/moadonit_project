var grades;
var gridSelectedGrade;
function loadCourseSearch() {
	
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
          url : "ActivityController?action=getCourses&activityNum=0",
          datatype : "json",
          mtype : 'POST',
          colNames : ['מספר','סוג פעילות' , 'שם הקבוצה' ,'שם המורה', 'יום בשבוע', 'שעת התחלה', 'שעת סיום','מחיר לחודש' ,'תשלום חומרים' ,'חוג' ,'סוג החוג' ],         
          colModel : [ {
                  name : 'activityNum',
                  index : 'activityNum',
                  width : 100,
                  hidden: true
          },
          {
                  name : 'activityType',
                  index : 'activityType',
                  width : 150,
                  hidden: true
          }, {
                  name : 'activityName', //שם הקבוצה
                  index : 'activityName',
                  width : 100,
                  editable : true
          },
           {
              name : 'staffName', 
              index : 'staffName',
              width : 100,
              
          }, {
                  name : 'weekDay',
                  index : 'weekDay',
                  width : 60,
                  editable : true,
                  stype: "select",
                  searchoptions: { value: " : ;א:א;ב:ב;ג:ג;ד:ד;ה:ה;ו:ו"}
          }, {
                  name : 'startTime',
                  index : 'startTime',
                  width : 90,
                  searchoptions: { 
                	  dataInit: function (element) {
                		
                          $(element).timepicker({
          				    
          				    closeOnWindowScroll : true,
          				    disableTextInput: true,
          				    step: 15,
          				    timeFormat : 'H:i',
          				    maxTime : '17:00',
          				    minTime : '12:30'
          				});
                      } ,
                      //sopt: ["ge","le","eq"]
                	  
                  }
                        
                 
          }
         , {
              name : 'endTime', 
              index : 'endTime',
              width : 90,
              searchoptions: { 
            	  dataInit: function (element) {
            		
                      $(element).timepicker({
      				    
      				    closeOnWindowScroll : true,
      				    disableTextInput: true,
      				    step: 15,
      				    timeFormat : 'H:i',
      				    maxTime : '17:00',
      				    minTime : '12:30'
      				});
                  } ,
                  //sopt: ["ge","le","eq"]
            	  
              }
          } 
         , {
             name : 'pricePerMonth', 
             index : 'pricePerMonth',
             width : 100,
             formatter: currencyFmatter,
             unformat:unformatCurrency
         }
         , {
             name : 'extraPrice', 
             index : 'extraPrice',
             width : 100,
             formatter: currencyFmatter,
             unformat:unformatCurrency
         }
         , 
         {
             name : 'activityGroup', //קבוצת חוגים
             index : 'activityGroup',
             width : 100
         }
         , {
             name : 'regularOrPrivate', 
             index : 'regularOrPrivate',
             width : 100,
             stype: "select",
             searchoptions: { value: " : ;רגיל:רגיל;מיוחד:מיוחד"}
         }
         
          ],
          loadComplete : function(data) {
				
			},
			loadError : function(xhr, status, error) {
				/*alert("complete loadError");*/
			},
			
          pager : '#pager',
          rowNum : 50,
          rowList : [ ],
          sortname : 'activityName',
          cmTemplate: {sortable:false},
          /*scroll: true,*/
          direction:"rtl",
          viewrecords : true,
          gridview : true,
          height: "100%",
          width: "100%",
          ondblClickRow: function(rowId) { 
              var rowData = jQuery(this).getRowData(rowId); 
              var actID = rowData.activityNum;
              window.location.href = "course_card_view.jsp?activityNum="+actID+"";
          },
          jsonReader : {
                  repeatitems : false,
          },
          
          recreateFilter:true,
          
          rowList: [],        // disable page size dropdown
          pgbuttons: true,     // disable page control like next, back button
          /*pgtext: null, */        // disable pager text like 'Page 0 of 10'
          loadui:"block",
          viewrecords: true,
          ajaxSelectOptions: {
              dataType: 'json',
              cache: false
          }

          
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
function currencyFmatter (cellvalue, options, rowObject)
{
 
   return cellvalue + " ש\"ח";
}
function  unformatCurrency (cellvalue, options)
{
 
   return cellvalue.replace(" ש\"ח","");
}

function onGradeChange(elem){

		var color = $("#"+elem+" option:selected", elem).attr("class");
		$(elem).attr("class", color); alert($("#gradeSelect"));
		
}
function exportData(cols,file,gridId){
    var columns = cols.toString().split(",");
    var columnNms = $('#'+gridId).jqGrid('getGridParam','colNames');
    var theads = "";
    for(var cc=0;cc<columnNms.length;cc++){
        var isAdd = true;
        for(var p=0;p<columns.length;p++){
            if(cc==columns[p]){
                isAdd = false;
                break;
            }
        }
        if(isAdd){
            theads = theads +"<th>"+columnNms[cc]+"</th>"
        }
    }
    theads = "<tr>"+theads+"</tr>";
    var mya=new Array();
    mya=jQuery('#'+gridId).getDataIDs();  // Get All IDs
    var data=jQuery('#'+gridId).getRowData(mya[0]);     // Get First row to get the labels
    //alert(data['id']);
    var colNames=new Array();
    var ii=0;
    for (var i in data){
        colNames[ii++]=i;
    }

    var pageHead = "Testing PDF";
    var html="<!DOCTYPE html><html lang=\"he\" ><head> <meta charset=\"utf-8\" /><style script='css/text'>table.tableList_1 th {border:1px solid #a8da7f; border-bottom:2px solid #a8da7f; text-align:center; vertical-align: middle; padding:5px; background:#e4fad0;}table.tableList_1 td {border:1px solid #a8da7f; text-align: left; vertical-align: top; padding:5px;}</style></head><body dir=\"rtl\"><div class='pageHead_1'>"+pageHead+"</div><table border='"+(file=='pdf'? '0' : '1')+"' class='tableList_1 t_space' cellspacing='10' cellpadding='0'>"+theads;
    //var html = "<html><head><style script='css/text'>table.tableList_1 th {border:1px solid #a8da7f; border-bottom:2px solid #a8da7f; text-align:center; vertical-align: middle; padding:5px; background:#e4fad0;}table.tableList_1 td {border:1px solid #a8da7f; text-align: left; vertical-align: top; padding:5px;}</style></head><body ><div class='pageHead_1'>"+pageHead+"</div><table border='"+(file=='pdf'? '0' : '1')+"' class='tableList_1 t_space' cellspacing='10' cellpadding='0'>"+theads;
    
    //alert('len'+mya.length);
    for(i=0;i<mya.length;i++)
    {
        html=html+"<tr>";
        data=jQuery('#'+gridId).getRowData(mya[i]); // get each row
        for(j=0;j<colNames.length;j++){
            var isjAdd = true;
            for(var pj=0;pj<columns.length;pj++){
                if(j==columns[pj]){
                    isjAdd = false;
                    break;
                }
            }
            if(isjAdd){
                html=html+"<td>"+data[colNames[j]]+"</td>"; // output each column as tab delimited
            }
        }
        html=html+"</tr>";  // output each row with end of line

    }
    html=html+"</table></body></html>";  // end of line at the end
    //alert(html);
    
    var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });

    var test = new Object();
    test.id = 1;
    test.name = "שלמה";
    test.addres = "חיפה";
    test.boy = true;
    
    $.fileDownload("GenerateGridPDFs", {
        successCallback: function(url) {

            $preparingFileModal.dialog('close');
        },
        failCallback: function(responseHtml, url) {

            $preparingFileModal.dialog('close');
            $("#error-modal").dialog({ modal: true });
        },
        data : { pdfBuffer : html, fileType : file, fileName: 'exportFile' , action: "export" },
        httpMethod: "POST",
        popupWindowTitle: "ייצוא קובץ...",
    });
    return false; //this is critical to stop the click event which will trigger a normal file download!
    /*document.formstyle.pdfBuffer.value=html;
    document.formstyle.fileType.value=file;
    document.formstyle.method='POST';
    document.formstyle.action='GenerateGridPDFs';  // send it to server which will open this contents in excel file
    document.formstyle.submit();*/
}