package com.leaocrist.risk_service.infrastructure.persistence.risk;

import com.leaocrist.risk_service.domain.risk.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskRepository extends JpaRepository<Risk, Long> {
}
