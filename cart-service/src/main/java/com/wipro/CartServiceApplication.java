package com.wipro;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.wipro.config.FeignClientConfig;
import com.wipro.dto.CartDto;
import com.wipro.dto.ItemEntryDto;
import com.wipro.entity.Cart;
import com.wipro.entity.ItemEntry;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = FeignClientConfig.class)
public class CartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartServiceApplication.class, args);
	}
	
	 @Bean
	    public ModelMapper modelMapper() {
	        ModelMapper modelMapper = new ModelMapper();

	        modelMapper.typeMap(Cart.class, CartDto.class).addMappings(mapper -> {
	            mapper.map(Cart::getItemsList, CartDto::setItemsDtoList);
	        });

	        modelMapper.typeMap(ItemEntry.class, ItemEntryDto.class).addMappings(mapper -> {
	            mapper.map(ItemEntry::getProductId, ItemEntryDto::setProductId);
	            mapper.map(ItemEntry::getProductName, ItemEntryDto::setProductName);
	            mapper.map(ItemEntry::getProductPrice, ItemEntryDto::setProductPrice);
	            mapper.map(ItemEntry::getQuantity, ItemEntryDto::setQuantity);
	        });

	        return modelMapper;
	    }

}
