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
	
	<script type="text/javascript">
			
			var currentUserId =	 '${ session.getAttribute("userid") }';
		
		</script>
<title>מועדונית</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Core CSS RTL-->
<link href="css/bootstrap-rtl.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/sb-admin.css" rel="stylesheet">
<link href="css/sb-admin-rtl.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link href="css/datepicker.css" rel="stylesheet">
<!-- jqgrid style -->
 <link rel="stylesheet"	href="resources/jquery-ui-1.11.4.custom/jquery-ui.css">
 <link rel="stylesheet" href="css/ui.jqgrid.css">
<link rel="stylesheet" href="css/ui.jqgrid-bootstrap-ui.css">
<link rel="stylesheet" href="css/ui.jqgrid-bootstrap.css">
 <link href="css/mycss.css" rel="stylesheet">

<!-- jQuery -->
<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- jqgrid Core -->
<script src="js/i18n/grid.locale-he.js"></script>
 <script src="js/jquery.jqGrid.min.js"></script>
<!-- jqgrid Addons -->
 <script src="js/jQuery.jqGrid.setColWidth.js"></script>
 <script src="js/jQuery.jqGrid.autoWidthColumns.js"></script>
 

<!-- bootbox code -->
<script src="js/bootbox.js"></script>
<script src="js/bootstrap-datepicker.js"></script> 
<script src="js/i18n/bootstrap-datepicker.he.min.js"></script> 

<!-- form validation plugin -->
<script src="js/jquery.validate.js"></script>
<script src="js/additional-methods.js"></script>
<script src="js/messages_he.js"></script>

<script src="js/js_logic.js"></script>

<script src="js/js_pupils_search.js"></script>
		
<script src="js/js_settings_general_tables.js"></script>
	
<script src="js/jquery.fileDownload.js"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
   <!-- datepicker datepicker-dropdown dropdown-menu datepicker-rtl datepicker-orient-left datepicker-orient-bottom  -->
<style type="text/css">

 .ui-jqgrid .ui-jqgrid-view, .ui-jqgrid .ui-jqgrid-pager {
    z-index: 9;
} 

