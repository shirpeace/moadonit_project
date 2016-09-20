var grades;
var gridSelectedGrade;

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
                  cellattr : formatGradeCell,
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
              searchoptions: { value: ":;2:רשום;1:לא רשום"},
              formatter: "checkbox",
          } 
          ],
          pager : '#pager',
          rowNum : 10,
          rowList : [ ],
          sortname : 'gradeName',
          /*scroll: true,*/
          direction:"rtl",
          viewrecords : true,
          gridview : true,
          height: "100%",
          loadComplete : function(data) {
				
				if (parseInt(data.records, 10) == 0) {
					
				} 
			},
			loadError : function(xhr, status, error) {
				if (xhr) {
					
				}
			},
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
          //pgbuttons: true,     // disable page control like next, back button
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

	var $grid = $("#" + gridId);
    var postData = $grid.jqGrid('getGridParam', 'postData');
    
    var firstName =  null ,gender = null,  isReg=    null , lastName = null , gradeName =  null;
    if(postData._search === true){
    	firstName = postData.firstName ,gender =  postData.gender, 
    	isReg=    postData.isReg , lastName =  postData.lastName ,
    	gradeName =   postData.gradeName;    	
    }
    
    var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });
    
    //addGridSearchOption($grid,'action','export');
    //$grid[0].triggerToolbar();   
    
   $.fileDownload("FullPupilCardController", {
        successCallback: function(url) {

            $preparingFileModal.dialog('close');
        },
        failCallback: function(responseHtml, url) {

            $preparingFileModal.dialog('close');
            $("#error-modal").dialog({ modal: true });
        },
        data : {  fileType : file, fileName: 'exportFile' , action: "export" ,
			        	firstName : firstName ,gender : gender, 
			        	isReg:  isReg , lastName : lastName ,
			        	gradeName :  gradeName,sord : postData.sord, sidx : postData.sidx,pageName : "pupils_search", colNum : 5
			    },
        httpMethod: "POST",
        popupWindowTitle: "ייצוא קובץ...",
    });

    return false; //this is critical to stop the click event which will trigger a normal file download!
   /* document.formstyle.pdfBuffer.value=html;
    document.formstyle.fileType.value=file;
    document.formstyle.fileName.value='exportFile';
    //
    document.formstyle.firstName.value=firstName;
    document.formstyle.gender.value=gender;
    document.formstyle.isReg.value=isReg;
    document.formstyle.lastName.value=lastName;
    document.formstyle.gradeName.value=gradeName;
    document.formstyle.sord.value=postData.sord;
    document.formstyle.sidx.value= postData.sidx;*/
    //
   /* var actionToSend = 'FullPupilCardController?action=export&firstName=' + firstName + '&gender=' + gender+ '&isReg=' +
    isReg +'&lastName='+ lastName +'&gradeName=' + gradeName +'&sord=' +postData.sord +'&sidx=' +postData.sidx;
    actionToSend = encodeURIComponent(actionToSend);
    document.formstyle.method='POST';
    document.formstyle.action= actionToSend;  // send it to server which will open this contents in excel file
    document.formstyle.submit();*/
}

function exportDataOntime(cols,file,gridId ,pageHead){

	/*var $grid = $("#" + gridId);
    var postData = $grid.jqGrid('getGridParam', 'postData');
    
    var firstName =  null ,gender = null,  isReg=    null , lastName = null , gradeName =  null;
    if(postData._search === true){
    	firstName = postData.firstName ,gender =  postData.gender, 
    	isReg=    postData.isReg , lastName =  postData.lastName ,
    	gradeName =   postData.gradeName;    	
    }*/
    debugger;
    var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });
    
    //addGridSearchOption($grid,'action','export');
    //$grid[0].triggerToolbar();   
    
   $.fileDownload("ReportsController", {
        successCallback: function(url) {

            $preparingFileModal.dialog('close');
        },
        failCallback: function(responseHtml, url) {

            $preparingFileModal.dialog('close');
            $("#error-modal").dialog({ modal: true });
        },
        data : {  fileType : file, fileName: 'exportFile' , action: "export" , pageName : "OneTimeReport", month : 8, year : 2 },
        httpMethod: "POST",
        popupWindowTitle: "ייצוא קובץ...",
    });

    return false; 
}