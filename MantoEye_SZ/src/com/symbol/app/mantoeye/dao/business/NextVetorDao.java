package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.business.FtbMainAssistVetorNew;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;

@Repository
public class NextVetorDao extends CommonQueryDAO {

	public Page<FtbMainAssistVetorNew> findForQuery(
			Page<FtbMainAssistVetorNew> page, List<PropertyFilter> filters) {
		String sql = bulidSql(filters);

		sql += " order by businessName desc";

		List list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();

		Page<FtbMainAssistVetorNew> newPage = new Page<FtbMainAssistVetorNew>();
		newPage.setResult(buildTrendBeanList(list));
		newPage.setPageNo(page.getPageNo());

		return newPage;
	}

	private List<FtbMainAssistVetorNew> buildTrendBeanList(List list) {
		List<FtbMainAssistVetorNew> nList = new ArrayList<FtbMainAssistVetorNew>();
		FtbMainAssistVetorNew b = null;
		FtbBusinessAssistVetor ftbBusinessAssistVetor = null;
		FtbBusinessMainVetor ftbBusinessMainVetor = null;
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] o = (Object[]) list.get(i);
				b = new FtbMainAssistVetorNew();
				ftbBusinessAssistVetor = new FtbBusinessAssistVetor();
				ftbBusinessAssistVetor.setIntPort(Common.StringToInt(o[1]
						.toString()));
				ftbBusinessAssistVetor.setVcApn(o[2].toString());
				ftbBusinessAssistVetor.setVcUrl(o[0].toString());
				ftbBusinessAssistVetor.setVcUserAgent(o[3].toString());

				ftbBusinessMainVetor = new FtbBusinessMainVetor();
				ftbBusinessMainVetor.setVcServerIp(o[4].toString());
				DtbBusinessSite s = new DtbBusinessSite();
				s.setVcBussinessName(o[5].toString());
				ftbBusinessMainVetor.setDtbBusinessSite(s);

				b.setFtbBusinessAssistVetor(ftbBusinessAssistVetor);
				b.setFtbBusinessMainVetor(ftbBusinessMainVetor);
				b.setNmMainAssistVetorId(Common
						.StringToInteger(o[6].toString()));

			}
		}

		return nList;
	}

	private String bulidSql(List<PropertyFilter> filters) {

		StringBuffer sql = new StringBuffer(200);

		sql.append(" select fbav.vcUrl as assistruleUrl,");
		sql.append(" fbav.intPort as assistrulePort,");
		sql.append(" fbav.vcApn as assistruleApn,");
		sql.append(" fbav.vcUserAgent as assistruleUserAgent,");
		sql.append(" fbmv.vcServerIp as mainruleIp,");
		// sql
		// .append(" select convert(varchar(200),isnull(fbav.vcUrl,'')) as assistruleUrl,");
		// sql.append(" fbav.intPort as assistrulePort,");
		// sql.append(" fbav.vcApn as assistruleApn,");
		// sql.append(" fbav.vcUserAgent as assistruleUserAgent,");
		// sql
		// .append(" convert(varchar(200),isnull(fbmv.vcServerIp,'')) as mainruleIp,");
		sql.append(" dbs.vcBussinessName as businessName,");
		sql.append(" fmav.nmMainAssistVetorId as mainAssistVetorId");
		sql.append(" from ftbBusinessAssistVetor fbav");
		sql.append(" inner join ftbMainAssistVetor fmav");
		sql.append(" on fbav.nmBusinessAssistVetorId =");
		sql.append(" fmav.nmBusinessAssistVetorId");
		sql.append(" inner join ftbBusinessMainVetor fbmv");
		sql.append(" on fmav.nmBusinessMainVetorId =");
		sql.append(" fbmv.nmBusinessMainVetorId");
		sql.append(" inner join dtbBusinessSite dbs");
		sql.append(" on fbav.nmBussinessId = dbs.nmBussinessId");
		// sql.append(" inner join dtbBusinessCompany dbc");
		// sql.append(" on dbs.nmCompanyId = dbc.nmCompanyId");
		sql.append(" where 1 = 1");

		if (null != filters && !filters.isEmpty()) {
			for (PropertyFilter pf : filters) {

				String pftn = pf.getPropertyName();
				String pftnn = pftn.substring(pftn.indexOf(".") + 1);

				String columName = pftnn;// .split(":")[1];
				String columValue = pf.getValue().toString().toLowerCase();

				if ("vcBussinessName".toUpperCase().equals(
						columName.toUpperCase())) {
					sql.append(" and lower(businessName) like '%");
				} else if ("vcServerIp".toUpperCase().equals(
						columName.toUpperCase())) {
					sql.append(" and lower(mainruleIp) like '%");
				} else if ("vcUrl".toUpperCase()
						.equals(columName.toUpperCase())) {
					sql.append(" and lower(assistruleUrl) like '%");
				} else if ("vcApn".toUpperCase()
						.equals(columName.toUpperCase())) {
					sql.append(" and lower(assistruleApn) like '%");
				} else {
					sql.append(" and lower(assistruleUserAgent) like '%");
				}

				sql.append(columValue);
				sql.append("%'");
			}
		}

		return sql.toString();
	}

	public int getTotalCount(List<PropertyFilter> filters) {
		String sql = " select count(1) from (";
		sql += bulidSql(filters);
		sql += " ) as total";

		Object o = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.parseInt(o.toString());
	}
}
