package com.example.demo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.AdmitPatientDto;
import com.example.demo.dtos.AppointmentDto;
import com.example.demo.dtos.PatientDto;
import com.example.demo.dtos.TestBookingDto;
import com.example.demo.entities.Appointment;
import com.example.demo.entities.Bed;
import com.example.demo.entities.BedBooking;
import com.example.demo.entities.Bill;
import com.example.demo.entities.Employee;
import com.example.demo.entities.MedicalHistory;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Test;
import com.example.demo.entities.TestBooking;
import com.example.demo.entities.TestType;
import com.example.demo.entities.enums.BedType;
import com.example.demo.entities.enums.PatientStatus;
import com.example.demo.entities.enums.TestStatus;
import com.example.demo.repositories.AppointmentRepo;
import com.example.demo.repositories.BedBookingRepo;
import com.example.demo.repositories.BedRepo;
import com.example.demo.repositories.BillRepo;
import com.example.demo.repositories.EmployeeRepo;
import com.example.demo.repositories.MedicalHistoryRepo;
import com.example.demo.repositories.PatientRepo;
import com.example.demo.repositories.TestBookingRepo;
import com.example.demo.repositories.TestRepo;
import com.example.demo.repositories.TestTypeRepo;

@Service
public class PatientService {

	@Autowired
	PatientRepo prepo;

	@Autowired
	EmployeeRepo erepo;

	@Autowired
	BillRepo brepo;

	@Autowired
	TestRepo trepo;

	@Autowired
	AppointmentRepo arepo;

	@Autowired
	TestTypeRepo ttrepo;

	@Autowired
	MedicalHistoryRepo mrepo;

	@Autowired
	TestBookingRepo tbrepo;

	@Autowired
	BedRepo bedRepo;

	@Autowired
	BedBookingRepo bbrepo;

	public HashMap<String, Object> getPatientData(int id) {
		HashMap<String, Object> res = null;
		Optional<Patient> pt = prepo.findById(id);
		if (pt.isPresent()) {
			res = new HashMap<>();
			Patient p = pt.get();
			res.put("id", p.getId());
			res.put("name", p.getName());
			res.put("age", p.getAge());
			if (p.getBills() != null)
				res.put("bills", getBillDto(p.getBills()));
			res.put("phone", p.getPhone());
			res.put("Patient_Status", p.getCurrentStatus());
			if (p.getMhis() != null)
				res.put("Medical_History", getMedHistoryDto(p.getMhis()));
		}
		return res;
	}

	private List<HashMap<String, Object>> getBillDto(List<Bill> bt) {
		List<HashMap<String, Object>> ls = new ArrayList<>();
		for (Bill b : bt) {
			HashMap<String, Object> res = new HashMap<>();
			res.put("Billedby", b.getBilledBy().getName());
			res.put("date", b.getDate());
			res.put("Item_Price", b.getItem_Price());
			res.put("Item_Unit", b.getItem_Unit());
			res.put("cleared", b.isCleared());
			res.put("id", b.getId());
			ls.add(res);
		}
		return ls;
	}

	private List<HashMap<String, Object>> getMedHistoryDto(List<MedicalHistory> bt) {
		List<HashMap<String, Object>> ls = new ArrayList<>();
		for (MedicalHistory b : bt) {
			HashMap<String, Object> res = new HashMap<>();
			res.put("BedBookingDetails", getBedBookingsDto(b.getBedBookings()));
			res.put("medicines", b.getMedicines());
			res.put("departement", b.getDepartment().getName());
			res.put("diagnosis", b.getDiagnosis());
			res.put("date", b.getDate());
			res.put("todate", b.getTodate());
			res.put("doctor", b.getDoctor().getName());
			res.put("report", b.getReports());
			res.put("testDetails", getTestDetails(b.getTestBookings()));
//			res.put("OperationDetails", getOtDetails(b.getOtbooking()));
			ls.add(res);
		}
		return ls;
	}

//	private List<HashMap<String, Object>> getOtDetails(List<OTBooking> tls) {
//		List<HashMap<String, Object>> ls = new ArrayList<>();
//		for (OTBooking b : tls) {
//			HashMap<String, Object> res = new HashMap<>();
//			res.put(null, b.getDate());
//			res.put(null, b.getId());
//			res.put(null, b.getOtObject().getId());
//			ls.add(res);
//		}
//		return ls;
//	}

