package com.wipro.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.config.FeignClientConfig;
import com.wipro.dto.ProductDto;
@FeignClient(name = "Product-service", configuration = FeignClientConfig.class)

public interface ProductApiClient {
	
	@GetMapping("/api/product/{id}")
	public ProductDto getProductById (@PathVariable("id") Long id);
	
}