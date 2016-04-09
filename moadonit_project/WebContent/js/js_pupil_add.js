function loadPupilSearch() {
	$("#testBtn").click(function() {
	    var dataString = 'id='+ 1 + '&action=' + "get";
	    $.ajax({
	  		async: false,
			type: 'GET',
			datatype: 'jsonp',
	        url: "FullPupilCardController",
	        data: dataString,
	        success: function(data) {
	        	if(data != undefined){
	        		pupilID = data.pupilNum;
	        		
	        		alert(pupilID); 
	        		/* setPupilCardData(data); */
	        		
	        	}
	        	else
        			alert("no data");
	        },
	        error: function(e) {
	        	console.log("error");
				
	        }
	        
	      }); 
	      
	  	 return false;
	     
	    });
	 	
	 	
////////////////////////	 	
/*	 	$("#testgetlist").click(function() {
	 	var dataString = '&action=' + "getAll";
	 	$.ajax({
	  		async: false,
			type: 'GET',
			datatype: 'jsonp',
	        url: "FullPupilCardController",
	        data: dataString,
	        success: function(data) {
	        	if(data != undefined){
	        		alert(data);
	        		
	        	}
	        	else
     			alert("no data");
	        },
	        error: function(e) {
	        	console.log("error");
				
	        }
	        
	      }); 
	 	return false;
	     
	    });*/
/////////////////////////
	 	
	 	$("#testJSP").click(function() {
	 		window.location.href = "pupil_card_view.jsp?pupil="+pupilID+"";
	 	});
	  }
	 	