<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Page</title>
<link rel="stylesheet" type="text/css" href="/BlackTieGraphics/stylesheets/login.css" />
</head>
<body>

	<div class="mainContainer"> 

		<%
		String errorMessage =(String) request.getAttribute(StringUtils.ERROR_MESSAGE);
		if (errorMessage != null && !errorMessage.isEmpty()){
		%>
		<p class ="error-message"><%=errorMessage%> </p>
		<%
		} 
		%>

		<form action="/BlackTieGraphics/RegisterServlet" method="post" enctype="multipart/form-data">
			<div class="container-5">
				<a style="text-decoration: none;" href="#">
					<span class="register">
						Register
					</span>
				</a>

				<a style="text-decoration: none;" href="${pageContext.request.contextPath }/pages/login.jsp">
				<span class="register">
					Login?	
				</span>
				</a>
			</div>

			<div class="container-9">
				<div class="line-1">
				</div>	
			</div>
			
			<div class = container>
				<div class="row">
					<div class="col">
						<input type="text" id="firstName" name="firstName" required placeholder="Enter the First Name">
					</div>
					<div class ="lastname">
						<input type ="text" id ="lastName" name ="lastName" required placeholder="Enter the Last Name">
					</div>
				</div>
						
				<div class ="row">
					<div class ="col">
						<input type="text" id ="username" name="username" required placeholder="Enter the Username">
					</div>
					
					<div class ="col">
						<input type="password" id ="password" name ="password" required placeholder="Password">
					</div>
				</div>		
						
				<div class ="row">
					<div class ="col">
						<input type ="password" id ="retypePassword" name="retypePassword" required placeholder="Retype Password">
					</div>
					
					<div class ="col">
						<input type="email" id="email" name="email" required placeholder="Enter the Email">
					</div>
				</div>
				
				<div class="row">
					<div class="col">
						<label for="image">Profile Picture</label> 
						<input type="file" id="image" name="image" required>
					</div>
				</div>
					
			</div>
			<br>
			<br>	
			<center>
				<button type="submit" class="btn_submit">Register</button>
			</center>
			<br>
				
		</form>	
		<center>
			<form action="/BlackTieGraphics/HomePageServlet" method="post" >
				<button type="submit">Home Page</button>
			</form>
		</center>
	</div>
</body>
</html>