package com.github.jlabeaga.peb.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Customer;
import com.github.jlabeaga.peb.model.Input;
import com.github.jlabeaga.peb.model.Lot;
import com.github.jlabeaga.peb.model.Pallet;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.model.Variety;

@Component
public class SearchCriteria {

	public int year = LocalDateTime.now().getYear();
	
	public Company company;
	
	public User user;
	
	public Input input;
	
	public Lot lot;
	
	public Variety variety;
	
	public Pallet pallet;
	
	public Customer customer;

	public LocalDate dateFrom;
	
	public LocalDate dateTo;

	@Override
	public String toString() {
		return "SearchCriteria [year=" + year + ", company=" + company + ", user=" + user + ", input=" + input
				+ ", variety=" + variety + ", lot=" + lot + ", pallet=" + pallet + ", customer=" + customer + ", dateFrom=" + dateFrom
				+ ", dateTo=" + dateTo + "]";
	}
	
	
}
