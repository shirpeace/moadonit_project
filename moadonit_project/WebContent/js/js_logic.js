
/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/*************************************************/

/*   the current user the us logged if to the system */
var currentUserId =	 '<%=session.getAttribute("userid")%>';	



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

function savePupilCardData(){
	
	 	var pupil = new Object();
	 	var family  = new Object();
	 	var regPupil  = new Object();
	 	var parent1  = new Object();
	 	var parent2  = new Object();
	 	/*var relation1 = new  Object();
	 	var relation2 = new  Object();*/
	 	
	    pupil.pupilNum = pupil.pupilNum == 0 ? 0 : pupil.pupilNum;	    
	    pupil.firstName = $('#fName').val();
	    pupil.lastName = $('#lName').val();
	    pupil.cellphone = $('#cell').val();
	    pupil.photoPath = '';
	    pupil.birthDate = $('#date_of_birth').combodate('getValue', null);
	    pupil.familyID = null;	   
	    pupil.tblGenderRef = {gender :  $("input[name=genderGruop]:checked").val() };
	    pupil.tblGrade = { gradeID : $('#grade').val() };
	    
	    /*pupil.gradeID = $('#grade').val();
	    pupil.gender = $("input[name=genderGruop]:checked").val();*/
	    
	    /* family data */	    
	    family.familyID = null;
	    family.homeAddress = $('#address').val();
	    family.homePhoneNum = $('#phone').val();
	    family.parentID1 = null;
	    family.parentID2 = null;
	    	    
	    regPupil.pupilNum = pupil.pupilNum == 0 ? 0 : pupil.pupilNum;	
	    regPupil.healthProblems = $('#health').val();
	    regPupil.ethiopian = $('#ethi').is(":checked") ? 1 : 0;
	    
	    if($('#staff').is(":checked")){
	    	regPupil.staffChild = $('#staffJob').val();
	    }else{
	    	regPupil.staffChild = null;
	    }	
	    
	    regPupil.foodSensitivity = $('#foodsens').val();
	    regPupil.otherComments = $('#comnt').val();	    
	    regPupil.tblFoodType = { foodTypeID: $('#food').find('option:selected').val() };
	    /* regPupil.foodType = $('#food').find('option:selected').val(); */
	    
	    /* parents data  */
	    parent1.parentID = null;
	    parent1.firstName = $('#p1fName').val();
	    parent1.lastName = $('#p1lName').val();
	    parent1.cellphone = $('#p1cell').val();
	    parent1.parentEmail = $('#p1mail').val();
	    parent1.relationToPupil = $('#p1relat').val();	
	    parent1.tblFamilyRelation = {idFamilyRelation: $('#p1relat').val() };
	    
	    parent2.parentID = null;
	    parent2.firstName = $('#p2fName').val();
	    parent2.lastName = $('#p2lName').val();
	    parent2.cellphone = $('#p2cell').val();
	    parent2.parentEmail = $('#p2mail').val();
	    parent2.relationToPupil = $('#p2relat').val();
	    parent2.tblFamilyRelation = { 'idFamilyRelation': $('#p2relat').val() };
	    
	   	    	  
		   
	  	
	  	  $.ajax({
	  		async: false,
			type: 'POST',
			datatype: 'jsonp',
	        url: "FullPupilCardController",
	        data: { action:"update", pupilParam : JSON.stringify(pupil), 
		        	familyParam : JSON.stringify(family),
		        	regPupilParam : JSON.stringify(regPupil),
		        	parent1Param : JSON.stringify(parent1),
		        	parent2Param : JSON.stringify(parent2),
		        	gradeID : pupil.gradeID,
		        	gender : pupil.gender 
	        	  },
	        	
	        success: function(data) {
	        	if(data != undefined){
	        		alert(data);
	        		
	        	
	        	}
	        },
	        error: function(e) {
	        	console.log("error");
				
	        }
	        
	      }); 
	  	 
	  	 
	    
	
}

function loadPupilCard(dataString){
	
/*	$("fieldset input").prop("disabled", false);
	$("fieldset :input").attr('readonly', 'readonly');
	$("#date_of_birth").attr("disabled", true);
	$("fieldset :checkbox").prop("disabled", true);
	$("fieldset :radio").prop("disabled", true);
	$("#editModeBtn").hide();*/
	 setPageBtns();
	
	 $("fieldset :input").prop("disabled", true);
	 $("fieldset input").prop("disabled", false);
	 $("fieldset :input").attr('readonly', 'readonly');
	 $("fieldset :checkbox").prop("disabled", true);
	 $("fieldset :radio").prop("disabled", true);
/*	 $("#date_of_birth").selectmenu( "disable" );
*/	 $("#editModeBtn").hide();
	 
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

function setPageBtns(){
	bootbox.setDefaults({
		locale: "he"
	});
	

	$("#deleteBtn").click(function() {
		bootbox.confirm("׳”׳�׳� ׳�׳×׳” ׳¨׳•׳¦׳” ׳�׳�׳—׳•׳§?", function(result) {
			if (result === true) {                                             
			    alert(" delete");                              
			  } else {
			    alert("don't");                          
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
	
	$("#saveBtn").click(function() {
		var newData = pupilData; newData.firstName = "׳©׳™׳¨׳”"; //false edit - delete!!!
		//try saving to DB
		var result = false; //false value - delete!!!
		//if success
		if(result === true){
			bootbox.alert("׳”׳©׳™׳ ׳•׳™׳™׳� ׳ ׳©׳�׳¨׳•.", function() {});
			pupilData = newData;
			setPupilCardData(newData);
			formDisable();
		}
		//if error
		else{
			bootbox.alert("׳”׳™׳×׳” ׳‘׳¢׳™׳” ׳‘׳©׳�׳™׳¨׳”. ׳ ׳¡׳” ׳©׳•׳‘.", function() {});
		}
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
	
}	/* $("fieldset :input").prop("disabled", true);
$("fieldset input").prop("disabled", false);
$("fieldset :input").attr('readonly', 'readonly');
$("fieldset :checkbox").prop("disabled", true);
$("fieldset :radio").prop("disabled", true);
$("#editModeBtn").hide();*/
