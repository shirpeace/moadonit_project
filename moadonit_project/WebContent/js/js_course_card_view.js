
/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/*************************************************/

/*   the current user the us logged if to the system */
var currentUserId =	 '<%=session.getAttribute("userid")%>';	
/*
activityNum, activityType, activityName, , startTime,
	 endTime, schoolYear, responsibleStaff, pricePerMonth, extraPrice, regularOrPrivate, category
	 
 * */

//define state for the editable page
var state = {
    EDIT: 1,
    READ: 0,	    
};
/*************************************************/
//TODO //*  START  PUPILADD PAGE FUNCTIONS       */
/*************************************************/
var courseData;	
// set the state at start to read. (state object from js_logic file)
var currentPageState = state.READ;


$(function(){

		 moment.locale();         // he

         $('#startTime').timepicker({
			    
			    closeOnWindowScroll : true,
			    disableTextInput: true,
			    step: 15,
			    timeFormat : 'H:i',
			    maxTime : '17:00',
			    minTime : '12:30'
			});
		 
         $('#endTime').timepicker({
			    
			    closeOnWindowScroll : true,
			    disableTextInput: true,
			    step: 15,
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
		
		$('#detailsLink').attr('href','pupil_card_view.jsp?li=0&pupil=' + activityNum);
		/* $('#scheduleLink').attr('href','pupil_week_view.jsp?li=1&pupil=' + activityNum);
		$('#regLink').attr('href','pupil_week_view.jsp?li=2&pupil=' + activityNum);
		$('#oneTimeLink').attr('href','pupil_week_view.jsp?li=3&pupil=' + activityNum); */
		
		
		var dataString = 'activityNum='+ activityNum + '&action=' + "getCourses";
		loadCourseData(dataString);	
	    
		
	    /* set the validattion for form */
		var validator = $("#ajaxform").validate({
			
			errorPlacement: function(error, element) {
				// Append error within linked label					
				error.css("color", "red");				
				$( element )
					.closest( "form" )
						.find( "label[for='" + element.attr( "id" ) + "']" )
							.append(  error );
			},
			rules: {   
				
				// set a rule to inputs
				// input must have name and id attr' and with same value !!!
				activityName : {  
					required: true,
					minlength: 2,
					maxlength: 20,  
					nameValidator : true //custom validation from additional-methods.js
					},
				
				lName:  {
					required: true,
					minlength: 2,
					maxlength: 20,
					nameValidator : true 
				},

				extraPrice: {
					required: "#extraPriceChk:checked",
					digits: true
				}
				
			},
			errorElement: "span",

		});
	    
	   

		
		$("#cancelBtn").click(function() { debugger;
			formDisable();
			validator.resetForm();
			setCourseData(courseData);

			return false;
		});

        
	});

function setCourseData(courseData){
			
			if(courseData != undefined){
				
				$('.page-header').html("חוג " + courseData.activityName + " ");
				
			/* pupil details import */
			$('#activityName').val(courseData.activityName);
			$('#weekDay').val(courseData.weekDay);
			$('#startTime').val(courseData.startTime);
			$('#endTime').val(courseData.endTime);
			$('#responsibleStaff').val(courseData.responsibleStaff);
			
			$('#pricePerMonth').val(courseData.pricePerMonth);				
			$('#extraPrice').val(courseData.extraPrice);	
			
			
			if(courseData.extraPrice !== null){
				$('#extraPriceChk').prop('checked', true);
				$("#extraPriceDiv").toggle(true);
				$("#extraPrice").val(courseData.extraPrice);
			}
			else{
				$('#extraPriceChk').prop('checked', false);
				$("#extraPriceDiv").toggle(false);				
			}
					
			
			
			if(courseData.regularOrPrivate && courseData.regularOrPrivate == 'רגיל'){
				$('#regularOrPrivate :nth-child(1)').prop('selected', true);
			}
			else
				$('#regularOrPrivate :nth-child(2)').prop('selected', true);	
				
			}
		}

function loadCourseData(dataString){
	setPageBtns();
	
	$("fieldset :input").prop("disabled", true);
	$("fieldset input").prop("disabled", false);
	$("fieldset :input").attr('readonly', 'readonly');
	$("fieldset :checkbox").prop("disabled", true);
	$("fieldset :radio").prop("disabled", true);
	$("#editModeBtn").hide();
	 
	$.ajax({
	  		async: false,
			type: 'GET',
			datatype: 'jsonp',
	        url: "ActivityController",
	        data: dataString,
	        success: function(data) {
	        	if(data != undefined){
	        		courseData = data.rows[0];
	        		activityNum = courseData.activityNum;
	        		
	        		setCourseData(courseData);
	        		
	        	}
	        	else
     			alert("לא קיימים נתונים");
	        },
	        error: function(e) {
	        	console.log("error");
				
	        }
	        
	      }); 

}

function deletePupil(id){
	
	
}

function saveCourseData(action,forward){
    

    var activity = new Object();	
	activity.activityNum = activityNum;
	activity.tblActivityType = { typeID : 1, }  ; //course type id
	activity.activityName = $('#activityName').val();
	activity.startTime = $('#startTime').val();
	activity.endTime = $('#endTime').val();
	activity.schoolYear = 0;
	activity.tblStaff = { staffID : $('#responsibleStaff').val(),firstName : "", lastName: "", };
	activity.tblCourse =   { 
			
			activityNum : activityNum,
			category :0,
			pricePerMonth: $('#pricePerMonth').val(),
			regularOrPrivate : $('#regularOrPrivate').val(),
			extraPrice: $('#extraPrice').val(),
	} ;
	
    var result;
     
  	  $.ajax({
  		async: false,
		type: 'POST',
		datatype: 'jsonp',
        url: "ActivityController",
        data: { action: action , activityData : JSON.stringify(activity)	        	
        	  },
        	
        success: function(data) {
        	if(data != undefined){
        		/*alert(data);*/
        		if(data.msg == "1"){
        			result = true;
        			activityNum = data.result;
        			if(action === "insert"){
        				if (typeof forward != undefined && forward) {
        					bootbox.alert("נתונים נשמרו בהצלחה, הנך מועבר למסך תלמיד", function() {
		        				// send user to the pupil page after successful insert
		        				window.location.href = "course_card_view.jsp?activityNum="+activityNum+"";
		    	        	});
						} else {
							bootbox.alert("נתונים נשמרו בהצלחה", function() {			        				
		    	        	});
						}
	        			
        			}else{
        				bootbox.alert("נתונים עודכנו בהצלחה", function() {		        				
	    	        	});
        				
        			}
        		}
        		else if(data.msg == "0"){	
        			result = false;
        			bootbox.alert("שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.", function() {	   	        		 
    	        	});
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

function setPageBtns(){
	bootbox.setDefaults({
		locale: "he"
	});
	

	$("#saveBtn").click(function() {
		var result ;
		//check for changes before saving data
		//if ($('#ajaxform').hasClass('dirty')) {
			
			// validate and process form here
			 var form = $("#ajaxform");
			 //var dateVal = $("#date_of_birth").combodate('getValue', null);
			//form.validate();
			if (form.valid()) {	
				// if(dateVal == null || dateVal == "")
						//return false;									 				
					 
				 	result = saveCourseData("update",false);			
					if(result === true){
						formDisable();
						$('.page-header').html($('#activityName').val() + " " + " " + "חוג ");
						/*$('#ajaxform').trigger('reinitialize.areYouSure');*/
					}
					
			} else {
				
			} 
			
			
		//}
		
		
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
		formEnable();
		return false;
	});
	

}

function formEnable(){
	 $("fieldset :input").prop("disabled", false); 
	 $("fieldset :input").removeAttr('readonly');
	 $("fieldset :checkbox").prop("disabled", false);
	 $("fieldset :radio").prop("disabled", false);
	 $("#viewModeBtn").hide();
	 $("#editModeBtn").show();
	
	/* $('#ajaxform').areYouSure( { message: "ישנם שינויים שלא נשמרו !"} );*/
}

function formDisable(){
	$("fieldset :input").prop("disabled", true);
	$("fieldset input").prop("disabled", false);
	$("fieldset :input").attr('readonly', 'readonly');
	$("fieldset :checkbox").prop("disabled", true);
	$("fieldset :radio").prop("disabled", true);
	$("#editModeBtn").hide();
	$("#viewModeBtn").show();
	
}	