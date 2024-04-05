package com.higorsouza.apihospital.Exams.service;

import com.higorsouza.apihospital.Exams.dto.ExamsRequest;
import com.higorsouza.apihospital.Exams.dto.ExamsResponse;

import java.util.List;
import java.util.UUID;

public interface ExamsService {

    ExamsResponse create(ExamsRequest examsRequest);
    List<ExamsResponse> listAll();
    ExamsResponse getExamById(UUID id);
    boolean deleteExam(UUID id);
    ExamsResponse updateExam(UUID id, ExamsRequest examsRequest);
}
