package com.github.jlabeaga.peb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.model.QUser;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.repository.UserRepository;
import com.querydsl.core.types.Predicate;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

//	// JPA repository interface example
//	public List<User> findAll() {
//		return userRepository.findAll();
//	}
	
	// QueryDSL example
	public List<User> findAll() {
		QUser user = QUser.user;
		Predicate all = user.nickname.like("%");
		log.debug("Executing querydsl predicate: " + all);
		return userRepository.findAll(all);
	}
	
	public User findOne(Long id) {
		return userRepository.findOne(id);
	}
	
	public void delete(Long id) {
		userRepository.delete(id);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public List<User> findActiveUsersOfCompany(Long companyId) {
		log.debug("findActiveUsersOfCompany for companyId="+companyId);
		return userRepository.findActiveUsersOfCompany(companyId);
	}
	
}
