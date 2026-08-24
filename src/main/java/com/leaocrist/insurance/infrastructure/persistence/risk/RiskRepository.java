package com.leaocrist.insurance.infrastructure.persistence.risk;

import com.leaocrist.insurance.domain.risk.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskRepository extends JpaRepository<Risk, Long> {
}
