package com.higorsouza.apihospital.Exams.controller;

import com.higorsouza.apihospital.Exams.dto.ExamsRequest;
import com.higorsouza.apihospital.Exams.dto.ExamsResponse;
import com.higorsouza.apihospital.Exams.model.Exams;
import com.higorsouza.apihospital.Exams.service.ExamsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/exam")
@Tag(name = "Exames", description = "API para gerenciamento de exames")
public class ExamsController {

    @Autowired
    ExamsService examsService;

    @Operation(summary = "Cria um exame.", description = "Esse endpoint deve ser usada para gerenciar os exames.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Exams.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Exams.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PostMapping
    public ResponseEntity<ExamsResponse> create(@RequestBody ExamsRequest examsRequest) {
        log.debug("Processando a requisição para criar exame. {}", examsRequest.getName());

        ExamsResponse examDataObj = examsService.create(examsRequest);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.CREATED).body(examDataObj);
    }

    @GetMapping("/listAllExams")
    public ResponseEntity<List<ExamsResponse>> listAllExam() {
        log.debug("Processando a requisição para listar todos os exames.");

        List<ExamsResponse> candidateDataObj = examsService.listAll();
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(candidateDataObj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamsResponse> getCandidate(@PathVariable UUID id) {
        log.debug("Processando a requisição para localizar o exame com id: {}.", id);

        ExamsResponse candidateDataObj = examsService.getExamById(id);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(candidateDataObj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCandidate(@PathVariable UUID id) {
        log.debug("Processando a requisição para deletar o exame com id: {}.", id);

        boolean candidateDataObj = examsService.deleteExam(id);
        if (candidateDataObj){
            log.info("Exame {} excluído com sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body("Exame excluído com sucesso.");
        }else {
            log.error("Não foi possível excluir o exame.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível excluir o exame.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExamsResponse> deleteCandidate(@PathVariable UUID id,
                                                            @RequestBody ExamsRequest examsRequest) {
        log.debug("Processando a requisição para atualizar o exame com id: {}.", id);

        ExamsResponse candidateDataObj = examsService.updateExam(id, examsRequest);

        return ResponseEntity.status(HttpStatus.OK).body(candidateDataObj);
    }

}
