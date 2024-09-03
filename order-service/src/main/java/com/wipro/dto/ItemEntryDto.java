package com.wipro.dto;

public class ItemEntryDto {
	private int itemEntryId;
	
	private Long productId;
	private String productName;
	private double productPrice;
	private int quantity;
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public int getItemEntryId() {
		return itemEntryId;
	}
	public void setItemEntryId(int itemEntryId) {
		this.itemEntryId = itemEntryId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public ItemEntryDto(int itemEntryId, Long productId, String productName, double productPrice, int quantity) {
		super();
		this.itemEntryId = itemEntryId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.quantity = quantity;
	}
	public ItemEntryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}