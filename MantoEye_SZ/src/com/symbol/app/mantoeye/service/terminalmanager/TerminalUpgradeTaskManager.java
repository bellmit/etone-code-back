package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.TerminalUpgradeTaskDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbStatTerminalChangeTask;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * 全量业务自发现
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class TerminalUpgradeTaskManager extends EntityManager<FtbStatTerminalChangeTask, Long> {

	@Autowired
	private TerminalUpgradeTaskDAO terminalUpgradeTaskDAO;
	public static Map<String, Integer> sortMap;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected TerminalUpgradeTaskDAO getEntityDao() {
		return terminalUpgradeTaskDAO;
	}

	/**
	 * 保存解析记录 hasgroup:分组标识
	 */
	public void saveParseTask(FtbStatTerminalChangeTask entity) {
		terminalUpgradeTaskDAO.saveTask(entity);
	}

	/**
	 * 编辑解析记录
	 */
	public boolean editParseTask(FtbStatTerminalChangeTask entity) {

		boolean succ = true;
		FtbStatTerminalChangeTask entity2 = terminalUpgradeTaskDAO.get(entity.getNmTerminalChangeIdTaskId());
		if (entity2.getIntTaskStatus() != 0) {
			succ = false;
		} else {
			entity2.setDtTaskStartTime(entity.getDtTaskStartTime());
		//	entity2.setDtTaskEndTime(entity.getDtTaskEndTime());
			entity2.setBitTaskActiveFlag(entity.getBitTaskActiveFlag());
			entity2.setVcTaskName(entity.getVcTaskName());
			terminalUpgradeTaskDAO.save(entity2);
		}
		return succ;
	}

	// //////////////////////////////////////////////////////////////////////////////////////
	public Page<CommonSport> query(final Page page, String vcTaskName,
			String vcTaskOrder, int intTaskStatus, String startTime,
			String endTime,long TerminalChangeId) {

		return terminalUpgradeTaskDAO.query(page, vcTaskName, vcTaskOrder, intTaskStatus,
				startTime, endTime, TerminalChangeId);
	}

	public void deleteTasks(Long taskId) {
		// 删除任务
		terminalUpgradeTaskDAO.deleteTask(taskId);
	}

	// 停止任务
	public void stopTask(long taskId) {
		terminalUpgradeTaskDAO.stopTask(taskId);
	}

	// 开始任务
	public void startTask(long taskId) {
		terminalUpgradeTaskDAO.startTask(taskId);
	}

	public int isVcTaskName(String vcTaskName) {
		return terminalUpgradeTaskDAO.queryByVcTaskName(vcTaskName);
	}

}
