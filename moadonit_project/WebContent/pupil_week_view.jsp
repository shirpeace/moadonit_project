<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<link rel="stylesheet"
	href="resources/jquery-ui-1.11.4.custom/jquery-ui.css">
<link rel="stylesheet" href="css/ui.jqgrid.css">

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
<script src="js/js_pupil_week_view.js"></script>
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
			<div class="nav navbar-right top-nav" style="padding-top: 15px;">
				<a href="index.html"> <i class="fa fa-fw fa-power-off"></i>&nbsp;יציאה
				</a>
			</div>
			<div class="navbar-header">
				<a class="navbar-brand" href="dashboard.jsp"> <i
					class="fa fa-home fa-fw"></i>&nbsp;מועדונית
				</a>

			</div>

			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav" id="menuSideBar">
					<li><a href="" style="font-size: 120%; pointer-events: none;" >
							<i class="fa fa-fw fa-users"></i> כרטיס תלמיד
					</a> <br></li>
					<li><a href="pupil_card_view.jsp" id="detailsLink" ><i
							class="fa fa-fw fa-list-alt" ></i> פרטים אישיים</a></li>
					<li><a href="pupil_week_view.jsp" id="scheduleLink"><i
							class="fa fa-fw fa-th"></i> תכנית שבועית</a></li>
					<li><a href="pupil_week_view.jsp" id="regLink"><i
							class="fa fa-fw fa-edit"></i> עריכת רישום</a></li>
							                     <li>
                        <a href= "pupil_one_time_act.jsp"><i class="fa fa-fw fa-plus-square-o"></i> פעילות חד פעמית</a>
                     </li>  
				</ul>
			</div>
			<!-- /.navbar-collapse -->

		</nav>

		<!-- Main content -->
		<div id="page-wrapper">


			<div class="container-fluid">

				<!-- Page Heading -->
