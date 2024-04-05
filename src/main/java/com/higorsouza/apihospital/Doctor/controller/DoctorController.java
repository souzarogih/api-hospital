package com.higorsouza.apihospital.Doctor.controller;

import com.higorsouza.apihospital.Doctor.dto.DoctorRequest;
import com.higorsouza.apihospital.Doctor.dto.DoctorResponse;
import com.higorsouza.apihospital.Doctor.model.Doctor;
import com.higorsouza.apihospital.Doctor.service.DoctorService;
import com.higorsouza.apihospital.Exams.model.Exams;
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
@RequestMapping("/doctor")
@Tag(name = "Médicos", description = "API para gerenciamento de Médicos")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Operation(summary = "Cria um médico.", description = "Esse endpoint deve ser usada para gerenciar os médicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DoctorResponse> create(@RequestBody DoctorRequest doctorRequest) {
        log.debug("Processando a requisição para criar médico. {}", doctorRequest.getName());

        DoctorResponse doctorDataObj = doctorService.create(doctorRequest);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorDataObj);
    }

    @Operation(summary = "Lista médicos.", description = "Esse endpoint deve ser usada para listar dados de todos os médicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/listAllDoctor")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DoctorResponse>> listAllDoctor() {
        log.debug("Processando a requisição para listar todos os médico.");

        List<DoctorResponse> doctorDataObj = doctorService.listAll();
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(doctorDataObj);
    }

    @Operation(summary = "Obtem um médico.", description = "Esse endpoint deve ser usada para buscar dados de um médico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DoctorResponse> getDoctor(@PathVariable UUID id) {
        log.debug("Processando a requisição para localizar o médico com id: {}.", id);

        DoctorResponse doctorDataObj = doctorService.getDoctorById(id);
        log.debug("Preparando resposta da requisição.");

        return ResponseEntity.status(HttpStatus.OK).body(doctorDataObj);
    }

    @Operation(summary = "Remover um médico.", description = "Esse endpoint deve ser usada para remover dados de um médico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteDoctor(@PathVariable UUID id) {
        log.debug("Processando a requisição para deletar o médico com id: {}.", id);

        boolean doctorDataObj = doctorService.deleteDoctor(id);
        if (doctorDataObj){
            log.info("Médico {} excluído com sucesso.", id);
            return ResponseEntity.status(HttpStatus.OK).body("Médico excluído com sucesso.");
        }else {
            log.error("Não foi possível excluir o médico.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível excluir o médico.");
        }
    }

    @Operation(summary = "Atualizar um médico.", description = "Esse endpoint deve ser usada para atualiza dados de um médico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DoctorResponse> deleteDoctor(@PathVariable UUID id,
                                                       @RequestBody DoctorRequest doctorRequest) {
        log.debug("Processando a requisição para atualizar o médico com id: {}.", id);

        DoctorResponse doctorDataObj = doctorService.updateDoctor(id, doctorRequest);

        return ResponseEntity.status(HttpStatus.OK).body(doctorDataObj);
    }
}
