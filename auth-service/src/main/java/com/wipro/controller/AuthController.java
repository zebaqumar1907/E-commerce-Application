package com.wipro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.LoginDto;
import com.wipro.dto.UserDto;
import com.wipro.entity.User;
import com.wipro.service.AuthService;
import com.wipro.service.JwtService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	 @Autowired
	    private AuthService authService;

	    @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtService jwtService;
	    
	    
	    @PostMapping("/register")
	    public User registerUser(@RequestBody UserDto userDto) {
	        return authService.registerUser(userDto);
	    }

	   @PostMapping("/token")
	   public String getToken(@RequestBody LoginDto loginDto) {
		    Authentication authenticate =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getPassword()));
		   if(authenticate.isAuthenticated()) {
			   return authService.generateToken(loginDto.getUserEmail());
		   }else {
			   throw new RuntimeException("invalid access");
		   }
		   
	   }
	   
	   @GetMapping("/validate")
	   public String validateToken(@RequestParam("token") String token) {
		   authService.validateToken(token);
		   return "Token is valid";
	   }
}
