package com.symbol.wp.modules.orm.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.utils.ReflectionUtils;

/**
 * 默认的领域对象业务管理类基类,提供默认的泛型DAO成员变量.
 * 
 * @param <T>
 *            领域对象类型
 * @param <PK>
 *            领域对象的主键类型
 */
@Transactional
public class EntityManagerOld<T, PK extends Serializable> {

	protected HibernateDaoOld<T, PK> entityDao;// 默认的泛型DAO成员变量.

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 通过注入的sessionFactory初始化默认的泛型DAO成员变量.
	 */
	@SuppressWarnings("unchecked")
	//@Autowired
	//@Qualifier("sessionFactory")
	@Resource(name="sessionFactoryOld")
	public void setSessionFactory(final SessionFactory sessionFactory) {
		Class<T> entityClass = ReflectionUtils
				.getSuperClassGenricType(getClass());
		entityDao = new HibernateDaoOld<T, PK>(sessionFactory, entityClass);
	}

	protected HibernateDaoOld<T, PK> getEntityDao() {
		return entityDao;
	}

	// CRUD函数 //

	@Transactional(readOnly = true)
	public T get(final PK id) {
		return getEntityDao().get(id);
	}

	@Transactional(readOnly = true)
	public Page<T> getAll(final Page<T> page) {
		return getEntityDao().getAll(page);
	}

	@Transactional(readOnly = true)
	public List<T> getAll() {
		return getEntityDao().getAll();
	}
	@Transactional(readOnly = true)
	public List<T> find(final List<PropertyFilter> filters) {
		System.out.println("<------------------connect EntityManagerOld------------------------>");
		return getEntityDao().find(filters);
	}

	@Transactional(readOnly = true)
	public Page<T> search(final Page<T> page, final List<PropertyFilter> filters) {
		System.out.println("<------------------@Override super search------------------------>");
		return getEntityDao().find(page, filters);
	}

	/**
	 * 保存新增或编辑后的数据
	 * 
	 * @param entity
	 */
	public void save(final T entity) {
		getEntityDao().save(entity);
	}

	/**
	 * 通过主键删除数据
	 * 
	 * @param id
	 */
	public void delete(final PK id) {
		getEntityDao().delete(id);
	}

	/**
	 * 通过主键的数组删除数据
	 * 
	 * @param ids
	 */
	public void deleteByKeys(final PK[] ids) {
		for (PK id : ids) {
			getEntityDao().delete(id);
		}
	}
	/**
	 * 判断数据是否是唯一的
	 * @param propertyName 需要判断的数据的字段名称（Hibernate映射后的名称）
	 * @param value 字段的值
	 * @param oldvalue 字段原来的值（编辑用，如果是添加则设为空）
	 * @return
	 */
	public boolean isPropertyUnique(String propertyName, Object value, Object oldvalue) {

		return getEntityDao().isPropertyUnique(propertyName, value, oldvalue);
	}

}
