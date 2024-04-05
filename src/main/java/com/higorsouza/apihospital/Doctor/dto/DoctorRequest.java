package com.higorsouza.apihospital.Doctor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorRequest {

    @NotBlank(message = "The field name is mandatory")
    private String name;

    @NotBlank(message = "The field crm is mandatory")
    private String crm;
}
