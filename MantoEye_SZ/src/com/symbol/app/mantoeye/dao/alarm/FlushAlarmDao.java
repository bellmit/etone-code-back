package com.symbol.app.mantoeye.dao.alarm;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 流量告警信息DAO
 * 
 * @author Jane
 * 
 */
@Repository
public class FlushAlarmDao extends HibernateDao {

	/**
	 * 初始化BSC,CGI,SGSN三个业务类型的流量告警相关信息
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public AlarmBean initQuery(Map<String, Object> condition) {

		String sql = buildInitSql(condition);
		AlarmBean bean = (AlarmBean) this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("typeName", Hibernate.STRING)
				.addScalar("countLow", Hibernate.INTEGER)
				.addScalar("countIncrease", Hibernate.INTEGER)
				.addScalar("countDecrease", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.uniqueResult();

		if (null == bean) {
			bean = new AlarmBean();
			String typeName = condition.get("typeName").toString();
			bean.setTypeName(typeName);
			bean.setCountDecrease(0);
			bean.setCountIncrease(0);
			bean.setCountLow(0);
		}
		return bean;
	}

	/**
	 * 构造初始化查询的sql语句
	 * 
	 * @param condition
	 * @return
	 */
	private String buildInitSql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select '").append(condition.get("typeName"));
		sql.append("' typeName, case isLow when 1 then ");
		sql.append(" count(b.isLow) else 0  end countLow ,");
		sql.append(" aa.countIncrease,cc.countDecrease");
		sql.append(" from ").append(condition.get("tableName")).append(" b ,");
		sql.append(" (select  case isIncrease when 1 then");
		sql.append(" count(a.isIncrease) else 0 end countIncrease");
		// sql.append(" from vAlarmBscFlush a ");
		sql.append(" from ").append(condition.get("tableName")).append(" a");
		sql.append(" where 1=1 ");
		Set<String> keySet = condition.keySet();

		for (String key : keySet) {
			if (!"tableName".equals(key) && !"typeName".equals(key)) {
				sql.append(" and a.").append(key).append("=")
						.append(condition.get(key));
			}
		}
		sql.append(" and a.isIncrease=1");
		sql.append(" group by a.isIncrease  ) aa,");
		sql.append(" (select  case isDecrease when 1 then");
		sql.append(" count(c.isDecrease) else 0 end  countDecrease");
		// sql.append(" from vAlarmBscFlush c");
		sql.append(" from ").append(condition.get("tableName")).append(" c");
		sql.append(" where 1=1 ");
		for (String key : keySet) {
			if (!"tableName".equals(key) && !"typeName".equals(key)) {
				sql.append(" and c.").append(key).append("=")
						.append(condition.get(key));
			}
		}
		sql.append(" and c.isDecrease=1");
		sql.append(" group by c.isDecrease  ) cc");
		sql.append(" where 1=1 ");
		for (String key : keySet) {
			if (!"tableName".equals(key) && !"typeName".equals(key)) {
				sql.append(" and b.").append(key).append("=")
						.append(condition.get(key));
			}
		}
		sql.append(" and b.isLow=1");
		sql.append(" group by b.isLow,aa.countIncrease,cc.countDecrease");

