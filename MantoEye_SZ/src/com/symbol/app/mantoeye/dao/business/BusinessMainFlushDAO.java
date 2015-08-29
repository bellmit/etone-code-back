package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.bean.FlushRoleBean;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.entity.business.VstatDayMainRuleFlush;
import com.symbol.app.mantoeye.entity.business.VstatDayMainRuleFlushId;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

/**
 * 主规则流量碰撞率统计
 * 
 * @author rankin
 * 
 */
@Repository
public class BusinessMainFlushDAO extends
		HibernateQueryDAO<VstatDayMainRuleFlush, VstatDayMainRuleFlushId> {
	public Page<VstatDayMainRuleFlush> findFromView(
			final Page<VstatDayMainRuleFlush> page, Date date, String businame,
			String sip) {
		String sql = "from VstatDayMainRuleFlush where id.dtStatTime = ?  ";
		if (Common.judgeString(businame)) {
			sql = sql + " and id.nmBusinessMainVetorId in ("
					+ getMainMapIds(businame, 1) + ") ";
		}
		if (Common.judgeString(sip)) {
			sql = sql + " and id.nmBusinessMainVetorId in ("
					+ getMainMapIds(sip, 2) + ") ";
		}
		if (page.getOrderBy() != null && page.getOrderBy() != "") {
			sql = sql + " order by id." + page.getOrderBy() + " "
					+ page.getOrder();
		} else {
			sql = sql + " order by id.flushRate";
		}

		logger.info("DATE:" + date);
		Query q = this.getSession().createQuery(sql);
		q.setParameter(0, date);

		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());
		List<VstatDayMainRuleFlush> list = q.list();
		logger.info("SIZE:" + list.size());
		page.setVresult(list);
		page.setResult(list);
		return page;
	}

	public int getCount(Date date, String businame, String sip) {
		logger.info("COUNT ** DATE:" + date);
		String sql = "from  VstatDayMainRuleFlush where id.dtStatTime = ?  ";
		if (Common.judgeString(businame)) {
			sql = sql + " and id.nmBusinessMainVetorId in ("
					+ getMainMapIds(businame, 1) + ") ";
		}
		if (Common.judgeString(sip)) {
			sql = sql + " and id.nmBusinessMainVetorId in ("
					+ getMainMapIds(sip, 2) + ") ";
		}
		Query q = this.getSession().createQuery(sql);
		q.setParameter(0, date);

		List list = q.list();
		int count = 0;
		if (list != null && list.size() > 0) {
			count = list.size();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<FlushRoleBean> findAllFromView(String date, String businame,
			String sip) {
		String sql = buildSql(date, businame, sip);

		sql += " order by fullDate";

		List<FlushRoleBean> list = this.getSession().createSQLQuery(sql)
				.addScalar("fulldate", Hibernate.STRING).addScalar(
						"vcBussinessName", Hibernate.STRING).addScalar("flush",
						Hibernate.LONG).addScalar("lastflush", Hibernate.LONG)
				.addScalar("flushRate", Hibernate.DOUBLE).addScalar(
						"conflictCount", Hibernate.LONG).addScalar(
						"lastconflictCount", Hibernate.LONG).addScalar("ip",
						Hibernate.STRING).addScalar("conflictCountRate",
						Hibernate.DOUBLE).setResultTransformer(
						Transformers.aliasToBean(FlushRoleBean.class)).list();

		return list;
		// return buildNewList(list);
	}

	// public List<VstatDayMainRuleFlush> findAllFromView(Date date,
	// String businame, String sip) {
	// String sql = "from VstatDayMainRuleFlush where id.dtStatTime = ? ";
	// if (Common.judgeString(businame)) {
	// sql = sql + " and id.nmBusinessMainVetorId in ("
	// + getMainMapIds(businame, 1) + ") ";
	// }
	// if (Common.judgeString(sip)) {
	// sql = sql + " and id.nmBusinessMainVetorId in ("
	// + getMainMapIds(sip, 2) + ") ";
	// }
	// sql = sql + "order by id.flushRate desc ";
	// Query q = this.getSession().createQuery(sql);
	// q.setParameter(0, date);
	// List<VstatDayMainRuleFlush> list = q.list();
	//		
	// return list;
	// }

	private List<VstatDayMainRuleFlush> buildNewList(List list) {
		List<VstatDayMainRuleFlush> newList = new ArrayList<VstatDayMainRuleFlush>();
		VstatDayMainRuleFlush b = null;
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] o = (Object[]) list.get(i);
				b = new VstatDayMainRuleFlush();

			}
		}

		return newList;
	}

	private String buildSql(String date, String businame, String sip) {
		StringBuffer sql = new StringBuffer();

		sql.append(" select convert(varchar(10),vsmrf.dtStatTime) fulldate ,");
		sql.append(" fmv.nmBussinessId businessId,");
		sql.append(" dbs.vcBussinessName vcBussinessName,");
		sql.append(" vsmrf.nmFlush flush,vsmrf.lnmFlush lastflush,");
		sql.append(" vsmrf.flushRate flushRate,vsmrf.nmConflictCount");
		sql.append(" conflictCount,fmv.vcServerIp ip,");
		sql.append(" vsmrf.LnmConflictCount lastconflictCount,");
		sql.append(" vsmrf.ConflictCountRate conflictCountRate ");
		sql.append(" from vStatDayMainRuleFlush vsmrf");
		sql.append(" inner join ftbBusinessMainVetor fmv on");
		sql.append(" vsmrf.nmBusinessMainVetorId = fmv.nmBusinessMainVetorId");
		sql.append(" inner join dtbBusinessSite dbs");
		sql.append(" on fmv.nmBussinessId = dbs.nmBussinessId");
		sql.append(" inner join dtbBusinessCompany dbc ");
		sql.append(" on dbs.nmCompanyId=dbc.nmCompanyId");
		sql.append(" where 1=1 ");

		if (Common.judgeString(date)) {
			sql.append(" and fullDate='").append(date).append("'");
		}
		if (Common.judgeString(date)) {
			sql.append(" and vcBussinessName like '%").append(businame);
			sql.append("%'");
		}
		if (Common.judgeString(date)) {
			sql.append(" and fmv.vcServerIp like '%").append(sip).append("%'");
		}

		return sql.toString();
	}

	private String getMainMapIds(String filter, int type) {
		String sql = "select  nmBusinessMainVetorId  from FtbBusinessMainVetor where 1=1 ";
		if (type == 1) {
			sql = sql
					+ " and nmBussinessId in (select nmBussinessId from DtbBusinessSite where vcBussinessName like '%"
					+ filter + "%') ";
		} else {
			sql = sql + " and vcServerIp like '%" + filter + "%'";
		}
		return sql;
	}
}
