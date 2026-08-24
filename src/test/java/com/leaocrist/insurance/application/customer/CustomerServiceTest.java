package com.leaocrist.insurance.application.customer;

import com.leaocrist.insurance.application.customer.dto.CustomerRequest;
import com.leaocrist.insurance.application.customer.dto.CustomerResponse;
import com.leaocrist.insurance.domain.customer.Customer;
import com.leaocrist.insurance.infrastructure.persistence.customer.CustomerRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Test
    void shouldCreateCustomer(){

        CustomerRepository customerRepository = mock(CustomerRepository.class);

        Customer savedCustomer = new Customer(
                "Leandro",
                "0989743",
                "leandro@correo.com"
        );

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(savedCustomer);

        CustomerService customerService = new CustomerService(customerRepository);

        CustomerRequest request = new CustomerRequest(
                "Leandro",
                "0989743",
                "leandro@correo.com"
        );

        CustomerResponse result = customerService.createCustomer(request);

        assertEquals("Leandro", result.name());
        assertEquals("0989743", result.phone());
        assertEquals("leandro@correo.com", result.email());
    }

    @Test
    void shouldUpdateCustomer(){

        CustomerRepository customerRepository = mock(CustomerRepository.class);

        Customer customer = new Customer(
                "Leandro",
                "0989743",
                "leandro@correo.com"
        );

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(customer))
                .thenReturn(customer);

        CustomerService customerService = new CustomerService(customerRepository);

        CustomerRequest request = new CustomerRequest(
                "Leandro updated",
                "123456789",
                "updated@correo.com"
        );

        CustomerResponse result = customerService.updateCustomer(1L, request);

        assertEquals("Leandro updated", result.name());
        assertEquals("123456789", result.phone());
        assertEquals("updated@correo.com", result.email());

        verify(customerRepository).findById(1L);
        verify(customerRepository).save(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerDoesNotExist(){
        CustomerRepository customerRepository = mock(CustomerRepository.class);

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        CustomerService customerService = new CustomerService(customerRepository);

        CustomerRequest request = new CustomerRequest(
                "Leandro",
                "0989743",
                "leo@correo.com"
        );

        assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.updateCustomer(99L, request)
        );
        verify(customerRepository).findById(99L);
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void shouldDeleteCustomer(){

        CustomerRepository customerRepository = mock(CustomerRepository.class);

        Customer customer = new Customer(
                "Leandro",
                "0989743",
                "leandro@correo.com"
        );

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        CustomerService customerService = new CustomerService(customerRepository);

        customerService.deleteCustomer(1L);

        verify(customerRepository).findById(1L);
        verify(customerRepository).delete(customer);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingCustomer(){

        CustomerRepository customerRepository = mock(CustomerRepository.class);

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        CustomerService customerService = new CustomerService(customerRepository);

        assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.deleteCustomer(99L)
        );

        verify(customerRepository).findById(99L);
        verify(customerRepository, never()).delete(any(Customer.class));
    }
}
