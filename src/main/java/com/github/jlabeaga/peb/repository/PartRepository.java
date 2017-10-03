package com.github.jlabeaga.peb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.jlabeaga.peb.model.Lot;
import com.github.jlabeaga.peb.model.Part;

@Repository
public interface PartRepository extends JpaRepository<Lot, Long> {
	
	@Query("SELECT p FROM Part p WHERE p.lot.id = :lotId")
	public List<Part> getParts(@Param("lotId") Long lotId);
	
}
