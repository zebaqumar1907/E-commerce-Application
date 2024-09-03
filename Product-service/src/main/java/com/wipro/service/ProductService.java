package com.wipro.service;

import java.util.List;

import com.wipro.dto.ProductDto;

public interface ProductService {
	public List<ProductDto> getAllProducts();
	public ProductDto getProductById(long id);
	public String deleteProductById(long id);
	public ProductDto updateProduct(long id,ProductDto productDto);
	public ProductDto createProduct(ProductDto productDto);
	public List<ProductDto> getProductByCategory(String category);
	public List<ProductDto> getProductByAvailabilty(boolean available);
	
}
