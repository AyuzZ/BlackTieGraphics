package model;

import java.io.Serializable;

public class CartModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int cartId;
	private String username;
	
	public CartModel() {
		super();
	}

	public CartModel(int cartId, String username) {
		super();
		this.cartId = cartId;
		this.username = username;
	}

	public int getCartId() {
		return cartId;
	}

	public String getUsername() {
		return username;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
