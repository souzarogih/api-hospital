package com.higorsouza.apihospital.Patient.model;

import com.higorsouza.apihospital.Exams.model.Exams;
import com.higorsouza.apihospital.HealthInsurance.model.HealthInsurance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;
    private String cpf;

    @ManyToOne
    private HealthInsurance healthInsurance;

    @OneToMany(mappedBy = "patient")
    private List<Exams> exams;
}
