var selectedTab;
var pupilID;
var tableName, query, whereclause, columnsData;
var colname = new Array() ; // column array for jqgrid
var tablePk;
var  cols; // original column array from server
jQuery(document).ready(function() {	
	selectedTab = "OnTimeReg";
	$('#ulTabs').on('click', 'a', function(e) {
	    //e.preventDefault();
		selectedTab = this.parentElement.id; 
		
			switch (this.parentElement.id) {
			    case "OnTimeReg":
			    	
			        break;
			    case "MoadonitReg":
			    	
			        break;
			    case "CourseReg":
			    	
			        break;
			    case "MoadonitData":
			    	
			        break;
			    case "CourseData":
			    	 getCourseIds();
			        break;	
			    case "Search":
					//loadPupilSearch();
			    	//colname =[{ name: "staffID", sorttype: "int", key: true }];
			    	tableName =  "tbl_staff";
			    	query = "SELECT COLUMN_NAME, COLUMN_COMMENT, TABLE_NAME, DATA_TYPE, COLUMN_KEY ,EXTRA FROM information_schema.columns ";
			    	whereclause = " WHERE (table_name = 'tbl_staff'); ";
			    	getGeneralGrid();
			        break;	
			}
		
	});
	
	 $('#monthPick').datepicker({
		    format: "dd/mm/yyyy",
		    language: "he" ,
		     startDate: '01/01/2000',
		    maxViewMode: 1,
		    minViewMode: 1,
		    todayBtn: false,
		    keyboardNavigation: false,
		    daysOfWeekDisabled: "1,2,3,4,5,6",
		    todayHighlight: true,
		    toggleActive: true,
		    autoclose: true
		   
		}); 
	 
	 $('#dayPick').datepicker({
		    format: "dd/mm/yyyy",
		    language: "he" ,
		     startDate: '01/01/2000',
		    maxViewMode: 0,
		    minViewMode: 0,
		    todayBtn: false,
		    keyboardNavigation: false,
		    daysOfWeekDisabled: "5,6",
		    todayHighlight: true,
		    toggleActive: true,
		    autoclose: true
		   
		}); 
	 

});


var editSettings = {
        //recreateForm: true,
        jqModal: false,
        reloadAfterSubmit: true,
        closeOnEscape: true,
        savekey: [true, 13],
        closeAfterEdit: true,
        onclickSubmit: onclickSubmitLocal
    },
    addSettings = {
        //recreateForm: true,
        jqModal: false,
        reloadAfterSubmit: false,
        savekey: [true, 13],
        closeOnEscape: true,
        closeAfterAdd: true,
        onclickSubmit: onclickSubmitLocal
    },
    delSettings = {
        // because I use "local" data I don't want to send the changes to the server
        // so I use "processing:true" setting and delete the row manually in onclickSubmit
        onclickSubmit: function (options, rowid) {
            var $this = $(this), id = $.jgrid.jqID(this.id), p = this.p,
                newPage = p.page;

            // reset the value of processing option to true to
            // skip the ajax request to "clientArray".
            options.processing = true;

            // delete the row
            $this.jqGrid("delRowData", rowid);
            if (p.treeGrid) {
                $this.jqGrid("delTreeNode", rowid);
            } else {
                $this.jqGrid("delRowData", rowid);
            }
            $.jgrid.hideModal("#delmod" + id, {
                gb: "#gbox_" + id,
                jqm: options.jqModal,
                onClose: options.onClose
            });

            if (p.lastpage > 1) {// on the multipage grid reload the grid
                if (p.reccount === 0 && newPage === p.lastpage) {
                    // if after deliting there are no rows on the current page
                    // which is the last page of the grid
                    newPage--; // go to the previous page
                }
                // reload grid to make the row from the next page visable.
                $this.trigger("reloadGrid", [{page: newPage}]);
            }

            return true;
        },
        processing: true
    };

