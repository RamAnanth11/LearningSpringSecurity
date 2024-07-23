package com.spring.security.dtos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HolidayClass {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private LocalDate date;
	private DayOfWeek day;
	private String nameOfTheHoliday;

	public HolidayClass() {
	}

	public HolidayClass(LocalDate date, DayOfWeek day, String nameOfTheHoliday) {
		super();
		this.date = date;
		this.day = day;
		this.nameOfTheHoliday = nameOfTheHoliday;
	}

	public HolidayClass(LocalDate date, String nameOfTheHoliday) {
		super();
		this.date = date;
		this.nameOfTheHoliday = nameOfTheHoliday;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public String getNameOfTheHoliday() {
		return nameOfTheHoliday;
	}

	public void setNameOfTheHolidy(String nameOfTheHoliday) {
		this.nameOfTheHoliday = nameOfTheHoliday;
	}

}
