package com.example.demo.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class BedBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(targetEntity = MedicalHistory.class)
	private MedicalHistory medHistory;

	@ManyToOne(targetEntity = Bed.class)
	private Bed bed;

	@ManyToOne(targetEntity = Patient.class)
	private Patient patient;

	private Date date;

	@ManyToOne(targetEntity = Employee.class)
	private Employee attendedBy;

	private String symptoms;

	private String medicines;

}
