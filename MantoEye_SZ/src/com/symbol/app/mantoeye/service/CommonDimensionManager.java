package com.symbol.app.mantoeye.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.common.DimensionDAO;
import com.symbol.app.mantoeye.dto.common.CommonDimension;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class CommonDimensionManager {

	@Autowired
	private DimensionDAO dimensionDAO;

	public List<CommonDimension> findAllCountry() {
		return dimensionDAO.findAllCountry();
	}
	public List<CommonDimension> findAllBrand() {
		return dimensionDAO.findAllBrand();
	}
	public List<CommonDimension> findAllLdc() {
		return dimensionDAO.findAllLdc();
	}

	public List<CommonDimension> findAllBsc() {
		return dimensionDAO.findAllBsc();
	}

	public List<CommonDimension> findAllStreet() {
		return dimensionDAO.findAllStreet();
	}

	public List<CommonDimension> findAllSaleArea() {
		return dimensionDAO.findAllSaleArea();
	}

	public List<CommonDimension> findAllSgsn() {
		return dimensionDAO.findAllSgsn();
	}

	public List<CommonDimension> findAllBranch() {
		return dimensionDAO.findAllBranch();
	}

	public List<CommonDimension> findAllBussiness() {
		return dimensionDAO.findAllBussiness();
	}
	
//	public List<CommonDimension> findAllVillage() {
//		return dimensionDAO.findAllVillage();
//	}
	
	public List<CommonDimension> findAllCGI() {
		return dimensionDAO.findAllCGI();
	}
	
	public List<CommonDimension> findAllBusinessHall() {
		return dimensionDAO.findAllBusinessHall();
	}
	public List<CommonDimension> findAllTcp() {
		return dimensionDAO.findAllTcp();
	}
	public List<CommonDimension> findAllWap() {
		return dimensionDAO.findAllWap();
	}
	public List<CommonDimension> findAllApnType() {
		return dimensionDAO.findAllApnType();
	}
	public List<CommonDimension> findAllUserBelong() {
		return dimensionDAO.findAllUserBelong();
	}
}
