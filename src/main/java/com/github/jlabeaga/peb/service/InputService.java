package com.github.jlabeaga.peb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Input;
import com.github.jlabeaga.peb.model.SearchCriteria;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.repository.CompanyRepository;
import com.github.jlabeaga.peb.repository.InputRepository;
import com.github.jlabeaga.peb.repository.UserRepository;

@Service
public class InputService {

	private static final Logger log = LoggerFactory.getLogger(InputService.class);

	@Autowired
	private InputRepository inputRepository;
	
	public List<Input> findAll() {
		return inputRepository.findAll();
	}
	
	public Input findOne(Long id) {
		return inputRepository.findOne(id);
	}
	
	public void delete(Long id) {
		inputRepository.delete(id);
	}

	public void save(Input input) {
		inputRepository.save(input);
	}

	public List<Input> search(SearchCriteria searchCriteria) {
		log.debug("InputService.searchCriteria="+searchCriteria);
		return inputRepository.findAll();
	}
	
}
