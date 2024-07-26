package model;

public class CartInfoModel {
	private int cartId;
	private int productId;
	private int orderQuantity;
	private double lineTotal;
	
	public CartInfoModel(int cartId, int productId, int orderQuantity, double lineTotal) {
		super();
		this.cartId = cartId;
		this.productId = productId;
		this.orderQuantity = orderQuantity;
		this.lineTotal = lineTotal;
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
	
	
	
}
