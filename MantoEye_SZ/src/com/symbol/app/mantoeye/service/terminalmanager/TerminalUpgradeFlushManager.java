package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.TerminalUpgradeFlushDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class TerminalUpgradeFlushManager {
	@Autowired
	private TerminalUpgradeFlushDAO terminalUpgradeFlushDAO ;

	public Page<CommonSport> query(final Page page, int taskId,int dataType) {
		return terminalUpgradeFlushDAO.query(page, taskId,dataType);
	}

	public List<CommonSport> listData(int taskId,int dataType) {
		return terminalUpgradeFlushDAO.listData(taskId,dataType);
	}


}
