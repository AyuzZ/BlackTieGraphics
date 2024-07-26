package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import model.CartPageModel;
import util.StringUtils;

@WebServlet(asyncSupported = true, urlPatterns = { "/GetCartInfoServlet" })
public class GetCartInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final DatabaseController dbController;

    public GetCartInfoServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession userSession = request.getSession(false);
		String sessionUsername = (String) userSession.getAttribute("username");
	
		// Check if user is logged in
		// if sessions is not null and has username - it's logged in
		boolean isLoggedIn = (userSession != null) && (sessionUsername != null);
		if (isLoggedIn) {

			List<CartPageModel> cartList = dbController.getCartItems(sessionUsername);
			request.setAttribute("cartList", cartList);
			request.getRequestDispatcher("pages/cartPage.jsp").forward(request, response);
		}else {
			// Get the referer URL - url of the page from which the request was sent
	        String referer = request.getHeader("referer");

	        if (referer != null && !referer.isEmpty()) {
	        	String refererPage = "";
	        	if (referer.endsWith("/pages/aboutUs.jsp")) {
	        		refererPage = "/pages/aboutUs.jsp";
	        	}else {
	        		//couldn't find how to separate between different product pages 
	        		//so they will all redirect to home page instead
	        		refererPage = "/HomePageServlet";
	        	}
	        	request.setAttribute("errorMessage", StringUtils.LOG_IN_TO_VIEW_CART_MESSAGE);
				request.getRequestDispatcher(refererPage).forward(request, response);
	        }//if referer url is empty, also send to home page
	        else {
	        	request.setAttribute("errorMessage", StringUtils.LOG_IN_TO_VIEW_CART_MESSAGE);
				request.getRequestDispatcher("/HomePageServlet").forward(request, response);
	        }
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
