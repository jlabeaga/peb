package com.github.jlabeaga.peb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Input;
import com.github.jlabeaga.peb.model.Pallet;
import com.github.jlabeaga.peb.model.SearchCriteria;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.repository.CompanyRepository;
import com.github.jlabeaga.peb.repository.InputRepository;
import com.github.jlabeaga.peb.repository.PalletRepository;
import com.github.jlabeaga.peb.repository.UserRepository;

@Service
public class PalletService {

	private static final Logger log = LoggerFactory.getLogger(PalletService.class);

	@Autowired
	private PalletRepository palletRepository;
	
	public List<Pallet> findAll() {
		return palletRepository.findAll();
	}
	
	public Pallet findOne(Long id) {
		return palletRepository.findOne(id);
	}
	
	public void delete(Long id) {
		palletRepository.delete(id);
	}

	public Pallet save(Pallet pallet) {
		return palletRepository.save(pallet);
	}

	public List<Pallet> search(SearchCriteria searchCriteria) {
		log.debug("PalletService.searchCriteria="+searchCriteria);
		return palletRepository.findAll();
	}
	
}
