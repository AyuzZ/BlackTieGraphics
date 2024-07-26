<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Page</title>
<link rel="stylesheet" type="text/css" href="/BlackTieGraphics/stylesheets/register.css" />
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
		
		<form action="/BlackTieGraphics/UpdateUserServlet" method="post" enctype="multipart/form-data">
			<div class="container-5">
				<span class="register">Edit Profile</span>		
			</div>
			<div class="container-9">
				<div class="line-1"></div>	
			</div>
			<div class = container>
				<div class ="row">
					<div class ="col">
						Username: ${user.username}
						<input type="hidden" id="username" name="username" value="${user.username}">
					</div>
				</div>	
			
				<div class="row">
					<div class="col">
						First Name
						<input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
					</div>
					<div class ="lastname">
						Last Name
						<input type ="text" id ="lastName" name ="lastName" value="${user.lastName}" required>
					</div>
				</div>		
				<div class ="row">
					<div class ="col">
						Phone Number
						<input type ="text" id ="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
					</div>
					<div class ="col">
						Email
						<input type="email" id="email" name="email" value="${user.email}" required>
					</div>
				</div>
				<div class ="row">
					<div class ="col">
					Gender
					<input type ="text" id ="gender" name="gender" value="${user.gender}">
					</div>
				</div>
					
				<div class ="row">
					<div class ="col">
					Date of Birth
					<input type ="date" id ="dob" name="dob" value="${user.dob}">
					</div>
					<div class ="col">
					Address
						<input type="text" id ="address" name ="address" value="${user.address}">
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
			<button type="submit" class="btn_submit">Update</button>	
		</form>	
		<div class="flex">	
			<form action="/BlackTieGraphics/UserProfileServlet" method="get">
				<button class="btn_submit">Profile Page</button>
			</form>
		</div>

	</div>
</body>
</html>