
/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */ 
/*************************************************/

/*   the current user the us logged if to the system */
var currentUserId =	 '<%=session.getAttribute("userid")%>';	
//$.jgrid.defaults.responsive = true;
//$.jgrid.defaults.styleUI = 'Bootstrap';
var gradeData;
var grades, FoodTypes , FamilyRelation, RegSource;
var RegDatesToValid = { startDate:null, lastDateToReg : null , numOfDaysToModify : null};
// define state for the editable page
	var state = {
	    EDIT: 0,
	    READ: 1,	    
	};
var CurrentYearEndDate = null;
var colors  = {
		future : '#ff9999',
		presernt : '#3399ff'
		
};

//set the local for bootbox pligin
bootbox.setLocale("he");

/*************************************************/
//TODO //*  START  PUPILADD PAGE FUNCTIONS       */
/*************************************************/
jQuery.fn.center = function(parent) {
	    if (parent) {
	        parent = this.parent();
	    } else {
	        parent = window;
	    }
	    this.css({
	        "position": "absolute",
	        "top": ((($(parent).height() - this.outerHeight()) / 2) + $(parent).scrollTop() + "px"),
	        "left": ((($(parent).width() - this.outerWidth()) / 2) + $(parent).scrollLeft() + "px")
	    });
	return this;
};

function getRegTypesData(){
	var returnData;
	$.ajax({
  		async: false,
		type: 'POST',
		datatype: 'json',
        url: "PupilRegistration?action=getRegTypesData",
        
        success: function(data) {
        	if(data != undefined){
        		returnData =  data;
        	}
        	else{
        		return '';
        	}
        },
        error: function(e) {
        	console.log("error loading grades");
        	return '';
			
        }
        
      });
	
	return returnData;
	
}


function getRegDatesToValid(){
	
	$.ajax({
  		async: false,
		type: 'POST',
		datatype: 'json',
        url: "PupilRegistration?action=getRegDatesToValid",
        
        success: function(data) {
        	if(data != undefined){
        		 
        		RegDatesToValid.startDate = getDateFromValue(data[0].startDate);
        		RegDatesToValid.lastDateToReg = getDateFromValue(data[0].lastDateToReg);
        		RegDatesToValid.numOfDaysToModify = data[0].numOfDaysToModify;
        		
        	}
        	else{
        		console.log("data of RegDatesToValid is undefined");
        	}
        },
        error: function(e) {
        	console.log("error loading RegDatesToValid");
        	return '';
			
        }
        
      });		
	
}



function setGradeBgColor(elem){
	
	$(elem).removeClass("form-control");
	$(elem).addClass("form-control");
	
	var option = $(elem).find('option:selected');
	if($(elem).find(":selected").val().trim() != ""){
		var bgcol = $(option).css('backgroundColor');
		$(elem).css("border-color", bgcol);
		$(elem).css("border-width", "medium");
	}
	else{
		$(elem).css("border-color", "");
		$(elem).css("border-width", "");
	}
	
} 



function setErrorStyle(value, elementID){
	
	var elem = "#" + elementID;
	if (value != null && value != "" ) {
		 
		$(elem).addClass("errorField");
			  
	}
	else{ 
		$(elem).removeClass("errorField");
	}
}



function setColorsForGrade(){
	
    var $select = $('#grade');                        
    $select.find('option').remove();   
    var gradesCopy = grades.value.split(";");
    $.each(gradesCopy, function(key, value) {  
    	value  = value.split(":");
    	if(key != 0)
    	$select.append('<option style="background-color:'+ value[2]+'" value=' + value[0] + '>' + value[1] + '</option>'); 
    	else
    		$select.append('<option  value=' + value[0] + '>' + value[1] + '</option>'); 	
    });
	
}



function setFoodTypeSelect(selectObj){
	
    var $select = selectObj;                       
    $select.find('option').remove();   
    var FoodTypesCopy = FoodTypes.value.split(";");
    $.each(FoodTypesCopy, function(key, value) {  
    	value  = value.split(":");
    	$select.append('<option  value=' + value[0] + '>' + value[1] + '</option>'); 	
    });
	
}



/**
 * fill the selectObj with the values from the valObj
 * @param selectObj - select element to fill
 * @param valObj - json object with values from db
 */
function setSelectValues(selectObj, valObj){
	
    var $select = selectObj;                       
    $select.find('option').remove();
    
    if(typeof window[valObj] == "undefined") return;
    
    var objCopy = window[valObj].value.split(";");
    $.each(objCopy, function(key, value) {  
    	value  = value.split(":");
    	if(valObj === "grades"){
    		if(key != 0)
    	    	$select.append('<option style="background-color:'+ value[2]+'" value=' + value[0] + '>' + value[1] + '</option>'); 
    	    	else
    	    		$select.append('<option  value=' + value[0] + '>' + value[1] + '</option>'); 
    	}
    	else if(valObj === "FamilyRelation"){
    		if(key == 0)
    	    	return; 
    	    else
    	    		$select.append('<option  value=' + value[0] + '>' + value[1] + '</option>'); 
    	}    	
    	else
    	$select.append('<option  value=' + value[0] + '>' + value[1] + '</option>'); 	
    });
	
}


/**
 * get data for select element
 * @param funcName - name of function to trigger in controller
 * @param objVal - javascript object name to set
 */
