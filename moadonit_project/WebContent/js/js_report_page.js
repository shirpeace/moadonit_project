var selectedTab;

jQuery(document).ready(function() {	
	selectedTab = "OnTimeReg";
	$('#ulTabs').on('click', 'a', function(e) {
	    //e.preventDefault();
		selectedTab = this.parentElement.id; 
		
			switch (this.parentElement.id) {
			    case "OnTimeReg":
			    	
			        break;
			    case "MoadonitReg":
			    	
			        break;
			    case "CourseReg":
			    	
			        break;
			    case "MoadonitData":
			    	
			        break;
			    case "CourseData":
			    	 getCourseIds();
			        break;	   
			}
		
	});
	
});

function OnBntExportClick(type){
	
	if(selectedTab){
		switch (selectedTab) {
		    case "OnTimeReg":
		    	exportDataOntime(type);
		        break;
		    case "MoadonitReg":
		    	console.log(selectedTab);
		        break;
		    case "CourseReg":
		    	console.log(selectedTab);
		        break;
		    case "MoadonitData":
		    	console.log(selectedTab);
		        break;
		    case "CourseData":
		    	console.log(selectedTab);
		    	exportCourseData(type);
		        break;	   
		}
	}
}

function exportDataOntime(type){

	var month = $('#monthNum').val(), year =$('#yearNum').val();
	var params = {  fileType : type, fileName: 'exportFile' , action: "export" , pageName : "OneTimeReport", month : month, year : year };
	
	exportData(type, params); 
} 

function exportCourseData(type){

	var month = $('#monthNum').val(), year =$('#yearNum').val();
	var arr = getSelectedOptions();
	var params = {  fileType : type, fileName: 'exportFile' , action: "export" , pageName : "CourseData", options : arr, year : year };	
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
	
	$.ajax({
		async : false,
		type : 'POST',
		datatype : 'jsonp',
		url : "ReportsController",
		data : { action : "getCourseForReport" },
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
