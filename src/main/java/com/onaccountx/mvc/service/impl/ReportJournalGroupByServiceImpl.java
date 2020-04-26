/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onaccountx.mvc.model.dao.ReportJournalGroupByDAO;
import com.onaccountx.mvc.model.entity.ReportJournalGroupBy;
import com.onaccountx.mvc.service.ReportJournalGroupByService;
import com.onaccountx.restful.ResponseREST;

/**
 * <pre>
 * [實作 ReportJournalGroupByService] 2020-04-24 17:05
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class ReportJournalGroupByServiceImpl implements ReportJournalGroupByService {

	@Autowired
	private ReportJournalGroupByDAO reportJournalNoCashDAO;

	@Override
	public boolean create(ReportJournalGroupBy bean) {
		return false;
	}

	@Override
	@Transactional
	public List<ReportJournalGroupBy> query() {
		List<ReportJournalGroupBy> reportJournalNoCash = null;
		try {
			reportJournalNoCash = reportJournalNoCashDAO.findAll().stream()
					.sorted((o1, o2) -> o1.getCode().compareTo(o2.getCode()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (reportJournalNoCash == null) {
				throw new Exception(">>> Report JournalNoCash Query Failed <<<");
			}
		} catch (Exception e) {
			reportJournalNoCash = null;
			e.printStackTrace();
		}
		return reportJournalNoCash;
	}

	@Override
	public ReportJournalGroupBy find(Long id) {
		return null;
	}

	@Override
	public boolean update(ReportJournalGroupBy bean) {
		return false;
	}

	@Override
	public boolean delete(Long id) {
		return false;
	}

	@Override
	public ResponseREST queryREST(Object json) {
		return null;
	}

}
