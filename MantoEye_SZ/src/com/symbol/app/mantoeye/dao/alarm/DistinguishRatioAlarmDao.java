package com.symbol.app.mantoeye.dao.alarm;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.app.mantoeye.dto.alarm.AlarmRatioLimmit;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 识别率告警相关信息操作DAO
 * 
 * @author Jane
 * 
 */
@Repository
public class DistinguishRatioAlarmDao extends HibernateDao {

	/**
	 * 页面饼图所需数据查询
	 * 
	 * @param condition
	 *            条件集合
	 * @return
	 */
	public AlarmBean pieQueryByType(Map<String, Object> condition) {

		String sql = buildSql(condition);

		AlarmBean bean = (AlarmBean) this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("alarmId", Hibernate.INTEGER)
				.addScalar("fullDate", Hibernate.STRING)
				.addScalar("alarmRatio", Hibernate.STRING)
				.addScalar("reAlarmRatio", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.uniqueResult();

		return bean;
	}

	/**
	 * 构造查询语句
	 * 
	 * @param condition
	 * @return
	 */
	private String buildSql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select nmAlarmRegnizeId alarmId,");
		sql.append(" convert(varchar(10),convert(datetime,");
		sql.append(" string(intYear,'-',intMonth,'-',intDay))) fullDate,");
		sql.append(" convert(varchar(5),(nmRegnizeRate*100)) alarmRatio,");
		sql.append(" convert(varchar(5),(100-nmRegnizeRate*100)) reAlarmRatio");
		sql.append(" from tbAlarmRegnize");
		sql.append(" where 1=1");

		if (4 == condition.size()) {
			Set<String> keySet = condition.keySet();
			for (String key : keySet) {
				sql.append(" and ").append(key).append("=");
				sql.append(condition.get(key));
			}
		} else {
			sql.append(" and intType=").append(condition.get("intType"));
			sql.append(" and fullDate>='");
			sql.append(condition.get("startDate")).append("'");
			sql.append(" and fullDate<='");
			sql.append(condition.get("endDate")).append("'");
		}

		return sql.toString();
	}

	/**
	 * 查询历史告警记录
	 * 
	 * @param condition
	 *            查詢條件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmBean> queryHistoryAlarm(Page<AlarmBean> page,
			Map<String, Object> condition) {
		String sql = buildSql(condition);

		sql += " order by fullDate desc";

		List<AlarmBean> bean = this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("alarmId", Hibernate.INTEGER)
				.addScalar("alarmRatio", Hibernate.STRING)
				.addScalar("reAlarmRatio", Hibernate.STRING)
				.addScalar("fullDate", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list();

		return bean;
	}

	public int getTotalCount(Map<String, Object> condition) {
		String sql = buildSql(condition);

		sql = " select count(1) from ( " + sql + " ) as totalCount";

		Object o = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.parseInt(o.toString());

	}

	@SuppressWarnings("unchecked")
	public List<AlarmBean> queryHistoryAlarm(Map<String, Object> condition) {
		String sql = buildSql(condition);

		sql += " order by fullDate";

		List<AlarmBean> list = this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("alarmId", Hibernate.INTEGER)
				.addScalar("alarmRatio", Hibernate.STRING)
				.addScalar("reAlarmRatio", Hibernate.STRING)
				.addScalar("fullDate", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.list();

		return list;
	}

	public List<AlarmRatioLimmit> queryAlarmRatioLimmit() {
		String sql = buildSqlLimit();
		List<AlarmRatioLimmit> list = this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("typeName", Hibernate.STRING)
				.addScalar("minLimmit", Hibernate.STRING)
				.addScalar("maxLimmit", Hibernate.STRING)
				.addScalar("limmit", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(AlarmRatioLimmit.class))
				.list();

		return list;
	}

	private String buildSqlLimit() {
		StringBuffer sql = new StringBuffer();

		sql.append(" select case intType");
		sql.append(" when 1 then 'CGI' ");
		sql.append(" when 2 then 'BSC' ");
		sql.append(" when 3 then 'SGSN'");
		sql.append(" else '业务' ");
		sql.append(" end typeName,");
		sql.append(" (nmMinLimmit) minLimmit,");
		sql.append(" (nmMaxLimmit) maxLimmit,");
		// sql.append(" (nmMinLimmit*100) minLimmit,");
		// sql.append(" (nmMaxLimmit*100) maxLimmit,");
		sql.append(" (string(minLimmit)+'%~'+string(maxLimmit)+'%') limmit");
		sql.append(" from tbRegnizeLimmit");

		return sql.toString();
	}

	public AlarmRatioLimmit findAlarmRatioLimmit(String type) {
		String sql = buildSqlLimit();
		sql += " where 1=1 and intTYpe=" + type;

		AlarmRatioLimmit ar = (AlarmRatioLimmit) this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("typeName", Hibernate.STRING)
				.addScalar("minLimmit", Hibernate.STRING)
				.addScalar("maxLimmit", Hibernate.STRING)
				.addScalar("limmit", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(AlarmRatioLimmit.class))
				.uniqueResult();

		return ar;
	}

	public void configAlarmRatioLimmit(String type, String minLimmit,
			String maxLimmit) {
		String sql = " update tbRegnizeLimmit set nmMinLimmit = " + minLimmit
				+ ",nmMaxLimmit = " + maxLimmit + " where intType=" + type;

		this.getSession().createSQLQuery(sql).executeUpdate();

	}
}
