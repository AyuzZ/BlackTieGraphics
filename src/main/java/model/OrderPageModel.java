package model;

import java.time.LocalDate;

public class OrderPageModel {

	//orders table
	private int orderId;
	private LocalDate orderDate;
	private double orderTotal;
	private String status;
	//order info
	private int productId;
	private int orderQuantity;
	private double lineTotal;
	//products
	private String productName;
	private double unitPrice;
	private String imageUrlFromPart;
	
	public OrderPageModel() {
		super();
	}

	public OrderPageModel(int orderId, LocalDate orderDate, double orderTotal, String status, int productId,
			int orderQuantity, double lineTotal, String productName, double unitPrice, String imageUrlFromPart) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
		this.status = status;
		this.productId = productId;
		this.orderQuantity = orderQuantity;
		this.lineTotal = lineTotal;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.imageUrlFromPart = imageUrlFromPart;
	}

	public int getOrderId() {
		return orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public String getStatus() {
		return status;
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

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public void setStatus(String status) {
		this.status = status;
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
