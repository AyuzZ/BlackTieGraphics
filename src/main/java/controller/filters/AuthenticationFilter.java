package controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.StringUtils;

@WebFilter("/*") 
public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Cast the request and response to HttpServletRequest and HttpServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		// Retrive the current session if it exists
		HttpSession session = req.getSession(false);

		// Check if user is logged in
		// if sessions is not null and has username - it's logged in
		boolean isLoggedIn = (session != null) && (session.getAttribute(StringUtils.USER_NAME) != null);
		

		// Get the requested URI
		String uri = req.getRequestURI();

		//allows css to pass through without checks
		if (uri.endsWith(".css")
				|| (uri.contains("/resources")) ){
			chain.doFilter(request, response);
			return;
		}

		//can be accessed without login
		boolean isLogin = uri.endsWith(StringUtils.LOGIN_PAGE);
		boolean isLoginServlet = uri.endsWith(StringUtils.LOGIN_SERVLET);
		
		boolean isRegister = uri.endsWith(StringUtils.REGISTER_PAGE);
		boolean isRegisterServlet = uri.endsWith(StringUtils.REGISTER_SERVLET);
		
		boolean isHome = uri.endsWith(StringUtils.INDEX_PAGE);
		boolean isHomePageServlet = uri.endsWith("/HomePageServlet");
		boolean isSearchServlet = uri.endsWith("/SearchServlet");
		
		boolean isProductPageServlet = uri.contains("/ProductPageServlet");
		
		boolean isAboutUs = uri.endsWith("/aboutUs.jsp");
		
		//can only be accessed by admin
		boolean isDashboardServlet = uri.endsWith("/DashboardServlet");
		boolean isAddProductServlet = uri.endsWith("/AddProductServlet");
		boolean isUpdateProductServlet = uri.contains("/UpdateProductServlet");
		boolean isUpdateOrderStatusServlet = uri.contains("/UpdateOrderStatusServlet");
		
		//protect access nadine
		if (!isLoggedIn && !(isLogin || isLoginServlet 
				|| isRegister || isRegisterServlet 
				|| isHome || isHomePageServlet || isSearchServlet
				|| isProductPageServlet || isAboutUs)) {
			res.sendRedirect(req.getContextPath() + StringUtils.LOGIN_PAGE);
		}//logged in vako le cannot got to login or register again
		else if(isLoggedIn){
			boolean isAdmin = session.getAttribute(StringUtils.ROLE).equals("admin");
			boolean isCustomer = session.getAttribute(StringUtils.ROLE).equals("customer");
			if(isLogin || isLoginServlet 
					|| isRegister || isRegisterServlet) {
				if(isCustomer) {
					res.sendRedirect(req.getContextPath() + "/HomePageServlet");
				}else {
					res.sendRedirect(req.getContextPath() + "/DashboardServlet");
				}
			}
//			if(!isAdmin && (isDashboardServlet || isAddProductServlet || isUpdateProductServlet || isUpdateOrderStatusServlet)) {
//				res.sendRedirect(req.getContextPath() + "/HomePageServlet");
//			}
//			if(isAdmin && !(isDashboardServlet || isAddProductServlet || isUpdateProductServlet || isUpdateOrderStatusServlet)) {
//				res.sendRedirect(req.getContextPath() + "/DashboardServlet");
//			}
			else {
				chain.doFilter(request, response);
			}
				
		}
		else {
			chain.doFilter(request, response);
		}
	
	}

	@Override
	public void destroy() {
	}
}