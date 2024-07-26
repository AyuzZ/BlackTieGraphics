package util;

import java.io.File;

public class StringUtils {

	//sql queries
	//users
	//registers the user
	public static final String INSERT_USER = "INSERT INTO users"
			+ "(username, first_name, last_name, email, password, user_image) "
			+ "VALUES(?, ?, ?, ?, ?, ?)";
	//update user
	public static final String UPDATE_USER = "UPDATE users "
			+ "SET first_name = ?, last_name = ?, phone_number = ?, email = ?, "
			+ "gender = ?, dob = ?, address = ?, user_image = ? "
			+ "WHERE username = ?";
	//gets username and password for login
	public static final String GET_LOGIN_INFO = "SELECT * "
			+ "FROM users "
			+ "WHERE username = ? ";
	//returns the count of matching username
	public static final String GET_USERNAME = "SELECT COUNT(*) "
			+ "FROM users "
			+ "WHERE username = ?";
	//returns the count of matching email
	public static final String GET_EMAIL = "SELECT COUNT(*) "
			+ "FROM users "
			+ "WHERE email = ?";	
	//returns the count of matching phone number
	public static final String GET_PHONE_NUMBER = "SELECT COUNT(*) "
			+ "FROM users "
			+ "WHERE phone_number = ?";
	//returns the count of matching email
	public static final String GET_EMAIL_USER = "SELECT COUNT(*) "
			+ "FROM users "
			+ "WHERE email = ? "
			+ "AND username = ?";	
	//returns the count of matching phone number
	public static final String GET_PHONE_NUMBER_USER = "SELECT COUNT(*) "
			+ "FROM users "
			+ "WHERE phone_number = ? "
			+ "AND username = ?";
	//returns all the users
	public static final String GET_ALL_USER_INFO = "SELECT * "
			+ "FROM users";
	//returns all info for one user
	public static final String GET_USER_INFO = "SELECT * "
			+ "FROM users "
			+ "WHERE username = ?";
	//get cart_id
	public static final String GET_CART_ID = "SELECT cart_id "
			+ "FROM users "
			+ "WHERE username = ?";
	
	//user messages
	//store user message
	public static final String STORE_USER_MESSAGE = "INSERT INTO usermessages"
			+ "(username, user_message) "
			+ "VALUES(?, ?)";
	
