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

    <title>מועדונית</title>
	
	 <!-- jQuery -->
    <script src="js/jquery.js"></script>
	<script src="js/moment-with-locales.js"></script> 
    
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
	
	<script src="js/jquery-ui.js"></script>
	<script src="js/js_logic.js"></script>
    
    
	
	<script src="js/combodate.js"></script> 	   
    <script src="js/bootbox.js"></script> 
    <script src="js/js_pupil_one_time_act.js"></script> 
	
    
    <script src="js/jquery.are-you-sure.js"></script> 
    
    	<!-- form validation plugin -->
	<script src="js/jquery.validate.js"></script>
	<script src="js/additional-methods.js"></script>
	<script src="js/messages_he.js"></script>
	
	<!-- bootbox code -->
    <script src="js/bootbox.js"></script> 
    <script src="js/bootstrap-datepicker.js"></script> 
  <!--   <script src="js/i18n/bootstrap-datepicker.he.min.js"></script>  -->
	
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Core CSS RTL-->
    <link href="css/bootstrap-rtl.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="css/sb-admin-rtl.css" rel="stylesheet">
	<link href="css/datepicker.css" rel="stylesheet">
	<link href="css/bootstrap-datepicker.standalone.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    

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
        <nav class="navbar navbar-inverse navbar-fixed-top"  >
            <div class="nav navbar-right top-nav" style="padding-top: 15px; ">
            	<a href="index.html">
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
                     <li>
                        <a href= "pupil_card_view.jsp"><i class="fa fa-fw fa-list-alt"></i> פרטים אישיים</a>
                     </li> 
                     <li>
                        <a href= "pupil_week_view.jsp"><i class="fa fa-fw fa-th"></i> תכנית שבועית</a>
                     </li>
                     <li>
                        <a href= "pupil_week_view.jsp"><i class="fa fa-fw fa-edit"></i> עריכת רישום</a>
                     </li> 
                     <li class="active">
                        <a href= "pupil_week_view.jsp"><i class="fa fa-fw fa-plus-square-o"></i> פעילות חד פעמית</a>
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
                            יוסי משה
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-home"></i> ראשי</a>
                            </li>
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-users"></i> תלמידים</a>
                            </li>
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-user"></i> כרטיס תלמיד</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-plus-square-o"></i> פעילות חד פעמית
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <div >
					<!-- <div class="col-lg-2">
					</div> -->
	                <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-info fa-fw"></i> בחר תאריך וסוג רישום ולחץ שמור</h3>
                            </div>
                            <div class="panel-body">
                                <div class="text-right">
                                	<form >
										<div class="row">
											<div class="form-group col-lg-3">
												<label for="action">ברצוני</label>
												<select class="form-control " id="action" name="action" onchange="actionChanged()">
													<option value="1">לרשום את התלמיד</option>
													<option value="2">לבטל רישום</option>
												</select>
											</div>
											
											<div class="form-group  col-lg-3">
												<label for="datepicker">בתאריך</label>
												<input type="date" class="form-control" name="datepicker"  id="datepicker" placeholder="בחר">
											</div>
											<div class="form-group col-lg-3 " id="typeDiv">
												<label for="type">סוג הרישום</label>
												<select class="form-control " id="type" name="type">
													<option value="1">מועדונית</option>
													<option value="2">אוכל בלבד</option>
												</select>
											</div>
											<div class="form-group  col-lg-2">
												<label for="type"> </label>
												<input class="form-control btn btn-primary" id="saveBtn" style="margin-top: 5px;" type="button" value="שמור">
											</div>
										</div>
									</form>
									
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
		loadPupilOneAct(dataString);	
		
		
		
		/* 
		 $('#datePick').datepicker({
		    format: "dd/mm/yyyy",
		    startDate: "today",
		    maxViewMode: 0,
		    todayBtn: true,
		    locale: "he",
		    keyboardNavigation: false,
		    daysOfWeekDisabled: "5,6",
		    todayHighlight: true,
		    toggleActive: true
		});  */
	</script>
		
</body>

</html>
