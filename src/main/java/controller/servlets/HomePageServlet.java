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

@WebServlet(asyncSupported = true, urlPatterns = { "/HomePageServlet" })
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DatabaseController dbController;
	
    public HomePageServlet() {
		this.dbController = new DatabaseController();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
		List<ProductModel> productsList = dbController.getAllProductsInfo();
		request.setAttribute("productList", productsList);
		
		request.getRequestDispatcher("pages/index.jsp").forward(request, response);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
