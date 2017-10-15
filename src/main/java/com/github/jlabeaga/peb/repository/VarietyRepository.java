package com.github.jlabeaga.peb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.jlabeaga.peb.model.Variety;

@Repository
public interface VarietyRepository extends JpaRepository<Variety, Long> {
	public Variety findByCode(String code);
	
}
