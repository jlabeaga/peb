package com.github.jlabeaga.peb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SearchCriteria {

	private static List<Integer> buildYears() {
		List<Integer> result = new ArrayList<Integer>();
		for( int y = 2015; y <= presentYear; y++ ) {
			result.add(new Integer(y));
		}
		return result;
	}
	
	public static int presentYear = LocalDateTime.now().getYear();
	
	public static List<Integer> years = buildYears();
	
	private Integer year = new Integer(presentYear);
	
	private Company company;
	
	private User user;
	
	private Input input;
	
	private Lot lot;
	
	private Variety variety;
	
	private Pallet pallet;
	
	private Customer customer;

	private LocalDate dateFrom;
	
	private LocalDate dateTo;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public Variety getVariety() {
		return variety;
	}

	public void setVariety(Variety variety) {
		this.variety = variety;
	}

	public Pallet getPallet() {
		return pallet;
	}

	public void setPallet(Pallet pallet) {
		this.pallet = pallet;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		return "SearchCriteria [year=" + year + ", company=" + company + ", user=" + user + ", input=" + input
				+ ", lot=" + lot + ", variety=" + variety + ", pallet=" + pallet + ", customer=" + customer
				+ ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
	}

	
	
}
