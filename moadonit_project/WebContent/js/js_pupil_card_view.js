
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

function setPupilCardData(pupil){
			
			if(pupil != undefined){
				var d;
				$('.page-header').html(pupil.firstName + " " + pupil.lastName);
				if(pupil.birthDate != null){
					
					d = new Date(pupil.birthDate);
					$('#date_of_birth').combodate('setValue', d);
				}
								
				
			/* pupil details import */
			$('#fName').val(pupil.firstName);
			$('#lName').val(pupil.lastName);
			$('#cell').val(pupil.cellphone);
			
			$('#grade').val(pupil.gradeID);	
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
			    deletePupil(id);                            
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