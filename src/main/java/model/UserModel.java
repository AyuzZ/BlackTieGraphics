package model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;

import javax.servlet.http.Part;

import util.StringUtils;


public class UserModel implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String firstName;
		private String lastName;
		private String address;
		private LocalDate dob;
		private String gender;
		private String email;
		private String phoneNumber;
		private String username;
		private String password;
		private String role;
		private String imageUrlFromPart;
		
		public UserModel() {}
		
		public UserModel(String firstName, String lastName, String address, LocalDate dob, String gender, String email,
				String phoneNumber, String username, String password, String role,  Part imagePart) {
			
			this.firstName = firstName;
			this.lastName = lastName;
			this.dob = dob;
			this.gender = gender;
			this.email = email;
			this.phoneNumber = phoneNumber;
			this.username = username;
			this.password = password;
			this.role = role;
			this.imageUrlFromPart = getImageUrl(imagePart);
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}
		public String getAddress() {
			return address;
		}

		public LocalDate getDob() {
			return dob;
		}

		public String getGender() {
			return gender;
		}

		public String getEmail() {
			return email;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public String getRole() {
			return role;
		}

		public String getImageUrlFromPart() {
			return imageUrlFromPart;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		public void setAddress(String address) {
			this.address = address;
		}

		public void setDob(LocalDate dob) {
			this.dob = dob;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setRole(String role) {
			this.role = role;
		}
		
		public void setImageUrlFromPart(String imageUrlFromPart) {
			this.imageUrlFromPart = imageUrlFromPart;
		}
		
		public void setImageUrlFromPart(Part imagePart) {
			this.imageUrlFromPart = getImageUrl(imagePart);
		}

		private String getImageUrl(Part part) {
			String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_USER;
			File fileSaveDir = new File(savePath);
			String imageUrlFromPart = null;
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}
			String contentDisp = part.getHeader("content-disposition");
			String[] items = contentDisp.split(";");
			for (String s : items) {
				if (s.trim().startsWith("filename")) {
					imageUrlFromPart = s.substring(s.indexOf("=") + 2, s.length() - 1);
				}
			}
			if (imageUrlFromPart == null || imageUrlFromPart.isEmpty()) {
				imageUrlFromPart = "download.jpg";
			}
			return imageUrlFromPart;
		}
}
