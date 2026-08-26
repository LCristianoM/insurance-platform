package com.leaocrist.insurance.application.policy;

import com.leaocrist.insurance.application.customer.CustomerNotFoundException;
import com.leaocrist.insurance.application.policy.dto.PolicyRequest;
import com.leaocrist.insurance.application.policy.dto.PolicyResponse;
import com.leaocrist.insurance.application.risk.RiskNotFoundException;
import com.leaocrist.insurance.domain.customer.Customer;
import com.leaocrist.insurance.domain.policy.Policy;
import com.leaocrist.insurance.domain.policy.PolicyStatus;
import com.leaocrist.insurance.domain.policy.PolicyTerm;
import com.leaocrist.insurance.domain.risk.Risk;
import com.leaocrist.insurance.infrastructure.persistence.customer.CustomerRepository;
import com.leaocrist.insurance.infrastructure.persistence.policy.PolicyRepository;
import com.leaocrist.insurance.infrastructure.persistence.risk.RiskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RiskRepository riskRepository;

    @InjectMocks
    private PolicyService policyService;

    private Customer dummyCustomer;
    private Risk dummyRisk;
    private PolicyRequest validRequest;

    @BeforeEach
    void setUp(){
        dummyCustomer = new Customer(
                "Leao",
                "911",
                "leao@test.com"
        );

        dummyRisk = new Risk(
                "VEHICLE",
                "Mercedes Benz CLA 2016"
        );

        validRequest = new PolicyRequest(
                "POL-001",
                "AUTO",
                LocalDate.of(2026, 8, 24),
                PolicyTerm.ONE_YEAR,
                1L,
                1L
        );
    }

    @Nested
    @DisplayName("Pruebas de creación de pólizas (createPolicy)")
    class CreatePolicyTest{

        @Test
        @DisplayName("Debe crear una póliza exitosa cuando los datos son válidos.")
        void shouldCreatePolicySuccessfully(){

            //Arrange
            when(policyRepository.existsByPolicyNumber(validRequest.policyNumber())).thenReturn(false);
            when(customerRepository.findById(1L)).thenReturn(Optional.of(dummyCustomer));
            when(riskRepository.findById(1L)).thenReturn(Optional.of(dummyRisk));

            Policy savedPolicy = new Policy(
                    validRequest.policyNumber(),
                    validRequest.policyType(),
                    validRequest.effectiveDate(),
                    validRequest.term(),
                    dummyCustomer,
                    dummyRisk
            );
            when(policyRepository.save(any(Policy.class))).thenReturn(savedPolicy);

            //Act
            PolicyResponse response = policyService.createPolicy(validRequest);

            //Assert
            assertThat(response).isNotNull();
            assertThat(response.policyNumber()).isEqualTo("POL-001");
            assertThat(response.status()).isEqualTo(PolicyStatus.ACTIVE);

            verify(policyRepository).save(any(Policy.class));
        }

        @Test
        @DisplayName("Debe lanzar PolicyNumberAlreadyExistsException si el número de póliza ya existe")
        void shouldThrowExceptionWhenPolicyNumberExists(){

            //Arrange
            when(policyRepository.existsByPolicyNumber(validRequest.policyNumber())).thenReturn(true);

            //Act & Assert
            assertThatThrownBy(() -> policyService.createPolicy(validRequest))
                    .isInstanceOf(PolicyNumberAlreadyExistsException.class)
                    .hasMessageContaining("POL-001");

            verify(policyRepository, never()).save(any());
        }

        @Test
        @DisplayName("Debe lanzar CustomerNotFoundException si el cliente no existe")
        void shouldThrowExceptionWhenCustomerDoesNotExist(){

            //Arrange
            when(policyRepository.existsByPolicyNumber(validRequest.policyNumber())).thenReturn(false);
            when(customerRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(()-> policyService.createPolicy(validRequest))
                    .isInstanceOf(CustomerNotFoundException.class);

            verify(policyRepository, never()).save(any());
        }

        @Test
        @DisplayName("Debe lanzar RiskNotFoundException si el riesgo no existe")
        void shouldThrowExceptionWhenRiskDoesNotExist(){

            //Arrange
            when(policyRepository.existsByPolicyNumber(validRequest.policyNumber())).thenReturn(false);
            when(customerRepository.findById(1L)).thenReturn(Optional.of(dummyCustomer));
            when(riskRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(()-> policyService.createPolicy(validRequest))
                    .isInstanceOf(RiskNotFoundException.class);

            verify(policyRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Pruebas de cancelación (cancelPolicy)")
    class CancelPolicyTest{

        @Test
        @DisplayName("Debe cancelar la póliza exitosamente si está ACTIVE")
        void shouldCancelPolicySuccessfully(){

            //Arrange
            Policy activePolicy = new Policy(
                    "POL-001",
                    "AUTO",
                    LocalDate.of(2026, 8, 24),
                    PolicyTerm.ONE_YEAR,
                    dummyCustomer,
                    dummyRisk
            );

            // ACt
            when(policyRepository.findById(1L)).thenReturn(Optional.of(activePolicy));
            when(policyRepository.save(any(Policy.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            PolicyResponse response = policyService.cancelPolicy(1L);

            //Assert
            assertThat(response.status()).isEqualTo(PolicyStatus.CANCELLED);
        }

        @Test
        @DisplayName("Debe lanzar IllegalStateException al intentar cancelar una póliza ya CANCELLED")
        void shouldThrowExceptionWhenCancellingAlreadyCancelledPolicy(){

            //Arrange
            Policy cancelledPolicy = new Policy(
                    "POL-001",
                    "AUTO",
                    LocalDate.of(2026, 8, 24),
                    PolicyTerm.ONE_YEAR,
                    dummyCustomer,
                    dummyRisk
            );
            cancelledPolicy.cancel();

            when(policyRepository.findById(1L)).thenReturn(Optional.of(cancelledPolicy));

            // Act & Assert
            assertThatThrownBy(()-> policyService.cancelPolicy(1L))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("already cancelled");
        }
    }
}
