package com.spring.security.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.security.dtos.HolidayClass;
import com.spring.security.repositories.HolidayRepo;

@Service
public class HolidayServices {
	Logger logger = LoggerFactory.getLogger(HolidayServices.class);
	public static String TYPE = "text/csv";

	@Autowired
	HolidayRepo holidayRepo;

	public void save(MultipartFile file) {
		try {
			List<HolidayClass> holidayClasses = csvToHoliday(file.getInputStream());
			for (HolidayClass holidayClass : holidayClasses) {
				logger.info(holidayClass.getNameOfTheHoliday());
			}
			holidayRepo.saveAll(holidayClasses);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public static List<HolidayClass> csvToHoliday(InputStream is) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			String firstLine = fileReader.readLine();
			if (firstLine != null && firstLine.startsWith("\uFEFF")) {
				firstLine = firstLine.substring(1);
			}
			try (CSVParser csvParser = CSVParser.parse(
					firstLine + "\n" + fileReader.lines().collect(Collectors.joining("\n")),
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
				List<HolidayClass> holiday = new ArrayList<>();
				Iterable<CSVRecord> csvRecords = csvParser.getRecords();
				for (CSVRecord csvRecord : csvRecords) {
					HolidayClass holidayClass = new HolidayClass(LocalDate.parse(csvRecord.get("date"), formatter),
							csvRecord.get("name"));
					holidayClass.setDay(holidayClass.getDate().getDayOfWeek());
					holiday.add(holidayClass);
				}
				return holiday;
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public List<HolidayClass> getUpcomingHolidays() {
		LocalDate today = LocalDate.now();
		return holidayRepo.findAll().stream().filter(holidayClass -> holidayClass.getDate().isAfter(today))
				.collect(Collectors.toList());
	}
}
