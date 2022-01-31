package com.example.demo.repositories;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.Patient;

public interface AppointmentRepo  extends CrudRepository<Appointment , Integer>{

	Optional<Appointment> findByPatientAndDate(Patient patient, Date date);

}
