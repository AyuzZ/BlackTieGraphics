<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Profile</title>
<link rel="stylesheet" href="globals.css" />
<link rel="stylesheet" href="styleguide.css" />
<link rel="stylesheet" href="/BlackTieGraphics/stylesheets/style.css" />
</head>

<body>
	<jsp:include page="<%=StringUtils.HEADER_PAGE%>" />

	<div class="frame">
		<div class="div">
			<div class="desktop">
				<img src="${pageContext.request.contextPath }/resources/images/users/${user.imageUrlFromPart}" style="max-width: 600px; border-radius: 24px; margin: 10px " >
				<br>
				<br>
				<center>
				<div class="div-3">
					<form method="get" action="${pageContext.request.contextPath}/UpdateUserServlet">
						<input type="hidden" name="updateUsername" value="${user.username}" />
						<button type="submit" class="button btn-1">Edit Profile</button>
					</form>
					<button type="submit" class="button btn-1" onclick="location.href='/BlackTieGraphics/OrderHistoryServlet'">
						View Order History
					</button>
				</div>
				<center>
			</div>
			<div class="div-2">
				<div class="text-wrapper">First Name: ${user.firstName}</div>
				<div class="text-wrapper-2">Last Name: ${user.lastName}</div>
				<div class="text-wrapper-2">Username: ${user.username}</div>
				<div class="text-wrapper-2">Address: ${user.address}</div>
				<div class="text-wrapper-2">Gender: ${user.gender}</div>
				<div class="text-wrapper-2">Dob: ${user.dob}</div>
				<div class="text-wrapper-2">Email: ${user.email}</div>
				<div class="text-wrapper-2">Phone Number: ${user.phoneNumber}</div>
			</div>
		</div>
	</div>

	<br><br><br><br><br>

	<jsp:include page="<%=StringUtils.FOOTER_PAGE%>" />  

</body>
</html>