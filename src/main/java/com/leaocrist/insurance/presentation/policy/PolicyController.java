package com.leaocrist.insurance.presentation.policy;

import com.leaocrist.insurance.application.policy.PolicyService;
import com.leaocrist.insurance.application.policy.dto.PolicyRequest;
import com.leaocrist.insurance.application.policy.dto.PolicyResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(@Valid @RequestBody PolicyRequest request){
        PolicyResponse response = policyService.createPolicy(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Long id){
        return ResponseEntity.ok(policyService.findPolicyById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PolicyResponse>> getPolicies(Pageable pageable){
        return ResponseEntity.ok(policyService.getPolicies(pageable));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<PolicyResponse> cancelPolicy(@PathVariable Long id){
        return ResponseEntity.ok(policyService.cancelPolicy(id));
    }
}
