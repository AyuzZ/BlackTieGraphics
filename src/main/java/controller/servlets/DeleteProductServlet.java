package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import util.StringUtils;

@WebServlet(asyncSupported = true, urlPatterns = { "/DeleteProductServlet" })

public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DatabaseController dbController;

    public DeleteProductServlet() {
    	this.dbController = new DatabaseController();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String deleteId = request.getParameter("deleteId");
		if (deleteId != null && !deleteId.isEmpty()) {
			doDelete(request, response);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("delete triggered");
		int deleteId = Integer.parseInt(request.getParameter("deleteId"));
		int result = dbController.deleteProduct(deleteId);
		if (result == 1) {
			request.setAttribute("errorMessage", StringUtils.PRODUCT_DELETE_SUCCESS_MESSAGE);
			request.getRequestDispatcher("/DashboardServlet").forward(request, response);
		} else {
			request.setAttribute("errorMessage", StringUtils.PRODUCT_DELETE_ERROR_MESSAGE);
			request.getRequestDispatcher("/DashboardServlet").forward(request, response);
		}
	}
}