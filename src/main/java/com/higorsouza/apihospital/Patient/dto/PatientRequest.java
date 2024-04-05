package com.higorsouza.apihospital.Patient.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {

    @NotBlank(message = "Field name is invalid")
    private String name;

    @NotBlank(message = "Field cpf is invalid")
    private String cpf;
}
