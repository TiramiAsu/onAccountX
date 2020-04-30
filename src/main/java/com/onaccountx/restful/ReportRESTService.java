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
import com.onaccountx.mvc.model.entity.Journal;
import com.onaccountx.mvc.model.entity.ReportGroupByCredit;
import com.onaccountx.mvc.model.entity.ReportGroupByDebit;
import com.onaccountx.mvc.model.entity.Subject;
import com.onaccountx.mvc.service.JournalService;
import com.onaccountx.mvc.service.ReportGroupByService;
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

	public static String _INCOME_CODE = "2-1";
	public static String _EXPENSE_CODE = "2-2";

	@Autowired
	SubjectService subjectService;

	@Autowired
	JournalService journalService;

	@Autowired
	ReportGroupByService reportGroupByService;

	private void enableService() {
		subjectService = (subjectService == null) ? SpringUtils.getBean(SubjectService.class) : subjectService;
		journalService = (journalService == null) ? SpringUtils.getBean(JournalService.class) : journalService;
		reportGroupByService = (reportGroupByService == null) ? SpringUtils.getBean(ReportGroupByService.class) : reportGroupByService;
	}

	@POST
	@Path("/table")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryResultREST(InputStream in) {

		JSONObject jsonObj = JsonUtils.toJsonObj(in);

		// Data
		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}
		if (jsonObj.get("year") == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}
		Map<String, Object> responseData = new HashMap<>();
		Map<String, Map<String, Object>> tableDataDebit = new HashMap<>();
		Map<String, Map<String, Object>> tableDataCredit = new HashMap<>();
		List<Journal> journalList;
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
		journalList = journalService.query().stream()
				.filter(rj -> {
					Calendar c = Calendar.getInstance();
					c.setTime(rj.getTimeDate());
					return c.get(Calendar.YEAR) == Integer.parseInt(filterYear);
				})
				.collect(Collectors.toList());

		// subtotal 1~12 month amount group by Subject
		// get correct year of data from & to
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(journalList.stream()
				.max((d1, d2) -> d1.getTimeDate().getTime() > d2.getTimeDate().getTime() ? 1 : -1)
				.get().getTimeDate());
		c2.setTime(journalList.stream()
				.max((d1, d2) -> d1.getTimeDate().getTime() < d2.getTimeDate().getTime() ? 1 : -1)
				.get().getTimeDate());
		dataYearStart = c1.get(Calendar.YEAR);
		dataYearEnded = c2.get(Calendar.YEAR);
		Map<String, Object> rangeYear = new HashMap<>();
		rangeYear.put("startYear", dataYearStart);
		rangeYear.put("endedYear", dataYearEnded);
		responseData.put("rangeYear", rangeYear);

		// get Subject Map(code & name)
		Map<String, Object> codeName = new HashMap<>();
		for (int i = 1; i < 13; i++) { // 1~12 mouth
			tableDataDebit.put(i + "", new HashMap<>());
			tableDataCredit.put(i + "", new HashMap<>());
			for (Subject subj : subjectList) {
				codeName.put(subj.getCode(), subj.getName());
				if (subj.getCode().length() > 1) {
					if (!subj.getCode().substring(0, 3).equals(_INCOME_CODE)) {
						tableDataDebit.get(i + "").put(subj.getCode(), 0);
					}
					if (!subj.getCode().substring(0, 3).equals(_EXPENSE_CODE)) {
						tableDataCredit.get(i + "").put(subj.getCode(), 0);
					}
				} else {
					tableDataDebit.get(i + "").put(subj.getCode(), 0);
					tableDataCredit.get(i + "").put(subj.getCode(), 0);
				}
			}
		}
		responseData.put("codeName", codeName);
		
		// subtotal amount of Subject(Debit) by month
		journalList.stream()
			.forEach(j -> {
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
						//debit
						if (code.equals(j.getDebit().getCode())) {
							int sum = 0;
							Long ts = j.getTimeDate().getTime();
							if (ts >= ts1 && ts < ts2) { // 分月份
								int oriSubtotal = Integer.parseInt(tableDataDebit.get(i + "").get(code) + "");
								sum = oriSubtotal + j.getAmount();
								tableDataDebit.get(i + "").put(code, sum);
							}
						}
						//credit
						if (code.equals(j.getCredit().getCode())) {
							int sum = 0;
							Long ts = j.getTimeDate().getTime();
							if (ts >= ts1 && ts < ts2) { // 分月份
								int oriSubtotal = Integer.parseInt(tableDataCredit.get(i + "").get(code) + "");
								sum = oriSubtotal + j.getAmount();
								tableDataCredit.get(i + "").put(code, sum);
							}
						}
					}
				}
			});
		responseData.put("tableDataDebit", tableDataDebit);
		responseData.put("tableDataCredit", tableDataCredit);
		// return response
		return new ResponseREST(SUCCESS)
				.setData(responseData)
				.build();
	}

	@GET
	@Path("/journal/groupby/debit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryJournalDebitREST() {
		
		// Data
		List<ReportGroupByDebit> reportJournalGroupByList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		reportJournalGroupByList = reportGroupByService.queryDebit();
		
		// Response
		if (reportJournalGroupByList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (ReportGroupByDebit rjnc : reportJournalGroupByList) {
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

	@GET
	@Path("/journal/groupby/credit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryJournalCreditREST() {
		
		// Data
		List<ReportGroupByCredit> reportGroupByCreditList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		reportGroupByCreditList = reportGroupByService.queryCredit();
		
		// Response
		if (reportGroupByCreditList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (ReportGroupByCredit rjnc : reportGroupByCreditList) {
				Map<String, Object> restBean = new GenericRESTBean()
						.put("id", rjnc.getId())
						.put("code", rjnc.getCode())
						.put("credit", rjnc.getCredit())
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
