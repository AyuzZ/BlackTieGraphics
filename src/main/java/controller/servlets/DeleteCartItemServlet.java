package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import util.StringUtils;

@WebServlet(asyncSupported = true, urlPatterns = { "/DeleteCartItemServlet" })
public class DeleteCartItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final DatabaseController dbController;
    
    public DeleteCartItemServlet() {
        this.dbController = new DatabaseController();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deleteProductId = request.getParameter("deleteProductId");
		String deleteCartId = request.getParameter("deleteCartId");
		if (deleteProductId != null && !deleteProductId.isEmpty() 
				&& deleteCartId != null && !deleteCartId.isEmpty()) {
			doDelete(request, response);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("delete triggered");
		int deleteCartId = Integer.parseInt(request.getParameter("deleteCartId"));
		int deleteProductId = Integer.parseInt(request.getParameter("deleteProductId"));
		int result = dbController.deleteCartItem(deleteCartId, deleteProductId);
		
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