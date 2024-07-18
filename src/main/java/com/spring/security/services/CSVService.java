package com.spring.security.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.security.entities.Tutorial;
import com.spring.security.helper.CSVHelper;
import com.spring.security.repositories.TutorialRepository;

@Service
public class CSVService {
	Logger logger = LoggerFactory.getLogger(CSVService.class);

	@Autowired
	TutorialRepository repository;

	public void save(MultipartFile file) {
		try {
			List<Tutorial> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
			for (Tutorial tutorial : tutorials) {
				logger.info(tutorial.getTitle());
				System.out.println(tutorial.getTitle());
			}

			repository.saveAll(tutorials);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<Tutorial> getAllTutorials() {
		return repository.findAll();
	}
}