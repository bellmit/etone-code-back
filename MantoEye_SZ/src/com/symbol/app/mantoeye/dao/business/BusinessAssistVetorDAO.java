package com.symbol.app.mantoeye.dao.business;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 附规则
 * 
 * @author rankin
 * 
 */
@Repository
public class BusinessAssistVetorDAO extends
		HibernateDao<FtbBusinessAssistVetor, Integer> {

	/**
	 * 保存
	 * 
	 * @param entiy
	 */
	public void saveEntity(FtbBusinessAssistVetor entity, Integer busiId) {
		String sql = " insert into ftbBusinessAssistVetor(nmBussinessId,vcServerIp,intPort,vcUrl,vcUserAgent,vcApn) values (?,?,?,?,?,?)";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, busiId);
		query.setParameter(1, entity.getVcServerIp());
		query.setParameter(2, entity.getIntPort());
		query.setParameter(3, entity.getVcUrl());
		query.setParameter(4, entity.getVcUserAgent());
		query.setParameter(5, entity.getVcApn());

		query.executeUpdate();
	}

	public boolean isAllPropertyUnique(FtbBusinessAssistVetor entity,
			Integer busiId, Integer oldkey) {

		String sql = "select nmBusinessAssistVetorId from  ftbBusinessAssistVetor where 1=1 ";
		if (entity.getVcServerIp() != null) {
			sql = sql + " and vcServerIp = '" + entity.getVcServerIp() + "' ";
		} else {
			sql = sql + " and vcServerIp is null ";
		}

		if (entity.getIntPort() != null && entity.getIntPort() > 0) {
			sql = sql + " and intPort = " + entity.getIntPort() + " ";
		} else {
			sql = sql + " and intPort is null ";
		}

		if (entity.getVcUrl() != null) {
			sql = sql + " and vcUrl = '" + entity.getVcUrl() + "' ";
		} else {
			sql = sql + " and vcUrl is null ";
		}

		if (entity.getVcUserAgent() != null) {
			sql = sql + " and vcUserAgent = '" + entity.getVcUserAgent() + "' ";
		} else {
			sql = sql + " and vcUserAgent is null ";
		}

		if (entity.getVcApn() != null) {
			sql = sql + " and vcApn = '" + entity.getVcApn() + "' ";
		} else {
			sql = sql + " and vcApn is null ";
		}

		sql = sql + " and nmBussinessId = " + busiId;

		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			if (list.size() == 1 && oldkey > 0) {
				BigDecimal kkk = new BigDecimal(oldkey);
				if (kkk.equals((BigDecimal) list.get(0))) {// 编辑时允许编辑后的和编辑前的一样
					return true;
				} else {
					return false;
				}
			} else {// 不唯一
				return false;
			}
		} else {// 唯一
			return true;
		}

	}

	public String getBusiNameByIds(Long[] ids) {
		String names = "";
		String iid = "";
		for (Long l : ids) {
			iid = iid + l + ",";
		}
		if (iid.endsWith(",")) {
			iid = iid.substring(0, iid.length() - 1);
		}
		String sql = "select b.vcBussinessName from ftbBusinessAssistVetor a,dtbBusinessSite b where a.nmBussinessId = b.nmBussinessId and a.nmBusinessAssistVetorId in ("
				+ iid + ") order by b.nmBussinessId";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		//query.setParameter(0, ids);
		List nameList = query.list();
		if (nameList != null && nameList.size() > 0) {
			for (int i = 0; i < nameList.size(); i++) {

				names = names + nameList.get(i) + ",";
			}
			names = names.substring(0, names.length() - 1);
		}
		return names;
	}
}