	//products
	//add new product
	public static final String INSERT_PRODUCT = "INSERT INTO products"
			+ "(product_name, brand, unit_price, stock_quantity, product_description, product_image) "
			+ "VALUES(?, ?, ?, ?, ?, ?)";
	//returns the count of matching username
	public static final String GET_PRODUCT_NAME = "SELECT COUNT(*) "
			+ "FROM products "
			+ "WHERE product_name = ?";
	//returns the count of matching username
	public static final String GET_BRAND = "SELECT COUNT(*) "
			+ "FROM products "
			+ "WHERE brand = ?";
	//returns the count of matching username
	public static final String GET_PRODUCT_NAME_FOR_ID = "SELECT COUNT(*) "
			+ "FROM products "
			+ "WHERE product_name = ? "
			+ "AND product_id = ?";
	//returns the count of matching username
	public static final String GET_BRAND_FOR_ID = "SELECT COUNT(*) "
			+ "FROM products "
			+ "WHERE brand = ? "
			+ "AND product_id = ?";
	//return all the products
	public static final String GET_ALL_PRODUCTS_INFO = "SELECT * "
			+ "FROM products";
	
	
	//return searched the products
	public static final String GET_SEARCHED_PRODUCTS_INFO_FROM_NAME = "SELECT * "
			+ "FROM products "
			+ "WHERE product_name LIKE ?";
	public static final String GET_SEARCHED_PRODUCTS_INFO_FROM_PRICE = "SELECT * "
			+ "FROM products "
			+ "WHERE unit_price BETWEEN ? AND ?";
	public static final String GET_SEARCHED_PRODUCTS_INFO_FROM_BOTH = "SELECT * "
			+ "FROM products "
			+ "WHERE product_name LIKE ? "
			+ "AND unit_price BETWEEN ? AND ?";
	
	
	//return all the products
	public static final String GET_PRODUCT_INFO = "SELECT * "
		+ "FROM products "
		+ "WHERE product_id = ? ";
	//update product
	public static final String UPDATE_PRODUCT = "UPDATE products "
			+ "SET product_name = ?, brand = ?, unit_price = ?, stock_quantity = ?, product_description = ?, product_image = ? "
			+ "WHERE product_id = ?";
	//delete product
	public static final String DELETE_PRODUCT = "DELETE FROM products "
			+ "WHERE product_id = ?";
	//get stock quantity
	public static final String GET_STOCK_QUANTITY = "SELECT stock_quantity "
			+ "FROM products "
			+ "WHERE product_id = ?";
	//update stock quantity
	public static final String UPDATE_STOCK_QUANTITY = "UPDATE products "
			+ "SET stock_quantity = ? "
			+ "WHERE product_id = ?";
	
	
	//returns the count of matching username
	public static final String GET_PRODUCT_ID_COUNT_FROM_CART = "SELECT COUNT(*) "
			+ "FROM cartinfo "
			+ "WHERE product_id = ? "
			+ "AND cart_id = ?";
	//get stock quantity
	public static final String GET_ORDER_QUANTITY_FROM_CART = "SELECT order_quantity "
			+ "FROM cartinfo "
			+ "WHERE product_id = ? "
			+ "AND cart_id = ?";
	//add new cartinfo
	public static final String INSERT_CART_INFO = "INSERT INTO cartinfo"
			+ "(cart_id, product_id, order_quantity, line_total) "
			+ "VALUES(?, ?, ?, ?)";
	//updating quantity if same product is added to cart again
	public static final String UPDATE_ORDER_QUANTITY_FOR_CART = "UPDATE cartinfo "
			+ "SET order_quantity = ?, line_total = ? "
			+ "WHERE cart_id = ? "
			+ "AND product_id = ?";
	//get cart items 
	public static final String GET_CART_ITEMS = "SELECT cI.cart_id, cI.product_id, cI.order_quantity, cI.line_total, "
			+ "p.product_name, p.unit_price, p.product_image "
			+ "FROM cartinfo cI "
			+ "JOIN products p "
			+ "ON cI.product_id = p.product_id "
			+ "WHERE cart_id = ?";
	//delete cartItem
	public static final String DELETE_CART_ITEM = "DELETE FROM cartinfo "
			+ "WHERE cart_id = ? "
			+ "AND product_id = ?";
	
	
	//orders
	public static final String GET_ALL_ORDERS_INFO = "SELECT * "
			+ "FROM orders";
	public static final String GET_ORDER_STATUS = "SELECT status "
			+ "FROM orders "
			+ "WHERE order_id = ?";
	public static final String UPDATE_ORDER_STATUS = "UPDATE orders "
			+ "SET status = ? "
			+ "WHERE order_id = ?";
	//add new order
	public static final String INSERT_ORDER = "INSERT INTO orders"
			+ "(order_date, order_total, username) "
			+ "VALUES(?, ?, ?)";
	//get orderId
	public static final String GET_ORDER_ID = "SELECT * "
			+ "FROM orders "
			+ "WHERE username = ? "
			+ "AND order_date = ?";
	//add new orderinfo
	public static final String INSERT_ORDER_INFO = "INSERT INTO orderinfo"
			+ "(order_id, product_id, order_quantity, line_total) "
			+ "VALUES(?, ?, ?, ?)";
	//get current orderDetails
	public static final String GET_ORDER_DETAILS = "SELECT o.order_id, o.order_date, o.order_total, o.status, "
			+ "oI.product_id, oI.order_quantity, oI.line_total, "
			+ "p.product_name, p.unit_price, p.product_image "
			+ "FROM orders o "
			+ "JOIN orderinfo oI "
			+ "ON o.order_id = oI.order_id "
			+ "JOIN products p "
			+ "ON oI.product_id = p.product_id "
			+ "WHERE o.order_id = ?";
	//get current orderhistory
	public static final String GET_ORDER_HISTORY = "SELECT o.order_id, o.order_date, o.order_total, o.status, "
			+ "oI.product_id, oI.order_quantity, oI.line_total, "
			+ "p.product_name, p.unit_price, p.product_image "
			+ "FROM orders o "
			+ "JOIN orderinfo oI "
			+ "ON o.order_id = oI.order_id "
			+ "JOIN products p "
			+ "ON oI.product_id = p.product_id "
			+ "WHERE o.username = ?";
	
