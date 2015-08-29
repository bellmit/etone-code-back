package com.symbol.app.mantoeye.service.sports;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.sports.AreaMapCellDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbMsisdnList;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;


@Service
@Transactional
public class AreaMapCellManager extends EntityManager<FtbMsisdnList,Long> {
	@Autowired
	private AreaMapCellDAO areaMapCellDAO;
	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page,
			String vcAreaName, String vcCgiName,Integer intType,String vcCGI) {
		return areaMapCellDAO.query(page, vcAreaName,vcCgiName,intType,vcCGI);
	}
	
	public Page<CommonSport> queryCGI(final Page page,Long id) {
		return areaMapCellDAO.queryCGI(page, id);
	}

	public List<CommonSport> listData(String vcAreaName, String vcCgiName,Integer intType,String vcCGI) {
		return areaMapCellDAO.listData(vcAreaName, vcCgiName,intType,vcCGI);
	}
	
	public void deleteById(String ids,int intType) {
		areaMapCellDAO.deleteById(ids,intType);
	}
	
	
	public void deleteAreaMapCell(String ids) {
		areaMapCellDAO.deleteAreaMapCell(ids);
	}
	
	public List queryCgi(String vcCgiName) {
		List list = areaMapCellDAO.queryCgi(vcCgiName);
		return list;
	}
	
	public List queryByVcCgi(String vcCGI) {
		List list = areaMapCellDAO.queryByVcCgi(vcCGI);
		return list;
	}
	
	public void updateArea(Long id,String vcAreaName) {
		String dtUpdateTime = CommonUtils.getCurrentDate();
		areaMapCellDAO.updateArea(id, vcAreaName, dtUpdateTime);
	}
	
	public List queryArea(String vcAreaName,int intTyp) {
		List areaList = new ArrayList();
		areaList = areaMapCellDAO.queryArea(vcAreaName,intTyp);
		if (areaList==null || areaList.size()==0) {
			areaMapCellDAO.insertArea(vcAreaName,intTyp,CommonUtils.getCurrentDate());
			areaList = areaMapCellDAO.queryArea(vcAreaName,intTyp);
		}
		return areaList;
	}
	
	public void insertAreaMapCell(Long nmAreaId,String vcCGI) {
		boolean flag = areaMapCellDAO.queryAreaMapCell(nmAreaId,vcCGI);
		if (flag) {
			areaMapCellDAO.insertAreaMapCell(nmAreaId,vcCGI);
		}
	}
		
}
