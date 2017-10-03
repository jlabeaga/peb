package com.github.jlabeaga.peb.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.github.jlabeaga.peb.converters.LocalDateTimeAttributeConverter;


@Entity
public class Company extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public enum ProductionType {
		C("Convencional"),
		E("Ecologico");

		private String description;
		
		private ProductionType(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
	public enum Area {
		A("Asturias"),
		G("Lugo"),
		L("León"),
		S("Santander");
		
		private String description;
		
		private Area(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
	@NotNull(message = "El nombre es obligatorio")
    private String name;

    private boolean enabled;

    private String code;
    
    @Enumerated(EnumType.STRING)
    private ProductionType productionType;
    
    @Enumerated(EnumType.STRING)
    private Area area;
    
    private String nif;
    
    private String invoiceAddressLine1;
    
    private String invoiceAddressLine2;
    
    private String locationAddressLine1;
    
    private String locationAddressLine2;

    private String comments;
    
    @OneToMany(fetch=FetchType.LAZY,mappedBy="company")
    private List<User> users;
    
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime dateCreated;

    public Company() {
    	super();
    }

	public Company(String name, String code, ProductionType productionType, Area area) {
		super();
		this.name = name;
		this.code = code;
		this.productionType = productionType;
		this.area = area;
		this.dateCreated = LocalDateTime.now();
		this.enabled = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ProductionType getProductionType() {
		return productionType;
	}

	public void setProductionType(ProductionType productionType) {
		this.productionType = productionType;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	
	public List<User> getUsers() {
		return users;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + "]";
	}
    
}
