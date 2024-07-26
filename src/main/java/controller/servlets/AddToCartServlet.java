package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import util.StringUtils;

@WebServlet(asyncSupported = true, urlPatterns = { "/AddToCartServlet" })
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final DatabaseController dbController;

    public AddToCartServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession userSession = request.getSession(false);
		String sessionUsername = (String) userSession.getAttribute("username");
		
		String productId = request.getParameter(StringUtils.PRODUCT_ID);
		int productIdInt = Integer.parseInt(productId);
		
		String orderQuantity = request.getParameter(StringUtils.ORDER_QUANTITY);
		int orderQuantityInt = Integer.parseInt(orderQuantity);
		
		//adding the Object to database
		
		int result = dbController.addToCart(sessionUsername, productIdInt, orderQuantityInt);
			
		if (result == 1) {
			System.out.println("inside if add to cart servlet");
			request.setAttribute("errorMessage", StringUtils.PRODUCT_ADD_SUCCESS_MESSAGE);
			response.sendRedirect(request.getContextPath() + "/GetCartInfoServlet");
		}else if (result == -2) {
			System.out.println("inside else if add to cart servlet");
			request.setAttribute("errorMessage", StringUtils.PRODUCT_EXISTS_ERROR_MESSAGE);
			request.getRequestDispatcher("/HomePageServlet").forward(request, response);
		}else{
			System.out.println("inside else add to cart servlet");
			request.setAttribute("errorMessage", StringUtils.SERVER_ERROR_MESSAGE);
			request.getRequestDispatcher("/HomePageServlet").forward(request, response);
		}
	}
	
}