var mydata = {}, existingProperties = {},
floatTemplate = {formatter: 'number', sorttype: 'int'},
integerTemplate = {formatter: 'integer', sorttype: 'int'},
inLineErrorfunc = function (rowID, response) {
	alert(rowID);
	debugger;
    // todo: why this does not allow Enter key to continue ase after error:
	$.jgrid.info_dialog($.jgrid.errors.errcap,'<div class="ui-state-error">'+
		    response.responseText +'</div>', $.jgrid.edit.bClose,{buttonalign:'right'});
},
dateTemplate = {
    editable: true,
    
    searchoptions: {
        dataInit: function (el) {
            var self = this;
            /*$(el).datepicker({
                dateFormat: 'dd/mm/yy', maxDate: 0, changeMonth: true, changeYear: true,
                onSelect: function (dateText, inst) {
                    setTimeout(function () {
                        self.triggerToolbar();
                    }, 50);
                }
            });*/
            
            $(el).datepicker({
			    format: "dd/mm/yyyy",
			    language: "he" ,
			    startDate: "today",
			    maxViewMode: 0,
			    minViewMode: 0,
			    todayBtn: true,
			    keyboardNavigation: false,
			    daysOfWeekDisabled: "5,6",
			    todayHighlight: true,
			    toggleActive: true ,
			    onSelect: function (dateText, inst) {
                    setTimeout(function () {
                        self.triggerToolbar();
                    }, 50);
                }
			});
        }
    },
    editoptions: {
        dataInit: function (el) {
        	$(el).datepicker({
			    format: "dd/mm/yyyy",
			    language: "he" ,
			    startDate: "today",
			    maxViewMode: 0,
			    minViewMode: 0,
			    todayBtn: true,
			    keyboardNavigation: false,
			    daysOfWeekDisabled: "5,6",
			    todayHighlight: true,
			    toggleActive: true ,
			    onSelect: function (dateText, inst) {
                    setTimeout(function () {
                        self.triggerToolbar();
                    }, 50);
                }
			});
        }
        //readonly: 'readonly'
    },
    formatter : function (cellValue, opts, rwd) {								
		if (cellValue) {
			 getDateFromValue(cellValue);
			
			var d = $.fn.fmatter.call(this, "date",
					getDateFromValue(cellValue), opts, rwd);
			return d;
		} else {
			return '';
		}
	}
    , formatoptions: { newformat: "d/m/Y" }
},
dropdownTemplate = {
    edittype: "select",
    editable: true,
    stype: "select"
},
timeTemplate = {
	    editable: true,
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
                
                
            } 
      	  
        },
	    editoptions: {
	        dataInit: function (el) {
	        	 $(element).timepicker({
					    
					    closeOnWindowScroll : true,
					    disableTextInput: true,
					    step: 15,
					    timeFormat : 'H:i',
					    maxTime : '17:00',
					    minTime : '12:30'
					});
	        }
	    }
	}, 
	navParams = {
		   edit: false,
		   add: false,
		   del: true,
		   search:false,
		   refresh:false,
		   position : "right",
		   alertcap: "שים לב"
	}
	,inlinNavParameters = { 
			   edit: true,
			   editicon: "ui-icon-pencil",
			   add: true,
			   addicon:"ui-icon-plus",
			   save: true,
			   saveicon:"ui-icon-disk",
			   cancel: true,
			   cancelicon:"ui-icon-cancel",
			   addParams : {useFormatter : false},
			   editParams : { errorfunc: function (rowID, response) {
				   debugger;
					alert(response.responseText);
				    // todo: why this does not allow Enter key to continue ase after error:
					$.jgrid.info_dialog("שגיאה",'<div class="ui-state-error">'+
						    response.responseText +'</div>', "סגור",{buttonalign:'right', modal : true});
					/*$.jgrid.info_dialog($.jgrid.errors.errcap,'<div class="ui-state-error">'+
						    response.responseText +'</div>', $.jgrid.edit.bClose,{buttonalign:'right'});*/
				}},
			   del: true,
			   
			   
	} ;

/**
 * set the client array of cols (colname Object) wich is used in grid 
 * @param result - the array  object from server with the cols data
 */
