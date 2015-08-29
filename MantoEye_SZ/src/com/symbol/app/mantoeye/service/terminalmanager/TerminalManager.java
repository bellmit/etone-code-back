package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.TerminalManagerDAO;
import com.symbol.app.mantoeye.dto.AppServer;
import com.symbol.app.mantoeye.dto.TermDataLoadTask;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.flush.TerminalEntity;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class TerminalManager extends EntityManager<TerminalEntity, String> {

	@Autowired
	private TerminalManagerDAO terminalManagerDAO;

	public void insertTerminal(List<TerminalEntity> list) {
		terminalManagerDAO.insertTerminal(list);
	}

	public void clearTable() {
		terminalManagerDAO.clearTerminalTable();
	}

	public Page<TerminalEntity> queryTerminal(final Page page,
			TerminalEntity terminalEntity) {
		return terminalManagerDAO.queryTerminalEntity(page, terminalEntity);
	}

	/**
	 * 构建品牌和型号map
	 * 
	 * @return
	 */
	public Map<String, Map<String, String>> bulidBrandAndType() {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		List<TerminalEntity> list = terminalManagerDAO.queryAll();
		for (int i = 0; i < list.size(); i++) {
			TerminalEntity te = list.get(i);
			String brandAndId = te.getTerminalBrand() + ":" + te.getBradId();// 品牌和品牌ID
			if (map.get(brandAndId) == null) {
				Map<String, String> mapEntity = new HashMap<String, String>();
				mapEntity.put(te.getTerminalType(), te.getBradId() + ":"
						+ te.getTypeId());// 终端型号和终端型号ID
				map.put(brandAndId, mapEntity);
			} else {
				map.get(brandAndId).put(
						SymbolUtils.getSaftDialogStr(te.getTerminalType()),
						te.getBradId() + ":" + te.getTypeId());
			}

		}
		return map;
	}

	/**
	 *根据选择的任务 构建品牌和型号map
	 * 
	 * @return
	 */
	public Map<String, Map<String, String>> bulidBrandAndType(int taskId) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		List<TerminalEntity> list = terminalManagerDAO.queryAllByTaskId(taskId);
		for (int i = 0; i < list.size(); i++) {
			TerminalEntity te = list.get(i);
			String brandAndId = te.getTerminalBrand() + ":" + te.getBradId();// 品牌和品牌ID
			if (map.get(brandAndId) == null) {
				Map<String, String> mapEntity = new HashMap<String, String>();
				mapEntity.put(SymbolUtils
						.getSaftDialogStr(te.getTerminalType()), te.getBradId()
						+ ":" + te.getTypeId());// 终端型号和终端型号ID
				map.put(brandAndId, mapEntity);
			} else {
				map.get(brandAndId).put(
						SymbolUtils.getSaftDialogStr(te.getTerminalType()),
						te.getBradId() + ":" + te.getTypeId());
			}

		}
		return map;
	}

	/**
	 *根据选择的任务 构建品牌和型号map
	 * 
	 * @return
	 */
	public String bulidBrandAndTypeByTaskId(int taskId) {
		String terminalNames = "";
		String brandIdAndTypeId = "";
		List<TerminalEntity> list = terminalManagerDAO.queryAllByTaskId(taskId);
		for (int i = 0; i < list.size(); i++) {
			TerminalEntity te = list.get(i);
			brandIdAndTypeId = brandIdAndTypeId + te.getBradId() + ":"
					+ te.getTypeId() + ",";
			terminalNames = terminalNames
					+ SymbolUtils.getSaftDialogStr(te.getTerminalType()) + ",";
		}
		brandIdAndTypeId = brandIdAndTypeId.substring(0, brandIdAndTypeId
				.length() - 1);
		terminalNames = terminalNames.substring(0, terminalNames.length() - 1);
		return brandIdAndTypeId + "@" + terminalNames;
	}

	public void saveUpFile(String serverIp, String filePath) {
		terminalManagerDAO.saveUpFile(serverIp, filePath);
	}

	/**
	 * 通过服务器IP 获取应用服务器信息
	 * 
	 * @param ip
	 * @return
	 */
	public AppServer getAppServerByServerIp(String ip) {
		return terminalManagerDAO.getAppServerByServerIp(ip);
	}
	public AppServer getAppServerByOutIp(String ip) {
		return terminalManagerDAO.getAppServerByOutIp(ip);
	}

	/**
	 * 查看所有的上传任务
	 * 
	 * @return
	 */
	public List<TermDataLoadTask> findUpFiles() {
		return terminalManagerDAO.findUpFiles();
	}

	public Map<String, Map<String, String>> bulidAreaAndType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		List<BussAndBussType> list = terminalManagerDAO.queryBussAndBussType();
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
