package com.higorsouza.apihospital.Exams.service;

import com.higorsouza.apihospital.Exams.dto.ExamsRequest;
import com.higorsouza.apihospital.Exams.dto.ExamsResponse;
import com.higorsouza.apihospital.Exams.model.Exams;
import com.higorsouza.apihospital.Exams.repository.ExamsRepository;
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
public class ExamsServiceImpl implements ExamsService{

    @Autowired
    ExamsRepository examsRepository;

    public ExamsResponse create(ExamsRequest examsRequest) {
        log.debug("Analisando dados para cadastro do exame. {}", examsRequest.getName());
        Exams examsDataDb = examsRepository.save(new Exams(
                UUID.randomUUID(),
                examsRequest.getName(),
                examsRequest.getExamsCode(),
                null,
                null,
                null
        ));
        log.info("Exame {} salvo com id {}", examsDataDb.getName(), examsDataDb.getExamsCode());
        return new ExamsResponse(examsDataDb.getId(), examsDataDb.getName(), examsDataDb.getExamsCode());
    }

    public List<ExamsResponse> listAll() {
        log.debug("Localizando todos os exames cadastrados na base de dados");

        List<ExamsResponse> examsDataDb = examsRepository.findAll().stream()
                .map(doctor -> new ExamsResponse(
                        doctor.getId(), doctor.getName(), doctor.getExamsCode()
                )).collect(Collectors.toList());
        log.info("Lista de exames obtida");
        return examsDataDb;
    }

    public ExamsResponse getExamById(UUID id) {
        log.debug("Localizando o exame com id: {}", id);

        Optional<Exams> examsDataDb = examsRepository.findById(id);
        ExamsResponse examsResponse = new ExamsResponse();
        examsDataDb.ifPresent(doctor -> {
            examsResponse.setId(doctor.getId());
            examsResponse.setName(doctor.getName());
            examsResponse.setExamsCode(doctor.getExamsCode());
        });

        log.info("Dados do exame {} - {} obtida com sucesso.", examsResponse.getId(), examsResponse.getName());
        return examsResponse;
    }

    public boolean deleteExam(UUID id) {
        log.debug("Localizando o exame com id: {}", id);

        Optional<Exams> examDataDb = examsRepository.findById(id);
        if (!examDataDb.isPresent()){
            log.error("Não foi possível localizar o exame {}", id);
            return false;
        }

        log.debug("Deletando o exame com id: {}", id);
        examsRepository.deleteById(id);
        log.info("Exame {} deletado!", id);
        return true;
    }

    public ExamsResponse updateExam(UUID id, ExamsRequest examsRequest) {
        log.debug("Localizando o exame com id: {}", id);
        log.debug("Request: {}", examsRequest);

        Optional<Exams> doctorDataDb = examsRepository.findById(id);
        if(doctorDataDb.isPresent()) {
            Exams examsModel = doctorDataDb.get();
            BeanUtils.copyProperties(examsRequest, examsModel, "id");

            log.info("Exame atualizado com sucesso.");
            return new ExamsResponse(examsModel.getId(), examsModel.getName(), examsModel.getExamsCode());
        }

        return null;
    }

}
