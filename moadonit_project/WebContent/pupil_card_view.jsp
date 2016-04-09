<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="windows-1255"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">

<head>

   <!--  <meta charset="utf-8"> -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>מועדונית</title>
	
     <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
	
	<script src="js/jquery-ui.js"></script>

    
    
	<script src="js/moment-with-locales.js"></script> 
	<script src="js/combodate.js"></script> 	   
    
    
	<script src="js/js_logic.js"></script>
    
    <script src="js/js_pupil_card_view.js"></script>
    
    	<!-- form validation plugin -->
	<script src="js/jquery.validate.js"></script>
	<script src="js/additional-methods.js"></script>
	<script src="js/messages_he.js"></script>
	
		   <!-- bootbox code -->
    <script src="js/bootbox.js"></script> 
    
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Core CSS RTL-->
    <link href="css/bootstrap-rtl.min.css" rel="stylesheet">


    
    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="css/sb-admin-rtl.css" rel="stylesheet">
    <link href="css/mycss.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
   <!--  <link href="css/plugins/morris.css" rel="stylesheet"> -->

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    

	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="nav navbar-right top-nav" style="padding-top: 15px; ">
            	<a href="index.html">
	            	<i class="fa fa-fw fa-power-off"></i>&nbsp;יציאה</a>
            </div>
            <div class="navbar-header" >
            	<a class="navbar-brand" href="dashboard.jsp">
            	<i class="fa fa-home fa-fw"></i>&nbsp;מועדונית</a>
                
            </div>
            
             <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                   <li>
                        <a href="" style="font-size: 120%; pointer-events: none;"> <i class="fa fa-fw fa-user"></i> כרטיס תלמיד</a>
                        <br>
                     </li> 
                     <li class="active">
                        <a href= "pupil_card_view.jsp"><i class="fa fa-fw fa-list-alt"></i> פרטים אישיים</a>
                     </li> 
                     <li>
                        <a href= "pupil_week_view.jsp"><i class="fa fa-fw fa-th"></i> תכנית שבועית</a>
                     </li>
                     <li>
                        <a href= "pupil_week_view.jsp"><i class="fa fa-fw fa-edit"></i> עריכת רישום</a>
                     </li>  
                </ul>
            </div>
            <!-- /.navbar-collapse -->
            
        </nav>
        
        <!-- Main content -->
        <div id="page-wrapper">
			

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h2 class="page-header" style="margin: 0px 0 0px; border-bottom: 1px solid #a6b7bd">
                            יוסי משה
                        </h2>
                        <ol class="breadcrumb">
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-home"></i> ראשי</a>
                            </li>
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-users"></i> תלמידים</a>
                            </li>
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-user"></i> כרטיס תלמיד</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-list-alt"></i> פרטים אישיים
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

     <!--         copy content from pupil_add change buttons text & fonctionality -->
           		<form role="form" id="ajaxform">
           		<fieldset >
		<!-- row 1 col 1 -->
					<div class="row" style="border-bottom: 1px solid #a6b7bd">
						<div class="col-lg-12">
							<div class="col-lg-3">
								<div class="form-group">
									<label for="fName">שם פרטי</label> <input type="text"
										class="form-control input-sm" id="fName" placeholder="שם">
								</div>
								<div class="form-group">
									<label for="lName">שם משפחה</label> <input type="text"
										class="form-control input-sm" id="lName" placeholder="משפחה">
								</div>
								<div class="form-group">
									<label for="cell">טלפון נייד תלמיד</label> <input type="text"
										class="form-control input-sm" id="cell" placeholder="טלפון">
								</div> 
							</div>
		<!-- row 1 col 2 -->
							<div class="col-lg-3">
								<div class="form-group">
									<label for="date_of_birth">תאריך לידה</label>
									<br>	
									<input id="date_of_birth" value="01-01-2003"  data-smartDays="true" data-firstItem="name"
										data-format="DD-MM-YYYY" data-template="D MMM YYYY" > 
								</div>
								<div class="form-group">
									
									<label for="grade">כיתה</label>
									<select class="form-control input-sm"
										id="grade">
										<option value="11">א-א</option>
										<option value="12">א-ב</option>
										<option value="13">א-ג</option>
										<option value="21">ב-א</option>
										<option value="22">ב-ב</option>
										<option value="23">ב-ג</option>
										<option value="31">ג-א</option>
										<option value="32">ג-ב</option>
										<option value="33">ג-ג</option>
										<option value="41">ד-א</option>
										<option value="42">ד-ב</option>
										<option value="43">ד-ג</option>
										<option value="51">ה-א</option>
										<option value="52">ה-ב</option>
										<option value="53">ב-ג</option>
										<option value="61">ו-א</option>
										<option value="62">ו-ב</option>
										<option value="63">ו-ג</option>
									</select>
								</div>
								<div class="form-group">
									<div class="checkbox-group required">
										<label for="cell" class="col-lg-1">מגדר</label>
										<label class="radio-inline col-lg-2"> 
										<input type="radio" name="genderGruop" id="male" value="1"> בן </label>
										<label class="radio-inline col-lg-2">
										<input type="radio" name="genderGruop" id="female" value="2">בת</label>
									</div>
								</div>
							</div>
		<!-- row 1 col 3 -->
							<div class="col-lg-3">
								<div class="form-group">
									<label for="food">סוג מנה</label> <select class="form-control input-sm"
										id="food">
										<option value="1">רגילה</option>
										<option value="2">אפויה</option>
										<option value="3">צמחונית</option>
										<option value="4">ללא-גלוטן</option>
									</select>
								</div>
								<div class="checkbox ">
									<label for="ethi">אתיופי</label> <input type="checkbox"
										id="ethi">
								</div>
								<div class="checkbox">
									<label for="staff">ילד סגל</label> <input type="checkbox"
										id="staff">
								</div>
							</div>
							
		<!-- row 1 col 4 -->							
							<div class="col-lg-3">
								<div class="form-group">
									<label for="health">בעיות בריאות</label> 
									 <input type="text"
										class="form-control input-sm" id="health" placeholder=""> 
										<!-- <textarea rows="2" class="form-control input-sm" id="health" ></textarea> -->
								</div>
								<div class="form-group">
									<label for="foodsens">רגישות למזון</label> <input type="text"
										class="form-control input-sm" id="foodsens" placeholder="">
								</div>
								<div class="form-group">
									<label for="comnt">הערות כלליות</label> <input type="text"
										class="form-control input-sm" id="comnt" placeholder="">
								</div>
							</div>
						</div>
					</div>
					
					
					<!-- row 2 col 1 -->
					<div class="row" style="position: relative; ">
						<div class="col-lg-12">	
						<h3>פרטי התקשרות</h3>					
							<div class="col-lg-3">
								
								<div class="form-group">
									<label for="p1fName">שם ההורה</label> <input type="text"
										class="form-control input-sm" id="p1fName" placeholder="שם">
								</div>
								<div class="form-group">
									<label for="p1lName">שם משפחה</label> <input type="text"
										class="form-control input-sm" id="p1lName" placeholder="משפחה">
								</div>
								<div class="form-group">
									<label for="p1cell">טלפון נייד</label> <input type="text"
										class="form-control input-sm" id="p1cell" placeholder="טלפון">
								</div>
								<div class="form-group">
									<label for="p1mail">אימייל</label> <input type="text"
										class="form-control input-sm" id="p1mail" placeholder="אימייל">
								</div>
								<div class="form-group">
									<label for="p1relat">קרבה</label>
									<select class="form-control input-sm"
										id="p1relat">
										<option value="1">אמא</option>
										<option value="2">אבא</option>
										<option value="3">אח</option>
										<option value="4">אחות</option>
										<option value="5">אחר</option>
									</select>
								</div>
							</div>
			<!-- row 2 col 2 -->
							<div class="col-lg-3">
								
								<div class="form-group">
									<label for="p2fName">שם ההורה</label> <input type="text"
										class="form-control input-sm" id="p2fName" placeholder="שם">
								</div>
								<div class="form-group">
									<label for="p2lName">שם משפחה</label> <input type="text"
										class="form-control input-sm" id="p2lName" placeholder="משפחה">
								</div>
								<div class="form-group">
									<label for="p2cell">טלפון נייד</label> <input type="text"
										class="form-control input-sm" id="p2cell" placeholder="טלפון">
								</div>
								<div class="form-group">
									<label for="p2mail">אימייל</label> <input type="text"
										class="form-control input-sm" id="p2mail" placeholder="אימייל">
								</div>
								<div class="form-group">
									<label for="p2relat">קרבה</label>
									<select class="form-control input-sm"
										id="p2relat">
										<option value="1">אמא</option>
										<option value="2">אבא</option>
										<option value="3">אח</option>
										<option value="4">אחות</option>
										<option value="5">אחר</option>
									</select>
								</div>
							</div>

			<!-- row 2 col 3 -->
							<div class="col-lg-3">
								
								<div class="form-group">
									<label for="address">כתובת מגורים</label> <input type="text"
										class="form-control input-sm" id="address" placeholder="כתובת">
								</div>
								<div class="form-group">
									<label for="phone">טלפון בבית</label> <input type="text"
										class="form-control input-sm" id="phone" placeholder="טלפון">
								</div>
							</div>
							
			<!-- row 2 col 4 -->				
							<div class="col-lg-3" style="position: absolute;  bottom: 0; left: 0">
								
								<div class="form-group pull-left" id="viewModeBtn">
									<input type="button" id="editBtn" name="editBtn"
									class="btn btn-primary" value="עריכה">
								
									<input type="submit" id="deleteBtn" name="deleteBtn"
									class="btn btn-primary" value="מחיקה">
								
									
									<input type="submit" id="addPupil" name="clearBtn"
									 class="btn btn-primary" value="הוסף חדש">
									<!-- <button id="testBtn">test</button> -->
								</div>
								
								<div class="form-group pull-left" id="editModeBtn" style="display: none">
									<input type="submit" id="saveBtn" name="saveBtn"
									class="btn btn-primary" value="שמור שינויים">
								
									<input type="submit" id="cancelBtn" name="deleteBtn"
									class="btn btn-primary" value="בטל שינויים">
								</div>
							</div>
						</div>
					</div>
					</fieldset>
				</form>
  			</div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

		<%
			if(request.getParameter("pupil") != null){
				String pupil = request.getParameter("pupil");
		%>	
	<script type="text/javascript">
		var pupilID = "<%=pupil%>";
	</script>
	<%} 
	else{%>
	
	<script type="text/javascript">
		var pupilID = "3";
	</script>
	
	<%} %>
	
	<script type="text/javascript">
	var pupilData;	
	// set the state at start to read. (state object from js_logic file)
	var currentPageState = state.READ;
	$(function(){
		 $('#date_of_birth').combodate({
			    minYear: 1975,
			    maxYear: 2016,
			    minuteStep: 10
			});  
			 moment.locale();         // he
		    var dataString = 'id='+ pupilID + '&action=' + "get";
		    loadPupilCard(dataString);	
		    
			$("#saveBtn").click(function() {
				var result ;
				//check for changes before saving data
				//if ($('#ajaxform').hasClass('dirty')) {
					
					// validate and process form here
					 var form = $("#ajaxform");
					//form.validate();
					if (form.valid()) {	
						 if($("#date_of_birth").combodate('getValue', null)== null)
							return false;						 				
							 
						 
						 	return false;
						 	result = savePupilCardData("update");			
							if(result === true){
								formDisable();
								$('#ajaxform').trigger('reinitialize.areYouSure');
							}
							
					} else {
						
					} 
					
					
				//}
				
				
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
					fName:  {
						required: true,
						minlength: 2,
						maxlength: 20
					},
					lName:  {
						required: true,
						minlength: 2,
						maxlength: 20
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
						maxlength: 20
					}
					,
					p1fName : {
						required: true,
						maxlength: 20
					},
					p1lName : {
						required: true,
						maxlength: 20
					},
					p2fName : {
						
						maxlength: 20
					},
					p2lName : {
					
						maxlength: 20
					},
					p1mail : {
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
						digits: true
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
			
		    /*$('#ajaxform').areYouSure( { message: "ישנם שינויים שלא נשמרו !"} );
		    
			// code below is optional to handle disabled state of the save button
	        $('#ajaxform').bind('dirty.areYouSure', function () {
	            // Enable save button only as the form is dirty.
	            $('#saveBtn').attr({ 'disabled': false });
	        });
	        $('#ajaxform').bind('clean.areYouSure', function () {
	            // Form is clean so nothing to save - disable the save button.
	            $('#saveBtn').attr({ 'disabled': true });
	        });*/
	        
		  /*   setPageBtns(); */
		});
	</script>

	<!-- Morris Charts JavaScript -->
   <!--  <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script> -->

</body>

</html>
