<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="/BlackTieGraphics/stylesheets/login.css" />
</head>
<body style="margin-right: 0px">

	<div class="mainContainer">
		<%
		String errorMessage =(String) request.getAttribute(StringUtils.ERROR_MESSAGE);
		if (errorMessage != null && !errorMessage.isEmpty()){
		%>

		<p class ="error-message"><%=errorMessage%> </p>

		<%
		}
		%>

		<form action="/BlackTieGraphics/LoginServlet" method="post">

			<div class="container-5">
				<a style="text-decoration: none;" href="#">
					<span class="register">
						Login
					</span>
				</a>
				<a style="text-decoration: none;" href="${pageContext.request.contextPath }/pages/register.jsp">
					<span class="login">
						Register?	
					</span>
				</a>
			</div>

			<div class="container-9">
				<div class="line-1">
				</div>
			</div>
			
			<div class = container>		
				<div class ="row">
					<div class ="col">
						<input type="text" id ="username" name="username" required placeholder="Enter the Username">
					</div>
					<div class ="col">
						<input type="password" id ="password" name ="password" required placeholder="Password">
					</div>
				</div>		
			</div>
			<br>
			<br>	
			<center>
				<button type="submit" class="btn_submit">
					Login
				</button>
			</center>
			<br>	
		</form>	

		<center>
			<form action="/BlackTieGraphics/HomePageServlet" method="post" >
		<button type="submit">Home Page</button></form></center>
	</div>

</body>
</html>