package com.leaocrist.customer_service.application.customer;

import com.leaocrist.customer_service.application.customer.dto.CustomerRequest;
import com.leaocrist.customer_service.application.customer.dto.CustomerResponse;
import com.leaocrist.customer_service.domain.customer.Customer;
import com.leaocrist.customer_service.infrastructure.persistence.customer.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CustomerRequest request){
        Customer customer = new Customer(
                request.name(),
                request.phone(),
                request.email()
        );

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getPhone(),
                savedCustomer.getEmail()
        );
    }

    public CustomerResponse findCustomerById(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException(id));

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail()
        );
    }

    public Page<CustomerResponse> getCustomers(Pageable pageable){
        return customerRepository.findAll(pageable)
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmail()
                ));
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest request){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException(id));

        customer.updateInformation(
                request.name(),
                request.phone(),
                request.email()
        );

        Customer updateCustomer = customerRepository.save(customer);

        return new CustomerResponse(
                updateCustomer.getId(),
                updateCustomer.getName(),
                updateCustomer.getPhone(),
                updateCustomer.getEmail()
        );
    }

    public boolean existsById(Long id){
        return customerRepository.existsById(id);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(customer);
    }
}
