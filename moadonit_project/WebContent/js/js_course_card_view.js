/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/** ********************************************** */

/* the current user the us logged if to the system */
var currentUserId = '<%=session.getAttribute("userid")%>';
var selectedIds;
/*
 * activityNum, activityType, activityName, , startTime, endTime, schoolYear,
 * responsibleStaff, pricePerMonth, extraPrice, courseTypeID, category
 * 
 */

// define state for the editable page
/** ********************************************** */
// TODO //* START COURSE PAGE FUNCTIONS */
/** ********************************************** */
var courseData, pupilCount, courseTypes, genders;
// set the state at start to read. (state object from js_logic file)
var currentPageState = state.READ;
var popUp, popUPResult;
var validator , validator1;
var endDateEdit,startDateEdit; 
var $editRowID, isNonEditable = false, lastSelection = -1 ;//lastSelection and seledtedId are for row selection events and validation process //;
$.extend($.jgrid.inlineEdit, { restoreAfterError: false });
$(function() {

	moment.locale(); // he
	
	$('#newGroup').click(function() {
		var isChecked = this.checked;
		if (isChecked) {
			$("#newActivityGroupDiv").toggle(true);
			$("#activityGroupHead").prop("disabled", true);

		} else {
			$("#newActivityGroupDiv").toggle(false);
			$("#newActivityGroupHead").val('');
			$("#activityGroupHead").prop("disabled", false);
		}

	});
	
	$('#startTime').timepicker({

		closeOnWindowScroll : true,
		disableTextInput : true,
		step : 15,
		timeFormat : 'H:i',
		maxTime : '17:00',
		minTime : '12:30'
	});

	$('#endTime').timepicker({

		closeOnWindowScroll : true,
		disableTextInput : true,
		step : 15,
		timeFormat : 'H:i',
		maxTime : '17:00',
		minTime : '12:30'
	});

	$('#regDate').datepicker({
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
	    
	}); 
	
	var d = new Date();
	var currMonth = d.getMonth();
	var currYear = d.getFullYear();
	var startDate = new Date(currYear,currMonth,1);
	$('#startDate').datepicker({
	    format: "dd/mm/yyyy",
	    language: "he" ,
	     startDate: startDate,
	    maxViewMode: 0,
	    minViewMode: 0,
	    todayBtn: true,
	    keyboardNavigation: false,
	    daysOfWeekDisabled: "5,6",
	    todayHighlight: true,
	    toggleActive: true 
	}); 
	
	$('#endDate').datepicker({
	    format: "dd/mm/yyyy",
	    language: "he" ,
	     startDate: "today",
	    maxViewMode: 0,
	    minViewMode: 0,
	    todayBtn: true,
	    keyboardNavigation: false,
	    daysOfWeekDisabled: "5,6",
	    todayHighlight: true,
	    toggleActive: true 
	}); 

	$('#extraPriceChk').click(function() {
		var isChecked = this.checked;
		if (isChecked) {
			$("#extraPriceDiv").toggle(true);

		} else {
			$("#extraPriceDiv").toggle(false);
			$("#extraPrice").val('');
		}

	});

	$('#detailsLink').attr('href',
			'course_card_view.jsp?activityNum=' + activityNum);
	/*
	 * 
	 * $('#scheduleLink').attr('href','pupil_week_view.jsp?li=1&pupil=' +
	 * activityNum); $('#regLink').attr('href','pupil_week_view.jsp?li=2&pupil=' +
	 * activityNum);
	 * $('#oneTimeLink').attr('href','pupil_week_view.jsp?li=3&pupil=' +
	 * activityNum);
	 */

	getSelectValuesFromDB("getSatff", "Staff","ActivityController");
	setSelectValues($('#responsibleStaff'), "Staff");
	
	getSelectValuesFromDB("getCourseType", "courseTypes","ActivityController");
	setSelectValues($('#courseTypeID'), "courseTypes");
	//
	
	getSelectValuesFromDB("getActGroup", "activityGroup","ActivityController", 1);
	setSelectValues($('#activityGroupHead'), "activityGroup");
	
	getSelectValuesFromDB("getGrades","grades","FullPupilCardController"); //getGrades
	getSelectValuesFromDB("get_GenderRef","genders","ActivityController"); //getGrades
	
	var dataString = 'activityNum=' + activityNum + '&action=' + "getCourses";
	loadCourseData(dataString);	
	/* set the validation for form */ 
	 validator = $("#ajaxform").validate(
			{

				errorPlacement : function(error, element) {
					// Append error within linked label
					error.css("color", "red");
					$(element).closest("form").find(
							"label[for='" + element.attr("id") + "']").append(
									error);
				},
				rules : {

					// set a rule to inputs
					// input must have name and id attr' and with same value !!!
					activityName : {
						required : true,
						minlength : 2,
						maxlength : 20,
					//	nameValidator : true
					// custom validation from additional-methods.js
					},
					responsibleStaff : {
						required : true						
					// custom validation from additional-methods.js
					},
					/*lName : {
						required : true,
						minlength : 2,
						maxlength : 20,
						nameValidator : true
					},*/

					extraPrice : {
						required : "#extraPriceChk:checked",
						digits : true
					},
					activityGroupHead: {
				        required : "#newGroup:not(:checked)"
				        
						
				    },
				    newActivityGroupHead: {
				        required: "#newGroup:checked"
				    	
				    }

				},
				errorElement : "span",

			});

	 validator1 = $("#ajaxPoUPform").validate(
			{

				errorPlacement : function(error, element) {
					// Append error within linked label
					error.html('שדה חובה').css("color", "red");
					$(element).closest("form").find(
							"label[for='" + element.attr("id") + "']").append(
									error);
				},
				rules : {

					// set a rule to inputs
					// input must have name and id attr' and with same value !!!
					/*endDate : {
						required : true,						
					// custom validation from additional-methods.js
					},*/

					regDate : {
						required : true,						
					},

					startDate : {
						required : true,	
					}

				},
				errorElement : "span",

			});
	
	$("#cancelBtn").click(function() {
		formDisable('ajaxform');
		currentPageState = state.READ;
		validator.resetForm();
		setCourseData(courseData);

		return false;
	});

	
	
	loadGrid('list');
	getCurrentYearEndDate();
	var rowCount =  $('#list').getGridParam("reccount");
	var rowCount1 = $('#list').jqGrid('getGridParam','records');
			
});

