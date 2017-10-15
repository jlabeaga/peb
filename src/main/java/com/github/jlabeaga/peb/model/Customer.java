package com.github.jlabeaga.peb.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.github.jlabeaga.peb.converters.LocalDateTimeAttributeConverter;


@Entity
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "El nombre es obligatorio")
    private String name;

    private String nif;
    
    private String invoiceAddressLine1;
    
    private String invoiceAddressLine2;
    
    private String locationAddressLine1;
    
    private String locationAddressLine2;
    
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime dateCreated;

    public Customer() {
    	super();
    }

	public Customer(String name) {
		super();
		this.name = name;
		this.dateCreated = LocalDateTime.now();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}


	public String getInvoiceAddressLine1() {
		return invoiceAddressLine1;
	}

	public void setInvoiceAddressLine1(String invoiceAddressLine1) {
		this.invoiceAddressLine1 = invoiceAddressLine1;
	}

	public String getInvoiceAddressLine2() {
		return invoiceAddressLine2;
	}

	public void setInvoiceAddressLine2(String invoiceAddressLine2) {
		this.invoiceAddressLine2 = invoiceAddressLine2;
	}

	public String getLocationAddressLine1() {
		return locationAddressLine1;
	}

	public void setLocationAddressLine1(String locationAddressLine1) {
		this.locationAddressLine1 = locationAddressLine1;
	}

	public String getLocationAddressLine2() {
		return locationAddressLine2;
	}

	public void setLocationAddressLine2(String locationAddressLine2) {
		this.locationAddressLine2 = locationAddressLine2;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", nif=" + nif + ", dateCreated=" + dateCreated
				+ "]";
	}
	
	

}
