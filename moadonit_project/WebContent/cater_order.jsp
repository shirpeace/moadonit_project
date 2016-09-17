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
    
    <!-- jqgrid style -->
	<link rel="stylesheet"
		href="resources/jquery-ui-1.11.4.custom/jquery-ui.css">
	<link rel="stylesheet" href="css/ui.jqgrid.css">
	<!-- <link rel="stylesheet" href="css/jquery.timepicker.css"> -->
	
    <link href="css/datepicker.css" rel="stylesheet">
    <link href="css/mycss.css" rel="stylesheet">
    
    <!-- jQuery -->
<script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
<script src="js/i18n/grid.locale-he.js"></script>
 <script src="js/jquery.jqGrid.min.js"></script>

<!-- bootbox code -->
<script src="js/bootbox.js"></script>
<script src="js/bootstrap-datepicker.js"></script> 
<script src="js/i18n/bootstrap-datepicker.he.min.js"></script> 

   
<script src="js/js_logic.js"></script>
	<script src="js/js_cater_order.js"></script> 	

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
                        <a href="" style="font-size: 120%; pointer-events: none;"> <i class="fa fa-fw fa-cutlery"></i> קייטרינג</a>
                        <br>
                     </li> 
                     <li class="active">
                        <a href= "#"><i class="fa fa-fw fa-file-o"></i> הזמנה</a>
                     </li> 
                     <li>
                        <a href= "#"><i class="fa fa-fw fa-file-text-o"></i> דוח הזמנות</a>
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
                            הערכת כמות מנות שבועית
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-home"></i> ראשי</a>
                            </li>
                            <li>
                                 <a href="settings_main.jsp"><i class="fa fa-folder-open"></i> ניהול</a>
                            </li>
                            <li class="active">
                                 <i class="fa fa-cutlery"></i> קייטרינג
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-12">
                        <div class="form-group  col-lg-2">
							<label for="weekPick">בחר שבוע (יום ראשון)</label>
							<input  type="text" class="form-control" id="weekPick" name="weekPick" >
						</div>
                    </div>
                </div>
                <!-- /.row -->
				
				<div class="col-lg-2"></div>
                <div class="col-lg-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-search fa-fw"></i> תכנית שבועית</h3>
                            </div>
                            <div class="panel-body">
                               <table class="table table-bordered table-hover table-striped"
									id="list" style="height: 50px">
										<tr>
											<td></td>
										</tr>
								</table>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-2">
                    	<input type="button" id="saveBtn" name="saveBtn" class="btn btn-primary " value="תיעוד הזמנה">
                    </div>
            </div>

        <!-- /#page-wrapper -->

    </div>
    </div>
    <!-- /#wrapper -->

   

</body>

</html>
