package com.github.jlabeaga.peb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.jlabeaga.peb.model.Pallet;

@Repository
public interface PalletRepository extends JpaRepository<Pallet, Long> {
	
	@Query("SELECT pallet FROM Part part INNER JOIN part.pallet pallet INNER JOIN part.lot lot WHERE lot.id = :lotId")
	public List<Pallet> findByLot(@Param("lotId") Long lotId);

}
