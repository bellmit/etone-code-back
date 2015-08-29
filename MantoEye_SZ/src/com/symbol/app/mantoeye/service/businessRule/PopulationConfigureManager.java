package com.symbol.app.mantoeye.service.businessRule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.business.PopulationConfigureDAO;
import com.symbol.app.mantoeye.entity.FtbResidentConfig;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class PopulationConfigureManager extends EntityManager<FtbResidentConfig, String>{
	@Autowired
	private PopulationConfigureDAO populationConfigureDAO;
	
	/**
	 * 
	 * 添加常驻人口配置
	 */
	public void insertConfigure(Integer intType,Integer intDay,String vcNote){
		//this.deleteTask(taskEntity.getNmTerminalPolicyId()+"");
		populationConfigureDAO.saveConfigure(intType,intDay,vcNote);
	}
	
	/**
	 * 
	 * @param taskName
	 * @return int
	 * 通过用户名判断该用户是否存在
	 */
	public int isIntType(Integer intType){
		return populationConfigureDAO.queryByIntType(intType);
	}
	
	/**
	 * 
	 * @param page
	 * @param taskEntity
	 * @return
	 * 查询所有任务
	 */
	public Page<FtbResidentConfig> queryEntity(final Page page){
		return populationConfigureDAO.queryTask(page);
	}
	
	/**
	 * 
	 * @param keys
	 * 
	 * 删除常驻人口配置
	 */
	
	public void deleteConfigure(String nmResidentIds){
		populationConfigureDAO.deleteConfigure(nmResidentIds);
	}
	
	
	
	/**
	 * 
	 * 更新常驻人口配置
	 */
	public void updateConfigure(Long nmResidentId,Integer intDay,String vcNote){
		populationConfigureDAO.updateConfigure(nmResidentId,intDay,vcNote);
	}
	
}
