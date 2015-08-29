package com.symbol.app.mantoeye.dao.businessAnalyse;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class CompetitorBusinessConfigureDAO extends HibernateDao{
	
	public void saveBussList(Long businessId,Long id) {
		String sql = "insert into ftbBussRivalList(nmBussinessRivalId,nmBussinessId) values("+businessId+",'"+id+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void delBussListByBussId(Long id) {
		String sql = "delete from ftbBussRivalList where nmBussinessId = "+id;
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public List findBussId(Long id) {
		String sql = "select nmBussListId from ftbBussList where nmBussinessId = "+id;
		return this.getSession().createSQLQuery(sql).list();
	}
	
	// 根据任务ID 来关联业务类型业务查询 用于终端业务分析
	public List<BussAndBussType> queryBussAndBussTypeByTaskId(Long taskId) {

		String sql = "select b.nmDimensId,b.nmGroupId,b.vcDimensName,b.vcGroupName from ftbBussRivalList a,dtbGroupTree b where a.nmBussinessRivalId=b.nmDimensId and b.nmTypeId=1 and a.nmBussinessId="
				+ taskId;
		List list = this.getSession().createSQLQuery(sql).list();
		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
		BussAndBussType bt = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bt = new BussAndBussType();
				Object[] obj = (Object[]) list.get(i);
				bt.setBusinessId(obj[0] + "");
				bt.setBusinessName(obj[2] + "");
				bt.setBusinessTypeId(obj[1] + "");
				bt.setBusinessTypeName(obj[3] + "");
				listEntity.add(bt);
			}
		}
		return listEntity;
	}
	


}
