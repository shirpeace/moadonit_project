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
 			alert("לא קיימים נתונים");
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