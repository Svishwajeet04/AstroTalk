package com.example.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Hospital {

	@Id
	private String name;

	private String address;

	@OneToMany(targetEntity = Employee.class)
	private List<Employee> employees;

	@OneToMany(targetEntity = Department.class)
	private List<Department> departments;

	@OneToMany(targetEntity = Laboratory.class)
	private List<Laboratory> labs;
//
//	@OneToMany(targetEntity = OT.class)
//	private List<OT> ots;

	@OneToMany(targetEntity = Bed.class)
	private List<Bed> beds;

//	@OneToOne(targetEntity = Employee.class)
//	private Employee Dean;

}
