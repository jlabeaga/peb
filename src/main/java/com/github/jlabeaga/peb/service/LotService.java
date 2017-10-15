package com.github.jlabeaga.peb.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.exception.PebBusinessException;
import com.github.jlabeaga.peb.exception.PebWarningException;
import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Input;
import com.github.jlabeaga.peb.model.Lot;
import com.github.jlabeaga.peb.model.Part;
import com.github.jlabeaga.peb.model.Part.Status;
import com.github.jlabeaga.peb.model.SearchCriteria;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.repository.CompanyRepository;
import com.github.jlabeaga.peb.repository.InputRepository;
import com.github.jlabeaga.peb.repository.LotRepository;
import com.github.jlabeaga.peb.repository.PartRepository;
import com.github.jlabeaga.peb.repository.UserRepository;
import com.github.jlabeaga.peb.repository.VarietyRepository;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@Service
public class LotService {

	private static final Logger log = LoggerFactory.getLogger(LotService.class);

	@Autowired
	private LotRepository lotRepository;
	
	@Autowired
	private PartRepository partRepository;

	@Autowired
	private VarietyRepository varietyRepository;
	
	@Autowired
	private CompanyService companyService;
	
	public List<Lot> findAll() {
		return lotRepository.findAll();
	}
	
	public List<Lot> findByInput(Long inputId) {
		return lotRepository.findByInput(inputId);
	}
	
	public Lot findOne(Long id) {
		return lotRepository.findOne(id);
	}
	
	public Lot findOneWithParts(Long id) {
		return lotRepository.findOneWithParts(id);
	}
	
	public void delete(Long id) throws PebBusinessException {
		if( !isUnassigned(id) ) {
			throw new PebBusinessException("No se puede eliminar el lote porque tiene partidas asignadas a algún palet");
		}
		lotRepository.delete(id);
	}

	public Lot save(Lot lot) {
		return lotRepository.save(lot);			
	}

	public Lot processLot(Lot lot) throws PebBusinessException {
		if( lot.getStatus().equals(Lot.Status.PROCESSED) ) {
			throw new PebBusinessException("El lote ya se encuentra en estado Procesado");
		}
		if( !lot.getParts().isEmpty() ) {
			throw new PebBusinessException("El lote no puede ser procesado porque tiene ya partidas asignadas");
		}
		Part atLeastOnePart = createUniquePartForLot(lot);
		partRepository.save(atLeastOnePart);
		lot.setStatus(Lot.Status.PROCESSED);
		lot = lotRepository.save(lot);	
		return lot;
	}

	private Part createUniquePartForLot(Lot lot) {
		Part newPart = newPartForLot(lot, lot.getWeightProcessed());
		return newPart;
	}
	
	private Part newPartForLot(Lot lot, int partWeight) {
		Part newPart = new Part();
		newPart.setLot(lot);
		newPart.setCode(lot.getCode() + "-" + lot.nextPartCounter());
		newPart.setWeight(partWeight);
		newPart.setStatus(Status.UNASSIGNED);
		return newPart;
	}
	
	public Part splitPart(Part oldPart, int oldPartNewWeight, int newPartWeight) throws PebBusinessException {
		int initialWeight = oldPart.getWeight();
		if( initialWeight != oldPartNewWeight + newPartWeight ) {
			throw new PebBusinessException("La suma de nuevos pesos no coincide con el peso anterior");
		}
		oldPart.setWeight(oldPartNewWeight);
		Part newPart = newPartForLot(oldPart.getLot(), newPartWeight);
		return newPart;
	}
	
	public void checkWeigthBalance(Lot lot) throws PebBusinessException {
		if( lot.getParts().stream().map(part->part.getWeight()).mapToInt(Integer::intValue).sum() != lot.getWeightProcessed() ) {
			throw new PebBusinessException("Atención, la suma de pesos de las partidas no coincide con el peso total del lote");
		}
	}
	
	public void deletePart(Long partId) {
		partRepository.delete(partId);
	}

	public List<Lot> search(SearchCriteria searchCriteria) {
		log.debug("InputService.searchCriteria="+searchCriteria);
		return lotRepository.findAll();
	}
	
	public boolean isUnassigned(Long lotId) {
		Lot lot = lotRepository.findOne(lotId);
		for( Part part : lot.getParts() ) {
			if( part.getStatus() != Part.Status.UNASSIGNED ) {
				return false;
			}
		}
		return true;
	}

	public String buildCodeFromData(Lot lot) {
		String result = "";
		if( lot == null ) {
			return result;
		}
		if( lot.getCompany() != null ) {
			result += lot.getCompany().getCode();
			result += lot.getCompany().getProductionType();
			result += lot.getCompany().getArea();
		}
		if( lot.getVariety() != null ) {
			result += lot.getVariety().getCode();
		}
		if( lot.getInput() != null && lot.getInput().getInputDate() != null ) {
			result += String.format("%2d", lot.getInput().getInputDate().getDayOfMonth());
			result += String.format("%2d", lot.getInput().getInputDate().getMonthValue());
		}
		return result;
	}
	
	public Lot setCodeFromData(Lot lot) {
		lot.setCode(buildCodeFromData(lot));
		return lot;
	}

	public Lot buildDataFromCode(Lot lot) {
		// if length < 8 is not a valid code
		if( lot.getCode() == null || lot.getCode().length() < 10 ) {
			throw new PebWarningException("El codigo de lote no es correcto");
		}
		lot.setCompany( companyService.findByCode(lot.getCode().substring(0,2)) );
		lot.setVariety( varietyRepository.findByCode(lot.getCode().substring(4,6)) );
		String theoreticalCode = buildCodeFromData(lot);
		if( !lot.getCode().equals(theoreticalCode) ) {
			throw new PebWarningException(String.format("El codigo de lote '%s' no coincide con el teórico: '%s'", lot.getCode(), theoreticalCode));
		}
		return lot;
	}
	

}