function setCourseData(courseData) {

	if (courseData != undefined) {

	//	$('.page-header').html("חוג " + courseData.activityName + " ");
		$('.page-header').html(courseData.activityGroup+ "  -  " + courseData.activityName );
		//activityGroup
		
		/* course details import */
		$('#activityName').val(courseData.activityName);
		$('#weekDay').val(courseData.weekDay);
		$('#startTime').val(courseData.startTime);
		$('#endTime').val(courseData.endTime);
		
	//	$('#activityGroup').val(courseData.activityGroup);
		$('#activityGroupHead').val(courseData.activityGNum);

		$('#pricePerMonth').val(courseData.pricePerMonth);
		$('#extraPrice').val(courseData.extraPrice);
		$("#capacity").val(courseData.pupilCapacity);
		$("#responsibleStaff").val(courseData.staffID);
		$("#activityGroup").val(courseData.activityGNum);

		if (courseData.extraPrice !== null) {
			$('#extraPriceChk').prop('checked', true);
			$("#extraPriceDiv").toggle(true);
			$("#extraPrice").val(courseData.extraPrice);
		} else {
			$('#extraPriceChk').prop('checked', false);
			$("#extraPriceDiv").toggle(false);
		}

		if (courseData.courseTypeID) {
			if(courseData.courseTypeID == 1){
				$('#courseTypeID :nth-child(1)').prop('selected', true);
			}
			else if(courseData.courseTypeID == 2){
				$('#courseTypeID :nth-child(2)').prop('selected', true);
			}
		}
			

	}
}

function loadCourseData(dataString) {
	setPageBtns();

	$("fieldset :input").prop("disabled", true);
	$("fieldset input").prop("disabled", false);
	$("fieldset :input").attr('readonly', 'readonly');
	$("fieldset :checkbox").prop("disabled", true);
	$("fieldset :radio").prop("disabled", true);
	$("#editModeBtn").hide();

	$.ajax({
		async : false,
		type : 'GET',
		datatype : 'jsonp',
		url : "ActivityController",
		data : dataString,
		success : function(data) {
			if (data != undefined) {
				courseData = data.rows[0];
				activityNum = courseData.activityNum;

				setCourseData(courseData);

			} else
				alert("לא קיימים נתונים");
		},
		error : function(e) {
			console.log("error");

		}

	});
	
}

function deleteCourse(id) {

}


function setPageBtns() {
	bootbox.setDefaults({
		locale : "he"
	});

	$("#saveBtn").click(
			function() {
				var result;
				// check for changes before saving data
				// if ($('#ajaxform').hasClass('dirty')) {

				// validate and process form here
				var form = $("#ajaxform");
				
				if (form.valid()) {
					result = saveCourseData("update", false);
					if (result === true) {
						formDisable('ajaxform');
						currentPageState = state.READ;
						$('.page-header').html($('#activityGroup').find("option:selected").text() + "  -  " + $('#activityName').val() );
						/* $('#ajaxform').trigger('reinitialize.areYouSure'); */
					}

				} else {

				}

				// }

				return false;
			});

	$("#deleteBtn").click(function() {
		bootbox.confirm("האם אתה רוצה למחוק?", function(result) {
			if (result === true) {
				deleteCourse(id);
			}
		});
		return false;
	});

	$("#addCourse").click(function() {
		//window.location.href = "pupil_add.jsp";

		return false;
	});

	$("#editBtn").click(function() {
		formEnable('ajaxform');
		currentPageState = state.EDIT;
		return false;
	});

}

