package com.higorsouza.apihospital.Doctor.service;

import com.higorsouza.apihospital.Doctor.dto.DoctorRequest;
import com.higorsouza.apihospital.Doctor.dto.DoctorResponse;
import com.higorsouza.apihospital.Doctor.model.Doctor;
import com.higorsouza.apihospital.Doctor.repository.DoctorRepository;
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
public class DoctorServiceImpl implements DoctorService{

    @Autowired
    DoctorRepository doctorRepository;

    public DoctorResponse create(DoctorRequest doctorRequest) {
        log.debug("Analisando dados para cadastro do Médico. {}", doctorRequest.getName());
        Doctor doctorDataDb = doctorRepository.save(new Doctor(
                UUID.randomUUID(),
                doctorRequest.getName(),
                doctorRequest.getCrm()
        ));
        log.info("Médico {} salvo com id {}", doctorDataDb.getName(), doctorDataDb.getCrm());
        return new DoctorResponse(doctorDataDb.getId(), doctorDataDb.getName(), doctorDataDb.getCrm());
    }

    public List<DoctorResponse> listAll() {
        log.debug("Localizando todos os médicos cadastrados na base de dados");

        List<DoctorResponse> doctorDataDb = doctorRepository.findAll().stream()
                .map(doctor -> new DoctorResponse(
                        doctor.getId(), doctor.getName(), doctor.getCrm()
                )).collect(Collectors.toList());
        log.info("Lista de médicos obtida");
        return doctorDataDb;
    }

    public DoctorResponse getDoctorById(UUID id) {
        log.debug("Localizando o médico com id: {}", id);

        Optional<Doctor> doctorDataDb = doctorRepository.findById(id);
        DoctorResponse doctorResponse = new DoctorResponse();
        doctorDataDb.ifPresent(doctor -> {
            doctorResponse.setId(doctor.getId());
            doctorResponse.setName(doctor.getName());
            doctorResponse.setCrm(doctor.getCrm());
        });

        log.info("Dados do médico {} - {} obtida com sucesso.", doctorResponse.getId(), doctorResponse.getName());
        return doctorResponse;
    }

    public boolean deleteDoctor(UUID id) {
        log.debug("Localizando o médico com id: {}", id);

        Optional<Doctor> doctorDataDb = doctorRepository.findById(id);
        if (!doctorDataDb.isPresent()){
            log.error("Não foi possível localizar o médico {}", id);
            return false;
        }

        log.debug("Deletando o médico com id: {}", id);
        doctorRepository.deleteById(id);
        log.info("Médico {} deletado!", id);
        return true;
    }

    public DoctorResponse updateDoctor(UUID id, DoctorRequest doctorRequest) {
        log.debug("Localizando o médico com id: {}", id);
        log.debug("Request: {}", doctorRequest);

        Optional<Doctor> doctorDataDb = doctorRepository.findById(id);
        if(doctorDataDb.isPresent()) {
            Doctor doctorModel = doctorDataDb.get();
            BeanUtils.copyProperties(doctorRequest, doctorModel, "id");

            log.info("Médico atualizado com sucesso.");
            return new DoctorResponse(doctorModel.getId(), doctorModel.getName(), doctorModel.getCrm());
        }

        return null;
    }
}
