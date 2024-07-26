package controller.servlets;

import controller.database.DatabaseController;
import model.LoginModel;
import util.StringUtils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.LOGIN_SERVLET })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DatabaseController dbController;
	
    public LoginServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter(StringUtils.USER_NAME);
        String password = request.getParameter(StringUtils.PASSWORD);

        LoginModel loginModel = new LoginModel(username, password);
        
        int loginResult =dbController.getLoginInfo(loginModel);

        if (loginResult == 1){ //customer
            HttpSession userSession = request.getSession();
            userSession.setAttribute(StringUtils.USER_NAME,username);
            userSession.setAttribute(StringUtils.ROLE,"customer");
            userSession.setMaxInactiveInterval(30*60);

            Cookie userCookie = new Cookie (StringUtils.USER,username);
            userCookie.setMaxAge(30*60);
            response.addCookie(userCookie);

            request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.LOGIN_SUCCESS_MESSAGE);
            response.sendRedirect(request.getContextPath() + "/HomePageServlet");

        }else if (loginResult == 2){ //admin
            HttpSession userSession = request.getSession();
            userSession.setAttribute(StringUtils.USER_NAME,username);
            userSession.setAttribute(StringUtils.ROLE,"admin");
            userSession.setMaxInactiveInterval(30*60);

            Cookie userCookie = new Cookie (StringUtils.USER,username);
            userCookie.setMaxAge(30*60);
            response.addCookie(userCookie);

            request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.LOGIN_SUCCESS_MESSAGE);
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");

        }else if (loginResult== 0){
            request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.PASSWORD_ERROR_MESSAGE);
            request.setAttribute(StringUtils.USER_NAME, username);
            request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request,response);

        }else if (loginResult== -2){
            request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.USERNAME_NOT_FOUND_MESSAGE);
            request.setAttribute(StringUtils.USER_NAME, username);
            request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request,response);

        }else{
            request.setAttribute(StringUtils.ERROR_MESSAGE,StringUtils.SERVER_ERROR_MESSAGE);
            request.setAttribute(StringUtils.USER_NAME, username);
            request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request,response);
        }
	}

}
