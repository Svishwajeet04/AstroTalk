package com.example.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.demo.entities.enums.PatientStatus;

import lombok.Data;

@Entity
@Data
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

	private int age;

	@OneToMany(targetEntity = MedicalHistory.class)
	private List<MedicalHistory> mhis;

	private String phone;

	@OneToMany(targetEntity = Bill.class)
	private List<Bill> bills;

	private PatientStatus currentStatus;

	private boolean billCleared;

	private boolean admitted;

//	private int totalAmountPending;

}
