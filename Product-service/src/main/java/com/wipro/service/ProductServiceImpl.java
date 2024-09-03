package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.dto.ProductDto;
import com.wipro.entity.Product;
import com.wipro.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRespository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<ProductDto> getAllProducts() {
		// TODO Auto-generated method stub
		List<Product> productList=productRespository.findAll();
		return productList.stream().map(p->mapper.map(p, ProductDto.class)).collect(Collectors.toList());
	}

	@Override
	public ProductDto getProductById(long id) {
		// TODO Auto-generated method stub
		Product product=productRespository.findById(id).orElseThrow(()->new RuntimeException("Product not found with Id : "+id));
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public String deleteProductById(long id) {
		// TODO Auto-generated method stub
		productRespository.deleteById(id);
		return "Product with Id :"+id +" deleted successfully";
		

	}

	@Override
	public ProductDto updateProduct(long id, ProductDto productDto) {
		// TODO Auto-generated method stub
		Product existingProduct=productRespository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with Id : "+id));
		existingProduct.setCategory(productDto.getCategory());
		existingProduct.setDescription(productDto.getDescription());
		existingProduct.setName(productDto.getName());
		existingProduct.setPrice(productDto.getPrice());
		existingProduct.setStock(productDto.getStock());
		existingProduct.setAvailable(productDto.isAvailable());
		
		Product SavedProduct=productRespository.save(existingProduct);
		
		return mapper.map(SavedProduct, ProductDto.class);
	}

	@Override
	public ProductDto createProduct(ProductDto productDto) {
		// TODO Auto-generated method stub
		
		Product newProduct=new Product(productDto.getId(),productDto.getName(),productDto.getDescription(),productDto.getStock(),productDto.getPrice(),productDto.getCategory(),productDto.isAvailable());
		Product savedProduct=productRespository.save(newProduct);
		
		return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public List<ProductDto> getProductByCategory(String category) {
		// TODO Auto-generated method stub
		
		List<Product> productList=productRespository.findByCategory(category);
		
		return productList.stream().map(p->mapper.map(p, ProductDto.class)).collect(Collectors.toList());
	}


	@Override
	public List<ProductDto> getProductByAvailabilty(boolean available) {
		// TODO Auto-generated method stub
		List<Product> productList=productRespository.findByAvailable(available);
		return productList.stream().map(p->mapper.map(p, ProductDto.class)).collect(Collectors.toList());
	}

}
