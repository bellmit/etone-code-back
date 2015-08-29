package com.symbol.app.mantoeye.service.businessRule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.business.BusinessAssistVetorDAO;
import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;
//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BusinessAssistVetorManager extends EntityManager<FtbBusinessAssistVetor, Integer> {

		@Autowired
		private BusinessAssistVetorDAO businessAssistVetorDAO;
		
		/**
		 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
		 */
		@Override
		protected BusinessAssistVetorDAO getEntityDao() {
			return businessAssistVetorDAO;
		}
		
		/**
		 * 重写父类的方法，把View的list写进去
		 */
		@Override
		@Transactional(readOnly = true)
		public Page<FtbBusinessAssistVetor> search(final Page<FtbBusinessAssistVetor> page,
				final List<PropertyFilter> filters) {
			Page<FtbBusinessAssistVetor> apage = businessAssistVetorDAO.find(page, filters);
			List<FtbBusinessAssistVetor> dateList = apage.getResult();
			apage.setVresult(dateList);
			return apage;
		}
		
		/**
		 * 保存
		 * @param entiy
		 */
		public void saveEntity(FtbBusinessAssistVetor entity,Integer busiId){
			businessAssistVetorDAO.saveEntity(entity, busiId);
		}
		@Transactional(readOnly = true)
		public boolean isAllPropertyUnique(FtbBusinessAssistVetor entity,Integer busiId,Integer oldkey) {			
			return businessAssistVetorDAO.isAllPropertyUnique(entity,busiId,oldkey);
		}
		/**
		 * 通过规则id获取业务名称
		 * @param ids
		 * @return
		 */
		public String getBusiNameByIds(String ids){
			String[] idArray = ids.split(",");
			Long [] lidArray = Common.StringArrayToLongArray(idArray);
			return businessAssistVetorDAO.getBusiNameByIds(lidArray);
		}
		
	}