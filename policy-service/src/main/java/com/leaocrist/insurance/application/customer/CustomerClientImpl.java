package com.leaocrist.insurance.application.customer;

import com.leaocrist.insurance.application.policy.CustomerServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CustomerClientImpl implements CustomerClient {
    private final RestClient restClient;
    @Value("${customer.service.url}")
    private String customerServiceUrl;

    public CustomerClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    @CircuitBreaker(name ="customerService", fallbackMethod = "existsByIdFallback")
    public boolean existsById(Long customerId){
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

    public boolean existsByIdFallback(Long customerId, Throwable t) {
        System.out.println("## Circuit breaker fallback> " + t.getClass().getSimpleName());
        throw new CustomerServiceUnavailableException(
                "customer-service unavailable (circuit open or call failed): " + t.getMessage()
        );
    }
}