function formEnable(form) {

	 $("#ajaxform :input").prop("disabled", false); 
	 $("#ajaxform :input").removeAttr('readonly');
	 $("#ajaxform :checkbox").prop("disabled", false);
	 $("#ajaxform :radio").prop("disabled", false);
	 $("#viewModeBtn").hide();
	 $("#editModeBtn").show();
	 $("#headerDiv").hide();
	 $("#headerEditDiv").show();
	 
	 
	/* $('#ajaxform').areYouSure( { message: "ישנם שינויים שלא נשמרו !"} ); */
}

function formDisable(form) {

	$("#ajaxform :input").prop("disabled", true);
	$("#ajaxform input").prop("disabled", false);
	$("#ajaxform :input").attr('readonly', 'readonly');
	$("#ajaxform :checkbox").prop("disabled", true);
	$("#ajaxform :radio").prop("disabled", true);
	$("#editModeBtn").hide();
	$("#viewModeBtn").show();
	$("#headerDiv").show();
	$("#headerEditDiv").hide();
	
}

function loadGrid(gridName) {

	var regoptions = {	value : "1:לא רשום;2:מועדונית;3:אוכל בלבד"	},oldDateVal = new Date(), lastSelection = -1, grid =$("#" +gridName),
	
	myDelOptions = {
		 rowData : {},
		
		 onClose : function (rowID, response) {
			 
	        },
		 errorTextFormat: function (response) {
			 
			 	$(this).restoreAfterErorr = false;
			 	var overlay = $('body > div.ui-widget-overlay'); //.is(":visible");
				 if (overlay.is(":visible")) {
					 overlay.remove();	
					 //$('#delmodlistRegistration').remove();
					 $("#delmod"+grid[0].id).remove();
					 
				}
			 	bootbox.alert("שגיאה במחיקת רשומה. נא נסה שוב.",
						function() {
				});
			 				
	            return true;
	           
		       
		    },
		   afterSubmit: function (response, postdata) {
			   var responseTxt = jQuery.parseJSON(response.responseText);
               if (responseTxt.msg ==1) {
            	   $("#popUPResultContent").empty();
            	   $("#popUPResultContent").append(responseTxt.result);
            	   popUPResult =  $('#popUPResult').bPopup({ //show popup
            			position : ['auto',100] ,			
            			positionStyle : 'absolute', 
            			onClose : onClosing,	        				
            			follow : ([false,false]),
            			modalClose : false
            		});
            		
            	   $(popUPResult).center(false);
             }
               
			   /*response = $.parseJSON(response.responseText);
			   // delete row
               grid.delRowData(rowData.rowid);
               bootbox.alert("רשומה נמחקה בהצלחה",
						function() {
				});
		        
			   console.log(response);*/
		        return [true, "success"];
		    },
		    afterComplete: function (response, postdata, formid) {
		    	response = $.parseJSON(response.responseText);
		       
		    },
	  	serializeDelData: function(postdata) { 
			return { pupilActivity : JSON.stringify(createPostData(activityNum, rowData,false))  } ;
       },
      
       onclickSubmit: function(funcParam, rowid) {
           // we can use onclickSubmit function as "onclick" on "Delete" button
          
           rowData = $(this).jqGrid("getRowData", rowid);
           //$.extend(rowData, {rowid: rowid});
           //var d = getDateFromValue( rowData.startDate);
           funcParam.url = "ActivityController?" + $.param({
           	action: "deletePupilActivity",
           	pupilActivity : rowData,
           	/*pupilID: pupilID,
               startDate : d.getTime(),*/
           });
          /* // delete row
           grid.delRowData(rowid);*/
          
           $("#delmod"+grid[0].id).hide();

          /* if (grid[0].p.lastpage > 1) {
               // reload grid to make the row from the next page visable.
               // TODO: deleting the last row from the last page which number is higher as 1
               grid.trigger("reloadGrid", [{page:grid[0].p.page}]);
           }*/

           return {}; // you can return additional data which will be sent to the server
           //return true;
       },            
   };
	
	$("#list")
			.jqGrid(
					{
						url : "ActivityController?action=getPupilInCourse&activityNum="
								+ activityNum,
						datatype : "json",
						editurl : "ActivityController?action=editPupilInCourse",
						mtype : 'GET',
						colNames : [ 'מספר חוג', 'שם חוג', 'מספר תלמיד','שם משפחה', 'שם פרטי', 'כיתה','תאריך רישום','תאריך התחלה', 'תאריך סיום' , 'פעולה'],
						colModel : [ {
							name : 'activityNum',
							index : 'activityNum',
							width : 100,
							hidden : true
						}, {
							name : 'activityName',
							index : 'activityName',
							hidden : true
						}, {
							name : 'pupilNum',
							index : 'pupilNum',
							width : 150,
							hidden : true
						}, {
							name : 'lastName',
							index : 'lastName',
							width : 100,
						}, {
							name : 'firstName',
							index : 'firstName',
							width : 100,
						},
						 {
							name : 'gradeName',
							cellattr : formatGradeCell,
							width : 50,
							title:false
						},{
							name : 'regDate',
							index : 'regDate',
							width : 100,
							formatter : formatDateInGrid,
							sorttype : "date",
							editable : false,
						    formatoptions: { newformat: "d/m/Y" },
							editoptions: {
		                            size: 20,
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
		                				    toggleActive: true 
		                				}); 
		                            },
		                            defaultValue: function () {
		                                var currentTime = new Date();
		                                var month = parseInt(currentTime.getMonth() + 1);
		                                month = month <= 9 ? "0" + month : month;
		                                var day = currentTime.getDate();
		                                day = day <= 9 ? "0" + day : day;
		                                var year = currentTime.getFullYear();
		                                return day + "/" + month + "/" + year;
		                            }
							 }
						}, {
							name : 'startDate',
							index : 'startDate',
							width : 100,
							formatter : formatDateInGrid,
							sorttype : "date",
							editable : true,
							formatoptions: { newformat: "d/m/Y" },
							editoptions: {
		                            size: 20,
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
		                				    toggleActive: true 
		                				}); 
		                            },
		                            defaultValue: function () {
		                                var currentTime = new Date();
		                                var month = parseInt(currentTime.getMonth() + 1);
		                                month = month <= 9 ? "0" + month : month;
		                                var day = currentTime.getDate();
		                                day = day <= 9 ? "0" + day : day;
		                                var year = currentTime.getFullYear();
		                                return day + "/" + month + "/" + year;
		                            }
							 }
						}, {
							name : 'endDate',
							index : 'endDate',
							width : 100,
							formatter : formatDateInGrid ,
							sorttype : "date",
							editable : true,
							formatoptions: { newformat: "d/m/Y" },
							editoptions: {
		                            size: 20,
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
		                				    toggleActive: true 
		                				}); 
		                            },
		                            defaultValue: function () {
		                                var currentTime = new Date();
		                                var month = parseInt(currentTime.getMonth() + 1);
		                                month = month <= 9 ? "0" + month : month;
		                                var day = currentTime.getDate();
		                                day = day <= 9 ? "0" + day : day;
		                                var year = currentTime.getFullYear();
		                                return day + "/" + month + "/" + year;
		                            }
							 }
						},
						{name : 'actions', index: 'actions', formatter:'actions', align: "center",	sortable:false,formatter:'actions',	
							width : 100,
						    formatoptions: {
						        keys: true,
						        editbutton: true,
						        onEdit:function(rowid) {
		                           
		                         },								
		                         onSuccess:function(jqXHR) {
		                           var response = jQuery.parseJSON(jqXHR.responseText);
			                           if (response.msg ==1) {
			                        	   $("#popUPResultContent").empty();
			                        	   $("#popUPResultContent").append(response.result);
			                        	   popUPResult =  $('#popUPResult').bPopup({ //show popup
				                    			position : ['auto',100] ,			
				                    			positionStyle : 'absolute', 
				                    			onClose : onClosing,	        				
				                    			follow : ([false,false]),
				                    			modalClose : false
				                    		});
				                    		
				                    	   $(popUPResult).center(false);
			                           }
		                     		
		                    		
		                             return true;
		                         },
		                         onError:function(rowid, jqXHR, textStatus) {
		                             // the function will be used as "errorfunc" parameter of editRow function
		                             // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#editrow)
		                             // and saveRow function
		                             // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#saverow)
		                        	 var response = jQuery.parseJSON(jqXHR.responseText);
		                        	 if (response.msg == 0) {
		                        		 bootbox.alert(response.result,
				                 					function() {
				                 					});
			                           }
		                        	 
		                        	return false;
		                            /* alert("in onError used only for remote editing:"+
		                                   "\nresponseText="+jqXHR.responseText+
		                                   "\nstatus="+jqXHR.status+
		                                   "\nstatusText"+jqXHR.statusText+
		                                   "\n\nWe don't need return anything");*/
		                         },
		                         afterSave:function(rowid) {
		                             /*alert("in afterSave (Submit): rowid="+rowid+"\nWe don't need return anything");*/
		                        	 // if endDate was saved as null reload page after editing to get the generated ednDate in DB
		                        	 var endDate = jQuery('#list').jqGrid ('getCell', rowid, 'endDate');
		                        	 if (endDate == '') {
		                        		 var myGrid = jQuery("#list").jqGrid({});
			                     		 myGrid.trigger('reloadGrid');
									  }
		                        	
		                         },
		                         afterRestore:function(rowid) {
		                             
		                         },		                         
						        delOptions: myDelOptions /*{ url: "PupilRegistration?action=delete&pupilID="+ pupilID }*/
						        }}
						],
						pager : '#pager',
						rowNum : 15,
						rowList : [],
						
						/* scroll: true, */
						direction : "rtl",
						viewrecords : true,
						gridview : true,
						height : "100%",
						loadComplete : function(data) {
							if(data.records !== undefined){
								if (parseInt(data.records, 10) == 0) {
									currentDate = new Date(); // default date
																// is today if
																// there are no
																// rows in this
																// grid
									//$("#pager div.ui-paging-info").show();
								} else {
									//$("#pager div.ui-paging-info").hide();
								}
							}else{
								//$("#pager div.ui-paging-info").show();
							}
														
							pupilCount = $('#list').jqGrid('getGridParam','records');
							$("#pupilCount").text("מספר תלמידים בחוג : " + pupilCount);
							
							if(courseData != null && courseData.pupilCapacity != null ){
								if(courseData.pupilCapacity <= pupilCount)
								setInfoMsg("מכסת התלמידים לחוג זה מלאה", true, 'alert alert-warning');
								else
									setInfoMsg('', false, null);
							}
							
						},
						loadError : function(xhr, status, error) {
							/* alert("complete loadError"); */
							jQuery("#rsperror").html("Type: "+st+"; Response: "+ xhr.status + " "+xhr.statusText);
						},
						serializeRowData: function(postdata) { 
							return { pupilActivity : JSON.stringify(createPostData(activityNum, postdata,true))  } ;
				        },
						ondblClickRow : function(rowId) {
							var rowData = jQuery(this).getRowData(rowId);
							var pupilID = rowData.pupilNum;
							window.location.href = "pupil_card_view.jsp?pupil="
									+ pupilID + "";
						},
						jsonReader : {
							repeatitems : false,
						},
						recreateFilter : true,

						rowList : [], // disable page size dropdown
						pgbuttons : true, // disable page control like next,
											// back button
						/* pgtext: null, */// disable pager text like 'Page 0
											// of 10'
						loadui : "block",
						viewrecords : true,
						ajaxSelectOptions : {
							dataType : 'json',
							cache : false
						}

					});
	jQuery("#list").jqGrid('navGrid', '#pager', {
		edit : false,
		add : false,
		del : false,
		search : false,
		refresh : false
	});

	
	// jQuery("#list").jqGrid('filterToolbar',{autosearch:true/*, stringResult:
	// true*/});

}

