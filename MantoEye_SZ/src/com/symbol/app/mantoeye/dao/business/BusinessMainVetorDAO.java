package com.symbol.app.mantoeye.dao.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class BusinessMainVetorDAO extends
		HibernateDao<FtbBusinessMainVetor, Integer> {

	/**
	 * 保存
	 * 
	 * @param entiy
	 */
	public void saveEntity(FtbBusinessMainVetor entity, Integer busiId) {
		String sql = " insert into ftbBusinessMainVetor(nmBussinessId,vcServerIp,intPort,vcUrl,vcUserAgent,vcApn) values (?,?,?,?,?,?)";
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

		String sql = "select nmBusinessMainVetorId from  ftbBusinessMainVetor where 1=1 ";

		sql = sql + " and vcServerIp = '" + entity.getVcServerIp() + "' ";

		/*
		 * if(entity.getVcServerIp()!=null){ sql =sql +
		 * " and vcServerIp = '"+entity.getVcServerIp()+"' "; }else{ sql =sql +
		 * " and vcServerIp is null "; }
		 * 
		 * if(entity.getIntPort()!=null&&entity.getIntPort()>0){ sql =sql +
		 * " and intPort = "+entity.getIntPort()+" "; }else{ sql =sql +
		 * " and intPort is null "; }
		 * 
		 * if(entity.getVcUrl()!=null){ sql =sql +
		 * " and vcUrl = '"+entity.getVcUrl()+"' "; }else{ sql =sql +
		 * " and vcUrl is null "; }
		 * 
		 * if(entity.getVcUserAgent()!=null){ sql =sql +
		 * " and vcUserAgent = '"+entity.getVcUserAgent()+"' "; }else{ sql =sql
		 * + " and vcUserAgent is null "; }
		 * 
		 * if(entity.getVcApn()!=null){ sql =sql +
		 * " and vcApn = '"+entity.getVcApn()+"' "; }else{ sql =sql +
		 * " and vcApn is null "; }
		 */

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

	public Map<String,String> getBusiNameByIds(String ids) {
		Map<String,String> names = new HashMap<String,String>();
		Object [] objs;
		String sql = "select b.nmBussinessId,b.vcBussinessName from dtbBusinessSite b where b.nmBussinessId in ("
			+ ids + ") order by b.nmBussinessId";

		SQLQuery query = this.getSession().createSQLQuery(sql);
		List nameList = query.list();
		if (nameList != null && nameList.size() > 0) {		
			for (int i = 0; i < nameList.size(); i++) {
				objs =(Object [])nameList.get(i);
				names.put(objs[0]+"", objs[1]+"");
			}
		}
		return names;
	}
}
