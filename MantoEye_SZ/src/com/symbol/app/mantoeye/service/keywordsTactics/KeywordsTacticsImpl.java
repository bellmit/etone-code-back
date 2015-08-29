package com.symbol.app.mantoeye.service.keywordsTactics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.keywordsTactics.KeywordsTacticsDAO;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.keywordsTactics.DialectKeywordsDTO;
import com.symbol.app.mantoeye.dto.keywordsTactics.FtbKeyValueGetterFilter;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsDetail;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsTactics;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsTacticsDTO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.Page;

@Component
@Transactional
public class KeywordsTacticsImpl{
	private KeywordsTacticsDAO keywordsTacticsDAO;

	public KeywordsTacticsDAO getKeywordsTacticsDAO() {
		return keywordsTacticsDAO;
	}

	@Resource(name = "keywordsTacticsDAO")
	public void setKeywordsTacticsDAO(KeywordsTacticsDAO keywordsTacticsDAO) {
		this.keywordsTacticsDAO = keywordsTacticsDAO;
	}

	/** 查询策略任务表 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<KeywordsTacticsDTO> queryTacticsList() {
		List<?> lst = (List<?>) keywordsTacticsDAO.queryTacticsList();
		List<KeywordsTacticsDTO> list = new ArrayList();
		for (Object obj : lst) {
			Object[] objs = (Object[]) obj;
			int i = 0;
			KeywordsTacticsDTO dto = new KeywordsTacticsDTO();
			dto.setNmDataGetterTaskId(Utils.getInt(objs[i++]));
			dto.setTacticsName(Utils.getString(objs[i++]));
			dto.setUserName(Utils.getString(objs[i++]));
			dto.setCreateTime(Utils.getString(objs[i++]));
			dto.setStartTime(Utils.getString(objs[i++]));
			dto.setEndTime(Utils.getString(objs[i++]));
			dto.setMissionTime(dto.getStartTime().substring(0, 10) + "~"
					+ dto.getEndTime().substring(0, 10));
			dto.setStartHour(Utils.getString(objs[i++]));
			dto.setEndHour(Utils.getString(objs[i++]));
			dto.setMissionHour(dto.getStartHour() + "~" + dto.getEndHour());
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
	public List<DialectKeywordsDTO> showDialect(String nmDataGetterTaskId) {
		List<?> lst = (List<?>) keywordsTacticsDAO
				.showDialect(nmDataGetterTaskId);
		List<DialectKeywordsDTO> list = new ArrayList();
		for (Object obj : lst) {
			Object[] objs = (Object[]) obj;
			int i = 0;
			DialectKeywordsDTO dto = new DialectKeywordsDTO();
			dto.setNmId(Utils.getString(objs[i++]));
			dto.setNmMsisdn(Utils.getString(objs[i++]));
			dto.setVcSoName(Utils.getString(objs[i++]));
			dto.setVcKeyValues(Utils.getString(objs[i++]));
			switch (Utils.getInt(objs[i++])) {
			case 0:
				dto.setIsExactMark("否");
				break;
			case 1:
				dto.setIsExactMark("是");
				break;
			}
			dto.setUserTime(Utils.getString(objs[i++]));
			list.add(dto);
		}
		return list;
	}

	/** 获取策略名 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<KeywordsTacticsDTO> getShowMessage(String nmDataGetterTaskId) {
		List<?> lst = keywordsTacticsDAO.getShowMessage(nmDataGetterTaskId);
		String isExactMark = "";
		String unExactMark = "";
		int j = 0;
		List<KeywordsTacticsDTO> list = new ArrayList();
		for (Object obj : lst) {
			Object[] objs = (Object[]) obj;
			int i = 0;
			isExactMark += Utils.getString(objs[objs.length - 2]);
			unExactMark += Utils.getString(objs[objs.length - 1]);
			if (j != lst.size() - 1) {
				isExactMark += ",";
				unExactMark += ",";
			}

			if (j == lst.size() - 1) {
				KeywordsTacticsDTO dto = new KeywordsTacticsDTO();
				dto.setTacticsName(Utils.getString(objs[i++]));
				dto.setStartTime(Utils.getString(objs[i++]));
				dto.setEndTime(Utils.getString(objs[i++]));
				dto.setMissionTime(dto.getStartTime().substring(0, 10) + "~"
						+ dto.getEndTime().substring(0, 10));
				dto.setStartHour(Utils.getString(objs[i++]));
				dto.setEndHour(Utils.getString(objs[i++]));
				dto.setMissionHour(dto.getStartHour() + "时~" + dto.getEndHour()
						+ "时");
				dto.setVcContents(Utils.getString(objs[i++]));
				dto.setVcSoName(Utils.getString(objs[i++]));
				dto.setIsExactMark(isExactMark);
				dto.setUnExactMark(unExactMark);
				list.add(dto);
			}
			j++;
		}
		return list;
	}

	/** 向策略任务表插入数据 */
	public void insertTactics(String vcTaskName, String dtOrderTime,
			String vcTaskOrder, String dtStartTime, String dtEndTime,
			String intStartHour, String intEndHour, String vcContents) {
		keywordsTacticsDAO.insertTactics(vcTaskName, dtOrderTime, vcTaskOrder,
				dtStartTime, dtEndTime, intStartHour, intEndHour, vcContents);
	}
	
