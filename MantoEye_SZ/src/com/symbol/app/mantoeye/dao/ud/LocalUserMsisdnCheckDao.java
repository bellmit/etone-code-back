/**
 * com.symbol.app.mantoeye.dao.ud.LocalUserMsisdnCheckDao.java
 */
package com.symbol.app.mantoeye.dao.ud;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.UserBelongMsisdn;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * @author Wu Zhenzhen
 * @version 2013-10-17 下午12:35:11
 */
@Repository
public class LocalUserMsisdnCheckDao extends
		HibernateDao<UserBelongMsisdn, Integer> {

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:50:16
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getLocalMsisdn() {
		String sql = " commit;select nmMsisdnSeg from ftbMsisdnSeg, dtbCity where ftbMsisdnSeg.intCityId = dtbCity.intCityId and vcName='深圳'  ";

		List<Integer> list = this.getSession().createSQLQuery(sql)
				.addScalar("nmMsisdnSeg", Hibernate.INTEGER).list();

		return list;
	}

}
