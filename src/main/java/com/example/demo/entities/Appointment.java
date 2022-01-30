package com.example.demo.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(targetEntity = Employee.class)
	private Employee doctor;

	@ManyToOne(targetEntity = Patient.class)
	private Patient patient;

	private Date bookingdateTime;

	private Date date;

	private boolean visited;

	@OneToOne(targetEntity = MedicalHistory.class)
	private MedicalHistory medHistory;

}
