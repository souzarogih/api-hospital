package com.higorsouza.apihospital.Patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {

    private UUID id;
    private String name;
    private String cpf;
}
