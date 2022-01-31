package com.example.demo.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Hospital;
import com.example.demo.entities.Patient;
import com.example.demo.repositories.PatientRepo;

@Service
public class SearchService {

	@Autowired
	HospitalService hservice;

	@Autowired
	PatientRepo prepo;

	public Hospital getHospitalInfo() {

		HashMap<String, Object> ls = new HashMap<String, Object>();
		return hservice.getHospitalDataFromDB();
	}

	public List<HashMap<String, Object>> getAllAdmittedPatient() {
		List<Patient> pls = prepo.findAllByAdmitted(true);
		List<HashMap<String, Object>> ls = new LinkedList<>();
		for (Patient p : pls) {
			HashMap<String, Object> res = new HashMap<String, Object>();
			res.put("name", p.getName());
			res.put("age", p.getAge());
			res.put("phone", p.getPhone());
			res.put("admitted ", p.getCurrentStatus());
			ls.add(res);
		}
		return ls;
	}

}
