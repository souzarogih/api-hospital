package com.higorsouza.apihospital.Query.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRequest {

    @NotBlank(message = "Field name is invalid")
    private String name;

    @NotBlank(message = "Field cpf is invalid")
    private String cpf;
}
