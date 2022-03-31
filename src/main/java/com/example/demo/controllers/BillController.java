package com.example.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.ClearBillDto;
import com.example.demo.services.BillService;

@RestController
@RequestMapping("/bill")
@Slf4j
public class BillController {

	@Autowired
	BillService bservice;

	@GetMapping("/viewTotalAmountPending")
	public ResponseEntity<Object> viewTotalAmountPending(@RequestHeader("Authorization") String auth,
			@RequestParam int id) {
		return ResponseEntity.ok(bservice.getTotalAmountPending(id));
	}

	@PostMapping("/clearBill")
	public ResponseEntity<Object> clearBill(@RequestHeader("Authorization") String auth,
			@RequestBody ClearBillDto dto) {
		return ResponseEntity.ok(bservice.clearBill(dto));
	}

	@GetMapping("/viewAllBill")
	public ResponseEntity<Object> viewBill(@RequestHeader("Authorization") String auth, @RequestParam int p) {

		return ResponseEntity.ok(bservice.viewAllBills(p));
	}

	@GetMapping("/viewPendingBill")
	public ResponseEntity<Object> viewPendingBill(@RequestHeader("Authorization") String auth, @RequestParam int p) {
		return ResponseEntity.ok(bservice.viewPendingBills(p));
	}

}
