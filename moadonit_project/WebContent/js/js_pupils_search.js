var grades;
var gridSelectedGrade;
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
          colNames : ['מספר', 'שם משפחה' ,'שם פרטי' , 'מגדר', 'כיתה', 'רשום'],         
          colModel : [ {
                  name : 'id',
                  index : 'id',
                  width : 100,
                  hidden: true
          }, {
                  name : 'lastName',
                  index : 'lastName',
                  width : 150,
                  editable : true
          }, {
              name : 'firstName',
              index : 'firstName',
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
                  searchoptions: {
                	  		dataUrl: "FullPupilCardController?action=getGrades",
							buildSelect : function (data) {
                	  		var codes, i, l, code, prop; 
                	  		
                	  		var s = '<select id="gradeSelect" >', codes, i, l, code, prop;
                            if (data ) {
                                codes = data.value.split(';');
                                for (i = 0, l = codes.length; i < l; i++) {
                                    code = codes[i];
                                    // enumerate properties of code object
                                    for (prop in code) {
                                        if (code.hasOwnProperty(prop)) {
                                        	var op = code.split(':');
                                        	if(op[0] == ' '){
                                        		//FFFFFF
                                        		s += '<option style="background-color:#FFFFFF" value="' + op[0] + '">' + op[1] + '</option>';
                                                break; // we need only the first property
                                        	}
                                        	else{
                                        		s += '<option style="background-color:'+ op[2]+'" value="' + op[0] + '">' + op[1] + '</option>';
                                                break; // we need only the first property
                                        	}
                                            
                                        }
                                    }
                                }
                            }
                            
                           
                            return s + "</select>";
                		  }
                  }// grades
          }
         , {
              name : 'isReg', 
              index : 'isReg',
              width : 100,
              editable : false,
              stype: "select",
              searchoptions: { value: ":;1:רשום;2:לא רשום"},
              formatter: "checkbox",
          } 
          ],
          pager : '#pager',
          rowNum : 50,
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

function onGradeChange(elem){

		var color = $("#"+elem+" option:selected", elem).attr("class");
		$(elem).attr("class", color); alert($("#gradeSelect"));
		
}
function exportData(cols,file,gridId ,pageHead){
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

    var pageHead = "רשימת תלמידים";
    var html="<!DOCTYPE html><html lang=\"he\" ><head> <meta charset=\"utf-8\" /><style script='css/text'>table.tableList_1 th {border:1px solid #a8da7f; border-bottom:2px solid #a8da7f; text-align:center; vertical-align: middle; padding:5px; background:#e4fad0;}table.tableList_1 td {border:1px solid #a8da7f; text-align: left; vertical-align: top; padding:5px;}</style></head><body dir=\"rtl\"><div class='pageHead_1'>"+pageHead+"</div><table border='"+(file=='pdf'? '0' : '1')+"' class='tableList_1 t_space' cellspacing='10' cellpadding='0'>"+theads;
    var html="<!DOCTYPE html><html lang=\"he\" ><head> <meta charset=\"utf-8\" /><style script='css/text'>table.tableList_1 th {border:1px solid #a8da7f; border-bottom:2px solid #a8da7f; text-align:center; vertical-align: middle; padding:5px; background:#e4fad0;}table.tableList_1 td {border:1px solid #a8da7f; text-align: left; vertical-align: top; padding:5px;}</style></head><body dir=\"rtl\"><div class='pageHead_1'>"+pageHead+"</div><table border='1' class='tableList_1 t_space' cellspacing='10' cellpadding='0'>"+theads;
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
    html = "<!DOCTYPE html><html><body><table><tr><td>מתן</td><td>טספאי</td><td>50</td></tr><tr><td>משה</td><td>מזרחי</td><td>50</td></tr><tr><td>אבי</td><td>יצחק</td><td>94</td></tr><tr><td>חיים</td><td>משה</td><td>80</td></tr></table></body></html>";
    var $grid = $("#list");
    var postData = $grid.jqGrid('getGridParam', 'postData');
    var firstName =  null ,gender = null,  isReg=    null , lastName = null , gradeName =  null;
    if(postData._search === true){
    	firstName = postData.firstName ,gender =  postData.gender, 
    	isReg=    postData.isReg , lastName =  postData.lastName ,
    	gradeName =   postData.gradeName;
    }
    
    var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });

    var test = new Object();
    test.id = 1;
    test.name = "שלמה";
    test.addres = "חיפה";
    test.boy = true;
    
   $.fileDownload("FullPupilCardController", {
        successCallback: function(url) {

            $preparingFileModal.dialog('close');
        },
        failCallback: function(responseHtml, url) {

            $preparingFileModal.dialog('close');
            //$("#error-modal").dialog({ modal: true });
        },
        data : { pdfBuffer : html, fileType : file, fileName: 'exportFile' , action: "export" ,
			        	firstName : firstName ,gender : gender, 
			        	isReg:  isReg , lastName : lastName ,
			        	gradeName :  gradeName
			    },
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