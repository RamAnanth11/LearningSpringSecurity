package com.spring.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security.entities.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
}