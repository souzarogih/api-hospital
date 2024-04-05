package com.higorsouza.apihospital.Exams.repository;

import com.higorsouza.apihospital.Exams.model.Exams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExamsRepository extends JpaRepository<Exams, UUID> {
}
