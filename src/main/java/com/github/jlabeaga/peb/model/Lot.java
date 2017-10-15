package com.github.jlabeaga.peb.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.github.jlabeaga.peb.converters.LocalDateAttributeConverter;


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

	@ManyToOne
	private Input input;
	
	@Convert(converter = LocalDateAttributeConverter.class)
	private LocalDate processingDate;
	
	@ManyToOne
	private Company company;

	@ManyToOne
	private Variety variety;

    @Enumerated(EnumType.STRING)
    private Status status;
	
	private int packages;

	private int weightGross;
	
	private int weightNet;
	
	private int weightProcessed;
	
	@OneToMany(mappedBy="lot")
    private Set<Part> parts;

	private int partCounter;
	
    public Lot() {
    	super();
    }


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public LocalDate getProcessingDate() {
		return processingDate;
	}


	public void setProcessingDate(LocalDate processingDate) {
		this.processingDate = processingDate;
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

	
	public Set<Part> getParts() {
		return parts;
	}


	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}


	public int getPartCounter() {
		return partCounter;
	}


	public void setPartCounter(int partCounter) {
		this.partCounter = partCounter;
	}


	public int nextPartCounter() {
		return ++partCounter;
	}

	@Override
	public String toString() {
		return "Lot [code=" + code + ", processingDate=" + processingDate + ", company=" + company + ", variety=" + variety
				+ ", status=" + status + ", packages=" + packages + ", weightGross=" + weightGross + ", weightNet="
				+ weightNet + ", weightProcessed=" + weightProcessed +", parts=" + parts
				+ ", partCounter=" + partCounter + ", input=" + input + "]";
	}


}
