package com.example.demo.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.example.demo.entities.enums.TestStatus;

import lombok.Data;

@Entity
@Data
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private TestStatus testStatus;

	private String result;

	@OneToOne(targetEntity = TestBooking.class, mappedBy = "test")
	private TestBooking bookingDetails;

	private Date resultDate;

}
