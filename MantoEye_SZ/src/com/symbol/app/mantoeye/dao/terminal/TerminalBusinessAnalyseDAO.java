package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.flush.TerminalBusiness;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class TerminalBusinessAnalyseDAO extends HibernateDao {

	/**
	 * 根基任务查询统计数据
	 * 
	 * @param taskId
	 * @return
	 */
	public List<TerminalBusiness> query(int taskId) {
		String sql = "select vcBrand,vcTmerTypeName,vcBussinessTypeName,vcBussinessName,nmUsers,nmFlush,flUsersRate,flFlustRate,intTmerTypeId from vStatWeekTermBuss where nmTerminalPolicyId="
				+ taskId;
		List list = this.getSession().createSQLQuery(sql).list();

		return this.bulidData(list);
	}

	/**
	 * 组装数据
	 * 
	 * @param list
	 * @return
	 */
	public List<TerminalBusiness> bulidData(List<Object[]> list) {
		List<TerminalBusiness> listData = new ArrayList<TerminalBusiness>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = list.get(i);
				TerminalBusiness tb = new TerminalBusiness();
				tb.setTerminalBrandName(obj[0] + "");
				tb.setTerminalTypeName(obj[1] + "");
				tb.setBusinessTypeName(obj[2] + "");
				tb.setBusinessName(obj[3] + "");
				tb.setIntUsers(Common.StringToLong(obj[4] + ""));
				tb.setIntFlush(Common.StringToLong(obj[5] + ""));
				tb.setFlUsersRate(obj[6] + "");
				tb.setFlFlustRate(obj[7] + "");
				tb.setTerminalTypeId(Common.StringToLong(obj[8] + ""));
				tb.calculateData();
				listData.add(tb);
			}
		}
		return listData;
	}

	/**
	 * 查询终端业务分析的总用户数
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<Long, Long> findSumUsers(int taskId) {
		Map<Long, Long> map = new HashMap<Long, Long>();
		String sql = " select intTmerTypeId,nmUsers from ftbStatWeekTerminalFlush where nmTerminalPolicyId =   "
				+ taskId;
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				map.put(Common.StringToLong(objs[0] + ""),
						Common.StringToLong(objs[1] + ""));
			}
		}
		return map;
	}

	public Map<Long, Double> findsumFlush(int taskId) {
		Map<Long, Double> map = new HashMap<Long, Double>();
		String sql = " select intTmerTypeId,convert(numeric(38,2),round(nmFlush/1024,2)) nmFlush"
				+ " from ftbStatWeekTerminalFlush where nmTerminalPolicyId = "
				+ taskId;
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				map.put(Common.StringToLong(objs[0] + ""),
						Common.StringTodouble(objs[1] + ""));
			}
		}
		return map;
	}

}
