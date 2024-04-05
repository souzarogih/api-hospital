package com.higorsouza.apihospital.Exams.model;

import com.higorsouza.apihospital.Doctor.model.Doctor;
import com.higorsouza.apihospital.Patient.model.Patient;
import com.higorsouza.apihospital.Query.model.Query;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Exams implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;
    private String examsCode;

    @ManyToOne
    private Doctor doctor;

    @OneToOne
    private Query query;

    @ManyToOne
    private Patient patient;
}
