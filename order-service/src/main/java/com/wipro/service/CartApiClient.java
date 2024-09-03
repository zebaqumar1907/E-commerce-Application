package com.wipro.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.config.FeignClientConfig;
import com.wipro.dto.CartDto;

@FeignClient(name = "cart-service",configuration = FeignClientConfig.class)
public interface CartApiClient {
	
	@GetMapping("api/getcartid/{cartId}")
	public CartDto getCartByCartId(@PathVariable("cartId") Integer cartId);
	
	@DeleteMapping("api/cart/{cartId}")
	public String deleteWholeCart(@PathVariable("cartId") Integer cartId);
}