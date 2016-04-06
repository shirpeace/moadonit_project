
/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/*************************************************/

/*   the current user the us logged if to the system */
var currentUserId =	 '<%=session.getAttribute("userid")%>';	

// define state for the editable page
	var state = {
	    EDIT: 0,
	    READ: 1,	    
	};

/*************************************************/
//TODO //*  START  PUPILADD PAGE FUNCTIONS       */
/*************************************************/

/* action to update/insert pupil*/

function savePupilCardData(action){
		    
	    
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
	    var gender = $("input[name=genderGruop]:checked").val();
	    pupil.tblGenderRef = {gender :  gender };
	    pupil.tblGrade = { gradeID : $('#grade').val() };
	    
	    pupil.gradeID = $('#grade').val();
	    pupil.gender = $("input[name=genderGruop]:checked").val();
	    
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
	        data: { action: action , pupilParam : JSON.stringify(pupil), 
		        	familyParam : JSON.stringify(family),
		        	regPupilParam : JSON.stringify(regPupil),
		        	parent1Param : JSON.stringify(parent1),
		        	parent2Param : JSON.stringify(parent2),
		        	gradeID : pupil.gradeID,
		        	gender : pupil.gender 
	        	  },
	        	
	        success: function(data) {
	        	if(data != undefined){
	        		/*alert(data);*/
	        		if(data.msg == "1"){
	        			pupilID = data.result;
	        			bootbox.alert("נתונים נשמרו בהצלחה, הנך מועבר למסך תלמיד", function() {
	        				// send user to the pupil page after successful insert
	        				window.location.href = "pupil_card_view.jsp?pupil="+pupilID+"";
	    	        	});
	        		}
	        		else if(data.msg == "0"){	        			
	        			bootbox.alert("שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.", function() {	   	        		 
	    	        	});
	        		}
	        	}
	        },
	        error: function(e) {
	        	console.log(e);
			        	bootbox.alert("שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.", function() {	   	        		 
			        	});			
	        }
	        
	      }); 
	  	 
	  	 
	    
	
}

	
