package com.spring.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.dtos.HolidayClass;

@Repository
public interface HolidayRepo extends JpaRepository<HolidayClass, String>{

}
