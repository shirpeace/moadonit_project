/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/** ********************************************** */

/* the current user the us logged if to the system */
var currentUserId = '<%=session.getAttribute("userid")%>';
var selectedIds;
/*
 * activityNum, activityType, activityName, , startTime, endTime, schoolYear,
 * responsibleStaff, pricePerMonth, extraPrice, regularOrPrivate, category
 * 
 */

// define state for the editable page
/** ********************************************** */
// TODO //* START PUPILADD PAGE FUNCTIONS */
/** ********************************************** */
var courseData;
// set the state at start to read. (state object from js_logic file)
var currentPageState = state.READ;
var popUp, popUPResult;
var validator , validator1;
$(function() {

	moment.locale(); // he

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
	    toggleActive: true 
	}); 
	
	$('#startDate').datepicker({
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

	var dataString = 'activityNum=' + activityNum + '&action=' + "getCourses";
	loadCourseData(dataString);

	/* set the validattion for form */ 
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
						nameValidator : true
					// custom validation from additional-methods.js
					},

					lName : {
						required : true,
						minlength : 2,
						maxlength : 20,
						nameValidator : true
					},

					extraPrice : {
						required : "#extraPriceChk:checked",
						digits : true
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
					endDate : {
						required : true,						
					// custom validation from additional-methods.js
					},

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

	loadGrid();
});

function setCourseData(courseData) {

	if (courseData != undefined) {

		$('.page-header').html("חוג " + courseData.activityName + " ");

		/* pupil details import */
		$('#activityName').val(courseData.activityName);
		$('#weekDay').val(courseData.weekDay);
		$('#startTime').val(courseData.startTime);
		$('#endTime').val(courseData.endTime);
		$('#responsibleStaff').val(courseData.responsibleStaff);

		$('#pricePerMonth').val(courseData.pricePerMonth);
		$('#extraPrice').val(courseData.extraPrice);

		if (courseData.extraPrice !== null) {
			$('#extraPriceChk').prop('checked', true);
			$("#extraPriceDiv").toggle(true);
			$("#extraPrice").val(courseData.extraPrice);
		} else {
			$('#extraPriceChk').prop('checked', false);
			$("#extraPriceDiv").toggle(false);
		}

		if (courseData.regularOrPrivate
				&& courseData.regularOrPrivate == 'רגיל') {
			$('#regularOrPrivate :nth-child(1)').prop('selected', true);
		} else
			$('#regularOrPrivate :nth-child(2)').prop('selected', true);

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

function deletePupil(id) {

}

function saveCourseData(action, forward) {

	var activity = new Object();
	activity.activityNum = activityNum;
	activity.tblActivityType = {
		typeID : 1,
	}; // course type id
	activity.activityName = $('#activityName').val();
	// activity.startTime = $('#startTime').val();
	// activity.endTime = $('#endTime').val();
	activity.schoolYear = 0;
	activity.tblStaff = {
		staffID : $('#responsibleStaff').val(),
		firstName : "",
		lastName : "",
	};
	activity.tblCourse = {

		activityNum : activityNum,
		category : 0,
		pricePerMonth : $('#pricePerMonth').val(),
		regularOrPrivate : $('#regularOrPrivate').val(),
		extraPrice : $('#extraPrice').val(),
	};

	var result;

	$
			.ajax({
				async : false,
				type : 'POST',
				datatype : 'jsonp',
				url : "ActivityController",
				data : {
					action : action,
					activityData : JSON.stringify(activity),
					endTime : $('#startTime').val(),
					startTime : $('#endTime').val()
				},

				success : function(data) {
					if (data != undefined) {
						/* alert(data); */
						/**
						 * FIX error of update/
						 */
						if (!data.msg) {
							result = true;
						}
						if (data.msg == "1") {
							result = true;
							activityNum = data.result;
							if (action === "insert") {
								if (typeof forward != undefined && forward) {
									bootbox
											.alert(
													"נתונים נשמרו בהצלחה, הנך מועבר למסך החוג",
													function() {
														// send user to the
														// pupil page after
														// successful insert
														window.location.href = "course_card_view.jsp?activityNum="
																+ activityNum
																+ "";
													});
								} else {
									bootbox.alert("נתונים נשמרו בהצלחה",
											function() {
											});
								}

							} else {
								bootbox.alert("נתונים עודכנו בהצלחה",
										function() {
										});

							}
						} else if (data.msg == "0") {
							result = false;
							bootbox
									.alert(
											"שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.",
											function() {
											});
						}
					}
				},
				error : function(e) {
					result = false;
					console.log(e);
					bootbox
							.alert(
									"שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.",
									function() {
									});
				}

			});

	return result;

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
				// var dateVal = $("#date_of_birth").combodate('getValue',
				// null);
				// form.validate();
				if (form.valid()) {
					// if(dateVal == null || dateVal == "")
					// return false;

					result = saveCourseData("update", false);
					if (result === true) {
						formDisable('ajaxform');
						currentPageState = state.READ;
						$('.page-header').html(
								$('#activityName').val() + " " + " " + "חוג ");
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
				deletePupil(id);
			}
		});
		return false;
	});

	$("#addCourse").click(function() {
		window.location.href = "pupil_add.jsp";

		return false;
	});

	$("#editBtn").click(function() {
		formEnable('ajaxform');
		currentPageState = state.EDIT;
		return false;
	});

}

function formEnable(form) {

	 $("fieldset :input").prop("disabled", false); 
	 $("fieldset :input").removeAttr('readonly');
	 $("fieldset :checkbox").prop("disabled", false);
	 $("fieldset :radio").prop("disabled", false);
	 $("#viewModeBtn").hide();
	 $("#editModeBtn").show();

	/* $('#ajaxform').areYouSure( { message: "ישנם שינויים שלא נשמרו !"} ); */
}

