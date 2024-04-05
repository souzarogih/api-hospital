package com.higorsouza.apihospital.Exams.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamsRequest {

    @NotBlank(message = "Field name is invalid")
    private String name;

    @NotBlank(message = "Field examsCode is invalid")
    private String examsCode;
}