/***
 * show info msg
 * @param msgHtml - msg content (string)
 * @param msgStatus - dispay status (bolean)
 * @param msgCss - msg style (string)
 */
function setInfoMsg(msgHtml,msgStatus, msgCss){
	if(msgHtml != null) $('#InfoMsg').html("<strong>שיב לב</strong> " + msgHtml);
	$('#InfoMsg').removeClass();
	if(msgCss != null) $('#InfoMsg').addClass( msgCss);
	$('#InfoMsg').toggle(msgStatus);	
}

function closeThis(elem){
	
	popUPResult.close();
}
function openPopup() {

	// Prevents the default action to be triggered.

	// Triggering bPopup when click event is fired
	popUp = $('#mainPopUP').bPopup({
	    position : ['auto',20] ,
		positionStyle : 'absolute', 
		onClose : onClosing,
		onOpen : loadPupilGrid,
		follow : ([false,false]),
		modalClose : false
	});

}

/**
 * closing event of popup
 */
function onClosing() {
	
	if ($(this)[0].id === 'mainPopUP') {
		
		var courseGrid = jQuery("#list").jqGrid({});
		courseGrid.trigger('reloadGrid');
		validator1.resetForm();
		
		var popUpGrid = jQuery("#listPopUp");
		clearGridSearch(popUpGrid, false, true);
		
		//$.jgrid.gridUnload("listPopUp");
		
	}
	else if($(this)[0].id === 'popUPResult'){
		
		
		var popUpGrid = jQuery("#listPopUp");
		clearGridSearch(popUpGrid, true);
		

		//myGrid.trigger('reloadGrid');
		
	}
	
}

