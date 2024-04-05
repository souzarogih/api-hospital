package com.higorsouza.apihospital.Query.service;


import com.higorsouza.apihospital.Query.dto.QueryRequest;
import com.higorsouza.apihospital.Query.dto.QueryResponse;
import com.higorsouza.apihospital.Query.model.Query;
import com.higorsouza.apihospital.Query.repository.QueryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    QueryRepository queryRepository;

    public QueryResponse create(QueryRequest queryRequest) {
        log.debug("Analisando dados para cadastro do consulta. {}", queryRequest.getName());
        Query queryDataDb = queryRepository.save(new Query(
                UUID.randomUUID(),
                queryRequest.getName(),
                queryRequest.getCpf()
//                null
        ));
        log.info("Consulta {} salvo com id {}", queryDataDb.getName(), queryDataDb.getQueryCode());
        return new QueryResponse(queryDataDb.getId(), queryDataDb.getName(), queryDataDb.getQueryCode());
    }

    public List<QueryResponse> listAll() {
        log.debug("Localizando todos as consultas cadastradas na base de dados");

        List<QueryResponse> queryDataDb = queryRepository.findAll().stream()
                .map(query -> new QueryResponse(
                        query.getId(), query.getName(), query.getQueryCode()
                )).collect(Collectors.toList());
        log.info("Lista de consultas obtida");
        return queryDataDb;
    }

    public QueryResponse getQueryById(UUID id) {
        log.debug("Localizando a consulta com id: {}", id);

        Optional<Query> queryDataDb = queryRepository.findById(id);
        QueryResponse queryResponse = new QueryResponse();
        queryDataDb.ifPresent(query -> {
            queryResponse.setId(query.getId());
            queryResponse.setName(query.getName());
            queryResponse.setCpf(query.getQueryCode());
        });

        log.info("Dados da consulta {} - {} obtida com sucesso.", queryResponse.getId(), queryResponse.getName());
        return queryResponse;
    }

    public boolean deleteQuery(UUID id) {
        log.debug("Localizando a consulta com id: {}", id);

        Optional<Query> queryDataDb = queryRepository.findById(id);
        if (!queryDataDb.isPresent()){
            log.error("Não foi possível localizar a consulta {}", id);
            return false;
        }

        log.debug("Deletando a consulta com id: {}", id);
        queryRepository.deleteById(id);
        log.info("Consulta {} deletado!", id);
        return true;
    }

    public QueryResponse updateQuery(UUID id, QueryRequest queryRequest) {
        log.debug("Localizando a consulta com id: {}", id);
        log.debug("Request: {}", queryRequest);

        Optional<Query> queryDataDb = queryRepository.findById(id);
        if(queryDataDb.isPresent()) {
            Query queryModel = queryDataDb.get();
            BeanUtils.copyProperties(queryRequest, queryModel, "id");

            log.info("Consulta atualizado com sucesso.");
            return new QueryResponse(queryModel.getId(), queryModel.getName(), queryModel.getQueryCode());
        }

        return null;
    }

}
