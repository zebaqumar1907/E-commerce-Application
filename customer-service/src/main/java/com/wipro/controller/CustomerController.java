package com.wipro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.CustomerDto;
import com.wipro.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customer")
	public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
		return customerService.createCustomer(customerDto);
	}
	
	@GetMapping("/customer")
	public List<CustomerDto> getAllCustomers(){
		return customerService.getAllCustomer();
	}
	
	@PutMapping("/customer/{customerId}")
	public CustomerDto updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody CustomerDto customerDto) {
		return customerService.updateCustomerDetails(customerId, customerDto);
	}
	
	@GetMapping("/customer/{customerId}")
	public CustomerDto getCustomerDetails(@PathVariable("customerId") Integer customerId) {
		return customerService.getCustomerById(customerId);
	}
	
}
