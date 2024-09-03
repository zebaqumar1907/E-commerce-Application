package com.wipro.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.config.FeignClientConfig;
import com.wipro.dto.CustomerDto;

@FeignClient(name = "customer-service",configuration = FeignClientConfig.class)
public interface CustomerApiClient {
	@GetMapping("api/customer/{customerId}")
	public CustomerDto getCustomerDetails(@PathVariable("customerId") Integer customerId);
}