	//parameter names - jsp
	public static final String USER_NAME = "username";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String EMAIL = "email";
	public static final String ROLE = "role";
	public static final String PASSWORD = "password";
	public static final String RETYPE_PASSWORD = "retypePassword";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String DOB = "dob";
	public static final String ADDRESS = "address";
	public static final String GENDER = "gender";
	
	//referencing db, jsp
	public static final String PRODUCT_ID_DB = "product_id";
	public static final String PRODUCT_ID = "productId";
	public static final String PRODUCT_NAME_DB = "product_name";
	public static final String PRODUCT_NAME = "productName";
	public static final String BRAND = "brand";
	public static final String UNIT_PRICE_DB = "unit_price";
	public static final String UNIT_PRICE = "unitPrice";
	public static final String STOCK_QUANTITY_DB = "stock_quantity";
	public static final String STOCK_QUANTITY = "stockQuantity";
	public static final String PRODUCT_DESCRIPTION_DB = "product_description";
	public static final String PRODUCT_DESCRIPTION = "productDescription";
	public static final String PRODUCT_IMAGE_DB = "product_image";
	
	public static final String ORDER_ID_DB = "order_id";
	public static final String ORDER_ID = "orderId";
	public static final String ORDER_DATE_DB = "order_date";
	public static final String ORDER_TOTAL_DB = "order_total";
	public static final String INVOICE_ID_DB = "invoice_id";
	public static final String STATUS = "status";
	
	public static final String CART_ID_DB = "cart_id";
	public static final String CART_ID = "cartId";
	public static final String LINE_TOTAL_DB = "line_total";
	public static final String LINE_TOTAL ="lineTotal";
	public static final String ORDER_QUANTITY_DB= "order_quantity";
	public static final String ORDER_QUANTITY= "orderQuantity";
	
	
	
	
	//messages
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String SUCCESS_MESSAGE = "successMessage";
	
	public static final String SEARCH_EMPTY_ERROR_MESSAGE = "Provide atleast one criteria.";
	
	public static final String LOG_IN_TO_VIEW_CART_MESSAGE = "Login to view your cart.";
	public static final String LOG_IN_TO_ADD_TO_CART_MESSAGE = "Login to add to your cart.";
	public static final String LOG_IN_TO_VIEW_PROFILE_MESSAGE = "Login to view your profile";
	
	public static final String NEED_TO_LOG_IN_MESSAGE = "You must be logged in to send the message.";
	public static final String MESSAGE_SENT_SUCCESS_MESSAGE = "Message has been sent. The admin will contact you through your email.";
	public static final String MESSAGE_SENT_ERROR_MESSAGE = "Failed to send message. Try Again!";
	
	public static final String REGISTER_SUCCESS_MESSAGE = "Successfully Registered!";
	public static final String LOGIN_SUCCESS_MESSAGE = "Successfully Logged In!";
	public static final String UPDATE_SUCCESS_MESSAGE = "Successfully Updated!";
	
	public static final String SERVER_ERROR_MESSAGE = "Server Error.";
	
	public static final String USERNAME_ERROR_MESSAGE = "Username is taken.";
	public static final String PHONE_NUMBER_ERROR_MESSAGE = "Phone number is taken.";
	public static final String EMAIL_ERROR_MESSAGE = "Email is taken.";
	
	public static final String USERNAME_NOT_FOUND_MESSAGE = "Username not found. Recheck your username Or Create a new Account. Click On Register To Create a new Account.";
	
	public static final String PASSWORD_ERROR_MESSAGE = "Password doesn't match.";
	
	public static final String PRODUCT_ADD_SUCCESS_MESSAGE = "Successfully Added!";
	public static final String PRODUCT_UPDATED_SUCCESS_MESSAGE = "Successfully Updated!";
	public static final String PRODUCT_EXISTS_ERROR_MESSAGE = "The Product Already Exists.";
	public static final String PRODUCT_DELETE_SUCCESS_MESSAGE = "Deletion Successful!";
	public static final String PRODUCT_DELETE_ERROR_MESSAGE = "Deletion Failed!";
	
	
	public static final String CART_ITEM_DELETE_SUCCESS_MESSAGE = "Deletion Successful!";
	public static final String CART_ITEM_DELETE_ERROR_MESSAGE = "Deletion Failed!";
	
