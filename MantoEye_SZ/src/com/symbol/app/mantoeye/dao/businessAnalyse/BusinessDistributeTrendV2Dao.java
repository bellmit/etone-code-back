package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
@SuppressWarnings("unchecked")
public class BusinessDistributeTrendV2Dao extends CommonQueryDAO {

	/**
	 * 分页查询数据
	 * 
	 * @param page
	 * @param timeLevel
	 * @param startDate
	 * @param endDate
	 * @param tableName
	 * @param idColumnName
	 * @param nameColumnName
	 * @return
	 */
	public Page<CommonFlush> queryByPage(Page page, int timeLevel,
			String startTime, String endTime, String tableName,
			String bussinessName) {

		String sql = buildSql(timeLevel, startTime, endTime, tableName,
				bussinessName);

		sql += " order by " + buildOrder(page, timeLevel);

		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(page.getFirst());
		query.setMaxResults(page.getPageSize());

		List list = query.list();
		List<CommonFlush> result = buildTrendBeanList(list, timeLevel);
		page.setResult(result);
		page.setVresult(result);

		return page;
	}

	private String buildOrder(Page page, int timeLevel) {
		String sql = "";
		String deforder = "asc";
		String orderBy = "";
		String order = "asc";
		if (page != null) {
			orderBy = page.getOrderBy();
			order = page.getOrder();
		}
		if (Common.judgeString(orderBy) && !orderBy.equals("fullDate")) {
			sql = " " + orderBy + " " + order;
		} else {
			if (Common.judgeString(order) && order.toLowerCase().equals("desc")) {
				deforder = "desc";
			}
			switch (timeLevel) {
			case 1:
				sql += "viewArea.intYear " + deforder + ",viewArea.intMonth "
						+ deforder + ",viewArea.intDay " + deforder
						+ ",viewArea.intHour " + deforder;
				break;
			case 2:
				sql += "viewArea.intYear " + deforder + ",viewArea.intMonth "
						+ deforder + ",viewArea.intDay " + deforder;
				break;
			case 3:
				sql += "viewArea.intYear " + deforder + ",viewArea.intWeek "
						+ deforder;
				break;
			case 4:
				sql += "viewArea.intYear " + deforder + ",viewArea.intMonth "
						+ deforder;
				break;

			}
		}

		return sql;
	}

	public List<CommonFlush> queryAll(int timeLevel, String startTime,
			String endTime, String tableName, String bussinessName) {
		String sql = buildSql(timeLevel, startTime, endTime, tableName,
				bussinessName);

		sql += " order by " + buildOrder(null, timeLevel);

		SQLQuery query = this.getSession().createSQLQuery(sql);

		List list = query.list();
		List<CommonFlush> result = buildTrendBeanList(list, timeLevel);

		return result;
	}

	/**
	 * 构造SQL
	 * 
	 * @param timeLevel
	 * @param startDate
	 * @param endDate
	 * @param tableName
	 * @param idColumnName
	 * @param nameColumnName
	 * @return
	 */
	private String buildSql(int timeLevel, String startTime, String endTime,
			String tableName, String bussinessName) {
		StringBuffer sql = new StringBuffer(200);

		sql.append(" select");
		sql.append(" viewArea.nmAppUpFlush intUpFlush,");
		sql.append(" viewArea.nmAppDownFlush intDownFlush,");
		sql.append(" viewArea.nmFlush totalFlush,");
		sql.append(" viewArea.nmUsers intImsis,");
		if (timeLevel==1) {
			sql.append(" viewArea.nmFlush/viewArea.nmUsers  nmAveFlush,");
		}else{
			sql.append(" viewArea.nmAveFlush nmAveFlush,");
		}
		sql.append(buildSelectColumnDate(timeLevel));
		sql.append(" from ");
		sql.append(tableName);
		sql.append(" viewArea where 1=1");
		sql.append(" and viewArea.vcDimensName='").append(bussinessName)
				.append("'");
		sql.append(" and viewArea.intRaitype = 2");
		sql.append(buildConditionSql(timeLevel, startTime, endTime));

		return sql.toString();
	}

	private String buildBussinessNameCondition(List<String> bussinessNameList) {

		StringBuffer sql = new StringBuffer(150);
		sql.append(" and viewArea.vcDimensName='").append(
				bussinessNameList.get(0)).append("'");
		for (int i = 1; i < bussinessNameList.size(); i++) {
			sql.append(" or viewArea.vcDimensName='").append(
					bussinessNameList.get(i));
			sql.append("'");
		}
		return sql.toString();

	}

