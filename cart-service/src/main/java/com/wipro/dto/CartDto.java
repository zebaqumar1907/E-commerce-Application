package com.wipro.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wipro.entity.ItemEntry;

public class CartDto {
	private int cartId;
	private int customerId;
	private List<ItemEntryDto> itemsDtoList= new ArrayList();
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
	
	
	public List<ItemEntryDto> getItemsDtoList() {
		return itemsDtoList;
	}
	public void setItemsDtoList(List<ItemEntryDto> itemsDtoList) {
		this.itemsDtoList = itemsDtoList;
	}
	public BigDecimal getCartPrice() {
		return cartPrice;
	}
	public void setCartPrice(BigDecimal cartPrice) {
		this.cartPrice = cartPrice;
	}
	public CartDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDto(int cartId, int customerId, List<ItemEntryDto> itemsDtoList, BigDecimal cartPrice) {
		super();
		this.cartId = cartId;
		this.customerId = customerId;
		this.itemsDtoList = itemsDtoList;
		this.cartPrice = cartPrice;
	}
	
	
	
	
	
}
