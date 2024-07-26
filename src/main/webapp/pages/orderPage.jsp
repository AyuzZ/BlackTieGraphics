<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Page</title>
<link rel="stylesheet" href="globals.css" />
<link rel="stylesheet" href="styleguide.css" />
<link rel="stylesheet" href="/BlackTieGraphics/stylesheets/style.css" />
<style>
.footer{
	min-height: 100%;
	position: relative;
	margin-top: -100px;
	clear: both;
	margin: 0;
}

th{
	border: 2px solid black;
}

td{
	border: 2px solid black
}
</style>
</head>

<body>
	<jsp:include page="<%=StringUtils.HEADER_PAGE%>" />

	<%
	String errorMessage =(String) request.getAttribute(StringUtils.ERROR_MESSAGE);
	if (errorMessage != null && !errorMessage.isEmpty()){
	%>
	<p class ="error-message"><%=errorMessage%> </p>
	<%
	} 
	%>

	<br><br><br><br><br><br>
	<c:forEach var="orderItem" items="${orderList}">
		<span>
			Order id : ${orderItem.orderId}
			Order date: ${orderItem.orderDate}
			Order total: ${orderItem.orderTotal}
			Order status: ${orderItem.status}
		</span>
	</c:forEach>
	<table style="width: 75%">
		<tr>
			<th>Image</th>
			<th>Product Name</th>
			<th>Unit Price</th>
			<th>Order Quantity</th>
			<th>Line Total</th>
		</tr>
		<c:forEach var="orderItem" items="${orderList}">
			<tr>
				<td><img src="${pageContext.request.contextPath }/resources/images/products/${orderItem.imageUrlFromPart}" width="80" height="80" alt="Product image"></td>
				<td>${orderItem.productName}</td>
				<td>${orderItem.unitPrice}</td>
				<td>${orderItem.orderQuantity}</td>
				<td>${orderItem.lineTotal}</td>
			</tr>
		</c:forEach>
    </table>
	  

	<jsp:include page="<%=StringUtils.FOOTER_PAGE%>" />
</body>
</html>