	// /**
	// * 根据时间粒度的不同，查询不同的字段值
	// *
	// * @param timeLevel
	// * @param sql
	// * @return
	// */
	// private Query getQuery(int timeLevel, String sql) {
	// SQLQuery query = this.getSession().createSQLQuery(sql).addScalar(
	// "intUpFlush", Hibernate.LONG).addScalar("intDownFlush",
	// Hibernate.LONG).addScalar("totalFlush", Hibernate.LONG)
	// .addScalar("intImsis", Hibernate.LONG).addScalar("intYear",
	// Hibernate.INTEGER);
	// Query q = null;
	// switch (timeLevel) {
	// case 1:
	// q = query.addScalar("intMonth", Hibernate.INTEGER).addScalar(
	// "intDay", Hibernate.INTEGER).addScalar("intHour",
	// Hibernate.INTEGER).setResultTransformer(
	// Transformers.aliasToBean(CommonFlush.class));
	// break;
	// case 2:
	// q = query.addScalar("intMonth", Hibernate.INTEGER).addScalar(
	// "intDay", Hibernate.INTEGER).setResultTransformer(
	// Transformers.aliasToBean(CommonFlush.class));
	// break;
	// case 3:
	// q = query.addScalar("intWeek", Hibernate.INTEGER)
	// .setResultTransformer(
	// Transformers.aliasToBean(CommonFlush.class));
	// break;
	// case 4:
	// q = query.addScalar("intMonth", Hibernate.INTEGER)
	// .setResultTransformer(
	// Transformers.aliasToBean(CommonFlush.class));
	// break;
	//
	// }
	//
	// return q;
	// }

	public int getTotalCount(int timeLevel, String startTime, String endTime,
			String tableName, String bussinessName) {
		String sql = " select count(*) from ( ";
		sql += buildSql(timeLevel, startTime, endTime, tableName, bussinessName);
		sql += " ) as totalCount";

		Object count = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.valueOf(count.toString());
	}

	private String buildConditionSql(int timeLevel, String startTime,
			String endDate) {
		String sql = "";
		if (timeLevel == 3) {
			Date sDate = CommonUtils.getDate(startTime);
			int sYear = CommonUtils.getYear(sDate);
			int sMonth = CommonUtils.getMonth(sDate);
			int sWeek = CommonUtils.getWeek(sDate);

			Date eDate = CommonUtils.getDate(endDate);
			int eYear = CommonUtils.getYear(eDate);
			int eMonth = CommonUtils.getMonth(eDate);
			int eWeek = CommonUtils.getWeek(eDate);

			if (sMonth == 12 && sWeek == 1) {
				sYear = sYear + 1;
			}
			if (eMonth == 12 && eWeek == 1) {
				eYear = eYear + 1;
			}
			if (sYear != eYear) {
				sql = sql + " and  (( viewArea.intYear = " + sYear
						+ " and viewArea.intWeek>=" + sWeek
						+ " and viewArea.intWeek < 100 )"
						+ " or ( viewArea.intYear = " + eYear
						+ " and viewArea.intWeek>0 and viewArea.intWeek <= "
						+ eWeek + " ))  ";
			} else {
				sql = sql + " and  (( viewArea.intYear = " + sYear
						+ " and viewArea.intWeek>=" + sWeek
						+ " and viewArea.intWeek <= " + eWeek + " ))  ";
			}
		} else {
			sql = " and ddate >= "
					+ MantoEyeUtils.formatData(startTime, timeLevel)
					+ " and ddate <= "
					+ MantoEyeUtils.formatData(endDate, timeLevel);
		}
		return sql;
	}

	private String buildSelectColumnDate(int timeLevel) {
		String sql = "";
		switch (timeLevel) {
		case 1:
			sql += "convert(datetime,string(viewArea.intYear,'-',viewArea.intMonth,'-',viewArea.intDay,' ',viewArea.inthour,':00:00')) as ddate";
			break;
		case 2:
			sql += "convert(datetime,string(viewArea.intYear,'-',viewArea.intMonth,'-',viewArea.intDay)) as ddate";
			break;
		case 3:
			sql += "viewArea.intYear,viewArea.intWeek";
			break;
		case 4:
			sql += "convert(datetime,string(viewArea.intYear,'-',viewArea.intMonth)) as ddate";
			break;

		}
		return sql;
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildTrendBeanList(List list, int timeLevel) {
		List<CommonFlush> commonFlushList = new ArrayList<CommonFlush>();
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setIntUpFlush(Common.StringToLong(objs[0] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[1] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[2] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[3] + ""));// 用户数
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[4]+""));
				if (timeLevel == 1) {
					commonFlush.setFullDate((objs[5] + "").substring(0, 16));
				} else if (timeLevel == 2) {
					commonFlush.setFullDate((objs[5] + "").split(" ")[0]);
				} else if (timeLevel == 4) {
					commonFlush.setFullDate((objs[5] + "").substring(0, 7));
				} else {
					commonFlush.setIntYear(Common.StringToInt(objs[5] + ""));
					commonFlush.setIntWeek(Common.StringToInt(objs[6] + ""));
					commonFlush.setSpanDate(timeLevel);
				}

				commonFlush.calculateData();
				//commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}
}
