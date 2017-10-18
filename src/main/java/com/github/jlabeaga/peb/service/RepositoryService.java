package com.github.jlabeaga.peb.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RepositoryService {

	private static final Logger log = LoggerFactory.getLogger(RepositoryService.class);

	@PersistenceContext
	private EntityManager em;
	
	public void flushAndClear() {
		log.debug("flushing and clearing EntitiyManager");
		em.flush();
		em.clear();
	}
}
