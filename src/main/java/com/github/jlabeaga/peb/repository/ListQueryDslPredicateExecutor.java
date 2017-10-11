package com.github.jlabeaga.peb.repository;

import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

public interface ListQueryDslPredicateExecutor<T> extends QueryDslPredicateExecutor<T> {
	 
	  @Override
	  List<T> findAll(Predicate predicate);
	 
	  @Override
	  List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders);
}