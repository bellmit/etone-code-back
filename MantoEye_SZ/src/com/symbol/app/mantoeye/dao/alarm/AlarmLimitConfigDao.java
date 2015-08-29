package com.symbol.app.mantoeye.dao.alarm;

import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.alarm.AlarmLimitBean;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 流量告警阀值设置类DAO
 * 
 * @author Jane
 * 
 */
@Repository
public class AlarmLimitConfigDao extends HibernateDao<AlarmLimitBean, Integer> {

	@SuppressWarnings("unchecked")
	public List<AlarmLimitBean> queryAlarmLimit(Map<String, Object> condition) {
		String sql = buildSql(condition);

		List<AlarmLimitBean> list = this.getSession().createSQLQuery(sql)
				.addScalar("alarmType", Hibernate.STRING).addScalar(
						"alarmName", Hibernate.STRING).addScalar("operator",
						Hibernate.STRING).addScalar("alarmLimit",
						Hibernate.STRING).addScalar("timeLevel",
						Hibernate.STRING).setResultTransformer(
						Transformers.aliasToBean(AlarmLimitBean.class)).list();

		list = listIsNull(list, condition.get("type").toString());

		return list;
	}

	private String buildSql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();

		sql.append(" select vcAlarmName alarmType,");
		sql.append(" case vcAlarmName");
		sql.append(" when '").append(condition.get("alarmTypeLow"));
		sql.append(" ' then '").append(condition.get("type"));
		sql.append(" 流量异常' ");
		sql.append(" when '").append(condition.get("alarmTypeInrease"));
		sql.append(" ' then '").append(condition.get("type"));
		sql.append(" 流量增长率' ");
		sql.append(" when '").append(condition.get("alarmTypeDerease"));
		sql.append(" ' then '").append(condition.get("type"));
		sql.append(" 流量下降率'");
		sql.append(" end alarmName,vcCompare operator,");
		sql.append(" nmAlarmLimit alarmLimit,");
		sql.append(" intGranularity timeLevel");
		sql.append(" from tbAlarmPolicy");
		sql.append(" where 1=1 ");
		sql.append(" and vcAlarmName in ('");
		sql.append(condition.get("alarmTypeLow"));
		sql.append(" ','").append(condition.get("alarmTypeInrease"));
		sql.append(" ','").append(condition.get("alarmTypeDerease"));
		sql.append(" ')");

		return sql.toString();
	}

	private List<AlarmLimitBean> listIsNull(List<AlarmLimitBean> list,
			String type) {
		if (null == list || list.isEmpty()) {
			if ("BSC".equals(type)) {
				list.add(new AlarmLimitBean("4", "BSC流量异常", "--", "--", "--"));
				list.add(new AlarmLimitBean("5", "BSC流量增长率", "--", "--", "--"));
				list.add(new AlarmLimitBean("6", "BSC流量下降率", "--", "--", "--"));
			} else if ("小区".equals(type) || "CGI".equals(type)) {
				list.add(new AlarmLimitBean("1", "小区流量异常", "--", "--", "--"));
				list.add(new AlarmLimitBean("2", "小区流量增长率", "--", "--", "--"));
				list.add(new AlarmLimitBean("3", "小区流量下降率", "--", "--", "--"));
			} else if ("SGSN".equals(type)) {
				list.add(new AlarmLimitBean("8", "SGSN流量异常", "--", "--", "--"));
				list
						.add(new AlarmLimitBean("9", "SGSN流量增长率", "--", "--",
								"--"));
				list
						.add(new AlarmLimitBean("10", "SGSN流量下降率", "--", "--",
								"--"));
			}

		}

		return list;
	}

	public void updateAlarmLimit(Map<String, Object> condition) {
		String sql = buildUpdate(condition);

		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	private String buildUpdate(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();

		sql.append(" update tbAlarmPolicy set vcCompare='");
		sql.append(condition.get("operator"));
		sql.append("',");
		sql.append(" nmAlarmLimit=");
		sql.append(condition.get("alarmLimit"));
		sql.append(" , intGranularity=");
		sql.append(condition.get("timeLevel"));
		sql.append("");
		sql.append(" where vcAlarmName='");
		sql.append(condition.get("alarmType"));
		sql.append("'");

		return sql.toString();
	}

	public int findAlarmByType(String alarmType) {
		String sql = "select count(1) from tbAlarmPolicy where vcAlarmName='"
				+ alarmType + "'";

		Object count = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.parseInt(count.toString());
	}

	public void addAlarmLimit(Map<String, Object> condition) {

		String sql = buildAddSql(condition);

		this.getSession().createSQLQuery(sql).executeUpdate();

	}

	private String buildAddSql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();

		sql.append(" insert into tbAlarmPolicy(nmAlarmPolicyId,vcAlarmName,");
		sql.append(" vcCompare,nmAlarmLimit,intGranularity)");
		sql.append(" select top(1) nmAlarmPolicyId+1,");
		sql.append(" '").append(condition.get("alarmType"));
		sql.append("','").append(condition.get("operator"));
		sql.append("',").append(condition.get("alarmLimit"));
		sql.append(" ,").append(condition.get("timeLevel"));
		sql.append(" from tbAlarmPolicy order by nmAlarmPolicyId desc");

		return sql.toString();
	}

	public int findAll() {
		String sql = "select count(1) from tbAlarmPolicy ";

		Object count = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.parseInt(count.toString());
	}

	public void addAlarmLimitOne(Map<String, Object> condition) {
		String sql = buildAddSqlOne(condition);

		this.getSession().createSQLQuery(sql).executeUpdate();

	}

	private String buildAddSqlOne(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();

		sql.append(" insert into tbAlarmPolicy(nmAlarmPolicyId,vcAlarmName,");
		sql.append(" vcCompare,nmAlarmLimit,intGranularity)");
		sql.append(" values(1,'").append(condition.get("alarmType")).append(
				"','");
		sql.append(condition.get("operator"));
		sql.append("',").append(condition.get("alarmLimit"));
		sql.append(" ,").append(condition.get("timeLevel"));
		sql.append(" )");
		return sql.toString();
	}

}
