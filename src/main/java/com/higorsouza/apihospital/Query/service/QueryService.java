package com.higorsouza.apihospital.Query.service;

import com.higorsouza.apihospital.Query.dto.QueryRequest;
import com.higorsouza.apihospital.Query.dto.QueryResponse;

import java.util.List;
import java.util.UUID;

public interface QueryService {

    QueryResponse create(QueryRequest queryRequest);
    List<QueryResponse> listAll();
    QueryResponse getQueryById(UUID id);
    boolean deleteQuery(UUID id);
    QueryResponse updateQuery(UUID id, QueryRequest queryRequest);
}
