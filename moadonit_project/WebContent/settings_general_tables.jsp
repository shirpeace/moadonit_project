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
			<div class="navbar-header pull-left" id="yearTag">
			 <span class="navbar-brand">שנה </span>
			</div>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					 <li>
                        <a href="#" style="font-size: 120%; pointer-events: none;"> <i class="fa fa-fw fa-cutlery"></i> ניהול מערכת</a>
                        <br>
                     </li> 
                     <li class="active">
                        <a href= "#"><i class="fa fa-fw fa-file-o"></i>  טבלאות מערכת</a>
                     </li> 
                     <li>
                        <a href= "cater_order.jsp"><i class="fa fa-fw fa-file-o"></i> קייטרינג</a>
                     </li> 
                     <li>
                        <a href= "#"><i class="fa fa-fw fa-file-text-o"></i> צוות המועדונית</a>
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
						<h1 class="page-header">
						טבלאות מערכת  <small></small>
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
				    <li id="tbl_reg_types" class="active"><a data-toggle="pill" href="#tblData"><span>סוגי <br>רישום</span></a></li>
				    <li id="tbl_food_type"><a data-toggle="pill" href="#tblData"><span>סוגי <br>ארוחות</span></a></li>
				    <li id="tbl_family_relation"><a data-toggle="pill" href="#tblData"><span >סוגי קרבה <br>משפחתית</span></a></li>
				    <li id="tbl_job_type"><a data-toggle="pill" href=#tblData><span >סוגי <br>משרות</span></a></li>
				    <li id="tbl_payment_type"><a data-toggle="pill" href="#tblData"><span>סוגי <br>תשלום</span></a></li>
				    <!-- <li id="tbl_pupil_state"><a data-toggle="pill" href="#tblData"><span >סטטוס <br>תלמיד</span></a></li> -->
				    
				    <li id="tbl_moadonit_groups"><a data-toggle="pill" href="#tblData"><span >שיוך כיתות <br>לקבוצות מועדונית</span></a></li>
				    <!-- <li id="tbl_pupil_state"><a data-toggle="pill" href="#tblData"><span >סטטוס <br>תלמיד</span></a></li> -->
				    <li id="tbl_school_years"><a data-toggle="pill" href="#tblData"><span >שנות <br>לימוד</span></a></li>
				    <li id="tbl_general_parameters"><a data-toggle="pill" href="#tblData"><span >פרמטרים <br>כלליים</span></a></li>
				    <li id="tbl_grade_code"><a data-toggle="pill" href="#tblData"><span >כיתות <br>ביה"ס</span></a></li>
				    <li id="tbl_grade_in_year"><a data-toggle="pill" href="#tblData"><span >כיתות <br>בשנה נוכחוית</span></a></li>
				  </ul>
				  
				  <div class="tab-content">

				    <div id="tblData" class="tab-pane active">
				     
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title pull-right">
										<i class="fa fa-info fa-fw"></i> פרטי טבלה
									</h3>
									<div class="clearfix"></div>
								</div>
								<div class="panel-body">
		
								
									<div class="table-responsive col-lg-12" id="tableContainer">
									<!-- 	<table class="table table-bordered table-hover "
											id="list" >
		
											<tr>
												<td></td>
											</tr>
										</table>
		
										<div id="pager"></div>
										
		
										 -->
									</div>
		
								</div>
							</div>
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
</div>

	<script type="text/javascript">
		/* var currentUserId =	 '${p.pupilNum}' */

	</script>


</body>

</html>
