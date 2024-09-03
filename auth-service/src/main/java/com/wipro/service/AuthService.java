package com.wipro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.dto.CustomerDto;
import com.wipro.dto.UserDto;
import com.wipro.entity.User;
import com.wipro.repository.UserRepository;

@Service
public class AuthService {
	
		@Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private JwtService jwtService;
	    
	    @Autowired
	    private CustomerApiClient customerApiClient;

	    public User registerUser(UserDto userDto) {
	        User user = new User();
	        user.setUserName(userDto.getUserName());
	        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
	        user.setRole("ROLE_USER"); // Default role for a new user
	        user.setShippingAddress(userDto.getShippingAddress());
	        user.setUserEmail(userDto.getUserEmail());
	        CustomerDto customerDto=new CustomerDto();
	        customerDto.setCustomerEmail(userDto.getUserEmail());
	        customerDto.setCustomerName(userDto.getUserName());
	        customerDto.setShippingAddress(userDto.getShippingAddress());
	        
	        customerApiClient.createCustomer(customerDto);
	        
	        return userRepository.save(user);
	    }

//	    public User findByUsername(String userEmail) {
//	        return userRepository.findByUserEmail(userEmail).orElse(null);
//	    }
//	    
	    public String generateToken(String userEmail) {
	    	return jwtService.generateToken(userEmail);
	    }
	    
	    public void validateToken(String token) {
	    	jwtService.validateToken(token);
	    }
}
