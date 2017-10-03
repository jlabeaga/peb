package com.github.jlabeaga.peb.model;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.github.jlabeaga.peb.converters.LocalDateAttributeConverter;
import com.github.jlabeaga.peb.model.User.Status;


@Entity
public class Lot extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public enum Status {
		UNPROCESSED("Sin procesar"),
		RAW("Granel"),
		PROCESSED("Procesado");
		
		private String description;
		
		private Status(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
	
	@NotNull(message = "El código es obligatorio")
    private String code;

	@Convert(converter = LocalDateAttributeConverter.class)
	private LocalDateTime inputDate;
	
	@OneToOne
	private Company company;

	@OneToOne
	private Variety variety;

    @Enumerated(EnumType.STRING)
    private Status status;
	
	private int packages;

	private int weightGross;
	
	private int weightNet;
	
	private int weightProcessed;
	
	@ManyToOne
    private Input input;

	
    public Lot() {
    	super();
    }


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public LocalDateTime getInputDate() {
		return inputDate;
	}


	public void setInputDate(LocalDateTime inputDate) {
		this.inputDate = inputDate;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public Variety getVariety() {
		return variety;
	}


	public void setVariety(Variety variety) {
		this.variety = variety;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public int getPackages() {
		return packages;
	}


	public void setPackages(int packages) {
		this.packages = packages;
	}


	public int getWeightGross() {
		return weightGross;
	}


	public void setWeightGross(int weightGross) {
		this.weightGross = weightGross;
	}


	public int getWeightNet() {
		return weightNet;
	}


	public void setWeightNet(int weightNet) {
		this.weightNet = weightNet;
	}


	public int getWeightProcessed() {
		return weightProcessed;
	}


	public void setWeightProcessed(int weightProcessed) {
		this.weightProcessed = weightProcessed;
	}


	public Input getInput() {
		return input;
	}


	public void setInput(Input input) {
		this.input = input;
	}

	public String buildCode() {
		String result = "";
		if( getCompany() != null ) {
			result += getCompany().getCode();
			result += getCompany().getProductionType();
			result += getCompany().getArea();
		}
		if( getVariety() != null ) {
			result += getVariety().getId();
		}
		if( getInputDate() != null ) {
			result += String.format("%2d", getInputDate().getDayOfMonth());
			result += String.format("%2d", getInputDate().getMonthValue());
		}
		code = result;
		return code;
	}
	

	@Override
	public String toString() {
		return "Lot [code=" + code + ", inputDate=" + inputDate + ", company=" + company.getName() + ", variety=" + variety.getName()
				+ ", packages=" + packages + ", weightGross=" + weightGross + ", weightNet=" + weightNet + ", weightProcessed=" + weightProcessed + "]";
	}


}
