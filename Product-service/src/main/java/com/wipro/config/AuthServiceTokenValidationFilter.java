package com.wipro.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class AuthServiceTokenValidationFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final String authServiceUrl;

    public AuthServiceTokenValidationFilter(RestTemplate restTemplate, String authServiceUrl) {
        this.restTemplate = restTemplate;
        this.authServiceUrl = authServiceUrl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No token found in the request or invalid token format");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            System.out.println("Validating token: " + jwt);
            ResponseEntity<String> validationResponse = restTemplate.getForEntity(
                    authServiceUrl + "/api/auth/validate?token=" + jwt,
                    String.class
            );

            System.out.println("Token validation response: " + validationResponse.getStatusCode());

            if (validationResponse.getStatusCode() == HttpStatus.OK) {
                System.out.println("Token validation successful");

                // Extract username from JWT (you might need to implement this method)
                String username = extractUsernameFromJwt(jwt);

             // In the doFilterInternal method, modify the authentication creation:
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, jwt, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
                // Set details
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("Authentication set in SecurityContext for user: " + username);

                filterChain.doFilter(request, response);
            } else {
                System.out.println("Token validation failed. Status: " + validationResponse.getStatusCode());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println("Error during token validation: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String extractUsernameFromJwt(String jwt) {
        String[] parts = jwt.split("\\.");
        if (parts.length == 3) {
            String payload = new String(java.util.Base64.getDecoder().decode(parts[1]));
            return payload.split("\"sub\":\"")[1].split("\"")[0];
        }
        return null;
    }
}