/**
 * add pupil activity for pupil to this course
 * @param PupilActivity - PupilActivity entity 
 * @param action - action to preform (insert)
 * @returns {Boolean} return true if adding was suuccesfull otherwise false 
 */
function addPupilToCourse(PupilActivity, action){
	var result =false;  

	$.ajax({
	  		async: false,
			type: 'POST',
			datatype: 'jsonp',
	        url: "ActivityController",
	        data: { action: action , activityData : JSON.stringify(PupilActivity)	  },
	        	
	        success: function(data) {
	        	if(data != undefined){
	        		/*alert(data);*/
	        		if(data.msg == "1"){
	        			result = true;
	        			var msgResult = data.result;
	        			
	        		}
	        		else if(data.msg == "0"){	
	        			result = false;
	        		
	        		}
	        	}
	        },
	        error: function(e) {
	        	result = false;
	        	console.log(e);
			        	bootbox.alert("שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.", function() {	   	        		 
			        	});			
	        }
	        
	      });
	
	return result;
}

/**
 * get ids of all selected pupil and fire addPupilToCourse for each one
 */
function AddSelectedPupil() {

	if (popUp) {		 
		
		 var div1 = $("<div>").append('התלמידים הבאים נוספו בהצלחה').append("</br>").append("<ul></ul>");
		 var div2 = $("<div>").append('התלמידים הבאים לא נוספו').append("</br>").append("<ul></ul>");
		 var resultpopup  = $("#popUPResultContent").empty();
		 var ok, error;
		 var rowData ;
		 var form = $("#ajaxPoUPform"); //get the form on popup
		 if (form.valid()){ //validate form
			 selectedIds = jQuery("#listPopUp").getGridParam('selarrrow'); //get selected ids
			 
			 if (selectedIds.length > 0) { // if we selected some on
				 
				 
				 for (var i = 0, ok = 0, error = 0; i < selectedIds.length; i++) {		    		
						var PupilActivity = new Object();
						var tempDate = $( '#startDate' ).datepicker( "getDate" );
						rowData = jQuery('#listPopUp').jqGrid ('getRowData', selectedIds[i]); // get row data on grid and add it to pop up result
						PupilActivity.id = {pupilNum : rowData.pupilNum ,activityNum: activityNum, startDate : tempDate };
						PupilActivity.endDate = $( '#endDate' ).datepicker( "getDate" ); 
						PupilActivity.regDate = $( '#regDate' ).datepicker( "getDate" );
						//PupilActivity.startDate = $( '#startDate' ).datepicker( "getDate" );
						PupilActivity.tblPupil = { pupilNum : rowData.pupilNum };
						PupilActivity.tblUser = null;						
						var result  = addPupilToCourse(PupilActivity, 'insertPupilActivity'); // fire ajax funw to add row on server
						
						var li = "<li>";
						if (result) { // if adding was ok
							$(div1).find('ul').append(li.concat(rowData.firstName + ' ' + rowData.lastName));
							ok++;
						}else{ // there was an error on adding row on server
							error++;
							$(div2).find('ul').append(li.concat(rowData.firstName + ' ' + rowData.lastName));
						}
					}
				 
				  
					
					if(ok){ // count of added rows
						$(resultpopup).append(div1);
					}
					
					if(error){ // count of rows the had error
						$(resultpopup).append('</br>');
						$(resultpopup).append(div2); // add data to popup
					}
			 }
			 else{
				 
				 var divEmpty = $("<div>").append('יש לסמן תלמידים להוספה').append("</br>");
				 $(resultpopup).append(divEmpty); // add msg to popup
			 }
			
			
			 
			popUPResult =  $('#popUPResult').bPopup({ //show popup
				position : ['auto',100] ,			
				positionStyle : 'absolute', 
				onClose : onClosing,	        				
				follow : ([false,false]),
				modalClose : false
			});
			
			$(popUPResult).center(false); //center pop up (function from js_logic file)
		 }

		//popUp.close();
	}

}

