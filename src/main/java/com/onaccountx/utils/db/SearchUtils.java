/*
 * 
 * 
 * 
 * 
 * 
 * &cOperateeratey;TiramiAsu
 * 
 */
package com.onaccountx.utils.db;

import java.util.Map;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/**
 * <pre>
 * [搜尋] 2020-02-21 10:55
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class SearchUtils {

	// between
	public static void buildEqualRange(Search search, String key, Operate operate) {
		if (operate.getValueStart() != null)
			search.addFilterGreaterOrEqual(key, operate.getValueStart());

		if (operate.getValueEnd() != null)
			search.addFilterLessOrEqual(key, operate.getValueEnd());
	}

	// or
	public static void buildOrCondition(Search search, Object value) {
		if (value == null)
			return;
		search.addFilterOr((Filter[]) value);
	}

	public static Search buildSearchCondition(Search search, Map<String, Object> conditions) throws Exception {

		if (conditions.size() > 0) {
			for (String key : conditions.keySet()) {
				System.out.println(">>>" + key);
				Object obj = conditions.get(key);

				if (obj instanceof Operate) {
					Operate op = (Operate) obj;
					switch (op.getOperateType()) {
					case Operate.TYPE_LIKE:
						search.addFilterILike(key, (String) op.getValue());
						break;
					case Operate.TYPE_NOT_EQUAL:
						search.addFilterNotEqual(key, op.getValue());
						break;
					case Operate.TYPE_GreaterEqual:
						search.addFilterGreaterOrEqual(key, op.getValue());
						break;
					case Operate.TYPE_LessEqual:
						search.addFilterLessOrEqual(key, op.getValue());
						break;
					case Operate.TYPE_EqualRange:
						buildEqualRange(search, key, op);
						break;
					case Operate.TYPE_FILTER:
						search.addFilter((Filter) op.getValue());
						break;
					case Operate.TYPE_NOT_NULL:
						search.addFilterNotEmpty(key);
						break;
					case Operate.TYPE_NULL:
						search.addFilterEmpty(key);
						break;
					case Operate.TYPE_OR:
						buildOrCondition(search, op.getValue());
						break;
					default:
						throw new Exception(">>> Operate Setting Error <<<");
					}
				} else {
					search.addFilterEqual(key, conditions.get(key));
				}
			}
		}
		return search;
	}
}
