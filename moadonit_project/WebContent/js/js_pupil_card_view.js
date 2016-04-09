
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
				var d = new Date(pupil.birthDate);
			/* pupil details import */
			$('#fName').val(pupil.firstName);
			$('#lName').val(pupil.lastName);
			$('#cell').val(pupil.cellphone);
			$('#date_of_birth').combodate('setValue', d);
			$('#grade').val(pupil.gradeID);	
			$("input[name=genderGruop][value=" + pupil.gender + "]").prop('checked', true);	
			$('#food').val(pupil.foodType);	
			if(pupil.ethiopian===1){
				$('#ethi').prop('checked', true);
			}
			if(pupil.staffChild !== null){
				$('#staff').prop('checked', true);
			}
			$('#health').val(pupil.healthProblems);
			$('#foodsens').val(pupil.foodSensitivity);
			$('#comnt').val(pupil.otherComments);
			
			/* parents details import */			
			$('#p1fName').val(pupil.p1fname);
			$('#p1lName').val(pupil.p1lname);
			$('#p1cell').val(pupil.p1cell);
			$('#p1mail').val(pupil.p1mail);
			$('#p1relat').val(pupil.p1relation);	
			
			$('#p2fName').val(pupil.p2fname);
			$('#p2lName').val(pupil.p2lname);
			$('#p2cell').val(pupil.p2cell);
			$('#p2mail').val(pupil.p2mail);
			$('#p2relat').val(pupil.p2relation);	
			
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
     			alert("no data");
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
	

	
	$("#cancelBtn").click(function() {
		formDisable();
		setPupilCardData(pupilData);
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