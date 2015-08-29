package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class TerminalUpgradeBisFlushDAO extends HibernateQueryDAO {

	public Page<CommonSport> query(final Page page, int taskId,int dataType) {
		String sql = this.buildSql(taskId, page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list,dataType));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(int taskId,int dataType) {
		String sql = this.buildSql(taskId, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list,dataType);
	}

	public List<CommonSport> buildBeanList(List list,int dataType) {
		List<CommonSport> CustomerList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			CustomerList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				commonSport.setNTerminal(findTerminalById(Common
						.StringToLong(bean[0] + "")));
				commonSport.setBusiness(FindBisById(Common.StringToLong(bean[1]
						+ "")));
				commonSport.setNmUsers(Common.StringToLong(bean[2]+""));
				commonSport.setNmFlush(Common.StringToLong(bean[3]+""));
				
				commonSport.setIntYear(Common.StringToInt(bean[5] + ""));
				commonSport.setIntMonth(Common.StringToInt(bean[6] + ""));// 月
				commonSport.setIntDay(Common.StringToInt(bean[8] + ""));// 日
				commonSport.setIntWeek(Common.StringToInt(bean[7] + ""));// 周
				
				
				commonSport.setSpanDate(dataType+1);
				if(dataType==0)
					commonSport.setStatdate(commonSport.getStatdate().split(" ")[0]);
				commonSport.calculateData();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String buildSql(int taskId, Page page) {
		String sortSql = "";
		String sortType = " asc ";
		String sortColumn = " nmTerminalChangeId";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		String sql = "select nmTerminalId,nmBussinessId,intUserNum,nmFlush,dtStatTime,intYear,intMonth,intWeek,intDay"
				+ " from ftbStatBussFlushTerminalChange where nmTerminalChangeIdTaskId="
				+ taskId;
		if(sortColumn.trim().equals("statdate"))
			sortSql =" order by intYear,intMonth,intWeek,intDay "+ sortType;
		else
			sortSql =" order by "+sortColumn+sortType;
		sql=sql+sortSql;
		return sql;
	}

	public String findTerminalById(long id) {
		String sql = "select vcTerminalBrand,vcTerminalName from dtbTerminal where nmTerminalId="
				+ id;
		List list = this.getSession().createSQLQuery(sql).list();
		if (list == null || list.isEmpty()) {
			return "未知";
		} else {
			Object[] objects = (Object[]) list.get(0);
			String TerminalBrand = objects[0] == null ? "未知" : objects[0] + "";
			String TerminalName = objects[1] == null ? "未知" : objects[1] + "";
			return TerminalBrand + "/" + TerminalName;
		}
	}

	public String FindBisById(long bisid) {
		String sql = "select vcBussinessName from dtbBusinessSite where nmBussinessId="
				+ bisid;
		List list = this.getSession().createSQLQuery(sql).list();
		if (list == null || list.isEmpty()) {
			return null;
		} else {

			return list.get(0).toString();
		}
	}
}