.ui-jqgrid tr.jqgrow td {
 white-space: normal !important;
 height:auto;
 vertical-align:text-top;
 padding-top:2px;
}
</style>

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="nav navbar-right top-nav" style="padding-top: 15px;">
				<a href="login.jsp?action=logout"> <i
					class="fa fa-fw fa-power-off"></i>&nbsp;יציאה
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
					<li class="active"><a href="pupils_search.jsp"><i
							class="fa fa-fw fa-search"></i> חיפוש</a></li>
					<!-- <li><a href="pupils_phones.jsp"><i
							class="fa fa-fw fa-phone"></i> דפי קשר</a></li> -->
					<li><a href="pupil_add.jsp"><i class="fa fa-fw fa-edit"></i>
							הוספת חדש</a></li>
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
						<h1 class="page-header">
							חיפוש תלמידים  <small></small>
						</h1>
						<ol class="breadcrumb">
							<li><a href="dashboard.jsp"><i class="fa fa-home"></i>
									ראשי</a></li>
							<li class="active"><i class="fa fa-users"></i> תלמידים</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

    <div class="row"> <!--  pills row -->
               <br>
            	<!-- <div class="col-lg-1 col-md-6"></div> -->
            	<div class="col-lg-12 col-md-6 text-center">
				  <ul class="nav nav-pills center-pills" id="ulTabs">
				    <li id="OnTimeReg" class="active"><a data-toggle="pill" href="#rep1"><span>דוח גביה <br>רישום חד פעמי</span></a></li>
				    <li id="MoadonitReg"><a data-toggle="pill" href="#rep4"><span>דוח גביה <br>רישום למועדונית</span></a></li>
				    <li id="CourseReg"><a data-toggle="pill" href="#rep1"><span >דוח גביה <br>רישום לחוגים</span></a></li>
				    <li id="MoadonitData"><a data-toggle="pill" href="#rep2"><span >נתוני רישום <br>למועדונית</span></a></li>
				    <li id="CourseData"><a data-toggle="pill" href="#rep3"><span >נתוני רישום <br>לחוגים</span></a></li>
				    <li id="Search"><a data-toggle="pill" href="#menu2"><span >הצגת <br>תלמידים</span></a></li>
				  </ul>
				  
				  <div class="tab-content">
				    <div id="rep1" class="tab-pane  active">				      
				      <div class="col-lg-12">
				      <br>
						<div class="panel panel-default">
							<div class="panel-heading">
							<br>
								<h3 class="panel-title pull-right">
									<i class="fa fa-info fa-fw"></i> הכנס פרמטרים לדוח ולחץ על יצירת קובץ אקסל
								</h3>
							
								 <div class=" pull-left" id="">
									 <a
										href="javascript:void(0);"
										onclick="OnBntExportClick('xls');">
										<img alt="" src="resources/images/Excel-icon.png" style="border-radius:15%; margin-right: 10px;">
									</a>
								</div>
								<!-- <button id="resetBtn" class="pull-left btn btn-primary">נקה חיפוש</button> -->
								<div class="clearfix"></div>
							</div>
							<div class="panel-body">
								<div class=" col-lg-12" id="forRep1">
									<div class="form-group col-lg-2">
										
						
										<label for="monthNum">דוח לחודש</label>
										<select class="form-control input-sm" id="monthNum" name="monthNum">
											
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
											<option value="9">9</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>											
										</select>
										 
									</div>		
									<div class="form-group col-lg-2">
									
										<label for="yearNum">לשנה</label>
										<select class="form-control input-sm" id="yearNum" name="yearNum">
											<option value="0">נוכחית</option>
											<option value="1">תשע"ו</option>
											
										</select> 
									</div>							
								</div>
							</div>
						</div>
					</div>
				    </div>
				    <div id="rep2" class="tab-pane ">
				      <div class="col-lg-12">
				      <br>
						<div class="panel panel-default">
							<div class="panel-heading">
							<br>
								<h3 class="panel-title pull-right">
									<i class="fa fa-info fa-fw"></i> הכנס פרמטרים לדוח ולחץ על יצירת קובץ אקסל
								</h3>
							
								 <div class=" pull-left" id="">
									 <a
										href="javascript:void(0);"
										onclick="OnBntExportClick('xls');">
										<img alt="" src="resources/images/Excel-icon.png" style="border-radius:15%; margin-right: 10px;">
									</a>
								</div>
								<!-- <button id="resetBtn" class="pull-left btn btn-primary">נקה חיפוש</button> -->
								<div class="clearfix"></div>
							</div>
							<div class="panel-body">
								<div class=" col-lg-12" id="forRep1">
									<div class="form-group col-lg-2">
									
									
										<label for="dayPick">דוח לחודש</label>
										<input  type="text" class="form-control" id="dayPick" name="dayPick" >
										 
									</div>								
								</div>
							</div>
						</div>
					</div>
				    </div>
				    <div id="rep3" class="tab-pane ">
				      <div class="col-lg-12">
				      <br>
						<div class="panel panel-default">
							<div class="panel-heading">
							<br>
								<h3 class="panel-title pull-right">
									<i class="fa fa-info fa-fw"></i> הכנס פרמטרים לדוח ולחץ על יצירת קובץ אקסל
								</h3>
							
								 <div class=" pull-left" id="">
									 <a
										href="javascript:void(0);"
										onclick="OnBntExportClick('xls');">
										<img alt="" src="resources/images/Excel-icon.png" style="border-radius:15%; margin-right: 10px;">
									</a>
								</div>
								<!-- <button id="resetBtn" class="pull-left btn btn-primary">נקה חיפוש</button> -->
								<div class="clearfix"></div>
							</div>
							<div class="panel-body">
								<div class=" col-lg-12" id="forRep1">
									
									<div class="form-group col-lg-2" style="margin-right: 15px;">
									
										<label for="yearNum">לשנה</label>
										<select class="form-control input-sm" id="yearNum" name="yearNum">
											<option value="1">נוכחית</option>
											<option value="2">תשע"ו</option>
											
										</select> 
									</div>
									<div class="form-group col-lg-2">
									
										<label for="monthNum">חוגים</label>
										<select class="selectpicker"  style="margin-left: 15px;" data-size="8" data-width="250px"  multiple data-actions-box="true" id="courseList" name="courseList"  >
											<!-- <option value="9">כדורגל</option>
											<option value="10">כדורעף</option>
											<option value="11">כדורסל</option>
											<option value="12">כדוריד</option>
											<option value="1">קראטה</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option> -->
											
										</select> 
									</div>								
								</div>
							</div>
						</div>
					</div>
				    </div>
				    <div id="rep4" class="tab-pane ">				      
				      <div class="col-lg-12">
				      <br>
						<div class="panel panel-default">
							<div class="panel-heading">
							<br>
								<h3 class="panel-title pull-right">
									<i class="fa fa-info fa-fw"></i> הכנס פרמטרים לדוח ולחץ על יצירת קובץ אקסל
								</h3>
							
								 <div class=" pull-left" id="">
									 <a
										href="javascript:void(0);"
										onclick="OnBntExportClick('xls');">
										<img alt="" src="resources/images/Excel-icon.png" style="border-radius:15%; margin-right: 10px;">
									</a>
								</div>
								<!-- <button id="resetBtn" class="pull-left btn btn-primary">נקה חיפוש</button> -->
								<div class="clearfix"></div>
							</div>
							<div class="panel-body">
								<div class=" col-lg-12" id="forRep1">
									<div class="form-group col-lg-2"> 
										<label for="monthPick">דוח לחודש</label>
										<input  type="text" class="form-control" id="monthPick" name="monthPick" >
										 
									</div>								
								</div>
							</div>
						</div>
					</div>
				    </div>
				    <div id="menu2" class="tab-pane ">
				     
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title pull-right">
								<i class="fa fa-info fa-fw"></i> כל התלמידים
							</h3>
							<!-- <label for="isReg" class="pull-left" >�¨�©��</label> <select
															class="form-control pull-left col-lg-1"  id="isReg" name="sunday"
															 >
															<option value="0"></option>
															<option value="1">�¨�©��</option>
															<option value="2">�� �¨�©��</option>
														</select> -->

							 <div class="form-group pull-left" id="viewModeBtn">
							<!-- 	<a href="javascript:void(0);"
									onclick="exportData('0','pdf', 'list');">
									<img alt="" src="resources/images/pdf-icon.png">
									</a> -->
								 <a
									href="javascript:void(0);"
									onclick="exportData('0','xls', 'list','רשימת תלמידים','pupil list');">
									<img alt="" src="resources/images/Excel-icon.png" style="border-radius:15%; margin-right: 10px;">
								</a>
								
								<!-- 								 <a
									href="javascript:void(0);"
									onclick="exportDataOntime('0','xls', 'list');">
									<img alt="" src="resources/images/Excel-icon.png" >
								</a> -->
							
							</div>

							<button id="resetBtn" class="pull-left btn btn-primary">נקה חיפוש</button>
							<div class="clearfix"></div>
						</div>
						<div class="panel-body">

							<!--  <div class="text-right">
                                    <a href="#">View All Transactions <i class="fa fa-arrow-circle-right"></i></a>
                                </div> -->
						
							<div class="table-responsive col-lg-12">
								<table class="table table-bordered table-hover table-striped"
									id="list" >

									<tr>
										<td></td>
									</tr>
								</table>

								<div id="pager"></div>
								<form id="formstyle" action="" method="post" name="formstyle">
									<input type="hidden" name="pdfBuffer" id="pdfBuffer" value="" />
									<input type="hidden" name="fileName" id="fileName"
										value="GridPDF" /> <input type="hidden" name="fileType"
										id="fileType" value="" />
										
										<input type="hidden" name="firstName" id="firstName" value="" />
										<input type="hidden" name="lastName" id="lastName" value="" />
										<input type="hidden" name="gender" id="gender" value="" />
										<input type="hidden" name="isReg" id="isReg" value="" />
										<input type="hidden" name="gradeName" id="gradeName" value="" />
										<input type="hidden" name="sord" id="sord" value="" />
										<input type="hidden" name="sidx" id="sidx" value="" />

								</form>

								<!-- HTML for jQuery UI Modals -->
								<div id="preparing-file-modal" title="ייצוא הקובץ ..."
									style="display: none;">
										ייצוא הקובץ בתהליך, אנא המתן...

									<!--Throw what you'd like for a progress indicator below-->
									<div
										class="ui-progressbar-value ui-corner-left ui-corner-right"
										style="width: 100%; height: 22px; margin-top: 20px;"><img alt="" src="resources/images/ajax-loader.gif"></div>
								</div>

								<div id="error-modal" title="Error" style="display: none;">
									קיימת שגיאה בייצוא הקובץ, אנא נסה שוב</div>
							</div>

						</div>
					</div>
				</div>
				     </div>
				    <div id="menu3" class="tab-pane ">
				      <h3>Menu 3</h3>
				      <p>Eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
				    </div>
				  </div>
				</div>
			</div>
			

				<!-- /.container-fluid -->

			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->
	</div>


	<script type="text/javascript">
		/* var currentUserId =	 '${p.pupilNum}' */

	</script>


</body>

</html>
