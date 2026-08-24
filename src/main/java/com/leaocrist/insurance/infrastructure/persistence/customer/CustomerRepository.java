package com.leaocrist.insurance.infrastructure.persistence.customer;

import com.leaocrist.insurance.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

//this repository works with customer entities and identifier is Long type.
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
