package controller.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.CartPageModel;
import model.LoginModel;
import model.OrderModel;
import model.OrderPageModel;
import model.PasswordEncryptionWithAes;
import model.ProductModel;
import model.RegisterModel;
import model.UserModel;
import util.StringUtils;

public class DatabaseController {
	
	/**
	 * This method establishes a connection to the database using pre-defined credentials and
	 * driver information.
	 * 
	 * @return A `Connection` object representing the established connection to the
	 *         database.
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public Connection getConnection () throws SQLException, ClassNotFoundException{
		//load driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		//connection
		String url = "jdbc:mysql://localhost:3306/black_tie_graphics"; //database url
		String user = "root"; //mysql username
		String pass = ""; //mysql pass
		return DriverManager.getConnection(url, user, pass);
	}

	/**
	 * This method attempts to register a new user in the database.
	 * 
	 * @param registerModel A 'RegisterModel' object containing the user's register information.
	 * @return An integer value indicating the registrating status:
	 * 		   - 1: Registration successful
	 * 		   - 0: Registration failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * 		   - -2: Registration failed (Username already exists)
	 * 		   - -3: Registration failed (Email already exists)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int registerUser (RegisterModel registerModel) {
		try (Connection con = getConnection()) {
			
			//username_validation - checking for existing username
			PreparedStatement checkUsernameSt = con.prepareStatement(StringUtils.GET_USERNAME);
			checkUsernameSt.setString(1, registerModel.getUsername());
			ResultSet checkUsernameRs = checkUsernameSt.executeQuery(); //returns the count of the matching username
			checkUsernameRs.next();
			if (checkUsernameRs.getInt(1) > 0) {
				return -2; //Username already exists.
			}
			
			//email_validation
			PreparedStatement checkEmailSt = con.prepareStatement(StringUtils.GET_EMAIL);
			checkEmailSt.setString(1, registerModel.getEmail());
			ResultSet checkEmailRs = checkEmailSt.executeQuery(); //returns the count of the matching email
			checkEmailRs.next();
			if (checkEmailRs.getInt(1) > 0) {
				return -3; //Email already exists.
			}
			
			String encryptedPassword = PasswordEncryptionWithAes.encrypt(registerModel.getUsername(), registerModel.getPassword());
			
			// Create a PreparedStatement for inserting a new user
			PreparedStatement st = con.prepareStatement (StringUtils.INSERT_USER);
			st.setString(1, registerModel.getUsername());
			st.setString(2, registerModel.getFirstName());
			st.setString(3, registerModel.getLastName());
			st.setString(4, registerModel.getEmail());
			st.setString(5, encryptedPassword);
			st.setString(6, registerModel.getImageUrlFromPart());
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace (); // Log the exception for debugging
			return -1;
		}
	}
	
	/**
	 * This method attempts to login a user by validating their username and password
	 * against the database.
	 * 
	 * @param loginModel A 'LoginModel' object containing the user's login information (username and password).
	 * @return An integer value indicating the login status:
	 * 		   - 1: Login successful (customer)
	 * 		   - 2: Login successful (admin)
	 * 		   - 0: Login failed (username and password mismatch)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * 		   - -2: Login failed (username doesn't exist.)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int getLoginInfo(LoginModel loginModel) {
		try(Connection con = getConnection()){
			PreparedStatement st = con.prepareStatement(StringUtils.GET_LOGIN_INFO);
			st.setString(1, loginModel.getUsername());

			ResultSet rs = st.executeQuery();
		
			if (rs.next()) {
				//User name match in the database
				String usernameDb = rs.getString(StringUtils.USER_NAME);
				String encryptedPasswordDb = rs.getString(StringUtils.PASSWORD);
				String decryptedPassword = PasswordEncryptionWithAes.decrypt(encryptedPasswordDb, usernameDb);
				
				if(decryptedPassword != null 
						&& usernameDb.equals(loginModel.getUsername()) 
						&& decryptedPassword.equals(loginModel.getPassword())) {
					if (rs.getString(StringUtils.ROLE).equals("customer")) {
						return 1; //login successful - customer
					}else {
						return 2; //login successful - admin
					}	
				}else {
					return 0; //username or pw mismatch
				}
			}else {
				//No matching record found, username doesnt exist
				return -2;
			}
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method returns user's information to display on user profile page.
	 * 
	 * @param username The username of the logged in user.
	 * @return A 'UserModel' object containing user's information.
	 * 		   - user: Successful retrieval.
	 * 		   - null: Failure to retrieve or Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public UserModel getUserInfo(String username){
		try(Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.GET_USER_INFO);
			st.setString(1, username);
			ResultSet result = st.executeQuery();
			
			if(result.next()) {
				UserModel user = new UserModel();
				
				user.setUsername(result.getString("username"));
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setEmail(result.getString("email"));
				user.setPhoneNumber(result.getString("phone_number"));
				user.setAddress(result.getString("address"));
				user.setGender(result.getString("gender"));
				user.setImageUrlFromPart(result.getString("user_image"));
				user.setDob(null);
				if(result.getString("dob") != null) {
					user.setDob(result.getDate("dob").toLocalDate());
				}
				return user;
				
			}else {
				return null;
			}
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method attemps to update user's information.
	 * 
	 * @param userModel A 'UserModel' object containing the user's all information.
	 * @return An integer value indicating the update status:
	 * 		   - 1: Update successful
	 * 		   - 0: Update failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * 		   - -3: Update failed (Email already exists)
	 * 		   - -4: Update failed (Phone Number already exists)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	//update User info
	public int updateUser(UserModel userModel) {
		try(Connection con = getConnection()){
			//email and phone numbers can match only with their data not other
			//email_validation
			PreparedStatement checkEmailSt = con.prepareStatement(StringUtils.GET_EMAIL);
			checkEmailSt.setString(1, userModel.getEmail());
			ResultSet checkEmailRs = checkEmailSt.executeQuery(); //returns the count of the matching email
			checkEmailRs.next();
			PreparedStatement checkUserEmailSt = con.prepareStatement(StringUtils.GET_EMAIL_USER);
			checkUserEmailSt.setString(1, userModel.getEmail());
			checkUserEmailSt.setString(2, userModel.getUsername());
			ResultSet checkUserEmailRs = checkUserEmailSt.executeQuery(); //returns the count of the matching email
			checkUserEmailRs.next();
			//checkUserEmailRs.getInt(1) returns 1 if the email exists for the logged in user
			if (checkEmailRs.getInt(1) > 0 && checkUserEmailRs.getInt(1) <= 0){
				return -3; //Email already exists.
			}
			
			//phoneNumber_validation
			PreparedStatement checkPhoneSt = con.prepareStatement(StringUtils.GET_PHONE_NUMBER);
			checkPhoneSt.setString(1, userModel.getPhoneNumber());
			ResultSet checkPhoneRs = checkPhoneSt.executeQuery(); //returns the count of the matching phonenumber
			checkPhoneRs.next();
			PreparedStatement checkUserPhoneSt = con.prepareStatement(StringUtils.GET_PHONE_NUMBER_USER);
			checkUserPhoneSt.setString(1, userModel.getPhoneNumber());
			checkUserPhoneSt.setString(2, userModel.getUsername());
			ResultSet checkUserPhoneRs = checkUserPhoneSt.executeQuery(); //returns the count of the matching email
			checkUserPhoneRs.next();
			if (checkPhoneRs.getInt(1) > 0 && checkUserPhoneRs.getInt(1) <= 0) {
				return -4; //Phone Number already exists.
			}
			
			// Create a PreparedStatement for inserting a new product
			PreparedStatement st = con.prepareStatement (StringUtils.UPDATE_USER);
			st.setString(1, userModel.getFirstName());
			st.setString(2, userModel.getLastName());
			st.setString(3, userModel.getPhoneNumber());
			st.setString(4, userModel.getEmail());
			st.setString(5, userModel.getGender());
			if(userModel.getDob() == null) {
				st.setDate(6, null);
			}else {
				st.setDate(6, Date.valueOf(userModel.getDob()));
			}
			st.setString(7, userModel.getAddress());
			st.setString(8, userModel.getImageUrlFromPart());
			st.setString(9, userModel.getUsername());
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method attempts to return all the products stored in the database.
	 * 
	 * @return An ArrayList containing objects of ProductModel class:
	 * 		   - products: Successfull retrieval.
	 * 	   	   - null: Failure to retrieve or Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public ArrayList<ProductModel> getAllProductsInfo(){
		try(Connection con = getConnection()){
			PreparedStatement st = con.prepareStatement(StringUtils.GET_ALL_PRODUCTS_INFO);
			ResultSet rs = st.executeQuery();
			
			ArrayList<ProductModel> products = new ArrayList<>();
			
			while(rs.next()) {
				ProductModel product = new ProductModel();
				
				product.setProductId(rs.getInt(StringUtils.PRODUCT_ID_DB));
				product.setProductName(rs.getString(StringUtils.PRODUCT_NAME_DB));
				product.setBrand(rs.getString(StringUtils.BRAND));
				product.setUnitPrice(rs.getDouble(StringUtils.UNIT_PRICE_DB));
				product.setStockQuantity(rs.getInt(StringUtils.STOCK_QUANTITY_DB));
				product.setProductDescription(rs.getString(StringUtils.PRODUCT_DESCRIPTION_DB));
				product.setImageUrlFromPart(rs.getString("product_image"));
				
				products.add(product);
			}
			return products;
		
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method returns information of one product.
	 * 
	 * @param productId
	 * @return A 'ProductModel' object containing a product's information:
	 * 		   - product: Successful retrieval
	 * 	   	   - null: Failure to retrieve or Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public ProductModel getProductInfo(int productId){
		try(Connection con = getConnection()){
			PreparedStatement st = con.prepareStatement(StringUtils.GET_PRODUCT_INFO);
			st.setInt(1, productId);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				ProductModel product = new ProductModel();
				
				product.setProductId(rs.getInt(StringUtils.PRODUCT_ID_DB));
				product.setProductName(rs.getString(StringUtils.PRODUCT_NAME_DB));
				product.setBrand(rs.getString(StringUtils.BRAND));
				product.setUnitPrice(rs.getDouble(StringUtils.UNIT_PRICE_DB));
				product.setStockQuantity(rs.getInt(StringUtils.STOCK_QUANTITY_DB));
				product.setProductDescription(rs.getString(StringUtils.PRODUCT_DESCRIPTION_DB));
				product.setImageUrlFromPart(rs.getString("product_image"));
				return product;
			}else {
				return null;
			}	
		
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method returns products that match the seach criteria given by the user. 
	 * It searches based on product name, product price or both.
	 * 
	 * @param searchedProductName
	 * @param searchedProductPrice
	 * @return An ArrayList containing objects of ProductModel class. The objects are products that fit the search criteria:
	 * 		   - products: Successfull retrieval.
	 * 	   	   - null: Failure to retrieve or Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public ArrayList<ProductModel> getSearchedProductsInfo(String searchedProductName, int searchedProductPrice){
		try(Connection con = getConnection()){
			
			ArrayList<ProductModel> products = new ArrayList<>();
			if(searchedProductName != null && searchedProductPrice == 0) {
				String searchProductNameWithWildcards = "%" + searchedProductName + "%";
				
				PreparedStatement st = con.prepareStatement(StringUtils.GET_SEARCHED_PRODUCTS_INFO_FROM_NAME);
				st.setString(1, searchProductNameWithWildcards);
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					ProductModel product = new ProductModel();
					product.setProductId(rs.getInt(StringUtils.PRODUCT_ID_DB));
					product.setProductName(rs.getString(StringUtils.PRODUCT_NAME_DB));
					product.setBrand(rs.getString(StringUtils.BRAND));
					product.setUnitPrice(rs.getDouble(StringUtils.UNIT_PRICE_DB));
					product.setStockQuantity(rs.getInt(StringUtils.STOCK_QUANTITY_DB));
					product.setImageUrlFromPart(rs.getString("product_image"));
					
					products.add(product);
				}
				return products;
			}
			if (searchedProductPrice != 0 && searchedProductName == null) {
				
				int lowerLimit = searchedProductPrice - 100;
				if (lowerLimit < 0) {
					lowerLimit = 0;
				}
				int upperLimit = searchedProductPrice + 100;
				
				PreparedStatement st = con.prepareStatement(StringUtils.GET_SEARCHED_PRODUCTS_INFO_FROM_PRICE);
				st.setInt(1, lowerLimit);
				st.setInt(2, upperLimit);
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					ProductModel product = new ProductModel();
					product.setProductId(rs.getInt(StringUtils.PRODUCT_ID_DB));
					product.setProductName(rs.getString(StringUtils.PRODUCT_NAME_DB));
					product.setBrand(rs.getString(StringUtils.BRAND));
					product.setUnitPrice(rs.getDouble(StringUtils.UNIT_PRICE_DB));
					product.setStockQuantity(rs.getInt(StringUtils.STOCK_QUANTITY_DB));
					product.setImageUrlFromPart(rs.getString("product_image"));
					
					products.add(product);
				}
				return products;
				
			}
			if ((searchedProductName != null) && (searchedProductPrice != 0)) {
				String searchProductNameWithWildcards = "%" + searchedProductName + "%";
				
				int lowerLimit = searchedProductPrice - 100;
				if (lowerLimit < 0) {
					lowerLimit = 0;
				}
				int upperLimit = searchedProductPrice + 100;
				
				PreparedStatement st = con.prepareStatement(StringUtils.GET_SEARCHED_PRODUCTS_INFO_FROM_BOTH);
				st.setString(1, searchProductNameWithWildcards);
				st.setInt(2, lowerLimit);
				st.setInt(3, upperLimit);
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					ProductModel product = new ProductModel();
					product.setProductId(rs.getInt(StringUtils.PRODUCT_ID_DB));
					product.setProductName(rs.getString(StringUtils.PRODUCT_NAME_DB));
					product.setBrand(rs.getString(StringUtils.BRAND));
					product.setUnitPrice(rs.getDouble(StringUtils.UNIT_PRICE_DB));
					product.setStockQuantity(rs.getInt(StringUtils.STOCK_QUANTITY_DB));
					product.setImageUrlFromPart(rs.getString("product_image"));
					
					products.add(product);
				}
				return products;
			}
			return null;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method attempts to insert a new product in the database.
	 * 
	 * @param productModel A 'ProductModel' object containing the product's insert information
	 * @return An integer value indicating the insertionstatus:
	 * 		   - 1: Insertion successful
	 * 		   - 0: Insetion failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * 		   - -2: Registration failed (Product already exists)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int insertProduct(ProductModel productModel) {
		try (Connection con = getConnection()) {
			
			//productId_validation - checking for existing productName
			PreparedStatement checkProductNameSt = con.prepareStatement(StringUtils.GET_PRODUCT_NAME);
			checkProductNameSt.setString(1, productModel.getProductName());
			ResultSet checkProductNameRs = checkProductNameSt.executeQuery(); //returns the count of the matching productName
			//productId_validation - checking for existing productName
			PreparedStatement checkBrandSt = con.prepareStatement(StringUtils.GET_BRAND);
			checkBrandSt.setString(1, productModel.getBrand());
			ResultSet checkBrandRs = checkBrandSt.executeQuery(); //returns the count of the matching productName
			checkProductNameRs.next();
			checkBrandRs.next();
			if ((checkProductNameRs.getInt(1) > 0) && (checkBrandRs.getInt(1) > 0) ) {
				return -2; //Product already exists.
				//A products already has matching name and brand
			}
			
			// Create a PreparedStatement for inserting a new product
			PreparedStatement st = con.prepareStatement (StringUtils.INSERT_PRODUCT);
			st.setString(1, productModel.getProductName());
			st.setString(2, productModel.getBrand());
			st.setDouble(3, productModel.getUnitPrice());
			st.setInt(4, productModel.getStockQuantity());
			st.setString(5, productModel.getProductDescription());
			st.setString(6, productModel.getImageUrlFromPart());
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace (); // Log the exception for debugging
			return -1;
		}
	}
	
	/**
	 * This method attempts to update product's information.
	 * 
	 * @param productModel A 'ProductModel' object containing the product's all information to be updated.
	 * @return  An integer value indicating the update status:
	 * 		   - 1: Update successful
	 * 		   - 0: Update failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * 		   - -2: Update failed (Product already exists)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int updateProduct(ProductModel productModel) {
		try(Connection con = getConnection()){
			
			PreparedStatement checkProductNameSt = con.prepareStatement(StringUtils.GET_PRODUCT_NAME);
			checkProductNameSt.setString(1, productModel.getProductName());
			ResultSet checkProductNameRs = checkProductNameSt.executeQuery(); //returns the count of the matching productName
			checkProductNameRs.next();
			
			PreparedStatement checkBrandSt = con.prepareStatement(StringUtils.GET_BRAND);
			checkBrandSt.setString(1, productModel.getBrand());
			ResultSet checkBrandRs = checkBrandSt.executeQuery(); //returns the count of the matching productName
			checkBrandRs.next();
			
			PreparedStatement checkProductNameWithIdSt = con.prepareStatement(StringUtils.GET_PRODUCT_NAME_FOR_ID);
			checkProductNameWithIdSt.setString(1, productModel.getProductName());
			checkProductNameWithIdSt.setInt(2, productModel.getProductId());
			ResultSet checkProductNameWithIdRs = checkProductNameWithIdSt.executeQuery(); //returns the count of the matching productName
			checkProductNameWithIdRs.next();
			
			PreparedStatement checkBrandWithIdSt = con.prepareStatement(StringUtils.GET_BRAND_FOR_ID);
			checkBrandWithIdSt.setString(1, productModel.getBrand());
			checkBrandWithIdSt.setInt(2, productModel.getProductId());
			ResultSet checkBrandWithIdRs = checkBrandWithIdSt.executeQuery(); //returns the count of the matching productName
			checkBrandWithIdRs.next();
			
			if ((checkProductNameRs.getInt(1) > 0) 
					&& (checkBrandRs.getInt(1) > 0) 
					&& (checkProductNameWithIdRs.getInt(1) <= 0) 
					&& (checkBrandWithIdRs.getInt(1) <= 0)  ) {
				return -2; //Product already exists.
				//A products already has matching name and brand for a different product
			}
			
			// Create a PreparedStatement for inserting a new product
			PreparedStatement st = con.prepareStatement (StringUtils.UPDATE_PRODUCT);
			st.setString(1, productModel.getProductName());
			st.setString(2, productModel.getBrand());
			st.setDouble(3, productModel.getUnitPrice());
			st.setInt(4, productModel.getStockQuantity());
			st.setString(5, productModel.getProductDescription());
			st.setString(6, productModel.getImageUrlFromPart());
			
			st.setInt(7, productModel.getProductId());
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	//CRUD DELETE
	/**
	 * This method attempts to delete a product from the database using productId.
	 * 
	 * @param productId
	 * @return An integer value indicating the delete status:
	 * 		   - 1: Delete successful
	 * 		   - 0: Delete failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int deleteProduct(int productId) {
		try(Connection con = getConnection()){
			PreparedStatement st = con.prepareStatement(StringUtils.DELETE_PRODUCT);
			st.setInt(1, productId);
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
				
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method attempts to place an order. Take all the items from a user's cart and orders them. 
	 * Calls various other methods to complete the process.
	 * 
	 * @param username
	 * @param orderDate
	 * @return An integer value indicating the placing order status:
	 * 		   - 1: Order placed, successful
	 * 		   - 0: Order placing failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * 
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int placeOrder(String username, LocalDate orderDate) {
		try(Connection con = getConnection()){
			
			double orderTotal = 0;
			
			ArrayList<CartPageModel> cartList = getCartItems(username);
			int updateResults = 0;
			for(CartPageModel cartItem: cartList) {
				int productId = cartItem.getProductId();
				int orderQuantity = cartItem.getOrderQuantity();
				//condition rakhnu xa
				int stockQuantity = getStockQuantity(productId);
				int updatedStockQuantity = stockQuantity - orderQuantity;
				//check updateResult
				int updateResult = updateStockQuantity(productId, updatedStockQuantity);
				updateResults = updateResults + updateResult;
				
				orderTotal = orderTotal + cartItem.getLineTotal();
			}
			
			//order table ma insert
			int insertIntoOrderResult = insertIntoOrders(username, orderDate, orderTotal);
			//tyo insert huda order id generate hunxa tyo retrieve gareko
			int orderId = getOrderId(username, orderDate);
			
			int insertIntoOrderInfoResults = 0;
			int deleteResults = 0;
			//tyo order ma vako each product lai order info ma halna
			for(CartPageModel cartItem: cartList) {
				int productId = cartItem.getProductId();
				int orderQuantity = cartItem.getOrderQuantity();
				double lineTotal = cartItem.getLineTotal();
				//inserting into order info
				int insertIntoOrderInfoResult = insertIntoOrderInfo(orderId, productId, orderQuantity, lineTotal);
				insertIntoOrderInfoResults = insertIntoOrderInfoResults + insertIntoOrderInfoResult;
				
				int deleteResult = deleteCartItem(cartItem.getCartId(), productId);
				deleteResults = deleteResults + deleteResult;
			}
			
			if (updateResults > 0 && insertIntoOrderResult > 0 && insertIntoOrderInfoResults > 0 && deleteResults > 0) {
				return 1;
			}else {
				return 0;
			}
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method attempts to insert order information into orders table in database.
	 * @param username
	 * @param orderDate
	 * @param orderTotal
	 * @return An integer value indicating the insertion status:
	 * 		   - 1: Insertion successful
	 * 		   - 0: Insertion failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * 		   
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int insertIntoOrders(String username, LocalDate orderDate, double orderTotal) {
		try(Connection con = getConnection()){
			
			PreparedStatement st = con.prepareStatement(StringUtils.INSERT_ORDER);
			st.setDate(1, Date.valueOf(orderDate));
			st.setDouble(2, orderTotal);
			st.setString(3, username);
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method returns order id of the just processed order.
	 * 
	 * @param username
	 * @param orderDate
	 * @return An integer value:
	 * 		   - orderId: Successful retrieval of orderId
	 * 		   - 0: Failed to get orderId
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int getOrderId(String username, LocalDate orderDate) {
		try(Connection con = getConnection()){
			
			PreparedStatement st = con.prepareStatement(StringUtils.GET_ORDER_ID);
			st.setString(1, username);
			st.setDate(2, Date.valueOf(orderDate));
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				int orderId = rs.getInt("order_id");
				return orderId;
			}else {
				return 0;
			}
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * This method attempts to insert order information that includes productId, orderQuantity and lineTotal into ordersinfo table in database.
	 * 
	 * @param orderId
	 * @param productId
	 * @param orderQuantity
	 * @param lineTotal
	 * @return An integer value indicating the insertion status:
	 * 		   - 1: Insertion successful
	 * 		   - 0: Insertion failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * 		   
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int insertIntoOrderInfo(int orderId, int productId, int orderQuantity, double lineTotal ) {
		try(Connection con = getConnection()){
			
			PreparedStatement st = con.prepareStatement(StringUtils.INSERT_ORDER_INFO);
			st.setInt(1, orderId);
			st.setInt(2, productId);
			st.setInt(3, orderQuantity);
			st.setDouble(4, lineTotal);
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * This method returns stock quantity of the product from the database.
	 * 
	 * @param productId
	 * @return An integer value:
	 * 		   - stockQuantity: Successful retrieval of stockQuantity
	 * 		   - 0: Failed to get stockQuantity
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 * 
	 */
	public int getStockQuantity(int productId) {
		try(Connection con = getConnection()){
			
			PreparedStatement st = con.prepareStatement(StringUtils.GET_STOCK_QUANTITY);
			st.setInt(1, productId);
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				int stockQuantity = rs.getInt("stock_quantity");
				return stockQuantity;
			}else {
				return 0;
			}
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method attempts to update stock quantity of the product being ordered. 
	 * It subtracts the ordered quantity from the stock quantity.
	 * 
	 * @param productId
	 * @param updatedStockQuantity
	 * @return An integer value indicating the update status:
	 * 		   - 1: Update successful
	 * 		   - 0: Update failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int updateStockQuantity(int productId, int updatedStockQuantity) {
		try(Connection con = getConnection()){
			
			PreparedStatement st = con.prepareStatement(StringUtils.UPDATE_STOCK_QUANTITY);
			st.setInt(1, updatedStockQuantity);
			st.setInt(2, productId);
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * This method attempts to return all items from the just processed order.
	 * 
	 * @param username
	 * @param orderDate
	 * @return An ArrayList containing objects of OrderPageModel class:
	 * 		   - orderItems: Successful retrieval.
	 * 	   	   - null: Internal error (ClassNotFound or SQLException)
	 * 
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public ArrayList<OrderPageModel> getOrderItems(String username, LocalDate orderDate){
		try(Connection con = getConnection()){
			System.out.println("getOderItems running + orderdate: "+orderDate);
			int orderId = getOrderId(username, orderDate);
			System.out.println(orderId);
			PreparedStatement st = con.prepareStatement(StringUtils.GET_ORDER_DETAILS);
			st.setInt(1, orderId);
			ResultSet rs = st.executeQuery();
			
			ArrayList<OrderPageModel> orderItems = new ArrayList<>();
			
			while(rs.next()) {
				OrderPageModel orderModel = new OrderPageModel();
				
				orderModel.setOrderId(rs.getInt(StringUtils.ORDER_ID_DB));
				orderModel.setOrderDate(rs.getDate(StringUtils.ORDER_DATE_DB).toLocalDate());
				orderModel.setOrderTotal(rs.getInt(StringUtils.ORDER_TOTAL_DB));
				orderModel.setStatus(rs.getString(StringUtils.STATUS));
				
				orderModel.setProductId(rs.getInt(StringUtils.PRODUCT_ID_DB));
				orderModel.setOrderQuantity(rs.getInt(StringUtils.ORDER_QUANTITY_DB));
				orderModel.setLineTotal(rs.getDouble(StringUtils.LINE_TOTAL_DB));
				
				orderModel.setProductName(rs.getString(StringUtils.PRODUCT_NAME_DB));
				orderModel.setUnitPrice(rs.getDouble(StringUtils.UNIT_PRICE_DB));
				orderModel.setImageUrlFromPart(rs.getString(StringUtils.PRODUCT_IMAGE_DB));
				
				orderItems.add(orderModel);
			}
			return orderItems;
		
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method attempts to return the order history of the logged in user.
	 * It returns the orders belonging to the user.
	 * 
	 * @param username
	 * @return An ArrayList containing objects of OrderPageModel class:
	 * 		   - orderItems: Successful retrieval.
	 * 	   	   - null: Internal error (ClassNotFound or SQLException)
	 * 
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public ArrayList<OrderPageModel> getOrderHistory(String username){
		try(Connection con = getConnection()){
			System.out.println("getOderItems running + orderdate: ");

			PreparedStatement st = con.prepareStatement(StringUtils.GET_ORDER_HISTORY);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			
			ArrayList<OrderPageModel> orderItems = new ArrayList<>();
			
			while(rs.next()) {
				OrderPageModel orderModel = new OrderPageModel();
				
				orderModel.setOrderId(rs.getInt(StringUtils.ORDER_ID_DB));
				orderModel.setOrderDate(rs.getDate(StringUtils.ORDER_DATE_DB).toLocalDate());
				orderModel.setOrderTotal(rs.getInt(StringUtils.ORDER_TOTAL_DB));
				orderModel.setStatus(rs.getString(StringUtils.STATUS));
				
				orderModel.setProductId(rs.getInt(StringUtils.PRODUCT_ID_DB));
				orderModel.setOrderQuantity(rs.getInt(StringUtils.ORDER_QUANTITY_DB));
				orderModel.setLineTotal(rs.getDouble(StringUtils.LINE_TOTAL_DB));
				
				orderModel.setProductName(rs.getString(StringUtils.PRODUCT_NAME_DB));
				orderModel.setUnitPrice(rs.getDouble(StringUtils.UNIT_PRICE_DB));
				orderModel.setImageUrlFromPart(rs.getString(StringUtils.PRODUCT_IMAGE_DB));
				
				orderItems.add(orderModel);
			}
			return orderItems;
		
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method attempts to return the orders of all users for the admin.
	 * 
	 * @param username
	 * @return An ArrayList containing objects of OrderModel class:
	 * 		   - orders: Successful retrieval.
	 * 	   	   - null: Internal error (ClassNotFound or SQLException)
	 * 
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public ArrayList<OrderModel> getAllOrdersInfo(){
		try(Connection con = getConnection()){
			PreparedStatement st = con.prepareStatement(StringUtils.GET_ALL_ORDERS_INFO);
			ResultSet rs = st.executeQuery();
			
			ArrayList<OrderModel> orders = new ArrayList<>();
			
			while(rs.next()) {
				OrderModel order = new OrderModel();
				
				order.setOrderId(rs.getInt(StringUtils.ORDER_ID_DB));
				order.setOrderDate(rs.getDate(StringUtils.ORDER_DATE_DB));
				order.setOrderTotal(rs.getDouble(StringUtils.ORDER_TOTAL_DB));
				
				order.setUsername(rs.getString(StringUtils.USER_NAME));
				order.setStatus(rs.getString(StringUtils.STATUS));
				
				orders.add(order);
			}
			return orders;
		
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * This method returns the order status of the given order id.
	 * 
	 * @param orderId
	 * @return An 'OrderModel' object containing orderid and orderStatus.
	 * 		   - order: Successful retrieval.
	 * 	   	   - null: Failure to retrieve or Internal error (ClassNotFound or SQLException)
	 * 
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public OrderModel getOrderStatus(int orderId){
		try(Connection con = getConnection()){
			PreparedStatement st = con.prepareStatement(StringUtils.GET_ORDER_STATUS);
			st.setInt(1, orderId);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				OrderModel order = new OrderModel();
				
				order.setOrderId(orderId);
				order.setStatus(rs.getString(StringUtils.STATUS));
				return order;
			}else {
				return null;
			}	
		
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method attempts to update the order status of the given order id.
	 * 
	 * @param orderModel A 'OrderModel' containing order id and order status.
	 * @return An integer value indicating the update status:
	 * 		   - 1: Update successful
	 * 		   - 0: Update failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int updateOrderStatus(OrderModel orderModel) {
		try(Connection con = getConnection()){
			
			PreparedStatement st = con.prepareStatement (StringUtils.UPDATE_ORDER_STATUS);
			st.setString(1, orderModel.getStatus());
			st.setInt(2, orderModel.getOrderId());
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method returns cart id of the logged in user.
	 * 
	 * @param username The username of the logged in user.
	 * @return An integer value:
	 * 		   - cartId: Successful retrieval of cartId
	 * 		   - 0: Failed to get cartId
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int getCartId(String username) {
		try(Connection con = getConnection()){
			
			PreparedStatement st = con.prepareStatement(StringUtils.GET_CART_ID);
			st.setString(1, username);
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				int cartId = rs.getInt("cart_id");
				return cartId;
			}else {
				return 0;
			}
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method attempts to return all the cart items belonging to an user.
	 * 
	 * @param username
	 * @return An ArrayList containing objects of CartPageModel class:
	 * 		   - cartItems: Successfull retrieval.
	 * 	   	   - null: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public ArrayList<CartPageModel> getCartItems(String username){
		try(Connection con = getConnection()){
			
			int cartId = getCartId(username);
			
			PreparedStatement st = con.prepareStatement(StringUtils.GET_CART_ITEMS);
			st.setInt(1, cartId);
			ResultSet rs = st.executeQuery();
			
			ArrayList<CartPageModel> cartItems = new ArrayList<>();
			
			while(rs.next()) {
				CartPageModel cartModel = new CartPageModel();
				cartModel.setCartId(rs.getInt(StringUtils.CART_ID_DB));
				cartModel.setProductId(rs.getInt(StringUtils.PRODUCT_ID_DB));
				cartModel.setOrderQuantity(rs.getInt(StringUtils.ORDER_QUANTITY_DB));
				cartModel.setLineTotal(rs.getDouble(StringUtils.LINE_TOTAL_DB));
				cartModel.setProductName(rs.getString(StringUtils.PRODUCT_NAME_DB));
				cartModel.setUnitPrice(rs.getDouble(StringUtils.UNIT_PRICE_DB));
				cartModel.setImageUrlFromPart(rs.getString(StringUtils.PRODUCT_IMAGE_DB));
				cartItems.add(cartModel);
			}
			return cartItems;
		
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method attempts to inserts cart information to cartinfo.
	 * 
	 * @param username
	 * @param productId
	 * @param orderQuantity
	 * @return An integer value indicating the registrating status:
	 * 		   - 1: Insertion successful
	 * 		   - 0: Insertion failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int addToCart(String username,int productId,int orderQuantity) {
		try (Connection con = getConnection()) {
			
			// Create a PreparedStatement for inserting product to cart
			//getproductInfo 
			ProductModel product = getProductInfo(productId);
			int cartId = getCartId(username);
			double lineTotal = orderQuantity * product.getUnitPrice();
			
			//checking if the product already exists in cart
			//if it exists, order quantity will be increased. if it doesnt, it will be inserted into a new row.
			PreparedStatement checkProductIdSt = con.prepareStatement(StringUtils.GET_PRODUCT_ID_COUNT_FROM_CART);
			checkProductIdSt.setInt(1, productId);
			checkProductIdSt.setInt(2, cartId);
			ResultSet checkProductIdRS = checkProductIdSt.executeQuery(); //returns the count of the matching product
			checkProductIdRS.next();
			if (checkProductIdRS.getInt(1) > 0) {
				int existingOrderQuantity = 0;
				System.out.println("here");
				PreparedStatement getOrderQuantitySt = con.prepareStatement(StringUtils.GET_ORDER_QUANTITY_FROM_CART);
				getOrderQuantitySt.setInt(1,productId);
				getOrderQuantitySt.setInt(2, cartId);
				ResultSet getOrderQuantityRs = getOrderQuantitySt.executeQuery(); 
				if (getOrderQuantityRs.next()) {
					existingOrderQuantity = getOrderQuantityRs.getInt("order_quantity");
				}
				int newOrderQuantity = existingOrderQuantity + orderQuantity;
				lineTotal = newOrderQuantity * product.getUnitPrice();
				PreparedStatement st = con.prepareStatement(StringUtils.UPDATE_ORDER_QUANTITY_FOR_CART);
				st.setInt(1,newOrderQuantity);
				st.setDouble(2,lineTotal);
				st.setInt(3,cartId);
				st.setInt(4,productId);
				int result = st.executeUpdate();
				return result > 0 ? 1 : 0;
			}else {
				PreparedStatement st = con.prepareStatement(StringUtils.INSERT_CART_INFO);
				st.setInt(1, cartId);
				st.setInt(2,productId);
				st.setInt(3,orderQuantity);
				st.setDouble(4,lineTotal);
				
				int result = st.executeUpdate();
				return result > 0 ? 1 : 0;
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace (); // Log the exception for debugging
			return -1;
		}
	}
	
	/**
	 * This methods updates order quantity of the product stored in the cart.
	 * 
	 * @param cartId
	 * @param productId
	 * @param orderQuantity
	 * @return An integer value indicating the update status:
	 * 		   - 1: Update successful
	 * 		   - 0: Update failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int updateCartOrderQuantity(int cartId, int productId, int orderQuantity) {
		try(Connection con = getConnection()){
			ProductModel product = getProductInfo(productId);
			double lineTotal = orderQuantity * product.getUnitPrice();
			PreparedStatement st = con.prepareStatement(StringUtils.UPDATE_ORDER_QUANTITY_FOR_CART);
			st.setInt(1, orderQuantity);
			st.setDouble(2,lineTotal);
			st.setInt(3,cartId);
			st.setInt(4,productId);
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This method attemps to remove the selected item from the user's cart.
	 * 
	 * @param cartId
	 * @param productId
	 * @return An integer value indicating the deletion status:
	 * 		   - 1: Delete successful
	 * 		   - 0: Delete failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int deleteCartItem(int cartId, int productId) {
		try(Connection con = getConnection()){
			PreparedStatement st = con.prepareStatement(StringUtils.DELETE_CART_ITEM);
			st.setInt(1, cartId);
			st.setInt(2, productId);
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
				
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	//store user message from about us page
	/**
	 * This method attempts to store user's message provided through the about us page.
	 * 
	 * @param username
	 * @param userMessage
	 * @return An integer value indicating the insertion status:
	 * 		   - 1: insertion successful
	 * 		   - 0: insertion failed (no rows affected)
	 * 		   - -1: Internal error (ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int storeUserMessage(String username, String userMessage) {
		try(Connection con = getConnection()){
			
			PreparedStatement st = con.prepareStatement(StringUtils.STORE_USER_MESSAGE);
			st.setString(1, username);
			st.setString(2, userMessage);
			
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
}