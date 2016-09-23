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
	
    <link href="css/mycss.css" rel="stylesheet">

	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
	<script src="js/js_report_page.js"></script>
	
	<script src="js/jquery.fileDownload.js"></script>
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
                    <!--  <li >
                        <a href= "#"><i class="fa fa-fw fa-file-o"></i> </a>
                     </li> 
                     <li>
                        <a href= "#"><i class="fa fa-fw fa-file-text-o"></i> </a>
                     </li> -->
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
            	<div class="col-lg-1 col-md-6"></div>
            	<div class="col-lg-10 col-md-6 text-center">
				  <ul class="nav nav-pills center-pills" id="ulTabs">
				    <li id="OnTimeReg" class="active"><a data-toggle="pill" href="#rep1"><span>דוח גביה <br>רישום חד פעמי</span></a></li>
				    <li id="MoadonitReg"><a data-toggle="pill" href="#rep1"><span>דוח גביה <br>רישום למועדונית</span></a></li>
				    <li id="CourseReg"><a data-toggle="pill" href="#rep1"><span >דוח גביה <br>רישום לחוגים</span></a></li>
				    <li id="MoadonitData"><a data-toggle="pill" href="#rep2"><span >נתוני רישום <br>למועדונית</span></a></li>
				    <li id="CourseData"><a data-toggle="pill" href="#rep2"><span >נתוני רישום <br>לחוגים</span></a></li>
				  </ul>
				  
				  <div class="tab-content">
				    <div id="rep1" class="tab-pane  active">
				      
				      <div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
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
											<option value="1">נוכחית</option>
											<option value="2">תשע"ו</option>
											
										</select> 
									</div>							
								</div>
							</div>
						</div>
					</div>
				    </div>
				    <div id="rep2" class="tab-pane ">
				      <div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
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
									
										<label for="monthNum">פרמטר אחר</label>
										<select class="form-control input-sm" id="monthNum" name="monthNum">
											<option value="9">א</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
											
										</select> 
									</div>
									<div class="form-group col-lg-2">
									
										<label for="yearNum">לשנה</label>
										<select class="form-control input-sm" id="yearNum" name="yearNum">
											<option value="1">נוכחית</option>
											<option value="2">תשע"ו</option>
											
										</select> 
									</div>								
								</div>
							</div>
						</div>
					</div>
				    </div>
				    <div id="menu2" class="tab-pane ">
				      <h3>Menu 2</h3>
				      <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
				    </div>
				    <div id="menu3" class="tab-pane ">
				      <h3>Menu 3</h3>
				      <p>Eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
				    </div>
				  </div>
				</div>
			</div>
<!--  pills end --> 
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
