package com.higorsouza.apihospital.Patient.service;

import com.higorsouza.apihospital.Patient.dto.PatientRequest;
import com.higorsouza.apihospital.Patient.dto.PatientResponse;
import com.higorsouza.apihospital.Patient.model.Patient;
import com.higorsouza.apihospital.Patient.repository.PatientRepository;
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
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    public PatientResponse create(PatientRequest patientRequest) {
        log.debug("Analisando dados para cadastro do paciente. {}", patientRequest.getName());
        Patient patientDataDb = patientRepository.save(new Patient(
                UUID.randomUUID(),
                patientRequest.getName(),
                patientRequest.getCpf(),
                null,
                null
        ));
        log.info("Paciente {} salvo com id {}", patientDataDb.getName(), patientDataDb.getCpf());
        return new PatientResponse(patientDataDb.getId(), patientDataDb.getName(), patientDataDb.getCpf());
    }

    public List<PatientResponse> listAll() {
        log.debug("Localizando todos os pacientes cadastrados na base de dados");

        List<PatientResponse> patientDataDb = patientRepository.findAll().stream()
                .map(patient -> new PatientResponse(
                        patient.getId(), patient.getName(), patient.getCpf()
                )).collect(Collectors.toList());
        log.info("Lista de pacientes obtida");
        return patientDataDb;
    }

    public PatientResponse getQueryById(UUID id) {
        log.debug("Localizando o paciente com id: {}", id);

        Optional<Patient> patientDataDb = patientRepository.findById(id);
        PatientResponse patientResponse = new PatientResponse();
        patientDataDb.ifPresent(patient -> {
            patientResponse.setId(patient.getId());
            patientResponse.setName(patient.getName());
            patientResponse.setCpf(patient.getCpf());
        });

        log.info("Dados do paciente {} - {} obtida com sucesso.", patientResponse.getId(), patientResponse.getName());
        return patientResponse;
    }

    public boolean deleteQuery(UUID id) {
        log.debug("Localizando o paciente com id: {}", id);

        Optional<Patient> patientDataDb = patientRepository.findById(id);
        if (!patientDataDb.isPresent()){
            log.error("Não foi possível localizar o paciente {}", id);
            return false;
        }

        log.debug("Deletando o paciente com id: {}", id);
        patientRepository.deleteById(id);
        log.info("Paciente {} deletado!", id);
        return true;
    }

    public PatientResponse updateQuery(UUID id, PatientRequest patientRequest) {
        log.debug("Localizando o paciente com id: {}", id);
        log.debug("Request: {}", patientRequest);

        Optional<Patient> doctorDataDb = patientRepository.findById(id);
        if(doctorDataDb.isPresent()) {
            Patient patientModel = doctorDataDb.get();
            BeanUtils.copyProperties(patientRequest, patientModel, "id");

            log.info("Paciente atualizado com sucesso.");
            return new PatientResponse(patientModel.getId(), patientModel.getName(), patientModel.getCpf());
        }

        return null;
    }

}
