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

function exportPupilcontact(cols,type,gridId ,pageHead){

	var $grid = $("#" + gridId);
    var postData = $grid.jqGrid('getGridParam', 'postData');
    
    var firstName =  null ,gender = null,  isReg=    null , lastName = null , gradeName =  null,pupilCell = null, homePhone = null,
    p1Name = null, p1Cell = null, p2Name = null, p2Cell = null;
    
    if(postData._search === true){
    	firstName = postData.firstName ,gender =  postData.gender, 
    	isReg=    postData.isReg , lastName =  postData.lastName ,
    	gradeName =   postData.gradeName,
    	pupilCell =  postData.pupilCell,homePhone =  postData.homePhone, p1Name =  postData.p1Name,
    	p1Cell = postData.p1Cell, p2Name =  postData.p2Name, p2Cell = postData.p2Cell;
    }
    
    var params = { Controller: "FullPupilCardController",  fileType : type, fileName: 'exportFile' , action: "export" ,
        	pupilCell : pupilCell, homePhone :  homePhone,
            p1Name: p1Name, p1Cell : p1Cell, p2Name : p2Name, p2Cell : p2Cell,        	
        	firstName : firstName ,gender : gender, 
			        	isReg:  isReg , lastName : lastName ,
			        	gradeName :  gradeName,sord : postData.sord, sidx : postData.sidx,pageName : "pupils_phones"
			        	, colNum : 11
			    };
    
    exportData(type, params); 
   /* var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });       
    
   $.fileDownload("FullPupilCardController", {
        successCallback: function(url) {

            $preparingFileModal.dialog('close');
        },
        failCallback: function(responseHtml, url) {

            $preparingFileModal.dialog('close');
            $("#error-modal").dialog({ modal: true });
        },
        data : {  fileType : file, fileName: 'exportFile' , action: "export" ,
        	pupilCell : pupilCell, homePhone :  homePhone,
            p1Name: p1Name, p1Cell : p1Cell, p2Name : p2Name, p2Cell : p2Cell,        	
        	firstName : firstName ,gender : gender, 
			        	isReg:  isReg , lastName : lastName ,
			        	gradeName :  gradeName,sord : postData.sord, sidx : postData.sidx,pageName : "pupils_phones"
			        	, colNum : 11
			    },
        httpMethod: "POST",
        popupWindowTitle: "ייצוא קובץ...",
    });

    return false; //this is critical to stop the click event which will trigger a normal file download!
   */
}

function exportStaff(type){
	var params = { Controller: "ReportsController",  fileType : type, fileName: 'staffDetails' , action: "export" , pageName : "staffDetails"};	
	exportData(type, params); 
} 

function exportData(type, params){

	
    var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });
    
   $.fileDownload(params.Controller, {
        successCallback: function(url) {

            $preparingFileModal.dialog('close');
        },
        failCallback: function(responseHtml, url) {

            $preparingFileModal.dialog('close');
            $("#error-modal").dialog({ modal: true });
        },
        data : params,
        httpMethod: "POST",
        popupWindowTitle: "ייצוא קובץ...",
    });

    return false; 
} 

function loadGrid(){
	  $("#contact").jqGrid({
          url : "FullPupilCardController?action=contactPage",
          datatype : "json",
          mtype : 'POST',
          colNames : ['מספר','רשום','שם משפחה' , 'שם פרטי' , 'מגדר', 'כיתה', 'סלולר תלמיד', 'טלפון בבית','שם ההורה','טלפון','מייל','שם ההורה','טלפון','מייל'],
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
	              searchoptions: { value: ":;2:רשום;1:לא רשום"},
	              formatter: "checkbox"
	      }, {
                  name : 'lastName',
                  index : 'lastName',
                  width : 80,
                  editable : false
          }, {
              name : 'firstName',
              index : 'firstName',
              width : 80,
              editable : true
          }
          ,{
                  name : 'gender',
                  index : 'gender',
                  width : 60,
                  editable : false,
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
            }
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
              name : 'p1mail',
              index : 'p1mail',
              width : 160,
              editable : true
          }
         , {
              name : 'p2Name', 
              index : 'p2Name',
              width : 100,
              editable : false
          }, {
              name : 'p2Cell', 
              index : 'p2Cell',
              width : 100,
              editable : false
          } , {
              name : 'p2mail',
              index : 'p2mail',
              width : 160,
              editable : true
          } ],
          pager : '#cont_page',
          rowNum : 100,
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
          pgtext: null ,
          // disable pager text like 'Page 0 of 10'
          /*viewrecords: false*/
          ajaxSelectOptions: {
              dataType: 'json',
              cache: false
          }
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