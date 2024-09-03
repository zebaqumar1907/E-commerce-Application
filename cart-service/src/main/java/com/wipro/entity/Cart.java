package com.wipro.entity;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cartId;
	private int customerId;
	
	 @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemEntry> itemsList= new ArrayList();
	 
	private BigDecimal cartPrice;
	
	
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public List<ItemEntry> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<ItemEntry> itemsList) {
		this.itemsList = itemsList;
	}
	public BigDecimal getCartPrice() {
		return cartPrice;
	}
	public void setCartPrice(BigDecimal cartPrice) {
		this.cartPrice = cartPrice;
	}
	
	
	
	public Cart(int cartId, int customerId, List<ItemEntry> itemsList, BigDecimal cartPrice) {
		super();
		this.cartId = cartId;
		this.customerId = customerId;
		this.itemsList = itemsList;
		this.cartPrice = cartPrice;
	}
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