function loadPupilGrid() {

	var date = new Date();
	$("#regDate").datepicker("setDate" , date );
	
	var date = new Date(CurrentYearEndDate);
	if (CurrentYearEndDate != null) {
		$("#endDate").datepicker("setDate" , date );
	}
	
	
	if (popUp) {
		var myGrid = jQuery("#listPopUp").jqGrid({
			//prmNames: { page: null, rows: null, sort: null, order: null },
		    /*postData: {
		        StateId: function() { return jQuery("#StateId option:selected").val(); },
		        CityId: function() { return jQuery("#CityId option:selected").val(); },
		        hospname: function() { return jQuery("#HospitalName").val(); }
		    }*/ 
		    // ...
			
		});
		myGrid.trigger('reloadGrid');
		
	} else {
		var grid = $("#listPopUp"),i;

		$("#listPopUp")
				.jqGrid(
						{
							
							
							url : "FullPupilCardController?action=SelectPupilNotInActivity&activityNum="
								+ activityNum + "&weekDay=" + courseData.weekDay,
							datatype : "json",
							mtype : 'POST',
							colNames : [ 'מספר','מאופשר', 'שם משפחה', 'שם פרטי', 'מגדר',
									'כיתה', 'רשום','חוגים נוספים באותו יום' ],
							loadComplete : function(data) {
								var cbs = $("tr.jqgrow > td > input.cbox",this);

								if (parseInt(data.records, 10) == 0) {
									currentDate = new Date(); // default date
																// is today if
																// there are no
																// rows in this
																// grid
									$("#pager div.ui-paging-info").show();
								} else {
									$("#pager div.ui-paging-info").hide();
								}
								
								for (var int = 0; int < data.rows.length; int++) {
									if(data.rows[int].isEnabled == 2){
										var cb = $("tr.jqgrow > td > input.cbox",this).eq(int);
										cb.attr("disabled", true);

									}
								}
							},
							loadError : function(xhr, status, error) {
								/* alert("complete loadError"); */
							},
							/*rowattr: function (item) {
			                    if (parseInt(item.isEnabled, 10) === 2) { //disable selection of rows
			                        //return {"class": "ui-state-disabled ui-jqgrid-disablePointerEvents"};
			                        return {"class": "ui-jqgrid-disablePointerEvents"};
			                    }
			                },*/
			                beforeSelectRow: function (rowid, e) {
			                    /*if ($(e.target).closest("tr.jqgrow").hasClass("ui-state-disabled")) {
			                        return false;   // dont allow selection the of
			                    }
			                    return true;    // allow selection of row
*/			                
				                var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox:disabled", grid[0]);
			                    if (cbsdis.length === 0) {
			                        return true;    // allow select the row
			                    } else {
			                        return false;   // not allow select the row
			                    }

			                },
			                onSelectAll: function(aRowids,status) {
			                    if (status) {
			                        // uncheck "protected" rows
			                    	
			                    	var cbs = $("tr.jqgrow > td > input.cbox:disabled", grid[0]);
			                        cbs.removeAttr("checked");

			                        //modify the selarrrow parameter
			                        this.p.selarrrow = grid.find("tr.jqgrow:has(td > input.cbox:checked)")
			                            .map(function() { return this.id; }) // convert to set of ids
			                            .get(); // convert to instance of Array
			                        /*var trChecked = $("tr.jqgrow:has(td > input.cbox:checked)", grid[0]);
			                        var newSelArr = [];
			                        for (i=0; i<trChecked.length; i++) {
			                            newSelArr.push(trChecked[i].id);
			                        }
			                        grid[0].p.selarrrow = newSelArr;*/
			                    }
			                },

							colModel : [
									{
										name : 'pupilNum',
										index : 'pupilNum',
										width : 100,
									hidden:true
									},
									{
										name : 'isEnabled',
										index : 'isEnabled',										
										hidden:true
									},
									{
										name : 'lastName',
										index : 'lastName',
										width : 150,
										title:false
									},
									{
										name : 'firstName',
										index : 'firstName',
										width : 150,
										title:false
									},
									{
										name : 'gender',
										index : 'gender',
										width : 100,
									
										stype : "select",
										searchoptions : {
											value : ":;1:בן;2:בת"
										},
										title:false,
									    formatter : formattCell
									    
									},
									{
										name : 'gradeID',
										index : 'gradeID',
										width : 100,
										formatter : formattCell,
										stype : "select",
										cellattr : formatGradeCell,
										title:false,
										searchoptions : {
											dataUrl : "FullPupilCardController?action=getGrades",
											buildSelect : function(data) {
												var codes, i, l, code, prop;
											
												var s = '<select id="gradeSelect" >', codes, i, l, code, prop;
												if (data) {
													codes = data.value
															.split(';');
													for (i = 0,
															l = codes.length; i < l; i++) {
														code = codes[i];
														// enumerate properties
														// of code object
														for (prop in code) {
															if (code
																	.hasOwnProperty(prop)) {
																var op = code
																		.split(':');
																if (op[0] == ' ') {
																	// FFFFFF
																	s += '<option style="background-color:#FFFFFF" value="'
																			+ op[0]
																			+ '">'
																			+ op[1]
																			+ '</option>';
																	break; // we
																			// need
																			// only
																			// the
																			// first
																			// property
																} else {
																	s += '<option style="background-color:'
																			+ op[2]
																			+ '" value="'
																			+ op[0]
																			+ '">'
																			+ op[1]
																			+ '</option>';
																	break; // we
																			// need
																			// only
																			// the
																			// first
																			// property
																}

															}
														}
													}
												}

												return s + "</select>";
											}
										}
									// grades
									}, {
										name : 'isReg',
										index : 'isReg',
										width : 110,
										title:false,
										stype : "select",
										searchoptions : {
											value : ":;2:רשום;1:לא רשום"
										},
										formatter : "checkbox",
									} ,
									{
										name : 'courses',
										index : 'courses',
										width : 150,
										//search:false
										
									}],
							pager : '#pagerPopUp',
							rowNum : 10,
							rowList : [],
							/* scroll: true, */
							multiselect: true,
							sortname : 'gradeID',
							direction : "rtl",
							viewrecords : true,
							height : "100%",
							gridview : true,
							
							/*
							 * ondblClickRow: function(rowId) { var rowData =
							 * jQuery(this).getRowData(rowId); var pupilID =
							 * rowData.id; window.location.href =
							 * "pupil_card_view.jsp?pupil="+pupilID+""; },
							 */
							jsonReader : {
								repeatitems : false,
							},
							recreateFilter : true,

							rowList : [], // disable page size dropdown
							pgbuttons : true, // disable page control like
												// next, back button
							/* pgtext: null, */// disable pager text like 'Page
												// 0 of 10'
							loadui : "block",
							ajaxSelectOptions : {
								dataType : 'json',
								cache : false
							}

						});
		jQuery("#listPopUp").jqGrid('navGrid', '#pagerPopUp', {
			edit : false,
			add : false,
			del : false,
			search : false,
			refresh : false
		});

		jQuery("#listPopUp").jqGrid('filterToolbar', {
			autosearch : true
		/* , stringResult: true */});

	}

}

