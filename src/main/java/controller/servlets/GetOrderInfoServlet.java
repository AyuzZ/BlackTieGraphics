package controller.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import model.OrderPageModel;

@WebServlet(asyncSupported = true, urlPatterns = { "/GetOrderInfoServlet" })
public class GetOrderInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final DatabaseController dbController;
	
    public GetOrderInfoServlet() {
    	this.dbController = new DatabaseController();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession userSession = request.getSession(false);
		String sessionUsername = (String) userSession.getAttribute("username");
		LocalDate orderDate = LocalDate.now();
		
		System.out.println("in getorderinfoservlet+ orderDate"+ orderDate);
		
		List<OrderPageModel> orderList = dbController.getOrderItems(sessionUsername, orderDate);
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("pages/orderPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
