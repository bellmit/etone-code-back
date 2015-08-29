package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.sun.mail.imap.Utility.Condition;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class FlushLevelAreaAnalyseDAO extends HibernateDao {

	public Page<CommonSport> query(final Page page,Long nmStatFlushLayeringId,String time_search,int timeLevel_search,int areaType) {
		String sql = this.buildSql(nmStatFlushLayeringId,time_search,timeLevel_search,areaType, page);
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
	public List<CommonSport> listData(Long nmStatFlushLayeringId,String time_search,int timeLevel_search,int areaType) {
		String sql = this.buildSql(nmStatFlushLayeringId,time_search,timeLevel_search,areaType, null);
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
			
			commonSport.setVcAreaName(bean[0]+"");
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
	public String buildSql(Long nmStatFlushLayeringId,String time_search,int timeLevel_search,int areaType,Page page) {
		Date date = CommonUtils.getDate(time_search);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int day = CommonUtils.getDay(date);
		String sql = "";
		String field = "";
		String connectTable="";
		String sortSql = "";
		String sortType = " desc ";
		 String sortColumn = "nmUsers";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (areaType) {
		case 1:
			field = " vcName as vcAreaName,sum(nmUsers) as nmUsers,sum(nmFlush) as nmFlush,convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush ";
			connectTable = " left join dtbBsc on intBscId=nmAreaId ";
			break;

		case 2:
			field = " vcName as vcAreaName,sum(nmUsers) as nmUsers,sum(nmFlush) as nmFlush,convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush ";
			connectTable = " left join dtbGsn on intSgsnId=nmAreaId ";
			break;
		case 3:
			field = " vcName as vcAreaName,sum(nmUsers) as nmUsers,sum(nmFlush) as nmFlush,convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush ";
			connectTable = " left join dtbStreet on intStreetId=nmAreaId ";
			break;
		case 4:
			field = " vcSaleAreaName as vcAreaName,sum(nmUsers) as nmUsers,sum(nmFlush) as nmFlush,convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush ";
			connectTable = " left join dtbSaleArea on intSaleAreaId=nmAreaId ";
			break;
		case 5:
			field = " vcBranchName as vcAreaName,sum(nmUsers) as nmUsers,sum(nmFlush) as nmFlush,convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush ";
			connectTable = " left join dtbSubsidiaryCompany on intBranchId =nmAreaId ";
			break;
		}
		String condition =" where intRaitype=1 "
			+" and intYear="+year
			+" and intMonth="+month
			+" and intDay="+day
			+" and nmStatFlushLayeringId="+nmStatFlushLayeringId
			+" and nmAreaTypeId="+areaType
			+" group by vcAreaName,nmStatFlushLayeringId,intYear,intMonth,intDay,intRaitype,nmAreaTypeId";
		sql = "select " +field+" from ftbStatSpaceFlushLayering " +connectTable+condition;
	
			sortSql = " order by " + sortColumn + " " + sortType;
			sql = sql + sortSql;
		return sql;
	}

}
