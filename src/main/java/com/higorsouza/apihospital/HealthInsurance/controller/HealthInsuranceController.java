package com.higorsouza.apihospital.HealthInsurance.controller;

import com.higorsouza.apihospital.Exams.model.Exams;
import com.higorsouza.apihospital.HealthInsurance.dto.HealthInsuranceRequest;
import com.higorsouza.apihospital.HealthInsurance.dto.HealthInsuranceResponse;
import com.higorsouza.apihospital.HealthInsurance.model.HealthInsurance;
import com.higorsouza.apihospital.HealthInsurance.service.HealthInsuranceService;
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
@RequestMapping("/healthInsurance")
@Tag(name = "Planos de Saúde", description = "API para gerenciamento de Planos de Saúde")
public class HealthInsuranceController {

    @Autowired
    HealthInsuranceService healthInsuranceService;

    @Operation(summary = "Cria um plano de saúde.", description = "Esse endpoint deve ser usada para gerenciar os planos de saúde.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = HealthInsurance.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = HealthInsurance.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PostMapping
    public ResponseEntity<HealthInsuranceResponse> create(@RequestBody HealthInsuranceRequest healthInsuranceRequest) {
        log.debug("Processando a requisição para criar plano de saúde. {}", healthInsuranceRequest.getName());

        HealthInsuranceResponse healthInsuranceDataObj = healthInsuranceService.create(healthInsuranceRequest);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.CREATED).body(healthInsuranceDataObj);
    }

    @GetMapping("/listAllHealthInsurance")
    public ResponseEntity<List<HealthInsuranceResponse>> listAllHealthInsurance() {
        log.debug("Processando a requisição para listar todos os planos de saúde.");

        List<HealthInsuranceResponse> healthInsuranceDataObj = healthInsuranceService.listAll();
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(healthInsuranceDataObj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthInsuranceResponse> getHealthInsurance(@PathVariable UUID id) {
        log.debug("Processando a requisição para localizar o plano de saúde com id: {}.", id);

        HealthInsuranceResponse healthInsuranceDataObj = healthInsuranceService.getHealthInsuranceById(id);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(healthInsuranceDataObj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHealthInsurance(@PathVariable UUID id) {
        log.debug("Processando a requisição para deletar o plano de saúde com id: {}.", id);

        boolean healthInsuranceDataObj = healthInsuranceService.deleteHealthInsurance(id);
        if (healthInsuranceDataObj){
            log.info("Plano de saúde {} excluído com sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body("Médico excluído com sucesso.");
        }else {
            log.error("Não foi possível excluir o plano de saúde.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível excluir o plano de saúde.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HealthInsuranceResponse> deleteDHealthInsurance(@PathVariable UUID id,
                                                       @RequestBody HealthInsuranceRequest healthInsuranceRequest) {
        log.debug("Processando a requisição para atualizar o plano de saúde com id: {}.", id);

        HealthInsuranceResponse healthInsuranceDataObj = healthInsuranceService.updateHealthInsurance(id, healthInsuranceRequest);

        return ResponseEntity.status(HttpStatus.OK).body(healthInsuranceDataObj);
    }
}
