package com.github.jlabeaga.peb.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.github.jlabeaga.peb.converters.LocalDateTimeAttributeConverter;


@Entity
public class Part extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public enum Status {
		UNASSIGNED("Sin asignar"),
		ASSIGNED("Asignado"),
		WASTED("Desechado");
		
		private String description;
		
		private Status(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
	
	@Enumerated
	private Status status = Status.UNASSIGNED;
	
    private String code;

	@ManyToOne
    private Lot lot;

	private int weight;
	
	@ManyToOne
    private Pallet pallet;


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Lot getLot() {
		return lot;
	}


	public void setLot(Lot lot) {
		this.lot = lot;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}


	public Pallet getPallet() {
		return pallet;
	}


	public void setPallet(Pallet pallet) {
		this.pallet = pallet;
	}


	@Override
	public String toString() {
		return "Part [lot=" + lot.getCode() + ", code=" + code + ", weight=" + weight + ", status=" + status + ", pallet=" + ((pallet==null)?"":pallet.getCode()) + "]";
	}


	
}
