package com.symbol.app.mantoeye.dao.mms;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.mms.SpPortControlBean;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 彩信端口管理DAO
 * <p>
 * 完成端口的增刪改查操作
 * 
 * @author Jane Wu
 * 
 */
@Repository
@SuppressWarnings("unchecked")
public class SpPortControlDao extends HibernateDao {

	/**
	 * 添加新的彩信端口信息操作
	 * 
	 * @param bean
	 */
	public void insert(SpPortControlBean bean) {

		String sql = buildInsertSql();

		this.getSession().createSQLQuery(sql).setString(0, bean.getSpPort())
				.setString(1, bean.getBeLong()).setString(2,
						bean.getCompanyName()).executeUpdate();

	}

	/**
	 * 构建更新操作SQL
	 * 
	 * @param bean
	 * @return
	 */
	private String buildInsertSql() {

		StringBuffer sql = new StringBuffer(200);

		sql.append(" insert into ftbSpPortMap");
		sql.append(" (nmSpPort,vcBelong,vcCompanyName)");
		sql.append(" values(?,?,?)");

		return sql.toString();
	}

	/**
	 * 删除指定ID的彩信端口记录数据
	 * 
	 * @param spPortMapId
	 */
	public void delete(long spPortMapId) {

		String sql = buildDeleteSql();

		this.getSession().createSQLQuery(sql).setParameter(0, spPortMapId)
				.executeUpdate();

	}

	/**
	 * 根据指定的端口号删除记录
	 * 
	 * @param spPort
	 */
	public void delete(String spPort) {

		String sql = buildDeleteBySpPortSql();

		this.getSession().createSQLQuery(sql).setParameter(0, spPort)
				.executeUpdate();

	}

	/**
	 * 构建删除操作SQL
	 * 
	 * @return
	 */
	private String buildDeleteSql() {
		String sql = new String();

		sql = " delete from ftbSpPortMap where nmSpPortMapId=?";

		return sql;
	}

	private String buildDeleteBySpPortSql() {
		String sql = new String();

		sql = " delete from ftbSpPortMap where nmSpPort=?";

		return sql;
	}

	/**
	 * 更新指定ID的彩信端口数据信息
	 * 
	 * @param bean
	 */
	public void update(SpPortControlBean bean) {

		String sql = buildUpdateSql();

		this.getSession().createSQLQuery(sql).setParameter(0, bean.getBeLong())
				.setParameter(1, bean.getCompanyName()).setParameter(2,
						bean.getSpPort()).executeUpdate();

	}

	/**
	 * 构建更新操作的SQL
	 * 
	 * @return
	 */
	private String buildUpdateSql() {
		StringBuffer sql = new StringBuffer(150);

		sql.append(" update ftbSpPortMap fspm");
		sql.append(" set fspm.vcBelong=? ,");
		sql.append(" fspm.vcCompanyName=?");
		sql.append(" where fspm.nmSpPort=?");

		return sql.toString();
	}

	/**
	 * 查询所有符合条件的数据
	 * 
	 * @param parameters
	 *            条件参数集合
	 * @return
	 */
	public List<SpPortControlBean> selectAll(Map<String, String> paremeters) {
		List<SpPortControlBean> list = null;

		String sql = buildSelectSql(paremeters);

		sql += buildOrderBySql();

		Query query = getQueryObject(sql);

		list = query.list();

		return list;
	}
	/**
	 * 查询所有符合条件的数据
	 * 
	 * @param parameters
	 *            条件参数集合
	 * @return
	 */
	public List<SpPortControlBean> selectAllForImport(Map<String, String> paremeters) {
		List<SpPortControlBean> list = null;
		
		StringBuffer sbql = new StringBuffer(150);

		sbql.append(" select fspm.nmSpPort spPort,");
		sbql.append(" fspm.vcBelong beLong,");
		sbql.append(" fspm.vcCompanyName companyName");
		sbql.append(" from ftbSpPortMap fspm");
		sbql.append(" where 1=1");

		Set<String> keySet = paremeters.keySet();
		for (String key : keySet) {
			sbql.append(" and fspm.").append(key);
			sbql.append(" = '").append(paremeters.get(key));
			sbql.append("' ");
		}

		String sql =sbql.toString();

		sql += buildOrderBySql();

		Query query = getQueryObject(sql);

		list = query.list();

		return list;
	}

	/**
	 * 构建ORDER BY SQL
	 * 
	 * @return
	 */
	private String buildOrderBySql() {
		String sql = " order by fspm.nmSpPort";
		return sql;
	}