	private List<HashMap<String, Object>> getTestDetails(List<TestBooking> tls) {
		List<HashMap<String, Object>> ls = new ArrayList<>();
		for (TestBooking b : tls) {
			HashMap<String, Object> res = new HashMap<>();
			res.put(null, b.getDate());
			res.put(null, b.getId());
			res.put(null, b.getTest().getId());
			ls.add(res);
		}
		return ls;
	}

	private List<HashMap<String, Object>> getBedBookingsDto(List<BedBooking> bedBookings) {
		List<HashMap<String, Object>> ls = new ArrayList<>();
		for (BedBooking b : bedBookings) {
			HashMap<String, Object> res = new HashMap<>();
//			res.put("attendedBy", b.getAttendedBy().getName());
//			res.put("BedId", b.getBed().getId());
			res.put("date", b.getDate());
			res.put("BedBookingId", b.getId());
			ls.add(res);
		}
		return ls;
	}

	public HashMap addPatientData(PatientDto dto) {
		Optional<Patient> op = prepo.findByPhone(dto.getPhone());
		HashMap<String, Object> res = new HashMap<>();
		if (op.isEmpty()) {

			Patient p = new Patient();
			p.setAdmitted(false);
			p.setCurrentStatus(PatientStatus.DISCHARGED);
			p.setAge(dto.getAge());
			p.setBillCleared(false);
			p.setMhis(null);
			p.setName(dto.getName());
			p.setPhone(dto.getPhone());
			p = prepo.save(p);
			return getPatientData(p.getId());
		} else {
			res.put("result", "phone already exists");
			return res;
		}
	}

	public HashMap bookAppointment(AppointmentDto dto) {
		Appointment a = new Appointment();

		Optional<Patient> op = prepo.findById(dto.getPatientId());
		Optional<Employee> oe = erepo.findById(dto.getDoctorId());
		if (op.isPresent() && oe.isPresent()) {
			a.setVisited(true);
			a.setBookingdateTime(new Date());
			a.setDate(new Date());
			a.setMedHistory(null);
			a.setPatient(op.get());
			a.setDoctor(oe.get());
			a = arepo.save(a);
			a.setMedHistory(createMedicalHistory(a));
			HashMap<String, Integer> item_unit = new HashMap<>();
			item_unit.put("appointment", 1);
			HashMap<String, Integer> item_price = new HashMap<>();
			item_price.put("appointment", oe.get().getConsultantFee());
			createBill(oe.get(), item_unit, item_price, op.get());
			return getPatientData(a.getPatient().getId());
		}
		return null;

	}

	private MedicalHistory createMedicalHistory(Appointment a) {
		MedicalHistory m = new MedicalHistory();
		m.setAdmitted(false);
		m.setBedBookings(null);
		m.setDepartment(a.getDoctor().getDepartment());
		m.setDate(a.getBookingdateTime());
		m.setOperationSuggested(false);
		m.setPatient(a.getPatient());
		m.setDoctor(a.getDoctor());
		m.setTodate(new Date());
		mrepo.save(m);
		return m;
	}

	public String bookTest(TestBookingDto dto) {
		TestBooking t = new TestBooking();
		Optional<Patient> op = prepo.findById(dto.getPatient());
		Optional<Employee> oe = erepo.findById(dto.getRefferedBy());
		if (op.isPresent() && oe.isPresent()) {
			t.setPatient(op.get());
			t.setRefferedBy(oe.get());
			t.setSampleId(dto.getSampleId());
			Iterable<TestType> ils = ttrepo.findAllById(dto.getTestsIds());
			List<TestType> ls = new LinkedList<TestType>();
			int amount = 0;
			for (TestType temp : ils) {
				ls.add(temp);
				amount += temp.getPrice();
			}
			t.setTests(ls);
			t.setSampleType(dto.getSampleType());
			t.setTest(createTest(t));
			t.setDate(new Date());
			HashMap<String, Integer> item_unit = new HashMap<String, Integer>();
			item_unit.put("testBooking", 1);
			HashMap<String, Integer> item_amount = new HashMap<String, Integer>();
			item_unit.put("testBooking", amount);
			createBill( oe.get() ,item_unit, item_amount, op.get());
			t = tbrepo.save(t);
			return String.valueOf(t.getId());
		}
		return "error booking test";
	}

	private Test createTest(TestBooking tb) {
		Test t = new Test();
		t.setBookingDetails(tb);
		t.setResult("");
		t.setResultDate(new Date());
		t.setTestStatus(TestStatus.ARRIVED);
		t = trepo.save(t);
		return t;
	}

