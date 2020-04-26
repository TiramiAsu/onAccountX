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
import static com.onaccountx.utils.ResponseUtils.ERROR_INPUT;
import static com.onaccountx.utils.ResponseUtils.SUCCESS;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onaccountx.generic.GenericRESTBean;
import com.onaccountx.mvc.model.entity.ReportJournal;
import com.onaccountx.mvc.model.entity.ReportJournalGroupBy;
import com.onaccountx.mvc.model.entity.Subject;
import com.onaccountx.mvc.service.ReportJournalGroupByService;
import com.onaccountx.mvc.service.ReportJournalService;
import com.onaccountx.mvc.service.SubjectService;
import com.onaccountx.utils.JsonUtils;
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
	SubjectService subjectService;

	@Autowired
	ReportJournalService reportJournalService;

	@Autowired
	ReportJournalGroupByService reportJournalGroupByService;

	private void enableService() {
		subjectService = (subjectService == null) ? SpringUtils.getBean(SubjectService.class) : subjectService;
		reportJournalService = (reportJournalService == null) ? SpringUtils.getBean(ReportJournalService.class) : reportJournalService;
		reportJournalGroupByService = (reportJournalGroupByService == null) ? SpringUtils.getBean(ReportJournalGroupByService.class) : reportJournalGroupByService;
	}

	@POST
	@Path("/table")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryResultREST(InputStream in) {

		JSONObject jsonObj = JsonUtils.toJsonObj(in);

		// Data
		if (jsonObj == null && jsonObj.get("year") == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}
		Map<String, Object> responseData = new HashMap<>();
		Map<String, Map<String, Object>> tableData = new HashMap<>();
		List<ReportJournal> reportJournalList;
		List<Subject> subjectList;
		String filterYear = jsonObj.get("year") + "";
		int dataYearStart;
		int dataYearEnded;

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		// filter correct year data
		subjectList = subjectService.query();
		reportJournalList = reportJournalService.query().stream()
				.filter(rj -> {
					Calendar c = Calendar.getInstance();
					c.setTime(rj.getTimeDate());
					return c.get(Calendar.YEAR) == Integer.parseInt(filterYear);
				})
				.collect(Collectors.toList());
//		System.out.println(reportJournalList);
//		System.out.println(subjectList);
//		System.out.println();

		// subtotal 1~12 month amount group by Subject
		// get correct year of data from & to
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(reportJournalList.stream()
				.max((d1, d2) -> d1.getTimeDate().getTime() > d2.getTimeDate().getTime() ? 1 : -1)
				.get().getTimeDate());
		c2.setTime(reportJournalList.stream()
				.max((d1, d2) -> d1.getTimeDate().getTime() < d2.getTimeDate().getTime() ? 1 : -1)
				.get().getTimeDate());
		dataYearStart = c1.get(Calendar.YEAR);
		dataYearEnded = c2.get(Calendar.YEAR);
		Map<String, Object> rangeYear = new HashMap<>();
		rangeYear.put("startYear", dataYearStart);
		rangeYear.put("endedYear", dataYearEnded);
		responseData.put("rangeYear", rangeYear);
//		System.out.println(dataYearStart);
//		System.out.println(dataYearEnded);
//		System.out.println();

		// get Subject Map(code & name)
		Map<String, Object> codeName = new HashMap<>();
		for (int i = 1; i < 13; i++) { // 1~12 mouth
			tableData.put(i + "", new HashMap<>());
			for (Subject subj : subjectList) {
				codeName.put(subj.getCode(), subj.getName());
				tableData.get(i + "").put(subj.getCode(), 0);
			}
		}
		responseData.put("codeName", codeName);
		
		// subtotal amount of Subject by month
		reportJournalList.stream()
			.forEach(data -> {
				for (int i = 1; i < 13; i++) { // 1~12 mouth
					Long ts1 = 0L;
					Long ts2 = 0L;
					try {
						ts1 = new SimpleDateFormat("yyyy/MM/dd")
								.parse(dataYearEnded + "/" + i + "/1")
								.getTime(); // "2019/1/1"
						ts2 = new SimpleDateFormat("yyyy/MM/dd")
								.parse(dataYearEnded + "/" + (i + 1) + "/1")
								.getTime(); // "2019/2/1"
					} catch (ParseException e) {
						System.err.println(">>> Error, date build failed.");
						System.err.println(e.getMessage());
					}
					for (Subject subject : subjectList) {
						String code = subject.getCode();
						if (code.equals(data.getCode())) {
							int sum = 0;
							Long ts = data.getTimeDate().getTime();
							if (ts >= ts1 && ts < ts2) { // 分月份
								int oriSubtotal = Integer.parseInt(tableData.get(i + "").get(code) + "");
								sum = oriSubtotal + data.getAmount();
								tableData.get(i + "").put(code, sum);
							}
						}
					}
				}
			});
//		System.out.println(tableData);
		responseData.put("tableData", tableData);
		// return response
		return new ResponseREST(SUCCESS)
				.setData(responseData)
				.build();
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
						.put("code", rj.getCode())
						.put("name", rj.getName())
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

	@GET
	@Path("/journal/groupby")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryJournalNoCashREST() {
		
		// Data
		List<ReportJournalGroupBy> reportJournalNoCashList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		reportJournalNoCashList = reportJournalGroupByService.query();
		
		// Response
		if (reportJournalNoCashList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (ReportJournalGroupBy rjnc : reportJournalNoCashList) {
				Map<String, Object> restBean = new GenericRESTBean()
						.put("id", rjnc.getId())
						.put("code", rjnc.getCode())
						.put("debit", rjnc.getDebit())
						.put("subtotal", rjnc.getSubtotal())
						.build();
				beanList.add(restBean);
			}
			// 整理 data
			return new ResponseREST(SUCCESS)
					.setData(beanList)
					.build();
		}
	}
}
