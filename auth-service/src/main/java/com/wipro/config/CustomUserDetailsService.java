package com.wipro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wipro.entity.User;
import com.wipro.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	 @Autowired
	 private UserRepository userRepository;
	 
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 User user = userRepository.findByUserEmail(userEmail)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userEmail));

			/*
			 * return org.springframework.security.core.userdetails.User.withUsername(user.
			 * getUserEmail()) .password(user.getPassword())
			 * .roles(user.getRole().replace("ROLE_", "")) .build();
			 */
		 return new CustomUserDetails(user);
	}

}
