package com.symbol.app.mantoeye.service.businessRule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.business.NextVetorDao;
import com.symbol.app.mantoeye.dao.business.VetorDAO;
import com.symbol.app.mantoeye.dto.business.FtbMainAssistVetorNew;
import com.symbol.app.mantoeye.entity.business.FtbMainAssistVetor;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * 规则配置
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class VetorManager extends EntityManager<FtbMainAssistVetor, Integer> {

	@Autowired
	private VetorDAO vetorDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected VetorDAO getEntityDao() {
		return vetorDAO;
	}

	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbMainAssistVetor> search(final Page<FtbMainAssistVetor> page,
			final List<PropertyFilter> filters) {
		Page<FtbMainAssistVetor> apage = vetorDAO.find(page, filters);
		List<FtbMainAssistVetor> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}

	/**
	 * 通过主规则ID查询改ID关联的辅规则的id
	 * 
	 * @return
	 */
	public Integer[] findAssistIdByMainId(Integer mainid) {
		return vetorDAO.findAssistIdByMainId(mainid);
	}

	/**
	 * 关联辅规则
	 * 
	 * @param ikid
	 * @param asids
	 */
	@Transactional
	public void addAssociate(Integer ikid, Integer[] asids) {
		for (int i = 0; i < asids.length; i++) {
			vetorDAO.addAssociate(ikid, asids[i]);
		}
	}

	/**
	 * 删除辅规则关联
	 * 
	 * @param ikid
	 * @param asids
	 */
	@Transactional
	public void deleteAssociate(Integer ikid, Integer[] asids) {
		for (int i = 0; i < asids.length; i++) {
			vetorDAO.deleteAssociate(ikid, asids[i]);
		}
	}

	private NextVetorDao nextVetorDao;

	public Page<FtbMainAssistVetor> searchForQuery(
			Page<FtbMainAssistVetor> page, List<PropertyFilter> filters) {
		Page<FtbMainAssistVetor> p = new Page<FtbMainAssistVetor>();
		// p = vetorDAO.findForQuery(page, filters);
		// p.setTotalCount(vetorDAO.getTotalCount(filters));
		// p = nextVetorDao.findForQuery(page, filters);
		// p.setTotalCount(nextVetorDao.getTotalCount(filters));

		p = vetorDAO.find(page, filters);
		List<FtbMainAssistVetor> list = p.getResult();
		p.setVresult(list);
		// p.setTotalCount(totalCount)

		return p;
	}

	public VetorDAO getVetorDAO() {
		return vetorDAO;
	}

	public void setVetorDAO(VetorDAO vetorDAO) {
		this.vetorDAO = vetorDAO;
	}

	public NextVetorDao getNextVetorDao() {
		return nextVetorDao;
	}

	@Resource
	public void setNextVetorDao(NextVetorDao nextVetorDao) {
		this.nextVetorDao = nextVetorDao;
	}

}
