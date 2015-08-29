package com.symbol.app.mantoeye.dao.dimenManager;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.symbol.app.mantoeye.dto.dimenManager.Product;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDef;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDimen;
import com.symbol.wp.modules.orm.hibernate.SimpleHibernateDao;


@Component
public class ProductDAO extends SimpleHibernateDao<Product, Integer> {

	public List<ProductDimen> getProductDimenList(int pageNum, int pageCount) {
		String hql = "from ProductDimen";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(pageNum);
		query.setMaxResults(pageCount);
		return query.list();
	}

	public List<ProductDimen> getProductDimenList(String dimensName, int pageNum, int pageCount) {
		String hql = "from ProductDimen where dimensName like '%" + dimensName + "%'";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(pageNum);
		query.setMaxResults(pageCount);
		return query.list();
	}
	
	public List<ProductDimen> getProductDimenList(int dimenId) {
		String hql = "from ProductDimen where preDimens in (" + dimenId + ")";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public List<ProductDimen> getProductDimenList() {
		String hql = "from ProductDimen";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	public int getProductDimenCount() {
		String hql = "select count(dimensId) from ProductDimen";
		Query query = getSession().createQuery(hql);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public int getProductDimenCount(String dimensName) {
		String hql = "select count(dimensId) from ProductDimen where dimensName like '%" + dimensName + "%'";
		Query query = getSession().createQuery(hql);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public ProductDimen getProductDimen(int dimenId) {
		String hql = "from ProductDimen where dimensId in (" + dimenId + ")";
		Query query = getSession().createQuery(hql);
		return (ProductDimen) query.uniqueResult();
	}

	public ProductDef getProductDef(int recordId) {
		String hql = "from ProductDef where recordId in (" + recordId + ")";
		Query query = getSession().createQuery(hql);
		return (ProductDef) query.uniqueResult();
	}
	
	public List<ProductDef> getProductDef(String dimenIds) {
		String hql = "from ProductDef where dimensId in (" + dimenIds + ")";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

//	public List<ProductDef> getProductDefList(int dimenId) {
//		List<ProductDef> list = new ArrayList<ProductDef>();
//		String sql = "select * from tbProductDef where dimensId in (" + dimenId
//				+ ")";
//		try {
//			Connection conn = SessionFactoryUtils.getDataSource(
//					getSessionFactory()).getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				ProductDef productDef = new ProductDef();
//				productDef.setRecordId(rs.getInt("RecordId"));
//				productDef.setServerIp(rs.getString("ServerIp"));
//				productDef.setPort(rs.getInt("Port"));
//				productDef.setUrl(rs.getString("Url"));
//				productDef.setDimensId(rs.getInt("DimensId"));
//				list.add(productDef);
//			}
//			rs.close();
//			pstmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	// public List getProductList(String dimenIds){
	// String sql =
	// "select a.dimensId,a.dimensName,a.dimensDesc,a.preDimens,b.serverIp,b.port,b.url from tbProductDimens a "
	// +
	// "left join tbProductDef b on a.dimensId = b.dimensId where a.dimensId in ("
	// + dimenIds + ")";
	// Query query = getSession().createSQLQuery(sql);
	// return query.list();
	// }

	public void saveProductDimen(ProductDimen productDimen) {
		// String hql = "select Max(dimensId)+1 from ProductDimen";
		// Query query = getSession().createQuery(hql);
		// int dimensId = Integer.parseInt(query.uniqueResult().toString());
		String sql = "insert into tbProductDimens(dimensName,dimensDesc,preDimens)values('"
				+ productDimen.getDimensName()
				+ "','"
				+ productDimen.getDimensDesc()
				+ "',"
				+ productDimen.getPreDimens() + ")";
		Query sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.executeUpdate();
		String sql2 = "select @@IDENTITY";
		Query sqlQuery2 = getSession().createSQLQuery(sql2);
		productDimen.setDimensId(Integer.parseInt(sqlQuery2.uniqueResult().toString()));
	}

	public void updateProductDimen(ProductDimen productDimen) {
		getSession().update(productDimen);
	}

	public void deleteProductDimen(ProductDimen productDimen) {
		getSession().delete(productDimen);
	}

	public int saveProductDef(ProductDef productDef) {
		String sql1 = "insert into tbProductDef(serverIp,port,url,dimensId)values('"
				+ productDef.getServerIp()
				+ "',"
				+ productDef.getPort()
				+ ",'"
				+ productDef.getUrl() + "'," + productDef.getDimensId() + ");"; 
		String sql2 = "select @@IDENTITY";
		Query sqlQuery1 = getSession().createSQLQuery(sql1);
		sqlQuery1.executeUpdate();
		Query sqlQuery2 = getSession().createSQLQuery(sql2);
		return Integer.parseInt(sqlQuery2.uniqueResult().toString());
	}

	public void updateProductDef(ProductDef productDef) {
		getSession().update(productDef);
	}

	public void deleteProductDef(ProductDef productDef) {
		getSession().delete(productDef);
	}
	
	
	public ProductDimen getByName(String name){
		String hql = "from ProductDimen where dimensName = '"+name+"'";
	Query query = getSession().createQuery(hql);
	if(query.list() == null || query.list().size() == 0)return null;
	return (ProductDimen) query.list().get(0);
	}
	
	
	public void deleteAllDimens(){
		String sql = "delete from ProductDimen";
	Query sqlQuery = getSession().createQuery(sql);
	sqlQuery.executeUpdate();
	}
	
	public void deleteAllDimensDef(){
		String sql = "delete from ProductDef";
	Query sqlQuery = getSession().createQuery(sql);
	sqlQuery.executeUpdate();
	}

}
