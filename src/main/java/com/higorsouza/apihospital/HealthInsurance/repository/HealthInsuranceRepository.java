package com.higorsouza.apihospital.HealthInsurance.repository;

import com.higorsouza.apihospital.HealthInsurance.model.HealthInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, UUID> {
}
