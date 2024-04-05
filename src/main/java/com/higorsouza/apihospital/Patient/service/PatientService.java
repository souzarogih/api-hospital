package com.higorsouza.apihospital.Patient.service;

import com.higorsouza.apihospital.Patient.dto.PatientRequest;
import com.higorsouza.apihospital.Patient.dto.PatientResponse;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    PatientResponse create(PatientRequest patientRequest);
    List<PatientResponse> listAll();
    PatientResponse getQueryById(UUID id);
    boolean deleteQuery(UUID id);
    PatientResponse updateQuery(UUID id, PatientRequest patientRequest);
}
