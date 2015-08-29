package com.symbol.app.mantoeye.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.entity.DropIQConnectionDto;

@Repository
// public class DropIQConnectionDao extends HibernateDao {
public class DropIQConnectionDao extends HibernateQueryDAO {

	public List<DropIQConnectionDto> iqConnectionList() {
		String sql = "sp_iqconnection";

		List list = this.getSession().createSQLQuery(sql).list();

		List<DropIQConnectionDto> listDto = buildNewList(list);

		return listDto;
	}

	private List<DropIQConnectionDto> buildNewList(List list) {
		List<DropIQConnectionDto> listDto = new ArrayList<DropIQConnectionDto>();

		if (null == list || list.isEmpty()) {
			return listDto;
		}

		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);

			/**
			 * System.out.print(obj[0] + "-connId\t"); System.out.print(obj[3] +
			 * "-lastTime\t"); System.out.print(obj[6] + "-lastTime2\t");
			 * System.out.print(obj[11] + "-createTime\t");
			 * System.out.print(obj[18] + "\t");
			 */

			DropIQConnectionDto dto = new DropIQConnectionDto();
			dto.setConnectionId(obj[0] + "");
			dto.setConnectionIp(obj[18] + "");
			dto.setCreateTime(obj[11] + "");
			dto.setLastIQCmdTime(obj[3] + "");
			dto.setLastReqTime(obj[6] + "");
		}

		return listDto;
	}

}
