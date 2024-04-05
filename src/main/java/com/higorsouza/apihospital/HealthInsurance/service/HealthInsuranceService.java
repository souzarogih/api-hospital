package com.higorsouza.apihospital.HealthInsurance.service;

import com.higorsouza.apihospital.HealthInsurance.dto.HealthInsuranceRequest;
import com.higorsouza.apihospital.HealthInsurance.dto.HealthInsuranceResponse;

import java.util.List;
import java.util.UUID;

public interface HealthInsuranceService {

    HealthInsuranceResponse create(HealthInsuranceRequest healthInsuranceRequest);
    List<HealthInsuranceResponse> listAll();
    HealthInsuranceResponse getHealthInsuranceById(UUID id);
    boolean deleteHealthInsurance(UUID id);
    HealthInsuranceResponse updateHealthInsurance(UUID id, HealthInsuranceRequest healthInsuranceRequest);
}
