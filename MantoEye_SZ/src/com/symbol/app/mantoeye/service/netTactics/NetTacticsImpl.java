package com.symbol.app.mantoeye.service.netTactics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.netTactics.NetTacticsDAO;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsDetail;
import com.symbol.app.mantoeye.dto.netTactics.DialectNetDTO;
import com.symbol.app.mantoeye.dto.netTactics.NetTacticsDTO;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.Page;

@Component
@Transactional
public class NetTacticsImpl {
	private NetTacticsDAO netTacticsDAO;

	public NetTacticsDAO getNetTacticsDAO() {
		return netTacticsDAO;
	}

	@Resource(name = "netTacticsDAO")
	public void setNetTacticsDAO(NetTacticsDAO netTacticsDAO) {
		this.netTacticsDAO = netTacticsDAO;
	}

	/** 查询策略任务表 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<NetTacticsDTO> queryTacticsList() {
		List<?> lst = (List<?>) netTacticsDAO.queryTacticsList();
		List<NetTacticsDTO> list = new ArrayList();
		for (Object obj : lst) {
			Object[] objs = (Object[]) obj;
			int i = 0;
			NetTacticsDTO dto = new NetTacticsDTO();
			dto.setNmDataGetterTaskId(Utils.getInt(objs[i++]));
			dto.setTacticsName(Utils.getString(objs[i++]));
			dto.setUserName(Utils.getString(objs[i++]));
			dto.setCreateTime(Utils.getString(objs[i++]));
			dto.setStartDate(Utils.getString(objs[i++]));
			dto.setEndDate(Utils.getString(objs[i++]));
			dto.setMissionTime(dto.getStartDate().substring(0, 10) + "~"
					+ dto.getEndDate().substring(0, 10));
			dto.setStartHour(Utils.getString(objs[i++]));
			dto.setEndHour(Utils.getString(objs[i++]));
			dto.setMissionHour(dto.getStartHour() + "~" + dto.getEndHour());
			dto.setStartTime(dto.getStartDate().substring(0, 10)+" "+dto.getStartHour()+":00:00");
			dto.setEndTime(dto.getEndDate().substring(0,10)+" "+dto.getEndHour()+":00:00");
			switch (Utils.getInt(objs[i++])) {
			case 0:
				dto.setStatus("未开始");
				break;
			case 1:
				dto.setStatus("进行中");
				break;
			case 2:
				dto.setStatus("策略结束");
				break;
			case 3:
				dto.setStatus("停止");
				break;
			case 101:
				dto.setStatus("进行中");
				break;
			}
			list.add(dto);
		}
		return list;
	}

	/** 查询详细信息 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<NetTacticsDTO> getShowMessage(String nmDataGetterTaskId) {
		List<?> lst = netTacticsDAO.getShowMessage(nmDataGetterTaskId);
		
		String vcParentGroupName = "";
		String vcGroupName = "";
		int j = 0;
		
		List<NetTacticsDTO> list = new ArrayList();
		for (Object obj : lst) {
			Object[] objs = (Object[]) obj;
			int i = 0;
			vcParentGroupName += Utils.getString(objs[objs.length - 2]);
			vcGroupName += Utils.getString(objs[objs.length - 1]);
			if (j != lst.size() - 1) {
				vcParentGroupName += ",";
				vcGroupName += ",";
			}
			if (j == lst.size() - 1) {
				NetTacticsDTO dto = new NetTacticsDTO();
				dto.setTacticsName(Utils.getString(objs[i++]));
				dto.setStartDate(Utils.getString(objs[i++]));
				dto.setEndDate(Utils.getString(objs[i++]));
				dto.setMissionTime(dto.getStartDate().substring(0, 10) + "~"
						+ dto.getEndDate().substring(0, 10));
				dto.setStartHour(Utils.getString(objs[i++]));
				dto.setEndHour(Utils.getString(objs[i++]));
				dto.setMissionHour(dto.getStartHour() + "时~" + dto.getEndHour()
						+ "时");
				dto.setVcContents(Utils.getString(objs[i++]));
				dto.setVcParentGroupName(vcParentGroupName);
				dto.setVcGroupName(vcGroupName);
				list.add(dto);
			}
			j++;
		}
		return list;
	}

	/** 查询结果列表 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DialectNetDTO> showDialect(String nmDataGetterTaskId) {
		List<?> lst = netTacticsDAO.showDialect(nmDataGetterTaskId);
		List<DialectNetDTO> list = new ArrayList();
		for (Object obj : lst) {
			Object[] objs = (Object[]) obj;
			int i = 0;
			DialectNetDTO dto = new DialectNetDTO();
			dto.setNmId(Utils.getString(objs[i++]));
			dto.setNmMsisdn(Utils.getString(objs[i++]));
			dto.setVcGroupName(Utils.getString(objs[i++]));
			dto.setVcParentGroupName(Utils.getString(objs[i++]));
			dto.setUserTime(Utils.getString(objs[i++]));
			list.add(dto);
		}
		return list;
	}
	
	/** 查询导出列表 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DialectNetDTO> exportDialect(String nmDataGetterTaskId,String startDate,String endDate,String startHour,String endHour) {
		List<?> lst = netTacticsDAO.exportDialect(nmDataGetterTaskId, startDate, endDate, startHour, endHour);
		List<DialectNetDTO> list = new ArrayList();
		for (Object obj : lst) {
			Object[] objs = (Object[]) obj;
			int i = 0;
			DialectNetDTO dto = new DialectNetDTO();
			dto.setNmId(Utils.getString(objs[i++]));
			dto.setNmMsisdn(Utils.getString(objs[i++]));
			dto.setVcGroupName(Utils.getString(objs[i++]));
			dto.setVcParentGroupName(Utils.getString(objs[i++]));
			dto.setUserTime(Utils.getString(objs[i++]));
			list.add(dto);
		}
		return list;
	}
	
	/**获取treeID*/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<NetTacticsDTO> getTreeId(String id){
		List<?> lst = netTacticsDAO.getTreeId(id);
		List<NetTacticsDTO> list = new ArrayList();
		for (Object obj : lst) {
//			Object[] objs = (Object[]) obj;
			int i = 0;
			NetTacticsDTO dto = new NetTacticsDTO();
			dto.setTreeId(Utils.getString(obj));
			list.add(dto);
		}
		return list;
	}

