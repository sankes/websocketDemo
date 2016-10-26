package com.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class IMDao {
@Autowired
private JdbcTemplate jdbcTemplate;

public void test(){
	this.jdbcTemplate.queryForList("select * from system_user");
}
}
