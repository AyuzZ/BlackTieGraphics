<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Product Page</title>
<link rel="stylesheet" type="text/css" href="/BlackTieGraphics/stylesheets/register.css" />
</head>
<body>

	<div class="mainContainer"> 

		<img class ="ellipse-1" src="${pageContext.request.contextPath}/resources/images/ellipse1 _x2.svg" alt="ellipse"/>
		<img class ="ellipse-2" src="${pageContext.request.contextPath}/resources/images/ellipse2_x2.svg" alt="ellipse"/>
		<img class ="ellipse-3" src="${pageContext.request.contextPath}/resources/images/ellipse3_x2.svg" alt="ellipse"/>
		<img class ="ellipse-4" src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo"/>

		<%
		String errorMessage =(String) request.getAttribute(StringUtils.ERROR_MESSAGE);
		if (errorMessage != null && !errorMessage.isEmpty()){
		%>
		<p class ="error-message"><%=errorMessage%> </p>
		<%
		} 
		%>

		<form action="/BlackTieGraphics/UpdateOrderStatusServlet" method="post">
			<div class="container-9">
				<div class="line-1"></div>
				<div class="line-2"></div>
			</div>	
			<div class = container>
				<div class="row">
					<input type="hidden" id="orderId" name="orderId" value="${order.orderId}">
					<div class="col">
						Current Status: ${order.status}
						<br>
						<br>
						<label for="status">Change To: </label>
						<select id="status"
							name="status" required>
							<option value="Processing">Processing</option>
							<option value="Delivering">Delivering</option>
							<option value="Delivered">Delivered</option>
						</select>
					</div>		
				</div>
				<br>	
				<center><button type="submit" class="btn_submit">Update Status</button></center>
			</div>
		</form>
		<br>
		<br>	
		<center>
			<form action="/BlackTieGraphics/DashboardServlet" method="get">
				<button class="home-button">Dashboard Page</button>
			</form>
		</center>
	</div>

</body>
</html>