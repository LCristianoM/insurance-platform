package com.leaocrist.insurance.domain.policy;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String policyNumber;

    private String policyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyTerm term;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risk_id", nullable = false)
    private Risk risk;*/

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "risk_id", nullable = false)
    private Long riskId;

    private LocalDate effectiveDate;
    private LocalDate expirationDate;

    protected Policy(){
    }

    public Policy(
            String policyNumber,
            String policyType,
            LocalDate effectiveDate,
            PolicyTerm term,
            Long customerId,
            Long riskId
    ) {
        this.policyNumber = Objects.requireNonNull(policyNumber, "Policy number is required.");
        this.policyType = Objects.requireNonNull(policyType, "Policy type is required.");
        this.effectiveDate = Objects.requireNonNull(effectiveDate, "Effective date is required.");
        this.term = Objects.requireNonNull(term, "Policy term is required.");
        this.customerId = Objects.requireNonNull(customerId, "Customer is required.");
        this.riskId = Objects.requireNonNull(riskId, "Risk is required.");

        this.status = PolicyStatus.ACTIVE;

        calculateExpirationDate();
    }

    public Long getId() {
        return id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPolicyType() {
        return policyType;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public PolicyTerm getTerm() {
        return term;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getRiskId() {
        return riskId;
    }

    private void calculateExpirationDate(){
        this.expirationDate = effectiveDate.plusMonths(term.getMonths());
    }

    public void cancel(){
        if(this.status == PolicyStatus.CANCELLED){
            throw new IllegalStateException("Policy is already cancelled.");
        }
        this.status = PolicyStatus.CANCELLED;
    }

    public void updatePolicyType(String newType){
        if (this.status == PolicyStatus.CANCELLED){
            throw new IllegalStateException("Cannot update a cancelled policy.");
        }
        this.policyType = Objects.requireNonNull(newType, "Policy type is required");
    }
}
