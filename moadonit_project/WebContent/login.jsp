<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
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
	
<%-- 	<%
		if (session.getAttribute("userid") == null) {
			response.sendRedirect("login.jsp");
			return;
		}
	%> --%>

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

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script>
	var currentUserId =	 '<%=session.getAttribute("userid")%>';
	function register() {
		window.open("Register.jsp", "_self");
	}
</script>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<%
	//create a connection and store it in session attribute
	controller.MyConnection con;
%>

<%
	if (request.getParameter("action") != null) {
		if (request.getParameter("action").equals("logout")) {

			session.setAttribute("userid", null);
			if (session.getAttribute("connection") != null) {

				//get the connection
				con = (controller.MyConnection) session
						.getAttribute("connection");
				if (con != null) //close the connection on logout
				{
					//TODO: change this...
					con.closeConnection();
					session.setAttribute("connection", null);
				}

				request.getSession().invalidate();

			}
		}
	}
%>

<script type="text/javascript">


	 $(function() {
		    $('#FormError').hide();
		    $("#loginBtn").click(function() {
		      // validate and process form here
		      var err= "" ;
		      $('#FormError').hide();
		  	  var user = $("input#user").val();
		  		if (user == "") {
		  		err = "הזן שם משתמש";	
		  		 $('#FormError').html(err).show();
		  		$("input#user").focus();
		        return false;
		      }
		  		var pass = $("input#pass").val();
		  		if (pass == "") {
		  			err = "הזן סיסמא";	
		  			 $('#FormError').html(err).show();
		        $("input#pass").focus();
		        return false;
		      }
		  		
		  	var dataString = 'user='+ user + '&pass=' + pass + '&action=' + "login";
		      
		  
		  	 $.ajax({
		  		async: false,
				type: 'POST',
				datatype: 'jsonp',
		        url: "loginController",
		        data: dataString,
		        success: function(data) {
		        	if(data != undefined){
		        		window.location.href = "dashboard.jsp";
		        		//window.location.assign(data);
		        	
		        	
		        	}
		        },
		        error: function(e) {
		        	console.log("error");
					
		        }
		        
		      }); 
		      return false;
		      
		    });
		  });
	 

</script>
</head>

<body>

    <div id="wrapper" style="margin-left: 250px">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" >
            <!-- <div class="nav navbar-right top-nav" style="padding-top: 15px; ">
            	<a href="index.html">
	            	<i class="fa fa-fw fa-power-off"></i>&nbsp;יציאה</a>
            </div> -->
            <div class="navbar-header" >
            	<a class="navbar-brand" href="dashboard.jsp">
            	<i class="fa fa-home fa-fw"></i>&nbsp;מועדונית</a>
                
            </div>
            
            
        </nav>

        <div id="page-wrapper" style="height: 500px">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            <small></small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                               <!--  <i class="fa fa-home"></i> <a href="dashboard.jsp">עמוד ראשי</a> -->
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->



				
				<div class="row"> <!-- first button row -->
                    <div class="col-lg-4">
                    </div>
                    
                    <div class="col-lg-4">
                        <div class="panel panel-primary panel-footer">
							<h1>כניסה למערכת</h1>
							<br>
							<div id="FormError" class="alert alert-danger" role="alert"
								style="display: none;"></div>
							<form name="ajaxform" id="ajaxform" method="post"
								action="loginController">
								<input type="text" id="user" name="user" placeholder="שם משתמש">
								<br><br>
								<input type="password" id="pass" name="pass" placeholder="סיסמא">
								<br><br>
								<input type="submit" id="loginBtn" name="login"
									class="login login-submit" value="כניסה">
							</form>
						</div>
					</div>
				</div>
				<!-- /.row -->

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script >

    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script>

</body>

</html>
