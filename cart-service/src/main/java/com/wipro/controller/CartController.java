package com.wipro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.CartDto;
import com.wipro.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/cart/{customerId}")
	public CartDto addProductInCart(@PathVariable("customerId") Integer customerId, @RequestBody Long productId) {
		return cartService.addToCart(customerId, productId);
	}
	
	@PutMapping("/cart/{customerId}")
	public CartDto removeProductFromCart(@PathVariable("customerId") Integer customerId, @RequestBody Long productId) {
		return cartService.removeFromCart(customerId, productId);
	}
	
	@DeleteMapping("/cart/{cartId}")
	public String deleteWholeCart(@PathVariable("cartId") Integer cartId) {
		return cartService.deleteEntireCart(cartId);
	}
	
	@GetMapping("/cart/{customerId}")
	public CartDto getCartByCutomer(@PathVariable("customerId") Integer customerId) {
		return cartService.getCartItems(customerId);
	}
	
	@GetMapping("/getcartid/{cartId}")
	public CartDto getCartByCartId(@PathVariable("cartId") Integer cartId) {
		return cartService.getCartById(cartId);
	}
}

