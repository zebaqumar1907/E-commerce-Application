package com.wipro.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.dto.CartDto;
import com.wipro.dto.ProductDto;
import com.wipro.entity.Cart;
import com.wipro.entity.ItemEntry;
import com.wipro.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ProductApiClient productApiClient;

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CartDto addToCart(Integer customerId, Long productId) {
		// TODO Auto-generated method stub
		ProductDto productDto=productApiClient.getProductById(productId);
		if(productDto==null) {
			throw new RuntimeException("Product with ID " + productId + " not found.");
        }
		
		
		Cart cart=cartRepository.findByCustomerId(customerId);
		if(cart == null) {
			cart=new Cart();
			cart.setCustomerId(customerId);
			cart.setItemsList(new ArrayList<>());
		}
		
		Optional<ItemEntry> existingItemEntry=cart.getItemsList().stream().filter(i->i.getProductId().equals(productId)).findFirst();
		
		if(existingItemEntry.isPresent()) {
			ItemEntry item=existingItemEntry.get();
			item.setProductName(productDto.getName());
			item.setProductPrice(productDto.getPrice());
			item.setQuantity(item.getQuantity()+1);
		}else {
			ItemEntry newItem=new ItemEntry();
			newItem.setProductId(productId);
			newItem.setQuantity(1);
			newItem.setProductName(productDto.getName());
			newItem.setProductPrice(productDto.getPrice());
			newItem.setCart(cart);
			cart.getItemsList().add(newItem);
		}
		
		 BigDecimal totalPrice = cart.getItemsList().stream()
		            .map(item -> BigDecimal.valueOf(item.getProductPrice())
		                    .multiply(BigDecimal.valueOf(item.getQuantity())))
		            .reduce(BigDecimal.ZERO, BigDecimal::add);

		    cart.setCartPrice(totalPrice);
		
		
		Cart savedCart=cartRepository.save(cart);
		return mapper.map(savedCart, CartDto.class);
	}

	@Override
	public CartDto removeFromCart(Integer customerId, Long productId) {
		// TODO Auto-generated method stub
		Cart existingCart=cartRepository.findByCustomerId(customerId);
		if(existingCart==null) {
			throw new RuntimeException("You Dont have any cart Items");
		}
		
		ProductDto productDto=productApiClient.getProductById(productId);
		if(productDto==null) {
			throw new RuntimeException("Product with ID " + productId + " not found.");
        }
		
		Optional<ItemEntry> existingItem=existingCart.getItemsList().stream().filter(i->i.getProductId().equals(productId)).findFirst();
		
		if(existingItem.isPresent()) {
			
			ItemEntry item=existingItem.get();
			if(item.getQuantity()>1) {
				item.setQuantity(item.getQuantity()-1);
				item.setProductId(productId);
				item.setProductName(productDto.getName());
				item.setProductPrice(productDto.getPrice());
			}else {
				existingCart.getItemsList().remove(item);
			}
		}else {
			throw new RuntimeException("Product with ID " + productId + " not found in the cart.");
		}
		    
		 BigDecimal totalPrice = existingCart.getItemsList().stream()
		            .map(entry -> BigDecimal.valueOf(entry.getProductPrice())
		                    .multiply(BigDecimal.valueOf(entry.getQuantity())))
		            .reduce(BigDecimal.ZERO, BigDecimal::add);

		    existingCart.setCartPrice(totalPrice);
		
		Cart updatedCart=cartRepository.save(existingCart);
		
		return mapper.map(updatedCart, CartDto.class);
	}

	@Override
	@Transactional
	public String deleteEntireCart(Integer cartId) {
		// TODO Auto-generated method stub
		 Cart existingCart = cartRepository.findByCartId(cartId);
		 
		 if(existingCart==null) {
			 throw new RuntimeException("cart with ID " + cartId + " not found");
		 }
			        
		 cartRepository.deleteByCartId(cartId);
		return "Cart Deleted Successfully";
	}

	@Override
	public CartDto getCartItems(Integer customerId) {
		// TODO Auto-generated method stub
		
		Cart existingCart=cartRepository.findByCustomerId(customerId);
		if(existingCart == null) {
			  throw new RuntimeException("No cart found for customer with ID " + customerId);
		}
		
		return mapper.map(existingCart, CartDto.class);
	}

	@Override
	public CartDto getCartById(Integer cartId) {
		// TODO Auto-generated method stub
		Cart existingCart=cartRepository.findByCartId(cartId);
		if(existingCart==null) {
			throw new RuntimeException("No cart found for cart ID " + cartId);
		}
		
		return mapper.map(existingCart, CartDto.class);
	}

}
