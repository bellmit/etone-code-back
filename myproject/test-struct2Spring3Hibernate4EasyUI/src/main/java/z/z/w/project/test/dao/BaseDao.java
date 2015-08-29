/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.dao.BaseDao.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 下午12:12:06 
 *   LastChange: 2013-11-3 下午12:12:06 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.dao.BaseDao.java
 */
public class BaseDao<T> implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-3 下午02:36:46
	 */
	private static final long serialVersionUID = 4422082510987714796L;
	private SessionFactory sessionFactory;

	/**
	 * 查詢所有記錄 <br>
	 * Created on: 2013-11-3 下午04:24:59
	 * 
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findData(String hql, Map<String, Object> paramMap) {
		return getQuery(hql, paramMap).list();
	}

	/**
	 * 查詢分頁記錄 <br>
	 * Created on: 2013-11-3 下午01:11:07
	 * 
	 * @param paramMap
	 * @param intPage
	 *            (當前頁-1)*每頁顯示記錄數
	 * @param intRow
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findDataByPage(String hql, Map<String, Object> paramMap,
			int intPage, int intRow) {
		return getQuery(hql, paramMap).setFirstResult(intPage)
				.setMaxResults(intRow).list();
	}

	/**
	 * 查詢總記錄 <br>
	 * Created on: 2013-11-3 下午01:15:27
	 * 
	 * @param paramMap
	 * @return
	 */
	public Long count(String hql, Map<String, Object> paramMap) {
		return (Long) getQuery(hql, paramMap).uniqueResult();
	}

	/**
	 * 保存記錄 <br>
	 * Created on: 2013-11-3 下午01:20:43
	 * 
	 * @param t
	 * @return
	 */
	public Serializable save(T t) {
		return this.getCurrentSession().save(t);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-3 下午01:22:23
	 * 
	 * @param t
	 */
	public void delete(T t) {
		this.getCurrentSession().delete(t);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-3 下午01:22:20
	 * 
	 * @param t
	 */
	public void update(T t) {
		this.getCurrentSession().update(t);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-3 下午01:22:15
	 * 
	 * @param t
	 */
	public void saveOrUpdate(T t) {
		this.getCurrentSession().saveOrUpdate(t);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-3 下午04:25:29
	 * 
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	public Query getQuery(String hql, Map<String, Object> paramMap) {
		LogTools.getLogger(getClass()).debug("---paramMap---[{}]", paramMap);
		Query query = this.getCurrentSession().createQuery(hql);
		if (!DataTools.isEmpty(paramMap)) {
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}

		return query;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-3 下午12:13:37
	 * 
	 * @return
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午12:12:24
	 * 
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午12:12:39
	 * 
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
