package com.wipro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemEntry {
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int itemEntryId;
	
	
	private Long productId;
	private String productName;
	private double productPrice;
	private int quantity;
	
	 @ManyToOne
	 @JoinColumn(name = "cart_id")
	 private Cart cart;
	 
	 
	
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
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
	public ItemEntry() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ItemEntry(int itemEntryId, Long productId, String productName, double productPrice, int quantity,
			Cart cart) {
		super();
		this.itemEntryId = itemEntryId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.quantity = quantity;
		this.cart = cart;
	}
	
	
	
	
}
