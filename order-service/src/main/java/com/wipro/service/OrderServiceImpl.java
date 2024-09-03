package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.dto.CartDto;
import com.wipro.dto.CustomerDto;
import com.wipro.dto.OrderDto;
import com.wipro.dto.OrderItemDto;
import com.wipro.dto.ProductDto;
import com.wipro.entity.Order;
import com.wipro.entity.OrderItem;
import com.wipro.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CartApiClient cartApiClient;

	@Autowired
	private CustomerApiClient customerApiClient;
	
	@Autowired
	private ProductApiClient productApiClient;

	private OrderDto mapToOrderDto(Order order) {
		List<OrderItemDto> orderItemDtoList = order.getOrderItemList().stream()
				.map(item -> new OrderItemDto(item.getOrderItemId(), item.getProductName(), item.getProductId(),
						item.getQuantity(), item.getProductPrice()))
				.collect(Collectors.toList());

		return new OrderDto(order.getOrderId(), orderItemDtoList, order.getPrice(), order.getCustomerId(),
				order.getCustomerName(), order.getShippingAddress(), order.isDelivered(), order.isPaid());
	}

	@Override
	public List<OrderDto> getAllOrder() {
		// TODO Auto-generated method stub
		List<Order> orderLists = orderRepository.findAll();
		return orderLists.stream().map(o -> mapToOrderDto(o)).collect(Collectors.toList());
	}

	@Override
	public OrderDto placeOrder(Integer cartId) {
		// TODO Auto-generated method stub
		CartDto cartDto = cartApiClient.getCartByCartId(cartId);
		CustomerDto customerDto = customerApiClient.getCustomerDetails(cartDto.getCustomerId());

		Order newOrder = new Order();
		newOrder.setCustomerId(customerDto.getCustomerId());
		newOrder.setCustomerName(customerDto.getCustomerName());
		newOrder.setShippingAddress(customerDto.getShippingAddress());
		newOrder.setPrice(cartDto.getCartPrice());
		newOrder.setDelivered(false);
		newOrder.setPaid(false);

		List<OrderItem> orderItemList = cartDto.getItemsDtoList().stream().map(item -> {
			OrderItem newItem = new OrderItem();
			newItem.setProductId(item.getProductId());
			newItem.setProductName(item.getProductName());
			newItem.setProductPrice(item.getProductPrice());
			newItem.setQuantity(item.getQuantity());
			newItem.setOrder(newOrder);
			
			ProductDto productDto=productApiClient.getProductById(item.getProductId());
			if(productDto.getStock()<item.getQuantity()) {
				int decBy=item.getQuantity()-productDto.getStock();
				throw new RuntimeException("Product with ID " + item.getProductId()+" and product name with "+item.getProductName() + " is out of stock, to place the order decrease its quantity by "+decBy);
			}
			
			
			productDto.setStock(productDto.getStock()-item.getQuantity());
			productApiClient.updateProductById(productDto.getId(), productDto);
			return newItem;
		}).collect(Collectors.toList());

		newOrder.setOrderItemList(orderItemList);

		Order savedOrder = orderRepository.save(newOrder);
		OrderDto orderDto = mapToOrderDto(savedOrder);
		return orderDto;
	}

	@Override
	public OrderDto getOrderById(Integer orderId) {
		// TODO Auto-generated method stub
		Order existingOrder = orderRepository.findByOrderId(orderId);
		OrderDto orderDto=mapToOrderDto(existingOrder);
		return orderDto;
	}

	@Override
	public String deleteOrderById(Integer orderId) {
		// TODO Auto-generated method stub
		Order existingOrder = orderRepository.findByOrderId(orderId);
		if (existingOrder == null) {
			throw new RuntimeException("Order with ID " + orderId + " not found.");
		}
		
		existingOrder.getOrderItemList().stream().forEach(item->{
			ProductDto productDto=productApiClient.getProductById(item.getProductId());
			
			productDto.setStock(productDto.getStock()+item.getQuantity());
			
			productApiClient.updateProductById(item.getProductId(), productDto);
			
		});
		
		orderRepository.deleteById(orderId);
		return "Order with Id :" + orderId + " canceled ";
	}

	@Override
	public OrderDto updateOrderByIdForPayment(Integer orderId) {
		// TODO Auto-generated method stub
		Order existingOrder = orderRepository.findByOrderId(orderId);
		if (existingOrder == null) {
			throw new RuntimeException("Order with ID " + orderId + " not found.");
		}
		existingOrder.setPaid(true);
		Order updatedOrder = orderRepository.save(existingOrder);
		OrderDto orderDto=mapToOrderDto(updatedOrder);
		return orderDto;
	}

	@Override
	public OrderDto updateOrderByIdForDelivery(Integer orderId) {
		// TODO Auto-generated method stub
		Order existingOrder = orderRepository.findByOrderId(orderId);
		if (existingOrder == null) {
			throw new RuntimeException("Order with ID " + orderId + " not found.");
		}
		existingOrder.setDelivered(true);
		Order updatedOrder = orderRepository.save(existingOrder);
		OrderDto orderDto=mapToOrderDto(updatedOrder);
		return orderDto;
	}

	@Override
	public List<OrderDto> getOrderByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		List<Order> allOrdersByCustomer = orderRepository.findAllByCustomerId(customerId);

		List<OrderDto> orderDtoList = allOrdersByCustomer.stream().map(order -> {

			OrderDto newOrderDto = new OrderDto();
			newOrderDto.setCustomerId(order.getCustomerId());
			newOrderDto.setCustomerName(order.getCustomerName());
			newOrderDto.setDelivered(order.isDelivered());
			newOrderDto.setOrderId(order.getOrderId());
			newOrderDto.setPaid(order.isPaid());
			newOrderDto.setShippingAddress(order.getShippingAddress());
			newOrderDto.setPrice(order.getPrice());

			List<OrderItemDto> orderItemDtoList = order.getOrderItemList().stream().map(item -> {
				OrderItemDto itemDto = new OrderItemDto();
				itemDto.setOrderItemId(item.getOrderItemId());
				itemDto.setProductId(item.getProductId());
				itemDto.setProductName(item.getProductName());
				itemDto.setProductPrice(item.getProductPrice());
				itemDto.setQuantity(item.getQuantity());

				return itemDto;
			}).collect(Collectors.toList());

			newOrderDto.setOrderItemDtoList(orderItemDtoList);

			return newOrderDto;

		}).collect(Collectors.toList());

		return orderDtoList;
	}

	@Override
	public List<OrderDto> getOrderByPaid(boolean paid) {
		// TODO Auto-generated method stub
		List<Order> orderList = orderRepository.findAll();
		List<Order> paidOrders = orderList.stream().filter(o -> o.isPaid() == paid).collect(Collectors.toList());

		List<OrderDto> orderDtoList = paidOrders.stream().map(order -> {

			OrderDto newOrderDto = new OrderDto();
			newOrderDto.setCustomerId(order.getCustomerId());
			newOrderDto.setCustomerName(order.getCustomerName());
			newOrderDto.setDelivered(order.isDelivered());
			newOrderDto.setOrderId(order.getOrderId());
			newOrderDto.setPaid(order.isPaid());
			newOrderDto.setShippingAddress(order.getShippingAddress());
			newOrderDto.setPrice(order.getPrice());

			List<OrderItemDto> orderItemDtoList = order.getOrderItemList().stream().map(item -> {
				OrderItemDto itemDto = new OrderItemDto();
				itemDto.setOrderItemId(item.getOrderItemId());
				itemDto.setProductId(item.getProductId());
				itemDto.setProductName(item.getProductName());
				itemDto.setProductPrice(item.getProductPrice());
				itemDto.setQuantity(item.getQuantity());

				return itemDto;
			}).collect(Collectors.toList());

			newOrderDto.setOrderItemDtoList(orderItemDtoList);

			return newOrderDto;

		}).collect(Collectors.toList());

		return orderDtoList;
	}

	@Override
	public List<OrderDto> getOrderByDelivery(boolean delivered) {
		// TODO Auto-generated method stub
		List<Order> orderList = orderRepository.findAll();
		List<Order> deliveredOrders = orderList.stream().filter(o -> o.isDelivered() == delivered).collect(Collectors.toList());

		List<OrderDto> orderDtoList = deliveredOrders.stream().map(order -> {

			OrderDto newOrderDto = new OrderDto();
			newOrderDto.setCustomerId(order.getCustomerId());
			newOrderDto.setCustomerName(order.getCustomerName());
			newOrderDto.setDelivered(order.isDelivered());
			newOrderDto.setOrderId(order.getOrderId());
			newOrderDto.setPaid(order.isPaid());
			newOrderDto.setShippingAddress(order.getShippingAddress());
			newOrderDto.setPrice(order.getPrice());

			List<OrderItemDto> orderItemDtoList = order.getOrderItemList().stream().map(item -> {
				OrderItemDto itemDto = new OrderItemDto();
				itemDto.setOrderItemId(item.getOrderItemId());
				itemDto.setProductId(item.getProductId());
				itemDto.setProductName(item.getProductName());
				itemDto.setProductPrice(item.getProductPrice());
				itemDto.setQuantity(item.getQuantity());

				return itemDto;
			}).collect(Collectors.toList());

			newOrderDto.setOrderItemDtoList(orderItemDtoList);

			return newOrderDto;

		}).collect(Collectors.toList());

		return orderDtoList;
	}

}
