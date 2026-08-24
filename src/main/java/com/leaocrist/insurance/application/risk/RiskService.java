package com.leaocrist.insurance.application.risk;

import com.leaocrist.insurance.application.risk.dto.RiskRequest;
import com.leaocrist.insurance.application.risk.dto.RiskResponse;
import com.leaocrist.insurance.domain.risk.Risk;
import com.leaocrist.insurance.infrastructure.persistence.risk.RiskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RiskService {

    private final RiskRepository riskRepository;

    public RiskService(RiskRepository riskRepository){
        this.riskRepository = riskRepository;
    }

    @Transactional
    public RiskResponse createRisk(RiskRequest request){
        Risk risk = new Risk(request.type(), request.description());
        Risk savedRisk = riskRepository.save(risk);

        return new RiskResponse(savedRisk.getId(), savedRisk.getType(), savedRisk.getDescription());
    }

    @Transactional(readOnly = true)
    public RiskResponse findRiskById(Long id){
        Risk risk = riskRepository.findById(id)
                .orElseThrow(() -> new RiskNotFoundException(id));
        return new RiskResponse(risk.getId(), risk.getType(), risk.getDescription());
    }

    @Transactional(readOnly = true)
    public Page<RiskResponse> getRisks(Pageable pageable){
        return riskRepository.findAll(pageable)
                .map(risk -> new RiskResponse(risk.getId(), risk.getType(), risk.getDescription()));
    }

    @Transactional
    public RiskResponse updateRisk(Long id, RiskRequest request){
        Risk risk = riskRepository.findById(id)
                .orElseThrow(()-> new RiskNotFoundException(id));

        risk.updateInformation(request.type(), request.description());
        Risk updatedRisk = riskRepository.save(risk);

        return new RiskResponse(updatedRisk.getId(), updatedRisk.getType(), updatedRisk.getDescription());
    }

    @Transactional
    public void deleteRisk(Long id){
        Risk risk = riskRepository.findById(id)
                .orElseThrow(()-> new RiskNotFoundException(id));
        riskRepository.delete(risk);
    }
}
