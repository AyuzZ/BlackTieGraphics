package model;

public class OrderInfoModel {
	private int orderId;
	private int productId;
	private int orderQuantity;
	private double lineTotal;
	
	public OrderInfoModel(int orderId, int productId, int orderQuantity, double lineTotal) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.orderQuantity = orderQuantity;
		this.lineTotal = lineTotal;
	}

	public int getOrderId() {
		return orderId;
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

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
