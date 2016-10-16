var selectedTab;

jQuery(document).ready(function() {	
	selectedTab = "OnTimeReg";
	$('#ulTabs').on('click', 'a', function(e) {
	    //e.preventDefault();
		selectedTab = this.parentElement.id; 
		
			switch (this.parentElement.id) {
			    case "OnTimeReg":
			    	$('#monthPick').val("");
			        break;
			    case "MoadonitReg":
			    	$('#monthPick').val("");
			        break;
			    case "CourseReg":
			    	$('#monthPick').val("");
			        break;
			    case "MoadonitData":
			    	
			        break;
			    case "CourseData":
			    	 getCourseIds();
			        break;	   
			}
		
	});
	
	 $('#monthPick').datepicker({
		    format: "dd/mm/yyyy",
		    language: "he" ,
		     startDate: '01/01/2000',
		    maxViewMode: 1,
		    minViewMode: 1,
		    todayBtn: false,
		    keyboardNavigation: false,
		    daysOfWeekDisabled: "1,2,3,4,5,6",
		    todayHighlight: true,
		    toggleActive: true,
		    autoclose: true
		   
		}); 
	 
	 $('#dayPick').datepicker({
		    format: "dd/mm/yyyy",
		    language: "he" ,
		     startDate: '01/01/2000',
		    maxViewMode: 0,
		    minViewMode: 0,
		    todayBtn: false,
		    keyboardNavigation: false,
		    daysOfWeekDisabled: "5,6",
		    todayHighlight: true,
		    toggleActive: true,
		    autoclose: true
		   
		}); 
	    /* set the validation for forms */
		var validator1 = $("#rep1form").validate({
			
			errorPlacement: function(error, element) {				
				error.css("color", "red");				
				$( element )
					.closest( "form" )
						.find( "label[for='" + element.attr( "id" ) + "']" )
							.append(  error );
			},
			rules: {   
				monthPick : {  
					required: true,
					},
			},
			errorElement: "span",

		});
		var validator2 = $("#rep2form").validate({
			
			errorPlacement: function(error, element) {
				// Append error within linked label					
				error.css("color", "red");				
				$( element )
					.closest( "form" )
						.find( "label[for='" + element.attr( "id" ) + "']" )
							.append(  error );
			},
			rules: {   
				dayPick : {  
					required: true,
					},
			},
			errorElement: "span",

		});
		
		var validator3 = $("#rep3form").validate({
			
			errorPlacement: function(error, element) {
				// Append error within linked label					
				error.css("color", "red");				
				$( element )
					.closest( "form" )
						.find( "label[for='" + element.attr( "id" ) + "']" )
							.append(  error );
			},
			rules: {   
				yearNum : {  
					required: true,
					
					},
				courseList : {  
					required: true,
					
					},
			},
			errorElement: "span",

		});
	
});

function OnBntExportClick(type){
	if(selectedTab){
		
			switch (selectedTab) {
			    case "OnTimeReg":
			    	if ($("#rep1form").valid()) 
			    		exportDataOntime(type, "OneTimeReport");
			        break;
			    case "MoadonitReg":
			    	if ($("#rep1form").valid()) 
			    		exportMoadonitPay(type, "MoadonitPay");
			        break;
			    case "CourseReg":
			    	console.log(selectedTab);
			        break;
			    case "MoadonitData":
			    	if ($("#rep2form").valid()) 
			    		exportMoadonitData(type, "MoadonitDataReport");
			        break;
			    case "CourseData":
			    	if ($("#rep3form").valid()) 
			    		exportCourseData(type, "CourseRegistrationReport");
			        break;	   
			}
		
	}
}

function exportMoadonitPay(type, fileName){
	
	var month =  $('#monthPick').val();
	var monthTime =  getDateFromValue(month).getTime();
	var params = {  fileType : type, fileName: fileName , action: "export" , pageName : "MoadonitPay", month : monthTime };
	
	exportData(type, params);
	
}

function exportMoadonitData(type, fileName){
	var month =  $('#dayPick').val();
	var monthTime =  getDateFromValue(month).getTime();
	var params = {  fileType : type, fileName: fileName , action: "export" , pageName : "MoadonitData", month : monthTime };
	
	exportData(type, params);
	
}

function exportDataOntime(type, fileName){

	var month =  $('#monthPick').val();
	var monthTime =  getDateFromValue(month).getTime();
	// var month = $('#monthNum').val(), year =$('#yearNum').val();
	var params = {  fileType : type, fileName: fileName , action: "export" , pageName : "OneTimeReport", month : monthTime/*, year : year*/ };
	
	exportData(type, params); 
} 

function exportCourseData(type, fileName){

	var month = $('#monthNum').val(), year =$('#yearNum').val();
	var arr = getSelectedOptions();
	var params = {  fileType : type, fileName: fileName , action: "export" , pageName : "CourseData", options : arr, year : year };	
	exportData(type, params); 
} 

function exportData(type, params){

	
    var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });
    
   $.fileDownload("ReportsController", {
        successCallback: function(url) {

            $preparingFileModal.dialog('close');
        },
        failCallback: function(responseHtml, url) {

            $preparingFileModal.dialog('close');
            $("#error-modal").dialog({ modal: true });
        },
        data : params,
        httpMethod: "POST",
        popupWindowTitle: "ייצוא קובץ...",
    });

    return false; 
} 

function getSelectedOptions(){
	var array = [], idx = 0;
	var val = "";
    $('#courseList option:selected').each(function() {
    	/*var activity = new Object();
    	activity.activityNum = $(this).val();
    	activity.activityName = $(this).text();
    	array[idx++] = activity;*/

    	val += $(this).val() + ";" + $(this).text() + ",";
    });
    
    return val.substring(val.lastIndexOf(",", 0));
}


function getCourseIds(){
	var year =$('#yearNum').val();
	$.ajax({
		async : false,
		type : 'POST',
		datatype : 'jsonp',
		url : "ReportsController",
		data : { action : "getCourseForReport" , year : year},
		success : function(data) {
			if (data != undefined) {
				var values = [];
				if(data)
				for (var int = 0; int < data.length; int++) { // iterate the keys of the object that represent the option data and get the key and value
	          	    var item = {};
	            	item.activityNum = data[int].activityNum;
	            	item.activityName = data[int].activityName;
	            	values.push(item);
	            	
		        }  
				
				//sort options alphabetically
				values.sort(function(o1, o2) { return o1.activityName > o2.activityName ? 1 : o1.activityName < o2.activityName ? -1 : 0; });
				$('#courseList').find('option').remove();
				for (var i = 0; i < values.length; i++) {
					$('#courseList').append($("<option></option>")
		                    .attr("value",values[i].activityNum)
		                    .text(values[i].activityName)); 
				}
				
				//$('#courseList').selectpicker('val', [ "test1", "test2"]);
				$('.selectpicker').selectpicker('refresh');
			} else
				alert("לא קיימים נתונים");
		},
		error : function(e) {
			alert("שגיאה בשליפת נתונים");
			console.log("error");

		}

	});
}
