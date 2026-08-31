package com.leaocrist.insurance.presentation.risk;

import com.leaocrist.insurance.application.risk.RiskService;
import com.leaocrist.insurance.application.risk.dto.RiskRequest;
import com.leaocrist.insurance.application.risk.dto.RiskResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/risks")
public class RiskController {

    private final RiskService riskService;

    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @PostMapping
    public ResponseEntity<RiskResponse> createRisk(@Valid @RequestBody RiskRequest request){
        RiskResponse response = riskService.createRisk(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskResponse> getRiskById(@PathVariable Long id){
        return ResponseEntity.ok(riskService.findRiskById(id));
    }

    @GetMapping
    public ResponseEntity<Page<RiskResponse>> getRisks(Pageable pageable){
        return ResponseEntity.ok(riskService.getRisks(pageable));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id){
        return ResponseEntity.ok(riskService.existsById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskResponse> updateRisk(@PathVariable Long id, @Valid @RequestBody RiskRequest request){
        return ResponseEntity.ok(riskService.updateRisk(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRisk(@PathVariable Long id){
        riskService.deleteRisk(id);
        return ResponseEntity.noContent().build();
    }
}
