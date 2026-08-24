package com.leaocrist.insurance.infrastructure.persistence.customer;

import com.leaocrist.insurance.domain.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldSaveAndFindCustomer() {

        Customer customer = new Customer(
                "Leandro",
                "3001234567",
                "leandro@email.com"
        );

        Customer savedCustomer = customerRepository.save(customer);

        Optional<Customer> foundCustomer =
                customerRepository.findById(savedCustomer.getId());

        assertTrue(foundCustomer.isPresent());

        assertEquals(
                "Leandro",
                foundCustomer.get().getName()
        );
    }


    @Test
    void shouldUpdateCustomerInformation(){
        Customer customer = new Customer(
                "Joe Golberg",
                "2345678",
                "joe@email.com"
        );
        customer.updateInformation(
                "Jane",
                "987548475",
                "jane@email.com"
        );
        assertEquals("Jane", customer.getName());
        assertEquals("987548475", customer.getPhone());
        assertEquals("jane@email.com", customer.getEmail());
    }
}
