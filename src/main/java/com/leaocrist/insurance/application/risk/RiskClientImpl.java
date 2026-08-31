package com.leaocrist.insurance.application.risk;

import com.leaocrist.insurance.infrastructure.persistence.risk.RiskRepository;
import org.springframework.stereotype.Service;

@Service
public class RiskClientImpl implements RiskClient{

    private final RiskRepository riskRepository;

    public RiskClientImpl(RiskRepository riskRepository){
        this.riskRepository = riskRepository;
    }

    @Override
    public boolean existsById(Long riskId) {
        return riskRepository.existsById(riskId);
    }
}
