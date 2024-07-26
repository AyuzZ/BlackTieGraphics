package model;

public class CartPageModel {
	private int cartId;
	private int productId;
	private int orderQuantity;
	private double lineTotal;
	private String productName;
	private double unitPrice;
	private String imageUrlFromPart;

	public CartPageModel() {
		
	}

	public CartPageModel(int cartId, int productId, int orderQuantity, double lineTotal, String productName,
			double unitPrice, String imageUrlFromPart) {
		super();
		this.cartId = cartId;
		this.productId = productId;
		this.orderQuantity = orderQuantity;
		this.lineTotal = lineTotal;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.imageUrlFromPart = imageUrlFromPart;
	}

	public int getCartId() {
		return cartId;
	}

	public int getProductId() {
		return productId;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public double getLineTotal() {
		return lineTotal;
	}

	public String getProductName() {
		return productName;
	}

	public double getUnitPrice() {
		return unitPrice;
	}
	
	public String getImageUrlFromPart() {
		return imageUrlFromPart;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setImageUrlFromPart(String imageUrlFromPart) {
		this.imageUrlFromPart = imageUrlFromPart;
	}

}

