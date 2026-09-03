package com.leaocrist.insurance.domain.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PolicyTest {

    private Long customerId;
    private Long riskId;

    @BeforeEach
    void setUp(){
        customerId = 1L;
        riskId = 1L;
    }

    @Test
    void shouldCreatePolicyAsActive(){
        Policy policy = new Policy(
                "POL-00001",
                "AUTO",
                LocalDate.of(2026, 8, 18),
                PolicyTerm.ONE_YEAR,
                customerId,
                riskId
        );
        assertEquals(PolicyStatus.ACTIVE, policy.getStatus());
    }

    @Test
    void shouldCalculateExpirationDateForOneYearTerm(){
        LocalDate effectiveDate = LocalDate.of(2026, 8, 18);

        Policy policy = new Policy(
                "POL-00002",
                "AUTO",
                effectiveDate,
                PolicyTerm.ONE_YEAR,
                customerId,
                riskId
        );
        LocalDate expectedExpirationDate = LocalDate.of(2027, 8, 18);
        assertEquals(expectedExpirationDate, policy.getExpirationDate());
    }

    @Test
    void shouldCalculateExpirationDateForSixMonthTerm() {

        LocalDate effectiveDate = LocalDate.of(2026, 9, 1);

        Policy policy = new Policy(
                "POL-003",
                "AUTO",
                effectiveDate,
                PolicyTerm.SIX_MONTHS,
                customerId,
                riskId
        );

        LocalDate expectedExpirationDate = LocalDate.of(2027, 3, 1);

        assertEquals(expectedExpirationDate, policy.getExpirationDate());
    }

    @Test
    void shouldNotCreatePolicyWithoutCustomer() {

        LocalDate effectiveDate = LocalDate.of(2026, 9, 1);

        assertThrows(
                NullPointerException.class,
                () -> {
                    new Policy(
                            "POL-004",
                            "AUTO",
                            effectiveDate,
                            PolicyTerm.ONE_YEAR,
                            null,
                            riskId
                    );
                }
        );
    }
}
   /* @Test
    void shouldCreatePolicyAsActive(){

        Customer customer = new Customer(
                "Leao",
                "911",
                "leao@test.com"
        );

        Risk risk = new Risk(
                1L,
                "VEHICLE",
                "Mercedes Benz CLA 2016"
        );

        Policy policy = new Policy(
                "POL-00001",
                "AUTO",
                LocalDate.of(2026, 8, 18),
                PolicyTerm.ONE_YEAR,
                customer,
                risk
        );

        assertEquals(PolicyStatus.ACTIVE, policy.getStatus());
    }

    @Test
    void shouldCalculateExpirationDateForOneYearTerm(){
        Customer customer = new Customer(
                "Leao",
                "123",
                "leao@correo.com"
        );
        Risk risk = new Risk(
                2L,
                "VEHICLE",
                "Toyota"
        );
        LocalDate effectiveDate = LocalDate.of(2026, 8, 18);

        Policy policy = new Policy(
                "POL-00002",
                "AUTO",
                effectiveDate,
                PolicyTerm.ONE_YEAR,
                customer,
                risk
        );
        LocalDate expectedExpirationDate = LocalDate.of(2027, 8, 18);
        assertEquals(expectedExpirationDate, policy.getExpirationDate());
    }

    @Test
    void shouldCalculateExpirationDateForSixMonthTerm() {

        Customer customer = new Customer(
                "Leandro",
                "3001234567",
                "leandro@email.com"
        );

        Risk risk = new Risk(
                1L,
                "VEHICLE",
                "Toyota Corolla 2022"
        );

        LocalDate effectiveDate = LocalDate.of(2026, 9, 1);

        Policy policy = new Policy(
                "POL-002",
                "AUTO",
                effectiveDate,
                PolicyTerm.SIX_MONTHS,
                customer,
                risk
        );

        LocalDate expectedExpirationDate = LocalDate.of(2027, 3, 1);

        assertEquals(expectedExpirationDate, policy.getExpirationDate());
    }

    @Test
    void shouldNotCreatePolicyWithoutCustomer() {

        Risk risk = new Risk(
                1L,
                "VEHICLE",
                "Toyota Corolla 2022"
        );

        LocalDate effectiveDate = LocalDate.of(2026, 9, 1);

        assertThrows(
                NullPointerException.class,
                () -> {
                    new Policy(
                            "POL-003",
                            "AUTO",
                            effectiveDate,
                            PolicyTerm.ONE_YEAR,
                            null,
                            risk
                    );
                }
        );
    }
}*/
