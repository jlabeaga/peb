package com.github.jlabeaga.peb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.github.jlabeaga.peb.converters.LocalDateAttributeConverter;


@Entity
public class Pallet extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "El código es obligatorio")
    private String code;

	public enum Status {
		PREPARING,
		READY,
		SHIPPED,
		FROZEN,
		WASTED
	}

	private Status status = Status.PREPARING;
	
	private int weight;
	
	@OneToMany(mappedBy="pallet")
	private Set<Part> parts;
	
	@ManyToOne
	private Customer proposedCustomer;
	
	@ManyToOne
	private Shipment shipment;
	
    public Pallet() {
    	super();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	
	public Set<Part> getParts() {
		return parts;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}

	public Customer getProposedCustomer() {
		return proposedCustomer;
	}

	public void setProposedCustomer(Customer proposedCustomer) {
		this.proposedCustomer = proposedCustomer;
	}

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	@Override
	public String toString() {
		return "Pallet [code=" + code + ", status=" + status + ", weight=" + weight + ", parts=" + parts
				+ ", proposedCustomer=" + proposedCustomer + ", shipment=" + shipment + "]";
	}

	
}
