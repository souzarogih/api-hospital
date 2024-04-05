package com.higorsouza.apihospital.Query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryResponse {

    private UUID id;
    private String name;
    private String cpf;
}
