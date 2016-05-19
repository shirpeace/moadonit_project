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
    
    <script src="js/js_logic.js"></script>
    <script src="js/js_course_card_view.js"></script> 
	
    
    <script src="js/jquery.are-you-sure.js"></script> 
    
    	<!-- form validation plugin -->
	<script src="js/jquery.validate.js"></script>
	<script src="js/additional-methods.js"></script>
	<script src="js/messages_he.js"></script>
	<script src="js/jquery.timepicker.min.js"></script>
	<!-- bootbox code -->
    <script src="js/bootbox.js"></script> 
    
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
     <style type="text/css">
	  #mainPopUP, #popUPResult { 
	    background-color:#fff;
	    border-radius:15px;
	    color:#000;
	    display:none; 
	    padding:20px;
	   /*  min-width:400px;
	    min-height: 180px; */
	}
	
	.b-close{
	    cursor:pointer;
	    position:absolute;
	    right:10px;
	    top:5px;
	}
	.buttonPopUp{background-color:#2b91af;border-radius:10px;box-shadow:0 2px 3px rgba(0,0,0,0.3);color:#fff;cursor:pointer;display:inline-block;padding:10px 20px;text-align:center;text-decoration:none}
	.buttonPopUp.small{border-radius:15px;float:right;margin:22px 5px 0;padding:6px 15px}
	.buttonPopUp:hover{background-color:#1e1e1e}
	.buttonPopUp>span{font-size:84%}
	.buttonPopUp.b-close,.buttonPopUp.bClose{border-radius:7px 7px 7px 7px;box-shadow:none;font:bold 131% sans-serif;padding:0 6px 2px;position:absolute;right:-7px;top:-7px}
	 .example{display:block;line-height:1.25;padding:30px 110px 15px 0}
	.code-undefined{color:#617a61}
	.code-string{color:#fa8072}
	.code-function{color:#ffa54f}
	.code-int{color:#2b91af}
	.code-comment{color:#7ccd60}::-moz-selection{background-color:#2b91af;color:#fff;text-shadow:none}
	::selection{background-color:#2b91af;color:#fff;text-shadow:none}
	#page h1+.buttonPopUp{position:absolute;top:20px;right:25px} 
    </style>

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
                        <a href="" style="font-size: 120%; pointer-events: none;"> <i class="fa fa-futbol-o"></i> פרטי חוג</a>
                        <br>
                     </li> 
                     <li class="active">
                        <a href= "pupil_card_view.jsp" id="detailsLink"><i class="fa fa-fw fa-list-alt"></i> פרטי חוג</a>
                     </li> 
                     <li>
                        <a href= "pupil_week_view.jsp" id="scheduleLink"><i class="fa fa-fw fa-th"></i> רשומים</a>
                     </li>
                     <li>
                        <a href= "pupil_week_view.jsp" id="regLink" ><i class="fa fa-fw fa-edit"></i> עריכת רישום</a>
                     </li>  
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
                        <h2 class="page-header" id="nameTitle" style="margin: 0px 0 0px; border-bottom: 1px solid #a6b7bd">                     
                        </h2>
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
                                <i class="fa fa-list-alt"></i> פרטי חוג
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

     <!--         copy content from pupil_add change buttons text & fonctionality -->
           		<form role="form" id="ajaxform">
           		<fieldset >
		<!-- row 1 col 1 -->
					<div class="col-lg-12" style="border-bottom: 1px solid #a6b7bd">
						<div class="col-lg-12">
							<div class="col-lg-3">
								<div class="form-group">
									<label for="fName">שם החוג</label> <input type="text" 
										class="form-control input-sm" name="activityName"  id="activityName" >
								</div>
								<div class="form-group">
									<label for="cell">סוג החוג</label>
										<select class="form-control input-sm" style="width: 100px;"
										id="regularOrPrivate" name="regularOrPrivate" >
										<option value="רגיל">רגיל</option>
										<option value="מיוחד">מיוחד</option>
										
									</select>
									
								</div> 
								<div class="form-group">
									<label for="weekDay">יום בשבוע</label> 
										<select class="form-control input-sm" style="width: 50px;"
										id="weekDay" name="weekDay" >
										<option value="א">א</option>
										<option value="ב">ב</option>
										<option value="ג">ג</option>
										<option value="ד">ד</option>
										<option value="ה">ה</option>
										<!-- <option value="ו">ו</option>		 -->								
									</select>
									
								</div>
								
							</div>
		<!-- row 1 col 2 -->
							<div class="col-lg-2">
								<div class="form-group">
									<label for="date_of_birth">שעת התחלה</label>
									<br>	
									<input type="text"
										class="form-control input-sm" id="startTime" name="startTime" style="width: 90px;"  > 
								</div>
								<div class="form-group">
									<label for="food">שעת סיום</label>									
									<br>	
									<input type="text"
										class="form-control input-sm" id="endTime" name="endTime" style="width: 90px;"  > 
								</div>
								<div class="form-group">
									
									<label for="grade">מחיר בחודש</label>
									<input type="text"
										class="form-control input-sm" id="pricePerMonth" name="pricePerMonth"  > 
								</div>								
							</div>
		<!-- row 1 col 3 -->
							<div class="col-lg-2">
							<div class="form-group">
								<label for="weekDay">שם המורה</label> 
										<select class="form-control input-sm" style="width: 50px;"
										id="firstName" name="firstName" >
										<option value="א">א</option>
										<option value="ב">ב</option>
										<option value="ג">ג</option>
										<option value="ד">ד</option>
										<option value="ה">ה</option>
										<!-- <option value="ו">ו</option>		 -->								
									</select>								
								</div>
								<div class="checkbox">
									<label for="extraPriceChk">תשלום חומרים</label> <input type="checkbox"
										id="extraPriceChk" name="staff" >
								</div>
								<div class="form-group" style="display: none;" id="extraPriceDiv">
									<label for="extraPrice">מחיר חומרים</label> <input type="text"
										name="extraPrice" class="form-control" id="extraPrice"
										placeholder="מחיר">
								</div>
								<!-- <div class="form-group">
									
									<label for="genderGruop" class="col-lg-1">קטגוריה</label>
										<select class="form-control input-sm"
										id="regularOrPrivate" name="regularOrPrivate" >
										<option value="רגיל">רגיל</option>
										<option value="מיוחד">מיוחד</option>
										
									</select>
									
								</div> -->
							</div>
							
		<!-- row 1 col 4 -->							
							
						</div>
						<div class="col-lg-12">
						<div class="form-group pull-left" id="viewModeBtn">
									<input type="button" id="editBtn" name="editBtn"
									class="btn btn-primary" value="עריכה">
								
									<input type="submit" id="deleteBtn" name="deleteBtn"
									class="btn btn-primary" value="מחיקה">
								
									<input type="submit" id="addCourse" name="clearBtn"
									 class="btn btn-primary" value="הוסף חדש">
									<!-- <button id="testBtn">test</button> -->
								</div>
								
								<div class="form-group pull-left" id="editModeBtn" style="display: none">
									<input type="button" id="saveBtn" name="saveBtn"
									class="btn btn-primary" value="שמור שינויים">
								
									<input type="submit" id="cancelBtn" name="deleteBtn"
									class="btn btn-primary" value="בטל שינויים">
								</div>
						</div>
					</div>									
					</fieldset>
				</form>
  				
  				<div class="col-lg-12" style="margin: 0px 0 0px; border-bottom: 1px solid #a6b7bd; ">
                   
                    <div class="col-lg-12" style="">                        
                       <!--  <h3  >  
                        	תלמידים בחוג                   
                        </h3> -->
                        <div class="row" style="margin-bottom: 10px; ">
                        
                       
                         <div style="display: inline; text-align: right; font-size: x-large; margin-left: 20px;">
                         תלמידים בחוג    
                         </div>
                        	 <input type="button" id="addPupilToCourse" name="addPupilToCourse" onclick="openPopup()"
									class="btn btn-primary" value="הוסף תלמידים">               
                         </div>
                       
                        <div class="table-responsive col-lg-10">
                        <table class="table table-bordered table-hover table-striped"
									id="list" >

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
        <!-- /#page-wrapper -->
		<div id="mainPopUP">
		   <span class="buttonPopUp b-close"><span>X</span></span>
			
			<div id="page-wrapper">
			

            	<div class="container-fluid">


                <!-- Page Heading -->
                <div class="row">
		    		 <div  class="col-lg-12">
		    		 	<form role="form" id="ajaxPoUPform">
		    		 		<fieldset >	    		 	
		    		 		<div class="form-group  col-lg-3">
								<label for="regDate">תאריך רישום</label>
								<input  type="text" class="form-control" id="regDate" name="regDate" >
							</div>
		    		 		<div class="form-group  col-lg-3">
								<label for="startDate">תאריך התחלה</label>
								<input  type="text" class="form-control" id="startDate" name="startDate" >
							</div>
		    		 		<div class="form-group  col-lg-3">
								<label for="endDate">תאריך סיום</label>
								<input  type="text" class="form-control" id="endDate" name="endDate" >
							</div>
		    		 		<div class="form-group  col-lg-3" style="margin-top: 26px">
		    		 		
							<input type="button" id="btnAddPupil" name="btnAddPupil" onclick="AddSelectedPupil()"
										class="btn btn-primary" value="אישור"> 
							</div>
							</fieldset>																							    		 		 
		    		 	</form>			    	  	
			    	</div>
		    	</div>	
               	 <div class="row">
               	 
                    <div class="col-lg-12">
                          <div class="table-responsive col-lg-12">
                                <div id="divPopUp">
                    
	                                 <table class="table table-bordered table-hover table-striped"
										id="listPopUp" >
	
										<tr>
											<td></td>
										</tr>
									</table>
	
									<div id="pagerPopUp"></div>
								</div>
                        </div>
                    </div>
                
		    	</div>
		    		    	
		       </div>
		    </div>
		</div>
		<div id="popUPResult">
			 <span class="buttonPopUp b-close"><span>X</span></span>
				
				<div id="page-wrapper">				
	            	<div class="container-fluid">
	               	 <div class="row">
	               	 
	                    <div id="popUPResultContent" class="col-lg-12">
	                    
			    		</div>		    	
			    	 </div>
			    	 <div class="row">
			    	 <input type="button" id="btnAddPupil" name="btnAddPupil" onclick="closeThis(this)"
										class="btn btn-primary" value="אישור">  
			    	</div>
			    </div>
			</div>
      </div>
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

	<!-- Morris Charts JavaScript -->
   <!--  <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script> -->

</body>

</html>
