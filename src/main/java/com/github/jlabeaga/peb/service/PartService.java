package com.github.jlabeaga.peb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.model.Part;
import com.github.jlabeaga.peb.model.SearchCriteria;
import com.github.jlabeaga.peb.repository.PartRepository;

@Service
public class PartService {

	private static final Logger log = LoggerFactory.getLogger(PartService.class);

	@Autowired
	private PartRepository partRepository;
	
	public List<Part> findAll() {
		return partRepository.findAll();
	}
	
	public Part findOne(Long id) {
		return partRepository.findOne(id);
	}
	
	public void delete(Long id) {
		partRepository.delete(id);
	}

	public Part save(Part part) {
		return partRepository.save(part);
	}

	public List<Part> search(SearchCriteria searchCriteria) {
		log.debug("PartService.searchCriteria="+searchCriteria);
		return partRepository.findAll();
	}
	
}
