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
import org.springframework.stereotype.Repository;

import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [會員 DAO 實作] 2019-12-18 23:13
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void create(Member member) throws Exception {
		String sql = "INSERT INTO members(id, name, email, phone, time_build, time_modify) " +
				"Values(default, ?, ?, ?, ?, ?)";
		Object[] data = { member.getName(), member.getEmail(), member.getPhone(), new Date(), new Date() };
		jdbcTemplate.update(sql, data);
	}

	@Override
	public List<Member> query() throws Exception {
		String sql = "SELECT * FROM members";
		return jdbcTemplate.query(sql, new MemberRowMapper());
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Member find(Class<Member> clazz, Long id) throws Exception {
		String sql = "SELECT * FROM members WHERE id=?";
		return (Member) jdbcTemplate.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(Member.class));
	}

	@Override
	public void update(Long id, Member member) throws Exception {
		Member m = find(Member.class, id);
		String sql = "UPDATE members SET name=?, email=?, phone=?, time_modify=? WHERE id=?";
		Object[] args = {
				member.getName().equals(null) ? m.getName() : member.getName(),
				member.getEmail().equals(null) ? m.getEmail() : member.getEmail(),
				member.getPhone().equals(null) ? m.getPhone() : member.getPhone(),
				new Date(),
				id };
		jdbcTemplate.update(sql, args);
	}

	@Override
	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM members WHERE id=" + id;
		jdbcTemplate.execute(sql);
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private class MemberRowMapper implements RowMapper<Member> {
		@Override
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member member = new Member(
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("phone"),
					rs.getTimestamp("time_modify"),
					rs.getTimestamp("time_build"));
			member.setId(rs.getLong("id"));
			return member;
		}
	}

}
