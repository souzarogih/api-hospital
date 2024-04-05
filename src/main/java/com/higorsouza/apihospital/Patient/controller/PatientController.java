package com.higorsouza.apihospital.Patient.controller;

import com.higorsouza.apihospital.Exams.model.Exams;
import com.higorsouza.apihospital.Patient.dto.PatientRequest;
import com.higorsouza.apihospital.Patient.dto.PatientResponse;
import com.higorsouza.apihospital.Patient.model.Patient;
import com.higorsouza.apihospital.Patient.service.PatientService;
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
@RequestMapping("/patient")
@Tag(name = "Pacientes", description = "API para gerenciamento de pacientes")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Operation(summary = "Cadastra um Paciente.", description = "Esse endpoint deve ser usada para gerenciar os pacientes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Patient.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PostMapping
    public ResponseEntity<PatientResponse> create(@RequestBody PatientRequest patientRequest) {
        log.debug("Processando a requisição para criar paciente. {}", patientRequest.getName());

        PatientResponse patientDataObj = patientService.create(patientRequest);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.CREATED).body(patientDataObj);
    }

    @GetMapping("/listAllPatient")
    public ResponseEntity<List<PatientResponse>> listAllPatient() {
        log.debug("Processando a requisição para listar todos os pacientes.");

        List<PatientResponse> candidateDataObj = patientService.listAll();
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(candidateDataObj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getCandidate(@PathVariable UUID id) {
        log.debug("Processando a requisição para localizar o paciente id: {}.", id);

        PatientResponse candidateDataObj = patientService.getQueryById(id);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(candidateDataObj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCandidate(@PathVariable UUID id) {
        log.debug("Processando a requisição para deletar o paciente com id: {}.", id);

        boolean candidateDataObj = patientService.deleteQuery(id);
        if (candidateDataObj){
            log.info("Paciente {} excluído com sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body("Paciente excluído com sucesso.");
        }else {
            log.error("Não foi possível excluir o paciente.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível excluir o paciente.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponse> deleteCandidate(@PathVariable UUID id,
                                                           @RequestBody PatientRequest patientRequest) {
        log.debug("Processando a requisição para atualizar o paciente com id: {}.", id);

        PatientResponse candidateDataObj = patientService.updateQuery(id, patientRequest);

        return ResponseEntity.status(HttpStatus.OK).body(candidateDataObj);
    }

}
