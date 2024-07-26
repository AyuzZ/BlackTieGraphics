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

		<form action="/BlackTieGraphics/UpdateProductServlet" method="post" enctype="multipart/form-data">
		
			<div class="container-5">
				<span class="register">Update Product</span>		
			</div>
			<div class="container-9">
				<div class="line-1"></div>	
			</div>
			
			<div class = container>
				<div class="row">
					<input type="hidden" id="productId" name="productId" value="${product.productId}">		
					<div class="col">
						<input type="text" id="productName" name="productName" value="${product.productName}" required>
					</div>
					<div class ="lastname">
						<input type ="text" id ="brand" name ="brand" value="${product.brand}" required>
					</div>
				</div>
					
				<div class ="row">
					<div class ="col">
						<input type="text" id ="unitPrice" name="unitPrice" value="${product.unitPrice}" required>
					</div>
					<div class ="col">
						<input type="text" id ="stockQuantity" name ="stockQuantity" value="${product.stockQuantity}" required>
					</div>
				</div>	
				
				<div class="row">
					<div class="col">
						<textarea rows="7" cols="50" maxlength="255" 
						id="productDescription" name="productDescription" required>${product.productDescription}</textarea>
					</div>
					
				</div>	
			</div>
			<br>
			<div class="row">
				<div class="col">
					<label for="image">Product Image</label> 
					<input type="file" id="image" name="image" required>
				</div>
			</div>
			<br>	
			<center>
				<button type="submit" class="btn_submit">
					Update
				</button>
			</center>
		</form>	
		<br>
		<center>
			<form action="/BlackTieGraphics/DashboardServlet" method="get">
				<button class="home-button">Dashboard Page</button>
			</form>
		</center>
	</div>
</body>
</html>