		return sql.toString();
	}

	/**
	 * 查询历史流量告警信息
	 * 
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmBean> queryHistory(Page<AlarmBean> page,
			Map<String, Object> condition) {
		String sql = bulidHistorySql(condition);

		sql += " order by " + page.getOrderBy() + " " + page.getOrder();

		List<AlarmBean> list = this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("typeName", Hibernate.STRING)
				.addScalar("typeId", Hibernate.STRING)
				.addScalar("changeFlush", Hibernate.LONG)
				.addScalar("isIncrease", Hibernate.STRING)
				.addScalar("isDecrease", Hibernate.STRING)
				.addScalar("isLow", Hibernate.STRING)
				.addScalar("processor", Hibernate.STRING)
				.addScalar("disposeTime", Hibernate.STRING)
				.addScalar("fullDate", Hibernate.STRING)
				.addScalar("changeRatio", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list();

		return list;
	}

	/**
	 * 流量告警历史数据查询sql
	 * 
	 * @param condition
	 * @return
	 */
	private String bulidHistorySql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();

		if ("tbAlarmGprsTotalFlush".equalsIgnoreCase(condition.get("tableName")
				.toString())) {
			sql.append(" select ");
			sql.append(" case intRaitype when 1 then '全网GPRS'");
			sql.append(" else '全网TD' end");
		} else {
			sql.append(" select ").append(condition.get("columnName"));
		}
		sql.append(" typeName,nmFlush changeFlush,");
		sql.append(condition.get("typeId")).append(" typeId,");
		sql.append(" isnull(nmFlushChange,0)*100 changeRatio,");
		// sql.append(" string((isnull(nmFlushChange,0)*100))+'%' changeRatio,");
		sql.append(" case isIncrease when 1 then '是'");
		sql.append(" else '否' end isIncrease ,");
		sql.append(" case isLow when 1 then '是' else '否' end isLow,");
		sql.append(" case isDecrease when 1 then '是' else '否' end isDecrease,");
		sql.append(" vcUsers processor,");
		sql.append(" convert(varchar(19),dtETime) disposeTime,");
		sql.append(" convert(varchar(10),convert(datetime,");
		sql.append(" string(intYear,'-',intMonth,'-',intDay))) fullDate");
		sql.append(" from ").append(condition.get("tableName"));
		sql.append(" where 1=1");

		if ("2".equals(condition.get("warmOK"))) {
			sql.append(" and vcUsers <> null");
			sql.append(" and disposeTime <> null");
		} else if ("3".equals(condition.get("warmOK"))) {
			sql.append(" and vcUsers = null");
			sql.append(" and disposeTime = null");
		} else {

		}

		if ("2".equals(condition.get("flushOK"))) {
			sql.append(" and isIncrease = '是'");
		} else if ("3".equals(condition.get("flushOK"))) {
			sql.append(" and isDecrease = '是'");
		} else {
			sql.append(" and isLow = '是'");
		}

		sql.append(" and fullDate>='").append(condition.get("startDate"))
				.append("'");
		sql.append(" and fullDate<='").append(condition.get("endDate"))
				.append("'");

		if ("tbAlarmGprsTotalFlush".equalsIgnoreCase(condition.get("tableName")
				.toString())) {
			if ("TD".equalsIgnoreCase(condition.get("typeTD").toString())) {
				sql.append(" and intRaitype=2");
			} else {
				sql.append(" and intRaitype=1");
			}
		}
		return sql.toString();
	}

	/**
	 * 得到所查询数据的总记录数
	 * 
	 * @param condition
	 * @return
	 */
	public int getTotalCount(Map<String, Object> condition) {
		String sql = bulidHistorySql(condition);

		sql = " select count(1) from  ( " + sql + " ) as totalCount";

		Object total = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.parseInt(total.toString());
	}

	@SuppressWarnings("unchecked")
	public List<AlarmBean> queryHistory(Map<String, Object> condition) {
		String sql = bulidHistorySql(condition);

		sql += " order by fullDate desc";

		List<AlarmBean> list = this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("typeName", Hibernate.STRING)
				// .addScalar("alarmId",Hibernate.INTEGER)
				.addScalar("changeFlush", Hibernate.LONG)
				.addScalar("isIncrease", Hibernate.STRING)
				.addScalar("isDecrease", Hibernate.STRING)
				.addScalar("isLow", Hibernate.STRING)
				.addScalar("processor", Hibernate.STRING)
				.addScalar("disposeTime", Hibernate.STRING)
				.addScalar("fullDate", Hibernate.STRING)
				.addScalar("changeRatio", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.list();

		return list;
	}

	public int getTotalCountDay(Map<String, Object> condition) {
		String sql = bulidHistorySqlDay(condition);

		sql = " select count(1) from  ( " + sql + " ) as totalCount";

		Object total = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.parseInt(total.toString());
	}

	public int getTotalCountDayConfig(Map<String, Object> condition) {
		String sql = bulidHistorySqlDayConfig(condition);

		sql = " select count(1) from  ( " + sql + " ) as totalCount";

		Object total = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.parseInt(total.toString());
	}

	private String bulidHistorySqlDayConfig(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();

		// sql.append(" select ").append(condition.get("columnName"));
		if ("tbAlarmGprsTotalFlush".equalsIgnoreCase(condition.get("tableName")
				.toString())) {
			sql.append(" select ");
			sql.append(" case intRaitype when 1 then '全网GPRS'");
			sql.append(" else '全网TD' end");
		} else {
			sql.append(" select ").append(condition.get("columnName"));
		}
		sql.append(" typeName,nmFlush changeFlush,");
		sql.append(condition.get("typeId")).append(" typeId,");
		sql.append(" isnull(nmFlushChange,0)*100 changeRatio,");

		if ("add".equals(condition.get("typeName").toString().trim())) {
			sql.append(" case isIncrease when 1 then '是'");
			sql.append(" else '否' end isIncrease ,");
		} else if ("mv".equals(condition.get("typeName").toString().trim())) {
			sql.append(" case isDecrease when 1 then '是' else '否' end isDecrease,");
		} else if ("warm".equals(condition.get("typeName").toString().trim())) {
			sql.append(" case isLow when 1 then '是' else '否' end isLow,");
		}

		sql.append(" vcUsers processor,");
		sql.append(" convert(varchar(19),dtETime) disposeTime,");
		sql.append(" convert(varchar(10),convert(datetime,");
		sql.append(" string(intYear,'-',intMonth,'-',intDay))) fullDate");
		sql.append(" from ").append(condition.get("tableName"));
		sql.append(" where 1=1");
		sql.append(" and vcUsers = null");
		sql.append(" and disposeTime = null");

		if ("add".equals(condition.get("typeName").toString().trim())) {
			sql.append(" and isIncrease = '是'");
		} else if ("mv".equals(condition.get("typeName").toString().trim())) {
			sql.append(" and isDecrease = '是'");
		} else if ("warm".equals(condition.get("typeName").toString().trim())) {
			sql.append(" and isLow = '是'");
		}

		sql.append(" and fullDate='").append(condition.get("startDate"))
				.append("'");

		if ("tbAlarmGprsTotalFlush".equalsIgnoreCase(condition.get("tableName")
				.toString())) {
			if ("TD".equalsIgnoreCase(condition.get("typeTD").toString())) {
				sql.append(" and intRaitype=2");
			} else {
				sql.append(" and intRaitype=1");
			}
		}

		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	public List<AlarmBean> queryHistoryDay(Page<AlarmBean> page,
			Map<String, Object> condition) {
		String sql = bulidHistorySqlDay(condition);

		sql += " order by fullDate desc";

		List<AlarmBean> list = this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("typeName", Hibernate.STRING)
				.addScalar("typeId", Hibernate.STRING)
				.addScalar("changeFlush", Hibernate.LONG)
				.addScalar("isIncrease", Hibernate.STRING)
				.addScalar("isDecrease", Hibernate.STRING)
				.addScalar("isLow", Hibernate.STRING)
				.addScalar("processor", Hibernate.STRING)
				.addScalar("disposeTime", Hibernate.STRING)
				.addScalar("fullDate", Hibernate.STRING)
				.addScalar("changeRatio", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<AlarmBean> queryHistoryDayConfig(Page<AlarmBean> page,
			Map<String, Object> condition) {
		String sql = bulidHistorySqlDayConfig(condition);

		sql += " order by " + page.getOrderBy() + " " + page.getOrder();

		List<AlarmBean> list = null;

		if ("add".equals(condition.get("typeName"))) {
			list = this
					.getSession()
					.createSQLQuery(sql)
					.addScalar("typeName", Hibernate.STRING)
					.addScalar("typeId", Hibernate.STRING)
					.addScalar("changeFlush", Hibernate.LONG)
					.addScalar("isIncrease", Hibernate.STRING)
					.addScalar("processor", Hibernate.STRING)
					.addScalar("disposeTime", Hibernate.STRING)
					.addScalar("fullDate", Hibernate.STRING)
					.addScalar("changeRatio", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers.aliasToBean(AlarmBean.class))
					.setFirstResult(page.getFirst())
					.setMaxResults(page.getPageSize()).list();
		} else if ("mv".equals(condition.get("typeName"))) {
			list = this
					.getSession()
					.createSQLQuery(sql)
					.addScalar("typeName", Hibernate.STRING)
					.addScalar("typeId", Hibernate.STRING)
					.addScalar("changeFlush", Hibernate.LONG)
					.addScalar("isDecrease", Hibernate.STRING)
					.addScalar("processor", Hibernate.STRING)
					.addScalar("disposeTime", Hibernate.STRING)
					.addScalar("fullDate", Hibernate.STRING)
					.addScalar("changeRatio", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers.aliasToBean(AlarmBean.class))
					.setFirstResult(page.getFirst())
					.setMaxResults(page.getPageSize()).list();
		} else if ("warm".equals(condition.get("typeName"))) {
			list = this
					.getSession()
					.createSQLQuery(sql)
					.addScalar("typeName", Hibernate.STRING)
					.addScalar("typeId", Hibernate.STRING)
					.addScalar("changeFlush", Hibernate.LONG)
					.addScalar("isLow", Hibernate.STRING)
					.addScalar("processor", Hibernate.STRING)
					.addScalar("disposeTime", Hibernate.STRING)
					.addScalar("fullDate", Hibernate.STRING)
					.addScalar("changeRatio", Hibernate.INTEGER)
					.setResultTransformer(
							Transformers.aliasToBean(AlarmBean.class))
					.setFirstResult(page.getFirst())
					.setMaxResults(page.getPageSize()).list();
		}

		return list;
	}

	private String bulidHistorySqlDay(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();

		// sql.append(" select ").append(condition.get("columnName"));
		if ("tbAlarmGprsTotalFlush".equalsIgnoreCase(condition.get("tableName")
				.toString())) {
			sql.append(" select ");
			sql.append(" case intRaitype when 1 then '全网GPRS'");
			sql.append(" else '全网TD' end");
		} else {
			sql.append(" select ").append(condition.get("columnName"));
		}
		sql.append(" typeName,nmFlush changeFlush,");
		sql.append(condition.get("typeId")).append(" typeId,");
		sql.append(" isnull(nmFlushChange,0)*100 changeRatio,");
		sql.append(" case isIncrease when 1 then '是'");
		sql.append(" else '否' end isIncrease ,");
		sql.append(" case isLow when 1 then '是' else '否' end isLow,");
		sql.append(" case isDecrease when 1 then '是' else '否' end isDecrease,");
		sql.append(" vcUsers processor,");
		sql.append(" convert(varchar(19),dtETime) disposeTime,");
		sql.append(" convert(varchar(10),convert(datetime,");
		sql.append(" string(intYear,'-',intMonth,'-',intDay))) fullDate");
		sql.append(" from ").append(condition.get("tableName"));
		sql.append(" where 1=1");

		if ("2".equals(condition.get("warmOK"))) {
			sql.append(" and vcUsers <> null");
			sql.append(" and disposeTime <> null");
		} else if ("3".equals(condition.get("warmOK"))) {
			sql.append(" and vcUsers = null");
			sql.append(" and disposeTime = null");
		} else {

		}

		sql.append(" and fullDate='").append(condition.get("startDate"))
				.append("'");

		if ("tbAlarmGprsTotalFlush".equalsIgnoreCase(condition.get("tableName")
				.toString())) {
			if ("TD".equalsIgnoreCase(condition.get("typeTD").toString())) {
				sql.append(" and intRaitype=2");
			} else {
				sql.append(" and intRaitype=1");
			}
		}

		return sql.toString();
	}

	private String bulidHistorySqlDayAdd(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();

		sql.append(" select ").append(condition.get("columnName"));
		sql.append(" typeName,nmFlush changeFlush,");
		sql.append(condition.get("typeId")).append(" typeId,");
		sql.append(" isnull(nmFlushChange,0)*100 changeRatio,");
		sql.append(" case isIncrease when 1 then '是'");
		sql.append(" else '否' end isIncrease ,");
		sql.append(" case isLow when 1 then '是' else '否' end isLow,");
		sql.append(" case isDecrease when 1 then '是' else '否' end isDecrease,");
		sql.append(" vcUsers processor,");
		sql.append(" convert(varchar(19),dtETime) disposeTime,");
		sql.append(" convert(varchar(10),convert(datetime,");
		sql.append(" string(intYear,'-',intMonth,'-',intDay))) fullDate");
		sql.append(" from ").append(condition.get("tableName"));
		sql.append(" where 1=1");
		sql.append(" and fullDate='").append(condition.get("startDate"))
				.append("'");

		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	public List<AlarmBean> queryHistoryDay(Map<String, Object> condition) {
		String sql = bulidHistorySqlDay(condition);

		sql += " order by fullDate desc";

		List<AlarmBean> list = this
				.getSession()
				.createSQLQuery(sql)
				.addScalar("typeName", Hibernate.STRING)
				// .addScalar("alarmId",Hibernate.INTEGER)
				.addScalar("changeFlush", Hibernate.LONG)
				.addScalar("isIncrease", Hibernate.STRING)
				.addScalar("isDecrease", Hibernate.STRING)
				.addScalar("isLow", Hibernate.STRING)
				.addScalar("processor", Hibernate.STRING)
				.addScalar("disposeTime", Hibernate.STRING)
				.addScalar("fullDate", Hibernate.STRING)
				.addScalar("changeRatio", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.list();

		return list;
	}

	public AlarmBean initQuery2(Map<String, Object> condition) {
		String sqlLow = buildLowSql(condition);
		String sqlInc = buildIncSql(condition);
		String sqlDec = buildDecSql(condition);

		AlarmBean low = (AlarmBean) this
				.getSession()
				.createSQLQuery(sqlLow)
				.addScalar("countLow", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.uniqueResult();
		AlarmBean inc = (AlarmBean) this
				.getSession()
				.createSQLQuery(sqlInc)
				.addScalar("countIncrease", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.uniqueResult();
		AlarmBean dec = (AlarmBean) this
				.getSession()
				.createSQLQuery(sqlDec)
				.addScalar("countDecrease", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(AlarmBean.class))
				.uniqueResult();

		AlarmBean bean = new AlarmBean(condition.get("typeName").toString(),
				(null == low) ? 0 : low.getCountLow(), (null == inc) ? 0
						: inc.getCountIncrease(), (null == dec) ? 0
						: dec.getCountDecrease());

		return bean;

	}

	private String buildLowSql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();
		sql.append(buildInitSql2("isLow", "countLow", condition));
		return sql.toString();
	}

	private String buildIncSql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();
		sql.append(buildInitSql2("isIncrease", "countIncrease", condition));
		return sql.toString();
	}

	private String buildDecSql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();
		sql.append(buildInitSql2("isDecrease", "countDecrease", condition));
		return sql.toString();
	}

	private String buildInitSql2(String colName, String colAttrName,
			Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(").append(colName).append(") ");
		sql.append(colAttrName);
		sql.append(" from ").append(condition.get("tableName"));
		sql.append(" where 1=1 ");
		sql.append(" and ").append(colName).append(" = 1");
		sql.append(" and intYear = ").append(condition.get("intYear"));
		sql.append(" and intMonth = ").append(condition.get("intMonth"));
		sql.append(" and intDay = ").append(condition.get("intDay"));

		if ("tbAlarmGprsTotalFlush".equalsIgnoreCase(condition.get("tableName")
				.toString())) {
			if ("全网GPRS".equalsIgnoreCase(condition.get("typeName").toString())) {
				sql.append(" and intRaitype = 1");
			} else {
				sql.append(" and intRaitype = 2");
			}
		}

		return sql.toString();
	}

	public void doCancelWarn(Map<String, Object> condition) {
		String sql = updateWarInfoSql(condition);

		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	private String updateWarInfoSql(Map<String, Object> condition) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update ").append(condition.get("tableName"));
		sql.append(" set dtETime='").append(Common.getNow());
		sql.append("',vcUsers='").append(condition.get("user"));
		sql.append("',vcNote='").append(condition.get("alarmInfo"));
		sql.append("' where ").append(condition.get("columnName"));
		sql.append(" in ( ").append(condition.get("typeName")).append(" )");
		sql.append(" and convert(datetime,string(intYear,");
		sql.append(" '-',intMonth,'-',intDay)) in(");
		sql.append(condition.get("warmDate"));
		sql.append(" )");
		return sql.toString();
	}
}
