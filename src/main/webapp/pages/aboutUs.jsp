<%@page import="util.StringUtils"%> <%@ page language="java"
contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1" />
<title>About Us Page</title>
<link rel="stylesheet" type="text/css" href="/BlackTieGraphics/stylesheets/addProduct.css"/>
</head>

<body>
	<jsp:include page="<%=StringUtils.HEADER_PAGE%>"/>

    <div class="mainContainer">
		<% String errorMessage =(String)
		request.getAttribute(StringUtils.ERROR_MESSAGE); if (errorMessage != null
		&& !errorMessage.isEmpty()){ %>
			<p class="error-message"><%=errorMessage%></p>
		<% } %>

		<form action="/BlackTieGraphics/AboutUsServlet" method="post">
			<div class="container-5">
				<span class="register">
					Send Message
				</span>
			</div>

			<div class="container-9">
				<div class="line-1">
				</div>
			</div>

			<div class="container">
				<div class="row">
					<div class="col">
						<textarea rows="7" cols="50" maxlength="255" id="userMessage" name="userMessage" required placeholder="Enter your message"></textarea>
					</div>
				</div>
			</div>

			<br/>
			<center>
				<button type="submit" class="btn_submit">Submit</button>
			</center>
      	</form>
    </div>

    <jsp:include page="<%=StringUtils.FOOTER_PAGE%>" />
</body>
</html>
