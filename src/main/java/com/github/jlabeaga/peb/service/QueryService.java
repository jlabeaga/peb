package com.github.jlabeaga.peb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.jlabeaga.peb.model.UserDTO;
import com.github.jlabeaga.peb.model.Variety;
import com.github.jlabeaga.peb.view.UserView;

@Service
public class QueryService {

	private static final Logger log = LoggerFactory.getLogger(QueryService.class);

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<UserDTO> listUsers(){
		String sql = "SELECT "
				+ "user.id as id, "
				+ "user.nickname as nickname, "
				+ "user.email as email, "
				+ "user.phone as phone, "
				+ "user.role as role, "
				+ "user.status as status, "
				+ "company.name as companyName "
				+ "FROM user "
				+ "LEFT JOIN company ON user.company_id = company.id";
		List<UserDTO> result = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(UserDTO.class));
		log.debug("sql="+ sql);
		log.debug("result="+ result);
		return result;
	}

	public List<Variety> listVarieties(){
		String sql = "SELECT id, code, name "
				+ "FROM variety "
				+ "ORDER BY name";
		List<Variety> result = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(Variety.class));
		log.debug("sql="+ sql);
		log.debug("result="+ result);
		return result;
	}
	
	
}
