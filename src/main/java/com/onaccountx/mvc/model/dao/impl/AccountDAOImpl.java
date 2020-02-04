/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.onaccountx.mvc.model.dao.AccountDAO;
import com.onaccountx.mvc.model.entity.Account;
import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [帳號 DAO 實作] 2019-12-19 08:32
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Component
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void create(Account account) throws Exception {
		String sql = "INSERT INTO accounts(id, account, password, status, error_times, time_build, time_last, time_modify, m_id) " +
				"Values(default, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] data = { account.getAccount(),
				account.getPassword(),
				account.getStatus(),
				account.getErrorTime(),
				new Date(), new Date(), new Date(),
				account.getMemberId()};
		jdbcTemplate.update(sql, data);
	}

	@Override
	public List<Account> query() throws Exception {
		String sql = "SELECT * FROM accounts";
		return jdbcTemplate.query(sql, new AccountRowMapper());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Account find(Class<Account> clazz, Long id) throws Exception {
		String sql = "SELECT * FROM accounts WHERE id=?";
		return (Account) jdbcTemplate.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Member.class));
	}

	@Override
	public void update(Long id, Account account) throws Exception {
		Account acc = find(Account.class, id);
		String sql = "UPDATE accounts SET id, account=?, password=?, status=?, error_times=?, time_last=?, time_modify=?, m_id=? WHERE id=?";
		Object[] args = {
				account.getAccount().equals(null) ? acc.getAccount() : account.getAccount(),
				account.getPassword().equals(null) ? acc.getPassword() : account.getPassword(),
				account.getStatus() == 0 ? acc.getStatus() : account.getStatus(),
				account.getErrorTime() == 0 ? acc.getErrorTime() : account.getErrorTime(),
				account.getTimeLast().equals(null) ? acc.getTimeLast() : account.getTimeLast(),
				new Date(),
				account.getMemberId().equals(null) ? acc.getMemberId() : account.getMemberId(),
				id };
		jdbcTemplate.update(sql, args);
	}

	@Override
	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM accounts WHERE id=" + id;
		jdbcTemplate.execute(sql);
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private class AccountRowMapper implements RowMapper<Account> {
		@Override
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			Account account = new Account(
					rs.getString("account"),
					rs.getString("password"),
					rs.getInt("status"),
					rs.getInt("errorTimes"),
					rs.getDate("time_build"),
					rs.getDate("time_last"),
					rs.getDate("time_modify"),
					rs.getLong("memberId"));
			account.setId(rs.getLong("id"));
			return account;
		}
	}

}
