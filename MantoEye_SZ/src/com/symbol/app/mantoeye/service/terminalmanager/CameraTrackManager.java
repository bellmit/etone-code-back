package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.CameraTrackDAO;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbDataOutPutTask;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManagerOld;

@Service
@Transactional
public class CameraTrackManager extends EntityManagerOld<FtbDataOutPutTask, Long> {
	@Autowired
	private CameraTrackDAO cameraTrackDAO;

	public Page<CommonSport> query(final Page page, int taskStatus,
			String orderTime_search, String orderTime_end, String taskName,
			String taskMan) {
		return cameraTrackDAO.query(page, taskStatus, orderTime_search,
				orderTime_end, taskName, taskMan);
	}

	public int existVcTaskName(String vcTaskName) {
		return cameraTrackDAO.queryByVcTaskName(vcTaskName);
	}

	public boolean editParseTask(FtbDataOutPutTask entity,
			TbBaseUserInfo userInfo) {

		boolean succ = true;
		FtbDataOutPutTask entity2 = cameraTrackDAO.get(entity
				.getNmDataOutPutTaskId());
		if (entity2.getIntTaskStatus() != 0) {
			succ = false;
		} else {
			entity2.setIntTaskType(entity.getIntTaskType());
			entity2.setDtStartTime(entity.getDtStartTime());
			entity2.setDtEndTime(entity.getDtEndTime());
			entity2.setBitTaskActiveFlag(entity.getBitTaskActiveFlag());
			Long taskId = entity.getNmDataOutPutTaskId();
			cameraTrackDAO.save(entity2);
		}
		return succ;
	}

	public void saveParseTask(FtbDataOutPutTask entity) {
		cameraTrackDAO.saveTask(entity);
	}


	public boolean isExistOutPutColumnMap(Long taskId, int intTaskType) {
	String tableName =convertTableName(intTaskType);
		return cameraTrackDAO.isExistOutPutColumnMap(taskId, tableName);
	}

	public void deleteTasks(Long taskId, int intTaskType, int flag) {
		String tableName =convertTableName(intTaskType);
		boolean result=cameraTrackDAO.isExistOutPutColumnMap(taskId, tableName);
		if(result){
			cameraTrackDAO.	delContrast(taskId,tableName);
		}
		if(flag==2){
			List<Long> preIds = cameraTrackDAO.findPreIdByTaskId(taskId);
			for(long preId : preIds){
				cameraTrackDAO.delResultTable(preId);		
			}
			cameraTrackDAO.deleteTaskPre(taskId);	//删除中间表数据
			cameraTrackDAO.deleteTask(taskId);
		}
	}
	
	
	private String convertTableName(int intTaskType){
		String tableName = "";
		// 1.通用拍照指定号码 2.通用拍照指定终端 4.通用拍照指定区域 5.通用拍照指定业务 6.通用拍照指定APN 7.通用拍照用户归属
		switch (intTaskType) {
		case 1:
			tableName = "ftbOutPutMsisdnMap";
			break;
		case 2:
			tableName = "ftbOutPutTerminalMap";
			break;
		case 4:
			tableName = "ftbOutPutAreaMap";
			break;
		case 5:
			tableName = "ftbOutPutBussinessMap";
			break;
		case 6:
			tableName = "ftbOutPutApnMap";
			break;
		case 7:
			tableName = "ftbOutPutAttribMap";
			break;
		}
		return tableName;
	}
	
	
	
