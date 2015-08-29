/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.common.HqlFilter.java
 *         Desc: HQL过滤器，用于添加where条件和排序，过滤结果集  
 *				  添加规则使用addFilter方法  
 *				  举例：QUERY_t#id_S_EQ = 0 //最终连接出的HQL是 and t.id = :id id的值是0通过参数传递给Dao  
 *				  格式说明QUERY前缀就说明要添加过滤条件  
 *				 t#id 就是t.id  
 *			     S:String L:Long I:Integer D:Date ST:Short BD:BigDecimal FT:Float 
 *				 A:Object[] C:Collection
 *			     EQ 是操作符   // EQ 相等 // NE 不等 // LT 小于 // GT 大于 // LE 小于等于 
 *               GE 大于等于 // LK 模糊 // RLK 右模糊 // LLK 左模糊 //IN 在...期間
 *               OR 或者 //
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午05:20:02 
 *   LastChange: 2013-11-12 下午05:20:02 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.spending.common.HqlFilter.java
 */
public class HqlFilter {
	private StringBuffer hql = new StringBuffer();
	private String order = "asc";// asc/desc
	private Map<String, Object> params = new HashMap<String, Object>();// 条件参数

	private HttpServletRequest request;// 为了获取request里面传过来的动态参数

	private Map<String, Object> requestParams = new HashMap<String, Object>();// 条件参数

	private String sort;// 排序字段

	/**
	 * 默认构造
	 */
	public HqlFilter() {
	}

	/**
	 * 带参构造
	 * 
	 * @param request
	 */
	public HqlFilter(HttpServletRequest request) {
		this.request = request;
		addFilter(request);
	}

	/**
	 * 添加过滤
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public void addFilter(HttpServletRequest request) {
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = request.getParameter(name);
			addFilter(name, value);
		}
	}

	/**
	 * 添加过滤
	 * 
	 * 举例，name传递：QUERY_t#id_S_EQ
	 * 
	 * 举例，value传递：0
	 * 
	 * @param params
	 */
	public void addFilter(String name, String value) {
		requestParams.put(name, value);
		if (!DataTools.isEmpty(name) && !DataTools.isEmpty(value)) {
			if (name.startsWith("QUERY_")) {// 如果有需要过滤的字段
				String[] filterParams = StringUtils.split(name, "_");
				if (filterParams.length == 4) {
					String columnName = filterParams[1].replaceAll("#", ".");// 要过滤的字段名称
					String columnType = filterParams[2];// 字段类型
					String operator = filterParams[3];// SQL操作符
					String placeholder = UUID.randomUUID().toString().replace("-", "");// 生成一个随机的参数名称

					if (hql.toString().indexOf(" where 1=1") < 0) {
						hql.append(" where 1=1 ");
					}

					hql.append(" and " + columnName + " " + getSqlOperator(operator) + " :param" + placeholder + " ");// 拼HQL

					params.put("param" + placeholder, getObjValue(columnType, operator, value));// 添加参数
				}
			}
		}
	}

	/**
	 * 添加排序方法，默认asc升序
	 * 
	 * @param order
	 */
	public void addOrder(String order) {
		this.order = order;
	}

