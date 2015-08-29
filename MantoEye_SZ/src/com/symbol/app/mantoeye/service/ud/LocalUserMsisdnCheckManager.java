/**
 * com.symbol.app.mantoeye.service.ud.LocalUserMsisdnCheckManager.java
 */
package com.symbol.app.mantoeye.service.ud;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.ud.LocalUserMsisdnCheckDao;
import com.symbol.app.mantoeye.entity.UserBelongMsisdn;

/**
 * @author Wu Zhenzhen
 * @version 2013-10-17 下午12:34:12
 */
@Transactional
@Service
public class LocalUserMsisdnCheckManager {

	private LocalUserMsisdnCheckDao localUserMsisdnCheckDao = null;

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:48:01
	 * 
	 * @return the localUserMsisdnCheckDao
	 */
	public LocalUserMsisdnCheckDao getLocalUserMsisdnCheckDao() {
		return localUserMsisdnCheckDao;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:48:01
	 * 
	 * @param localUserMsisdnCheckDao
	 *            the localUserMsisdnCheckDao to set
	 */
	@Resource
	public void setLocalUserMsisdnCheckDao(
			LocalUserMsisdnCheckDao localUserMsisdnCheckDao) {
		this.localUserMsisdnCheckDao = localUserMsisdnCheckDao;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:34:20
	 * 
	 * @param ml
	 * @return
	 */
	public synchronized List<UserBelongMsisdn> checkLocalMsisdnAll(
			List<UserBelongMsisdn> ml) {
		List<UserBelongMsisdn> nml = new ArrayList<UserBelongMsisdn>();

		List<Integer> list = localUserMsisdnCheckDao.getLocalMsisdn();

		for (UserBelongMsisdn um : ml) {
			String msisdn = um.getNmMsisdn();
			int msisdnSeg = Integer.parseInt(String.valueOf(msisdn).substring(
					0, 7));

			if (list.contains(msisdnSeg))
				nml.add(um);
		}

		return nml;
	}

}
