package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import model.ProductModel;


@WebServlet(asyncSupported = true, urlPatterns = { "/ProductPageServlet" })
public class ProductPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	private final DatabaseController dbController;
	
    public ProductPageServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String viewId = request.getParameter("productId");
		int productId = Integer.parseInt(viewId);
		ProductModel productModel = dbController.getProductInfo(productId);
		request.setAttribute("product", productModel);	
		request.getRequestDispatcher("pages/product.jsp").forward(request, response);
	}

}
