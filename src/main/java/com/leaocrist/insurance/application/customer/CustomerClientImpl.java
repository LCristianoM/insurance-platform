package com.leaocrist.insurance.application.customer;

import com.leaocrist.insurance.application.policy.CustomerServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

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
        try {
            Boolean exists =
                    restClient.get()
                    .uri(customerServiceUrl + "/customers/{id}/exists", customerId)
                    .retrieve()
                    .body(Boolean.class);
            return Boolean.TRUE.equals(exists);

        } catch (RestClientException e) {
            throw new CustomerServiceUnavailableException(e.getMessage());
        }
    }
}
