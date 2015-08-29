package com.symbol.app.mantoeye.sso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.sso.common.UnmpConstants;
import com.symbol.app.mantoeye.sso.dao.MantoEyeUserInfoDAO;
import com.symbol.app.mantoeye.unmp.hrmanager.GZHRManager;
import com.symbol.app.mantoeye.unmp.hrmanager.GZHRManagerLocator;
import com.symbol.app.mantoeye.unmp.hrmanager.GZHRManagerSoap_BindingStub;
import com.symbol.app.mantoeye.unmp.hrmanager.GZHRManagerSoap_PortType;
import com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoIn;
import com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoOut;
import com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult;
import com.symbol.app.mantoeye.unmp.hrmanager.UserInfo;
import com.symbol.app.mantoeye.unmp.hrmanager.holders.GetAllUserInfoOutHolder;
import com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder;

/**
 * 同步统一网管平台用户
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class UnmpUserManager {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MantoEyeUserInfoDAO mantoEyeUserInfoDAO;

	@Transactional
	public boolean getAllUnmpUserInfo() {

		logger.info("****开始同步统一网管平台用户信息！****");
		try {
			GZHRManager manager = new GZHRManagerLocator();
			GZHRManagerSoap_PortType stub = null;

			// 输入参数
			GetAllUserInfoIn in = new GetAllUserInfoIn();
			in.setVersion("0");
			// gi.setSystemID(UnmpConstants.UNMP_SYSTEM_ID);
			// gi.setSysAccount(UnmpConstants.UNMP_SYS_ACCOUNT);
			// gi.setSysPassword(UnmpConstants.UNMP_SYS_PASSWORD);

			UNMPCallResultHolder resultHolder = new UNMPCallResultHolder();
			GetAllUserInfoOutHolder outHolder = new GetAllUserInfoOutHolder();

			stub = manager.getGZHRManagerSoap();

			logger.info("--stub-- " + stub);
			if (stub != null) {

				stub.getAllUserInfo(UnmpConstants.UNMP_SYSTEM_ID,
						UnmpConstants.UNMP_SYS_ACCOUNT,
						UnmpConstants.UNMP_SYS_PASSWORD, in, resultHolder,
						outHolder);

				//  -2：接口未实现
				//  -1：系统异常
				//  0：调用成功
				//  100-149：调用或验证错误编号；
				//  150-199：目录同步错误范围；
				//  200-299：消息管理错误范围；
				//  300-349：单点登陆错误范围；
				//  350-399：集中授权错误范围。

				UNMPCallResult result = resultHolder.value;
				logger.info("**Code**" + result.getReturnCode());
				if (result.getReturnCode() == 0) {
					logger.debug("****获取统一网管平台用户信息成功！****");
					GetAllUserInfoOut userOut = outHolder.value;
					UserInfo[] userArray = userOut.getUserInfo();
					logger.info("用户数：" + userArray.length);
					updateAllUser(userArray);
					logger.info("****同步统一网管平台用户信息成功！****");
					return true;
				} else {
					logger.info("****获取统一网管平台用户信息失败！****");
					logger.info("****Code:" + result.getReturnCode()
							+ "---Msg:" + result.getReturnMessage() + "****");
					return false;
				}

			} else {
				logger.info("****调用统一网管平台失败！****");
				return false;
			}
		} catch (Exception e) {
			logger.error("****同步统一网管平台用户信息失败！****");
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * 持久化所有的统一网管平台用户到数据库 更新数据
	 */
	@Transactional
	public void updateAllUser(UserInfo[] users) {
		String[] userids = new String[users.length];
		// 删除原有的所有统一网管平台用户数据
		mantoEyeUserInfoDAO.deleteAllUnmpUser();

		// 保存新的用户信息
		for (int i = 0; i < users.length; i++) {
			UserInfo user = users[i];
			userids[i] = user.getUserID();

			mantoEyeUserInfoDAO.saveUnmpUser(user.getUserID(), user
					.getLoginID(), user.getFullName(), user.getOuID(), user
					.getEmail(), user.getMobile(), user.getTelephoneNumber());
		}
		// 删除没有关联的用户角色关联表信息
		mantoEyeUserInfoDAO.deleteUserRoleMap(userids);
	}
}
