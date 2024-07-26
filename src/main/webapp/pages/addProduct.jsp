<%@page import="util.StringUtils"%> <%@ page language="java"
contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1" />
<title>Add Product Page</title>
<link rel="stylesheet" type="text/css" href="/BlackTieGraphics/stylesheets/addProduct.css"/>
</head>
<body>
    <div class="mainContainer">
    	<img class="ellipse-1" src="${pageContext.request.contextPath}/resources/images/ellipse1 _x2.svg" alt="ellipse"/>
      	<img class="ellipse-2" src="${pageContext.request.contextPath}/resources/images/ellipse2_x2.svg" alt="ellipse"/>
      	<img class="ellipse-3" src="${pageContext.request.contextPath}/resources/images/ellipse3_x2.svg" alt="ellipse"/>
      	<img class="ellipse-4" src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo"/>

		<% String errorMessage =(String)
		request.getAttribute(StringUtils.ERROR_MESSAGE); if (errorMessage != null
		&& !errorMessage.isEmpty()){ %>
		<p class="error-message"><%=errorMessage%></p>
		<% } %>

      	<form
			action="/BlackTieGraphics/AddProductServlet"
			method="post"
			enctype="multipart/form-data">
			<div class="container-5">
			<span class="register"> Add Product </span>
			</div>

			<div class="container-9">
			<div class="line-1"></div>
			</div>

			<div class="container">
				<div class="row">
					<div class="col">
						<input type="text" id="productName" name="productName" required placeholder="Enter the Product Name"/>
					</div>
					<div class="lastname">
						<input type="text" id="brand" name="brand" required placeholder="Enter the Product Brand"/>
					</div>
				</div>

				<div class="row">
					<div class="col">
						<input type="text" id="unitPrice" name="unitPrice" required placeholder="Enter the Unit Price"/>
					</div>
					<div class="col">
						<input type="text" id="stockQuantity" name="stockQuantity" required placeholder="Enter the Stock Quantity"/>
					</div>
				</div>

				<div class="row">
					<div class="col">
						<textarea rows="7" cols="50" maxlength="255" id="productDescription" name="productDescription" placeholder="Enter the Product Description" required></textarea>
					</div>
				</div>

				<div class="row">
					<div class="col">
						<label for="image">
							Product Image
						</label>
						<input type="file" id="image" name="image"/>
					</div>
				</div>
			</div>
			<br/>
			<center>
				<button type="submit" class="btn_submit">
					Add
				</button>
			</center>
      	</form>
      	<br/>
		<center>
			<form action="/BlackTieGraphics/DashboardServlet" method="get">
				<button class="home-button">Dashboard Page</button>
			</form>
		</center>
    </div>
</body>
</html>