	///////////////////////////////////////APN//////////////////////////////////////////////////////////
	/**
	 * 组装APN
	 */
	public Map<String, Map<String, String>> queryApnAndApnType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		List<BussAndBussType> list = cameraTrackDAO
				.queryApnAndApnType();
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				String bussTypeNameAndId = SymbolUtils.getSaftDialogStr(bat
						.getBusinessTypeName())
						+ ":" + bat.getBusinessTypeId();// 业务类型名，业务ID组装成主键一起传上应用层！
				if (mapAll.get(bussTypeNameAndId) == null) {
					bussMap = new HashMap<String, String>();
					bussMap.put(bat.getBusinessName(), bat.getBusinessTypeId()
							+ ":" + bat.getBusinessId());
					mapAll.put(bussTypeNameAndId, bussMap);
				} else {
					mapAll.get(bussTypeNameAndId)
							.put(
									SymbolUtils.getSaftDialogStr(bat
											.getBusinessName()),
									bat.getBusinessTypeId() + ":"
											+ bat.getBusinessId());
				}
			}
		}
		return mapAll;
	}
	public void deleteCameratrackApn(Long taskId) {
		cameraTrackDAO.deleteCameratrackApn(taskId);
	}
	
	
	public void saveCameratrackApn(Long taskId,String vcApn) {
		cameraTrackDAO.saveCameratrackApn(taskId,vcApn);
	}
	
	
	public String queryApnIdAndApnIdTypeByTaskId(int taskId) {
		List<BussAndBussType> list = cameraTrackDAO
				.queryApnIdAndApnIdTypeByTaskId(taskId);
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
	
	
	
	////////////////////////////////////////////////业务///////////////////////////////////////////////////////
	public void deleteCameratrackBis(Long taskId) {
		cameraTrackDAO.deleteCameratrackBis(taskId);
	}
	public void saveCameratrackBis(Long taskId,Long businessId) {
		cameraTrackDAO.saveCameratrackBis(taskId,businessId);
	}

	public String queryBussIdAndBussIdTypeByTaskId(int taskId) {
		List<BussAndBussType> list = cameraTrackDAO
				.queryBussAndBussTypeByTaskId(taskId);
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
	
	
	
	
////////////////////////////////////////////////////号码//////////////////////////////////////////////////////////
	
	
	public Page<CommonSport> queryNmMsisdn(final Page page,
			Long taskId,String nmMsisdn) {
		return cameraTrackDAO.queryNmMsisdn(page, taskId, nmMsisdn);
	}
	public List<CommonSport> exportNmMsisdn(Long taskId,String nmMsisdn) {
		return cameraTrackDAO.exportNmMsisdn(taskId,nmMsisdn);
	}
	public void deleteNmMsisdn(String nmIds) {
		cameraTrackDAO.deleteNmMsisdn(nmIds);
	}
	public int isNmMsisdn(Long taskId,Long nmMsisdn,Long nmId){
		return cameraTrackDAO.queryNmMsisdn(taskId,nmMsisdn,nmId);
	}
	
	public void saveMsisdn(Long taskId,List msisList) {
		List list = cameraTrackDAO.queryAllNmMsisdn(taskId);
		if (msisList!=null && msisList.size()>0) {
			for (int i = 0; i < msisList.size(); i++) {
				String msis = msisList.get(i).toString();
				boolean flag = true;
				if (list!=null && list.size()>0) {
					for (int j = 0; j < list.size(); j++) {
						String nmMsisdn = list.get(j).toString();
						if (msis.equals(nmMsisdn)) {
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					cameraTrackDAO.saveMsisdn(taskId,Common.StringToLong(msis));
				}
			}
		}
	}
	
	public void updateMsisdn(Long taskId,Long nmMsisdn,Long nmId) {
		cameraTrackDAO.updateMsisdn(taskId,nmMsisdn,nmId);
	}
	
	///////////////////////////////////////////////////终端类型/////////////////////////////////////////////////////////////////
	/**
	 * 组装终端
	 */
//	public Map<String, Map<String, String>> queryTerminalAndType() {
//		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
//		Map<String, String> bussMap = null;
//		List<BussAndBussType> list = cameraTrackDAO
//				.queryTerminalAndType();
//		BussAndBussType bat = null;
//		if (list != null && !list.isEmpty()) {
//			for (int i = 0; i < list.size(); i++) {
//				bat = list.get(i);
//				String bussTypeNameAndId = SymbolUtils.getSaftDialogStr(bat
//						.getBusinessTypeName())
//						+ ":" + bat.getBusinessTypeId();// 业务类型名，业务ID组装成主键一起传上应用层！
//				if (mapAll.get(bussTypeNameAndId) == null) {
//					bussMap = new HashMap<String, String>();
//					bussMap.put(bat.getBusinessName(), bat.getBusinessTypeId()
//							+ ":" + bat.getBusinessId());
//					mapAll.put(bussTypeNameAndId, bussMap);
//				} else {
//					mapAll.get(bussTypeNameAndId)
//							.put(
//									SymbolUtils.getSaftDialogStr(bat
//											.getBusinessName()),
//									bat.getBusinessTypeId() + ":"
//											+ bat.getBusinessId());
//				}
//			}
//		}
//		return mapAll;
//	}
//	
//	public String queryTerminalIdAndIdTypeByTaskId(int taskId) {
//		List<BussAndBussType> list = cameraTrackDAO
//				.queryTerminalIdAndIdTypeByTaskId(taskId);
//		String bisIdAndBisTypeId = "";
//		String businessName = "";
//		BussAndBussType bat = null;
//		if (list != null && !list.isEmpty()) {
//			for (int i = 0; i < list.size(); i++) {
//				bat = list.get(i);
//				bisIdAndBisTypeId = bisIdAndBisTypeId + bat.getBusinessTypeId()
//						+ ":" + bat.getBusinessId() + ",";
//				businessName = businessName
//						+ SymbolUtils.getSaftDialogStr(bat.getBusinessName())
//						+ ",";
//			}
//			bisIdAndBisTypeId = bisIdAndBisTypeId.substring(0, bisIdAndBisTypeId
//					.length() - 1);
//			businessName = businessName.substring(0, businessName.length() - 1);
//			return bisIdAndBisTypeId + "@" + businessName;
//		}else{
//			return null;
//		}
//	}
//	public void deleteCameratrackTerminal(Long taskId) {
//		cameraTrackDAO.deleteCameratrackTerminal(taskId);
//	}
//	
//	
//	
//	public void saveCameratrackTerminal(Long taskId,long vcApn) {
//		cameraTrackDAO.saveCameratrackTerminal(taskId,vcApn);
//	}
	public Page<CommonSport> queryTerminal(final Page page,String vcTerminalBrand,String vcTerminalName) {
		return cameraTrackDAO.queryTerminal(page,vcTerminalBrand,vcTerminalName);
	}
	public List<CommonSport> queryTerminalMap(Long taskId) {
		return cameraTrackDAO.queryTerminalMap(taskId);
	}
    
	public void deleteTerminalMap(long taskId){
		cameraTrackDAO.deleteTerminalMap(taskId);
	}
    public void saveTerminalMap(long taskId,long nmTerminalId){
    	cameraTrackDAO.saveTerminalMap(taskId,nmTerminalId);
    }
	
	
	
	
	
	///////////////////////////////////////////////////用户归属//////////////////////////////////////////////////
    public String queryUserBelongByTaskId(long taskId){
    	return cameraTrackDAO.queryUserBelongByTaskId(taskId);
    }
	
    public void saveCameratrackUser(long taskId,int intintUserBelongId){
    	 cameraTrackDAO.saveCameratrackUser(taskId, intintUserBelongId);
    }
	
    public void UpdateCameratrackUser(long taskId,int intintUserBelongId){
    	 cameraTrackDAO.UpdateCameratrackUser(taskId,intintUserBelongId);
    }
	///////////////////////////////////////////////////区域配置/////////////////////////////////////////////////////////
	public Page<CommonSport> queryArea(final Page page,
			int areaType,String areaName) {
		return cameraTrackDAO.queryArea(page,areaType,areaName);
	}
	public List<CommonSport> queryAreaMap(Long taskId) {
		return cameraTrackDAO.queryAreaMap(taskId);
	}
    
	public void deleteOutPutAreaMap(long taskId){
		cameraTrackDAO.deleteOutPutAreaMap(taskId);
	}
    public void saveOutPutAreaMap(long taskId,int areaType,String areaId){
    	cameraTrackDAO.saveOutPutAreaMap(taskId,areaType,areaId);
    }
    
    
}