	/** 向策略任务表插入数据 */
	public void insertTactics(String vcTaskName, String dtOrderTime,
			String vcTaskOrder, String dtStartTime, String dtEndTime,
			String intStartHour, String intEndHour, String vcContents) {
		netTacticsDAO.insertTactics(vcTaskName, dtOrderTime, vcTaskOrder,
				dtStartTime, dtEndTime, intStartHour, intEndHour, vcContents);
	}

	/** 保存策略 */
	public void updateTactics(String nmDataGetterTaskId, String vcTaskOrder,
			String dtStartTime, String dtEndTime, String intStartHour,
			String intEndHour, String vcContents) {
		netTacticsDAO.updateTactics(nmDataGetterTaskId, vcTaskOrder,
				dtStartTime, dtEndTime, intStartHour, intEndHour, vcContents);
	}

	/** 获取任务ID */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getTaskId(String dtOrderTime) {
		return netTacticsDAO.getTaskId(dtOrderTime);
	}
	
	public List<Object> findKeyById(long id) {
		return netTacticsDAO.findKeyById(id);
	}
	

	/** 向网络内容提取过滤表插入精确匹配数据 */
	public void insertNetContentGetterFilter(long nmDataGetterTaskId,
			long nmTreeId) {
		netTacticsDAO
				.insertNetContentGetterFilter(nmDataGetterTaskId, nmTreeId);
	}

	/** 停止策略 */
	public void stopTatctics(String nmDataGetterTaskId) {
		netTacticsDAO.stopTatctics(nmDataGetterTaskId);
	}

	/** 删除策略 */
	public void deleteTactics(String nmDataGetterTaskId) {
		netTacticsDAO.deleteTactics(nmDataGetterTaskId);
	}
	
	/**删除网络内容提取过滤表的数据*/
	public void deleteNetContentGetterFilter(String nmDataGetterTaskId){
		netTacticsDAO.deleteNetContentGetterFilter(nmDataGetterTaskId);
	}
	
	public void deleteNetContentGetterFilter(long nmDataGetterTaskId){
		netTacticsDAO.deleteNetContentGetterFilter(nmDataGetterTaskId);
	}
	
	/**
	 * 组装业务选择控件 获取所以得业务类型和业务
	 */
	public Map<String, Map<String, String>> queryBussAndBussType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		List<BussAndBussType> list = netTacticsDAO
				.queryBussAndBussType();
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

	
	public Page<KeywordsDetail> queryKey(final Page page, long id,String startTime,String endTime) {
		return netTacticsDAO.queryKey(page,id,startTime, endTime);
	}
	
	public List<KeywordsDetail> listData(long id,String startTime,String endTime) {
		return netTacticsDAO.listData(id,startTime, endTime);
	}
	
	public List findTreeId(String businessIds) {
		return netTacticsDAO.findTreeId(businessIds);
	}
	
	
}
