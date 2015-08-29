package com.symbol.app.mantoeye.dao.blackberry;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.blackberry.ActiveUserBean;
import com.symbol.app.mantoeye.dto.blackberry.ActiveUserSpaceBean;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

/**
 * 
 * 黑莓 活跃用户统计
 * 
 * @author rankin
 * 
 */
@Repository
public class BlackBerryActiveUserDAO extends CommonQueryDAO {

	/**
	 * 活跃用户呈现
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<ActiveUserBean> findAllActiveUser(int year, int month,
			String conditionType, Long countryId, Long ldcId, String condition,
			int conditionVal) {
		List<ActiveUserBean> result = new ArrayList<ActiveUserBean>();
		ActiveUserBean bean = null;
		Object[] objs = null;

		String sql = "select nmUpFlush,nmDownFlush,nmTotalFlush,nmUseTimes,intUseDays,vcCgi,nmMsisdn,nmImsi,vcIdc,vcCountryName,vcBranchName from vStatMonthBbActiveUser where  intYear = ? and intMonth = ? ";

		sql = sql
				+ buildConditionSQL(conditionType, countryId, ldcId, condition,
						conditionVal);
		sql = sql + "  order by intUseDays DESC";
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new ActiveUserBean();
				bean.setYear(year);
				bean.setMonth(month);

				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setCount(Common.StringToLong(objs[3] + ""));
				bean.setDays(Common.StringToInteger(objs[4] + ""));
				bean.setCgi(objs[5] + "");
				bean.setMsisdn(objs[6] + "");
				bean.setImsi(objs[7] + "");
				bean.setVcIdc(objs[8] + "");
				bean.setVcCountryName(objs[9] + "");
				bean.setVcBrandName(objs[10] + "");
				bean.calculateData();
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * 活跃用户呈现 分页
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<ActiveUserBean> findActiveUserPage(int year, int month,
			Page page, String conditionType, Long countryId, Long ldcId,
			String condition, int conditionVal) {
		List<ActiveUserBean> result = new ArrayList<ActiveUserBean>();
		ActiveUserBean bean = null;
		Object[] objs = null;

		String sql = "select bb.nmUpFlush,bb.nmDownFlush,bb.nmTotalFlush,bb.nmUseTimes,bb.intUseDays,cgi.vcCgiChName,bb.nmMsisdn,bb.nmImsi,bb.vcIdc,bb.vcCountryName,bb.vcBranchName from vStatMonthBbActiveUser bb,ftbcgi cgi where  bb.vcCgi=cgi.vcCgi and intYear = ? and intMonth = ?";
		sql = sql
				+ buildConditionSQL(conditionType, countryId, ldcId, condition,
						conditionVal);
		sql = sql + "  order by bb.nmTotalFlush DESC";

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		sq.setFirstResult(page.getFirst());
		sq.setMaxResults(page.getPageSize());
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new ActiveUserBean();
				bean.setYear(year);
				bean.setMonth(month);

				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setCount(Common.StringToLong(objs[3] + ""));
				bean.setDays(Common.StringToInteger(objs[4] + ""));
				bean.setCgi(objs[5] + "");
				bean.setMsisdn(objs[6] + "");
				bean.setImsi(objs[7] + "");
				bean.setVcIdc(objs[8] + "");
				bean.setVcCountryName(objs[9] + "");
				bean.setVcBrandName(objs[10] + "");
				bean.calculateData();
				result.add(bean);
			}
		}

		return result;
	}

	public int findActiveUserCount(int year, int month, String conditionType,
			Long countryId, Long ldcId, String condition, int conditionVal) {
		int count = 0;
		String sql = "select count(*) from vStatMonthBbActiveUser where  intYear = ? and intMonth = ? ";
		sql = sql
				+ buildConditionSQL(conditionType, countryId, ldcId, condition,
						conditionVal);

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		List list = sq.list();
		if (list != null && list.size() > 0) {
			count = Common.StringToInt(list.get(0) + "");
		}
		logger.info("---count:" + count);
		return count;

	}

	// /**
	// * 活跃用户空间分布
	// *
	// * @param spaceLevel
	// * @param year
	// * @param month
	// * @return
	// */
	// public List<ActiveUserSpaceBean> findActiveUserSpace(int spaceLevel,
	// int year, int month, String conditionType, Long countryId,
	// Long ldcId, String condition, int conditionVal) {
	// List<ActiveUserSpaceBean> reault = new ArrayList<ActiveUserSpaceBean>();
	// ActiveUserSpaceBean bean = null;
	// Object[] objs = null;
	// String viewName = "";
	// String dimName = "";
	// if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
	// viewName = "vStatMonthBscBbActiveUser";
	// dimName = "vcName";
	// } else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
	// viewName = "vStatMonthSgsnBbActiveUser";
	// dimName = "vcName";
	// } else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
	// viewName = "vStatMonthStreetBbActiveUser";
	// dimName = "vcName";
	// } else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
	// viewName = "vStatMonthSaleAreaBbActiveUser";
	// dimName = "vcSaleAreaName";
	// } else {
	// viewName = "vStatMonthCompanyBbActiveUser";
	// dimName = "vcBranchName";
	// }
	//
	// String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmUsers,"
	// + dimName
	// + " from "
	// + viewName
	// + " where intYear = ? and intMonth = ? order by nmTotalFlush desc";
	//
	// SQLQuery sq = this.getSession().createSQLQuery(sql);
	// sq.setInteger(0, year);
	// sq.setInteger(1, month);
	// List rs = sq.list();
	//
	// if (rs != null && rs.size() > 0) {
	// for (int i = 0; i < rs.size(); i++) {
	// bean = new ActiveUserSpaceBean();
	// bean.setYear(year);
	// bean.setMonth(month);
	// objs = (Object[]) rs.get(i);
	//
	// bean.setUpFlush(Common.StringToLong(objs[0] + ""));
	// bean.setDownFlush(Common.StringToLong(objs[1] + ""));
	// bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
	// bean.setImsis(Common.StringToLong(objs[3] + ""));
	// bean.setDimensionName(objs[4] + "");
	//
	// bean.calculateData();
	// reault.add(bean);
	// }
	// }
	// return reault;
	// }
	/**
	 * 活跃用户空间分布
	 * 
	 * @param spaceLevel
	 * @param year
	 * @param month
	 * @return
	 */
	public List<ActiveUserSpaceBean> findActiveUserSpace(int spaceLevel,
			int year, int month, String conditionType, Long countryId,
			Long ldcId, String condition, int conditionVal) {
		List<ActiveUserSpaceBean> reault = new ArrayList<ActiveUserSpaceBean>();
		ActiveUserSpaceBean bean = null;
		Object[] objs = null;
		String viewName = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatMonthBbActiveUser";
			dimName = "vcBscName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatMonthBbActiveUser";
			dimName = "vcSgsnName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatMonthBbActiveUser";
			dimName = "vcStreetName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatMonthBbActiveUser";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatMonthBbActiveUser";
			dimName = "vcBranchName";
		}
		String otherout = "";
		String othercondition = "";
		String cntcondition = " ";
		if (condition.equals("1")) {// 大于
			cntcondition = " and intUseDays > " + conditionVal;
		} else if (condition.equals("2")) {// 小于
			cntcondition = " and intUseDays < " + conditionVal;
		} else if (condition.equals("3")) {// 等于
			cntcondition = " and intUseDays = " + conditionVal;
		}
		boolean hascountry = false;
		boolean hasldc = false;
		if (conditionType.equals("1")) {// 通过国家过滤
			if (countryId > 0L) {
				hascountry = true;
				otherout = ",intCountryId,vcCountryName";
				if(countryId>0){
				othercondition = " and intCountryId = " + countryId;
				}
			}
		} else if (conditionType.equals("2")) {// 通过运营商过滤
			if (ldcId > 0L) {
				hasldc = true;
				otherout = ",intIdcId,vcIdc";
				if(ldcId>0){
				othercondition = " and intIdcId = " + ldcId;
				}
			}
		}
		// Select
		// sum(nmUpFlush),sum(nmDownFlush),sum(nmTotalFlush),count(nmImsi)
		// ,vcBranchName,intCountryId,vcCountryName from vStatMonthBbActiveUser
		// where intYear = 2010 and intMonth = 2 and intCountryId = 101 and
		// intUseDays>2 group by vcBranchName ,intCountryId,vcCountryName
		String sql = "Select sum(nmUpFlush),sum(nmDownFlush),sum(nmTotalFlush)  as ctflush,count(nmImsi),"
				+ dimName
				+ otherout
				+ " from "
				+ viewName
				+ " where intYear = ? and intMonth = ? "
				+ othercondition
				+ cntcondition;
		sql = sql + " group by " + dimName + otherout
				+ " order by ctflush desc";
		logger.info("SQL:"+sql);
		logger.info("year:"+year+" month:"+month);
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		List rs = sq.list();
		try{
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new ActiveUserSpaceBean();
				bean.setYear(year);
				bean.setMonth(month);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setImsis(Common.StringToLong(objs[3] + ""));
				bean.setDimensionName(objs[4] + "");
				if(objs.length>6){
					String oo6 = objs[6] + "";
					if(hascountry){
						bean.setCountryId(countryId);
						bean.setCountryName(oo6);
					}
					if(hasldc){
						bean.setLdcId(ldcId);
						bean.setLdcName(oo6);
					}
				}	
				bean.calculateData();
				reault.add(bean);
			}
		}}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("SSIZE:"+reault.size());
		return reault;
	}

	/*
	 * 获取条件SQL
	 * 
	 * @param conditionType
	 * 
	 * @param countryId
	 * 
	 * @param ldcId
	 * 
	 * @param condition
	 * 
	 * @param conditionVal
	 * 
	 * @return
	 */
	private String buildConditionSQL(String conditionType, Long countryId,
			Long ldcId, String condition, int conditionVal) {
		String sql = " ";
		if (conditionType.equals("1")) {// 通过国家过滤
			if (countryId > 0L) {
				sql = sql + " and intCountryId = " + countryId;
			}
		} else if (conditionType.equals("2")) {// 通过运营商过滤
			if (ldcId > 0L) {
				sql = sql + " and intIdcId = " + ldcId;
			}
		}
		if (condition.equals("1")) {// 大于
			sql = sql + " and intUseDays > " + conditionVal;
		} else if (condition.equals("2")) {// 小于
			sql = sql + " and intUseDays < " + conditionVal;
		} else if (condition.equals("3")) {// 等于
			sql = sql + " and intUseDays = " + conditionVal;
		}
		return sql;
	}
}
