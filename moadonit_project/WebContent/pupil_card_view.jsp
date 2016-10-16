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
	
<script type="text/javascript">
		var currentUserId =	 '<%=session.getAttribute("userid")%>';	
		
	</script>
	
	<%
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	%>
	
 	<%
		if (session.getAttribute("userid") == null) {
			response.sendRedirect("login.jsp");
			return;
		}
	%> 

    <title>מועדונית</title>
	
     <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
	
	<script src="js/jquery-ui.js"></script>
		<!-- bootbox code -->
    <script src="js/bootbox.js"></script> 
	<script src="js/js_logic.js"></script>
    
    
	<script src="js/moment-with-locales.js"></script> 
	<script src="js/combodate.js"></script> 	   
    
    <script src="js/js_pupil_card_view.js"></script> 
	
    
    <script src="js/jquery.are-you-sure.js"></script> 
    
    	<!-- form validation plugin -->
	<script src="js/jquery.validate.js"></script>
	<script src="js/additional-methods.js"></script>
	<script src="js/messages_he.js"></script>
	

    
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Core CSS RTL-->
    <link href="css/bootstrap-rtl.min.css" rel="stylesheet">


    
    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="css/sb-admin-rtl.css" rel="stylesheet">
    

    <!-- Morris Charts CSS -->
   <!--  <link href="css/plugins/morris.css" rel="stylesheet"> -->

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="css/mycss.css" rel="stylesheet">

	
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
            	<a href="login.jsp?action=logout">
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
                        <a href= "pupil_card_view.jsp" id="detailsLink"><i class="fa fa-fw fa-list-alt"></i> פרטים אישיים</a>
                     </li> 
                     <li>
                        <a href= "pupil_week_view.jsp" id="scheduleLink"><i class="fa fa-fw fa-th"></i> תכנית שבועית</a>
                     </li>
                     <li>
                        <a href= "pupil_week_view.jsp" id="regLink" ><i class="fa fa-fw fa-edit"></i> עריכת רישום</a>
                     </li>  
                    <li>
                        <a href= "pupil_one_time_act.jsp" id="oneTimeLink"><i class="fa fa-fw fa-plus-square-o"></i> פעילות חד פעמית</a>
                     </li> 
                     <li>
                     	<a href="pupil_add.jsp"><i class="fa fa-fw fa-edit"></i> הוספת חדש</a>
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
                        <h2 class="page-header" id="nameTitle" style="margin: 0px 0 0px; border-bottom: 1px solid #a6b7bd">                     
                        </h2>
                        <ol class="breadcrumb">
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-home"></i> ראשי</a>
                            </li>
                            <li>
                                 <a href="pupils_search.jsp"><i class="fa fa-users"></i> תלמידים</a>
                            </li>
                            <li>
                                 <a href="#"><i class="fa fa-user"></i> כרטיס תלמיד</a>
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
									<label for="lName">שם משפחה</label> <input type="text"
										class="form-control input-sm" id="lName" name="lName"  placeholder="משפחה" >
								</div>
								<div class="form-group">
									<label for="fName">שם פרטי</label> <input type="text" 
										class="form-control input-sm" name="fName"  id="fName" placeholder="שם">
								</div>
								<div class="form-group">
									<label for="cell">טלפון נייד תלמיד</label> <input type="text"
										class="form-control input-sm" id="cell" name="cell"  placeholder="טלפון">
								</div> 
							</div>
		<!-- row 1 col 2 -->
							<div class="col-lg-3">
								<div class="form-group">
									<label for="date_of_birth">תאריך לידה</label>
									<br>	
									<input id="date_of_birth" name="date_of_birth" 
									class=".ignore" data-smartDays="true" data-firstItem="name"
										data-format="DD-MM-YYYY" data-template="DD MMM YYYY" > 
								</div>
								<div class="form-group col-lg-5">
									
									<label for="grade">כיתה</label>
									<select class="form-control input-sm"
										id="grade" name="grade" onchange="setGradeBgColor(this)">
										
									</select>
								</div>
								<div class="form-group col-lg-7">
									
									<label for="teacher">מחנכ/ת</label>
									<input type="text" name="teacher" class="form-control" id="teacher"
										placeholder="מחנכ/ת">
								<!-- 	<select class="form-control input-sm"
										id="teacher" name="teacher" onchange="" disabled>
										<option value="א">שושנה מזרחי</option>
										<option value="ב">דנית גבאי</option>
										<option value="ג">שמשון בוזגלו</option>
										<option value="ד">אהובה שלום</option>
										<option value="ה">אורלי שוורץ</option>
									</select> -->
								</div>
								<div class="form-group">
									<div class="checkbox-group required">
									<label for="genderGruop" class="col-lg-1">מגדר</label>
										<label class="radio-inline col-lg-2"> 
										<input type="radio" name="genderGruop" id="male" value="1"> בן </label>
										<label class="radio-inline col-lg-2">
										<input type="radio" name="genderGruop" id="female" value="2">בת</label>
									</div>
								</div>
							</div>
		<!-- row 1 col 3 -->
							<div class="col-lg-2">
								<div class="form-group">
									<label for="food">סוג מנה</label>
									<select class="form-control input-sm"
										id="food" name="food" >
										<!-- <option value="0">בחר</option> -->
										<!-- <option value="1">רגילה</option>
										<option value="2">אפויה</option>
										<option value="3">צמחונית</option>
										<option value="4">ללא-גלוטן</option> -->
									</select>
								</div>
								<table dir="rtl"  style="width:100%" cellpadding="3" cellspacing="5">
									<tr align="right">
										<td  style=" width:60%;"><label for="ethi">אתיופי</label></td>
										<td><input type="checkbox"
										id="ethi" name="ethi"></td>
									</tr>
									<tr align="right">
										<td><label for="divorce">הורים גרושים</label></td>
										<td><input type="checkbox"
										id="divorce" name="divorce" ></td>
									</tr>
									<tr align="right">
										<td><label for="staff">ילד סגל</label></td>
										<td><input type="checkbox"
										name="staff" id="staff"></td>
									</tr>
								</table>
								<div class="form-group" style="display: none;" id="staffJobDiv">
									<label for="staffJob">תפקיד ההורה</label> <input type="text"
										name="staffJob" class="form-control" id="staffJob"
										placeholder="תפקיד">
								</div>
							<!-- 	<div class="checkbox ">
									<label for="ethi">אתיופי</label> <input type="checkbox"
										id="ethi" name="ethi" >
								</div>
								<div class="checkbox ">
									<label for="divorce">הורים גרושים</label> <input type="checkbox"
										id="divorce" name="divorce" >
								</div>
								<div class="checkbox">
									<label for="staff">ילד סגל</label> <input type="checkbox"
										id="staff" name="staff" >
								</div>
								<div class="form-group" style="display: none;" id="staffJobDiv">
									<label for="staffJob">תפקיד ההורה</label> <input type="text"
										name="staffJob" class="form-control" id="staffJob"
										placeholder="תפקיד">
								</div> -->
							</div>
							
		<!-- row 1 col 4 -->							
							<div class="col-lg-4">
								<div class="form-group">
									<label for="health">בעיות בריאות</label> 
									 <input type="text"
										class="form-control input-sm" id="health" name="health"  placeholder=""> 
										<!-- <textarea rows="2" class="form-control input-sm" id="health" ></textarea> -->
								</div>
								<div class="form-group">
									<label for="foodsens">רגישות למזון</label> <input type="text"
										class="form-control input-sm" id="foodsens" name="foodsens" placeholder="">
								</div>
								<div class="form-group">
									<label for="comnt">הערות כלליות</label> <input type="text"
										class="form-control input-sm" id="comnt"  name="comnt" placeholder="">
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
									<label for="p1relat">קרבה</label>
									<select class="form-control input-sm"
										id="p1relat" name="p1relat" >
										<!-- <option value="1" selected="selected">אמא</option>
										<option value="2">אבא</option>
										<option value="3">אח</option>
										<option value="4">אחות</option>
										<option value="5">אחר</option> -->
									</select>
								</div>
								<div class="form-group">
									<label for="p1fName">שם ההורה</label> <input type="text"
										class="form-control input-sm" id="p1fName" name="p1fName" placeholder="שם">
								</div>
								<div class="form-group">
									<label for="p1lName">שם משפחה</label> <input type="text"
										class="form-control input-sm" id="p1lName" name="p1lName"  placeholder="משפחה">
								</div>
								<div class="form-group">
									<label for="p1cell">טלפון נייד</label> <input type="text"
										class="form-control input-sm" id="p1cell" name="p1cell"  placeholder="טלפון">
								</div>
								<div class="form-group">
									<label for="p1mail">אימייל</label> <input type="text"
										class="form-control input-sm" id="p1mail" name="p1mail"  placeholder="אימייל">
								</div>
								
							</div>
			<!-- row 2 col 2 -->
							<div class="col-lg-3">
								<div class="form-group">
									<label for="p2relat">קרבה</label>
									<select class="form-control input-sm"
										id="p2relat" name="p2relat" >
										<!-- <option value="1">אמא</option>
										<option value="2" selected="selected">אבא</option>
										<option value="3">אח</option>
										<option value="4">אחות</option>
										<option value="5">אחר</option> -->
									</select>
								</div>
								<div class="form-group">
									<label for="p2fName">שם ההורה</label> <input type="text"
										class="form-control input-sm" id="p2fName" name="p2fName" placeholder="שם">
								</div>
								<div class="form-group">
									<label for="p2lName">שם משפחה</label> <input type="text"
										class="form-control input-sm" id="p2lName" name="p2lName" placeholder="משפחה">
								</div>
								<div class="form-group">
									<label for="p2cell">טלפון נייד</label> <input type="text"
										class="form-control input-sm" id="p2cell" name="p2cell"  placeholder="טלפון">
								</div>
								<div class="form-group">
									<label for="p2mail">אימייל</label> <input type="text"
										class="form-control input-sm" id="p2mail" name="p2mail" placeholder="אימייל">
								</div>
								
							</div>

			<!-- row 2 col 3 -->
							<div class="col-lg-3">
								
								<div class="form-group">
									<label for="address">כתובת מגורים</label> <input type="text"
										class="form-control input-sm" id="address" name="address" placeholder="כתובת">
								</div>
								<div class="form-group">
									<label for="phone">טלפון בבית</label> <input type="text"
										class="form-control input-sm" id="phone" name="phone"  placeholder="טלפון">
								</div>
							</div>
							
			<!-- row 2 col 4 -->				
							<div class="col-lg-9"></div>
							<div class="col-lg-3" >
								
								<div class="form-group " id="viewModeBtn">
									<input type="button" id="editBtn" name="editBtn"
									class="btn btn-primary" value="עריכה">
								
									<input type="button" id="deleteBtn" name="deleteBtn"
									class="btn btn-primary" value="מחיקה">
								
									<!-- <input type="button" id="addPupil" name="addPupil"
									 class="btn btn-primary" value="הוסף חדש"> -->
									
								</div>
								
								<div class="form-group " id="editModeBtn" style="display: none">
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
	var grades;

	</script>

	<!-- Morris Charts JavaScript -->
   <!--  <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script> -->

</body>

</html>