function formDisable(form) {

	$("fieldset :input").prop("disabled", true);
	$("fieldset input").prop("disabled", false);
	$("fieldset :input").attr('readonly', 'readonly');
	$("fieldset :checkbox").prop("disabled", true);
	$("fieldset :radio").prop("disabled", true);
	$("#editModeBtn").hide();
	$("#viewModeBtn").show();
	
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
			   response = $.parseJSON(response.responseText);
			   // delete row
               grid.delRowData(rowData.rowid);
               bootbox.alert("רשומה נמחקה בהצלחה",
						function() {
				});
		        
			   console.log(response);
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
           	action: "delete",
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
						colNames : [ 'מספר חוג', 'שם חוג', 'מספר תלמיד','שם משפחה', 'שם פרטי', 'תאריך רישום','תאריך התחלה', 'תאריך סיום' , 'פעולה'],
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
						}, {
							name : 'regDate',
							index : 'regDate',
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
						    formatoptions: {
						        keys: true,
						        editbutton: true,
						        onEdit:function(rowid) {
		                           
		                         },								
		                         onSuccess:function(jqXHR) {
		                           
		                             return true;
		                         },
		                         onError:function(rowid, jqXHR, textStatus) {
		                             // the function will be used as "errorfunc" parameter of editRow function
		                             // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#editrow)
		                             // and saveRow function
		                             // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#saverow)
		                        	 bootbox.alert("שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.",
		                 					function() {
		                 					});
		                            /* alert("in onError used only for remote editing:"+
		                                   "\nresponseText="+jqXHR.responseText+
		                                   "\nstatus="+jqXHR.status+
		                                   "\nstatusText"+jqXHR.statusText+
		                                   "\n\nWe don't need return anything");*/
		                         },
		                         afterSave:function(rowid) {
		                             /*alert("in afterSave (Submit): rowid="+rowid+"\nWe don't need return anything");*/
		                         },
		                         afterRestore:function(rowid) {
		                             
		                         },		                         
						        delOptions: myDelOptions /*{ url: "PupilRegistration?action=delete&pupilID="+ pupilID }*/
						        }}
						],
						pager : '#pager',
						rowNum : 50,
						rowList : [],
						sortname : 'gradeName',
						/* scroll: true, */
						direction : "rtl",
						viewrecords : true,
						gridview : true,
						height : "100%",
						loadComplete : function(data) {
							
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
						},
						loadError : function(xhr, status, error) {
							/* alert("complete loadError"); */
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
		
		var myGrid = jQuery("#list").jqGrid({});
		myGrid.trigger('reloadGrid');
		validator1.resetForm();
	}
	else if($(this)[0].id === 'popUPResult'){
		var myGrid = jQuery("#listPopUp").jqGrid({});
		myGrid.trigger('reloadGrid');
		
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
						PupilActivity.id = {pupilNum : selectedIds[i] ,activityNum: activityNum };
						PupilActivity.endDate = new Date();
						PupilActivity.regDate = new Date();
						PupilActivity.startDate = new Date();
						PupilActivity.tblPupil = { pupilNum : selectedIds[i] };
						PupilActivity.tblUser = null;						
						var result  = addPupilToCourse(PupilActivity, 'insertPupilActivity'); // fire ajax funw to add row on server
						rowData = jQuery('#listPopUp').jqGrid ('getRowData', selectedIds[i]); // get row data on grid and add it to pop up result
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
			
			$(popUPResult).center(true); //center pop up (function from js_logic file)
		 }

		//popUp.close();
	}

}

function loadPupilGrid() {

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
		$("#listPopUp")
				.jqGrid(
						{
							url : "FullPupilCardController?action=SelectPupilNotInActivity&activityNum="
								+ activityNum,
							datatype : "json",
							mtype : 'POST',
							colNames : [ 'מספר', 'שם פרטי', 'שם משפחה', 'מגדר',
									'כיתה', 'רשום' ],
							loadComplete : function(data) {
								
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
							},
							loadError : function(xhr, status, error) {
								/* alert("complete loadError"); */
							},
							colModel : [
									{
										name : 'id',
										index : 'id',
										width : 100,
										hidden : true
									},
									{
										name : 'firstName',
										index : 'firstName',
										width : 150,
										editable : true
									},
									{
										name : 'lastName',
										index : 'lastName',
										width : 150,
										editable : true
									},
									{
										name : 'gender',
										index : 'gender',
										width : 100,
										editable : true,
										stype : "select",
										searchoptions : {
											value : ":;1:בן;2:בת"
										}
									},
									{
										name : 'gradeName',
										index : 'gradeName',
										width : 100,
										editable : true,
										stype : "select",
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
										width : 100,
										editable : false,
										stype : "select",
										searchoptions : {
											value : ":;1:רשום;2:לא רשום"
										},
										formatter : "checkbox",
									} ],
							pager : '#pagerPopUp',
							rowNum : 50,
							rowList : [],
							/* scroll: true, */
							multiselect: true,
							sortname : 'gradeName',
							direction : "rtl",
							viewrecords : true,
							//gridview : true,
							height : "100%",
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
		
	pupilActivity.id = {			
		pupilNum : rowData.pupilNum ,
		activityNum : activityID
	}; // pk
	if (isEdit) {
			pupilActivity.regDate = rowData.regDate;
			pupilActivity.startDate = rowData.startDate;
			pupilActivity.tblPupil = {
					pupilNum : rowData.pupilNum
			};
			pupilActivity.tblUser = null;			
	}		

	return pupilActivity;
}
