package com.leaocrist.insurance.application.policy;

import com.leaocrist.insurance.application.customer.CustomerNotFoundException;
import com.leaocrist.insurance.application.policy.dto.PolicyRequest;
import com.leaocrist.insurance.application.policy.dto.PolicyResponse;
import com.leaocrist.insurance.application.risk.RiskNotFoundException;
import com.leaocrist.insurance.domain.customer.Customer;
import com.leaocrist.insurance.domain.policy.Policy;
import com.leaocrist.insurance.domain.risk.Risk;
import com.leaocrist.insurance.infrastructure.persistence.customer.CustomerRepository;
import com.leaocrist.insurance.infrastructure.persistence.policy.PolicyRepository;
import com.leaocrist.insurance.infrastructure.persistence.risk.RiskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;
    private final RiskRepository riskRepository;

    public PolicyService(PolicyRepository policyRepository, CustomerRepository customerRepository, RiskRepository riskRepository) {
        this.policyRepository = policyRepository;
        this.customerRepository = customerRepository;
        this.riskRepository = riskRepository;
    }

    @Transactional
    public PolicyResponse createPolicy(PolicyRequest request){
        if (policyRepository.existsByPolicyNumber(request.policyNumber())){
            throw new IllegalArgumentException("Policy number already exists: " + request.policyNumber());
        }

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(()-> new CustomerNotFoundException(request.customerId()));

        Risk risk = riskRepository.findById(request.riskId())
                .orElseThrow(()-> new RiskNotFoundException(request.riskId()));

        Policy policy = new Policy(
                request.policyNumber(),
                request.policyType(),
                request.effectiveDate(),
                request.term(),
                customer,
                risk
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
                policy.getCustomer().getId(),
                policy.getCustomer().getName(),
                policy.getRisk().getId(),
                policy.getRisk().getType()
        );
    }
}
