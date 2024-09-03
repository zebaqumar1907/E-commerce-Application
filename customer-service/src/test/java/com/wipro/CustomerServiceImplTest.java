package com.wipro;

import com.wipro.service.CustomerServiceImpl;
import com.wipro.dto.CustomerDto;
import com.wipro.dto.LoginDto;
import com.wipro.entity.Customer;
import com.wipro.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @MockBean
    private RestTemplate restTemplate;

    private String authToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authToken = generateToken();
    }

    private String generateToken() {
        LoginDto loginDto = new LoginDto("test@example.com", "password");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("mocked-token", HttpStatus.OK);
        when(restTemplate.postForEntity(eq("http://localhost:9092//api/auth/token"), eq(loginDto), eq(String.class)))
                .thenReturn(responseEntity);
        return responseEntity.getBody();
    }

    @Test
    void createCustomer() {
        CustomerDto inputDto = new CustomerDto(1, "John Doe", "john@example.com", "123 Main St");
        Customer savedCustomer = new Customer(1, "John Doe", "john@example.com", "123 Main St");
        CustomerDto expectedDto = new CustomerDto(1, "John Doe", "john@example.com", "123 Main St");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(modelMapper.map(savedCustomer, CustomerDto.class)).thenReturn(expectedDto);

        CustomerDto result = customerService.createCustomer(inputDto);

        assertNotNull(result);
        assertEquals(expectedDto.getCustomerId(), result.getCustomerId());
        assertEquals(expectedDto.getCustomerName(), result.getCustomerName());
        assertEquals(expectedDto.getCustomerEmail(), result.getCustomerEmail());
        assertEquals(expectedDto.getShippingAddress(), result.getShippingAddress());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomerDetails() {
        Integer customerId = 1;
        CustomerDto inputDto = new CustomerDto(customerId, "John Updated", "john.updated@example.com", "456 New St");
        Customer existingCustomer = new Customer(customerId, "John Doe", "john@example.com", "123 Main St");
        Customer updatedCustomer = new Customer(customerId, "John Updated", "john.updated@example.com", "456 New St");
        CustomerDto expectedDto = new CustomerDto(customerId, "John Updated", "john.updated@example.com", "456 New St");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
        when(modelMapper.map(updatedCustomer, CustomerDto.class)).thenReturn(expectedDto);

        CustomerDto result = customerService.updateCustomerDetails(customerId, inputDto);

        assertNotNull(result);
        assertEquals(expectedDto.getCustomerId(), result.getCustomerId());
        assertEquals(expectedDto.getCustomerName(), result.getCustomerName());
        assertEquals(expectedDto.getCustomerEmail(), result.getCustomerEmail());
        assertEquals(expectedDto.getShippingAddress(), result.getShippingAddress());

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void getAllCustomer() {
        List<Customer> customerList = Arrays.asList(
                new Customer(1, "John Doe", "john@example.com", "123 Main St"),
                new Customer(2, "Jane Doe", "jane@example.com", "456 Elm St")
        );
        List<CustomerDto> expectedDtoList = Arrays.asList(
                new CustomerDto(1, "John Doe", "john@example.com", "123 Main St"),
                new CustomerDto(2, "Jane Doe", "jane@example.com", "456 Elm St")
        );

        when(customerRepository.findAll()).thenReturn(customerList);
        when(modelMapper.map(any(Customer.class), eq(CustomerDto.class))).thenReturn(expectedDtoList.get(0), expectedDtoList.get(1));

        List<CustomerDto> result = customerService.getAllCustomer();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDtoList, result);

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById() {
        Integer customerId = 1;
        Customer existingCustomer = new Customer(customerId, "John Doe", "john@example.com", "123 Main St");
        CustomerDto expectedDto = new CustomerDto(customerId, "John Doe", "john@example.com", "123 Main St");

        when(customerRepository.findByCustomerId(customerId)).thenReturn(existingCustomer);
        when(modelMapper.map(existingCustomer, CustomerDto.class)).thenReturn(expectedDto);

        CustomerDto result = customerService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals(expectedDto.getCustomerId(), result.getCustomerId());
        assertEquals(expectedDto.getCustomerName(), result.getCustomerName());
        assertEquals(expectedDto.getCustomerEmail(), result.getCustomerEmail());
        assertEquals(expectedDto.getShippingAddress(), result.getShippingAddress());

        verify(customerRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void getCustomerById_NotFound() {
        Integer customerId = 999;

        when(customerRepository.findByCustomerId(customerId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(customerId));

        verify(customerRepository, times(1)).findByCustomerId(customerId);
    }
}