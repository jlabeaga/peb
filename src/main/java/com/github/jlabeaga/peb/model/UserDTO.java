package com.github.jlabeaga.peb.model;

import lombok.Data;

//@Data
public class UserDTO {

	public static final long serialVersionUID = 1L;

	private Long id;
	
	private String email; // username = email
	
	private String status;

	private String nickname;
    
	private String phone;
    
	private String role;
    
	private String companyName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", email=" + email + ", status=" + status + ", nickname=" + nickname + ", phone="
				+ phone + ", role=" + role + ", companyName=" + companyName + "]";
	}

	
	
}
