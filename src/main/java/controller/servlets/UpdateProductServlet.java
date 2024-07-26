package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.database.DatabaseController;
import model.ProductModel;
import util.StringUtils;
import util.ValidationUtils;

@WebServlet(asyncSupported = true, urlPatterns = { "/UpdateProductServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB 
//- threshold le given size ko multiple chunks ma divide garxa - if file size is 5 - 2mb, 2mb, 1mb ko chunk banxa
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class UpdateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final DatabaseController dbController;
	
    public UpdateProductServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String updateId = request.getParameter("updateId");
		int productId = Integer.parseInt(updateId);
		ProductModel productModel = dbController.getProductInfo(productId);
		request.setAttribute("product", productModel);	
		request.getRequestDispatcher("pages/updateProduct.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String productId = request.getParameter(StringUtils.PRODUCT_ID);
		String productName = request.getParameter(StringUtils.PRODUCT_NAME);
		String brand = request.getParameter(StringUtils.BRAND);
		String unitPrice = request.getParameter(StringUtils.UNIT_PRICE);
		String stockQuantity = request.getParameter(StringUtils.STOCK_QUANTITY);
		String productDescription = request.getParameter(StringUtils.PRODUCT_DESCRIPTION);
		Part imagePart = request.getPart("image");
		
		//input validation
		if(ValidationUtils.isNotAlphaNumericAndBlankSpaces(productName)) {
			request.setAttribute("errorMessage", StringUtils.PRODUCT_NAME_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
		}else if (ValidationUtils.isNotAlphaNumericAndBlankSpaces(brand)) {
			request.setAttribute("errorMessage", StringUtils.BRAND_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
		}else if (ValidationUtils.isNotFloatingPoint(unitPrice)) {
			request.setAttribute("errorMessage", StringUtils.UNIT_PRICE_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);	
		}else if (ValidationUtils.isNotNumeric(stockQuantity)) {
			request.setAttribute("errorMessage", StringUtils.STOCK_QUANTITY_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);	
		}else {
			int productIdInt = Integer.parseInt(productId);
			double UnitPriceDouble = Double.parseDouble(unitPrice);
			int stockQuantityInt = Integer.parseInt(stockQuantity);
			
			//creating new Object
			ProductModel productModel = new ProductModel(productIdInt, productName, brand, UnitPriceDouble, 
					stockQuantityInt, productDescription, imagePart);
			
			String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_PRODUCT;
		    String fileName = productModel.getImageUrlFromPart();
		    if(!fileName.isEmpty() && fileName != null)
	    		imagePart.write(savePath + fileName);
			
			//adding the Object to database
			int result = dbController.updateProduct(productModel);
			
			if (result == 1) {
				request.setAttribute("errorMessage", StringUtils.PRODUCT_ADD_SUCCESS_MESSAGE);
				response.sendRedirect(request.getContextPath() + "/DashboardServlet");
			}else if (result == -2) {
				request.setAttribute("errorMessage", StringUtils.PRODUCT_EXISTS_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
			}else{
				request.setAttribute("errorMessage", StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
			}
		}
	}

}
