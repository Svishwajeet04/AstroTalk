package com.example.demo.controllers;

import java.text.ParseException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AdmitPatientDto;
import com.example.demo.dtos.AppointmentDto;
import com.example.demo.dtos.PatientDto;
import com.example.demo.dtos.TestBookingDto;
import com.example.demo.services.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	PatientService pservice;

	@GetMapping("/data")
	public ResponseEntity<Object> getPatientData(@RequestHeader("Authorization")String auth ,@RequestParam int id) {
		HashMap<String, Object> res = pservice.getPatientData(id);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/addPatient")
	public ResponseEntity<Object> addPatientData(@RequestHeader("Authorization")String auth ,@RequestBody PatientDto dto) {
		HashMap p = pservice.addPatientData(dto);
		return ResponseEntity.ok(p);
	}

	@PostMapping("/bookAppointment")
	public ResponseEntity<Object> bookAppointment(@RequestHeader("Authorization")String auth ,@RequestBody AppointmentDto dto) throws ParseException {

		return ResponseEntity.ok(pservice.bookAppointment(dto));
	}

	@PostMapping("/bookTest")
	public ResponseEntity<Object> bookTest(@RequestHeader("Authorization")String auth ,@RequestBody TestBookingDto dto) {
		return ResponseEntity.ok(pservice.bookTest(dto));
	}
//
//	@PostMapping("/bookOperation")
//	public ResponseEntity<Object> bookOT() {
//		return null;
//	}

	@GetMapping("/dischargePatient")
	public ResponseEntity<Object> dischargePatient(@RequestHeader("Authorization")String auth ,@RequestParam int id) {
		return ResponseEntity.ok(pservice.dischargePatient(id));
	}

	@PostMapping("/admitPatient")
	public ResponseEntity<Object> admitPatient(@RequestHeader("Authorization")String auth ,@RequestBody AdmitPatientDto dto) {

		return ResponseEntity.ok(pservice.admitPatient(dto));
	}

	@PostMapping("/addBedBookingForTodayforAllAdmittedPatient")
	public ResponseEntity<Object> addBedBookingForTodayforAllAdmittedPatient(@RequestHeader("Authorization")String auth ) {

		return ResponseEntity.ok(pservice.addBedBookingForTodayforAllAdmittedPatient());
	}

}
