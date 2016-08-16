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
    <script src="js/jquery-ui.js"></script>
	
	<script src="js/moment-with-locales.js"></script> 
    
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootbox.js"></script> 
    <script src="js/i18n/grid.locale-he.js"></script>
 	<script src="js/jquery.jqGrid.min.js"></script>
    <script src="js/js_pupil_one_time_act.js"></script> 
	<script src="js/js_logic.js"></script>

	
	<!-- form validation plugin -->
	<script src="js/jquery.validate.js"></script>
	<script src="js/additional-methods.js"></script>
	<script src="js/messages_he.js"></script> 
	
    <script src="js/bootstrap-datepicker.js"></script> 
	<script src="js/i18n/bootstrap-datepicker.he.min.js"></script> 
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Bootstrap Core CSS RTL-->
    <link href="css/bootstrap-rtl.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="css/sb-admin-rtl.css" rel="stylesheet">
	<link href="css/datepicker.css" rel="stylesheet">
	<!-- <link href="css/bootstrap-datepicker.standalone.css" rel="stylesheet"> -->

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<!-- jqgrid style -->
	 <link rel="stylesheet"	href="resources/jquery-ui-1.11.4.custom/jquery-ui.css">
	 <link rel="stylesheet" href="css/ui.jqgrid.css">
	<link rel="stylesheet" href="css/ui.jqgrid-bootstrap-ui.css"> 
    <link href="css/mycss.css" rel="stylesheet">

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top"  >
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
                     <li><a href="pupil_card_view.jsp" id="detailsLink" ><i
							class="fa fa-fw fa-list-alt" ></i> פרטים אישיים</a></li>
					<li><a href="pupil_week_view.jsp" id="scheduleLink"><i
							class="fa fa-fw fa-th"></i> תכנית שבועית</a></li>
					<li><a href="pupil_week_view.jsp" id="regLink"><i
							class="fa fa-fw fa-edit"></i> עריכת רישום</a></li>
					<li>
                        <a href= "pupil_one_time_act.jsp" id="oneTimeLink"><i class="fa fa-fw fa-plus-square-o"></i> פעילות חד פעמית</a>
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
                        <h1 class="page-header"></h1>
                        <ol class="breadcrumb">
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-home"></i> ראשי</a>
                            </li>
                            <li>
                                 <a href="pupils_search.jsp"><i class="fa fa-users"></i> תלמידים</a>
                            </li>
                            <li>
                                 <a href="pupil_card_view.jsp" id="bcPupilCard"><i class="fa fa-user"></i> כרטיס תלמיד</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-plus-square-o"></i> פעילות חד פעמית
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <div  class="row">
					<div class="col-lg-1"></div>
	                <div class="col-lg-8">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-info fa-fw"></i> צור רישום חד פעמי</h3>
                            </div>
                            <div class="panel-body">
                                <div class="text-right">
                                	<form id="oneTForm">
										<div class="row">
											<div class="form-group col-lg-4" id="typeDiv">
												<label for="typePick">ברצוני לרשום את התלמיד ל</label> 
												<select class="form-control " id="typePick" name="typePick">
													<option value=""></option>
													<option value="2">מועדונית</option>
													<option value="3">אוכל בלבד</option>
												</select>
											</div>
											<div class="form-group  col-lg-4">
												<label for="datePick"> בתאריך</label>
												<input  type="text" class="form-control" id="datePick" name="datePick">
											</div>
											<div class="form-group  col-lg-2">
												<!-- <label for="type"> </label> -->
												<input class="form-control btn btn-primary" id="saveBtn" style="margin-top: 5px;" type="button" value="שמור">
											</div>											
										</div>
									</form>
									
								</div>
								
                            </div>
                        </div>
                    </div>
            	</div>
            	
            	<!-- /.row -->
            	
            	<div class="row">


					
					<div class="col-lg-1"></div>
					<div class="col-lg-8">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									 רישום חד פעמי קודם
								</h3>
							</div>
							<div class="panel-body">
								<div class="table-responsive col-lg-12">
									<table class="table table-bordered table-hover table-striped"
										id="listRegistration">

										<tr>
											<td></td>
										</tr>
									</table>
 
									<div id="listRegistrationPager"></div> 

								</div>
							</div>
						</div>
					</div>
				</div>
            </div>

        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
</div>


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
		var dataString = 'id='+ pupilID + '&action=' + "get";
		var pupilData;
	
		
	</script>
		
</body>

</html>