	/**
	 * 添加排序字段
	 * 
	 * @param sort
	 */
	public void addSort(String sort) {
		this.sort = sort;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:40:51
	 * 
	 */
	public void clearParams() {
		if (!DataTools.isEmpty(this.params))
			this.params.clear();
		if (!DataTools.isEmpty(this.hql))
			this.hql.delete(0, hql.length());
	}

	/**
	 * 将String值转换成Object，用于拼写HQL，替换操作符和值
	 * 
	 * S:String L:Long I:Integer D:Date ST:Short BD:BigDecimal FT:Float A|I:Integer[] A|L:Long[] A|S:String[] C|I:Collection<Integer>
	 * 
	 * @param columnType
	 * @param operator
	 * @param value
	 * @return
	 */
	private Object getObjValue(String columnType, String operator, String value) {
		if (StringUtils.equalsIgnoreCase(columnType, "S")) {
			if (StringUtils.equalsIgnoreCase(operator, "LK")) {
				value = "%%" + value + "%%";
			} else if (StringUtils.equalsIgnoreCase(operator, "RLK")) {
				value = value + "%%";
			} else if (StringUtils.equalsIgnoreCase(operator, "LLK")) {
				value = "%%" + value;
			}
			return value;
		}
		if (StringUtils.equalsIgnoreCase(columnType, "L")) {
			return Long.parseLong(value);
		}
		if (StringUtils.equalsIgnoreCase(columnType, "I")) {
			return Integer.parseInt(value);
		}
		if (StringUtils.equalsIgnoreCase(columnType, "D")) {
			try {
				return DateUtils.parseDate(value, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy/MM/dd" });
			} catch (ParseException e) {
				LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "HqlFilter", "getObjValue()", e.getMessage(), e.getCause(), e.getClass() });
			}
		}
		if (StringUtils.equalsIgnoreCase(columnType, "ST")) {
			return Short.parseShort(value);
		}
		if (StringUtils.equalsIgnoreCase(columnType, "BD")) {
			return BigDecimal.valueOf(Long.parseLong(value));
		}
		if (StringUtils.equalsIgnoreCase(columnType, "FT")) {
			return Float.parseFloat(value);
		}

		// A|S:String[]
		String[] columnTypeA = columnType.split("\\|", -1);
		if (StringUtils.equalsIgnoreCase(columnTypeA[0], "A")) {
			String[] objA = value.split(",", -1);
			int size = objA.length;

			Object[] paramA = new Object[size];
			for (int i = 0; i < size; i++) {
				paramA[i] = getObjValue(columnTypeA[1], operator, objA[i]);
			}
			return paramA;
		}

		// C|I:Collection<Integer>
		if (StringUtils.equalsIgnoreCase(columnTypeA[0], "C")) {
			String[] objA = value.split(",", -1);

			Collection<Object> objC = new ArrayList<Object>();
			for (String obj : objA) {
				objC.add(getObjValue(columnTypeA[1], operator, obj));
			}

			return objC;
		}

		return null;
	}

	/**
	 * 获得过滤字段参数和值
	 * 
	 * @return
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:33:37
	 * 
	 * @return the requestParams
	 */
	public Map<String, Object> getRequestParams() {
		return requestParams;
	}

	/**
	 * 转换SQL操作符
	 * 
	 * @param operator
	 * @return
	 */
	private String getSqlOperator(String operator) {
		if (StringUtils.equalsIgnoreCase(operator, "EQ")) {
			return " = ";
		}
		if (StringUtils.equalsIgnoreCase(operator, "NE")) {
			return " != ";
		}
		if (StringUtils.equalsIgnoreCase(operator, "LT")) {
			return " < ";
		}
		if (StringUtils.equalsIgnoreCase(operator, "GT")) {
			return " > ";
		}
		if (StringUtils.equalsIgnoreCase(operator, "LE")) {
			return " <= ";
		}
		if (StringUtils.equalsIgnoreCase(operator, "GE")) {
			return " >= ";
		}
		if (StringUtils.equalsIgnoreCase(operator, "LK") || StringUtils.equalsIgnoreCase(operator, "RLK") || StringUtils.equalsIgnoreCase(operator, "LLK")) {
			return " like ";
		}

		if (StringUtils.equalsIgnoreCase(operator, "IN")) {
			return " in ";
		}

		return "";
	}

	/**
	 * 获得添加过滤字段后加上排序字段的HQL
	 * 
	 * @return
	 */
	public String getWhereAndOrderHql() {
		if ( /* !StringUtils.isBlank(sort) && */!StringUtils.isBlank(order)) {
			// if (sort.indexOf(".") < 1) {
			// sort = "t." + sort;
			// }
			hql.append(" order by ").append(sort).append(" ").append(order).append(" ");// 添加排序信息
		} else {
			if (request != null) {
				String s = request.getParameter("sort");
				String o = request.getParameter("order");
				if (!StringUtils.isBlank(s)) {
					sort = s;
				}
				if (!StringUtils.isBlank(o)) {
					order = o;
				}
				if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
					// if (sort.indexOf(".") < 1) {
					// sort = "t." + sort;
					// }
					hql.append(" order by ").append(sort).append(" ").append(order).append(" ");// 添加排序信息
				}
			}
		}
		return hql.toString();
	}

	/**
	 * 获得添加过滤字段后的HQL
	 * 
	 * @return
	 */
	public String getWhereHql() {
		return hql.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HqlFilter [hql=" + hql + ", order=" + order + ", params=" + params + ", request=" + request + ", requestParams=" + requestParams + ", sort=" + sort + "]";
	}
}
