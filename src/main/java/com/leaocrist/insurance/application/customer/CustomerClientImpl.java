package com.leaocrist.insurance.application.customer;

import com.leaocrist.insurance.infrastructure.persistence.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerClientImpl implements CustomerClient {

    private final RestClient restClient;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    public CustomerClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public boolean existsById(Long customerId) {
        return Boolean.TRUE.equals(
                restClient.get()
                        .uri(customerServiceUrl + "/customers/{id}/exists", customerId)
                        .retrieve()
                        .body(Boolean.class)
        );
    }


   /* private final CustomerRepository customerRepository;

    public CustomerClientImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean existsById(Long customerId) {
        return customerRepository.existsById(customerId);
    }*/
}
