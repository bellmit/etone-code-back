/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.dao.BasicDao.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-8 下午01:08:04 
 *   LastChange: 2013-11-8 下午01:08:04 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import z.z.w.project.util.common.DataTools;

/**
 * z.z.w.project.test.dao.BasicDao.java
 */
@Repository
public class BasicDao<T> implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-8 下午01:08:08
	 */
	private static final long serialVersionUID = 7384646993854258860L;
	private SessionFactory sessionFactory;

	/**
	 * 統計 <br>
	 * Created on: 2013-11-8 下午02:06:28
	 * 
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	public BigInteger count(final String hql, final Map<String, Object> paramMap) {
		return (BigInteger) getUniqueResult(hql, paramMap);
	}

	/**
	 * 執行一條hql語句 <br>
	 * Created on: 2013-11-8 下午02:03:36
	 * 
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	public int executeUpdate(final String hql,
			final Map<String, Object> paramMap) {
		return createQuery(hql, paramMap).executeUpdate();
	}

	/**
	 * 分頁獲取實體對象列表 <br>
	 * Created on: 2013-11-8 下午02:02:04
	 * 
	 * @param hql
	 * @param paramMap
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPage(final String hql,
			final Map<String, Object> paramMap, final int page, final int rows) {
		return createQuery(hql, paramMap).setFirstResult((page - 1) * rows)
				.setMaxResults(rows).list();
	}

	/**
	 * 獲取實體對象列表 <br>
	 * Created on: 2013-11-8 下午02:00:37
	 * 
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String hql, final Map<String, Object> paramMap) {
		return createQuery(hql, paramMap).list();
	}

	/**
	 * 獲取唯一實體實例 <br>
	 * Created on: 2013-11-8 下午01:59:37
	 * 
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(final String hql, final Map<String, Object> paramMap) {
		return (T) getUniqueResult(hql, paramMap);
	}

	/**
	 * 獲取唯一結果值 <br>
	 * Created on: 2013-11-8 下午01:58:03
	 * 
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	public Object getUniqueResult(final String hql,
			final Map<String, Object> paramMap) {
		return createQuery(hql, paramMap).uniqueResult();
	}

	/**
	 * 創建查詢query <br>
	 * Created on: 2013-11-8 下午01:58:40
	 * 
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	private Query createQuery(final String hql,
			final Map<String, Object> paramMap) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (!DataTools.isEmpty(paramMap)) {
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}

		return query;
	}

	/**
	 * 通過主鍵獲取實體對象 <br>
	 * Created on: 2013-11-8 下午01:54:57
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(final Class<T> clazz, final Serializable id) {
		return (T) this.getCurrentSession().get(clazz, id);
	}

	/**
	 * 保存或更新實體對象 <br>
	 * Created on: 2013-11-8 下午01:53:42
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(final T entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 更新實體對象 <br>
	 * Created on: 2013-11-8 下午01:52:45
	 * 
	 * @param entity
	 */
	public void update(final T entity) {
		this.getCurrentSession().update(entity);
	}

	/**
	 * 刪除實體對象 <br>
	 * Created on: 2013-11-8 下午01:52:06
	 * 
	 * @param entity
	 */
	public void delete(final T entity) {
		this.getCurrentSession().delete(entity);
	}

	/**
	 * 保存實體對象 <br>
	 * Created on: 2013-11-8 下午01:51:23
	 * 
	 * @param entity
	 * @return
	 */
	public Serializable save(final T entity) {
		return this.getCurrentSession().save(entity);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-8 下午01:10:26
	 * 
	 * @return
	 */
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午01:08:36
	 * 
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午01:08:36
	 * 
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