	public static final String CART_ITEM_UPDATE_SUCCESS_MESSAGE = "Update Successful!";
	public static final String CART_ITEM_UPDATE_ERROR_MESSAGE = "Update Failed!";
	
	public static final String STATUS_UPDATE_ERROR_MESSAGE = "Status Updated!";
	public static final String STATUS_UPDATE_SUCCESS_MESSAGE = "Status Update Failed!";
	
	//input validation error messages
	public static final String USERNAME_LENGTH_ERROR = "Username must be atleast 6 characters long";
	public static final String USERNAME_CONTENT_ERROR = "Username must only contain alpha-numeric characters.";
	public static final String FIRST_NAME_CONTENT_ERROR = "First Name must only contain alphabets.";
	public static final String LAST_NAME_CONTENT_ERROR = "Last Name must only contain alphabets.";
	public static final String EMAIL_CONTENT_ERROR = "Email must have standard format.";
	public static final String PHONE_NUMBER_CONTENT_ERROR = "Phone Number must only contain numbers.";
	public static final String PHONE_NUMBER_LENGTH_ERROR = "Phone Number must be only 10 numbers long";
	public static final String GENDER_CONTENT_ERROR = "Gender must only contain alphabets.";
	public static final String ADDRESS_CONTENT_ERROR = "Address must only contain alpha-numeric and white-space characters.";
	
	public static final String PRODUCT_NAME_CONTENT_ERROR = "Product Name must only contain alpha-numeric characters.";
	public static final String BRAND_CONTENT_ERROR = "Brand must only contain alpha-numeric characters.";
	public static final String UNIT_PRICE_CONTENT_ERROR = "Unit Price must be numeric or floating point numbers.";
	public static final String STOCK_QUANTITY_CONTENT_ERROR = "Stock Quantity must be numeric.";
	
	
	
	//jsp pages route
	public static final String INDEX_PAGE = "/pages/index.jsp";
	public static final String LOGIN_PAGE = "/pages/login.jsp";
	public static final String REGISTER_PAGE = "/pages/register.jsp";
	public static final String DASHBOARD_PAGE = "/pages/dashboard.jsp";
	public static final String HEADER_PAGE = "/pages/header.jsp";
	public static final String FOOTER_PAGE = "/pages/footer.jsp";
	public static final String ADD_PRODUCT_PAGE = "/pages/addProduct.jsp";
	public static final String UPDATE_PRODUCT_PAGE = "/pages/updateProduct.jsp";
	public static final String UPDATE_USER_PAGE = "/pages/updateUser.jsp";
	public static final String USER_PROFILE_PAGE = "/pages/userProfile.jsp";
	public static final String UPDATE_ORDER_STATUS_PAGE = "/pages/updateOrderStatus.jsp";
	public static final String CART_PAGE = "/pages/cartPage.jsp";
	public static final String ABOUT_US_PAGE = "/pages/aboutUs.jsp";
	
	//servlets route
	public static final String REGISTER_SERVLET = "/RegisterServlet";
	public static final String LOGIN_SERVLET = "/LoginServlet";
	public static final String LOGOUT_SERVLET = "/LogoutServlet";
	public static final String DASHBOARD_SERVLET = "/DashboardServlet";
	
	public static final String USER = "user";
	public static final String JSESSIONID = "JSESSIONID";
	
	//user image C:\Users\Acer\Documents\EclipseProjects\BlackTieGraphics
	public static final String IMAGE_DIR_USER = "Users\\Acer\\Documents\\EclipseProjects\\BlackTieGraphics\\src\\main\\webapp\\resources\\images\\users\\";
	public static final String IMAGE_DIR_SAVE_PATH_USER = "C:" + File.separator + IMAGE_DIR_USER;
	
	//product image
	public static final String IMAGE_DIR_PRODUCT = "Users\\Acer\\Documents\\EclipseProjects\\BlackTieGraphics\\src\\main\\webapp\\resources\\images\\products\\";
	public static final String IMAGE_DIR_SAVE_PATH_PRODUCT = "C:" + File.separator + IMAGE_DIR_PRODUCT;

}
