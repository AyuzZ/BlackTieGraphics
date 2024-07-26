<%@page import="util.StringUtils"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>

<%
    // Get the session and request objects
    HttpSession userSession = request.getSession();
    String sessionUser = (String) userSession.getAttribute(StringUtils.USER_NAME);
    String contextPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css"
	href="<%=contextPath %>/stylesheets/header.css" />
<nav>
     <div class="navbar">
        <div class="navLogo">
           <a href ="/BlackTieGraphics/HomePageServlet"><img src="${pageContext.request.contextPath }/resources/images/Logo.jpg"  width="50" alt=""></a>
        </div>

        <!-- table tag for links in the navigation bar-->
        <table>
           <td><a href="/BlackTieGraphics/HomePageServlet" class="navLinks">Home</a></td>
           <td><a href="${pageContext.request.contextPath }${StringUtils.ABOUT_US_PAGE}" class="navLinks">About Us</a></td>
        </table>
       
        <!-- Search Function -->
        <form class="search" action="/BlackTieGraphics/SearchServlet" method="post">     
            <input class="search-input" type="search" id="searchedProductName" name="searchedProductName" placeholder="Search Name">
            <input class="search-input" type="number" id="searchedProductPrice" name="searchedProductPrice" placeholder="Search Price Range">
            <input type="image" src="${pageContext.request.contextPath }/resources/images/search.png"  style="width: 9%; margin-right: 20px;" alt="submit" /> 
        </form>
       
       <!-- code for the two icons in the right side of the navigation bar-->
        <div class="topRight">
            <button onclick="cart()" style="background-color: #575158; border: none">
                <a href="/BlackTieGraphics/GetCartInfoServlet">
                <i class ="cart-icon">
                <img src="${pageContext.request.contextPath }/resources/images/cart.png" alt="Cart" style="width: 30px;">
                </i>
                </a>
            </button>
    
            <!-- show logout or login -->
            <form action="<% // Conditionally set the action URL based on user session
                if (sessionUser != null) {
                    out.print("/BlackTieGraphics/LogoutServlet");
                } else {
                    out.print(contextPath + StringUtils.LOGIN_PAGE);
                }
                    %>" method="post">
                <input type="submit" value="<% // Conditionally set the button label based on user session
                    if (sessionUser != null) {
                        out.print("Logout");
                    } else {
                        out.print("Login");
                    }
                    %>"/>
            </form>
                    
            <!-- show register or profile page -->
            <form action="<%
                // Conditionally set the action URL based on user session
                if (sessionUser != null) {
                    out.print("/BlackTieGraphics/UserProfileServlet");
                } else {
                    out.print(contextPath + StringUtils.REGISTER_PAGE);
                }
                %>" method="post">
                <input type="submit" value="<%
                    // Conditionally set the button label based on user session
                    if (sessionUser != null) {
                        out.print("Profile Page");
                    } else {
                        out.print("Register");
                    }
                %>"/>
            </form>    
        </div>
    </div>
</nav>
