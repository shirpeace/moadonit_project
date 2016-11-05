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
var popUp, popUPResult, courseTypes;
var validator , validator1;
$(function() {

	moment.locale(); // he
	
	getSelectValuesFromDB("getCourseType", "courseTypes","ActivityController");
	setSelectValues($('#courseTypeID'), "courseTypes");
	
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

	$('#extraPriceChk').click(function() {
		var isChecked = this.checked;
		if (isChecked) {
			$("#extraPriceDiv").toggle(true);

		} else {
			$("#extraPriceDiv").toggle(false);
			$("#extraPrice").val('');
		}

	});


	getSelectValuesFromDB("getSatff", "Staff","ActivityController");
	setSelectValues($('#responsibleStaff'), "Staff");
	
	getSelectValuesFromDB("getActGroup", "activityGroup","ActivityController", 1);
	setSelectValues($('#activityGroupHead'), "activityGroup");
	
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
						required : true/*,
						isTimeOK: true				*/
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
				        required : "#newGroup:not(:checked)"
				        
						
				    },
				    newActivityGroupHead: {
				        required: "#newGroup:checked"
				    	
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

/*	 validator.addMethod("isTimeOK", function() { 
			var start = $("#startTime").val();
			var end = $("#endTime").val();
			if (start>=end)
				return false;
			else
				return true;
		}, "שעת התחלה אחרי שעת סיום"); */
});
/*function isTimeOK(){
	var start = $("#startTime").val();
	var end = $("#endTime").val();
	if (start>=end)
		return false;
	else
		return true;
	
};*/




/*function saveCourseData(action, forward) {

	var activity = new Object();
	activity.activityNum = activityNum;
	activity.activityName = $('#activityName').val();
	
	var groupNum ;
	var isChecked = $('#newGroup').prop("checked");
	if (isChecked){
		groupNum = -1;
	}
	else groupNum = $('#activityGroupHead').val();
	activity.tblActivityGroup ={
			activityGroupNum : groupNum,
			actGroupName : $('#newActivityGroupHead').val(),
			tblActivityType : {
				typeID : 1,
			}
	};

	activity.weekDay = $('#weekDay').val();
	activity.tblSchoolYear = {
			yearID : 0
	};
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
						 alert(data); 
						*//**
						 * FIX error of update/
						 *//*
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

}*/

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
					result = saveCourseData("insert", true);
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
		$("#newActivityGroupDiv").toggle(false);
		$("#activityGroupHead").prop("disabled", false);
		 return false;
	});
 
	
	$("#saveClearBtn").click(function() {
		
		var result;
		// validate and process form here
		 var form = $("#ajaxform");
		 if (form.valid()) {	
			result =  saveCourseData("insert", true);
			 if (result) {
				 $("#clearBtn").click();
			}
		} else {
			
		} 
		 return false;

	});

}