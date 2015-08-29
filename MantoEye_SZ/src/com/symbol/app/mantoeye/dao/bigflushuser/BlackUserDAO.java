package com.symbol.app.mantoeye.dao.bigflushuser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.flush.BigFlushUser;
import com.symbol.app.mantoeye.entity.business.BlackUser;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

//Spring DAO Bean的标识
@Repository
public class BlackUserDAO extends HibernateDao<BlackUser, Long> {

	public Page<BlackUser> query(final Page page, String msisdn) {

		String sql = "select b.nmBlackListUsersId,b.nmImsi,b.nmMsisdn,b.dtFirstTime,b.vcUserAction,b.dtLastTime,"
				+ "b.intLastFlush,b.intOverDate,b.intLastMonthFlush from vftbBlackListUsers b where 1=1 ";
		if (Common.judgeString(msisdn)) {
			msisdn = msisdn.trim();
			sql = sql + " and convert(varchar(20),b.nmMsisdn) like '%" + msisdn
					+ "%'";
		}

		sql = sql + " order by b." + page.getOrderBy() + " " + page.getOrder();
		// logger.info("--sql:"+sql);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<Object[]> list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		Page<BlackUser> newPage = new Page<BlackUser>();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		List<BlackUser> result = this.buildList(list);
		result = this.putImei(result, -1, -1, -1);
		newPage.setResult(result);
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<BlackUser> queryAllBlackUser(final Page page, String msisdn) {
		String sql = "select b.nmBlackListUsersId,b.nmImsi,b.nmMsisdn,b.dtFirstTime,b.vcUserAction,b.dtLastTime,"
				+ "b.intLastFlush,b.intOverDate,b.intLastMonthFlush from vftbBlackListUsers b where 1=1 ";
		if (msisdn != null && msisdn != "") {
			msisdn = msisdn.trim();
			sql = sql + " and convert(varchar(20),b.nmMsisdn) like '%" + msisdn
					+ "%'";
		}

		sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
		// logger.info("sql:" + sql);
		List<Object[]> pageList = this.getSession().createSQLQuery(sql).list();
		List<BlackUser> result = this.buildList(pageList);
		result = this.putImei(result, -1, -1, -1);
		return result;
	}

	public List<BlackUser> buildList(List<Object[]> list) {
		List<BlackUser> dataList = new ArrayList<BlackUser>();
		if (list != null & !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				BlackUser blackUser = new BlackUser();
				blackUser.setId(Common.StringToLong(obj[0] + ""));
				blackUser.setImsi(Common.StringToLong(obj[1] + ""));
				blackUser.setMsisdn(Common.StringToLong(obj[2] + ""));
				if (obj[3] != null) {
					blackUser.setStrFirstTime(CommonUtils.formatDate(obj[3])
							.split(" ")[0]);
				} else {
					blackUser.setStrFirstTime("--");
				}
				blackUser.setDescribe(obj[4] + "");
				if (obj[5] != null) {
					blackUser.setStrLastTime(CommonUtils.formatDate(obj[5])
							.split(" ")[0]);
				} else {
					blackUser.setStrLastTime("--");
				}
				blackUser.setLastFlush(Common.StringToLong(obj[6] + ""));
				blackUser.setOverDay(Common.StringToInt(obj[7] + ""));
				blackUser.setPreMonth(Common.StringToLong(obj[8] + ""));
				blackUser.calculateData();
				dataList.add(blackUser);
			}
		}
		return dataList;
	}

	public BlackUser queryEntiryById(Long id) {
		String sql = "select nmBlackListUsersId,nmImsi,nmMsisdn,dtFirstTime,vcUserAction,dtLastTime,intLastFlush,intOverDate,intLastMonthFlush from ftbBlackListUsers where nmBlackListUsersId="
				+ id;
		Iterator it = this.getSession().createSQLQuery(sql).list().iterator();
		BlackUser blackUser = new BlackUser();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			blackUser.setId(Common.StringToLong(obj[0] + ""));
			blackUser.setImsi(Common.StringToLong(obj[1] + ""));
			blackUser.setMsisdn(Common.StringToLong(obj[2] + ""));
			if (obj[3] != null) {
				blackUser.setStrFirstTime(CommonUtils.formatDate(obj[3]).split(
						" ")[0]);
			} else {
				blackUser.setStrFirstTime("--");
			}
			blackUser.setDescribe(obj[4] + "");
			if (obj[5] != null) {
				blackUser.setStrLastTime(CommonUtils.formatDate(obj[5]).split(
						" ")[0]);
			} else {
				blackUser.setStrLastTime("--");
			}
			blackUser.setLastFlush(Common.StringToLong(obj[6] + ""));
			blackUser.setOverDay(Common.StringToInt(obj[7] + ""));
			blackUser.setPreMonth(Common.StringToLong(obj[8] + ""));
			blackUser.calculateData();
		}
		return blackUser;
	}

	public int deleteUser(String id) {
		String sql = "delete from ftbBlackListUsers where nmBlackListUsersId in ("
				+ id + ")";
		return this.getSession().createSQLQuery(sql).executeUpdate();
	}

	public void insertBlackUser(List<BigFlushUser> list, String descrite) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into ftbBlackListUsers(nmImsi,nmMsisdn,dtFirstTime,vcUserAction,dtLastTime,intLastFlush,intOverDate,intLastMonthFlush) values (?,?,?,?,?,?,?,?)";
		for (int i = 0; i < list.size(); i++) {
			BigFlushUser bigFlusUser = list.get(i);
			Long msisdn = bigFlusUser.getMsisdn();
			Long imsi = bigFlusUser.getImsi();
			Long totalFlush = bigFlusUser.getTotalFlush();
			Date strartTime = Common.getYMDDate(bigFlusUser.getTime());
			if (strartTime == null) {
				strartTime = Common.getYMDDate(Common.getNow());
			}
			String sql2 = "select count(*) from ftbBlackListUsers where nmMsisdn="
					+ msisdn;
			int count = Common.StringToInt(this.getSession().createSQLQuery(
					sql2).list().get(0).toString());
			if (count < 1) {
				SQLQuery query = this.getSession().createSQLQuery(sql);
				query.setParameter(0, imsi);
				query.setParameter(1, msisdn);
				query.setParameter(2, strartTime);
				query.setParameter(3, descrite);
				query.setParameter(4, strartTime);
				query.setParameter(5, totalFlush);
				query.setParameter(6, 1);
				query.setParameter(7, totalFlush);
				query.executeUpdate();
			}
		}
	}

	private List<BlackUser> putImei(List<BlackUser> list, int year, int month,
			int day) {
		String imsis = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				imsis = imsis + "," + list.get(i).getImsi();
			}
			imsis = imsis.substring(1);
			// logger.info("IMSI:"+imsis);
			Map<Long, String> imeimap = getImeis(imsis, year, month, day);
			for (int i = 0; i < list.size(); i++) {
				Long imsi = list.get(i).getImsi();
				list.get(i).setImei(imeimap.get(imsi));
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
				if (imeimap.containsKey(imsi)) {
					imeimap.put(imsi, imeimap.get(imsi) + "," + imei);
				} else {
					imeimap.put(imsi, imei);
				}
			}
		}
		// logger.info("Map:"+imeimap);
		return imeimap;
	}
}
