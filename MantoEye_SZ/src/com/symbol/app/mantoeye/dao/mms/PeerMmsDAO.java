package com.symbol.app.mantoeye.dao.mms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.mms.PeerMmsBean;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

/**
 * 点对点彩信统计
 * 
 * @author rankin
 * 
 */
@Repository
public class PeerMmsDAO extends CommonQueryDAO {

	/**
	 * 点对点彩信号段分布（月）
	 * 
	 * @return
	 */
	public List<PeerMmsBean> findMonthPeerMmsSeg(int raitype, int year,
			int month) {

		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] cobjs = null;
		Object[] objs = null;
		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from vStatMonthPeerMmsSeg where intRaitype = ? and intYear = ? and intMonth = ? ";
		String sql = "Select nmUpCounts,nmDownCounts,nmTotalCounts,intMsisdnSegId,intMsisdnSeg from vStatMonthPeerMmsSeg where intRaitype = ? and intYear = ? and intMonth = ? order by nmTotalCounts desc";

		SQLQuery tasq = this.getSession().createSQLQuery(total);

		tasq.setInteger(0, raitype);
		tasq.setInteger(1, year);
		tasq.setInteger(2, month);

		List tars = tasq.list();

		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, raitype);
		sq.setInteger(1, year);
		sq.setInteger(2, month);
		List rs = sq.list();
		logger.info("Size:" + rs.size());
		Map<Integer, String> segMap = CommonConstants.getSegMap();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();
				bean.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH
						+ "");
				bean.setYear(year);
				bean.setMonth(month);
				objs = (Object[]) rs.get(i);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));
				bean.setDimensionId(Common.StringToLong(objs[3] + ""));
				bean.setDimensionName((objs[4] + "").equals("0") ? "其他"
						: objs[4] + "");

				bean.calculateData(totalUp, totalDown, totalTotal);
				reault.add(bean);
			}
		}
		return reault;
	}

	/**
	 * 点对点彩信号段分布（周）
	 * 
	 * @return
	 */
	public List<PeerMmsBean> findWeekPeerMmsSeg(int raitype, int year, int week) {
		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] objs = null;
		Object[] cobjs = null;
		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from vStatWeekPeerMmsSeg where intRaitype = ? and intYear = ? and intWeek = ? ";
		String sql = "Select nmUpCounts,nmDownCounts,nmTotalCounts,intMsisdnSegId,intMsisdnSeg from vStatWeekPeerMmsSeg where intRaitype = ? and intYear = ? and intWeek = ? order by nmTotalCounts desc";

		SQLQuery tasq = this.getSession().createSQLQuery(total);

		tasq.setInteger(0, raitype);
		tasq.setInteger(1, year);
		tasq.setInteger(2, week);

		List tars = tasq.list();
		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, raitype);
		sq.setInteger(1, year);
		sq.setInteger(2, week);
		List rs = sq.list();
		logger.info("Size:" + rs.size());
		Map<Integer, String> segMap = CommonConstants.getSegMap();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();
				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK
								+ "");
				bean.setYear(year);
				bean.setWeek(week);
				objs = (Object[]) rs.get(i);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));
				bean.setDimensionId(Common.StringToLong(objs[3] + ""));
				bean.setDimensionName((objs[4] + "").equals("0") ? "其他"
						: objs[4] + "");

				bean.calculateData(totalUp, totalDown, totalTotal);
				reault.add(bean);
			}
		}
		return reault;
	}

	/**
	 * 点对点彩信号段分布（天）
	 * 
	 * @return
	 */
	public List<PeerMmsBean> findDayPeerMmsSeg(int raitype, int year,
			int month, int day) {

		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] cobjs = null;
		Object[] objs = null;
		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from vStatDayPeerMmsSeg where intRaitype = ? and intYear = ? and intMonth = ? and intDay = ? ";
		String sql = "Select nmUpCounts,nmDownCounts,nmTotalCounts,intMsisdnSegId,intMsisdnSeg from vStatDayPeerMmsSeg where intRaitype = ? and intYear = ? and intMonth = ? and intDay = ?  order by nmTotalCounts desc";

		SQLQuery tasq = this.getSession().createSQLQuery(total);
		tasq.setInteger(0, raitype);
		tasq.setInteger(1, year);
		tasq.setInteger(2, month);
		tasq.setInteger(3, day);
		List tars = tasq.list();
		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, raitype);
		sq.setInteger(1, year);
		sq.setInteger(2, month);
		sq.setInteger(3, day);
		List rs = sq.list();
		logger.info("Size:" + rs.size());
		Map<Integer, String> segMap = CommonConstants.getSegMap();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();
				bean.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_DAY + "");
				bean.setYear(year);
				bean.setMonth(month);
				bean.setDay(day);
				objs = (Object[]) rs.get(i);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));
				bean.setDimensionId(Common.StringToLong(objs[3] + ""));
				bean.setDimensionName((objs[4] + "").equals("0") ? "其他"
						: objs[4] + "");

				bean.calculateData(totalUp, totalDown, totalTotal);
				reault.add(bean);
			}
		}
		return reault;
	}

	public Page<PeerMmsBean> findHourPeerMmsPage(Page<PeerMmsBean> page,
			int raitype, String startdate, String enddate) {

		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] cobjs = null;
		Object[] objs = null;// convert(datetime,string(intYear,'-',intMonth,"-",

		String sortSql = "";
		String sortType = "";
		String sortColumn = "";
		if (page != null) {
			sortType = page.getOrder();
			sortColumn = page.getOrderBy();
			if (sortColumn == "" ) {
				sortSql = " order by intYear asc,intMonth asc,intDay asc,intHour asc";
			} else if(sortColumn.equals("hourname")){
				if(sortType.toUpperCase().equals("asc")){
					sortSql = " order by intYear asc,intMonth asc,intDay asc,intHour asc";
				}else{
					sortSql = " order by intYear desc,intMonth desc,intDay desc,intHour desc";
				}			
			}else {
				sortSql = " order by " + sortColumn + "  " + sortType;
			}
		}

		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from vStatHourPeerMms where intRaitype = "
				+ raitype
				+ " and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) >= "
				+ MantoEyeUtils.formatData(startdate, 1)
				+ " and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) <=  "
				+ MantoEyeUtils.formatData(enddate, 1);
		// 不同天的相同时段数量相加统计
		// String sql =
		// "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts),intHour from vStatHourPeerMms where intRaitype = ? and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) >= ? and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) <= ? group by intHour  order by intHour asc";

		// 不同天的相同时段作为不同的记录
		String sql = "Select nmUpCounts, nmDownCounts, nmTotalCounts,intYear,intMonth,intDay,intHour  from vStatHourPeerMms where intRaitype = "
				+ raitype
				+ " and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) >= "
				+ MantoEyeUtils.formatData(startdate, 1)
				+ " and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) <=  "
				+ MantoEyeUtils.formatData(enddate, 1) + sortSql;

		SQLQuery tasq = this.getSession().createSQLQuery(total);
		List tars = tasq.list();

		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setFirstResult(page.getFirst());
		sq.setMaxResults(page.getPageSize());
		List rs = sq.list();
		logger.info("Size:" + rs.size());
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();

				objs = (Object[]) rs.get(i);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));

				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR
								+ "");
				bean.setYear(Common.StringToInteger(objs[3] + ""));
				bean.setMonth(Common.StringToInteger(objs[4] + ""));
				bean.setDay(Common.StringToInteger(objs[5] + ""));
				bean.setHour(Common.StringToInteger(objs[6] + ""));

				bean.calculateData(totalUp, totalDown, totalTotal);

				bean.setDimensionName(bean.getFullDate());
				reault.add(bean);
			}
			page.setResult(reault);
			page.setVresult(reault);
		}
		return page;

	}

	/**
	 * 点对点彩信时段分布
	 * 
	 * @return
	 */
	public List<PeerMmsBean> findHourPeerMms(int raitype, String startdate,
			String enddate) {

		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] cobjs = null;
		Object[] objs = null;// convert(datetime,string(intYear,'-',intMonth,"-",
		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from vStatHourPeerMms where intRaitype = "
				+ raitype
				+ " and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) >= "
				+ MantoEyeUtils.formatData(startdate, 1)
				+ " and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) <=  "
				+ MantoEyeUtils.formatData(enddate, 1);
		// 不同天的相同时段数量相加统计
		// String sql =
		// "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts),intHour from vStatHourPeerMms where intRaitype = ? and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) >= ? and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) <= ? group by intHour  order by intHour asc";

		// 不同天的相同时段作为不同的记录
		String sql = "Select nmUpCounts, nmDownCounts, nmTotalCounts,intYear,intMonth,intDay,intHour  from vStatHourPeerMms where intRaitype = "
				+ raitype
				+ " and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) >= "
				+ MantoEyeUtils.formatData(startdate, 1)
				+ " and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')) <=  "
				+ MantoEyeUtils.formatData(enddate, 1)
				+ " order by intYear asc,intMonth asc,intDay asc,intHour asc";

		SQLQuery tasq = this.getSession().createSQLQuery(total);
		List tars = tasq.list();

		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		List rs = sq.list();
		logger.info("Size:" + rs.size());
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();

				objs = (Object[]) rs.get(i);

				// bean.setFullDate(startdate+"到"+enddate);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));
				// bean.setDimensionId(Common.StringToLong(objs[3] + ""));
				// bean.setDimensionName(objs[3] + "");

				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR
								+ "");
				bean.setYear(Common.StringToInteger(objs[3] + ""));
				bean.setMonth(Common.StringToInteger(objs[4] + ""));
				bean.setDay(Common.StringToInteger(objs[5] + ""));
				bean.setHour(Common.StringToInteger(objs[6] + ""));

				bean.calculateData(totalUp, totalDown, totalTotal);

				bean.setDimensionName(bean.getFullDate());
				reault.add(bean);
			}
		}
		return reault;
	}

	/**
	 * 点对点空间分布（月）
	 * 
	 * @return
	 */
	public List<PeerMmsBean> findMonthPeerMmsSpace(int spaceLevel, int raitype,
			int year, int month) {

		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] objs = null;
		Object[] cobjs = null;
		String viewName = "";
		String dimId = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatMonthPeerMmsBscFlush";
			dimId = "intBscId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatMonthPeerMmsSgsnFlush";
			dimId = "intSgsnId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatMonthPeerMmsStreetFlush";
			dimId = "intStreetId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatMonthPeerMmsSaleAreaFlush";
			dimId = "intSaleAreaId";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatMonthPeerMmsBranchFlush";
			dimId = "intBranchId";
			dimName = "vcBranchName";
		}

		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from "
				+ viewName
				+ " where intRaitype = ? and intYear = ? and intMonth = ? ";
		String sql = "Select nmUpCounts,nmDownCounts,nmTotalCounts,"
				+ dimId
				+ ","
				+ dimName
				+ " from "
				+ viewName
				+ " where intRaitype = ? and intYear = ? and intMonth = ? order by nmTotalCounts desc";

		SQLQuery tasq = this.getSession().createSQLQuery(total);

		tasq.setInteger(0, raitype);
		tasq.setInteger(1, year);
		tasq.setInteger(2, month);

		List tars = tasq.list();

		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, raitype);
		sq.setInteger(1, year);
		sq.setInteger(2, month);
		List rs = sq.list();
		logger.info("findMonthPeerMmsSpace Size:" + rs.size());
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();
				bean.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH
						+ "");
				bean.setYear(year);
				bean.setMonth(month);
				objs = (Object[]) rs.get(i);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));
				bean.setDimensionId(Common.StringToLong(objs[3] + ""));
				bean.setDimensionName(objs[4] + "");

				bean.calculateData(totalUp, totalDown, totalTotal);
				reault.add(bean);
			}
		}
		return reault;
	}

	/**
	 * 点对点空间分布（周）
	 * 
	 * @return
	 */
	public List<PeerMmsBean> findWeekPeerMmsSpace(int spaceLevel, int raitype,
			int year, int week) {

		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] objs = null;
		Object[] cobjs = null;
		String viewName = "";
		String dimId = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatWeekPeerMmsBscFlush";
			dimId = "intBscId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatWeekPeerMmsSgsnFlush";
			dimId = "intSgsnId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatWeekPeerMmsStreetFlush";
			dimId = "intStreetId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatWeekPeerMmsSaleAreaFlush";
			dimId = "intSaleAreaId";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatWeekPeerMmsBranchFlush";
			dimId = "intBranchId";
			dimName = "vcBranchName";
		}

		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from "
				+ viewName
				+ " where intRaitype = ? and intYear = ? and intWeek = ? ";
		String sql = "Select nmUpCounts,nmDownCounts,nmTotalCounts,"
				+ dimId
				+ ","
				+ dimName
				+ " from "
				+ viewName
				+ " where intRaitype = ? and intYear = ? and intWeek = ? order by nmTotalCounts desc";

		SQLQuery tasq = this.getSession().createSQLQuery(total);

		tasq.setInteger(0, raitype);
		tasq.setInteger(1, year);
		tasq.setInteger(2, week);

		List tars = tasq.list();

		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, raitype);
		sq.setInteger(1, year);
		sq.setInteger(2, week);
		List rs = sq.list();
		logger.info("findWeekPeerMmsSpace Size:" + rs.size());
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();
				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK
								+ "");
				bean.setYear(year);
				bean.setWeek(week);
				objs = (Object[]) rs.get(i);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));
				bean.setDimensionId(Common.StringToLong(objs[3] + ""));
				bean.setDimensionName(objs[4] + "");

				bean.calculateData(totalUp, totalDown, totalTotal);
				reault.add(bean);
			}
		}
		return reault;
	}

	/**
	 * 点对点空间分布（天）
	 * 
	 * @return
	 */
	public List<PeerMmsBean> findDayPeerMmsSpace(int spaceLevel, int raitype,
			int year, int month, int day) {

		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] objs = null;
		Object[] cobjs = null;
		String viewName = "";
		String dimId = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatDayPeerMmsBscFlush";
			dimId = "intBscId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatDayPeerMmsSgsnFlush";
			dimId = "intSgsnId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatDayPeerMmsStreetFlush";
			dimId = "intStreetId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatDayPeerMmsSaleAreaFlush";
			dimId = "intSaleAreaId";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatDayPeerMmsBranchFlush";
			dimId = "intBranchId";
			dimName = "vcBranchName";
		}

		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from "
				+ viewName
				+ " where intRaitype = ? and intYear = ? and intMonth = ? and intDay = ?";
		String sql = "Select nmUpCounts,nmDownCounts,nmTotalCounts,"
				+ dimId
				+ ","
				+ dimName
				+ " from "
				+ viewName
				+ " where intRaitype = ? and intYear = ? and intMonth = ? and intDay = ? order by nmTotalCounts desc";

		SQLQuery tasq = this.getSession().createSQLQuery(total);

		tasq.setInteger(0, raitype);
		tasq.setInteger(1, year);
		tasq.setInteger(2, month);
		tasq.setInteger(3, day);

		List tars = tasq.list();

		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, raitype);
		sq.setInteger(1, year);
		sq.setInteger(2, month);
		sq.setInteger(3, day);
		List rs = sq.list();
		logger.info("findDayPeerMmsSpace Size:" + rs.size());
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();
				bean.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_DAY + "");
				bean.setYear(year);
				bean.setMonth(month);
				bean.setDay(day);
				objs = (Object[]) rs.get(i);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));
				bean.setDimensionId(Common.StringToLong(objs[3] + ""));
				bean.setDimensionName(objs[4] + "");

				bean.calculateData(totalUp, totalDown, totalTotal);
				reault.add(bean);
			}
		}
		return reault;
	}

	/**
	 * 点对点空间分布（小时）
	 * 
	 * @return
	 */
	public List<PeerMmsBean> findHourPeerMmsSpace(int spaceLevel, int raitype,
			int year, int month, int day, int hour) {

		Long totalUp = 0L;
		Long totalDown = 0L;
		Long totalTotal = 0L;
		List<PeerMmsBean> reault = new ArrayList<PeerMmsBean>();
		PeerMmsBean bean = null;
		Object[] objs = null;
		Object[] cobjs = null;
		String viewName = "";
		String dimId = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatHourPeerMmsBscFlush";
			dimId = "intBscId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatHourPeerMmsSgsnFlush";
			dimId = "intSgsnId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatHourPeerMmsStreetFlush";
			dimId = "intStreetId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatHourPeerMmsSaleAreaFlush";
			dimId = "intSaleAreaId";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatHourPeerMmsBranchFlush";
			dimId = "intBranchId";
			dimName = "vcBranchName";
		}

		String total = "Select sum(nmUpCounts), sum(nmDownCounts), sum(nmTotalCounts) from "
				+ viewName
				+ " where intRaitype = ? and intYear = ? and intMonth = ? and intDay = ? and intHour = ?";
		String sql = "Select nmUpCounts,nmDownCounts,nmTotalCounts,"
				+ dimId
				+ ","
				+ dimName
				+ " from "
				+ viewName
				+ " where intRaitype = ? and intYear = ? and intMonth = ? and intDay = ? and intHour = ? order by nmTotalCounts desc";

		SQLQuery tasq = this.getSession().createSQLQuery(total);

		tasq.setInteger(0, raitype);
		tasq.setInteger(1, year);
		tasq.setInteger(2, month);
		tasq.setInteger(3, day);
		tasq.setInteger(4, hour);

		List tars = tasq.list();

		if (tars != null && tars.size() > 0) {
			cobjs = (Object[]) tars.get(0);
			totalUp = Common.StringToLong(cobjs[0] + "");
			totalDown = Common.StringToLong(cobjs[1] + "");
			totalTotal = Common.StringToLong(cobjs[2] + "");
		}
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, raitype);
		sq.setInteger(1, year);
		sq.setInteger(2, month);
		sq.setInteger(3, day);
		sq.setInteger(4, hour);
		List rs = sq.list();
		logger.info("findHourPeerMmsSpace Size:" + rs.size());
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new PeerMmsBean();
				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR
								+ "");
				bean.setYear(year);
				bean.setMonth(month);
				bean.setDay(day);
				bean.setHour(hour);
				objs = (Object[]) rs.get(i);

				bean.setUpCount(Common.StringToLong(objs[0] + ""));
				bean.setDownCount(Common.StringToLong(objs[1] + ""));
				bean.setTotalCount(Common.StringToLong(objs[2] + ""));
				bean.setDimensionId(Common.StringToLong(objs[3] + ""));
				bean.setDimensionName(objs[4] + "");

				bean.calculateData(totalUp, totalDown, totalTotal);
				reault.add(bean);
			}
		}
		return reault;
	}

}
