package com.wipro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.ProductDto;
import com.wipro.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/product")
	public ProductDto createProducts(@RequestBody ProductDto prodcutDto) {
		return productService.createProduct(prodcutDto);
	}
	
	@GetMapping("/product/{id}")
	public ProductDto getProductById(@PathVariable("id") Long id) {
		return productService.getProductById(id);
	}
	
	@GetMapping("/getallproducts")
	public List<ProductDto> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/getproductsbycategory/query")
	public List<ProductDto> getProductsByCategory(@RequestParam String category){
		return productService.getProductByCategory(category);		
	}
	
	@DeleteMapping("/product/{id}")
	public String deleteProductById(@PathVariable("id") Long id) {
		return productService.deleteProductById(id);
	}
	
	@PutMapping("/product/{id}")
	public ProductDto updateProductById(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
		return productService.updateProduct(id, productDto);
	}
	
	@GetMapping("/getproductsbyavailability/query")
	public List<ProductDto> getProductsByAvailability(@RequestParam boolean available){
		return productService.getProductByAvailabilty(available);
	}
}
