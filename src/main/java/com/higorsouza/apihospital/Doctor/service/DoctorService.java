package com.higorsouza.apihospital.Doctor.service;

import com.higorsouza.apihospital.Doctor.dto.DoctorRequest;
import com.higorsouza.apihospital.Doctor.dto.DoctorResponse;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    DoctorResponse create(DoctorRequest doctorRequest);
    List<DoctorResponse> listAll();
    DoctorResponse getDoctorById(UUID id);
    boolean deleteDoctor(UUID id);
    DoctorResponse updateDoctor(UUID id, DoctorRequest doctorRequest);
}
