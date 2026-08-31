package com.leaocrist.insurance.application.risk;

import com.leaocrist.insurance.infrastructure.persistence.risk.RiskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RiskClientImpl implements RiskClient{

    private final RestClient restClient;

    @Value("${risk.service.url}")
    private String riskServiceUrl;

    public RiskClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public boolean existsById(Long riskId) {
        return Boolean.TRUE.equals(
                restClient.get()
                        .uri(riskServiceUrl + "/risks/{id}/exists", riskId)
                        .retrieve()
                        .body(Boolean.class)
        );
    }
}
