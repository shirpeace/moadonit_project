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
// TODO //* START COURSE PAGE FUNCTIONS */
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

	$('#extraPriceChk').click(function() {
		var isChecked = this.checked;
		if (isChecked) {
			$("#extraPriceDiv").toggle(true);

		} else {
			$("#extraPriceDiv").toggle(false);
			$("#extraPrice").val('');
		}

	});

/*	$('#detailsLink').attr('href',
			'course_card_view.jsp?activityNum=' + activityNum);*/
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
	
/*	var dataString = 'activityNum=' + activityNum + '&action=' + "getCourses";
	loadCourseData(dataString);	*/
	
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
						//nameValidator : true
					},
					weekDay : {
						required : true						
					},
					startTime : {
						required : true						
					},
					endTime : {
						required : true,
						isTimeOK: true				
					},
					responsibleStaff : {
						required : true						
					},
					capacity : {
						required : true,
						digits : true
					},
					pricePerMonth : {
						required : true,
						digits : true					
					},
					extraPrice : {
						required : "#extraPriceChk:checked",
						digits : true
					},
					activityGroupHead: {
				       /* required : function(element) {
				            return $("#newActivityGroupHead").is(':filled');
				            }*/
						required : "#newActivityGroupHead:filled"
				        
						
				    },
				    newActivityGroupHead: {
				        required: function(element) {
				            return $("#activityGroupHead").is(':empty');
				        }
				    	
				    }

				},
				errorElement : "span",

			});

	 setPageBtns();
	 	
		
	/*$("#cancelBtn").click(function() {
		formDisable('ajaxform');
		currentPageState = state.READ;
		validator.resetForm();
		setCourseData(courseData);

		return false;
	});*/

	
});
/*function isTimeOK(){
	var start = $("#startTime").val();
	var end = $("#endTime").val();
	if (start>=end)
		return false;
	else
		return true;
	
};*/
$.validator.addMethod("isTimeOK", function() { 
	var start = $("#startTime").val();
	var end = $("#endTime").val();
	if (start>=end)
		return false;
	else
		return true;
}, "שעת התחלה אחרי שעת סיום");
/*function setCourseData(courseData) {

	if (courseData != undefined) {

	//	$('.page-header').html("חוג " + courseData.activityName + " ");
		$('.page-header').html(courseData.activityGroup+ "  -  " + courseData.activityName );
		//activityGroup
		
		 course details import 
		$('#activityName').val(courseData.activityName);
		$('#weekDay').val(courseData.weekDay);
		$('#startTime').val(courseData.startTime);
		$('#endTime').val(courseData.endTime);
		
		$('#activityGroup').val(courseData.activityGroup);

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

		if (courseData.regularOrPrivate
				&& courseData.regularOrPrivate == 'רגיל') {
			$('#regularOrPrivate :nth-child(1)').prop('selected', true);
		} else
			$('#regularOrPrivate :nth-child(2)').prop('selected', true);

	}
}*/

/*function loadCourseData(dataString) {
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
	
}*/


function saveCourseData(action, forward) {

	var activity = new Object();
	activity.activityNum = activityNum;
	activity.activityName = $('#activityName').val();
	
	activity.tblActivityGroup ={
			activityGroupNum : $('#activityGroupHead').val(),
			actGroupName : $('#activityGroupHead').find("option:selected").text(),
			tblActivityType : {
				typeID : 1,
			}
	};

	activity.weekDay = $('#weekDay').val();
	activity.schoolYear = 0;
	activity.tblStaff = {
		staffID : $('#responsibleStaff').val()
	};
	activity.tblCourse = {

		activityNum : activityNum,
		category : 0,
		pricePerMonth : $('#pricePerMonth').val(),
		regularOrPrivate : $('#regularOrPrivate').val(),
		extraPrice : $('#extraPrice').val(),
		pupilCapacity: $('#capacity').val()
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
					startTime : $('#startTime').val(),
					endTime : $('#endTime').val()
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
							if (data.result != null) activityNum = data.result;
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
				var form = $("#ajaxform");
				if (form.valid()) {
					result = saveCourseData("insert", false);
					if (result === true) {
						formDisable('ajaxform');
						$('.page-header').html($('#activityGroup').find("option:selected").text() + "  -  " + $('#activityName').val() );
					}

				} else {

				}

				// }

				return false;
			});
	
	$("#clearBtn").click(function() {
		
		validator.resetForm();
		 
		$(this).closest('form')[0].reset();
//		setGradeBgColor($('#grade'));
		 return false;
	});
 
	
	$("#saveClearBtn").click(function() {
		
		var result;
		// validate and process form here
		 var form = $("#ajaxform");
		 if (form.valid()) {	
		//	result =  savePupilCardData("insert",false);
			 if (result) {
				 $("#clearBtn").click();
			}
		} else {
			
		} 
		 return false;

	});

}