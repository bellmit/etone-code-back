package com.symbol.app.mantoeye.dao.alarm;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.bean.MonitoringBean;
import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;

@Repository
public class MonitoringDBDao extends CommonQueryDAO {

	public MonitoringBean initQuery(String currentTime) {

		String sql = buildSql(currentTime);

		sql += " order by pTime desc ";

		MonitoringBean bean = (MonitoringBean) this.getSession()
				.createSQLQuery(sql).addScalar("mainSpaceSize", Hibernate.LONG)
				.addScalar("usedMainSpaceSize", Hibernate.LONG).addScalar(
						"mainSpaceRate", Hibernate.INTEGER).addScalar(
						"connMax", Hibernate.INTEGER).addScalar("curConn",
						Hibernate.INTEGER).addScalar("otherVersion",
						Hibernate.LONG).addScalar("currentTime",
						Hibernate.STRING).addScalar("unit", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(MonitoringBean.class))
				.uniqueResult();

		return bean;
	}

	private String buildSql(String currentTime) {
		StringBuffer sql = new StringBuffer();

		sql.append(" select top 1 pIqMainStoreSize mainSpaceSize,");
		sql.append(" pIqMainStoreSizeUsed usedMainSpaceSize,");
		sql.append(" pIqMainStoreUseRate mainSpaceRate,");
		sql.append(" pIqConnectionMax connMax,");
		sql.append(" pIqConnection curConn,");
		sql.append(" pOtherVersion otherVersion,");
		sql.append(" convert(varchar(19),string(pTime)) currentTime,pType unit");
		sql.append(" from tbMonitorIQ");
		sql.append(" where 1=1");
		sql.append(" and convert(varchar(13),string(pTime)) = '");
		sql.append(currentTime).append("'");

		return sql.toString();
	}

}
