package com.leaocrist.insurance.application.risk;

import com.leaocrist.insurance.application.policy.RiskServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class RiskClientImpl implements RiskClient{
    private final RestClient restClient;
    @Value("${risk.service.url}")
    private String riskServiceUrl;

    public RiskClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    @CircuitBreaker(name="riskService", fallbackMethod = "existsByIdFallback")
    public boolean existsById(Long riskId) {
        try {
            Boolean exists =
                    restClient.get()
                            .uri(riskServiceUrl + "/risks/{id}/exists", riskId)
                            .retrieve()
                            .body(Boolean.class);
            return Boolean.TRUE.equals(exists);
        }catch (RestClientException e) {
            throw new RiskServiceUnavailableException(e.getMessage());
        }
    }

    public boolean existsByIdFallback(Long riskId, Throwable t){
        System.out.println("## ms Risk Circuit breaker fallback :" + t.getClass().getSimpleName());
        throw new RiskServiceUnavailableException(
                "risk-service unavailable (circuit open or call failed: " + t.getMessage()
        );
    }
}
