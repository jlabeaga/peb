package com.github.jlabeaga.peb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.jlabeaga.peb.model.Lot;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
	
	@Query("SELECT l FROM Lot l WHERE l.input.id = :inputId")
	public List<Lot> findByInput(@Param("inputId") Long inputId);
	
}
