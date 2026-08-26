package com.leaocrist.insurance.infrastructure.persistence.policy;

import com.leaocrist.insurance.domain.policy.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    Optional<Policy> findByPolicyNumber(String policyNumber);

    boolean existsByPolicyNumber(String policyNumber);

    Page<Policy> findByCustomer_Id(Long customerId, Pageable pageable);
}
