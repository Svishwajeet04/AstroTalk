package com.example.demo.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.example.demo.entities.enums.TestSampleType;

import lombok.Data;

@Entity
@Data
public class TestBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	@ManyToOne(targetEntity = Patient.class)
	private Patient patient;

	@ManyToOne(targetEntity = Employee.class)
	private Employee refferedBy;

	@ManyToMany(targetEntity = TestType.class)
	private List<TestType> tests;

	private Date date;

	private TestSampleType sampleType;

	private String sampleId;

	@OneToOne(targetEntity = Test.class)
	private Test test;

}
