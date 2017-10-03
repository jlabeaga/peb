package com.github.jlabeaga.peb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.repository.CompanyRepository;
import com.github.jlabeaga.peb.repository.UserRepository;
import com.github.jlabeaga.peb.view.UserDetailView;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findOne(Long id) {
		return userRepository.findOne(id);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public List<User>  findActiveUsersOfCompany(Long companyId) {
		log.debug("findActiveUsersOfCompany for companyId="+companyId);
		return userRepository.findActiveUsersOfCompany(companyId);
	}
	
}
