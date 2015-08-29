package com.symbol.app.mantoeye.service.businessRule;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.business.BusinessMainVetorDAO;
import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

@Service
@Transactional
public class BusinessMainVetorManager extends EntityManager<FtbBusinessMainVetor, Integer> {

	@Autowired
	private BusinessMainVetorDAO businessMainVetorDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BusinessMainVetorDAO getEntityDao() {
		return businessMainVetorDAO;
	}
	
	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbBusinessMainVetor> search(final Page<FtbBusinessMainVetor> page,
			final List<PropertyFilter> filters) {
		Page<FtbBusinessMainVetor> apage = businessMainVetorDAO.find(page, filters);
		List<FtbBusinessMainVetor> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
	
	/**
	 * 保存
	 * @param entiy
	 */
	public void saveEntity(FtbBusinessMainVetor entity,Integer busiId){
		businessMainVetorDAO.saveEntity(entity, busiId);
	}
	/**
	 * 保存
	 * @param entiy
	 */
	public void mutilSaveEntity(String[] ipArray,Integer busiId){
		FtbBusinessMainVetor entity;
		for(int i=0;i<ipArray.length;i++){
			if(Common.judgeString(ipArray[i])){
				entity = new FtbBusinessMainVetor();
				entity.setVcServerIp(ipArray[i]);
				businessMainVetorDAO.saveEntity(entity, busiId);
			}
		}
	}
	@Transactional(readOnly = true)
	public boolean isAllPropertyUnique(FtbBusinessAssistVetor entity,Integer busiId,Integer oldkey) {			
		return businessMainVetorDAO.isAllPropertyUnique(entity,busiId,oldkey);
	}
	/**
	 * 通过规则id获取业务名称
	 * @param ids
	 * @return
	 */
	public Map<String,String> getBusiNameByIds(String ids){
		return businessMainVetorDAO.getBusiNameByIds(ids);
	}
}