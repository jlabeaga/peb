package com.github.jlabeaga.peb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.model.UserDTO;
import com.querydsl.core.types.Predicate;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, ListQueryDslPredicateExecutor<User> {
	
	@Query("SELECT u FROM User u WHERE u.company.id = :companyId and u.status = 'ENABLED'")
	public List<User> findActiveUsersOfCompany(@Param("companyId") Long companyId);
	
}
