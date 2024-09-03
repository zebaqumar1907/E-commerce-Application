package com.wipro.dto;

public class OrderItemDto {
	private int orderItemId;
	private String productName;
	private long productId;
	private int quantity;
	private double productPrice;
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public OrderItemDto(int orderItemId, String productName, long productId, int quantity, double productPrice) {
		super();
		this.orderItemId = orderItemId;
		this.productName = productName;
		this.productId = productId;
		this.quantity = quantity;
		this.productPrice = productPrice;
	}
	public OrderItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