function setColModelFormResult(result){
	 cols = JSON.parse(result);
	//Loop into the column values collection and push into the array.
	$.each(cols, function () {

	//Check the datatype of the column.
	if(this.IsKey)
		tablePk = this.Name;
	var cm = {
	        name: this.Name,
	        hidden: this.IsHidden //|| !existingProperties.hasOwnProperty(this.Name)
	        ,
	        editable : !this.IsKey,
	        //editoptions: this.DefaultValue != null && this.DefaultValue != "" ? { defaultValue: this.DefaultValue } : {},
	        editrules: { required: this.IsRequired },
	        label : this.label,
	        key: this.IsKey,
	        /*width: "120px"*/
	    };
	switch (this.Datatype) {
	    case 'int':
	    	/*$.extend(true, cm, { template: integerTemplate }, { editrules: {integer : true} });*/
	    	 $.extend(true, cm, { template: integerTemplate } , { editrules: { integer: true } });
	    	 break;
	    case 'float':
	        $.extend(true, cm, { template: floatTemplate }  , { editrules: { number: true } });
	        lastFieldName = cm.name; //Store the fieldName.
	        break;
	    case 'Date':
	        $.extend(true, cm, { template: dateTemplate } , { editrules: { date: true } });
	        lastFieldName = cm.name;
	        break;
	    case 'time':
	        $.extend(true, cm, { template: timeTemplate } , { editrules: { time: true } });
	        lastFieldName = cm.name;
	        break; 
	    case 'dropdown':
	        var values = this.ValueList.slice(0, -1);
	        $.extend(true, cm, { template: dropdownTemplate,
	            editoptions: { value:  values, defaultValue: this.DefaultValue },
	            searchoptions: { value: ":All;" + values }
	        }
	        /*,
	        this.ValueType == "F" ? { label: this.ValueId } : {}
	        */
	        );
	        break;
	    default:
	        break;
	}
	if (cm)
	colname.push(cm);
	});
}
function getGeneralGrid(){
	
	$.ajax({
		async : false,
		type : 'POST',
		datatype : 'jsonp',
		url : "LogisticsController",
		data : { action : "getMetaData", tableName : tableName, query: query, whereclause : whereclause  },
		success : function(data) {
			if (data != undefined) {
				
				setColModelFormResult(data.cols);
				
				
				// preper the rows data to be used in grid
				//var rows = JSON.parse(data.rows);
				var deleteMessage = function(response,postdata){
			        var json   = response.responseText; // response text is returned from server.
			        var result = JSON.parse(json); // convert json object into javascript object.
			        
			        return [result.status,result.message,null]; 
			    };
			    var grid = $("#list");
				
			    grid.jqGrid(
						{

							
							url : "LogisticsController?action=getGridRows&tableName=" +tableName,
							datatype : "json",
							mtype : 'POST',
					        colModel: colname,
							pager : '#pager',
							autowidth : true,
							shrinkToFit : true,
							rowNum : 30,
							rowList : [],						
							direction : "rtl",
							viewrecords : true,
							gridview : true,
							height : "100%",
							width : "100%",
							editurl : "LogisticsController?tableName=" + tableName,
							jsonReader : {
								repeatitems : false,
							},
							recreateFilter : true,
							sortname : tablePk,
							//pgbuttons : false, // disable page control like next, back
							// button
							//pgtext : null, // disable pager text like 'Page 0 of 10'
							/*new change */
							localReader: { id: tablePk },
							prmNames: { id: tablePk },
							/*new change*/
							onInitGrid: function () {
						        
							       //var p = $(this).jqGrid("getGridParam");
	
							        // set data parameter
							        //p.data = rows;
							       
							    
						    },
						    loadError : function(xhr,st,err) {
						    	debugger;
						    	jQuery("#rsperror").html("Type: "+st+"; Response: "+ xhr.status + " "+xhr.statusText);
						    },
							 errorTextFormat: function (response) {
								 	alert(response.responseText);	 	
								 	$(this).restoreAfterErorr = false;
								 	var overlay = $('body > div.ui-widget-overlay'); //.is(":visible");
									 if (overlay.is(":visible")) {
										 
										 
									}
								 	bootbox.alert("שגיאה במחיקת רשומה. נא נסה שוב.",
											function() {
									});
								 				
						            return true;
						           
							       
							 }
						    /*afterSubmit: function (response, postdata) {
						    	
						    	return [false,response.responseText] ;
								   //debugger;
									//alert(response.responseText);
								    // todo: why this does not allow Enter key to continue ase after error:
									$.jgrid.info_dialog("שגיאה",'<div class="ui-state-error">'+
										    response.responseText +'</div>', "סגור",{buttonalign:'right', modal : true});
									$.jgrid.info_dialog($.jgrid.errors.errcap,'<div class="ui-state-error">'+
										    response.responseText +'</div>', $.jgrid.edit.bClose,{buttonalign:'right'});
							}*/
							/*serializeRowData: function(postdata) { 
								
								for (var int = 0; int < cols.length; int++) {
									var col = cols[int];
									var value;
									switch (col.Datatype) {
									    case 'int':
									    case 'float':
									      
									        break;
									    case 'Date':
									    case 'time':
									    	var fieldValue = postdata[col.Name];
									    	if(fieldValue != undefined){
										    	value = getDateFromValue(fieldValue);
										        postdata[col.Name] = value.getTime();
											}
									    
									        break; 
									    case 'dropdown':
									      
									        break;
									    default:
									        break;
									}
									
								}
								return postdata;
								//return { rtm : JSON.stringify(createPostData(pupilID, postdata,true)), _oldDateVal: oldDateVal.getTime() , _oldEndDate: oldEndDate.getTime() } ;
					        }*/
							
						}).jqGrid("navGrid", "#pager", navParams)
						.jqGrid("inlineNav","#pager", inlinNavParameters );
			        
				$.extend($.jgrid.inlineEdit, { restoreAfterError: false });
				
				var originalDelFunc = $.fn.jqGrid.delGridRow;
			    $.fn.jqGrid.delGridRow = function (rowids, oMuligrid) {
			        /*var onPreDeleteRowEventHandler = this.getGridParam('onPreDeleteRow'),
			            consumeFlag = false;*/
			        $.extend(oMuligrid, { afterSubmit: function(){ retrun [false,"error", null]; } });
			        originalDelFunc.call(this, rowids, oMuligrid);
			        
			       /* if (typeof onPreDeleteRowEventHandler === 'function') {
			            consumeFlag = !!onPreDeleteRowEventHandler(rowids, oMuligrid);
			        }

			        if (!consumeFlag) {
			            originalDelFunc.call(this, rowids, oMuligrid);
			        }*/
			    };
				  
			    /*grid.jqGrid(
			            	'setGridParam',
				            {
				                onPreDeleteRow: function(rowids, oMuligrid) {
				                    // remove client data here
				                	alert("onPreDeleteRow event");
				                }
			            		
				            }
		      );*/
			    
			} else
				alert("לא קיימים נתונים");
		},
		error : function(e) {
			alert("שגיאה בשליפת נתונים");
			console.log("error");

		}

	});
	
}
function OnBntExportClick(type){
	
	if(selectedTab){
		switch (selectedTab) {
		    case "OnTimeReg":
		    	exportDataOntime(type, "OneTimeReport");
		        break;
		    case "MoadonitReg":
		    	console.log(selectedTab);
		    	exportMoadonitPay(type, "MoadonitPay");
		        break;
		    case "CourseReg":
		    	console.log(selectedTab);
		        break;
		    case "MoadonitData":
		    	console.log(selectedTab);
		    	exportMoadonitData(type, "MoadonitDataReport");
		        break;
		    case "CourseData":
		    	console.log(selectedTab);
		    	exportCourseData(type, "CourseRegistrationReport");
		        break;	   
		}
	}
}

