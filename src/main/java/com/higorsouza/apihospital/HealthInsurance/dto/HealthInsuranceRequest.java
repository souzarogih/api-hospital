package com.higorsouza.apihospital.HealthInsurance.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HealthInsuranceRequest {

    @NotBlank(message = "The field name is mandatory")
    private String name;

    @NotBlank(message = "The field crm is mandatory")
    private String cnpj;
}
