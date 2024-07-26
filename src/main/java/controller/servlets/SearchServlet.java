package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import model.ProductModel;
import util.StringUtils;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/SearchServlet" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DatabaseController dbController;

    public SearchServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String searchedProductName = null;
		int searchedProductPrice = 0;

		boolean searchCriteria = false;
		if(request.getParameter("searchedProductName") != null && !request.getParameter("searchedProductName").isEmpty()) {
			System.out.println("here name not empty");
			searchedProductName = request.getParameter("searchedProductName");
			searchCriteria = true;
		}
		if(request.getParameter("searchedProductPrice") != null && !request.getParameter("searchedProductPrice").isEmpty()) {
			System.out.println("here price not empty");
			searchedProductPrice = Integer.parseInt(request.getParameter("searchedProductPrice"));
			searchCriteria = true;
		}
		
		if(searchCriteria) {
			System.out.println("here search criteria true");
			List<ProductModel> productsList = dbController.getSearchedProductsInfo(searchedProductName, searchedProductPrice);
			request.setAttribute("productList", productsList);
			request.getRequestDispatcher("pages/index.jsp").forward(request, response);
		}else {
			System.out.println("here search criteria false");
			request.setAttribute("errorMessage", StringUtils.SEARCH_EMPTY_ERROR_MESSAGE);
			request.getRequestDispatcher("/HomePageServlet").forward(request, response);
		}
		
		
	}

}
