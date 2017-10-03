package com.github.jlabeaga.peb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company findOne(Long id) {
		return companyRepository.findOne(id);
	}
	
	public void delete(Company company) {
		companyRepository.delete(company);
	}

	public void save(Company company) {
		companyRepository.save(company);
	}

}
