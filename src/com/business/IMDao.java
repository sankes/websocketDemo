package com.business;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IMDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean loginCheck(String account, String password) {
		List<Map<String, Object>> list = this.jdbcTemplate
				.queryForList(
						"select count(*) as count from system_user where account=? and password=?",
						new Object[] { account, password });
		if (Integer.parseInt(list.get(0).get("count").toString())==0) {
			return false;
		} else {
			return true;
		}
	}
}
