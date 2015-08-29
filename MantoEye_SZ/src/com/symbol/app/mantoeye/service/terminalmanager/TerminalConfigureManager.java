package com.symbol.app.mantoeye.service.terminalmanager;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.terminal.TerminalConfigureDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;



@Service
@Transactional
public class TerminalConfigureManager {
	@Autowired
	private TerminalConfigureDAO terminalConfigureDAO;
	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page,
			String vcTerminalTac,String vcTerminalBrand,String vcTerminalName) {
		return terminalConfigureDAO.query(page, vcTerminalTac, vcTerminalBrand, vcTerminalName);
	}

	public List<CommonSport> listData(String vcTerminalTac,String vcTerminalBrand,String vcTerminalName) {
		return terminalConfigureDAO.listData(vcTerminalTac, vcTerminalBrand, vcTerminalName);
	}
	
	public void saveTerminal(String vcTerminalTac,String vcTerminalBrand,String vcTerminalName) {
		if (terminalUnique(null,vcTerminalTac)) {
			terminalConfigureDAO.saveTerminal(vcTerminalTac, vcTerminalBrand, vcTerminalName);
		}	
	}
	//不需要加重复判断
	public void saveTerminals(String vcTerminalTac,String vcTerminalBrand,String vcTerminalName) {
			terminalConfigureDAO.saveTerminal(vcTerminalTac, vcTerminalBrand, vcTerminalName);
	}
	
	public List<CommonSport> queryAllTerminal() {
		List<CommonSport> list = listData(null,null,null);
		return list;
	}
	
	public void deleteById(String ids) {
		terminalConfigureDAO.deleteById(ids);
	}
	
	public void updateTerminal(String vcTerminalTac, String vcTerminalBrand,String vcTerminalName,Long nmTerminalId) {
		terminalConfigureDAO.updateTerminal(vcTerminalTac, vcTerminalBrand, vcTerminalName,nmTerminalId);
	}
	
	public boolean terminalUnique(Long nmTerminalId,String vcTerminalTac){
		return terminalConfigureDAO.terminalUnique(nmTerminalId,vcTerminalTac);
	}
}
