function loadPupilOneAct(dataString) {
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
        		$('.page-header').html(pupilData.firstName + " " + pupilData.lastName);
        		
        	}
        	else
        		console.log("לא קיימים נתונים");
        },
        error: function(e) {
        	console.log("error");
			
        }
        
      }); 
	  }

function actionChanged(){
	if($("#action").val()==1){
		$("#typeDiv").show();
	}
	else
		$("#typeDiv").hide();
}


$("#saveBtn").click(function() {
	if($("#datepicker").val()!=null){
		var msg = "התלמיד "+pupilData.firstName + " " + pupilData.lastName;
		if($("#action").val()===1){
			msg+= "ירשם ל" + $("#type").val()===1?"מועדונית ":"אוכל בלבד ";
			
		}
		else
			msg+= " יוסר מפעילות";
			
		msg+= "בתאריך "+ $("#datepicker").val();
		bootbox.confirm(msg, function(result) {
			if (result === true)                                             
			    saveOneTimeAct(id);                            
			   
		});
			
		}
//	else
//		tell the user to enter date
		
	
	return false;
});