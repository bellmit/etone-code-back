package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.TerminalBusinessAnalyseDAO;
import com.symbol.app.mantoeye.dto.flush.TerminalBusiness;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class TerminalBusinessAnalyseManager {

	@Autowired
	private TerminalBusinessAnalyseDAO terminalBusinessAnalyseDAO;

	/**
	 * 根基任务查询统计数据
	 * 
	 * @param taskId
	 * @return
	 */
	public List<TerminalBusiness> query(int taskId) {
		return terminalBusinessAnalyseDAO.query(taskId);
	}

	public Map<Long, Long> findSumUsers(int taskId) {
		return terminalBusinessAnalyseDAO.findSumUsers(taskId);
	}

	public Map<Long, Double> findsumFlush(int taskId) {
		return terminalBusinessAnalyseDAO.findsumFlush(taskId);
	}
}
