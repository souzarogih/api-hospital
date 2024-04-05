package com.higorsouza.apihospital.HealthInsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HealthInsuranceResponse {

    private UUID id;
    private String name;
    private String cnpj;
}
