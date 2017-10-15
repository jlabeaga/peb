package com.github.jlabeaga.peb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.jlabeaga.peb.model.Input;

@Repository
public interface InputRepository extends JpaRepository<Input, Long> {
	
	@Query("SELECT i FROM Input i JOIN FETCH i.company c where c.id = :companyId")
	public List<Input> search(@Param("companyId") Long companyId);

	@Query("SELECT i FROM Input i JOIN FETCH i.company c")
	public List<Input> search();
}
