package com.github.jlabeaga.peb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.exception.PebBusinessException;
import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Input;
import com.github.jlabeaga.peb.model.Lot;
import com.github.jlabeaga.peb.model.Part;
import com.github.jlabeaga.peb.model.SearchCriteria;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.repository.CompanyRepository;
import com.github.jlabeaga.peb.repository.InputRepository;
import com.github.jlabeaga.peb.repository.LotRepository;
import com.github.jlabeaga.peb.repository.PartRepository;
import com.github.jlabeaga.peb.repository.UserRepository;

@Service
public class LotService {

	private static final Logger log = LoggerFactory.getLogger(LotService.class);

	@Autowired
	private LotRepository lotRepository;
	
	@Autowired
	private PartRepository partRepository;
	
	public List<Lot> findAll() {
		return lotRepository.findAll();
	}
	
	public List<Lot> findByInput(Long inputId) {
		return lotRepository.findByInput(inputId);
	}
	
	public Lot findOne(Long id) {
		return lotRepository.findOne(id);
	}
	
	public void delete(Long id) throws PebBusinessException {
		if( !isUnassigned(id) ) {
			throw new PebBusinessException("No se puede eliminar el lote porque tiene partidas asignadas a algún palet");
		}
		lotRepository.delete(id);
	}

	public void save(Lot lot) {
		lotRepository.save(lot);
	}

	public List<Lot> search(SearchCriteria searchCriteria) {
		log.debug("InputService.searchCriteria="+searchCriteria);
		return lotRepository.findAll();
	}
	
	public List<Part> getParts(Long lotId) {
		return partRepository.getParts(lotId);
	}

	public boolean isUnassigned(Long lotId) {
		List<Part> parts = getParts(lotId);
		for( Part part : parts ) {
			if( part.getStatus() != Part.Status.UNASSIGNED ) {
				return false;
			}
		}
		return true;
	}

}
