package controller.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import util.StringUtils;

@WebServlet(asyncSupported = true, urlPatterns = { "/PlaceOrderServlet" })
public class PlaceOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final DatabaseController dbController;
	
    public PlaceOrderServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		HttpSession userSession = request.getSession(false);
		String sessionUsername = (String) userSession.getAttribute("username");
		LocalDate orderDate = LocalDate.now();
		
		int result = dbController.placeOrder(sessionUsername, orderDate);
		
		if (result == 1) { 
			request.setAttribute("errorMessage", StringUtils.UPDATE_SUCCESS_MESSAGE);
			response.sendRedirect(request.getContextPath() + "/GetOrderInfoServlet");
		}else{
			request.setAttribute("errorMessage", StringUtils.SERVER_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.INDEX_PAGE).forward(request, response);
		}
	
	}

}
