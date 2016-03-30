
function setPupilCardData(pupil){
			
			if(pupil != undefined){
				var d = new Date(pupil.birthDate);
/* pupil details import */
			$('#fName').val(pupil.firstName);
			$('#lName').val(pupil.lastName);
			$('#cell').val(pupil.cellphone);
			$('#date_of_birth').combodate('setValue', d);
			$('#grade').val(pupil.gradeID);	
			$("input[name=genderGruop][value=" + pupil.gender + "]").prop('checked', true);	
			$('#food').val(pupil.foodType);	
			if(pupil.ethiopian===1){
				$('#ethi').prop('checked', true);
			}
			if(pupil.staffChild !== null){
				$('#staff').prop('checked', true);
			}
			$('#health').val(pupil.healthProblems);
			$('#foodsens').val(pupil.foodSensitivity);
			$('#comnt').val(pupil.otherComments);
/* parents details import */			
			$('#p1fName').val(pupil.p1fname);
			$('#p1lName').val(pupil.p1lname);
			$('#p1cell').val(pupil.p1cell);
			$('#p1mail').val(pupil.p1mail);
			$('#p1relat').val(pupil.p1relation);	
			
			$('#p2fName').val(pupil.p2fname);
			$('#p2lName').val(pupil.p2lname);
			$('#p2cell').val(pupil.p2cell);
			$('#p2mail').val(pupil.p2mail);
			$('#p2relat').val(pupil.p2relation);	
			
			$('#address').val(pupil.homeAddress);
			$('#phone').val(pupil.homePhoneNum);
			}
		}