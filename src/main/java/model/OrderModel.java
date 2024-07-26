package model;

import java.io.Serializable;
import java.sql.Date;

public class OrderModel implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	private int orderId;
	private Date orderDate;
	private double orderTotal;
	
	private String username;
	private String status;
	
	public OrderModel() {}
	
	public OrderModel(int orderId, Date orderDate, double orderTotal, String username,
			String status) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
		
		this.username = username;
		this.status = status;
	}

	public int getOrderId() {
		return orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public String getUsername() {
		return username;
	}

	public String getStatus() {
		return status;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
