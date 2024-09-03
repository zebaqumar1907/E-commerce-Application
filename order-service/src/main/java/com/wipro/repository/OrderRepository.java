package com.wipro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	public List<Order> findAllByCustomerId(Integer customerId);
	public List<Order> findByPaid(boolean paid);
	public List<Order> findByDelivered(boolean delivered);
	public Order findByOrderId(Integer orderId);
}
