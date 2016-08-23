
/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/*************************************************/

/*   the current user the us logged if to the system */
var currentUserId =	 '<%=session.getAttribute("userid")%>';	


//define state for the editable page
var state = {
    EDIT: 1,
    READ: 0,	    
};
/*************************************************/
//TODO //*  START  PUPILADD PAGE FUNCTIONS       */
/*************************************************/
$(function(){
	 $('#date_of_birth').combodate({
		    minYear: 1975,
		    maxYear: 2016,
		    minuteStep: 10
		});  
		 moment.locale();         // he
		 $('.day').attr('name', 'day');
		$('.month').attr('name', 'month');
		$('.year').attr('name', 'year'); 
			
		$('#staff').click(function() {
			var isChecked = this.checked;
			if (isChecked) {
				$("#staffJobDiv").toggle(true);

			} else {
				$("#staffJobDiv").toggle(false);
				$("#staffJob").val('');
			}

		});
		
		$('#detailsLink').attr('href','pupil_card_view.jsp?li=0&pupil=' + pupilID);
		$('#scheduleLink').attr('href','pupil_week_view.jsp?li=1&pupil=' + pupilID);
		$('#regLink').attr('href','pupil_week_view.jsp?li=2&pupil=' + pupilID);
		$('#oneTimeLink').attr('href','pupil_one_time_act.jsp?li=3&pupil=' + pupilID);
		
		getSelectValuesFromDB("getFoodTypes","FoodTypes","FullPupilCardController"); //getFoodTypes
		getSelectValuesFromDB("getGrades","grades","FullPupilCardController"); //getGrades
		getSelectValuesFromDB("getFamilyRelation","FamilyRelation","FullPupilCardController"); //getFamilyRelation
		
		setSelectValues($('#food'), "FoodTypes");
		setSelectValues($('#grade'), "grades");
		setSelectValues($('#p1relat'), "FamilyRelation");
		setSelectValues($('#p2relat'), "FamilyRelation");
		
		var dataString = 'id='+ pupilID + '&action=' + "get";
	   	loadPupilCard(dataString);	
	    
		
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
				fName : {  
					required: true,
					minlength: 2,
					maxlength: 20,  
					nameValidator : true //custom validation from additional-methods.js
					},
					food : {  
						required: true,
						
						},
				lName:  {
					required: true,
					minlength: 2,
					maxlength: 20,
					nameValidator : true 
				},
				 cell: {
					/* required: true, */
					minlength: 10,
					maxlength: 10,
					digits: true
					
				}, 
				staffJob: {
					required: "#staff:checked",
					minlength: 2,
					maxlength: 20,
					nameValidator : true 
				}
				,
				p1fName : {
					required: true,
					maxlength: 20,
					nameValidator : true 
				},
				p1lName : {
					required: true,
					maxlength: 20,
					nameValidator : true 
				},
				p2fName : {
					
					maxlength: 20,
					nameValidator : true 
				},
				p2lName : {
					maxlength: 20,
					nameValidator : true 
				},
				p1mail : {
					email: true,
					maxlength: 254
				},
				p2mail : {
					email: true,
					maxlength: 254
				}, 
				p2cell : {
					rangelength: [2, 10],
					digits: true
				},
				p1cell : {
					minlength: 10,
					maxlength: 10,
					digits: true
				},
				 genderGruop : {
					required: true,		
					minlength: 1
				}, 
				phone : {
					minlength: 9,
					maxlength: 9,
					digits: true
				},address : {
					
					maxlength: 45,
					
				},
				 health : { 
					 maxlength: 20 
				}, 
				foodsens : { 
				maxlength: 20
				
				}, 
				comnt : { 
					maxlength: 20
				},
				
				
			},
			errorElement: "span",

		});
	    
	   

		
		$("#cancelBtn").click(function() {
			formDisable();
			validator.resetForm();
			setPupilCardData(pupilData);

			return false;
		});
	    		
       
});


function setPupilCardData(pupil){
			
			if(pupil != undefined){
				var d;
				$('.page-header').html(pupil.lastName + " " + pupil.firstName);
				if(pupil.birthDate != null){
					
					d = new Date(pupil.birthDate);
					$('#date_of_birth').combodate('setValue', d);
				}
								
				
			/* pupil details import */
			$('#fName').val(pupil.firstName);
			$('#lName').val(pupil.lastName);
			$('#cell').val(pupil.cellphone);
			
			$('#grade').val(pupil.gradeID);
			setGradeBgColor($('#grade'));
			$("input[name=genderGruop][value=" + pupil.gender + "]").prop('checked', true);	
			$('#food').val(pupil.foodType);	
			if(pupil.ethiopian===1){
				$('#ethi').prop('checked', true);
			}
			else{
				$('#ethi').prop('checked', false);
			}
			
			if(pupil.staffChild !== null){
				$('#staff').prop('checked', true);
				$("#staffJobDiv").toggle(true);
				$("#staffJob").val(pupil.staffChild);
			}
			else{
				$('#staff').prop('checked', false);
				$("#staffJobDiv").toggle(false);				
			}
			
			$('#health').val(pupil.healthProblems);
			$('#foodsens').val(pupil.foodSensitivity);
			$('#comnt').val(pupil.otherComments);
			
			setErrorStyle(pupil.healthProblems,"health"); //function in js_logic
			setErrorStyle(pupil.foodSensitivity,"foodsens");
			setErrorStyle(pupil.otherComments,"comnt");
						
			/* parents details import */			
			$('#p1fName').val(pupil.p1fname);
			$('#p1lName').val(pupil.p1lname);
			$('#p1cell').val(pupil.p1cell);
			$('#p1mail').val(pupil.p1mail);
			
			if(pupil.p1relation == 0){
				$('#p1relat :nth-child(1)').prop('selected', true); 
			}
			else
				$('#p1relat :nth-child(' + pupil.p1relation + ')').prop('selected', true);		
			
			$('#p2fName').val(pupil.p2fname);
			$('#p2lName').val(pupil.p2lname);
			$('#p2cell').val(pupil.p2cell);
			$('#p2mail').val(pupil.p2mail);
			if(pupil.p2relation == 0){
				$('#p2relat :nth-child(2)').prop('selected', true);
			}
			else
				$('#p2relat :nth-child(' + pupil.p2relation + ')').prop('selected', true);	
			
			$('#address').val(pupil.homeAddress);
			$('#phone').val(pupil.homePhoneNum);
			}
		}

function loadPupilCard(dataString){
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
	        url: "FullPupilCardController",
	        data: dataString,
	        success: function(data) {
	        	if(data != undefined){
	        		pupilID = data.pupilNum;
	        		pupilData = data;
	        		setPupilCardData(pupilData);
	        		
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
	//shir says thaty its ok to change tje grade to the pupil
	// in the pupuil _grade table, but only if he has recordes in ref tables
	//else del from pupil_grade and tehn reg_pupil and then pupil
	// if he has brothers dont del family
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
					 
				 	result = savePupilCardData("update",false);			
					if(result === true){
						formDisable();
						$('.page-header').html($('#fName').val() + " " + $('#lName').val());
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
			    deletePupil(pupilID);                            
			  } 
		});
		return false;
	});
	
	$("#addPupil").click(function() {
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
	 $("#teacher").prop("disabled", true);
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