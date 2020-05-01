/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.service;

import java.util.List;

import com.onaccountx.mvc.model.entity.ReportGroupByCredit;
import com.onaccountx.mvc.model.entity.ReportGroupByDebit;

/**
 * <pre>
 * [定義 ReportGroupByService] 2020-04-24 16:54
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface ReportGroupByService {
	public List<ReportGroupByDebit> queryDebit();
	public List<ReportGroupByCredit> queryCredit();
}
