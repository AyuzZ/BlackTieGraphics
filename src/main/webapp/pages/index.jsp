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
<meta charset="utf-8">
<title>Home Page</title>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath %>/stylesheets/style.css" />
</head>

<body>
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
	
	<jsp:include page="<%=StringUtils.HEADER_PAGE%>" />
	
	<img class="ad" src="${pageContext.request.contextPath }/resources/images/4ktech.jpg" />
    <div class="frame-5">	
			<%
		String errorMessage =(String) request.getAttribute(StringUtils.ERROR_MESSAGE);
		if (errorMessage != null && !errorMessage.isEmpty()){
		%>
		<p class ="error-message"><%=errorMessage%> </p>
		<%
		} 
		%>
		<br>
		<br>
		<h1>Our Products</h1>	
    </div>

	<div class="small-container" style="display: flex;">
		<c:forEach var="product" items="${productList}">
			<!--1st Product-->
			<div class="col-3">
				<a href="#"><img src="${pageContext.request.contextPath }/resources/images/products/${product.imageUrlFromPart}" alt="Product image"></a>
				<h3>${product.productName}</h3>
				<p>${product.brand}</p>
				<div class="description">
					<div class="price">${product.unitPrice}</div>
					<form method="get"
						action="${pageContext.request.contextPath}/ProductPageServlet">
						<input type="hidden"  name="productId" value="${product.productId}" />
						<button class="button btn-1" type="submit">View Product</button>
					</form>
				</div>
			</div>
		</c:forEach>
	</div>

	<jsp:include page="<%=StringUtils.FOOTER_PAGE%>" />

</body>
</html>