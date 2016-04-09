
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
	 	
	 	if(typeof pupilData != undefined && pupilData != "" && pupilData != null)
	 		pupil.pupilNum = pupilData.pupilNum;
	 	else
	 		pupil.pupilNum = 0;	
	    
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
	    if(typeof pupilData != undefined && pupilData != "" && pupilData != null)
	    	family.familyID = pupilData.familyID;
	    else
	    	family.familyID = null;	    
	    
	    family.homeAddress = $('#address').val();
	    family.homePhoneNum = $('#phone').val();
	    family.parentID1 = null;
	    family.parentID2 = null;
	    	
	    if(typeof pupilData != undefined && pupilData != "" && pupilData != null)
	    	regPupil.pupilNum = pupilData.regPupilNum;
		else
			regPupil.pupilNum = 0;	
	    	  
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
	    if(typeof pupilData != undefined && pupilData != "" && pupilData != null)
	    	parent1.parentID = pupilData.parent1ID;
	    else
	    	parent1.parentID = null;
	    
	    parent1.firstName = $('#p1fName').val();
	    parent1.lastName = $('#p1lName').val();
	    parent1.cellphone = $('#p1cell').val();
	    parent1.parentEmail = $('#p1mail').val();
	    parent1.relationToPupil = $('#p1relat').val();	
	    parent1.tblFamilyRelation = {idFamilyRelation: $('#p1relat').val() };
	    
	    if(typeof pupilData != undefined && pupilData != "" && pupilData != null)
	    	parent2.parentID = pupilData.parent2ID;
	    else
	    	parent2.parentID = null;
	    
	    parent2.firstName = $('#p2fName').val();
	    parent2.lastName = $('#p2lName').val();
	    parent2.cellphone = $('#p2cell').val();
	    parent2.parentEmail = $('#p2mail').val();
	    parent2.relationToPupil = $('#p2relat').val();
	    parent2.tblFamilyRelation = { 'idFamilyRelation': $('#p2relat').val() };
	    
	    var result;
	     
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
	        			result = true;
	        			pupilID = data.result;
	        			if(action === "insert"){
		        			bootbox.alert("נתונים נשמרו בהצלחה, הנך מועבר למסך תלמיד", function() {
		        				// send user to the pupil page after successful insert
		        				window.location.href = "pupil_card_view.jsp?pupil="+pupilID+"";
		    	        	});
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

/*
* FormChanges(string FormID | DOMelement FormNode)
 * Returns an array of changed form elements.
 * An empty array indicates no changes have been made.
 * NULL indicates that the form does not exist.
 * 
 * ***********************
 * not in use
 * ***********************
 * */
function FormChanges(form) {

	// get form
	if (typeof form == "string") form = document.getElementById(form);
	if (!form || !form.nodeName || form.nodeName.toLowerCase() != "form") return null;
	
	// find changed elements
	var changed = [], n, c, def, o, ol, opt;
	for (var e = 0, el = form.elements.length; e < el; e++) {
		n = form.elements[e];
		c = false;
		
		switch (n.nodeName.toLowerCase()) {
		
			// select boxes
			case "select":
				def = 0;
				for (o = 0, ol = n.options.length; o < ol; o++) {
					opt = n.options[o];
					c = c || (opt.selected != opt.defaultSelected);
					if (opt.defaultSelected) def = o;
				}
				if (c && !n.multiple) c = (def != n.selectedIndex);
				break;
			
			// input / textarea
			case "textarea":
			case "input":
				
				switch (n.type.toLowerCase()) {
					case "checkbox":
					case "radio":
						// checkbox / radio
						c = (n.checked != n.defaultChecked);
						break;
					default:
						// standard values
						c = (n.value != n.defaultValue);
						break;				
				}
				break;
		}
		
		if (c) changed.push(n);
	}
	
	return changed;

}	
