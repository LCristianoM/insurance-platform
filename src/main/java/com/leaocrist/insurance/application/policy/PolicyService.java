package com.leaocrist.insurance.application.policy;

import com.leaocrist.insurance.application.customer.CustomerClient;
import com.leaocrist.insurance.application.customer.CustomerNotFoundException;
import com.leaocrist.insurance.application.policy.dto.PolicyRequest;
import com.leaocrist.insurance.application.policy.dto.PolicyResponse;
import com.leaocrist.insurance.application.risk.RiskClient;
import com.leaocrist.insurance.application.risk.RiskNotFoundException;
import com.leaocrist.insurance.domain.policy.Policy;
import com.leaocrist.insurance.infrastructure.persistence.policy.PolicyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final CustomerClient customerClient;
    private final RiskClient riskClient;

    public PolicyService(PolicyRepository policyRepository, CustomerClient customerClient, RiskClient riskClient) {
        this.policyRepository = policyRepository;
        this.customerClient = customerClient;
        this.riskClient = riskClient;
    }

    @Transactional
    public PolicyResponse createPolicy(PolicyRequest request){

        if (policyRepository.existsByPolicyNumber(request.policyNumber())){
            throw new PolicyNumberAlreadyExistsException(request.policyNumber());
        }
        if (!customerClient.existsById(request.customerId())){
            throw new CustomerNotFoundException(request.customerId());
        }
        if (!riskClient.existsById(request.riskId())){
            throw new RiskNotFoundException(request.riskId());
        }

        Policy policy = new Policy(
                request.policyNumber(),
                request.policyType(),
                request.effectiveDate(),
                request.term(),
                request.customerId(),
                request.riskId()
        );

        Policy savedPolicy = policyRepository.save(policy);
        return mapToResponse(savedPolicy);
    }

    @Transactional(readOnly = true)
    public PolicyResponse findPolicyById(Long id){
        Policy policy = policyRepository.findById(id)
                .orElseThrow(()-> new PolicyNotFoundException(id));
        return mapToResponse(policy);
    }

    @Transactional(readOnly = true)
    public Page<PolicyResponse> getPolicies(Pageable pageable){
        return policyRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<PolicyResponse> getPoliciesByCustomer(
            Long customerId, Pageable pageable
    ){
        if(!customerClient.existsById(customerId)){
            throw new CustomerNotFoundException(customerId);
        }

        return policyRepository
                .findByCustomerId(customerId, pageable)
                .map(this::mapToResponse);
    }

    @Transactional
    public PolicyResponse cancelPolicy(Long id){
        Policy policy = policyRepository.findById(id)
                .orElseThrow(()-> new PolicyNotFoundException(id));

        policy.cancel();
        Policy updatePolicy = policyRepository.save(policy);
        return mapToResponse(updatePolicy);
    }

    private PolicyResponse mapToResponse(Policy policy) {
        return new PolicyResponse(
                policy.getId(),
                policy.getPolicyNumber(),
                policy.getPolicyType(),
                policy.getStatus(),
                policy.getTerm(),
                policy.getEffectiveDate(),
                policy.getExpirationDate(),
                policy.getCustomerId(),
                policy.getRiskId()
        );
    }
}
