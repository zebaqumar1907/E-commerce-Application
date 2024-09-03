package com.wipro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	public Cart findByCustomerId(Integer customerId);
	public Cart findByCartId(Integer cartId);
	public String deleteByCartId(Integer cartId);
}
