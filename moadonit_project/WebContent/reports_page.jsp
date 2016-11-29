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
    <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge"> -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Core CSS RTL-->
    <link href="css/bootstrap-rtl.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="css/sb-admin-rtl.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="css/plugins/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <link rel="stylesheet"
	href="resources/jquery-ui-1.11.4.custom/jquery-ui.css">
	
	<link rel="stylesheet" href="css/bootstrap-select.css">
	
	<link href="css/datepicker.css" rel="stylesheet">
    <link href="css/mycss.css" rel="stylesheet">

	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-select.js"></script>
	<script src="js/defaults-he_HE.js"></script> <!-- translation files for bootstrap-select-->
	
	<script src="js/bootstrap-datepicker.js"></script> 
	<script src="js/i18n/bootstrap-datepicker.he.min.js"></script> 
	
	<script src="js/bootbox.js"></script>
	
	<script src="js/js_logic.js"></script>
	<script src="js/js_report_page.js"></script>
	
	<script src="js/jquery.fileDownload.js"></script>
	
	<!-- form validation plugin -->
	<script src="js/jquery.validate.js"></script>
	<script src="js/additional-methods.js"></script>
	<script src="js/messages_he.js"></script>

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
                        <a href="" style="font-size: 120%; pointer-events: none;"> <i class="fa fa-fw fa-file-o"></i> דוחות</a>
                        <br>
                     </li> 
                      <li class="active" >
                        <a href= "#"><i class="fa fa-fw fa-file-o"></i> דוחות</a>
                     </li> 
                     <li>
                        <a href= "cater_order.jsp"><i class="fa fa-fw fa-cutlery"></i> תכנון קייטרינג </a>
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
                            דוחות
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-home"></i> ראשי</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-file-o"></i> דוחות
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
<!--  pills start -->
               <div class="row"> <!--  pills row -->
               <br>
            	<div class="col-lg-1 col-md-6"></div>
            	<div class="col-lg-10 col-md-6 text-center">
				  <ul class="nav nav-pills center-pills" id="ulTabs">
				    <li id="OnTimeReg" class="active"><a data-toggle="pill" href="#rep1"><span>דוח גביה <br>רישום חד פעמי</span></a></li>
				    <li id="MoadonitReg"><a data-toggle="pill" href="#rep1"><span>דוח גביה <br>רישום למועדונית</span></a></li>
				 <!--    <li id="CourseReg" class="disabled"><a data-toggle="pill" href="#"><span >דוח גביה <br>רישום לחוגים</span></a></li> -->
				    <li id="MoadonitData"><a data-toggle="pill" href="#rep2"><span >נתוני רישום <br>למועדונית</span></a></li>
				    <li id="CourseData"><a data-toggle="pill" href="#rep3"><span >נתוני רישום <br>לחוגים</span></a></li>
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
									<form id="rep1form">
									<div class="form-group col-lg-2"> 
										<label for="monthPick">דוח לחודש</label>
										<input  type="text" class="form-control" id="monthPick" name="monthPick" >
										 
									</div>	
									</form>		
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
									<form id="rep2form">
									<div class="form-group col-lg-2">
									
										<label for="dayPick">דוח לתאריך</label>
										<input  type="text" class="form-control" id="dayPick" name="dayPick" >
										 
									</div>	
									</form>							
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
									<form id="rep3form">
									<div class="form-group col-lg-2" style="margin-right: 15px;">
									
										<label for="yearNum">לשנה</label>
										<select class="form-control input-sm" id="yearNum" name="yearNum">
											<option value="0">נוכחית</option>
											<option value="1">תשע"ו</option>
											
										</select> 
									</div>
									<div class="form-group col-lg-2">
									
										<label for="courseList">חוגים</label>
										<select class="selectpicker"  style="margin-left: 15px;" data-size="8" data-width="250px"  multiple data-actions-box="true" id="courseList" name="courseList"  >
										</select> 
									</div>	
									</form>							
								</div>
							</div>
						</div>
					</div>
				    </div>
				  </div>
				
				</div>
			</div>
<!--  pills end --> 
<br><br><br>
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
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    
    <!-- Morris Charts JavaScript -->
    <!-- <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script> -->

</body>

</html>
