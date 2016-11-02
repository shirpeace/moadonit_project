var grades; 
var selectedGrade;
jQuery(document).ready(function() {	
	setUserAccess();
});

function setUserAccess(){
	if(typeof userData != "undefined" && typeof userData.userTypeID != "undefined" && userData.userTypeID != 1){
		$("#divSys").hide();
	}
	
}