function exportMoadonitPay(type, fileName){
	
	var month =  $('#monthPick').val();
	var monthTime =  getDateFromValue(month).getTime();
	var params = {  fileType : type, fileName: fileName , action: "export" , pageName : "MoadonitPay", month : monthTime };
	
	exportData(type, params);
	
}

function exportMoadonitData(type, fileName){
	var month =  $('#dayPick').val();
	var monthTime =  getDateFromValue(month).getTime();
	var params = {  fileType : type, fileName: fileName , action: "export" , pageName : "MoadonitData", month : monthTime };
	
	exportData(type, params);
	
}

function exportDataOntime(type, fileName){

	var month = $('#monthNum').val(), year =$('#yearNum').val();
	var params = {  fileType : type, fileName: fileName , action: "export" , pageName : "OneTimeReport", month : month, year : year };
	
	exportData(type, params); 
} 

function exportCourseData(type, fileName){

	var month = $('#monthNum').val(), year =$('#yearNum').val();
	var arr = getSelectedOptions();
	var params = {  fileType : type, fileName: fileName , action: "export" , pageName : "CourseData", options : arr, year : year };	
	exportData(type, params); 
} 

function exportData(type, params){

	
    var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });
    
   $.fileDownload("ReportsController", {
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

function getSelectedOptions(){
	var array = [], idx = 0;
	var val = "";
    $('#courseList option:selected').each(function() {
    	/*var activity = new Object();
    	activity.activityNum = $(this).val();
    	activity.activityName = $(this).text();
    	array[idx++] = activity;*/

    	val += $(this).val() + ";" + $(this).text() + ",";
    });
    
    return val.substring(val.lastIndexOf(",", 0));
}


function getCourseIds(){
	
	$.ajax({
		async : false,
		type : 'POST',
		datatype : 'jsonp',
		url : "ReportsController",
		data : { action : "getCourseForReport" },
		success : function(data) {
			if (data != undefined) {
				var values = [];
				if(data)
				for (var int = 0; int < data.length; int++) { // iterate the keys of the object that represent the option data and get the key and value
	          	    var item = {};
	            	item.activityNum = data[int].activityNum;
	            	item.activityName = data[int].activityName;
	            	values.push(item);
	            	
		        }  
				
				//sort options alphabetically
				values.sort(function(o1, o2) { return o1.activityName > o2.activityName ? 1 : o1.activityName < o2.activityName ? -1 : 0; });
				$('#courseList').find('option').remove();
				for (var i = 0; i < values.length; i++) {
					$('#courseList').append($("<option></option>")
		                    .attr("value",values[i].activityNum)
		                    .text(values[i].activityName)); 
				}
				
				//$('#courseList').selectpicker('val', [ "test1", "test2"]);
				$('.selectpicker').selectpicker('refresh');
			} else
				alert("לא קיימים נתונים");
		},
		error : function(e) {
			alert("שגיאה בשליפת נתונים");
			console.log("error");

		}

	});
}

/******
 NOT IN USE FROM HERE TO END 
 ******/

$.each(mydata, function () {
	var p;
	for (p in this) {
	    if (this.hasOwnProperty(p) && this[p] !== null && (typeof this[p] === "string" && $.trim(this[p]) !== "")) {
	        existingProperties[p] = true;
	    }
	}
});

var onclickSubmitLocal = function (options, postdata) {debugger;
var $this = $(this), p = $(this).jqGrid("getGridParam"),// p = this.p,
    idname = p.prmNames.id,
    id = this.id,
    idInPostdata = id + "_id",
    rowid = postdata[idInPostdata],
    addMode = rowid === "_empty",
    oldValueOfSortColumn,
    newId,
    idOfTreeParentNode;

// postdata has row id property with another name. we fix it:
if (addMode) {
    // generate new id
    newId = $.jgrid.randId();
    while ($("#" + newId).length !== 0) {
        newId = $.jgrid.randId();
    }
    postdata[idname] = String(newId);
} else if (postdata[idname] === undefined) {
    // set id property only if the property not exist
    postdata[idname] = rowid;
}
delete postdata[idInPostdata];

// prepare postdata for tree grid
if (p.treeGrid === true) {
    if (addMode) {
        idOfTreeParentNode = p.treeGridModel === "adjacency" ? p.treeReader.parent_id_field : "parent_id";
        postdata[idOfTreeParentNode] = p.selrow;
    }

    $.each(p.treeReader, function () {
        if (postdata.hasOwnProperty(this)) {
            delete postdata[this];
        }
    });
}

// decode data if there encoded with autoencode
if (p.autoencode) {
    $.each(postdata, function (n, v) {
        postdata[n] = $.jgrid.htmlDecode(v); // TODO: some columns could be skipped
    });
}

// save old value from the sorted column
oldValueOfSortColumn = p.sortname === "" ? undefined : $this.jqGrid("getCell", rowid, p.sortname);

// save the data in the grid
if (p.treeGrid === true) {
    if (addMode) {
        $this.jqGrid("addChildNode", newId, p.selrow, postdata);
    } else {
        $this.jqGrid("setTreeRow", rowid, postdata);
    }
} else {
    if (addMode) {
        $this.jqGrid("addRowData", newId, postdata, options.addedrow);
    } else {
        $this.jqGrid("setRowData", rowid, postdata);
    }
}

if ((addMode && options.closeAfterAdd) || (!addMode && options.closeAfterEdit)) {
    // close the edit/add dialog
    $.jgrid.hideModal("#editmod" + $.jgrid.jqID(id), {
        gb: "#gbox_" + $.jgrid.jqID(id),
        jqm: options.jqModal,
        onClose: options.onClose
    });
}

if (postdata[p.sortname] !== oldValueOfSortColumn) {
    // if the data are changed in the column by which are currently sorted
    // we need resort the grid
    setTimeout(function () {
        $this.trigger("reloadGrid", [{current: true}]);
    }, 100);
}

// !!! the most important step: skip ajax request to the server
//options.processing = true;
return {};
};

