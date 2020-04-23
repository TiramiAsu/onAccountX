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

import com.onaccountx.mvc.model.dao.ReportJournalDAO;
import com.onaccountx.mvc.model.entity.ReportJournal;
import com.onaccountx.mvc.service.ReportJournalService;
import com.onaccountx.restful.ResponseREST;

/**
 * <pre>
 * [實作 ReportJournalService] 2020-04-23 16:19
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class ReportJournalServiceImpl implements ReportJournalService {

	@Autowired
	private ReportJournalDAO reportJournalDAO;

	@Override
	public boolean create(ReportJournal bean) {
		return false;
	}

	@Override
	@Transactional
	public List<ReportJournal> query() {
		List<ReportJournal> reportJournal = null;
		try {
			reportJournal = reportJournalDAO.findAll().stream()
					.sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (reportJournal == null) {
				throw new Exception(">>> Report Journal Query Failed <<<");
			}
		} catch (Exception e) {
			reportJournal = null;
			e.printStackTrace();
		}
		return reportJournal;
	}

	@Override
	public ReportJournal find(Long id) {
		return null;
	}

	@Override
	public boolean update(ReportJournal bean) {
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
