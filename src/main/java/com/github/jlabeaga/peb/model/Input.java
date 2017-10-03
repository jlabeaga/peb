package com.github.jlabeaga.peb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.github.jlabeaga.peb.converters.LocalDateAttributeConverter;
import com.github.jlabeaga.peb.converters.LocalDateTimeAttributeConverter;


@Entity
public class Input extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Convert(converter = LocalDateAttributeConverter.class)
	private LocalDate inputDate;
	
    @OneToOne
    private Company company;
    
    @OneToMany
    private List<Lot> lots;
    
    public Input() {
    	super();
    }

	public Input(LocalDate inputDate, Company company) {
		super();
		this.inputDate = inputDate;
		this.company = company;
	}

	public LocalDate getInputDate() {
		return inputDate;
	}

	public void setInputDate(LocalDate inputDate) {
		this.inputDate = inputDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	
	public List<Lot> getLots() {
		return lots;
	}

	public void setLots(List<Lot> lots) {
		this.lots = lots;
	}

	@Override
	public String toString() {
		return "Input [inputDate=" + inputDate + ", company=" + company + ", lots=" + lots + "]";
	}



}
