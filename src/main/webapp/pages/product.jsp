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

<body style="background:#eee">
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
	
	<!--LearnMore Body Starts-->
  <div class="learnmore" style="padding-top: 6%;">
    <!--Single Product Box Starts-->
    <div class="col-4">
      <!--Single Product Image-->
      <div id="img">
        <img id=featured src="${pageContext.request.contextPath }/resources/images/products/${product.imageUrlFromPart}">
      </div> 
      <!--Single Product Desciption Starts-->
      <div class="aboutproduct" style="margin: 25px">
        <!--Single Product Heading-->
        <h1 style="margin-bottom: 4px; font-size: 30px;">${product.productName} ${product.brand}</h1>
        <!--Single Product Rating-->
        <span class="fa fa-star checked"></span>
        <span class="fa fa-star checked"></span>
        <span class="fa fa-star checked"></span>
        <span class="fa fa-star"></span>
        <span class="fa fa-star"></span>
        <!--Signle Product Detail Heading-->
        <h2 style="margin-top: 10px; margin-bottom: 10px;">Product Details</h2>
        <!--Single Product Detail Description-->
        <p style="line-height: 1.5; margin-bottom: 30px;">
          ${product.productDescription}
        </p>
        <!--Single Product Amount-->
        <div style="display: flex; margin-bottom: 10px;">
          <div class="quantity">
            <button class="minus" onclick="minusFunction()">-</button>
            <input class="amount" type="text" value="0" id="number" />
            <button class="plus" onclick="plusFunction()">+</button><br>
          </div>
          
          

          <!--Single Product Price-->
          <div class="price" style="margin-left:20px; margin-top: 3px; margin-bottom: 10px; font-size: 20px; color:#334;">
            ${product.unitPrice}
          </div>
        </div>

        <!--Button To Buy Product And Add To Cart-->
        <div class="buyproduct">
          <%
          // Conditionally set the action URL based on user session
          if (userSession != null) {
            %>
          <form method="post" action="${pageContext.request.contextPath}/AddToCartServlet" onsubmit="return getOrderQuantity();">
          <input type="hidden"  name="productId" value="${product.productId}" />
            
          <input type="hidden" id="orderQuantity" name="orderQuantity" />
          <button class="button btn-1" type="submit">Add to Cart</button>
          <!--  <a href="#"><button onclick="cart()" class="button btn-1">Add To Cart</button></a>-->
          </form>
          <%
            } else {
          %>
            <form method="post" action="${pageContext.request.contextPath}${StringUtils.LOGIN_PAGE}">
            <button class="button btn-1" type="submit">Log In</button>
            </form>
                <%
            }
          %>
        </div>
      </div>
      <!--Single Product Desciption Ends-->

    </div>
    <!--Single Product Box Ends-->
	</div>
	<jsp:include page="<%=StringUtils.FOOTER_PAGE%>" />
</body>
<!--JavaScript For Quantity Starts-->
<script type="text/javascript">
    function plusFunction()
    {
      var value = parseInt(document.getElementById('number').value);
      value++;
      if(value<=10){
        document.getElementById('number').value = value;
      }
    }

    function minusFunction(){
      var value = parseInt(document.getElementById('number').value);               
      if(value>0){
        value--;
        document.getElementById('number').value = value;
      } 
    }
    function getOrderQuantity(){
		var orderQty = document.getElementById('number').value;
		
		if (orderQty === "" || isNaN(orderQty) || parseInt(orderQty) <= 0) {
		     alert("Please enter a valid quantity.");
		     return false; // Prevent form submission
		   }
		document.getElementById('orderQuantity').value = orderQty;
		return true;
	}
        </script>
<!--JavaScript For Quantity Ends-->
</html>