package com.github.jlabeaga.peb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
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
	
	@Convert(converter = LocalDateAttributeConverter.class)
	private LocalDateTime outputDate;
	
	@OneToOne
	private Customer customer;

	private int weight;
	
	private BigDecimal price = new BigDecimal(0);
	
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

	public LocalDateTime getOutputDate() {
		return outputDate;
	}

	public void setOutputDate(LocalDateTime outputDate) {
		this.outputDate = outputDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Pallet [code=" + code + ", weight=" + weight + ", status=" + status + ", outputDate=" + outputDate 
				+ ", customer=" + ((customer==null)?"":customer.getName()) + "]";
	}



}
