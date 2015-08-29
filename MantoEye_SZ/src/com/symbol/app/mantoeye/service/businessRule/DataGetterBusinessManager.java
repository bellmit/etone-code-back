package com.symbol.app.mantoeye.service.businessRule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.business.DataGetterBusinessDAO;
import com.symbol.app.mantoeye.dto.flush.TestFlush;
import com.symbol.app.mantoeye.entity.business.FtbDataGetterBusiness;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;
/**
 * 拨测结果
 * @author rankin
 *
 */
//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class DataGetterBusinessManager extends EntityManager<FtbDataGetterBusiness, Long> {

	@Autowired
	private DataGetterBusinessDAO dataGetterBusinessDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected DataGetterBusinessDAO getEntityDao() {
		return dataGetterBusinessDAO;
	}
	
	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbDataGetterBusiness> search(final Page<FtbDataGetterBusiness> page,
			final List<PropertyFilter> filters) {
		Page<FtbDataGetterBusiness> apage = dataGetterBusinessDAO.find(page, filters);
		List<FtbDataGetterBusiness> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
	/**
	 * 获取用户的流量
	 * @return
	 */
	public List<TestFlush> getMsisdnFlush(Long taskid){
		return dataGetterBusinessDAO.getMsisdnFlush(taskid);
	}
	/**
	 * 获取用户各业务的流量
	 * @return
	 */
	public List<TestFlush> getMsisdnBusiFlush(Long taskid,String msisdn){
		return dataGetterBusinessDAO.getMsisdnBusiFlush(taskid, msisdn);
	}
}
