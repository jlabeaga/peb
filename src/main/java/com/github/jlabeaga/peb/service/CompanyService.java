package com.github.jlabeaga.peb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.repository.CompanyRepository;
import com.github.jlabeaga.peb.view.CompanyView;

@Service
public class CompanyService {

	private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

	@Autowired
	private CompanyRepository companyRepository;
	
	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company findOne(Long id) {
		return companyRepository.findOne(id);
	}
	
	public void delete(Long id) {
		companyRepository.delete(id);
	}

	public void save(Company company) {
		companyRepository.save(company);
	}

}
