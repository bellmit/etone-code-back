package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class TerminalUpgradeFlushDAO extends HibernateQueryDAO {

	public Page<CommonSport> query(final Page page, int taskId,int dataType) {
		String sql = this.buildSql(taskId, page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql).setFirstResult(page.getFirst())
		.setMaxResults(page.getPageSize()).list();
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
		
				commonSport.setOTreminal(findTerminalById(Common.StringToLong(bean[0]+"")));
				commonSport.setNTerminal(findTerminalById(Common.StringToLong(bean[1]+"")));
				commonSport.setNmOFlush(Common.StringToLong(bean[2].toString()));
				commonSport.setNmFlush(Common.StringToLong(bean[3].toString()));
				commonSport.setIntYear(Common.StringToInt(bean[4] + ""));
				commonSport.setIntMonth(Common.StringToInt(bean[5] + ""));// 月
				commonSport.setIntDay(Common.StringToInt(bean[7] + ""));// 日
				commonSport.setIntWeek(Common.StringToInt(bean[6] + ""));// 周
				commonSport.calculateData();
				commonSport.setSpanDate(dataType+1);
				if(dataType==0)
					commonSport.setStatdate(commonSport.getStatdate().split(" ")[0]);
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
	public String buildSql(int taskId,Page page) {
		String sortSql = "";
		String sortType = " asc ";
		String sortColumn = " nmOldTerminalId";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		String sql ="select nmOldTerminalId ,nmNewTerminalId,nmOldTerminalFlush,nmNewTerminalFlush,intYear,intMonth,intWeek,intDay from ftbStatFlushTerminalChange  " +
				"where nmTerminalChangeIdTaskId="+taskId;
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
			Object[] objects=(Object[]) list.get(0);
			String TerminalBrand=objects[0]==null?"未知":objects[0]+"";
			String TerminalName=objects[1]==null?"未知":objects[1]+"";
			return 	TerminalBrand+"/"+TerminalName;
		
			
		}

	}

}
