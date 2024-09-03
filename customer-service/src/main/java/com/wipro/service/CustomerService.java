package com.wipro.service;

import java.util.List;

import com.wipro.dto.CustomerDto;

public interface CustomerService {
	public CustomerDto createCustomer(CustomerDto customerDto);
	public CustomerDto updateCustomerDetails(Integer customerId ,CustomerDto customerDto);
	public List<CustomerDto> getAllCustomer();
	public CustomerDto getCustomerById(Integer customerId);
}
