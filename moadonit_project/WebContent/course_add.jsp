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

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="css/datepicker.css" rel="stylesheet">
                
	<link rel="stylesheet"	href="resources/jquery-ui-1.11.4.custom/jquery-ui.css">
	<link rel="stylesheet" href="css/ui.jqgrid.css">
	<link rel="stylesheet" href="css/ui.jqgrid-bootstrap-ui.css">

	<link rel="stylesheet" href="css/jquery.timepicker.css">

    <link href="css/mycss.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
   <!--  <link href="css/plugins/morris.css" rel="stylesheet"> -->

     <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
	
	<script src="js/jquery-ui.js"></script>

    
    
	<script src="js/moment-with-locales.js"></script> 
	<script src="js/combodate.js"></script> 	   
    <script src="js/i18n/grid.locale-he.js"></script>
    <script src="js/jquery.jqGrid.min.js"></script>
    
    <!-- bootbox code -->
    <script src="js/bootbox.js"></script> 
    
    <script src="js/js_logic.js"></script>
    <script src="js/js_course_add.js"></script> 
	
    
    <script src="js/jquery.are-you-sure.js"></script> 
    
    	<!-- form validation plugin -->
	<script src="js/jquery.validate.js"></script>
	<script src="js/additional-methods.js"></script>
	<script src="js/messages_he.js"></script>
	<script src="js/jquery.timepicker.min.js"></script>
	
    
    <script src="js/jquery.bpopup.min.js"></script> 
    <script src="js/bootstrap-datepicker.js"></script> 
	<script src="js/i18n/bootstrap-datepicker.he.min.js"></script>
	
	<!--  script for popup bloking div -->
	<script src="js/jquery.blockUI.js"></script>
    		
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
					<li><a href="" style="font-size: 120%; pointer-events: none;">
							<i class="fa fa-fw fa-futbol-o"></i> חוגים
					</a> <br></li>
					<li><a href="course_search.jsp"><i
							class="fa fa-fw fa-search"></i> חיפוש </a></li>
					<li><a href="#"><i
							class="fa fa-fw fa-phone"></i> מורי חוגים </a></li>
					<li><a href="course_regs_chart.jsp"><i class="fa fa-fw fa-edit"></i>
							דוח רשומים </a></li>
					<li class="active"><a href="#"><i class="fa fa-fw fa-edit"></i>
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
                        <h1 class="page-header" id="nameTitle" style="margin: 0px 0 0px; border-bottom: 1px solid #a6b7bd"> הוספת חוג                    
                        </h1> 
                        <ol class="breadcrumb">
                            <li>
                                 <a href="dashboard.jsp"><i class="fa fa-home"></i> ראשי</a>
                            </li>
                            <li>
                                 <a href="course_search.jsp"><i class="fa fa-futbol-o"></i> חוגים</a>
                            </li>
                         <!--    <li>
                                 <a href="dashboard.jsp"><i class="fa fa-user"></i> כרטיס חוג</a>
                            </li> -->
                            <li class="active">
                                <i class="fa fa-fw fa-edit"></i> הוספת חדש
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
	<!-- row 1  choose activityGroup -->
				<form role="form" id="ajaxform">
				<div class="row">
					
					<div class="col-lg-12" style="border-bottom: 1px solid #a6b7bd">
					 	<h2 style="margin: 0px 0 0px; border-bottom: 1px solid #a6b7bd">שם החוג </h2>
							<div class="col-lg-3">
								 <div class="form-group">
									<label for="activityGroupHead">בחר חוג קיים</label> 
										<select class="form-control input-sm" 
										id="activityGroupHead" name="activityGroupHead" >
										<option value=""></option>
										<option value="1">א</option>
										<option value="2">ב</option>
										<option value="3">ג</option>
										<option value="4">ד</option>
										<option value="5">ה</option>								
									</select>
								</div> 
							</div>
							<div class="col-lg-2">
								<div class="checkbox">
								<br>
									<label for="newGroup">חוג חדש</label> 
									<input type="checkbox" id="newGroup" name="newGroup" >
								</div>
							</div> 
							 <div class="col-lg-3">
								 <div class="form-group" style="display: none;" id="newActivityGroupDiv">
								 	<label for="newActivityGroupHead">הכנס שם חוג </label> 
										<input type="text" class="form-control input-sm" name="newActivityGroupHead"  id="newActivityGroupHead" >
								</div> 
							</div> 
					</div>
					
				</div>
				
	<!-- row 2 activity + course details -->
			<div class="row">
				
           			<fieldset >
	<!-- row 2 col 1 -->
					<div class="col-lg-12" style="border-bottom: 1px solid #a6b7bd">
					<h2 style=" border-bottom: 1px solid #a6b7bd">פרטי הקבוצה                     
                        </h2>
						<div class="col-lg-12">
							<div class="col-lg-2">
								<div class="form-group">
									<label for="activityName">שם הקבוצה</label> 
									<input type="text" class="form-control input-sm" name="activityName"  id="activityName" >
								</div>
								<div class="form-group">
									<label for="regularOrPrivate">סוג</label>
										<select class="form-control input-sm" 
										id="regularOrPrivate" name="regularOrPrivate" >
										<option value="רגיל">רגיל</option>
										<option value="פרטי">פרטי</option>
									</select>
									
								</div> 
							</div>
		<!-- row 2 col 2 -->
							<div class="col-lg-2">
								<div class="form-group">
									<label for="weekDay">יום בשבוע</label> 
										<select class="form-control input-sm" 
										id="weekDay" name="weekDay" >
										<option value=""></option>
										<option value="א">א</option>
										<option value="ב">ב</option>
										<option value="ג">ג</option>
										<option value="ד">ד</option>
										<option value="ה">ה</option>
										<!-- <option value="ו">ו</option>		 -->								
									</select>
								</div>
								<div class="form-group">
									<label for="startTime">שעת התחלה</label>
									<br>	
									<!-- <input type="text"
										class="form-control input-sm" id="startTime" name="startTime" style="width: 90px;"  >  -->
									<input type="text" class="form-control input-sm" 
										id="startTime" name="startTime" >
								</div>
								<div class="form-group">
									<label for="endTime">שעת סיום</label>									
									<br>	
									<!-- <input type="text"
										class="form-control input-sm" id="endTime" name="endTime" style="width: 90px;"  > --> 
									<input type="text"
										class="form-control input-sm" id="endTime" name="endTime"  >
								</div>
																
							</div>
		<!-- row 2 col 3 -->
							<div class="col-lg-2">
								<div class="form-group">
										<label for="responsibleStaff">שם המורה</label> 
										<select class="form-control input-sm" id="responsibleStaff" name="responsibleStaff" >
										<!-- <option value="1">שושנה מזרחי</option>
										<option value="2">דנית גבאי</option>
										<option value="3">שמשון בוזגלו</option>
										<option value="4">אהובה שלום</option>
										<option value="5">אורלי שוורץ</option> -->
									</select>								
								</div>
								<div class="form-group">
									
									<label for="capacity">מקסימום תלמידים</label>
									<input type="text"
										class="form-control input-sm" id="capacity" name="capacity"  > 
								</div>
							</div>
							<div class="col-lg-2">
								<div class="form-group">
									
									<label for="pricePerMonth">מחיר בחודש</label>
									<input type="text"
										class="form-control input-sm" id="pricePerMonth" name="pricePerMonth"  > 
								</div>
								<div class="checkbox">
									<label for="extraPriceChk">תשלום חומרים</label> 
									<input type="checkbox" id="extraPriceChk" name="staff" >
								</div>
								<div class="form-group" style="display: none;" id="extraPriceDiv">
									<label for="extraPrice">מחיר חומרים</label> <input type="text"
										name="extraPrice" class="form-control" id="extraPrice"
										placeholder="מחיר">
								</div>
							</div>
		<!-- row 2 col 4 -->							
							
						</div>
					</div>									
					</fieldset>
				
				</div>
				<div class="row">
					<div class="col-lg-12" style="border-bottom: 1px solid #a6b7bd">
						<div class="col-lg-4"></div>
						<div class="form-group col-lg-6" id="viewModeBtn" style="margin: 15px;">
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
    </form>
           		
  				
  			</div>
       	  </div> 
        </div>
        <!-- /#page-wrapper -->
				
    <!-- /#wrapper -->

		<%
			if(request.getParameter("activityNum") != null){
				String activityNum = request.getParameter("activityNum");
		%>	
	<script type="text/javascript">
		var activityNum = "<%=activityNum%>";
		currentPageState = state.READ;
	</script>
	<%} 
	else{%>
	
	<script type="text/javascript">
		var activityNum = "0";
		currentPageState = state.EDIT;
	</script>
	
	<%} %>
	
	<script type="text/javascript">
	
	

   	
	</script>
</body>

</html>
