 $().ready(function (){
		 
		  $('#date_of_birth').combodate({
				minYear : 1975,
				maxYear : 2016,
				minuteStep : 10
			});
			moment.locale(); // he
			
			$('.day').attr('name', 'day');
			$('.month').attr('name', 'month');
			$('.year').attr('name', 'year'); 

			$('#staff').click(function() {
				var isChecked = this.checked;
				if (isChecked) {
					$("#staffJobDiv").toggle(true);

				} else {
					$("#staffJobDiv").toggle(false);
					$("#staffJob").val('');
				}

			});
			
			getGrades();
			
			setColorsForGrade();
			//saveBtn
			$("#saveBtn").click(function() {
				
				
				// validate and process form here
				 var form = $("#ajaxform");
				//var dateVal = $("#date_of_birth").combodate('getValue', null);
				//form.validate();
				if (form.valid()) {	
					// if(dateVal == null || dateVal == "")
						//return false;
						
					 
					 savePupilCardData("insert",true);
				} else {
					
				} 

				return false;

			});

			
			$("#saveClearBtn").click(function() {
				
				var result;
				// validate and process form here
				 var form = $("#ajaxform");
				 //var dateVal = $("#date_of_birth").combodate('getValue', null);
				//form.validate();
				if (form.valid()) {	
					//if(dateVal == null || dateVal == "")
						//return false;					 				
						 
					 result =  savePupilCardData("insert",false);
					 if (result) {
						 $("#clearBtn").click();
					}
				} else {
					
				} 

				return false;

			});
			
			/* set the validattion for form */
			var validator = $("#ajaxform").validate({
				
				errorPlacement: function(error, element) {
					// Append error within linked label					
					error.css("color", "red");				
					$( element )
						.closest( "form" )
							.find( "label[for='" + element.attr( "id" ) + "']" )
								.append(  error );
				},
					rules: {   
					
					// set a rule to inputs
					// input must have name and id attr' and with same value !!!
					fName : {  
						required: true,
						minlength: 2,
						maxlength: 20,  
						nameValidator : true //custom validation from additional-methods.js
						},
					
					lName:  {
						required: true,
						minlength: 2,
						maxlength: 20,
						nameValidator : true 
					},
					 cell: {
						/* required: true, */
						minlength: 10,
						maxlength: 10,
						digits: true
						
					}, 
					staffJob: {
						required: "#staff:checked",
						minlength: 2,
						maxlength: 20,
						nameValidator : true 
					}
					,
					p1fName : {
						required: true,
						maxlength: 20,
						nameValidator : true 
					},
					p1lName : {
						required: true,
						maxlength: 20,
						nameValidator : true 
					},
					p2fName : {
						
						maxlength: 20,
						nameValidator : true 
					},
					p2lName : {
						maxlength: 20,
						nameValidator : true 
					},
					p1mail : {
						email: true,
						maxlength: 254
					},
					p2mail : {
						email: true,
						maxlength: 254
					}, 
					p2cell : {
						rangelength: [2, 10],
						digits: true
					},
					p1cell : {
						minlength: 10,
						maxlength: 10,
						digits: true
					},
					 genderGruop : {
						required: true,		
						minlength: 1
					}, 
					phone : {
						minlength: 9,
						maxlength: 9,
						digits: true
					},address : {
						
						maxlength: 45,
						
					},
					 health : { 
						 maxlength: 20 
					}, 
					foodsens : { 
					maxlength: 20
					
					}, 
					comnt : { 
						maxlength: 20
					},
					
					
				},
				errorElement: "span",
				
				/* messages: { 
					
					// set custom error msg to inputs
					lName: {
						required: " (required)",
						minlength: " (must be at least 3 characters)"
					},
					genderGruop : {
						required: " (required)",
						
						
					}
						
					
				} */
			});
			
			$("#clearBtn").click(function() {
				
				validator.resetForm();
				 //$(this).closest('form').find("input[type=text],input[type=select],input[type=email],input[type=number], textarea").val("");
				 
				$(this).closest('form')[0].reset();
				setGradeBgColor($('#grade'));
				 return false;
			});

	 });

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
	 	