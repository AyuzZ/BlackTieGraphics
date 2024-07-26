<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard Page</title>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath %>/stylesheets/dashboard.css" />
</head>
<body>
	<div class="mainContainer">
		<%
			String userSession = (String) session.getAttribute(StringUtils.USER_NAME);
			
			String cookieUsername  = null;
			String cookieSessionID = null;
			Cookie[] cookies = request.getCookies();
			if(cookies != null){
				for(Cookie cookie: cookies){
					if(cookie.getName().equals(StringUtils.USER)) cookieUsername = cookie.getValue();
					if(cookie.getName().equals(StringUtils.JSESSIONID)) cookieSessionID = cookie.getValue();
				}
			}
		%>
		<br>
		<br>
		<%
		String errorMessage =(String) request.getAttribute(StringUtils.ERROR_MESSAGE);
		if (errorMessage != null && !errorMessage.isEmpty()){
		%>
		<p class ="error-message"><%=errorMessage%> </p>
		<%
		} 
		%>
		<div class="product-container">
			<div class="title-container">
				<h1>Admin Dashboard Page</h1>
				<form action="/BlackTieGraphics/LogoutServlet" method="post">
					<button class="home-button">Logout</button>
				</form>
			</div>
			<h2>Products List</h2>
			<div class="product">
				<table>
					<tr>
						<th>Product Id</th>
						<th>Product Name</th>
						<th>Brand</th>
						<th>Unit Price</th>
						<th>Stock Quantity</th>
						<th>Product Description</th>
						<th>Product Image</th>
					</tr>
					<c:forEach var="product" items="${productList}">
						<tr>
							<td>${product.productId}</td>
							<td>${product.productName}</td>
							<td>${product.brand}</td>
							<td>${product.unitPrice}</td>
							<td>${product.stockQuantity}</td>
							<th>${product.productDescription}</th>
							<td>
								<img src="${pageContext.request.contextPath }/resources/images/products/${product.imageUrlFromPart}" width="80" height="80" alt="Product Image"/>
							</td>
							<td>
								<form method="get" action="${pageContext.request.contextPath}/UpdateProductServlet">
									<input type="hidden" name="updateId" value="${product.productId}"/>
									<button type="submit">Update</button>
								</form>
								<form id="deleteForm-${product.productId}" method="post" action="${pageContext.request.contextPath}/DeleteProductServlet">
									<input type="hidden" name="deleteId" value="${product.productId}"/>
									<button type="button" onclick="confirmDelete('${product.productId}')">Delete</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
				<a href="pages/addProduct.jsp"><h3>Add New Product</h3></a>
			</div>
		</div>
		
		<div class="order-container">
			<h2>Orders List</h2>
			<div class="order">
				<table>
					<tr>
						<th>Order Id</th>
						<th>Order Date</th>
						<th>Order Total</th>
						
						<th>Username</th>
						<th>Status</th>
					</tr>
					<c:forEach var="order" items="${orderList}">
						<tr>
							<td>${order.orderId}</td>
							<td>${order.orderDate}</td>
							<td>${order.orderTotal}</td>
							
							<td>${order.username}</td>
							<td>${order.status}</td>
							<td>
								<form method="get" action="${pageContext.request.contextPath}/UpdateOrderStatusServlet">
									<input type="hidden" name="updateId" value="${order.orderId}" />
									<button type="submit">Update</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
	</div>
</body>
<script>
	function confirmDelete(productId) {
		if (confirm("Are you sure you want to delete this product: " + productId
				+ "?")) {
			document.getElementById("deleteForm-" + productId).submit();
		}
	}
</script>
</html>