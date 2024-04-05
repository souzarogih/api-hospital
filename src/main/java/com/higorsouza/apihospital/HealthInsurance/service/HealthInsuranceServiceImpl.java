package com.higorsouza.apihospital.HealthInsurance.service;

import com.higorsouza.apihospital.HealthInsurance.dto.HealthInsuranceRequest;
import com.higorsouza.apihospital.HealthInsurance.dto.HealthInsuranceResponse;
import com.higorsouza.apihospital.HealthInsurance.model.HealthInsurance;
import com.higorsouza.apihospital.HealthInsurance.repository.HealthInsuranceRepository;
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
public class HealthInsuranceServiceImpl implements HealthInsuranceService {

    @Autowired
    HealthInsuranceRepository healthInsuranceRepository;

    public HealthInsuranceResponse create(HealthInsuranceRequest healthInsuranceRequest) {
        log.debug("Analisando dados para cadastro do Médico. {}", healthInsuranceRequest.getName());
        HealthInsurance healthInsuranceDataDb = healthInsuranceRepository.save(new HealthInsurance(
                UUID.randomUUID(),
                healthInsuranceRequest.getName(),
                healthInsuranceRequest.getCnpj()
        ));
        log.info("Médico {} salvo com id {}", healthInsuranceDataDb.getName(), healthInsuranceDataDb.getCnpj());
        return new HealthInsuranceResponse(healthInsuranceDataDb.getId(), healthInsuranceDataDb.getName(), healthInsuranceDataDb.getCnpj());
    }

    public List<HealthInsuranceResponse> listAll() {
        log.debug("Localizando todos os médicos cadastrados na base de dados");

        List<HealthInsuranceResponse> healthInsuranceDataDb = healthInsuranceRepository.findAll().stream()
                .map(healthInsure -> new HealthInsuranceResponse(
                        healthInsure.getId(), healthInsure.getName(), healthInsure.getCnpj()
                )).collect(Collectors.toList());
        log.info("Lista de médicos obtida");
        return healthInsuranceDataDb;
    }

    public HealthInsuranceResponse getHealthInsuranceById(UUID id) {
        log.debug("Localizando o médico com id: {}", id);

        Optional<HealthInsurance> healthInsuranceDataDb = healthInsuranceRepository.findById(id);
        HealthInsuranceResponse healthInsuranceResponse = new HealthInsuranceResponse();
        healthInsuranceDataDb.ifPresent(healthInsure -> {
            healthInsuranceResponse.setId(healthInsure.getId());
            healthInsuranceResponse.setName(healthInsure.getName());
            healthInsuranceResponse.setCnpj(healthInsure.getCnpj());
        });

        log.info("Dados do médico {} - {} obtida com sucesso.", healthInsuranceResponse.getId(), healthInsuranceResponse.getName());
        return healthInsuranceResponse;
    }

    public boolean deleteHealthInsurance(UUID id) {
        log.debug("Localizando o médico com id: {}", id);

        Optional<HealthInsurance> healthInsuranceDataDb = healthInsuranceRepository.findById(id);
        if (!healthInsuranceDataDb.isPresent()){
            log.error("Não foi possível localizar o médico {}", id);
            return false;
        }

        log.debug("Deletando o médico com id: {}", id);
        healthInsuranceRepository.deleteById(id);
        log.info("Médico {} deletado!", id);
        return true;
    }

    public HealthInsuranceResponse updateHealthInsurance(UUID id, HealthInsuranceRequest healthInsuranceRequest) {
        log.debug("Localizando o médico com id: {}", id);
        log.debug("Request: {}", healthInsuranceRequest);

        Optional<HealthInsurance> healthInsuranceDataDb = healthInsuranceRepository.findById(id);
        if(healthInsuranceDataDb.isPresent()) {
            HealthInsurance healthInsuranceModel = healthInsuranceDataDb.get();
            BeanUtils.copyProperties(healthInsuranceRequest, healthInsuranceModel, "id");

            log.info("Médico atualizado com sucesso.");
            return new HealthInsuranceResponse(healthInsuranceModel.getId(), healthInsuranceModel.getName(), healthInsuranceModel.getCnpj());
        }

        return null;
    }
}
