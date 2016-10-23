<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="windows-1255"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="he">

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
  <!--   <link href="css/plugins/morris.css" rel="stylesheet"> -->

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <!-- jqgrid style -->
    <link rel="stylesheet" href="resources/jquery-ui-1.11.4.custom/jquery-ui.css">
	<link rel="stylesheet" href="css/ui.jqgrid.css">
	<link href="css/mycss.css" rel="stylesheet">
	
	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/i18n/grid.locale-he.js"></script>
  <!--  <script src="js/i18n/grid.locale-en.js"></script>  -->
	
 	
    <script src="js/jquery.jqGrid.min.js"></script> 

	
	<script src="js/js_pupils_phones.js"></script> 	
	
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
                        <a href="" style="font-size: 120%; pointer-events: none;"> <i class="fa fa-fw fa-phone"></i> דפי קשר</a>
                        <br>
                     </li> 
                     <li class="active">
                        <a href="pupils_phones.jsp" ><i class="fa fa-fw fa-user"></i> תלמידים</a>
                     </li> 
                     <li>
                        <a href="staff_phones.jsp" ><i class="fa fa-fw fa-phone"></i> סגל</a>
                     </li>
                     <!-- <li>
                        <a href= "#"><i class="fa fa-fw fa-phone"></i> מורי חוגים</a>
                     </li> 
                     <li>
                        <a href= "#"><i class="fa fa-fw fa-phone"></i> מורי  ביה"ס </a>
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
                            דפי קשר <small></small>
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-home"></i> ראשי</a>
                            </li>
                            <li>
                                 <a href="#"><i class="fa fa-phone"></i> דפי קשר</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-user"></i> תלמידים
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                                <div class="col-lg-12">
			                        <div class="panel panel-default">
			                            <div class="panel-heading">
			                                <h3 class="panel-title pull-right"><i class="fa fa-info fa-fw"></i> ניתן לחפש ולסנן רשומות</h3>
							                <div class="form-group pull-left" id="viewModeBtn">
												<a
													href="javascript:void(0);"
													onclick="exportData('0','xls', 'contact');">
													<img alt="" src="resources/images/Excel-icon.png" style="border-radius:15%; margin-right: 10px;">
												</a>
											
											</div>
			                                <button id="resetBtn" class="pull-left btn btn-primary">נקה חיפוש</button>
			                                <div class="clearfix"></div>
			                            </div>
			                            <div class="panel-body">
			                           
			                               <!--  <div class="text-right">
			                                    <a href="#">View All Transactions <i class="fa fa-arrow-circle-right"></i></a>
			                                </div> -->
			                                <div class="table-responsive col-lg-12">
			                                    <table class="table table-bordered table-hover table-striped" id="contact">
			                                
													<tr>
														<td ></td>
													</tr>
												</table>
											
											<div id="cont_page"></div>
			                                
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
<script type="text/javascript">

/* var currentUserId =	 '${p.pupilNum}' */
jQuery(document).ready(function() {
	var pupilID ;
	loadPupilSearch();
});
</script>
  
</body>

</html>
