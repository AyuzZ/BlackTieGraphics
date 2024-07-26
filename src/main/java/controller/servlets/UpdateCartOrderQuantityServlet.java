package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import util.StringUtils;

/**
 * Servlet implementation class UpdateCartOrderQuantityServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/UpdateCartOrderQuantityServlet" })
public class UpdateCartOrderQuantityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DatabaseController dbController;
	
    public UpdateCartOrderQuantityServlet() {
    	this.dbController = new DatabaseController();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int updateCartId = Integer.parseInt(request.getParameter("updateCartId"));
		int updateProductId = Integer.parseInt(request.getParameter("updateProductId"));
		int orderQuantity = Integer.parseInt(request.getParameter("orderQuantity"));
		int result = dbController.updateCartOrderQuantity(updateCartId, updateProductId, orderQuantity);
		
		if (result == 1) {
			System.out.println("delete succesful");
			request.setAttribute("errorMessage", StringUtils.CART_ITEM_DELETE_SUCCESS_MESSAGE);
			request.getRequestDispatcher("/GetCartInfoServlet").forward(request, response);
		} else {
			request.setAttribute("errorMessage", StringUtils.CART_ITEM_DELETE_ERROR_MESSAGE);
			request.getRequestDispatcher("/GetCartInfoServlet").forward(request, response);
		}
	}

}
