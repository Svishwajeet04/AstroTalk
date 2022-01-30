package com.example.demo.entities;

import java.sql.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Date date;

	private HashMap<String, Integer> item_Price;

	private HashMap<String, Integer> item_Unit;

	private HashMap<String, Integer> item_amount;

	private int totalAmount;

	@ManyToOne(targetEntity = Employee.class)
	private Employee billedBy;

	@ManyToOne(targetEntity = Patient.class)
	private Patient patient;

	private boolean cleared;

}
