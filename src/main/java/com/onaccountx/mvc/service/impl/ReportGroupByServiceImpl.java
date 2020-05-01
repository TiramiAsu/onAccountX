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

import com.onaccountx.mvc.model.dao.ReportGroupByCreditDAO;
import com.onaccountx.mvc.model.dao.ReportGroupByDebitDAO;
import com.onaccountx.mvc.model.entity.ReportGroupByCredit;
import com.onaccountx.mvc.model.entity.ReportGroupByDebit;
import com.onaccountx.mvc.service.ReportGroupByService;

/**
 * <pre>
 * [實作 ReportGroupByService] 2020-04-24 17:05
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class ReportGroupByServiceImpl implements ReportGroupByService {

	@Autowired
	private ReportGroupByDebitDAO reportGroupByDebitDAO;

	@Autowired
	private ReportGroupByCreditDAO reportGroupByCreditDAO;

	@Override
	@Transactional
	public List<ReportGroupByDebit> queryDebit() {
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
	@Transactional
	public List<ReportGroupByCredit> queryCredit() {
		List<ReportGroupByCredit> reportGroupByCredit = null;
		try {
			reportGroupByCredit = reportGroupByCreditDAO.findAll().stream()
					.sorted((o1, o2) -> o1.getCode().compareTo(o2.getCode()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (reportGroupByCredit == null) {
				throw new Exception(">>> Report GroupBy Credit Query Failed <<<");
			}
		} catch (Exception e) {
			reportGroupByCredit = null;
			e.printStackTrace();
		}
		return reportGroupByCredit;
	}
}
