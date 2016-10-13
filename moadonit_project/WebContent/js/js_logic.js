
/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */ 
/*************************************************/

/*   the current user the us logged if to the system */
var currentUserId =	 '<%=session.getAttribute("userid")%>';	
//$.jgrid.defaults.responsive = true;
//$.jgrid.defaults.styleUI = 'Bootstrap';
var gradeData;
var grades, FoodTypes , FamilyRelation, RegSource, Staff;
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
var rowDataGlobal = [];
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

Date.prototype.changeDate = function (n) {
    var time = this.getTime();
    var changedDate  = new Date();

    changedDate = new Date(time + (n * 24 * 60 * 60 * 1000));
    
    changedDate.setTime(changedDate.getTime());
    
    return changedDate;
};

Date.prototype.ddmmyyyy = function() {
	  var mm = this.getMonth() + 1; // getMonth() is zero-based
	  var dd = this.getDate();

	  return [ dd,"/", mm,"/", this.getFullYear() ].join(''); // padding
	 
};

function isElementInViewport (el) {	
	    //special bonus for those using jQuery
	    if (typeof jQuery === "function" && el instanceof jQuery) {
	        el = el[0];
	    }

	    var rect = el.getBoundingClientRect();

	    var r = (
		        rect.top >= 0 &&
		        rect.left >= 0 &&
		        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && /*or $(window).height() */
		        rect.right <= (window.innerWidth || document.documentElement.clientWidth) /*or $(window).width() */
		    );
	    return r;
}

function isDateIn3daysSpan(date){
	var startModDate , endModDate , firstDay = new Date();
	
	if(date == null || typeof date == undefined)
		return null;
	
	startModDate = new Date();
	endModDate = new Date();	
	
	var day = date.getDate();	
	if(day <= 15){// check for this month
	    firstDay = new Date(date.getFullYear(), date.getMonth(), 1);		
	}
	else{ // check for next month
		firstDay = new Date(date.getFullYear(), date.getMonth()+1, 1);		
	}
	
	startModDate = firstDay.changeDate(-3);
	endModDate = firstDay.changeDate(3);		
	
	return (startModDate <= date && date <= endModDate);
	
}

function isDateValidToReg(dateParam, endParam, idx , btn){
		
	var isStartInRegTime = false; // is start date in registration period (start of year) 
	var isEndIn3days = false; //  is end date -+3 days from start of month
	var isStartIn3days = false;
	var html;
	
	if(RegDatesToValid.startDate == null && RegDatesToValid.lastDateToReg == null) return;

	//check for overlapping dates
	var isOverlap  = checkOverlappingDatesInGrid(dateParam, endParam, idx , btn);
	
	
	if(isOverlap) return false;
	
	//if we in registration period
	if(RegDatesToValid.startDate <= dateParam && dateParam <= RegDatesToValid.lastDateToReg)		
		isStartInRegTime = true;

	// check if endParam date is in -+3 days from month
	isEndIn3days = isDateIn3daysSpan(endParam);
	
	//if we in registration time
	if(isStartInRegTime)
		return true;
	
	//if it's not regitration time and isNonEditable == true (startDate need validation) , need to check isStartIn3days 
	if(!isStartInRegTime && typeof isNonEditable != undefined && !isNonEditable)
		isStartIn3days = isDateIn3daysSpan(dateParam);
	else
		isStartIn3days = true;
	
	//if not in registration time , check if both dates are valid - (-+ 3days valid)
	if(isStartIn3days && isEndIn3days){
		setRegMsg("",false, null , btn, false);
		return true;
	}
	else{
		 html = "<strong>שים לב</strong> התאריכים לא בתקופת השינויים.<br>" +
		 "<input type='checkbox' id='OkToReg' name='OkToReg' value='1' onchange='OkToReg(this)' >&nbsp;&nbsp;שמור רישום בכל זאת";
		 
		 
		 
		 setRegMsg(html,true, "alert alert-warning" , btn, true);
		 return false;
/*		 if(btn){ // validate for outer form
			 setRegMsg(html,true, "alert alert-warning" , btn, true);
			 return false;
		 }
		 else{ // validate for inline edit
			 if(okToSave){
				 setRegMsg("",false, null , btn, null);
				 return true;
			 }else{
				 setRegMsg(html,true, "alert alert-warning" , btn, null);
				 return false;
			 }
		 }	*/	 	
		 
	}
	
	return false;
}



function checkOverlappingDatesInGrid(startParam, endParam, idx , btn){
	
	var rowCount =  $('#listRegistration').getGridParam("reccount");
	var allRowsInGrid = $('#listRegistration').jqGrid('getGridParam','data');
	
	var overlappingDates = [];	
	
	for (var i = 0; i < rowDataGlobal.length; i++) {
		var startDate, endDate;
		
		if(i == idx) continue;
		
		if(rowDataGlobal[i] == undefined) continue;
		
		startDate = getDateFromValue(rowDataGlobal[i].startDate);
		endDate = getDateFromValue(rowDataGlobal[i].endDate);
		
		if(endDate != null && endDate != null){
			
			if ((startParam < startDate && startDate <= endParam)
			        || (startParam >= startDate && endDate >= startParam)){
				//overlapping!!!
				var val = { "startDate" : startDate , "endDate" : endDate};
				
				overlappingDates.push(val);
			}
		}
	}
	
	if(overlappingDates.length > 0){
		var html = "קיימת חפיפה עם תקופה המתחילה ב  " + overlappingDates[0].startDate.ddmmyyyy() + " ומסתיימת בתאריך " + overlappingDates[0].endDate.ddmmyyyy() ;
		if(overlappingDates.length > 1){
			html = "קיימת חפיפה עם התקופות הבאות :";
			html+= "<br>";
			for (var int = 0; int < overlappingDates.length; int++) {
				html += int+1 + ". תקופה המתחילה ב :" + overlappingDates[int].startDate.ddmmyyyy() + " ומסתיימת בתאריך " + overlappingDates[int].endDate.ddmmyyyy() ;
				html+= "<br>";
			}
		}
		
		setRegMsg(html,true, "alert alert-danger" , btn, true);
		return true;
	}
	else{
		return false;
	}
	
	
}


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
function getSelectValuesFromDB(funcName,objName,ContorlName)
{

	var url = ContorlName + "?action=" + funcName;
	$.ajax({
  		async: false,
		type: 'GET',
		datatype: 'json',
        url: url,
        
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
	    family.areDivorced = $('#divorce').is(":checked") ? 1 : 0;
	    	
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
	    
	    
	    //pupil.areDivorced
	    
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

/**
 * update or insert course details 
 * activated from js_course_card_view.js , js_course_add.js
 */
function saveCourseData(action, forward) {


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
