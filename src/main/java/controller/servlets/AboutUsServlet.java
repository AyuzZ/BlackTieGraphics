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


@WebServlet(asyncSupported = true, urlPatterns = { "/AboutUsServlet" })
public class AboutUsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DatabaseController dbController;
	
    public AboutUsServlet() {
        this.dbController = new DatabaseController();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		//Retrive the current session if it exists
		HttpSession userSession = request.getSession(false);
		String sessionUsername = (String) userSession.getAttribute("username");
		
		// Check if user is logged in
		// if sessions is not null and has username - it's logged in
		boolean isLoggedIn = (userSession != null) && (sessionUsername != null);
		
		if (isLoggedIn) {
			String userMessage = request.getParameter("userMessage");
			int result = dbController.storeUserMessage(sessionUsername, userMessage);
			if (result == 1) { //might need to change this so it shows up on login page - maybe use cookies
				request.setAttribute("errorMessage", StringUtils.MESSAGE_SENT_SUCCESS_MESSAGE);
				request.getRequestDispatcher(StringUtils.ABOUT_US_PAGE).forward(request, response);
			}else {
				request.setAttribute("errorMessage", StringUtils.MESSAGE_SENT_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ABOUT_US_PAGE).forward(request, response);
			}
		}else {
			request.setAttribute("errorMessage", StringUtils.NEED_TO_LOG_IN_MESSAGE);
			request.getRequestDispatcher(StringUtils.ABOUT_US_PAGE).forward(request, response);
		}
	}
}
