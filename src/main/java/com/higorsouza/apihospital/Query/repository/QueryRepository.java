package com.higorsouza.apihospital.Query.repository;

import com.higorsouza.apihospital.Query.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueryRepository extends JpaRepository<Query, UUID> {
}
