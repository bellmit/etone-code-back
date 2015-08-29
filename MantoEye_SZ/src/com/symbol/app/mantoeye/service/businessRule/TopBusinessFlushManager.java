package com.symbol.app.mantoeye.service.businessRule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.business.TopBusinessFlushDAO;
import com.symbol.app.mantoeye.entity.business.FtbStatDayBusFlushOrder;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * 业务流量排行
 * @author rankin
 *
 */
//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class TopBusinessFlushManager extends EntityManager<FtbStatDayBusFlushOrder, Integer> {

	@Autowired
	private TopBusinessFlushDAO topBusinessFlushDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected TopBusinessFlushDAO getEntityDao() {
		return topBusinessFlushDAO;
	}
	
	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbStatDayBusFlushOrder> search(final Page<FtbStatDayBusFlushOrder> page,
			final List<PropertyFilter> filters){
		Page<FtbStatDayBusFlushOrder> apage = topBusinessFlushDAO.find(page,filters);
		List<FtbStatDayBusFlushOrder> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
	/**
	 * 查询满足条件的所有数据，因为数据比较多，
	 * 直接在数据库关联查询以提高速度
	 * @param findtype
	 * @param start
	 * @param end
	 * @return
	 */
	public List<FtbStatDayBusFlushOrder> findAll(String findtype, String start, String end){
		return topBusinessFlushDAO.findAll(findtype,start,end);
	}
}


