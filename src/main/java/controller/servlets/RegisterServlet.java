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
import model.RegisterModel;
import util.StringUtils;
import util.ValidationUtils;

@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.REGISTER_SERVLET })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB 
//- threshold le given size ko multiple chunks ma divide garxa - if file size is 5 - 2mb, 2mb, 1mb ko chunk banxa
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final DatabaseController dbController;

    public RegisterServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter(StringUtils.USER_NAME);
		String firstName = request.getParameter(StringUtils.FIRST_NAME);
		String lastName = request.getParameter(StringUtils.LAST_NAME);
		String email = request.getParameter(StringUtils.EMAIL);
		String password = request.getParameter(StringUtils.PASSWORD);
		String retypePassword = request.getParameter(StringUtils.RETYPE_PASSWORD);
		Part imagePart = request.getPart("image");
		
		//input validation
		if (username.length() < 6) {
			request.setAttribute("errorMessage", StringUtils.USERNAME_LENGTH_ERROR);
			request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
		}else if(ValidationUtils.isNotAlphaNumeric(username)) {
			request.setAttribute("errorMessage", StringUtils.USERNAME_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
		}else if (ValidationUtils.isNotText(firstName)) {
			request.setAttribute("errorMessage", StringUtils.FIRST_NAME_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
		}else if (ValidationUtils.isNotText(lastName)) {
			request.setAttribute("errorMessage", StringUtils.LAST_NAME_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);	
		}else if (!retypePassword.equals(password)) {
			request.setAttribute("errorMessage", StringUtils.PASSWORD_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);	
		}else {
			//creating new Object
			RegisterModel registerModel = new RegisterModel(firstName, lastName, username, 
					password, email, imagePart);
			
			
		    String fileName = registerModel.getImageUrlFromPart();
		    if(!fileName.isEmpty() && fileName != null) {
		    	String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_USER;
	    		imagePart.write(savePath + fileName);
		    }
			//registering / adding the Object to database
			int result = dbController.registerUser(registerModel);
			
			if (result == 1) { //might need to change this so it shows up on login page - maybe use cookies
				request.setAttribute("errorMessage", StringUtils.REGISTER_SUCCESS_MESSAGE);
				response.sendRedirect(request.getContextPath() + StringUtils.LOGIN_PAGE);
			}else if (result == -2) {
				request.setAttribute("errorMessage", StringUtils.USERNAME_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
			}else if (result == -3) {
				request.setAttribute("errorMessage", StringUtils.EMAIL_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
			}else{
				request.setAttribute("errorMessage", StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.REGISTER_PAGE).forward(request, response);
			}
		}
	}
}
