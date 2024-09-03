package com.wipro.service;

import com.wipro.dto.CartDto;

public interface CartService {
	public CartDto addToCart(Integer customerId, Long productId);
	public CartDto removeFromCart(Integer customerId, Long productId);
	public String deleteEntireCart(Integer cartId);
	public CartDto getCartItems(Integer customerId);
	public CartDto getCartById(Integer cartId);
}