	public boolean admitPatient(AdmitPatientDto dto) {
		Optional<Patient> op = prepo.findById(dto.getPatientId());
		if (op.isPresent()) {
			Optional<MedicalHistory> om = mrepo.findByPatientAndTodate(op.get(), new Date());
			if (om.isPresent()) {
				MedicalHistory m = om.get();
				Patient p = op.get();
				m.setAdmitted(true);
				p.setAdmitted(true);
				p.setCurrentStatus(PatientStatus.ADMITTED);
				List<BedBooking> bls = m.getBedBookings();
				BedBooking bb = createBedBooking(dto.getBedType(), p, m, m.getDoctor());
				if (bb != null) {
					bls.add(bb);
					m.setBedBookings(bls);
					m.setTodate(new Date());
					m = mrepo.save(m);
					return true;
				}
			}
		}
		return false;
	}

	private BedBooking createBedBooking(BedType bedType, Patient p, MedicalHistory m, Employee doctor) {
		List<Bed> bed = bedRepo.findAllByBedTypeAndStatus(bedType, false);
		if (bed != null) {
			BedBooking b = new BedBooking();
			b.setAttendedBy(doctor);
			b.setDate(new Date());
			b.setMedHistory(m);
			b.setPatient(p);
			b.setBed(bed.get(0));
			Bed be = bed.get(0);
			be.setStatus(true);
			bedRepo.save(be);
			b = bbrepo.save(b);
			HashMap<String, Integer> item_unit = new HashMap<>();
			item_unit.put("bed - " + bedType.toString(), 1);
			HashMap<String, Integer> item_price = new HashMap<>();
			item_unit.put("bed - " + bedType.toString(), bedType.getPrice());
			createBill(doctor , item_unit, item_price, p);
			return b;
		}
		return null;
	}

	private void createBill(Employee e , HashMap<String, Integer> item_unit, HashMap<String, Integer> item_price, Patient p) {
		Bill b = new Bill();
		b.setBilledBy(e);
		b.setDate(new Date());
		b.setPatient(p);
		b.setItem_Price(item_price);
		b.setItem_Unit(item_unit);
		b.setCleared(false);
		HashMap<String, Integer> item_amount = new HashMap<String, Integer>();
		int total = 0;
		for (String s : item_price.keySet()) {
			int amount = item_unit.get(s) * item_price.get(s);
			total += amount;
			item_amount.put(s, amount);
		}
		b.setItem_amount(item_amount);
		b.setTotalAmount(total);
		b = brepo.save(b);
		List<Bill> bls = p.getBills();
		bls.add(b);
		p.setBills(bls);
		p.setTotalAmountPending(total + p.getTotalAmountPending());
		p = prepo.save(p);
		brepo.save(b);
	}

	public String addBedBookingForTodayforAllAdmittedPatient() {
		List<Patient> lsp = prepo.findAllByAdmitted(true);
		for (Patient p : lsp) {
			List<MedicalHistory> ml = p.getMhis();
			MedicalHistory req = null;
			for (MedicalHistory m : ml) {
				if (m.getTodate().compareTo(new Date()) == -1) {
					req = m;
				}
			}
			if (req != null) {
				req.setAdmitted(true);
				p.setAdmitted(true);
				p.setCurrentStatus(PatientStatus.ADMITTED);
				List<BedBooking> bls = req.getBedBookings();
				Collections.sort(bls, new Comparator<BedBooking>() {

					@Override
					public int compare(BedBooking o1, BedBooking o2) {

						return o1.getDate().compareTo(o2.getDate()) * -1;
					}
				});
				BedBooking bb = createBedBooking(bls.get(0).getBed().getBedType(), p, req, req.getDoctor());
				if (bb != null) {
					bls.add(bb);
					req.setBedBookings(bls);
					req.setTodate(new Date());
					req = mrepo.save(req);
				}
			}
		}
		return "done successfully ";
	}

	public String dischargePatient(int id) {
		Optional<Patient> op = prepo.findById(id);
		if (op.isPresent() && op.get().isAdmitted()) {
			Patient p = op.get();
			int total = p.getTotalAmountPending();
			if (total > 0) {
				return "cannot discharge patient , pending bill of amount " + total;
			} else {
				p.setAdmitted(false);
				p.setCurrentStatus(PatientStatus.DISCHARGED);
				Optional<MedicalHistory> om = mrepo.findByPatientAndTodate(p, new Date());
				MedicalHistory m = om.get();
				List<BedBooking> bb = m.getBedBookings();
				Bed bed = bb.get(0).getBed();
				bed.setStatus(false);
				bedRepo.save(bed);
				prepo.save(p);
				return "patient discharged successfully";
			}
		}
		return "no such patient";
	}
}
