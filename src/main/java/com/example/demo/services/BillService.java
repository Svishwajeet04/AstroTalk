package com.example.demo.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.ClearBillDto;
import com.example.demo.entities.Bill;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Patient;
import com.example.demo.repositories.BillRepo;
import com.example.demo.repositories.EmployeeRepo;
import com.example.demo.repositories.PatientRepo;

@Service
public class BillService {

	@Autowired
	PatientRepo prepo;

	@Autowired
	BillRepo brepo;

	@Autowired
	EmployeeRepo erepo;

	public HashMap<String, Object> viewPendingBills(int id) {
		HashMap<String, Object> res = null;
		Optional<Patient> pt = prepo.findById(id);
		if (pt.isPresent()) {
			res = new HashMap<>();
			Patient p = pt.get();



			res.put("name", p.getName());
			res.put("age", p.getAge());
			res.put("Pending bills", getPendingBillDto(p.getBills()));
			res.put("phone", p.getPhone());
			res.put("Patient_Status", p.getCurrentStatus());
		}
		return res;
	}

	private List<HashMap<String, Object>> getPendingBillDto(List<Bill> bills) {
		List<HashMap<String, Object>> ls = new ArrayList<>();
		for (Bill b : bills) {
			if (!b.isCleared()) {
				HashMap<String, Object> res = new HashMap<>();
				res.put("Billedby", b.getBilledBy().getName());
				res.put("date", b.getDate());
				res.put("Item_Price", b.getItem_Price());
				res.put("Item_Unit", b.getItem_Unit());
				res.put("cleared", b.isCleared());
				res.put("id", b.getId());
				ls.add(res);
			}
		}
		return ls;
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

	public HashMap<String, Object> viewAllBills(int id) {
		HashMap<String, Object> res = null;
		Optional<Patient> pt = prepo.findById(id);
		if (pt.isPresent()) {
			res = new HashMap<>();
			Patient p = pt.get();
			res.put("name", p.getName());
			res.put("age", p.getAge());
			res.put("All bills", getBillDto(p.getBills()));
			res.put("phone", p.getPhone());
			res.put("Patient_Status", p.getCurrentStatus());
		}
		return res;
	}

	public String clearBill(ClearBillDto dto) {
		Optional<Patient> pt = prepo.findById(dto.getPid());
		if (pt.isPresent()) {
			Patient p = pt.get();
			int initial = getTotalAmountPending(p.getId());
			int remain = initial - dto.getAmount();
			List<Bill> bills = p.getBills();
			for (Bill b : bills) {
				b.setCleared(true);
				brepo.save(b);
			}
			p.setBills(bills);
			prepo.save(p);
			if (remain > 0) {
				HashMap<String, Integer> item_unit = new HashMap<>();
				item_unit.put("residue bill", 1);
				HashMap<String, Integer> item_price = new HashMap<>();
				item_price.put("residue bill", remain);
				createBill(dto.getEid(), item_unit, item_price, p);
				return "amount pending " + (remain);
			}
			return "bill cleared successfully";
		}
		return "no such patient";
	}

	private void createBill(int eid, HashMap<String, Integer> item_unit, HashMap<String, Integer> item_price,
			Patient p) {
		Employee e = erepo.findById(eid).get();
		Bill b = new Bill();
		b.setBilledBy(e);
		b.setDate(new Date(new java.util.Date().getTime()));
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
		p = prepo.save(p);
		brepo.save(b);
		
	}

	public int getTotalAmountPending(int id) {
		Optional<Patient> op = prepo.findById(id);

		if (op.isPresent()) {
			Patient p = op.get();
			List<Bill> lb = p.getBills();
			int total = 0;
			for (Bill b : lb) {
				if (!b.isCleared()) {
					total += b.getTotalAmount();
				}
			}
			return total;
		}
		return -1;
	}
}
