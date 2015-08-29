package com.symbol.app.mantoeye.dao.sports.aggregateNumDAO;

import java.util.ArrayList;

import java.util.Date;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class AggregateNumDAO extends HibernateDao {

	public Page<CommonSport> query(final Page page, String sTime, String eTime,
			String phoneNumber, String CustomerName) {
		String sql = this.buildSql(phoneNumber, sTime, eTime,CustomerName, page);

		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("dtUpdateTime",
						Hibernate.DATE).addScalar("nmMsisdn", Hibernate.STRING).addScalar("vcNote", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(list);
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<String> getAllMsisdn() {
		String sqlString = "select nmMsisdn from ftbMsisdnList where intType=2";
		List<String> msisdns = this.getSession().createSQLQuery(sqlString)
				.list();
		// List<FtbMsisdnList> t = getAll();
		// List msisdns=null;
		return msisdns;
	}

	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 *            .
	 * @param endTime
	 * @return
	 */
	public String buildSql(String phoneNumber, String sTime, String eTime,
			String CustomerName, Page page) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "dtUpdateTime";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}

		sql = "select nmMsisdnListId as id,dtUpdateTime,right(convert(varchar,nmMsisdn),11) as nmMsisdn,intType,vcNote from ftbMsisdnList where intType=2 ";
		if (sTime != null && !sTime.equals("")) {
			sql = sql + " and convert(date,dtUpdateTime) >= "
					+ MantoEyeUtils.formatData(sTime, 2);
		}
		if (eTime != null && !eTime.equals("")) {
			sql = sql + " and convert(date,dtUpdateTime) <="
					+ MantoEyeUtils.formatData(eTime, 2);
		}

		sortSql = " order by " + sortColumn + " " + sortType;

		if (phoneNumber != null && !("").equals(phoneNumber)) {
			sql = sql + " and convert(varchar,nmMsisdn) like '%" + phoneNumber
					+ "%'";
		}
		if (CustomerName != null && !("").equals(CustomerName)) 
			sql = sql + " and vcNote like '%" + CustomerName
					+ "%'";
		sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> listData(String sTime, String eTime,
			String phoneNumber, String CustomerName) {
		String sql = this.buildSql(phoneNumber, sTime, eTime,CustomerName, null);
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("dtUpdateTime",
						Hibernate.DATE).addScalar("nmMsisdn", Hibernate.STRING).addScalar("vcNote", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class)).list();
		return list;
	}

	public int findByMsisdn(String msisdn) {
		String sqlString = "select count(1) from  ftbMsisdnList where intType=2 and nmMsisdn = 86"
				+ msisdn;
		Object objcount = this.getSession().createSQLQuery(sqlString)
				.uniqueResult();
		Integer count = Integer.parseInt(null == objcount.toString()
				|| "".equals(objcount.toString()) ? "0" : objcount.toString());
		return count;
	}

	public void deleteByKeys(String ids) {
		String sql = "delete from ftbMsisdnList where nmMsisdnListId in ("
				+ ids + ")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	public void saveModify(Integer id, String nmMsisdn, String dtUpdateTime,String CustomerName) {

		String sql = "update ftbMsisdnList set nmMsisdn = " + nmMsisdn
				+ ",dtUpdateTime='" + dtUpdateTime + "',vcNote='"+CustomerName+"' where nmMsisdnListId="
				+ id;
		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	public void savenew(String vcNote,long Msisdns, String datetime) {
		String sqlString = "insert into ftbMsisdnList (vcNote,nmMsisdn,intType,dtUpdateTime) values('"
			+vcNote+"','"+ Msisdns + "',2,'" + datetime + "')";
		this.getSession().createSQLQuery(sqlString).executeUpdate();
	}

}
