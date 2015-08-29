package com.symbol.app.mantoeye.dao.bigflushuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.flush.BigFlushUser;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.entity.business.BlackUser;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class BigFlushUserDAO extends HibernateDao {
	private String buildMsisdnCondition(String smsisdn){
		String smsql = "";
		if(Common.judgeString(smsisdn)){
			String [] conds = smsisdn.split(",");
			for(int i=0;i<conds.length;i++){
				if(Common.judgeString(conds[i])){
					smsql = smsql + " or  string(b.nmMsisdn) like '%"+conds[i]+"%'";
				}
			}
			if(smsql.length()>3){
				smsql = smsql.substring(3);
			}
			smsql = " and ("+smsql+")";
		}
		logger.info("smsql:"+smsql);
		return smsql;
	}

	public Page<BigFlushUser> query(final Page page, String sdate,
			String edate, int isBlackUser,String smsisdn) {
		String smsql = buildMsisdnCondition(smsisdn);
		String sql = "select b.nmOverFlushUsersId, b.nmImsi,b.nmMsisdn,b.nmFlush,b.nmAppUpFlush,b.nmAppDownFlush,h.nmBlackListUsersId,b.vcCgiChName,b.vcBranchName,convert(datetime,string(b.intYear,'-',b.intMonth,'-',b.intDay)) as ddate,b.vcUserBelong from vStatOverFlushUsers"
				+ " b left join ftbBlackListUsers h on b.nmMsisdn=h.nmMsisdn where b.bitIsOver =1 and ddate >='"
				+ sdate + "' and ddate <= '" + edate + "' " +smsql ;
		

		String countSql = "select count(*) from vStatOverFlushUsers"
				+ " b left join ftbBlackListUsers h on b.nmMsisdn=h.nmMsisdn where b.bitIsOver =1 and convert(datetime,string(b.intYear,'-',b.intMonth,'-',b.intDay))  >='"
				+ sdate
				+ "' and convert(datetime,string(b.intYear,'-',b.intMonth,'-',b.intDay)) <= '"
				+ edate + "' " +smsql ;
		if (isBlackUser == 2) {
			sql = sql + " and h.nmBlackListUsersId is null";
			countSql = countSql + " and h.nmBlackListUsersId is null";
		} else if (isBlackUser == 3) {
			sql = sql + " and h.nmBlackListUsersId is not null";
			countSql = countSql + " and h.nmBlackListUsersId is not null";
		}
		sql = sql + " order by b." + page.getOrderBy() + " " + page.getOrder();
		int totalCount = Common.StringToInt(this.getSession().createSQLQuery(
				countSql).list().get(0).toString());
		SQLQuery query = this.getSession().createSQLQuery(sql);
		// logger.info("---xx:"+page.getFirst()+"--"+page.getPageSize());
		query.setFirstResult(page.getFirst());
		query.setMaxResults(page.getPageSize());
		List list = query.list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(totalCount);

		List<BigFlushUser> result = this.buildBeanList(list);
		result = this.putImei(result);
		newPage.setResult(result);
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<BigFlushUser> queryAll(String sdate, String edate,
			int isBlackUser,String smsisdn) {
		String smsql = buildMsisdnCondition(smsisdn);
		String sql = "select b.nmOverFlushUsersId, b.nmImsi,b.nmMsisdn,b.nmFlush,b.nmAppUpFlush,b.nmAppDownFlush,h.nmBlackListUsersId,b.vcCgiChName,b.vcBranchName,convert(datetime,string(b.intYear,'-',b.intMonth,'-',b.intDay)) as ddate from vStatOverFlushUsers"
				+ " b left join ftbBlackListUsers h on b.nmMsisdn=h.nmMsisdn where b.bitIsOver =1 and ddate >='"
				+ sdate + "' and ddate <= '" + edate + "'";
		if (isBlackUser == 2) {
			sql = sql + " and h.nmBlackListUsersId is null";
		} else if (isBlackUser == 3) {
			sql = sql + " and h.nmBlackListUsersId is not null";
		}
		sql = sql + smsql;
		List list = this.getSession().createSQLQuery(sql).list();

		List<BigFlushUser> result = this.buildBeanList(list);
		result = this.putImei(result);
		return result;

	}

	public List<BigFlushUser> buildBeanList(List list) {
		List<BigFlushUser> bigUserList = new ArrayList<BigFlushUser>();
		BigFlushUser bigFlushUser = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bigFlushUser = new BigFlushUser();
				Object[] objs = (Object[]) list.get(i);
				bigFlushUser.setId(Common.StringToLong(objs[0] + ""));
				bigFlushUser.setImsi(Common.StringToLong(objs[1] + ""));
				bigFlushUser.setMsisdn(Common.StringToLong(objs[2] + ""));
				bigFlushUser.setTotalFlush(Common.StringToLong(objs[3] + ""));
				bigFlushUser.setUpFlush(Common.StringToLong(objs[4] + ""));
				bigFlushUser.setDownFlush(Common.StringToLong(objs[5] + ""));
				bigFlushUser.setCellName(CommonUtils.killNull(objs[7] + ""));
				bigFlushUser.setBranchName(CommonUtils.killNull(objs[8] + ""));
				Object blackId = objs[6];
				if (blackId == null) {
					bigFlushUser.setIsBlackUser("否");
				} else {
					bigFlushUser.setIsBlackUser("是");
				}
				bigFlushUser.setTime((objs[9] + "").split(" ")[0]);
				bigFlushUser.setVcUserBelong(objs[10] + "");
				bigFlushUser.calculateData();
				bigUserList.add(bigFlushUser);
			}
		}
		return bigUserList;
	}

	public List<BigFlushUser> queryDestinationByBigUser(String date,
			long msisdn, String flag) {
		String strDate = CommonUtils.changeDate(date, 2);
		String[] str = strDate.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int day = Common.StringToInt(str[3]);
		String sql = "select top 5 b.nmAppUpFlush,b.nmAppDownFlush,b.nmFlush,";
		if ("apn".equals(flag)) {
			sql = sql
					+ "a.vcApnTypeName from ftbStatApnOverFlush b left join dtbApnType a on b.intApnTypeId=a.intApnTypeId";
		} else if ("street".equals(flag)) {
			sql = sql
					+ "s.vcName from ftbStatStreetOverFlush b left join dtbStreet s on b.intStreetId=s.intStreetId";
		} else if ("url".equals(flag)) {
			sql = sql + "b.ipServerAddress from ftbStatIpOverFlush b";
		} else if ("contentType".equals(flag)) {
			sql = sql + "b.vcContentType from ftbStatContentTypeOverFlush b";
		} else if ("hour".equals(flag)) {
			sql = sql + "b.intHour from ftbStatTimeOverFlush b";
		} else if ("userAgent".equals(flag)) {
			sql = sql + "b.vcWapUserAgent from ftbStatUserAgentOverFlush b";
		}
		//
		sql = sql + " where b.nmMsisdn=" + msisdn + " and b.intYear=" + year
				+ " and b.intMonth=" + month + " and b.intDay=" + day
				+ " order by b.nmFlush desc ";
		List list = this.getSession().createSQLQuery(sql).list();
		return this.biludList(list, flag, date);
	}

	public List<BigFlushUser> biludList(List list, String flag, String date) {
		List<BigFlushUser> listBean = new ArrayList<BigFlushUser>();
		BigFlushUser bigFlushUser = null;
		for (int i = 0; i < list.size(); i++) {
			bigFlushUser = new BigFlushUser();
			Object[] obj = (Object[]) list.get(i);
			bigFlushUser.setTime(date);
			bigFlushUser.setTotalFlush(Common.StringToLong(obj[2] + ""));
			bigFlushUser.setUpFlush(Common.StringToLong(obj[0] + ""));
			bigFlushUser.setDownFlush(Common.StringToLong(obj[1] + ""));
			if ("apn".equals(flag)) {
				if (obj[3] == null) {
					bigFlushUser.setApnName("CMWAP");
				} else {
					bigFlushUser.setApnName(obj[3] + "");
				}
			} else if ("street".equals(flag)) {
				if (obj[3] == null) {
					bigFlushUser.setStreet("STREET");
				} else {
					bigFlushUser.setStreet(obj[3] + "");
				}
			} else if ("url".equals(flag)) {
				if (obj[3] == null) {
					bigFlushUser.setUrl("URL");
				} else {
					bigFlushUser.setUrl(obj[3] + "");
				}
			} else if ("contentType".equals(flag)) {
				if (obj[3] == null) {
					bigFlushUser.setContentType("CONTENTTYPE");
				} else {
					bigFlushUser.setContentType(obj[3] + "");
				}
			} else if ("hour".equals(flag)) {
				bigFlushUser.setHour(Common.StringToInt(obj[3] + ""));
			} else if ("userAgent".equals(flag)) {

				if (obj[3] == null) {
					bigFlushUser.setUserAgent("USERAGENT");
				} else {
					bigFlushUser.setUserAgent(obj[3] + "");
				}
			}
			bigFlushUser.calculateData();
			listBean.add(bigFlushUser);
		}
		return listBean;
	}

	public List<BigFlushUser> queryEntityByKeys(String keys) {
		String sql = "select nmMsisdn,nmFlush,nmImsi,intYear,intMonth,intDay from ftbStatOverFlushUsers where nmOverFlushUsersId in ("
				+ keys + ")";

		Iterator it = this.getSession().createSQLQuery(sql).list().iterator();
		List<BigFlushUser> list = new ArrayList<BigFlushUser>();

		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			BigFlushUser bigFlushUser = new BigFlushUser();
			bigFlushUser.setMsisdn(Common.StringToLong(obj[0] + ""));
			bigFlushUser.setTotalFlush(Common.StringToLong(obj[1] + ""));
			bigFlushUser.setImsi(Common.StringToLong(obj[2] + ""));
			String ymd = obj[3] + "-" + obj[4] + "-" + obj[5];
			Date date = Common.getYMDDate(ymd);
			if (ymd != null) {
				bigFlushUser.setTime(ymd);
			}

			list.add(bigFlushUser);
		}
		return list;
	}

	private List<BigFlushUser> putImei(List<BigFlushUser> list) {
		// String strDate="";
		String imsis = "";
		List<String> queryed = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				imsis = imsis + "," + list.get(i).getImsi();
			}
			imsis = imsis.substring(1);
			Map<Long, String> imeimap = getImeis(imsis, -1, -1, -1);
			for (int i = 0; i < list.size(); i++) {
				String imei = imeimap.get(list.get(i).getImsi());
				list.get(i).setImei(Common.judgeString(imei) ? imei : "--");
			}
		}
		return list;
	}

	// 获取IMEI
	private Map<Long, String> getImeis(String imsis, int year, int month,
			int day) {
		Map<Long, String> imeimap = new HashMap<Long, String>();
		Object[] objs;
		String imeis;
		String sql = " select distinct nmImsi,nmImei from ftbStatImeiOverFlush where nmImsi in ("
				+ imsis + ")";
		if (year > 0) {// 获取全部时无此过滤条件
			sql = sql + " and intYear =" + year + " and intMonth =" + month
					+ " and intDay =" + day;
		}
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				objs = (Object[]) list.get(i);
				Long imsi = Common.StringToLong(objs[0] + "");
				String imei = objs[1] + "";
				if (!"0".equals(imei)) {
					if (imeimap.containsKey(imsi)) {
						imeimap.put(imsi, imeimap.get(imsi) + "," + imei);
					} else {
						imeimap.put(imsi, imei);
					}
				}
			}
		}
		logger.info("Map:" + imeimap);
		return imeimap;
	}

	public List<String> findBigFlushMsisdn(String searchDateStart,
			String searchDateEnd, String like_msisdn_search) {
		List<String> result = new ArrayList();
		String sql = "select distinct b.nmMsisdn from vStatOverFlushUsers b where b.bitIsOver =1 and convert(varchar(20),b.nmMsisdn) like '%"
				+ like_msisdn_search
				+ "%' "
				+ " and  convert(datetime,string(b.intYear,'-',b.intMonth,'-',b.intDay))>= '"
				+ searchDateStart
				+ "' and  convert(datetime,string(b.intYear,'-',b.intMonth,'-',b.intDay))<= '"
				+ searchDateEnd + "'";
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				result.add(list.get(i) + "");
			}
		}
		return result;
	}

	/**
	 *走势 分页查询
	 */
	public Page<BigFlushUser> queryTrend(final Page page, String stime,
			String etime, String apnName) {
		String sql = buildTrendSql(stime, etime, apnName);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(page.getFirst());
		query.setMaxResults(page.getPageSize());
		List list = query.list();
		Page newPage = new Page();
		newPage.setResult(buildTrendBeanList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询
	 */
	public List<BigFlushUser> queryAllTrend(String stime, String etime,
			String apnName) {
		String sql = buildTrendSql(stime, etime, apnName);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildTrendBeanList(list);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<BigFlushUser> buildTrendBeanList(List list) {
		List<BigFlushUser> bigUserList = new ArrayList<BigFlushUser>();
		BigFlushUser bigFlushUser = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bigFlushUser = new BigFlushUser();
				Object[] objs = (Object[]) list.get(i);
				bigFlushUser.setId(Common.StringToLong(objs[0] + ""));
				bigFlushUser.setImsi(Common.StringToLong(objs[1] + ""));
				bigFlushUser.setMsisdn(Common.StringToLong(objs[2] + ""));
				bigFlushUser.setTotalFlush(Common.StringToLong(objs[3] + ""));
				bigFlushUser.setUpFlush(Common.StringToLong(objs[4] + ""));
				bigFlushUser.setDownFlush(Common.StringToLong(objs[5] + ""));
				bigFlushUser.setCellName(CommonUtils.killNull(objs[7] + ""));
				bigFlushUser.setBranchName(CommonUtils.killNull(objs[8] + ""));
				Object blackId = objs[6];
				if (blackId == null) {
					bigFlushUser.setIsBlackUser("否");
				} else {
					bigFlushUser.setIsBlackUser("是");
				}
				bigFlushUser.setTime((objs[9] + "").split(" ")[0]);
				bigFlushUser.calculateData();
				bigUserList.add(bigFlushUser);
			}
		}
		return bigUserList;
	}

	public String buildTrendSql(String stime, String etime, String msisdn) {
		String sql = "";
		String sortSql = "";
		sql = " select b.nmOverFlushUsersId, b.nmImsi,b.nmMsisdn,b.nmFlush,b.nmAppUpFlush,b.nmAppDownFlush,h.nmBlackListUsersId,b.vcCgiChName,b.vcBranchName,convert(datetime,string(b.intYear,'-',b.intMonth,'-',b.intDay)) as ddate from vStatOverFlushUsers"
				+ " b left join ftbBlackListUsers h on b.nmMsisdn=h.nmMsisdn where 1=1 ";
		if (stime != null && !stime.trim().equals("")) {
			sql = sql + " and ddate >='" + stime + "' ";
		}
		if (etime != null && !etime.trim().equals("")) {
			sql = sql + " and ddate <= '" + etime + "'";
		}
		sql = sql + " and b.nmMsisdn = " + msisdn + " order by ddate";
		sql = sql + sortSql;
		return sql;
	}
}
