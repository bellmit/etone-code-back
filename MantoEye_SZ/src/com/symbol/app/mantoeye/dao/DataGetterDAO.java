package com.symbol.app.mantoeye.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
/**
 * 
 * 从事实表里面查询需要提取的数据
 * @author rankin
 *
 */
@Repository
public class DataGetterDAO extends CommonQueryDAO{
	
	public List findRuleTestData(Long imsi,Date startData,Date endDate){
		return null;
	}
	
}
