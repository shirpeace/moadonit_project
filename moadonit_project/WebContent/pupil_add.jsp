<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
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

<!--  java script -->
<!-- <script src="resources/js/jquery-1.12.2.js"></script>
	<script src="resources/bootstrap/js/bootstrap.js"></script>
	<script src="resources/js/template_logic.js"></script>
	<script src="resources/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script> -->

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<script src="js/jquery-ui.js"></script>

   <!-- bootbox code -->
    <script src="js/bootbox.js"></script>
    
<script src="js/js_logic.js"></script>

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
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">



<script src="js/moment-with-locales.js"></script>
<script src="js/combodate.js"></script>

<!-- form validation plugin -->
<script src="js/jquery.validate.js"></script>
<script src="js/additional-methods.js"></script>
<script src="js/messages_he.js"></script>
<script src="js/js_pupil_add.js"></script>

 

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<script type="text/javascript">
	var pupilID;
	var pupilData;
</script>

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="nav navbar-right top-nav" style="padding-top: 15px;">
				<a href="login.jsp?action=logout"> <i class="fa fa-fw fa-power-off"></i>&nbsp;יציאה
				</a>
			</div>
			<div class="navbar-header">
				<a class="navbar-brand" href="dashboard.jsp"> <i
					class="fa fa-home fa-fw"></i>&nbsp;מועדונית
				</a>

			</div>

			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="" style="font-size: 120%; pointer-events: none;">
							<i class="fa fa-fw fa-users"></i> תלמידים
					</a> <br></li>
					<li><a href="pupils_search.jsp"><i
							class="fa fa-fw fa-search"></i> חיפוש</a></li>
					<li><a href="pupils_phones.jsp"><i
							class="fa fa-fw fa-phone"></i> דפי קשר</a></li>
					<li class="active"><a href="pupil_add.jsp"><i
							class="fa fa-fw fa-edit"></i> הוספת חדש</a></li>
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
						<h1 class="page-header">הוספת תלמיד</h1>
						<ol class="breadcrumb">
							<li><a href="dashboard.jsp"><i class="fa fa-home"></i>
									ראשי</a></li>
							<li><a href="pupils_search.jsp"><i class="fa fa-users"></i>
									תלמידים</a></li>
							<li class="active"><i class="fa fa-edit"></i> הוספת תלמיד</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<form role="form" class="cmxform" id="ajaxform" name="ajaxform" method="get" action="">
					<!-- row 1 col 1 -->
					<div class="row">
						<div class="col-lg-12">
							<div class="col-lg-3">								
								<div class="form-group">
									<label for="lName">שם משפחה</label> <input type="text" 
										class="form-control" id="lName" name="lName" 
										placeholder="משפחה">
								</div>
								<div class="form-group">
									<label for="fName">שם פרטי</label> <input type="text"
										class="form-control" id="fName" name="fName" placeholder="שם" >
								</div>
								<div class="form-group">
									<label for="cell">טלפון נייד תלמיד</label> <input 
										class="form-control" id="cell" name="cell" placeholder="טלפון">
								</div>
							</div>
							<!-- row 1 col 2 -->
							<div class="col-lg-3" >
								<!-- <div class="form-group">
									<label for="fName">תאריך לידה</label> <input type="text"
										class="form-control" id="fName" placeholder="שם">
								</div> -->

								<div class="form-group">
									<label for="date_of_birth">תאריך לידה</label> <br> <input
										id="date_of_birth" name="date_of_birth" 
										data-smartDays="true" class=".ignore" data-firstItem="name"
										data-format="DD-MM-YYYY" data-template="D MMM YYYY">
								</div>
								<div class="form-group">

									<label for="grade">כיתה</label> 
									<select class="form-control" onchange="setGradeBgColor(this)"
										name="grade" id="grade"></select>
								</div>
								<div class="form-group">
								     <div class="checkbox-group required">
									<label for="genderGruop" class="col-lg-1">מגדר</label>
									 <label class="radio-inline col-lg-2">
									<input type="radio" name="genderGruop" id="male" value="1" checked="checked"> בן</label> 
									<label class="radio-inline col-lg-2"> <input
										type="radio" name="genderGruop" id="female" value="2">בת
									</label>									
									<!-- <span id="genderErr" style="font-weight:bold; color:red; display: inline; padding-right: 20px; visibility: hidden;">נא לסמן מגדר</span> -->
									</div>
								</div>
							</div>
							<!-- row 1 col 3 -->
							<div class="col-lg-2">
								<div class="form-group">
									<label for="food">סוג מנה</label>
									 <select class="form-control" name="food" id="food"></select>
								</div>
								<table dir="rtl"  style="width:100%" cellpadding="3" cellspacing="5">
									<tr>
										<td><label for="ethi">אתיופי</label></td>
										<td><input type="checkbox"
										id="ethi" name="ethi"></td>
									</tr>
									<tr>
										<td><label for="divorce">הורים גרושים</label></td>
										<td><input type="checkbox"
										id="divorce" name="divorce" ></td>
									</tr>
									<tr>
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
								<!-- <div class="checkbox ">
									<label for="ethi">אתיופי</label> <input type="checkbox"
										id="ethi" name="ethi">
								</div>
								<div class="checkbox ">
									<label for="divorce">הורים גרושים</label> <input type="checkbox"
										id="divorce" name="divorce" >
								</div>
								<div class="checkbox">
									<label for="staff">ילד סגל</label> <input type="checkbox"
										name="staff" id="staff">
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
									<label for="health">בעיות בריאות</label> <input type="text"
										name="health" class="form-control" id="health" placeholder="">
								</div>
								<div class="form-group">
									<label for="foodsens">רגישות למזון</label> <input type="text"
										class="form-control" id="foodsens" name="foodsens"
										placeholder="">
								</div>
								<div class="form-group">
									<label for="comnt">הערות כלליות</label> <input type="text"
										class="form-control" id="comnt" name="comnt" placeholder="">
								</div>
							</div>
						</div>
					</div>


					<!-- row 2 col 1 -->
					<div class="row" style="position: relative;">
						<div class="col-lg-12">
							<h2>פרטי התקשרות</h2>
							<div class="col-lg-3">

								<div class="form-group">
									<label for="p1fName">שם ההורה</label> <input type="text"
										name="p1fName" class="form-control" id="p1fName"
										placeholder="שם">
								</div>
								<div class="form-group">
									<label for="p1lName">שם משפחה</label> <input type="text"
										name="p1lName" class="form-control" id="p1lName"
										placeholder="משפחה">
								</div>
								<div class="form-group">
									<label for="p1cell">טלפון נייד</label> <input type="text"
										name="p1cell" class="form-control" id="p1cell"
										placeholder="טלפון">
								</div>
								<div class="form-group">
									<label for="p1mail">אימייל</label> <input type="email"
										name="p1mail" class="form-control" id="p1mail"
										placeholder="אימייל">
								</div>
								<div class="form-group">
									<label for="p1relat">קרבה</label> 
									<select class="form-control" name="p1relat" id="p1relat"></select>
								</div>
							</div>
							<!-- row 2 col 2 -->
							<div class="col-lg-3">

								<div class="form-group">
									<label for="p2fName">שם ההורה</label> <input type="text"
										name="p2fName" class="form-control" id="p2fName"
										placeholder="שם">
								</div>
								<div class="form-group">
									<label for="p2lName">שם משפחה</label> <input type="text"
										name="p2lName" class="form-control" id="p2lName"
										placeholder="משפחה">
								</div>
								<div class="form-group">
									<label for="p2cell">טלפון נייד</label> <input type="text"
										name="p2cell" class="form-control" id="p2cell"
										placeholder="טלפון">
								</div>
								<div class="form-group">
									<label for="p2mail">אימייל</label> <input type="email"
										name="p2mail" class="form-control" id="p2mail"
										placeholder="אימייל">
								</div>
								<div class="form-group">
									<label for="p2relat">קרבה</label> <select class="form-control"
										name="p2relat" id="p2relat">
										<!-- <option value="1">אמא</option>
										<option value="2" selected="selected">אבא</option>
										<option value="3">אח</option>
										<option value="4">אחות</option>
										<option value="5">אחר</option> -->
									</select>
								</div>
							</div>

							<!-- row 2 col 3 -->
							<div class="col-lg-3">

								<div class="form-group">
									<label for="address">כתובת מגורים</label> <input type="text"
										name="address" class="form-control" id="address"
										placeholder="כתובת">
								</div>
								<div class="form-group">
									<label for="phone">טלפון בבית</label> <input type="text"
										name="phone" class="form-control" id="phone"
										placeholder="טלפון">
								</div>
							</div>

							<!-- row 2 col 4 -->
							<div class="col-lg-9"></div>
							<div class="col-lg-3">
								<div class="form-group" id="viewModeBtn" >
								    <div id="btnActionDiv">
									<input type="button" id="saveBtn" name="saveBtn"
										class="btn btn-primary" value="שמור">
								    <input
										type="button" id="saveClearBtn" name="saveClearBtn"
										class="btn btn-primary" value="שמור ונקה">
								    <input
										type="button" id="clearBtn" name="clearBtn"
										class="btn btn-primary" value="נקה">
									</div>
								</div>

							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- Morris Charts JavaScript -->
	<!--  <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script> -->
	<script type="text/javascript">
	 /* //validate the form when it is submitted */
	
		
	</script>
</body>

</html>
