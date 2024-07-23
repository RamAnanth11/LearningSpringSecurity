package com.spring.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.security.dtos.HolidayClass;
import com.spring.security.message.ResponseMessage;
import com.spring.security.services.HolidayServices;

@RestController
@RequestMapping("/api/holiday")
public class HolidayEndPointController {

	@Autowired
	HolidayServices holidayServices;

	@GetMapping("/list")
	public ResponseEntity<List<HolidayClass>> getListOfHolidays() {
		try {
			return new ResponseEntity<List<HolidayClass>>(holidayServices.getUpcomingHolidays(),
					HttpStatusCode.valueOf(200));
		} catch (Exception e) {
			return new ResponseEntity<List<HolidayClass>>(null);
		}
	}

	@PostMapping("/uploadHolidays")
	public ResponseEntity<?> uploadHolidays(@RequestParam("file") MultipartFile file) {
		String message = "";
		if (HolidayServices.hasCSVFormat(file)) {
			try {
				holidayServices.save(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
			} catch (Exception e) {
				e.printStackTrace();
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		} else {
			message = "Please upload a csv file!";
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
}
