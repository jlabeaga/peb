package com.github.jlabeaga.peb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.jlabeaga.peb.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
