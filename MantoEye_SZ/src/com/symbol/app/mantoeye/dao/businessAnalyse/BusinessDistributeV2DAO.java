package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.TopFlushBean;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class BusinessDistributeV2DAO extends HibernateDao {
	Map<Integer, String> mapBussType = new HashMap<Integer, String>();
	Map<Integer, String> mapBuss = new HashMap<Integer, String>();
	Map<Integer, String> mapContext = new HashMap<Integer, String>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public void initMap() {
		mapBussType.clear();
		mapBuss.clear();
		mapContext.clear();
		mapBussType.put(4, "vStatMonthGprsBussType_V2");
		mapBussType.put(3, "vStatWeekGprsBussType_V2");
		mapBussType.put(2, "vStatDayGprsBussType_V2");
		mapBussType.put(1, "ftbStatHourGprsBussType_V2");
		mapBuss.put(4, "vStatMonthGprsBuss_V2");
		mapBuss.put(3, "vStatWeekGprsBuss_V2");
		mapBuss.put(2, "vStatDayGprsBuss_V2");
		mapBuss.put(1, "ftbStatHourGprsBuss_V2");
		mapContext.put(1, "ftbStatHourGprsBussConTent_V2");
		mapContext.put(2, "vStatDayGprsBussConTent_V2");
		mapContext.put(3, "vStatWeekGprsBussConTent_V2");
		mapContext.put(4, "vStatMonthGprsBussConTent_V2");
	}

	// 查询该时间的全网总流量和总用户数
	@SuppressWarnings("rawtypes")
	private Double[] findSumTotalData(int timeLevel, String date, int dataType) {
		String totalSql = MantoEyeUtils.getTotalFlushAndImsiSql(dataType,
				timeLevel, date);
		Double[] dbs = new Double[] { -1.0, -1.0 };
		List l = this.getSession().createSQLQuery(totalSql).list();
		if (l != null && l.size() > 0) {
			Object[] objs = (Object[]) l.get(0);
			dbs[0] = Common.StringTodouble(objs[0] + "");
			dbs[1] = Common.StringTodouble(objs[1] + "");
		}
		dbs[0] = dbs[0] == 0 ? -1.0 : dbs[0];
		dbs[1] = dbs[1] == 0 ? -1.0 : dbs[1];
		return dbs;
	}

	public Page<CommonFlush> queryBussDistributePage(Page<CommonFlush> page,
			int timeLevel, String date, int dataType, String businessType,
			String business) {
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date, dataType);

		StringBuffer sql = new StringBuffer();
		String dateStr = this.changeDate(date, timeLevel);
		String sumFlushSql = "select sum(nmFlush) from ";
		if (businessType.equals("business")) {
			sql.append("select vcDimensName,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmFlush/nmUsers as nmAveFlush,nmDimensId,nmLocalFlush,nmLocalUsers from ");
			sql.append(mapContext.get(timeLevel));
			sql.append(" where 1=1 ");
			sumFlushSql = sumFlushSql + mapContext.get(timeLevel);
			sumFlushSql = sumFlushSql + " where 1=1 ";
			if (business != null && business != "") {
				// sumFlushSql = sumFlushSql + "and vcBussinessName in ('"
				// + business.replaceAll(",", "','") + "') ";
				sql.append(" and vcDimensName in ('");
				sql.append(business.replaceAll(",", "','"));
				sql.append("')");
			}
		} else {
			sql.append("select vcParentGroupName,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmFlush/nmUsers as nmAveFlush,nmParentId as nmGroupId,nmLocalFlush,nmLocalUsers from ");
			sql.append(mapBussType.get(timeLevel));
			sql.append(" where 1=1 ");
			sumFlushSql = sumFlushSql + mapBussType.get(timeLevel);
			sumFlushSql = sumFlushSql + " where 1=1 ";
		}

		sql.append(this.getQueryCondition(dataType, timeLevel, dateStr));
		if (page.getOrderBy() != "" && page.getOrder() != "") {

			sql.append(" order by " + page.getOrderBy() + " " + page.getOrder());
		} else {

			sql.append(" order by nmFlush desc ");
		}

		sumFlushSql = sumFlushSql
				+ this.getQueryCondition(dataType, timeLevel, dateStr);
		CommonFlush commonFlush = null;
		List<CommonFlush> list = new ArrayList<CommonFlush>();
		List itSum = this.getSession().createSQLQuery(sumFlushSql).list();
		Double sumFlush = 1d;
		if ((itSum != null) && (!(itSum.isEmpty()))) {
			String strobj = String.valueOf(itSum.get(0));
			if ((strobj != null) && (!("null".equals(strobj)))) {
				sumFlush = Double.valueOf(Double.parseDouble(itSum.get(0)
						.toString()));
			}
		}
		SQLQuery squery = this.getSession().createSQLQuery(sql.toString());

		squery.setFirstResult(page.getFirst());
		squery.setMaxResults(page.getPageSize());
		Iterator it = squery.list().iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			commonFlush = new CommonFlush();
			if (businessType.equals("business")) {// 业务
				commonFlush.setBusiness(obj[0] + "");
				commonFlush.setIntUpFlush(Common.StringToLong(obj[1] + ""));
				commonFlush.setIntDownFlush(Common.StringToLong(obj[2] + ""));
				commonFlush.setTotalFlush(Common.StringToLong(obj[3] + ""));
				commonFlush.setIntImsis(Common.StringToLong(obj[4] + ""));
				commonFlush.setVisit(Common.StringToLong(obj[5] + ""));
				commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
				commonFlush.setNmGroupId(Common.StringToLong(obj[7] + ""));
				commonFlush.setLocalintImsis(Common.StringToLong(obj[9] + ""));
				commonFlush
						.setLocaltotalFlush(Common.StringToLong(obj[8] + ""));
				commonFlush.setFlushRate(CommonUtils.formatPercent(
						Common.StringToLong(obj[3] + ""), sumFlush));
				commonFlush.calculateData();
				commonFlush.calculateFlushRate(dbs[0], dbs[1]);

				// commonFlush.numberFormatData();
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

				} else {
					commonFlush.setFullDate(date);
				}
			} else {// 业务类型
				if (obj[0].toString().equals("其他")) {// 暂时隐藏其他，用户数太大，不能统计
					continue;
				}
				commonFlush.setBusinessType(obj[0] + "");
				commonFlush.setIntUpFlush(Common.StringToLong(obj[1] + ""));
				commonFlush.setIntDownFlush(Common.StringToLong(obj[2] + ""));
				commonFlush.setTotalFlush(Common.StringToLong(obj[3] + ""));
				commonFlush.setIntImsis(Common.StringToLong(obj[4] + ""));
				commonFlush.setVisit(Common.StringToLong(obj[5] + ""));
				commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
				commonFlush.setNmGroupId(Common.StringToLong(obj[7] + ""));

				commonFlush.setLocalintImsis(Common.StringToLong(obj[9] + ""));
				commonFlush
						.setLocaltotalFlush(Common.StringToLong(obj[8] + ""));
				commonFlush.setFlushRate(CommonUtils.formatPercent(
						Common.StringToLong(obj[3] + ""), sumFlush));
				commonFlush.calculateData();
				commonFlush.calculateFlushRate(dbs[0], dbs[1]);
				// commonFlush.numberFormatData();
				if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

				} else {
					commonFlush.setFullDate(date);
				}
			}
			list.add(commonFlush);
		}
		page.setResult(list);
		page.setVresult(list);
		return page;
	}

	// 查询业务分布情况
	public List<CommonFlush> queryBusinessDistribute(int timeLevel,
			String date, int dataType, String businessType, String business,
			String orderBy, String order) {
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date, dataType);
		StringBuffer sql = new StringBuffer();
		String dateStr = this.changeDate(date, timeLevel);
		String sumFlushSql = "select sum(nmFlush) from ";
		if (businessType.equals("business")) {
			sql.append("select vcDimensName,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmFlush/nmUsers as nmAveFlush,nmDimensId ,nmLocalFlush,nmLocalUsers from ");
			sql.append(mapContext.get(timeLevel));
			sql.append(" where 1=1");
			sumFlushSql = sumFlushSql + mapContext.get(timeLevel);
			sumFlushSql = sumFlushSql + " where 1=1 ";
			if (business != null && business != "") {
				// sumFlushSql = sumFlushSql + "and vcBussinessName in ('"
				// + business.replaceAll(",", "','") + "') ";
				sql.append(" and vcDimensName in ('");
				sql.append(business.replaceAll(",", "','"));
				sql.append("')");
			}
		} else {
			sql.append("select vcParentGroupName,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmFlush/nmUsers as nmAveFlush,nmParentId as nmGroupId,nmLocalFlush,nmLocalUsers from ");
			sql.append(mapBussType.get(timeLevel));
			sql.append(" where 1=1");
			sumFlushSql = sumFlushSql + mapBussType.get(timeLevel);
			sumFlushSql = sumFlushSql + " where 1=1 ";
		}

		sql.append(this.getQueryCondition(dataType, timeLevel, dateStr));
		if (orderBy != "" && order != "") {

			sql.append(" order by " + orderBy + " " + order);
		} else {

			sql.append(" order by nmFlush desc ");
		}

		sumFlushSql = sumFlushSql
				+ this.getQueryCondition(dataType, timeLevel, dateStr);
		CommonFlush commonFlush = null;
		List<CommonFlush> list = new ArrayList<CommonFlush>();
		List itSum = this.getSession().createSQLQuery(sumFlushSql).list();
		Double sumFlush = 1d;
		if ((itSum != null) && (!(itSum.isEmpty()))) {
			String strobj = String.valueOf(itSum.get(0));
			if ((strobj != null) && (!("null".equals(strobj)))) {
				sumFlush = Double.valueOf(Double.parseDouble(itSum.get(0)
						.toString()));
			}
		}
		Iterator it = this.getSession().createSQLQuery(sql.toString()).list()
				.iterator();

		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			commonFlush = new CommonFlush();
			if (businessType.equals("business")) {// 业务
				commonFlush.setBusiness(obj[0] + "");
				commonFlush.setIntUpFlush(Common.StringToLong(obj[1] + ""));
				commonFlush.setIntDownFlush(Common.StringToLong(obj[2] + ""));
				commonFlush.setTotalFlush(Common.StringToLong(obj[3] + ""));
				commonFlush.setIntImsis(Common.StringToLong(obj[4] + ""));
				commonFlush.setVisit(Common.StringToLong(obj[5] + ""));
				commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
				commonFlush.setNmGroupId(Common.StringToLong(obj[7] + ""));
				commonFlush.setLocalintImsis(Common.StringToLong(obj[9] + ""));
				commonFlush
						.setLocaltotalFlush(Common.StringToLong(obj[8] + ""));
				commonFlush.setFlushRate(CommonUtils.formatPercent(
						Common.StringToLong(obj[3] + ""), sumFlush));
				commonFlush.calculateData();
				commonFlush.calculateFlushRate(dbs[0], dbs[1]);
				// commonFlush.numberFormatData();
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

				} else {
					commonFlush.setFullDate(date);
				}
			} else {// 业务类型
				commonFlush.setBusinessType(obj[0] + "");
				commonFlush.setIntUpFlush(Common.StringToLong(obj[1] + ""));
				commonFlush.setIntDownFlush(Common.StringToLong(obj[2] + ""));
				commonFlush.setTotalFlush(Common.StringToLong(obj[3] + ""));
				commonFlush.setIntImsis(Common.StringToLong(obj[4] + ""));
				commonFlush.setVisit(Common.StringToLong(obj[5] + ""));
				commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
				commonFlush.setNmGroupId(Common.StringToLong(obj[7] + ""));
				commonFlush.setLocalintImsis(Common.StringToLong(obj[9] + ""));
				commonFlush
						.setLocaltotalFlush(Common.StringToLong(obj[8] + ""));
				commonFlush.setFlushRate(CommonUtils.formatPercent(
						Common.StringToLong(obj[3] + ""), sumFlush));
				commonFlush.calculateData();
				commonFlush.calculateFlushRate(dbs[0], dbs[1]);
				// commonFlush.numberFormatData();
				if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

				} else {
					commonFlush.setFullDate(date);
				}
			}
			list.add(commonFlush);
		}
		return list;
	}

	public List<CommonFlush> queryBusinessDateByType(int dataType,
			int timeLevel, String date, String businessType, Long nmGroupId) {// 根据业务类型查询具体业务分布
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date, dataType);

		String dateStr = this.changeDate(date, timeLevel);
		String[] str = dateStr.split(";");
		StringBuffer sql = null;
		if (businessType.equals("其他"))
			sql = new StringBuffer(
					"select top 10 vcGroupName,nmFlush,nmUsers,nmVisitCounts,nmAppUpFlush,nmAppDownFlush,nmFlush/nmUsers as nmAveFlush,nmGroupId,nmLocalFlush,nmLocalUsers from ");
		else {
			sql = new StringBuffer(
					"select vcGroupName,nmFlush,nmUsers,nmVisitCounts,nmAppUpFlush,nmAppDownFlush,nmFlush/nmUsers as nmAveFlush,nmGroupId,nmLocalFlush,nmLocalUsers from ");
		}
		sql.append(mapBuss.get(timeLevel));
		sql.append(" where 1=1");
		sql.append(this.getQueryCondition(dataType, timeLevel, dateStr));
		// logger.info("*****************timeLevel*************"+timeLevel);

		sql.append(" and nmParentId=");
		sql.append(nmGroupId);

		sql.append(" order by nmFlush desc");
		CommonFlush commonFlush = null;
		List<CommonFlush> list = new ArrayList<CommonFlush>();

		Iterator it = this.getSession().createSQLQuery(sql.toString()).list()
				.iterator();
		while (it.hasNext()) {
			commonFlush = new CommonFlush();
			Object[] obj = (Object[]) it.next();
			commonFlush.setBusiness(obj[0] + "");
			commonFlush.setTotalFlush(Common.StringToLong(obj[1] + ""));
			commonFlush.setIntImsis(Common.StringToLong(obj[2] + ""));
			commonFlush.setVisit(Common.StringToLong(obj[3] + ""));
			commonFlush.setIntUpFlush(Common.StringToLong(obj[4] + ""));
			commonFlush.setIntDownFlush(Common.StringToLong(obj[5] + ""));
			commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
			commonFlush.setNmGroupId(Common.StringToLong(obj[7] + ""));
			commonFlush.setLocaltotalFlush(Common.StringToLong(obj[8] + ""));
			commonFlush.setLocalintImsis(Common.StringToLong(obj[9] + ""));
			commonFlush.calculateData();
			commonFlush.calculateFlushRate(dbs[0], dbs[1]);
			// commonFlush.numberFormatData();
			if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
				commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

			} else {
				commonFlush.setFullDate(date);
			}
			list.add(commonFlush);
		}
		return list;
	}

	public List<CommonFlush> queryBussContextByTypeExport(int dataType,
			int timeLevel, String date, String businessType, Long nmGroupId) {// 根据业务类型查询具体业务分布
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date, dataType);

		String dateStr = this.changeDate(date, timeLevel);
		String[] str = dateStr.split(";");
		StringBuffer sql = null;
		if (businessType.equals("其他"))
			sql = new StringBuffer(
					"select top 10 vcDimensName,nmFlush,nmUsers,nmVisitCounts,nmAppUpFlush,nmAppDownFlush,nmFlush/nmUsers as nmAveFlush,nmLocalFlush,nmLocalUsers from ");
		else {
			sql = new StringBuffer(
					"select vcDimensName,nmFlush,nmUsers,nmVisitCounts,nmAppUpFlush,nmAppDownFlush,nmFlush/nmUsers as nmAveFlush,nmLocalFlush,nmLocalUsers from ");
		}
		sql.append(mapContext.get(timeLevel));
		sql.append(" where 1=1");
		sql.append(this.getQueryCondition(dataType, timeLevel, dateStr));
		// logger.info("*****************timeLevel*************"+timeLevel);

		sql.append(" and nmGroupId=");
		sql.append(nmGroupId);

		sql.append(" order by nmFlush desc");
		CommonFlush commonFlush = null;
		List<CommonFlush> list = new ArrayList<CommonFlush>();

		Iterator it = this.getSession().createSQLQuery(sql.toString()).list()
				.iterator();
		while (it.hasNext()) {
			commonFlush = new CommonFlush();
			Object[] obj = (Object[]) it.next();
			commonFlush.setBusiness(obj[0] + "");
			commonFlush.setTotalFlush(Common.StringToLong(obj[1] + ""));
			commonFlush.setIntImsis(Common.StringToLong(obj[2] + ""));
			commonFlush.setVisit(Common.StringToLong(obj[3] + ""));
			commonFlush.setIntUpFlush(Common.StringToLong(obj[4] + ""));
			commonFlush.setIntDownFlush(Common.StringToLong(obj[5] + ""));
			commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
			commonFlush.setLocaltotalFlush(Common.StringToLong(obj[7] + ""));
			commonFlush.setLocalintImsis(Common.StringToLong(obj[8] + ""));
			commonFlush.calculateData();
			commonFlush.calculateFlushRate(dbs[0], dbs[1]);
			// commonFlush.numberFormatData();
			if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
				commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

			} else {
				commonFlush.setFullDate(date);
			}
			list.add(commonFlush);
		}
		return list;
	}

	public Page<CommonFlush> queryBusinessDateByTypePage(final Page page,
			int dataType, int timeLevel, String date, String businessType,
			Long nmGroupId) {
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date, dataType);

		String dateStr = this.changeDate(date, timeLevel);
		String[] str = dateStr.split(";");
		StringBuffer sql = null;
		sql = new StringBuffer(
				"select vcGroupName,nmFlush,nmUsers,nmVisitCounts,nmAppUpFlush,nmAppDownFlush,nmFlush/nmUsers as nmAveFlush,nmGroupId,nmLocalFlush,nmLocalUsers from ");
		sql.append(mapBuss.get(timeLevel));
		sql.append(" where 1=1");
		sql.append(this.getQueryCondition(dataType, timeLevel, dateStr));
		sql.append(" and nmParentId=");
		sql.append(nmGroupId);

		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql.append(" order by");
		sql.append(sortColumn);
		sql.append(sortType);
		CommonFlush commonFlush = null;
		List<CommonFlush> list = new ArrayList<CommonFlush>();
		// SQLQuery query = this.getSession().createSQLQuery(sql.toString());

		String sqls = sql.toString().split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		Iterator it = this.getSession().createSQLQuery(sql.toString())
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list().iterator();

		Page newPage = new Page();
		newPage.setTotalCount(total);
		newPage.setPageNo(page.getPageNo());
		newPage.setPageSize(page.getPageSize());

		while (it.hasNext()) {
			commonFlush = new CommonFlush();
			Object[] obj = (Object[]) it.next();
			commonFlush.setBusiness(obj[0] + "");
			commonFlush.setTotalFlush(Common.StringToLong(obj[1] + ""));
			commonFlush.setIntImsis(Common.StringToLong(obj[2] + ""));
			commonFlush.setVisit(Common.StringToLong(obj[3] + ""));
			commonFlush.setIntUpFlush(Common.StringToLong(obj[4] + ""));
			commonFlush.setIntDownFlush(Common.StringToLong(obj[5] + ""));
			commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
			commonFlush.setNmGroupId(Common.StringToLong(obj[7] + ""));
			commonFlush.setLocaltotalFlush(Common.StringToLong(obj[8] + ""));
			commonFlush.setLocalintImsis(Common.StringToLong(obj[9] + ""));
			commonFlush.calculateData();
			commonFlush.calculateFlushRate(dbs[0], dbs[1]);
			// commonFlush.numberFormatData();
			if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
				commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

			} else {
				commonFlush.setFullDate(date);
			}
			list.add(commonFlush);
		}
		newPage.setResult(list);
		return newPage;
	}

	public Page<CommonFlush> queryBussContextByType(final Page page,
			int dataType, int timeLevel, String date, String businessType,
			Long nmGroupId) {
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date, dataType);

		String dateStr = this.changeDate(date, timeLevel);
		String[] str = dateStr.split(";");
		StringBuffer sql = null;
		sql = new StringBuffer(
				"select vcDimensName,nmFlush,nmUsers,nmVisitCounts,nmAppUpFlush,nmAppDownFlush,nmFlush/nmUsers as nmAveFlush,nmLocalFlush,nmLocalUsers from ");
		sql.append(mapContext.get(timeLevel));
		sql.append(" where 1=1");
		sql.append(this.getQueryCondition(dataType, timeLevel, dateStr));
		sql.append(" and nmGroupId=");
		sql.append(nmGroupId);

		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql.append(" order by");
		sql.append(sortColumn);
		sql.append(sortType);
		CommonFlush commonFlush = null;
		List<CommonFlush> list = new ArrayList<CommonFlush>();
		// SQLQuery query = this.getSession().createSQLQuery(sql.toString());

		String sqls = sql.toString().split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		Iterator it = this.getSession().createSQLQuery(sql.toString())
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list().iterator();

		Page newPage = new Page();
		newPage.setTotalCount(total);
		newPage.setPageNo(page.getPageNo());
		newPage.setPageSize(page.getPageSize());

		while (it.hasNext()) {
			commonFlush = new CommonFlush();
			Object[] obj = (Object[]) it.next();
			commonFlush.setBusiness(obj[0] + "");
			commonFlush.setTotalFlush(Common.StringToLong(obj[1] + ""));
			commonFlush.setIntImsis(Common.StringToLong(obj[2] + ""));
			commonFlush.setVisit(Common.StringToLong(obj[3] + ""));
			commonFlush.setIntUpFlush(Common.StringToLong(obj[4] + ""));
			commonFlush.setIntDownFlush(Common.StringToLong(obj[5] + ""));
			commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
			commonFlush.setLocaltotalFlush(Common.StringToLong(obj[7] + ""));
			commonFlush.setLocalintImsis(Common.StringToLong(obj[8] + ""));
			commonFlush.calculateData();
			commonFlush.calculateFlushRate(dbs[0], dbs[1]);
			// commonFlush.numberFormatData();
			if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
				commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

			} else {
				commonFlush.setFullDate(date);
			}
			list.add(commonFlush);
		}
		newPage.setResult(list);
		return newPage;
	}

	public String getFlushRate(long flush, double sumFlush) {
		Double d = flush / sumFlush;
		d = d * 100 * 100 + 0.5;
		Integer ii = Integer.parseInt(d.toString());
		Double ld = ii / 100.0;
		return ld.toString();
	}

	public String changeDate(String date, int timeLevel) {
		Date d = null;
		if (timeLevel == 1) {

		} else if (timeLevel == 2) {
			date = date + " 01:01";
		} else if (timeLevel == 3) {
			date = date + " 01:01";
		} else if (timeLevel == 4) {
			date = date + "-01" + " 01:00";
		}
		StringBuffer sql = new StringBuffer();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		String dateStr = year + ";" + month + ";" + week + ";" + day + ";"
				+ hour;
		return dateStr;

	}

	public String getQueryCondition(int dataType, int timeLevel, String dateStr) {

		String[] str = dateStr.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);
		StringBuffer conditonStr = new StringBuffer();
		conditonStr.append(" and intRaitype=");
		conditonStr.append(dataType);
		if (timeLevel == 1) {
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
			conditonStr.append(" and intDay=");
			conditonStr.append(day);
			conditonStr.append(" and intHour=");
			conditonStr.append(hour);
		} else if (timeLevel == 2) {
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
			conditonStr.append(" and intDay=");
			conditonStr.append(day);
		} else if (timeLevel == 3) {
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			// conditonStr.append(" and intMonth=");
			// conditonStr.append(month);
			conditonStr.append(" and intWeek=");
			conditonStr.append(week);
		} else if (timeLevel == 4) {
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
		}

		return conditonStr.toString();

	}

	/**
	 * 分页查询业务空间分布
	 * 
	 * @param page
	 * @param isTD
	 * @param areaType
	 *            BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5
	 * @param timeLevel
	 *            小时:1 天:2 周:3 月:4
	 * @param year
	 * @param month
	 * @param week
	 * @param day
	 * @param hour
	 * @return
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time, String business) {
		String strDate = this.changeDate(time, timeLevel);
		String[] str = strDate.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);

		String sql = this.buildSql(page, isTD, areaType, timeLevel, year,
				month, week, day, hour, business);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<Object[]> list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setPageNo(page.getPageNo());
		if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
			time = CommonUtils.getDayInnerWeek(time);
		}
		newPage.setResult(buildBeanList(list, time));

		return newPage;
	}

	public List<CommonFlush> listData(final Page page, int isTD, int areaType,
			int timeLevel, String time, String business) {
		String strDate = this.changeDate(time, timeLevel);
		String[] str = strDate.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);
		String sql = this.buildSql(page, isTD, areaType, timeLevel, year,
				month, week, day, hour, business);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
			time = CommonUtils.getDayInnerWeek(time);
		}
		return buildBeanList(list, time);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildBeanList(List list, String time) {
		List<CommonFlush> commonFlushList = null;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setBusiness(objs[0] + "");// 业务
				commonFlush.setBusinessName(objs[1] + "");// 业务名称
				commonFlush.setIntUpFlush(Common.StringToLong(objs[2] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[3] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[4] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[5] + ""));// 用户数
				commonFlush.setVisit(Common.StringToLong(objs[6] + ""));//
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[7] + ""));
				// 访问次数
				commonFlush.setFullDate(time);
				commonFlush.calculateData();
				// commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	/**
	 * 组装SQL语句
	 * 
	 * @param isTD
	 * @param areaType
	 * @param timeLevel
	 * @param year
	 * @param month
	 * @param week
	 * @param day
	 * @param hour
	 * @return
	 */
	public String buildSql(Page page, int isTD, int areaType, int timeLevel,
			int year, int month, int week, int day, int hour, String business) {
		String sql = "";
		String fields = "";
		String table = "";
		// 维度类型
		switch (areaType) {
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BSC:
			fields = " intBscId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourBscBussType_V2"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayBscBussType_V2"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekBscBussType_V2"
									: "vStatMonthBscBussType_V2";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN:
			fields = " intSgsnId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourSgsnBussType_V2"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySgsnBussType_V2"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSgsnBussType_V2"
									: "vStatMonthSgsnBussType_V2";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_STREET:
			fields = " intStreetId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourStreetBussType_V2"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayStreetBussType_V2"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekStreetBussType_V2"
									: "vStatMonthStreetBussType_V2";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA:
			fields = " intSaleAreaId,vcSaleAreaName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourSaleAreaBussType_V2"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySaleAreaBussType_V2"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSaleAreaBussType_V2"
									: "vStatMonthSaleAreaBussType_V2";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH:
			fields = " intBranchId,vcBranchName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourCompanyBussType_V2"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayCompanyBussType_V2"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekCompanyBussType_V2"
									: "vStatMonthCompanyBussType_V2";
			break;
		}
		sql = " select " + fields;

		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay,intHour"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intWeek"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					// + " and intMonth=" + month
					+ " and intWeek=" + week;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + " and vcGroupName = '" + business + "'";
		sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
		return sql;
	}

	// 业务类型业务关联查询
	public List<BussAndBussType> queryBussAndBussType() {
		// String sql =
		// "select b.nmBussinessId,b.nmBussinessTypeId,b.vcBussinessName,t.vcBussinessTypeName from dtbBusinessSite b join dtbBusinessType t on b.nmBussinessTypeId=t.nmBussinessTypeId";
		String sql = "select nmDimensId as nmBussinessId,nmGroupId as nmBussinessTypeId,vcDimensName as vcBussinessName,vcGroupName as vcBussinessTypeName from dtbGroupTree where nmTypeId=1 group by nmDimensId,vcDimensName,nmGroupId,vcGroupName order by nmBussinessTypeId";
		List list = this.getSession().createSQLQuery(sql).list();
		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
		BussAndBussType bt = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bt = new BussAndBussType();
				Object[] obj = (Object[]) list.get(i);
				bt.setBusinessId(obj[0] + "");
				bt.setBusinessName(obj[2] + "");
				bt.setBusinessTypeId(obj[1] + "");
				bt.setBusinessTypeName(obj[3] + "");
				listEntity.add(bt);
			}
		}
		return listEntity;
	}

	// 根据任务ID 来关联业务类型业务查询 用于终端业务分析
	public List<BussAndBussType> queryBussAndBussTypeByTaskId(int taskId) {

		String sql = "select b.nmBussinessId,b.nmBussinessTypeId,b.vcBussinessName,t.vcBussinessTypeName from  ftbTerminalPolicyMapBusiness pb,dtbBusinessSite b,dtbBusinessType t where  t.nmBussinessTypeId=pb.nmBussinessTypeId and pb.nmBussinessId=b.nmBussinessId and pb.nmTerminalPolicyId="
				+ taskId;
		List list = this.getSession().createSQLQuery(sql).list();
		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
		BussAndBussType bt = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bt = new BussAndBussType();
				Object[] obj = (Object[]) list.get(i);
				bt.setBusinessId(obj[0] + "");
				bt.setBusinessName(obj[2] + "");
				bt.setBusinessTypeId(obj[1] + "");
				bt.setBusinessTypeName(obj[3] + "");
				listEntity.add(bt);
			}
		}
		return listEntity;
	}

	// public List<TopFlushBean> queryTop10Busi(Date date, String type) {
	// // 计算占比
	// Double[] dbs = findSumTotalData(2, Common.getDateFormat(date), dataType);
	// int year = CommonUtils.getYear(date);
	// int month = CommonUtils.getMonth(date);
	// int day = CommonUtils.getDay(date);
	// List<TopFlushBean> result = new ArrayList<TopFlushBean>();
	// String sql =
	// "Select top 10 vcBussinessName,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush from vStatDayGprsBuss "
	// + " where intRaitype=1  and intYear = "
	// + year
	// + " and intMonth = " + month + " and intDay= " + day;
	// if ("wap".equals(type)) {
	// sql = sql
	// + " and  nmBussinessId in "
	// + " (select a.nmBussinessId from dtbBusinessSite a,dtbBusinessType b "
	// +
	// " where a.nmBussinessTypeId = b.nmBussinessTypeId and b.vcBussinessTypeName = '十大网站') ";
	// } else if ("cmcc".equals(type)) {
	// sql = sql
	// + " and  nmBussinessId in "
	// + " ( select this_.nmBussinessId"
	// + " from iqwrite.DBA.dtbBusinessSite this_"
	// +
	// " inner join iqwrite.DBA.dtbBusinessCompany dtbbusines1_ on this_.nmCompanyId=dtbbusines1_.nmCompanyId"
	// +
	// " where dtbbusines1_.vcName like '%中国移动%' and this_.vcBussinessName<>'CMWAP网关')";
	// }
	// sql = sql
	// +
	// " and nmBussinessId not in (select nmBussinessId from dtbBusinessSite where vcBussinessName = '其他' )";
	// if ("flush".equals(type)) {
	// sql = sql + " and vcBussinessName<>'CMWAP网关'";
	// }
	// sql = sql + " order by  nmFlush desc ";
	// List list = this.getSession().createSQLQuery(sql).list();
	// if (list != null && list.size() > 0) {
	// for (int i = 0; i < list.size(); i++) {
	// Object[] objs = (Object[]) list.get(i);
	// TopFlushBean tfb = new TopFlushBean();
	// tfb.setTop(i + 1);
	// tfb.setFlush(Common.StringToLong(objs[3] + ""));
	// tfb.setImsis(Common.StringToLong(objs[4] + ""));
	// tfb.setName(objs[0] + "");
	// tfb.calculate();
	// tfb.calculateFlushRate(dbs[0], dbs[1]);
	// result.add(tfb);
	// }
	// }
	// return result;
	// }

	// public TopFlushBean queryTop10Busi2(Date date, String type) {
	// // 计算占比
	// Double[] dbs = findSumTotalData(2, Common.getDateFormat(date));
	// int year = CommonUtils.getYear(date);
	// int month = CommonUtils.getMonth(date);
	// int day = CommonUtils.getDay(date);
	// List<TopFlushBean> result = new ArrayList<TopFlushBean>();
	// String sql =
	// "Select top 1 vcBussinessName,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush from vStatDayGprsBuss "
	// + " where intRaitype=1  and intYear = "
	// + year
	// + " and intMonth = " + month + " and intDay= " + day;
	// if ("wap".equals(type)) {
	// sql = sql
	// + " and  nmBussinessId in "
	// + " (select a.nmBussinessId from dtbBusinessSite a,dtbBusinessType b "
	// +
	// " where a.nmBussinessTypeId = b.nmBussinessTypeId and b.vcBussinessTypeName = '十大网站') ";
	// } else if ("cmcc".equals(type)) {
	// sql = sql
	// + " and  nmBussinessId in "
	// + " (select a.nmBussinessId from dtbBusinessSite a,dtbBusinessType b "
	// +
	// " where a.nmBussinessTypeId = b.nmBussinessTypeId and b.vcBussinessTypeName = '中国移动自有业务') ";
	// }
	// sql = sql
	// +
	// " and nmBussinessId not in (select nmBussinessId from dtbBusinessSite where vcBussinessName = '其他' )";
	// sql = sql + " order by  nmFlush desc ";
	// List list = this.getSession().createSQLQuery(sql).list();
	// if (list != null && list.size() > 0) {
	// for (int i = 0; i < list.size(); i++) {
	// Object[] objs = (Object[]) list.get(i);
	// TopFlushBean tfb = new TopFlushBean();
	// tfb.setTop(i + 1);
	// tfb.setFlush(Common.StringToLong(objs[3] + ""));
	// tfb.setImsis(Common.StringToLong(objs[4] + ""));
	// tfb.setName(objs[0] + "");
	// tfb.calculate();
	// tfb.calculateFlushRate(dbs[0], dbs[1]);
	// result.add(tfb);
	// }
	// }
	// return (null == result || result.isEmpty()) ? new TopFlushBean()
	// : result.get(0);
	// }

	public List<TopFlushBean> queryHourDataByName(String sdate, String edate,
			String busiName) {
		List<TopFlushBean> result = new ArrayList<TopFlushBean>();
		String sql = "";
		sql = "Select vcBussinessName,nmFlush,nmUsers,convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00')) as ddate "
				+ " from vStatHourGprsBuss where intRaitype = 1 and  ddate >= '"
				+ sdate + "' and ddate<='" + edate;

		// if (!"全网".equals(busiName.trim())) {
		sql += "' and vcBussinessName = '" + busiName;
		// }
		sql += "' order by ddate asc ";

		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				TopFlushBean tfb = new TopFlushBean();
				tfb.setName(objs[0] + "");
				tfb.setFlush(Common.StringToLong(objs[1] + ""));
				tfb.setImsis(Common.StringToLong(objs[2] + ""));
				tfb.setFulldate(objs[3] + "");
				tfb.calculate();
				result.add(tfb);
			}
		}
		return result;
	}

	public List<TopFlushBean> querySpaceDataByName(Date date, String busiName,
			String tableName, String spaceName) {
		List<TopFlushBean> result = new ArrayList<TopFlushBean>();
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int day = CommonUtils.getDay(date);

		String sql = "Select nmFlush,nmUsers," + spaceName + " from "
				+ tableName + " where intRaitype = 1 and intYear=" + year
				+ " and intMonth = " + month + " and intDay = " + day;
		// if (!"全网".equals(busiName.trim())) {
		sql += " and vcBussinessName = '" + busiName + "'";
		// }
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				TopFlushBean tfb = new TopFlushBean();
				tfb.setName(objs[0] + "");
				tfb.setFlush(Common.StringToLong(objs[0] + ""));
				tfb.setImsis(Common.StringToLong(objs[1] + ""));
				tfb.setSpaceName(objs[2] + "");
				tfb.calculate();
				result.add(tfb);
			}
		}
		return result;
	}

	public List<TopFlushBean> queryTotalFlush(String startTime, String endTime,
			String type) {
		List<TopFlushBean> result = new ArrayList<TopFlushBean>();
		String sql = null;

		if ("totalSpace".equals(type)) {
			if ("vStatDaySaleAreaGprsSpace".equals(endTime)) {
				sql = " select nmFlush,nmUsers,vcSaleAreaName";
			} else if ("vStatDayCompanyGprsSpace".equals(endTime)) {
				sql = " select nmFlush,nmUsers,vcBranchName";
			} else {
				sql = " select nmFlush,nmUsers,vcName";
			}
			sql += " from " + endTime;
			sql += " where 1=1  ";
			sql += " and convert(datetime,string(intYear,'-',intMonth,'-',intDay  ))='"
					+ startTime + "' ";
			sql += " order by intYear,intMonth,intDay";
		} else if ("totalTrend".equals(type)) {
			sql = " select nmFlush,nmUsers ";
			sql += " ,convert(varchar(19), convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00'))) as ddate";
			sql += " from vStatHourGprsTotalFlush";
			sql += " where 1=1  ";
			sql += " and convert(datetime,string(intYear,'-',intMonth,'-',intDay ,' ',intHour,':00:00'))>='"
					+ startTime + "' ";
			sql += " and convert(datetime,string(intYear,'-',intMonth,'-',intDay ,' ',intHour,':59:59'))<='"
					+ endTime + "'";
			sql += " and intRaitype=1";
			sql += " order by ddate";
		} else {
			sql = " select nmFlush,nmUsers,";
			sql += " convert(varchar(10),convert(datetime,string(intYear,'-',intMonth,'-',intDay))) as ddate";
			sql += " from vStatDayGprsTotalFlush ";
			sql += " where 1=1  and convert(datetime,string(intYear,'-',intMonth,'-',intDay ))>='"
					+ startTime + "'";
			sql += " and convert(datetime,string(intYear,'-',intMonth,'-',intDay ))<='"
					+ endTime + "' and intRaitype=1 order by ddate";
		}

		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {

			if ("totalSpace".equals(type)) {
				for (int i = 0; i < list.size(); i++) {
					Object[] objs = (Object[]) list.get(i);
					TopFlushBean tfb = new TopFlushBean();
					tfb.setName(objs[0] + "");
					tfb.setFlush(Common.StringToLong(objs[0] + ""));
					tfb.setImsis(Common.StringToLong(objs[1] + ""));
					tfb.setSpaceName(objs[2] + "");
					tfb.calculate();
					result.add(tfb);
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					Object[] objs = (Object[]) list.get(i);
					TopFlushBean tfb = new TopFlushBean();
					tfb.setFlush(Common.StringToLong(objs[0] + ""));
					tfb.setImsis(Common.StringToLong(objs[1] + ""));
					tfb.setFulldate(objs[2].toString());
					tfb.calculate();
					result.add(tfb);
				}
			}
		}

		return result;
	}

	public int isExistLeaf(Long nmGroupId) {
		String sql = "select count(*) from tbProductDimensGroup where parent="
				+ nmGroupId;
		Object totalObj = this.getSession().createSQLQuery(sql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		return total;
	}
}
