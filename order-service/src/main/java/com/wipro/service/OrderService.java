package com.wipro.service;

import java.util.List;

import com.wipro.dto.OrderDto;

public interface OrderService {
	public List<OrderDto> getAllOrder();
	public OrderDto placeOrder(Integer cartId);
	public OrderDto getOrderById(Integer orderId);
	public String deleteOrderById(Integer orderId);
	public OrderDto updateOrderByIdForPayment(Integer orderId);
	public OrderDto updateOrderByIdForDelivery(Integer orderId);
	public List<OrderDto> getOrderByCustomerId(Integer customerId);
	public List<OrderDto> getOrderByPaid(boolean paid);
	public List<OrderDto> getOrderByDelivery(boolean delivered);
}
