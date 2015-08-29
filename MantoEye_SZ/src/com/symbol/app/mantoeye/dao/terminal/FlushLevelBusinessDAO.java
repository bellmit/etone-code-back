package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class FlushLevelBusinessDAO extends HibernateDao {

	public Page<CommonSport> query(final Page page,Long nmStatFlushLayeringId,String time_search,int timeLevel_search) {
		String sql = this.buildSql(nmStatFlushLayeringId,time_search,timeLevel_search, page);
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
		newPage.setResult(buildBeanList(list,time_search,timeLevel_search));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(Long nmStatFlushLayeringId,String time_search,int timeLevel_search) {
		String sql = this.buildSql(nmStatFlushLayeringId,time_search,timeLevel_search, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, time_search, timeLevel_search);
	}

	
	public List<CommonSport> buildBeanList(List list,String time_search,int timeLevel_search) {
		Date date = CommonUtils.getDate(time_search);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int day = CommonUtils.getDay(date);
		List<CommonSport> cList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			cList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);		
			
			commonSport.setVcBussinessName(("null").equals(bean[0]+"")||(bean[0]+"")==null?"未知":bean[0]+"");
			commonSport.setIntImsis(Common.StringToLong(bean[1]+""));
			commonSport.setTotalFlush(Common.StringToLong(bean[2]+""));
			commonSport.setNmAveFlush(Common.StringTodouble(bean[3] + ""));
			commonSport.setFullDate(time_search);
			if (timeLevel_search == 0) {
				commonSport.setIntYear(year);
				commonSport.setIntMonth(month);
				commonSport.setIntDay(day);
				commonSport.setSpanDate1(2);
			} else {
				commonSport.setIntYear(year);
				commonSport.setIntMonth(month);
				commonSport.setSpanDate1(4);
			}
			commonSport.calculateData1();
			cList.add(commonSport);
			}
		}
		return cList;
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
	public String buildSql(Long nmStatFlushLayeringId,String time_search,int timeLevel_search,Page page) {
		Date date = CommonUtils.getDate(time_search);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int day = CommonUtils.getDay(date);
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		 String sortColumn = "nmFlush";
		 String groupByString="";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select dtbGroupTree.vcDimensName,sum(ftbStatBussFlushLayering.nmUsers) as nmUsers,sum(ftbStatBussFlushLayering.nmFlush) as nmFlush,convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush "
			+" from ftbStatBussFlushLayering "
			+" left join dtbGroupTree on dtbGroupTree.nmDimensId = ftbStatBussFlushLayering.nmBussinessId and dtbGroupTree.nmTypeId=1 "
			+" where ftbStatBussFlushLayering.intRaitype=1 "
			+" and ftbStatBussFlushLayering.intYear="+year
			+" and ftbStatBussFlushLayering.intMonth="+month
			+" and ftbStatBussFlushLayering.intDay="+day
			+" and ftbStatBussFlushLayering.nmStatFlushLayeringId="+nmStatFlushLayeringId
			+" group by dtbGroupTree.vcDimensName,ftbStatBussFlushLayering.nmStatFlushLayeringId,ftbStatBussFlushLayering.intYear,ftbStatBussFlushLayering.intMonth,ftbStatBussFlushLayering.intDay,ftbStatBussFlushLayering.intRaitype";
			sortSql = " order by " + sortColumn + " " + sortType;
			sql = sql + sortSql;
		return sql;
	}


}
