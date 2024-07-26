package controller.servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.database.DatabaseController;
import model.UserModel;
import util.StringUtils;
import util.ValidationUtils;

@WebServlet(asyncSupported = true, urlPatterns = { "/UpdateUserServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB 
//- threshold le given size ko multiple chunks ma divide garxa - if file size is 5 - 2mb, 2mb, 1mb ko chunk banxa
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DatabaseController dbController;

    public UpdateUserServlet() {
    	this.dbController = new DatabaseController();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String updateUsername = request.getParameter("updateUsername");
		UserModel userModel = dbController.getUserInfo(updateUsername);
		request.setAttribute("user", userModel);	
		request.getRequestDispatcher("pages/updateUser.jsp").forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//username and password aile change garna namilne banaune
		String username = request.getParameter(StringUtils.USER_NAME);
		String firstName = request.getParameter(StringUtils.FIRST_NAME);
		String lastName = request.getParameter(StringUtils.LAST_NAME);
		String email = request.getParameter(StringUtils.EMAIL);
		String phoneNumber = request.getParameter(StringUtils.PHONE_NUMBER);
		String gender = request.getParameter(StringUtils.GENDER);
		String dobString = request.getParameter(StringUtils.DOB);
		LocalDate dob = null;
		if(dobString != null && !dobString.isEmpty()) {
			dob = LocalDate.parse(dobString);
		}
		String address = request.getParameter(StringUtils.ADDRESS);
		Part imagePart = request.getPart("image");
		
		UserModel modelBeforeValidation = new UserModel(firstName, lastName, address, dob, 
				gender, email, phoneNumber, username, null, null, imagePart);
		
		//input validation
		if (ValidationUtils.isNotText(firstName)) {
			request.setAttribute("user", modelBeforeValidation);
			request.setAttribute("errorMessage", StringUtils.FIRST_NAME_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);
		}else if (ValidationUtils.isNotText(lastName)) {
			request.setAttribute("user", modelBeforeValidation);
			request.setAttribute("errorMessage", StringUtils.LAST_NAME_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);	
		}else if (!ValidationUtils.isEmail(email)) {
			request.setAttribute("user", modelBeforeValidation);
			request.setAttribute("errorMessage", StringUtils.EMAIL_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);	
		}else if (!phoneNumber.isEmpty() && phoneNumber != null && ValidationUtils.isNotNumeric(phoneNumber)) {
			request.setAttribute("user", modelBeforeValidation);
			request.setAttribute("errorMessage", StringUtils.PHONE_NUMBER_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);	
		}else if(!phoneNumber.isEmpty() && phoneNumber != null && phoneNumber.length() != 10) {
			request.setAttribute("user", modelBeforeValidation);
			request.setAttribute("errorMessage", StringUtils.PHONE_NUMBER_LENGTH_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);
		}else if(!gender.isEmpty() && gender != null && ValidationUtils.isNotText(gender)) {
			request.setAttribute("user", modelBeforeValidation);
			request.setAttribute("errorMessage", StringUtils.GENDER_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);	
		}else if(!address.isEmpty() && address != null && ValidationUtils.isNotAlphaNumericAndBlankSpaces(address)) {
			request.setAttribute("user", modelBeforeValidation);
			request.setAttribute("errorMessage", StringUtils.ADDRESS_CONTENT_ERROR);
			request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);	
		}

		else {
			//creating new Object
			UserModel userModel = new UserModel();

			//call setters
			userModel.setUsername(username);
			userModel.setFirstName(firstName);
			userModel.setLastName(lastName);
			userModel.setEmail(email);
			userModel.setImageUrlFromPart(imagePart);
			if (!phoneNumber.isEmpty() && phoneNumber != null) {
				userModel.setPhoneNumber(phoneNumber);
			}
			if(!gender.isEmpty() && gender != null) {
				userModel.setGender(gender);
			}
			if(!address.isEmpty() && address != null) {
				userModel.setAddress(address);
			}
			if(dob != null) {
				userModel.setDob(dob);
			}
			String fileName = userModel.getImageUrlFromPart();
		    if(!fileName.isEmpty() && fileName != null) {
		    	String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_USER;
		    	System.out.println(savePath + fileName);
	    		imagePart.write(savePath + fileName);
		    }
		   
			//registering / adding the Object to database
			int result = dbController.updateUser(userModel);
			
			if (result == 1) { 
				request.setAttribute("errorMessage", StringUtils.UPDATE_SUCCESS_MESSAGE);
				response.sendRedirect(request.getContextPath() + "/UserProfileServlet");
			}else if (result == -4) {
				request.setAttribute("errorMessage", StringUtils.PHONE_NUMBER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);
			}else if (result == -3) {
				request.setAttribute("errorMessage", StringUtils.EMAIL_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);
			}else{
				request.setAttribute("errorMessage", StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_USER_PAGE).forward(request, response);
			}
		}
	}

}
