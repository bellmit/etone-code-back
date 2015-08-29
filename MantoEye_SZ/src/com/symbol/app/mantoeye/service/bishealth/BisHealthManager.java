package com.symbol.app.mantoeye.service.bishealth;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.bishealth.BisHealthDAO;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbNetworkTask1;
import com.symbol.app.mantoeye.util.SymbolUtils;
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
public class BisHealthManager extends EntityManager<FtbNetworkTask1, Long> {

	@Autowired
	private BisHealthDAO bisHealthDAO;
	public static Map<String, Integer> sortMap;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BisHealthDAO getEntityDao() {
		return bisHealthDAO;
	}

	/**
	 * 保存解析记录 hasgroup:分组标识
	 */
	public void saveParseTask(FtbNetworkTask1 entity) {
		bisHealthDAO.saveTask(entity);
	}

	/**
	 * 编辑解析记录
	 */
	public boolean editParseTask(FtbNetworkTask1 entity) {

		boolean succ = true;
		FtbNetworkTask1 entity2 = bisHealthDAO.get(entity.getNmNetworkTaskId());
		if (entity2.getIntTaskStatus() != 0) {
			succ = false;
		} else {
			entity2.setDtStartTime(entity.getDtStartTime());
			entity2.setDtEndTime(entity.getDtEndTime());
			entity2.setBitTaskActiveFlag(entity.getBitTaskActiveFlag());
			if (entity.getNmBussinessId() != null
					&& !"".equals(entity.getNmBussinessId()))
				entity2.setNmBussinessId(entity.getNmBussinessId());
			entity2.setVcTaskName(entity.getVcTaskName());
			entity2.setIntTaskType(entity.getIntTaskType());
		//	entity2.setIntActiveDay(entity.getIntActiveDay());
			bisHealthDAO.save(entity2);
		}
		return succ;
	}

	// //////////////////////////////////////////////////////////////////////////////////////
	public Page<CommonSport> query(final Page page, String vcTaskName,
			String vcTaskOrder, int intTaskStatus, String startTime,
			String endTime) {

		return bisHealthDAO.query(page, vcTaskName, vcTaskOrder, intTaskStatus,
				startTime, endTime);
	}

	public void deleteTasks(Long taskId) {
		// 删除任务
		bisHealthDAO.deleteTask(taskId);
	}

	// 停止任务
	public void stopTask(long taskId) {
		bisHealthDAO.stopTask(taskId);
	}

	// 开始任务
	public void startTask(long taskId) {
		bisHealthDAO.startTask(taskId);
	}

	public int isVcTaskName(String vcTaskName) {
		return bisHealthDAO.queryByVcTaskName(vcTaskName);
	}

	/**
	 *根据选择的任务ID 组装业务对应的业务ID和业务类型ID
	 */
	public String FindBisById(long bisid) {
		List<BussAndBussType> list = bisHealthDAO.FindBisById(bisid);
		String bisIdAndBisTypeId = "";
		String businessName = "";
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				bisIdAndBisTypeId = bisIdAndBisTypeId + bat.getBusinessTypeId()
						+ ":" + bat.getBusinessId() + ",";
				businessName = businessName
						+ SymbolUtils.getSaftDialogStr(bat.getBusinessName())
						+ ",";
			}
			bisIdAndBisTypeId = bisIdAndBisTypeId.substring(0, bisIdAndBisTypeId
					.length() - 1);
			businessName = businessName.substring(0, businessName.length() - 1);
			return bisIdAndBisTypeId + "@" + businessName;
		}else{
			return null;
		}
		

	}

	// 子页查询
	public Page<CommonSport> queryDetail(final Page page, int intTaskType,
			long taskId) {
		return bisHealthDAO.queryDetail(page, intTaskType, taskId);
	}

	public List<CommonSport> listData(int intTaskType, long taskId) {
		return bisHealthDAO.listData(intTaskType, taskId);
	}

	public List<CommonSport> queryNetworkResult(long taskId) {
		return bisHealthDAO.queryNetworkResult(taskId);

	}
	

}
