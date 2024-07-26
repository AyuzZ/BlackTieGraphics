package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import model.OrderModel;
import util.StringUtils;

/**
 * Servlet implementation class UpdateOrderStatusServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/UpdateOrderStatusServlet" })
public class UpdateOrderStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final DatabaseController dbController;
	
    public UpdateOrderStatusServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String updateId = request.getParameter("updateId");
		int orderId = Integer.parseInt(updateId);
		OrderModel orderModel = dbController.getOrderStatus(orderId);
		request.setAttribute("order", orderModel);	
		request.getRequestDispatcher("pages/updateOrderStatus.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String orderId = request.getParameter(StringUtils.ORDER_ID);
		int orderIdInt = Integer.parseInt(orderId);
		String status = request.getParameter(StringUtils.STATUS);
		
		OrderModel orderModel = new OrderModel();
		orderModel.setOrderId(orderIdInt);
		orderModel.setStatus(status);
		
		int result = dbController.updateOrderStatus(orderModel);
		
		if (result == 1) {
			request.setAttribute("errorMessage", StringUtils.STATUS_UPDATE_SUCCESS_MESSAGE);
			response.sendRedirect(request.getContextPath() + "/DashboardServlet");
		}else{
			request.setAttribute("errorMessage", StringUtils.SERVER_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.UPDATE_ORDER_STATUS_PAGE).forward(request, response);
		}
	}

}
