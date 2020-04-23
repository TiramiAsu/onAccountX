/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.restful;

import static com.onaccountx.utils.ResponseUtils.ERROR_DATABASE;
import static com.onaccountx.utils.ResponseUtils.SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onaccountx.generic.GenericRESTBean;
import com.onaccountx.mvc.model.entity.ReportJournal;
import com.onaccountx.mvc.service.ReportJournalService;
import com.onaccountx.utils.SpringUtils;

/**
 * <pre>
 * [Report REST Service] 2020-04-23 16:43
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
@Path("/report")
public class ReportRESTService {

	@Autowired
	ReportJournalService reportJournalService;
	
	private void enableService() {
		reportJournalService = (reportJournalService == null) ? SpringUtils.getBean(ReportJournalService.class) : reportJournalService;
	}

	@GET
	@Path("/journal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryJournalREST() {
		
		// Data
		List<ReportJournal> reportJournalList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		reportJournalList = reportJournalService.query();
		
		// Response
		if (reportJournalList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (ReportJournal rj : reportJournalList) {
				Map<String, Object> restBean = new GenericRESTBean()
						.put("id", rj.getId())
						.put("debit", rj.getDebit())
						.put("increase", rj.getIncrease())
						.put("reduce", rj.getReduce())
						.put("amount", rj.getAmount())
						.put("timeDate", rj.getTimeDate().getTime())
						.build();
				beanList.add(restBean);
			}
			return new ResponseREST(SUCCESS)
					.setData(beanList)
					.build();
		}
	}
}
