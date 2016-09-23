var selectedTab;

jQuery(document).ready(function() {	
	selectedTab = "OnTimeReg";
	$('#ulTabs').on('click', 'a', function(e) {
	    //e.preventDefault();
		selectedTab = this.parentElement.id;	    
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
		        break;	   
		}
	}
}

function exportDataOntime(type){

	var month = $('#monthNum').val(), year =$('#yearNum').val();
	/*var $grid = $("#" + gridId);
    var postData = $grid.jqGrid('getGridParam', 'postData');
    
    var firstName =  null ,gender = null,  isReg=    null , lastName = null , gradeName =  null;
    if(postData._search === true){
    	firstName = postData.firstName ,gender =  postData.gender, 
    	isReg=    postData.isReg , lastName =  postData.lastName ,
    	gradeName =   postData.gradeName;    	
    }*/
    debugger;
    var $preparingFileModal = $("#preparing-file-modal");
    
    $preparingFileModal.dialog({ modal: true });
    
    //addGridSearchOption($grid,'action','export');
    //$grid[0].triggerToolbar();   
    
   $.fileDownload("ReportsController", {
        successCallback: function(url) {

            $preparingFileModal.dialog('close');
        },
        failCallback: function(responseHtml, url) {

            $preparingFileModal.dialog('close');
            $("#error-modal").dialog({ modal: true });
        },
        data : {  fileType : type, fileName: 'exportFile' , action: "export" , pageName : "OneTimeReport", month : month, year : year },
        httpMethod: "POST",
        popupWindowTitle: "ייצוא קובץ...",
    });

    return false; 
} 
