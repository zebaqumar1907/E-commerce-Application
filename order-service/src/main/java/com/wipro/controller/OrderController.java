package com.wipro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.OrderDto;
import com.wipro.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeorder/{cartId}")
	public OrderDto placeOrderCart(@PathVariable("cartId") Integer cartId) {
		return orderService.placeOrder(cartId);
	}
	
	@GetMapping("/order/{orderId}")
	public OrderDto getOrderById(@PathVariable("orderId") Integer orderId) {
		return orderService.getOrderById(orderId);
	}
	
	@GetMapping("/getallOrders")
	public List<OrderDto> getAllOrders(){
		return orderService.getAllOrder();
	}
	
	@DeleteMapping("/cancelorder/{orderId}")
	public String cancelOrderById(@PathVariable("orderId") Integer orderId) {
		return orderService.deleteOrderById(orderId);
	}
	
	@PutMapping("/updateorderpayment/{orderId}")
	public OrderDto updateOrderForPayment(@PathVariable("orderId") Integer orderId) {
		return orderService.updateOrderByIdForPayment(orderId);
	}
	
	@PutMapping("/updateorderdelivery/{orderId}")
	public OrderDto updateOrderForDelivery(@PathVariable("orderId") Integer orderId) {
		return orderService.updateOrderByIdForDelivery(orderId);
	}
	
	@GetMapping("/getorderbycustomer/{customerId}")
	public List<OrderDto> getOrdersOfCustomer(@PathVariable("customerId") Integer customerId){
		return orderService.getOrderByCustomerId(customerId);
	}
	
	@GetMapping("/getorderbydelivery/query")
	public List<OrderDto> getOrdersByDelivery(@RequestParam boolean delivery){
		return orderService.getOrderByDelivery(delivery);
	}
	
	@GetMapping("/getorderbypayment/query")
	public List<OrderDto> getOrdersByPayment(@RequestParam boolean paid){
		return orderService.getOrderByDelivery(paid);
	}
}
