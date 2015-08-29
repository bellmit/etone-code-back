package com.symbol.app.mantoeye.service.businessAnalyse;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.businessAnalyse.OwnBusinessAnlyseDAO;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class OwnBusinessAnlyseManager {

	@Autowired
	private OwnBusinessAnlyseDAO ownBusinessAnlyseDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String time_search,int spaceLevel_search,long bussinessId) {
		return ownBusinessAnlyseDAO.query(page, isTD, timeLevel, time_search,spaceLevel_search,bussinessId);
	}

	public List<CommonSport> listData(int isTD,int timeLevel, String time_search,int spaceLevel_search,long bussinessId) {
		return ownBusinessAnlyseDAO.listData(isTD, timeLevel, time_search,spaceLevel_search,bussinessId);
	}

	/**
	 * 组装业务选择控件 获取所以得业务类型和业务
	 */
	public Map<String, Map<String, String>> queryBussAndBussType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		List<BussAndBussType> list = ownBusinessAnlyseDAO
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

	
	public Map<String, Map<String, String>> bulidAreaAndType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		System.out.println("service---------111111111111");
		List<BussAndBussType> list = ownBusinessAnlyseDAO.queryOwnBussType();
		System.out.println("service---------2222222222222");
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
}
