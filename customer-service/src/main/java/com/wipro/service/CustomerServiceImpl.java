package com.wipro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.dto.CustomerDto;
import com.wipro.entity.Customer;
import com.wipro.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		Customer customer=new Customer(customerDto.getCustomerId(),customerDto.getCustomerName(),customerDto.getCustomerEmail(),customerDto.getShippingAddress());
		Customer savedCustomer=customerRepository.save(customer);
		
		return mapper.map(savedCustomer, CustomerDto.class);
	}

	@Override
	public CustomerDto updateCustomerDetails(Integer customerId, CustomerDto customerDto) {
		// TODO Auto-generated method stub
		Customer existingCustomer=customerRepository.findById(customerId).orElseThrow(()->new RuntimeException("Cutomer with customerId :"+customerId+" Not found"));
		existingCustomer.setCustomerEmail(customerDto.getCustomerEmail());
		existingCustomer.setCustomerName(customerDto.getCustomerName());
		existingCustomer.setShippingAddress(customerDto.getShippingAddress());
		
		Customer savedCustomer=customerRepository.save(existingCustomer);
		
		return mapper.map(savedCustomer, CustomerDto.class);
	}

	@Override
	public List<CustomerDto> getAllCustomer() {
		// TODO Auto-generated method stub
		
		List<Customer> customerList=customerRepository.findAll();
		
		return customerList.stream().map(c->mapper.map(c, CustomerDto.class)).collect(Collectors.toList());
	}

	@Override
	public CustomerDto getCustomerById(Integer customerId) {
		// TODO Auto-generated method stub
		Customer existingCustomer=customerRepository.findByCustomerId(customerId);
		if(existingCustomer==null) {
			throw new RuntimeException("Customer with ID " + customerId + " not found.");
		}
		return mapper.map(existingCustomer, CustomerDto.class);
	}

}
