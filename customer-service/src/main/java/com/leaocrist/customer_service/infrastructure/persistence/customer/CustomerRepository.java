package com.leaocrist.customer_service.infrastructure.persistence.customer;

import com.leaocrist.customer_service.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

//this repository works with customer entities and identifier is Long type.
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