function getSelectValuesFromDB(funcName,objName)
{

	$.ajax({
  		async: false,
		type: 'GET',
		datatype: 'json',
        url: "FullPupilCardController?action=" + funcName,
        
        success: function(data) {
        	if(data != undefined){
        		window[objName] = data;
        		console.log("Got " + window[objName] + "from db  => "+ window[objName]);
        	}
        	else
        		console.log("no data");
        },
        error: function(e) {
        	console.log("error loading " + window[objName]);       			
        }
        
      });	
}



//getFamilyRelationJson
function getGrades()
{
	$.ajax({
  		async: false,
		type: 'GET',
		datatype: 'json',
        url: "FullPupilCardController?action=getGrades",
        
        success: function(data) {
        	if(data != undefined){
        		grades=data;
        		console.log("grades"+grades);
        	}
        	else
        		console.log("no data");
        },
        error: function(e) {
        	console.log("error loading grades");
        	
			
        }
        
      });	
}



function getFoodTypes()
{
	$.ajax({
  		async: false,
		type: 'GET',
		datatype: 'json',
        url: "FullPupilCardController?action=getFoodTypes",
        
        success: function(data) {
        	if(data != undefined){
        		FoodTypes = data;
        		console.log("FoodTypes = "+FoodTypes);
        	}
        	else
        		console.log("no data");
        },
        error: function(e) {
        	console.log("error loading FoodTypes");
        	
			
        }
        
      });	
}

/**
 * the value to convert to date , if value is a milliseconds number , create an date from it.
 * if value is string, build date from it.
 * if value is string representing a number convert it to a number and build date from it. 
 * if value is empty string return null
 * @param value
 * @returns {Date}
 */
function getDateFromValue(value){
	if (typeof value === 'string' && value.trim().length > 0 ) {
	
		if(!isNaN(value)){
			var n = parseInt(value,10);
			var d = new Date(n);
			return d;
		}
		
		var isArry = false;
		var arr , deli = ['/','-',',']; 

		for (var int = 0; int < deli.length && !isArry; int++) {
			if (value.indexOf(deli[int]) > -1) {
				arr = value.split(deli[int]);
				if (arr.length == 3) {
					isArry = true;
				}
			}
		}
		
		var d = new Date(arr[2], arr[1] - 1, arr[0]);
		return d;
	}else if(typeof value === 'number'){
		var d = new Date(value);
		return d;
	}
	else{
		return null;
	}
	
}

function formatDateInGrid(cellValue, opts, rwd) {
	if (cellValue) {
		var d = $.fn.fmatter.call(this, "date",
				getDateFromValue(cellValue), opts, rwd);
		return d;			
	} else {
		return '';
	}
}



// i think we dont use this func
/* action to update/insert pupil*/
function loadGrades()
{
	$.ajax({
  		async: false,
		type: 'GET',
		datatype: 'json',
        url: "FullPupilCardController?action=getGrades",
        
        success: function(data) {
        	if(data != undefined){
        		gradeData=data;
        		console.log("grades"+gradeData);
        	}
        	else
        		console.log("no data for gradeData");
        },
        error: function(e) {
        	console.log("error loading gradeData");

        }
        
      }); 	
}



function savePupilCardData(action,forward){
		    
	    
	 	var pupil = new Object();
	 	var family  = new Object();
	 	var regPupil  = new Object();
	 	var parent1  = new Object();
	 	var parent2  = new Object();
	 	/*var relation1 = new  Object();
	 	var relation2 = new  Object();*/
	 	debugger;
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
	    /*pupil.tblGrade = { gradeID : $('#grade').val() };*/	    
		pupil.tblGradePupils = [ {
		    	id: {pupilNum: pupil.pupilNum ,
				gradeID: $('#grade').val(), 
				yearID: null //set the year id in DB from a DB function
			  } 
		    }
	    ];
	    
	    
	    /*pupil.gradeID = $('#grade').val();*/
	    /*pupil.gender = $("input[name=genderGruop]:checked").val();*/
	    
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
	        				if (typeof forward != undefined && forward) {
	        					bootbox.alert("נתונים נשמרו בהצלחה, הנך מועבר למסך תלמיד", function() {
			        				// send user to the pupil page after successful insert
			        				window.location.href = "pupil_card_view.jsp?pupil="+pupilID+"";
			    	        	});
							} else {
								bootbox.alert("נתונים נשמרו בהצלחה", function() {			        				
			    	        	});
							}
		        			
	        			}else{
	        				bootbox.alert("נתונים עודכנו בהצלחה", function() {		        				
		    	        	});
	        				
	        				setErrorStyle($('#foodsens').val(), 'foodsens');
	        				setErrorStyle($('#comnt').val(), 'comnt');
	        				setErrorStyle($('#health').val(), 'health');
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



String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};



function getCurrentYearEndDate(){
	
	$.ajax({
		async : false,
		type : 'GET',
		datatype : 'jsonp',
		url : "ActivityController",
		data : { action : "getCurrentYearEndDate"},
		success : function(data) {
			if (data != undefined) {
				CurrentYearEndDate = data.result;

			} else
				console.log("no data");
		},
		error : function(e) {
			console.log("some error");

		}

	});
	
	return CurrentYearEndDate;
}
/*var dd = new Intl.DateTimeFormat("he-IL").format(date);	   
dd = dd.replaceAll(".", "/");*/
