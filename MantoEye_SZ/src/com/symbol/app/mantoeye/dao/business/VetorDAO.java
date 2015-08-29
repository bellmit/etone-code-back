package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.business.FtbMainAssistVetorNew;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.app.mantoeye.entity.business.FtbMainAssistVetor;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 规则配置
 * 
 * @author rankin
 * 
 */
@Repository
public class VetorDAO extends HibernateDao<FtbMainAssistVetor, Integer> {

	/**
	 * 通过主规则ID获取相关联的辅规则ID
	 * 
	 * @param mainid
	 * @return
	 */
	public Integer[] findAssistIdByMainId(Integer mainid) {
		Integer[] array;
		String sql = "from FtbMainAssistVetor where ftbBusinessMainVetor.nmBusinessMainVetorId = ? ";
		Query query = this.getSession().createQuery(sql);
		query.setParameter(0, mainid);
		List<FtbMainAssistVetor> list = query.list();
		if (list != null && list.size() > 0) {
			array = new Integer[list.size()];
			for (int i = 0; i < list.size(); i++) {
				array[i] = list.get(i).getFtbBusinessAssistVetor()
						.getNmBusinessAssistVetorId();
			}
		} else {
			array = new Integer[] { 0 };
		}
		return array;
	}

	public void addAssociate(Integer ikid, Integer asid) {
		String sql = "insert into  ftbMainAssistVetor(nmBusinessMainVetorId,nmBusinessAssistVetorId) values(?,?) ";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, ikid);
		query.setParameter(1, asid);
		query.executeUpdate();
	}

	public void deleteAssociate(Integer ikid, Integer asid) {
		String sql = "delete  ftbMainAssistVetor where nmBusinessMainVetorId = ? and nmBusinessAssistVetorId=? ";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, ikid);
		query.setParameter(1, asid);
		query.executeUpdate();
	}

	/**
	 * 本機，81都可以運行，但是43上運行不了，改成使用HIBERNATE的方式，可以了，所以這個方法現在沒有再用
	 * 
	 * @param page
	 * @param filters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<FtbMainAssistVetor> findForQuery(Page<FtbMainAssistVetor> page,
			List<PropertyFilter> filters) {

		String sql = bulidSql(filters);

		sql += " order by businessName desc";

		List list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list();

		Page<FtbMainAssistVetor> newPage = new Page<FtbMainAssistVetor>();
		newPage.setResult(buildTrendBeanList(list));
		newPage.setPageNo(page.getPageNo());

		return newPage;

	}

	private List<FtbMainAssistVetor> buildTrendBeanList(List list) {
		List<FtbMainAssistVetor> nList = new ArrayList<FtbMainAssistVetor>();
		FtbMainAssistVetor b = null;
		FtbBusinessAssistVetor ftbBusinessAssistVetor = null;
		FtbBusinessMainVetor ftbBusinessMainVetor = null;
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] o = (Object[]) list.get(i);
				b = new FtbMainAssistVetor();
				ftbBusinessAssistVetor = new FtbBusinessAssistVetor();

				if (null != o[1] && !"".equals(o[1].toString().trim())) {
					ftbBusinessAssistVetor.setIntPort(Common.StringToInt(o[1]
							.toString()));
				} else {
					ftbBusinessAssistVetor.setIntPort(null);
				}

				ftbBusinessAssistVetor.setVcApn(null == o[2]
						|| "".equals(o[2].toString()) ? null : o[2].toString());
				ftbBusinessAssistVetor.setVcUrl(null == o[0]
						|| "".equals(o[0].toString()) ? null : o[0].toString());
				ftbBusinessAssistVetor.setVcUserAgent(null == o[3]
						|| "".equals(o[3].toString()) ? null : o[3].toString());

				ftbBusinessMainVetor = new FtbBusinessMainVetor();
				ftbBusinessMainVetor.setVcServerIp(null == o[4]
						|| "".equals(o[4].toString()) ? null : o[4].toString());
				DtbBusinessSite s = new DtbBusinessSite();
				s.setVcBussinessName(null == o[5] || "".equals(o[5].toString()) ? null
						: o[5].toString());
				ftbBusinessMainVetor.setDtbBusinessSite(s);

				b.setFtbBusinessAssistVetor(ftbBusinessAssistVetor);
				b.setFtbBusinessMainVetor(ftbBusinessMainVetor);
				b.setNmMainAssistVetorId(Common.StringToInteger(null == o[6]
						|| "".equals(o[6].toString()) ? null : o[6].toString()));
				nList.add(b);
			}
		}

		return nList;
	}

	private String bulidSql(List<PropertyFilter> filters) {

		StringBuffer sql = new StringBuffer(200);

		sql.append(" select fbav.vcUrl as assistruleUrl,");
		// sql.append(" string(fbav.intPort)+'' as assistrulePort,");
		sql.append(" fbav.intPort as assistrulePort,");
		sql.append(" fbav.vcApn as assistruleApn,");
		sql.append(" fbav.vcUserAgent as assistruleUserAgent,");
		sql.append(" fbmv.vcServerIp as mainruleIp,");
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
				String columValue = pf.getValue().toString().toLowerCase();

				if (pftn.contains("vcBussinessName")) {
					sql.append(" and lower(businessName) like '%");
				} else if (pftn.contains("vcServerIp")) {
					sql.append(" and lower(mainruleIp) like '%");
				} else if (pftn.contains("vcUrl")) {
					sql.append(" and lower(assistruleUrl) like '%");
				} else if (pftn.contains("vcApn")) {
					sql.append(" and lower(assistruleApn) like '%");
				} else if (pftn.contains("vcUserAgent")) {
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