	/** 向策略任务表插入数据 */
	public void save(KeywordsTactics entity) {
		 keywordsTacticsDAO.save(entity);
	}
	
	
	public void update(KeywordsTactics entity) {
		 keywordsTacticsDAO.update(entity);
	}

	/** 保存策略 */
	public void updateTactics(String nmDataGetterTaskId, String vcTaskOrder,
			String dtStartTime, String dtEndTime, String intStartHour,
			String intEndHour, String vcContents) {
		keywordsTacticsDAO.updateTactics(nmDataGetterTaskId, vcTaskOrder,
				dtStartTime, dtEndTime, intStartHour, intEndHour, vcContents);
	}

	/** 获取任务ID */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public long getTaskId(String dtOrderTime) {
		return keywordsTacticsDAO.getTaskId(dtOrderTime);
	}

	/** 向策略提取过滤表插入精确匹配数据 */
	public long insertIsExactMark(long nmDataGetterTaskId,
			String vcFilterKeyValue,int isExactMark) {
			return keywordsTacticsDAO.insertIsExactMark(nmDataGetterTaskId,vcFilterKeyValue,isExactMark);
	}
	
	
	public void insertKeyValueSoTypeId(long nmSoTypeId,long nmDataGetterFilterId) {
		keywordsTacticsDAO.insertKeyValueSoTypeId(nmSoTypeId,nmDataGetterFilterId);
	}

	/** 向策略提取过滤表插入模糊匹配数据 */
	public void insertUnExactMark(String nmDataGetterTaskId, String nmSoTypeId,
			String vcFilterKeyValue) {
		keywordsTacticsDAO.insertUnExactMark(nmDataGetterTaskId, nmSoTypeId,
				vcFilterKeyValue);
	}

	/** 停止策略 */
	public void stopTatctics(int nmDataGetterTaskId) {
		keywordsTacticsDAO.stopTatctics(nmDataGetterTaskId);
	}

	/** 删除策略 */
	public void deleteTactics(String nmDataGetterTaskId) {
		keywordsTacticsDAO.deleteTactics(nmDataGetterTaskId);
	}
	
	public KeywordsTactics findTaskById(long id) {
		return keywordsTacticsDAO.findTaskById(id);
	}
	
	public List<FtbKeyValueGetterFilter> findKeyById(long id) {
		return keywordsTacticsDAO.findKeyById(id);
	}
	
	
	/**删除策略提取过滤表的数据*/
	public void deleteKeyValueGetterFilter(String nmDataGetterTaskId){
		keywordsTacticsDAO.deleteKeyValueGetterFilter(nmDataGetterTaskId);
	}
	
	public void deleteKeyValueGetterFilter(long nmDataGetterTaskId){
		keywordsTacticsDAO.deleteKeyValueGetterFilter(nmDataGetterTaskId);
	}
	
	
	public void deleteKeyValueSoTypeIdFilter(String nmDataGetterFilterId){
		keywordsTacticsDAO.deleteKeyValueSoTypeIdFilter(nmDataGetterFilterId);
	}
	
	public List findDataGetterFilterId(String nmDataGetterTaskId){
		return keywordsTacticsDAO.findDataGetterFilterId(nmDataGetterTaskId);
	}
	/**
	 * 组装业务选择控件 获取所以得业务类型和业务
	 */
	public Map<String, Map<String, String>> queryBussAndBussType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		List<BussAndBussType> list = keywordsTacticsDAO
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

	
	public int isVcTaskName(String vcTaskName){
		return keywordsTacticsDAO.queryByVcTaskName(vcTaskName);
	}
	
	
	
	public Page<KeywordsDetail> queryKey(final Page page, long id,String startTime,String endTime) {
		return keywordsTacticsDAO.queryKey(page,id,startTime, endTime);
	}
	
	public List<KeywordsDetail> listData(long id,String startTime,String endTime) {
		return keywordsTacticsDAO.listData(id,startTime, endTime);
	}
	
}