/**
 * create the data to be sent to edit function on server
 * @param activityID
 * @param dateVal 
 * @param rowData from grid edit row
 * @returns { json objectc } - regToMoadonit data
 */
function createPostData(activityID, rowData,isEdit){
	
	var pupilActivity = new Object();
	var _pupilNum = (rowData.pupilNum !== undefined) ? rowData.pupilNum : jQuery('#list').jqGrid ('getCell', rowData.id, 'pupilNum') ;
	var regDate = (rowData.regDate !== undefined) ? rowData.regDate : jQuery('#list').jqGrid ('getCell', rowData.id, 'regDate') ;
	var tempDate = getDateFromValue(rowData.startDate);
	pupilActivity.id = {			
		pupilNum : _pupilNum ,
		activityNum : activityID,
		startDate : tempDate
	}; // pk

			
	pupilActivity.tblPupil = {
			pupilNum : _pupilNum
	};
	
	pupilActivity.tblUser = null;	
	pupilActivity.regDate = getDateFromValue(  regDate);
	//pupilActivity.startDate = getDateFromValue(rowData.startDate);
	pupilActivity.endDate = getDateFromValue(rowData.endDate);	
	
	return pupilActivity;
}

function formatGradeCell(rowId, val, rawObject, cm, rdata){
	//"style" : "background:"+colors.future+";","data-isHistory": false
	var cellVal='';
	var title = 'title="';
	if(window["grades"] != null){
		var gradesCopy = window["grades"].value.split(';'); 
		
	    $.each(gradesCopy, function(key, value) {  
	    	value  = value.split(":");
	    	if(key != 0){ 
	    		if(val == value[1]){
	    			cellVal =  'style="border-color:'+ value[2]+'; border-width: 3px;" ';	 	    			
	    		//$select.append('<option style="background-color:'+ value[2]+'" value=' + value[0] + '>' + value[1] + '</option>');
	    		}
	    	}
	    });
	    
	    if(this.id === "listPopUp" && rdata.isEnabled === 2){
			
			title += 'לא ניתן להוסיף';
			title+= rdata.gender == 1 ? ' תלמיד זה, לתלמיד' : ' תלמידה זו, לתלמידה';
			title += ' קיים רישום לחוג זה או לחוגים חופפים';
			
			cellVal ;
		}
	}
	
	//title += '"';
	return cellVal;
	
}

function formattCell(cellValue, options, rawData, action){
	var returnVal = '';
	 switch (options.colModel.name) {
	    case "gender":
	    	if(window["genders"] != null){
	    		for (var int = 0; int < window["genders"].length; int++) {
					if(window["genders"][int].gender == cellValue){
						returnVal = window["genders"][int]["genderName"];
				        return returnVal;
					}
				}
	    	}
	    	else{
	    		  return $.jgrid.htmlEncode(cellValue);
	    	}
	    	
	    case "gradeID":
	    	//grades
	    	if(window["grades"] != null){
	    		var arr = window["grades"].value.split(';');
	    		for (var int = 0; int < arr.length; int++) {
	    			var code = arr[int].split(':');
					if(code[0] == cellValue){
						returnVal = code[1];
				        return returnVal;
					}
				}
	    	}
	    	else{
	    		  return $.jgrid.htmlEncode(cellValue);
	    	}
	    default:
	        return $.jgrid.htmlEncode(cellValue);
	 }
}