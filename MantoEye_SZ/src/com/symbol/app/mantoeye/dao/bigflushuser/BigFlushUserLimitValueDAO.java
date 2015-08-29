package com.symbol.app.mantoeye.dao.bigflushuser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.business.BigFlushUserLimitValue;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class BigFlushUserLimitValueDAO extends
		HibernateDao<BigFlushUserLimitValue, String> {

	public List<BigFlushUserLimitValue> query() {
		Map<Integer, String> tileLevel = new HashMap<Integer, String>();
		tileLevel.put(1, "天");
		tileLevel.put(2, "周");
		tileLevel.put(3, "月");
		String sql = "select vcOverFlushLimitId,intTimeType,intLimitValue,vcNote from ftbOverFlushLimit";
		Iterator it = this.getSession().createSQLQuery(sql).list().iterator();
		List<BigFlushUserLimitValue> list = new ArrayList<BigFlushUserLimitValue>();
		BigFlushUserLimitValue big = null;
		while (it.hasNext()) {
			big = new BigFlushUserLimitValue();
			Object[] obj = (Object[]) it.next();
			big.setId(obj[0] + "");
			int i = Common.StringToInt(obj[1] + "");
			big.setStrTimeType(tileLevel.get(i));
			big.setIntLimitValue(Common.StringToInt(obj[2] + ""));
			big.setDescrite(obj[3] + "");
			list.add(big);
		}
		return list;

	}

	public boolean save(String id, String value) {
		String sql = "update BigFlushUserLimitValue set intLimitValue=" + value
				+ " where vcOverFlushLimitId='" + id + "'";
		try {
			this.getSession().createQuery(sql).executeUpdate();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
