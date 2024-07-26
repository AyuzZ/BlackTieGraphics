package model;

import java.io.File;
import java.io.Serializable;

import javax.servlet.http.Part;

import util.StringUtils;

public class ProductModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//product_id, product_name, brand, ram, clock_speed, output_interfaces,  unit_price, stock_qty) 

	private int productId;
	private String productName;
	private String brand;
	private double unitPrice;
	private int stockQuantity;
	private String productDescription;
	private String imageUrlFromPart;

	public ProductModel() {}
	
	public ProductModel(int productId, String productName, String brand, double unitPrice, 
			int stockQuantity, String productDescription, Part imagePart) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.brand = brand;
		this.unitPrice = unitPrice;
		this.stockQuantity = stockQuantity;
		this.productDescription = productDescription;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}

	public ProductModel(String productName, String brand, double unitPrice, 
			int stockQuantity, String productDescription, Part imagePart) {
		super();
		this.productName = productName;
		this.brand = brand;
		this.unitPrice = unitPrice;
		this.stockQuantity = stockQuantity;
		this.productDescription = productDescription;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getBrand() {
		return brand;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}
	
	public String getProductDescription() {
		return productDescription;
	}

	public String getImageUrlFromPart() {
		return imageUrlFromPart;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public void setImageUrlFromPart(String imageUrlFromPart) {
		this.imageUrlFromPart = imageUrlFromPart;
	}

	private String getImageUrl(Part part) {
		String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_PRODUCT;
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