<!-- 				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header"><br></h1>
						<ol class="breadcrumb">
							<li><a href="dashboard.jsp"><i class="fa fa-home"></i>
									ראשי</a></li>
							<li><a href="dashboard.jsp"><i class="fa fa-users"></i>
									תלמידים</a></li>
							<li><a href="dashboard.jsp"><i class="fa fa-list-alt"></i>
									כרטיס תלמיד</a></li>
							<li class="active"><i class="fa fa-th"></i> תכנית שבועית
								לתלמיד</li>
						</ol>
					</div>
				</div> -->
				   <div class="row">
                    <div class="col-lg-12">
                        <h2 class="page-header" id="nameTitle" style="margin: 0px 0 0px; border-bottom: 1px solid #a6b7bd">                     
                        </h2>
                       <ol class="breadcrumb">
							<li><a href="dashboard.jsp"><i class="fa fa-home"></i>
									ראשי</a></li>
							<li><a href="pupils_search.jsp"><i class="fa fa-users"></i>
									תלמידים</a></li>
							<li><a href="dashboard.jsp"><i class="fa fa-list-alt"></i>
									כרטיס תלמיד</a></li>
							<li class="active"><i class="fa fa-th"></i> תכנית שבועית
								לתלמיד</li>
						<li>
                        	<a href= "pupil_week_view.jsp"><i class="fa fa-fw fa-plus-square-o"></i> פעילות חד פעמית</a>
						</ol>
                    </div>
                </div>
				<!-- /.row -->

				<div class="row">


					<!-- /.row -->

					<div class="col-lg-1"></div>
					<div class="col-lg-10">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-search fa-fw"></i> תכנית שבועית
								</h3>
							</div>
							<div class="panel-body">
								<div class="table-responsive col-lg-12">
									<table class="table table-bordered table-hover table-striped"
										id="list">

										<tr>
											<td></td>
										</tr>
									</table>
 
									<div id="pager"></div> 

								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">


					<!-- /.row -->

					<div class="col-lg-1"></div>
					<div class="col-lg-10">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-search fa-fw"></i> היסטורית רישום
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
				<div class="row">
					<div class="col-lg-1"></div>
					<div class="col-lg-10">
						<div class="panel panel-default">
							<div class="panel-heading">								
								<a href="javascript:;" data-toggle="collapse"
									data-target="#editReg">עריכת הרישום <i
									class="fa fa-arrow-circle-down"></i></a>

							</div>
							<div class="panel-body">
									<div id="editReg" class="collapse">
										<form>
												<div class="row">
													<div class="form-group col-lg-2">
														<label for="action">יום ראשון</label> <select
															class="form-control " id="action" name="action"
															onchange="actionChanged()">
															<option value="2">לא רשום</option>
															<option value="1">מועדונית</option>
															<option value="2">אוכל בלבד</option>
														</select>
													</div>
													<div class="form-group col-lg-2">
														<label for="action">יום שני</label> <select
															class="form-control " id="action" name="action"
															onchange="actionChanged()">
															<option value="2">לא רשום</option>
															<option value="1">מועדונית</option>
															<option value="2">אוכל בלבד</option>
														</select>
													</div>
													<div class="form-group col-lg-2">
														<label for="action">יום שלישי</label> <select
															class="form-control " id="action" name="action"
															onchange="actionChanged()">
															<option value="2">לא רשום</option>
															<option value="1">מועדונית</option>
															<option value="2">אוכל בלבד</option>
														</select>
													</div>
													<div class="form-group col-lg-2">
														<label for="action">יום רביעי</label> <select
															class="form-control " id="action" name="action"
															onchange="actionChanged()">
															<option value="2">לא רשום</option>
															<option value="1">מועדונית</option>
															<option value="2">אוכל בלבד</option>
														</select>
													</div>
													<div class="form-group col-lg-2">
														<label for="action">יום חמישי</label> <select
															class="form-control " id="action" name="action"
															onchange="actionChanged()">
															<option value="2">לא רשום</option>
															<option value="1">מועדונית</option>
															<option value="2">אוכל בלבד</option>
														</select>
													</div>
													
													<!-- <div class="form-group  col-lg-2">
													<label for="type"> </label>
													<input class="form-control btn btn-primary " style="margin-top: 5px;" type="button" value="שמור">
												</div> -->
												</div>
												<div class="row">
												<div class="form-group  col-lg-2">
													<label for="datePick"> בתאריך</label>
													<input  type="text" class="form-control" id="datePick">
												</div>
													<div class="form-group col-lg-3">
														<label for="action">סיבת הרישום</label> <select
															class="form-control " id="action" name="action"
															onchange="actionChanged()">
															<option value="1">לרשום את התלמיד</option>
															<option value="2">לבטל רישום</option>
														</select>
													</div>
													<div class="form-group  col-lg-2">
														<label for="type"> </label> <input
															class="form-control btn btn-primary "
															style="margin-top: 5px;" type="button" value="שמור">
													</div>
												</div>
											</form>
									</div>
								
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>

		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->


	<%
		if (request.getParameter("pupil") != null) {
			String pupil = request.getParameter("pupil");
			String li = request.getParameter("li");
			String reg = request.getParameter("reg");
	%>
	<script type="text/javascript">
					var pupilID = "<%=pupil%>";
					var selectedLi = "<%=li%>";
					var reg = "<%= reg %>";
	</script>
	<%
		} else {
	%>

	<script type="text/javascript">
		var pupilID = "3";
		var selectedLi, reg;
	</script>

	<%
		}
	%>

	<script type="text/javascript">
		$(function() {

			$('#detailsLink').attr('href','pupil_card_view.jsp?li=0&pupil=' + pupilID);
			$('#scheduleLink').attr('href','pupil_week_view.jsp?li=1&pupil=' + pupilID);
			$('#regLink').attr('href','pupil_week_view.jsp?reg=1&li=2&pupil=' + pupilID);
			
			var dataString = 'id='+ pupilID + '&action=' + "get";
			loadPupilCard(dataString);
			
			 $('#datePick').datepicker({
				    format: "dd/mm/yyyy",
				    language: "he" ,
				     startDate: "today",
				    maxViewMode: 0,
				    minViewMode: 0,
				    todayBtn: true,
				    keyboardNavigation: false,
				    daysOfWeekDisabled: "5,6",
				    todayHighlight: true,
				    toggleActive: true 
				}); 
			 
			loadWeekGrid(pupilID);
			
			loadRegistrationGrid(pupilID); 
			
			if(selectedLi == 1)
				$('#scheduleLink').parent().addClass('active');
			else if(selectedLi == 2)
				$('#regLink').parent().addClass('active');
			
			if (reg != undefined && reg ==1 ) {
				
				$("#editReg").slideToggle(100, function () {

			    }); 
			}
		});
		
	</script>
</body>

</html>
