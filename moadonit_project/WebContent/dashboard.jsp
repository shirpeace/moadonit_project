<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<!--  java script -->
<script src="resources/js/jquery-1.12.2.js"></script>
<script src="resources/bootstrap/js/bootstrap.js"></script>
<script src="resources/js/template_logic.js"></script>
<script src="resources/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<!--  css -->

<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css" />
<link rel="stylesheet"
	href="resources/jquery-ui-1.11.4.custom/jquery-ui.css" />
<link rel="stylesheet" href="resources/css/dashboard.css" />
<link rel="stylesheet" href="resources/css/sticky-footer.css" />
<link rel="stylesheet" href="resources/css/login-css.css" />
<!-- <link rel="stylesheet" href="resources/css/login.css" /> -->

<title>מועדונית</title>
</head>
<body dir="rtl">
	<div id="header">
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
				
					<div class="navbar-collapse collapse" style="margin-top:15px" >
						<a id="logout" href="login.jsp?action=logout"><span style=" color: white; font-size: large;	" >יציאה</span><img
							src="resources/images/exit.png" id="logout">
						</a>

					</div>
				</div>

				<div class="col-md-6 .col-md-offset-3">
					<DIV style="padding: 10px; color: white; font-size: x-large;;">
						מועדונית</DIV>


				</div>

			</div>
		</div>
	</div>
	<div id="content">
		<div class="container" style="padding-top: 15px;">
		
			<DIV style="margin-top: 50px;">

				<div class="col-sm-4" style="text-align: center;">

					<div class="panel panel-primary"
						style="width: 200px; height: 172px; margin: 0px">
						<div class="panel-cust-heading">חוגים</div>
						<div class="panel-body" style="text-align: center;">

							<img src="resources/images/courses.png" style="width: 90px;"
								alt="Image" />

						</div>
						<div class="panel-cust-footer"></div>
					</div>

				</div>

				<div class="col-sm-4" style="text-align: center;">

					<div class="panel panel-primary"
						style="width: 200px; height: 172px; margin: 0px">
						<div class="panel-cust-heading">רישום</div>
						<div class="panel-body" style="text-align: center;">

							<img src="resources/images/register.png" style="width: 90px;"
								alt="Image" />

						</div>
						<div class="panel-cust-footer"></div>
					</div>

				</div>

				<div class="col-sm-4" style="text-align: center;">

					<div class="panel panel-primary"
						style="width: 200px; height: 172px; margin: 0px">
						<div class="panel-cust-heading">תלמידים</div>
						<div class="panel-body" style="text-align: center;">

							<img src="resources/images/kids.png" style="width: 90px;"
								alt="Image" />

						</div>
						<div class="panel-cust-footer"></div>
					</div>

				</div>
			</DIV>

		
		</div>
	</div>
	<div class="footer">
		<div class="container-fluid text-center">
			<p class="text-muted">תחתית....תחתית....תחתית....</p>
		</div>
	</div>


</body>
</html>