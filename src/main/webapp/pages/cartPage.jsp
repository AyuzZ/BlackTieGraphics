<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart Page</title>
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
	<table style="width: 75%">
		<tr>
			<th>Image</th>
			<th>Product Name</th>
			<th>Unit Price</th>
			<th>Order Quantity</th>
			<th>Line Total</th>
		</tr>
		<c:forEach var="cartItem" items="${cartList}">
		<tr>
			<td style="border: 2px solid black"><img src="${pageContext.request.contextPath }/resources/images/products/${cartItem.imageUrlFromPart}" width="80" height="80" alt="Product image"></td>
			<td style="border: 2px solid black">${cartItem.productName}</td>
			<td style="border: 2px solid black">${cartItem.unitPrice}</td>
			<td style="border: 2px solid black">
				<div class="quantity">
					<button class="minus" onclick="minusFunction('${cartItem.productId}')">-</button>
					<input class="amount" type="text" value="${cartItem.orderQuantity}" id="number-${cartItem.productId}" />
					<button class="plus" onclick="plusFunction('${cartItem.productId}')">+</button><br>
				</div>
			</td>

			<td style="border: 2px solid black">${cartItem.lineTotal}</td>
			<td>
				<form method="post" action="${pageContext.request.contextPath}/UpdateCartOrderQuantityServlet" onsubmit="return getOrderQuantity('${cartItem.productId}')">
					<input type="hidden" name="updateProductId" value="${cartItem.productId}" />
					<input type="hidden" name="updateCartId" value="${cartItem.cartId}" />
					<input type="hidden" id="orderQuantity-${cartItem.productId}" name="orderQuantity" />
					<button type="submit">Update</button>
				</form>
				<form id="deleteForm-${cartItem.productId}-${cartItem.cartId}" method="post" action="${pageContext.request.contextPath}/DeleteCartItemServlet">
					<input type="hidden" name="deleteProductId" value="${cartItem.productId}" />
					<input type="hidden" name="deleteCartId" value="${cartItem.cartId}" />
					<button type="button"
						onclick="confirmDelete('${cartItem.productId}','${cartItem.cartId}','${cartItem.productName}')">Delete
					</button>
				</form>
			</td>
		</tr>
		</c:forEach>
    </table>
	<br>
	<br>
	<center> 
	   <form method="post" action="${pageContext.request.contextPath}/PlaceOrderServlet">
			<%-- <input type="hidden" name="orderCartId" value="${cartItem.cartId}" /> --%>
			<button type="submit">Place Order</button>
		</form>
	<center>
	<jsp:include page="<%=StringUtils.FOOTER_PAGE%>" />
</body>
<script>
	function plusFunction(productId)
	{
		var inputElement = document.getElementById('number-' + productId);
    	var value = parseInt(inputElement.value);
	  value++;
	  if(value<=10){
		  inputElement.value = value;
	  }
	}
	
	function minusFunction(productId){
		var inputElement = document.getElementById('number-' + productId);
    	var value = parseInt(inputElement.value);               
	  if(value>0){
		  value--;
	        inputElement.value = value;
	  } 
	}
	
	function getOrderQuantity(productId){
		var inputElement = document.getElementById('number-' + productId);
		var orderQty = parseInt(inputElement.value); 
		if (orderQty === "" || isNaN(orderQty) || parseInt(orderQty) <= 0) {
	      alert("Please enter a valid quantity.");
	      return false; // Prevent form submission
	    }
		document.getElementById('orderQuantity-' + productId).value = orderQty;
		return true;
	}
	
	function confirmDelete(productId, cartId, productName) {
		if (confirm("Are you sure you want to remove this product from your cart: " + productName
				+ "?")) {
			document.getElementById("deleteForm-" + productId + "-" + cartId).submit();
		}
	}
</script>
</html>