package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.terminal.FlushGradeDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbMsisdnList;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

@Service
@Transactional
public class FlushGradeManager extends EntityManager<FtbMsisdnList, Long> {
	@Autowired
	private FlushGradeDAO flushGradeDAO;

	public Page<CommonSport> query(final Page page,int bitType) {
		return flushGradeDAO.query(page,bitType);
	}

	public List<CommonSport> listData() {
		return flushGradeDAO.listData();
	}



	public void deleteByKeys(String ids) {
		flushGradeDAO.deleteByKeys(ids);
	}



	public int FindByMsisdn(String nmFlushLevel) {
		return flushGradeDAO.findByMsisdn(nmFlushLevel);
	}
	
	public List findByBitType(int bitType) {
		return flushGradeDAO.findByBitType(bitType);
	}
	
	
	public void saveFlushConfigure(int bitType, int intDownFlush, int intUpFlush) {
		flushGradeDAO.saveFlushConfigure(bitType, intDownFlush, intUpFlush);
	}


	
	public boolean DataAnalyse(Long LFlushLevel,List<String> list) {
		long maxdata = 0;
//		List<String> list = flushGradeDAO.getAllnmFlushLevel();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String Flushdata = list.get(i);
				if(Flushdata.indexOf("以上")!=-1)
					return false;
				String[] datas = Flushdata.split("-");
				
				long data = Common.StringToLong(Common.OutConvert(datas[1]
						.replace("k", " ")));
				maxdata = data > maxdata ? data : maxdata;
			}
		}
		if(maxdata>LFlushLevel)
			return false;
		
		return true;
		
	}

}
