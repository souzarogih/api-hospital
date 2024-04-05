package com.higorsouza.apihospital.Query.controller;


import com.higorsouza.apihospital.Query.dto.QueryRequest;
import com.higorsouza.apihospital.Query.dto.QueryResponse;
import com.higorsouza.apihospital.Query.service.QueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/query")
@Tag(name = "Consultas", description = "API para gerenciamento de consultas")
public class QueryController {

    @Autowired
    QueryService queryService;

    @PostMapping
    public ResponseEntity<QueryResponse> create(@RequestBody QueryRequest queryRequest) {
        log.debug("Processando a requisição para criar consulta. {}", queryRequest.getName());

        QueryResponse queryDataObj = queryService.create(queryRequest);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.CREATED).body(queryDataObj);
    }

    @GetMapping("/listAllQuery")
    public ResponseEntity<List<QueryResponse>> listAll() {
        log.debug("Processando a requisição para listar todas as consulta.");

        List<QueryResponse> queryDataObj = queryService.listAll();
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(queryDataObj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QueryResponse> getQuery(@PathVariable UUID id) {
        log.debug("Processando a requisição para localizar a consulta id: {}.", id);

        QueryResponse queryDataObj = queryService.getQueryById(id);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(queryDataObj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuery(@PathVariable UUID id) {
        log.debug("Processando a requisição para deletar a consulta com id: {}.", id);

        boolean candidateDataObj = queryService.deleteQuery(id);
        if (candidateDataObj){
            log.info("Consulta {} excluída com sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body("Consulta excluída com sucesso.");
        }else {
            log.error("Não foi possível excluir a consulta.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível excluir a consulta.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QueryResponse> deleteQuery(@PathVariable UUID id,
                                                       @RequestBody QueryRequest queryRequest) {
        log.debug("Processando a requisição para atualizar a consulta com id: {}.", id);

        QueryResponse candidateDataObj = queryService.updateQuery(id, queryRequest);

        return ResponseEntity.status(HttpStatus.OK).body(candidateDataObj);
    }

}
