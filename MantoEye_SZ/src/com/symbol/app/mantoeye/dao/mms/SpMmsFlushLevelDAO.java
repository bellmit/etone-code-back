package com.symbol.app.mantoeye.dao.mms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;

@Repository
public class SpMmsFlushLevelDAO extends CommonQueryDAO {

	/**
	 * 查询所有
	 */
	public List<CommonFlush> listData(int isTD, String time) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, year, month, week, day, hour);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, time);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param time
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
				commonFlush.setBusiness(objs[0].toString());// 业务
				commonFlush.setBusinessName(objs[1].toString());// 业务名称
				commonFlush.setTotalSendFlush(Long
						.parseLong(objs[2].toString()));// 发送量
				commonFlush.setFullDate(time);// 时间
				//commonFlush.formatMmsData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public String buildSql(int isTD, int year, int month, int week, int day,
			int hour) {
		String sql = "select intSmsSizeId ,vcName, nmCounts,intYear,intMonth,intDay"
				+ " from vStatDaySpMmsFlushSort  where 1=1 "
				+ " and intYear="
				+ year + " and intMonth=" + month + " and intDay=" + day;
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + " order by intSmsSizeId asc ";
		return sql;
	}

}
