package com.github.jlabeaga.peb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.github.jlabeaga.peb.converters.LocalDateTimeAttributeConverter;


@Entity
public class Shipment extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime shippingDate;
	
	@OneToMany
    private Set<Pallet> pallets;
    
    @ManyToOne
    private Customer customer;
    
	private BigDecimal price = new BigDecimal(0);
	
    public Shipment() {
    	super();
    }

	public LocalDateTime getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(LocalDateTime shippingDate) {
		this.shippingDate = shippingDate;
	}

	public Set<Pallet> getPallets() {
		return pallets;
	}

	public void setPallets(Set<Pallet> pallets) {
		this.pallets = pallets;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Shipment [shippingDate=" + shippingDate + ", pallets=" + pallets + ", customer=" + customer + ", price="
				+ price + "]";
	}

	

}
