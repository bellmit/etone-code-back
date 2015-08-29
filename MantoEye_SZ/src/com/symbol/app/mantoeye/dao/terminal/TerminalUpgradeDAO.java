package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class TerminalUpgradeDAO extends HibernateQueryDAO {

	public Page<CommonSport> query(final Page page, int area, String sTime,
			String eTime, int changUsers_search, int changUsers_end,
			int timeLevel) {
		String sql = this.buildSql(area, sTime, eTime, changUsers_search,
				changUsers_end, timeLevel, page);
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
		newPage.setResult(buildBeanList(list, area, timeLevel));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(int area, String sTime, String eTime,
			int changUsers_search, int changUsers_end, int timeLevel) {
		String sql = this.buildSql(area, sTime, eTime, changUsers_search,
				changUsers_end, timeLevel, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, area, timeLevel);
	}

	public List<CommonSport> buildBeanList(List list, int area, int timeLevel) {
		List<CommonSport> List = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			List = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				commonSport.setNmTerminalChangeId(Common.StringToLong(bean[0]+""));
				commonSport.setNmUsers(Common.StringToLong(bean[1] + ""));
			//	long OTerminalId = Common.StringToLong(bean[2] + "");
			//	commonSport.setOTreminal(findTerminalById(OTerminalId));
			//	long terminalId = Common.StringToLong(bean[3] + "");
			//	commonSport.setNTerminal(findTerminalById(terminalId));
				String oTerminalName=("null").equals(bean[2]+"")?"未知":bean[2]+"";
				String nTerminalName=("null").equals(bean[3]+"")?"未知":bean[3]+"";
				String oTerminalBrand="";
				String nTerminalBrand="";
				commonSport.setDataType(Common.StringToInt(bean[4]+""));
				
				if (area == 0)
					commonSport.setArea("全网");
				else if (timeLevel == 2)
					commonSport.setArea(bean[11]+"");
				else if (timeLevel != 2)
					commonSport.setArea(bean[10]+"");

				
				commonSport.setIntYear(Common.StringToInt(bean[5] + ""));
				switch (timeLevel) {
				case 2:
					commonSport.setIntMonth(Common.StringToInt(bean[6] + ""));// 月
					commonSport.setIntDay(Common.StringToInt(bean[7] + ""));// 日
					oTerminalBrand=("null").equals(bean[9]+"")?"未知":bean[9]+"";
					nTerminalBrand=("null").equals(bean[10]+"")?"未知":bean[10]+"";
					break;
				case 3:

					commonSport.setIntWeek(Common.StringToInt(bean[6] + ""));// 周
					oTerminalBrand=("null").equals(bean[8]+"")?"未知":bean[8]+"";
					nTerminalBrand=("null").equals(bean[9]+"")?"未知":bean[9]+"";
					break;
				case 4:
					commonSport.setIntMonth(Common.StringToInt(bean[6] + ""));// 月
					oTerminalBrand=("null").equals(bean[8]+"")?"未知":bean[8]+"";
					nTerminalBrand=("null").equals(bean[9]+"")?"未知":bean[9]+"";
					break;
				}
				if(oTerminalBrand.equals("未知")&&oTerminalName.equals("未知"))
					commonSport.setOTreminal("未知");
				else 
				commonSport.setOTreminal(oTerminalBrand+"/"+oTerminalName);
				if(nTerminalBrand.equals("未知")&&nTerminalName.equals("未知"))
					commonSport.setNTerminal("未知");
				else
				commonSport.setNTerminal(nTerminalBrand+"/"+nTerminalName);
				commonSport.setSpanDate(timeLevel);
				List.add(commonSport);
			}
		}
		return List;
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

	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String buildSql(int area, String sTime, String eTime,
			int changUsers_search, int changUsers_end, int timeLevel, Page page) {
		Date d = CommonUtils.getDate(sTime) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int day=CommonUtils.getDay(d);
		int hour=CommonUtils.getHour(d);
		int week=CommonUtils.getWeek(d);
		
		String sql = "";
		String sortSql = "";
		String sortType = " asc ";
		String sortColumn = " intUserNum";
		String sqlCondition = "";
		String sqlFilt = "";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn =" " + page.getOrderBy() + " ";
		
		}

		switch (timeLevel) {

		case 2:
//			sql = "select nmTerminalChangeId, intUserNum, nmOldTerminalId,nmNewTerminalId,intDateType,intYear,intMonth,intDay,intAreaId";
//			sqlCondition = " from ftbStatTerminalChange where 1=1  "
sql="select a.nmTerminalChangeId, a.intUserNum,( b.vcTerminalName) as oTerminalName,(c.vcTerminalName)as nTerminalName ,a.intDateType,a.intYear,a.intMonth,a.intDay,a.intAreaId,(b.vcTerminalBrand)as oTerminalBrand,(c.vcTerminalBrand ) as nTerminalBrand ";
sqlCondition="from ftbStatTerminalChange as a left join dtbTerminal as b on a.nmOldTerminalId=b.nmTerminalId left join   dtbTerminal as c on a.nmNewTerminalId=c.nmTerminalId  ";
	sqlFilt=" where  intYear="+year+" and intMonth="+month+" and intDay="+day
					+" and intAreaType="+area+" and intDateType=1";

			break;
		case 3:
			Date sDate = CommonUtils.getDate(sTime);
			int sYear = CommonUtils.getYear(sDate);
			int sMonth = CommonUtils.getMonth(sDate);
			int sWeek = CommonUtils.getWeek(sDate);


			if (sMonth == 12 && sWeek == 1) {
				sYear = sYear + 1;
			}

//			sql = "select nmTerminalChangeId, intUserNum, nmOldTerminalId,nmNewTerminalId,intDateType,intYear,intWeek,intAreaId";
//			sqlCondition = " from ftbStatTerminalChange  " + " where 1=1 ";
			
			sql="select a.nmTerminalChangeId, a.intUserNum,( b.vcTerminalName) as oTerminalName,(c.vcTerminalName)as nTerminalName ,a.intDateType,a.intYear,a.intWeek,a.intAreaId,(b.vcTerminalBrand)as oTerminalBrand,(c.vcTerminalBrand ) as nTerminalBrand ";
			sqlCondition="from ftbStatTerminalChange as a left join dtbTerminal as b on a.nmOldTerminalId=b.nmTerminalId left join   dtbTerminal as c on a.nmNewTerminalId=c.nmTerminalId  ";
			
			sqlFilt =  " where   intYear = " + sYear	+ " and intWeek =" + sWeek+" and intAreaType="+area+" and intDateType=2";
			
			
			
			break;
		case 4:
//			sql = "select nmTerminalChangeId, intUserNum, nmOldTerminalId,nmNewTerminalId,intDateType,intYear,intMonth,intAreaId";
//			sqlCondition = " from ftbStatTerminalChange  " + " where 1=1 "

			sql="select a.nmTerminalChangeId, a.intUserNum,( b.vcTerminalName) as oTerminalName,(c.vcTerminalName)as nTerminalName ,a.intDateType,a.intYear,a.intMonth,a.intAreaId,(b.vcTerminalBrand)as oTerminalBrand,(c.vcTerminalBrand ) as nTerminalBrand ";
			sqlCondition="from ftbStatTerminalChange as a left join dtbTerminal as b on a.nmOldTerminalId=b.nmTerminalId left join   dtbTerminal as c on a.nmNewTerminalId=c.nmTerminalId  ";
			
			
				sqlFilt=" where  intYear="+year+" and intMonth="+month
					+" and intAreaType="+area+" and intDateType=3";
			break;
		}
		String tableColumns="";
		if (area!=0) {
			String areas=findArea(area);
			String[] areaS=areas.split("/");
			 tableColumns=areaS[0];
			String table=areaS[1];
			String tableCondition=areaS[2];
			sql=sql+","+tableColumns+" ";
			sqlCondition=sqlCondition+" left join "+table+" as d on a.intAreaId=d."+tableCondition;
		}
		
		sql = sql + sqlCondition +sqlFilt;
		if ("area".equals(sortColumn.trim()))
			sortColumn=tableColumns;
		if(area==0&&"area".equals(sortColumn.trim()))
			sortColumn=" intUserNum ";
		sortSql =  " order by  " + sortColumn + sortType;
	
				sql = sql + sortSql;
	
		return sql;
	}
	
	private String findArea(int area){
		String areas="";
		switch (area) {
		case 1:
			areas="vcName/dtbBsc/intBscId";
			break;
		case 5:
			areas="vcName/dtbGsn/intSgsnId";
			break;
		case 2:
			areas="vcName/dtbStreet/intStreetId";
			break;
		case 4:
			areas="vcSaleAreaName/dtbSaleArea/intSaleAreaId";
				
			break;
		case 3:
			areas="vcBranchName/dtbSubsidiaryCompany/intBranchId";
			break;
		}
		return areas;
	}



	private String findArea(int area, String id) {
		if (id == null || "".equals(id))
			return "未知";

		String sql = "";
		switch (area) {
		case 1:
			sql = "select vcName from dtbBsc where intBscId=" + id;
			break;
		case 5:
			sql = "select vcName from dtbGsn where intSgsnId=" + id;
			break;
		case 2:
			sql = "select vcName from dtbStreet where intStreetId=" + id;
			break;
		case 4:
			sql = "select vcSaleAreaName from dtbSaleArea where intSaleAreaId="
					+ id;
			break;
		case 3:
			sql = "select vcBranchName from dtbSubsidiaryCompany where intBranchId="
					+ id;
			break;
		}

		List list = this.getSession().createSQLQuery(sql).list();
		if (list == null || list.isEmpty()) {
			return "未知";
		} else {
			return list.get(0).toString();
		}
	}

}
