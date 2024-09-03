package com.wipro.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class OrderDto {
	private int orderId;
	private List<OrderItemDto> orderItemDtoList=new ArrayList<>();
	private BigDecimal price;
	private int customerId;
	private String customerName;
	private String shippingAddress;
	
	private boolean delivered;
	private boolean paid;
	
	public boolean isDelivered() {
		return delivered;
	}
	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
	
	public List<OrderItemDto> getOrderItemDtoList() {
		return orderItemDtoList;
	}
	public void setOrderItemDtoList(List<OrderItemDto> orderItemDtoList) {
		this.orderItemDtoList = orderItemDtoList;
	}
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	
	public OrderDto(int orderId, List<OrderItemDto> orderItemDtoList, BigDecimal price, int customerId,
			String customerName, String shippingAddress, boolean delivered, boolean paid) {
		super();
		this.orderId = orderId;
		this.orderItemDtoList = orderItemDtoList;
		this.price = price;
		this.customerId = customerId;
		this.customerName = customerName;
		this.shippingAddress = shippingAddress;
		this.delivered = delivered;
		this.paid = paid;
	}
	public OrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