	/**
	 * 构建条件查询SQL
	 * 
	 * @param paremeters
	 * @return
	 */
	private String buildSelectSql(Map<String, String> paremeters) {
		StringBuffer sql = new StringBuffer(150);

		sql.append(" select fspm.nmSpPort spPort,");
		sql.append(" fspm.vcBelong beLong,");
		sql.append(" fspm.vcCompanyName companyName");
		sql.append(" from ftbSpPortMap fspm");
		sql.append(" where 1=1");

		Set<String> keySet = paremeters.keySet();
		for (String key : keySet) {
			sql.append(" and fspm.").append(key);
			sql.append(" like '%").append(paremeters.get(key));
			sql.append("%' ");
		}

		return sql.toString();
	}

	/**
	 * 查询符合条件的所有记录的总数
	 * 
	 * @param paremeters
	 * @return
	 */
	public int getTotalCount(Map<String, String> paremeters) {
		String sql = buildTotalCount(paremeters);

		Object count = this.getSession().createSQLQuery(sql).uniqueResult();

		return Integer.valueOf(count.toString());
	}

	/**
	 * 构建查询总记录数的SQL
	 * 
	 * @param paremeters
	 * @return
	 */
	private String buildTotalCount(Map<String, String> paremeters) {
		String sql = " select count(*)";

		sql += " from (";

		sql += buildSelectSql(paremeters);

		sql += " ) as totalCount";

		return sql;
	}

	/**
	 * 分页查询符合条件的记录
	 * 
	 * @param paremeters
	 * @return
	 */
	public List<SpPortControlBean> selectByPage(Page page,
			Map<String, String> paremeters) {
		List<SpPortControlBean> list = null;

		String sql = buildSelectSql(paremeters);

		sql += buildOrderBySql();

		Query query = getQueryObject(sql);

		list = query.setFirstResult(page.getFirst()).setMaxResults(
				page.getPageSize()).list();
		// list = query.setFirstResult(3).setMaxResults(2).list();

		return list;
	}

	/**
	 * 得到整合了实体BEAN的QUERY对象
	 * 
	 * @return
	 */
	private Query getQueryObject(String sql) {
		Query query = this.getSession().createSQLQuery(sql).addScalar("spPort",
				Hibernate.STRING).addScalar("beLong", Hibernate.STRING)
				.addScalar("companyName", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(SpPortControlBean.class));

		return query;
	}

	/**
	 * 根据指定的彩信端口号更新记录
	 * 
	 * @param bean
	 */
	public void updateBySpPort(SpPortControlBean bean) {

		String sql = buildUpdateBySpPort();

		this.getSession().createSQLQuery(sql).setString(0, bean.getBeLong())
				.setString(1, bean.getCompanyName()).setString(2,
						bean.getSpPort()).executeUpdate();

	}

	/**
	 * 构建根据制定端口号更新记录的UPDATE SQL
	 * 
	 * @return
	 */
	private String buildUpdateBySpPort() {
		StringBuffer sql = new StringBuffer(100);
		sql.append(" update ftbSpPortMap fspm");
		sql.append(" set fspm.vcBelong=? ,");
		sql.append(" fspm.vcCompanyName=?");
		sql.append(" where fspm.nmSpPort=?");
		return sql.toString();
	}

	/**
	 * 根据制定ID查询数据
	 * 
	 * @param spPortMapId
	 * @return
	 */
	public SpPortControlBean selectBySpPort(String spPort) {
		String sql = buildSelectBySpPort();
		// String sql = buildSelectById();

		SpPortControlBean bean = (SpPortControlBean) this.getSession()
				.createSQLQuery(sql).addScalar("spPortMapId", Hibernate.LONG)
				.addScalar("spPort", Hibernate.STRING).addScalar("companyName",
						Hibernate.STRING).addScalar("beLong", Hibernate.STRING)
				.setParameter(0, spPort).setResultTransformer(
						Transformers.aliasToBean(SpPortControlBean.class))
				.uniqueResult();

		return bean;
	}

	/**
	 * 构建通过ID查询记录的SQL
	 * 
	 * @return
	 */
	private String buildSelectById() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select nmSpPortMapId spPortMapId,");
		sql.append(" nmSpPort spPort,vcCompanyName companyName,");
		sql.append(" vcBelong beLong");
		sql.append(" from ftbSpPortMap");
		sql.append(" where nmSpPortMapId=?");
		return sql.toString();
	}

	private String buildSelectBySpPort() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select nmSpPortMapId spPortMapId,");
		sql.append(" nmSpPort spPort,vcCompanyName companyName,");
		sql.append(" vcBelong beLong");
		sql.append(" from ftbSpPortMap");
		sql.append(" where nmSpPort=?");
		return sql.toString();
	}

}
