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

import com.onaccountx.mvc.model.dao.ReportGroupByDebitDAO;
import com.onaccountx.mvc.model.entity.ReportGroupByDebit;
import com.onaccountx.mvc.service.ReportGroupByDebitService;
import com.onaccountx.restful.ResponseREST;

/**
 * <pre>
 * [實作 ReportGroupByDebitService] 2020-04-24 17:05
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class ReportGroupByDebitServiceImpl implements ReportGroupByDebitService {

	@Autowired
	private ReportGroupByDebitDAO reportGroupByDebitDAO;

	@Override
	public boolean create(ReportGroupByDebit bean) {
		return false;
	}

	@Override
	@Transactional
	public List<ReportGroupByDebit> query() {
		List<ReportGroupByDebit> reportGroupByDebit = null;
		try {
			reportGroupByDebit = reportGroupByDebitDAO.findAll().stream()
					.sorted((o1, o2) -> o1.getCode().compareTo(o2.getCode()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (reportGroupByDebit == null) {
				throw new Exception(">>> Report GroupBy Debit Query Failed <<<");
			}
		} catch (Exception e) {
			reportGroupByDebit = null;
			e.printStackTrace();
		}
		return reportGroupByDebit;
	}

	@Override
	public ReportGroupByDebit find(Long id) {
		return null;
	}

	@Override
	public boolean update(ReportGroupByDebit bean) {
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
