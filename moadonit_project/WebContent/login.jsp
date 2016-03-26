<%@page import="dao.DataConnect"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<!--  java script -->
<script src="resources/js/jquery-1.12.2.js"></script>
<script src="resources/bootstrap/js/bootstrap.js"></script>
<script src="resources/js/template_logic.js"></script>
<script src="resources/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<!--  css -->

<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css" />
<link rel="stylesheet"
	href="resources/jquery-ui-1.11.4.custom/jquery-ui.css" />
<link rel="stylesheet" href="resources/css/dashboard.css" />
<link rel="stylesheet" href="resources/css/sticky-footer.css" />
<link rel="stylesheet" href="resources/css/login-css.css" />
<!-- <link rel="stylesheet" href="resources/css/login.css" /> -->

<title>מועדונית</title>

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
		      
		  	alert(dataString);
		  	 $.ajax({
		  		async: false,
				type: 'POST',
				datatype: 'json',
		        url: "loginController",
		        data: dataString,
		        success: function(data) {
		        	if (data.redirect) {
		                // data.redirect contains the string URL to redirect to
		                window.location.href = data.redirect;
		            }else {
		                // data.form contains the HTML for the replacement form
		                alert(data.form);
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
<body dir="rtl">
	<div id="header">
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container-fluid">


				<div class="col-md-6 .col-md-offset-3">
					<DIV style="padding: 10px; font-size: x-large; color: gray;">
						מועדונית</DIV>

				</div>

			</div>
		</div>
	</div>

	<div class="container" style="padding-top: 15px;">

		<div class="login-card">
			<h1>כניסה למערכת</h1>
			<br>
			<div id="FormError" class="alert alert-danger" role="alert" style="display: none;">
				
			</div>
			<form name="ajaxform" id="ajaxform" method="post"
				action="loginController">
				<input type="text" id="user" name="user" placeholder="שם משתמש"> <input
					type="password" id="pass" name="pass" placeholder="סיסמא"> <input
					type="submit" id="loginBtn" name="login" class="login login-submit"
					value="כניסה">
			</form>

			<!-- <div class="login-help">
				<a href="#">Register</a> • <a href="#">Forgot Password</a>
			</div> -->
		</div>
	</div>

	<div class="footer">
		<div class="container-fluid text-center">
			<p class="text-muted">תחתית....תחתית....תחתית....</p>
		</div>
	</div>


</body>
</html>