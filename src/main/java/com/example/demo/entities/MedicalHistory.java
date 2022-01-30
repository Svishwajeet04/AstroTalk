package com.example.demo.entities;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class MedicalHistory {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Employee getDoctor() {
		return doctor;
	}

	public void setDoctor(Employee doctor) {
		this.doctor = doctor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getMedicines() {
		return medicines;
	}

	public void setMedicines(String medicines) {
		this.medicines = medicines;
	}

	public boolean isAdmitted() {
		return admitted;
	}

	public void setAdmitted(boolean admitted) {
		this.admitted = admitted;
	}

	public boolean isTestSuggested() {
		return testSuggested;
	}

	public void setTestSuggested(boolean testSuggested) {
		this.testSuggested = testSuggested;
	}

	public String getReports() {
		return reports;
	}

	public void setReports(String reports) {
		this.reports = reports;
	}

	public boolean isOperationSuggested() {
		return operationSuggested;
	}

	public void setOperationSuggested(boolean operationSuggested) {
		this.operationSuggested = operationSuggested;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<BedBooking> getBedBookings() {
		return bedBookings;
	}

	public void setBedBookings(List<BedBooking> bedBookings) {
		this.bedBookings = bedBookings;
	}

	public List<TestBooking> getTestBookings() {
		return testBookings;
	}

	public void setTestBookings(List<TestBooking> testBookings) {
		this.testBookings = testBookings;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Employee doctor;

	private Date date;

	private Date todate;

	private String diagnosis;

	private String medicines;

	private boolean admitted;

	private boolean testSuggested;

	private String reports;

	private boolean operationSuggested;

	@ManyToOne(targetEntity = Department.class)
	private Department department;

//	@OneToMany(targetEntity = OTBooking.class)
//	private List<OTBooking> otbooking;

	@OneToMany(targetEntity = BedBooking.class)
	private List<BedBooking> bedBookings;

	@OneToMany(targetEntity = TestBooking.class)
	private List<TestBooking> testBookings;

}
