package com.github.jlabeaga.peb.model;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.github.jlabeaga.peb.converters.LocalDateTimeAttributeConverter;


@Entity
public class User extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public enum Status {
		ENABLED("Activo"),
		CHANGE_PASSWORD("Cambio de password"),
		DISABLED("Inactivo");
		
		private String description;
		
		private Status(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
	public enum Role {
		USER("Usuario"),
		ADMIN("Administrador"),
		OPERATOR("Operador");

		private String description;
		
		private Role(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
	
	}
	
	
	@NotNull(message = "El username es obligatorio")
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "El email debe tener un formato correcto")
    private String email; // username = email
	
    private String password;
    
    private String changePasswordToken;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    private String nickname;
    
    private String firstname;
    
    private String lastname;
    
    private String phone;
    
    private Role role;
    
    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime dateCreated = LocalDateTime.now();

    public User() {
    	super();
    }

	public User(String email, String password, String changePasswordToken, Status status, String nickname,
			String firstname, String lastname, String phone, Role role, Company company, LocalDateTime dateCreated) {
		super();
		this.email = email;
		this.password = password;
		this.changePasswordToken = changePasswordToken;
		this.status = status;
		this.nickname = nickname;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.role = role;
		this.company = company;
		this.dateCreated = dateCreated;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getChangePasswordToken() {
		return changePasswordToken;
	}

	public void setChangePasswordToken(String changePasswordToken) {
		this.changePasswordToken = changePasswordToken;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", changePasswordToken=" + changePasswordToken + ", status=" + status
				+ ", nickname=" + nickname + ", firstname=" + firstname + ", lastname=" + lastname + ", phone=" + phone
				+ ", role=" + role + ", company=" + company + ", dateCreated=" + dateCreated + "]";
	}

    
}
