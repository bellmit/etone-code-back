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
public class FlushLevelDAO extends HibernateDao {

	public Page<CommonSport> query(final Page page,  String time_search,int timeLevel_search) {
		Date date = CommonUtils.getDate(time_search);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(year,month,day,page);
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
	public List<CommonSport> listData( String time_search,int timeLevel_search) {
		Date date = CommonUtils.getDate(time_search);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(year,month,day,null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list,time_search,timeLevel_search);
	}

	
	public List<CommonSport> buildBeanList(List list,String time_search,int timeLevel_search) {
		Date date = CommonUtils.getDate(time_search);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int day = CommonUtils.getDay(date);
		List<CommonSport> flushList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			flushList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
//				if ("-1".equals(bean[0]+"")) {
//					commonSport.setFlushlevel(Common.StringToLong(bean[1]+"")/1024+" - 无限大");
//				}else {
//					commonSport.setFlushlevel(Common.StringToLong(bean[1]+"")/1024+" - "+Common.StringToLong(bean[0]+"")/1024);
//				}
				
				String[] FlushLevels=(bean[0]+"").split("~");
			long	SFlushLevel=Common.StringToLong(FlushLevels[0])/1024;
			String EFlushLevel=FlushLevels[1].equals("-1")?"无限大": (Common.StringToLong(FlushLevels[1])/1024)+"";
			commonSport.setFlushlevel(SFlushLevel+"~"+EFlushLevel);
				commonSport.setIntImsis(Common.StringToLong(bean[1]+""));
				commonSport.setNmStatFlushLayeringId(Common.StringToLong(bean[2]+""));
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
				flushList.add(commonSport);
			}
		}
		return flushList;
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
	public String buildSql(int year,int month,int day, Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " asc ";
		String sortType = " asc ";
		 String sortColumn = " nmStatFlushLayeringId";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
			if (page.getOrderBy().equals("flushlevel")) {
				sortColumn = " vcFlushConfigure ";
			}
		}
//			sql = "select fc.intUpFlush,fc.intDownFlush,sf.nmUsers,sf.nmStatFlushLayeringId"
//				+"  from ftbStatFlushLayering sf left join dtbFlushConfigure fc on fc.nmFlushConfigureId=sf.nmFlushConfigureId "
//				+" where intRaitype=1 "
//				+" and sf.intYear="+year
//				+" and sf.intMonth="+month
//				+" and sf.intDay="+day;
		sql="select vcFlushConfigure,nmUsers,nmStatFlushLayeringId FROM ftbStatFlushLayering where intRaitype=1 and intYear="+year+" and intMonth="+month
		+" and intDay="+day;
			sortSql = sortSql + " order by " + sortColumn + sortType;
			sql = sql + sortSql;
		return sql;
	}

}
