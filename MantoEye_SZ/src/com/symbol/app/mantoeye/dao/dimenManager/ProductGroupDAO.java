package com.symbol.app.mantoeye.dao.dimenManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Component;

import com.symbol.app.mantoeye.dto.dimenManager.ProductGroup;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDimen;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDimensGroup;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDimensGroupMapping;
import com.symbol.wp.modules.orm.hibernate.SimpleHibernateDao;




@Component
public class ProductGroupDAO extends
		SimpleHibernateDao<ProductGroup, Integer> {

	// public List<ProductDimensGroup> getProductDimenGroupList(int pageNum, int
	// pageCount) {
	// String hql = "from ProductDimensGroup where parent = 0";
	// Query query = getSession().createQuery(hql);
	// query.setFirstResult(pageNum);
	// query.setMaxResults(pageCount);
	// return query.list();
	// }

	public List<ProductDimen> getProductDimenList(int groupId, int pageNum,
			int pageCount) {
		String hql = "from ProductDimen where dimensId in ("
				+ "select dimensId from ProductDimensGroupMapping where groupId in ("
				+ groupId + "))";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(pageNum);
		query.setMaxResults(pageCount);
		return query.list();
	}
	
	public List<ProductDimen> getProductDimenList(int groupId, String dimensName,
			int pageNum, int pageCount) {
		String hql = "from ProductDimen where dimensId in ("
				+ "select dimensId from ProductDimensGroupMapping where groupId in ("
				+ groupId + ") and dimensName like '%" + dimensName + "%')";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(pageNum);
		query.setMaxResults(pageCount);
		return query.list();
	}

	public List<ProductDimen> getProductDimenList(int groupId) {
		String hql = "from ProductDimen where dimensId not in ("
				+ "select dimensId from ProductDimensGroupMapping where groupId in ("
				+ groupId + "))";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	
	
	
	public List<ProductDimensGroup> getProductDimenGroupList() {
		String hql = "from ProductDimensGroup";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public int getProductDimenCount(int groupId) {
		String hql = "select count(dimensId) from ProductDimen where dimensId in ("
				+ "select dimensId from ProductDimensGroupMapping where groupId in ("
				+ groupId + "))";
		Query query = getSession().createQuery(hql);
		return Integer.parseInt(query.uniqueResult().toString());
	}
	
	public int getProductDimenCount(int groupId, String dimensName) {
		String hql = "select count(dimensId) from ProductDimen where dimensId in ("
				+ "select dimensId from ProductDimensGroupMapping where groupId in ("
				+ groupId + ") and dimensName like '%" + dimensName + "%')";
		Query query = getSession().createQuery(hql);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public int getProductDimenGroupCount() {
		String hql = "select count(groupId) from ProductDimensGroup";
		Query query = getSession().createQuery(hql);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public ProductDimensGroup getProductDimensGroup(int groupId) {
		String hql = "from ProductDimensGroup where groupId in (" + groupId
				+ ")";
		Query query = getSession().createQuery(hql);
		return (ProductDimensGroup) query.uniqueResult();
	}

	public ProductDimensGroupMapping getProductDimensGroupMapping(int groupId,
			int dimensId) {
		String hql = "from ProductDimensGroupMapping where groupId in ("
				+ groupId + ") " + "and dimensId in (" + dimensId + ")";
		Query query = getSession().createQuery(hql);
		return (ProductDimensGroupMapping) query.uniqueResult();
	}

//	public List<ProductDimensGroup> getProductDimenGroupChild(int groupId) {
//		List<ProductDimensGroup> list = new ArrayList<ProductDimensGroup>();
//		String sql = "select * from tbProductDimensGroup where parent in ("
//				+ groupId + ")";
//		try {
//			Connection conn = SessionFactoryUtils.getDataSource(
//					getSessionFactory()).getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				ProductDimensGroup productDimensGroup = new ProductDimensGroup();
//				productDimensGroup.setGroupId(rs.getInt("GroupId"));
//				productDimensGroup.setParent(rs.getInt("Parent"));
//				productDimensGroup.setGroupName(rs.getString("GroupName"));
//				list.add(productDimensGroup);
//			}
//			rs.close();
//			pstmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	public List<ProductDimensGroupMapping> getProductDimenGroupMappingList(
			int groupId) {
		List<ProductDimensGroupMapping> list = new ArrayList<ProductDimensGroupMapping>();
		String sql = "select * from tbProductDimensGroupMapping where groupId in ("
				+ groupId + ")";
		try {
			Connection conn = SessionFactoryUtils.getDataSource(
					getSessionFactory()).getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductDimensGroupMapping productDimensGroupMapping = new ProductDimensGroupMapping();
				productDimensGroupMapping.setRecordId(rs.getInt("RecordId"));
				productDimensGroupMapping.setDimensId(rs.getInt("DimensId"));
				productDimensGroupMapping.setGroupId(rs.getInt("GroupId"));
				list.add(productDimensGroupMapping);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// public List getGroupList(String groupIds){
	// String sql =
	// "select a.groupId,a.parent,a.groupName,c.dimensId,c.dimensName,c.dimensDesc from tbProductDimensGroup a "
	// +
	// "left join tbProductDimensGroupMapping b on a.groupId = b.groupId " +
	// "left join tbProductDimens c  on b.dimensId = c.dimensId where a.groupId in ("
	// + groupIds + ")";
	// Query query = getSession().createSQLQuery(sql);
	// return query.list();
	// }

	public void saveProductDimensGroup(ProductDimensGroup productDimensGroup) {
		// String hql = "select Max(groupId)+1 from ProductDimensGroup";
		// Query query = getSession().createQuery(hql);
		// int groupId = Integer.parseInt(query.uniqueResult().toString());
		String sql = "insert into tbProductDimensGroup(parent,groupName)values("
				+ productDimensGroup.getParent()
				+ ",'"
				+ productDimensGroup.getGroupName() + "')";
		Query sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.executeUpdate();
	}

	public void updateProductDimensGroup(ProductDimensGroup productDimensGroup) {
		getSession().update(productDimensGroup);
	}

	public void deleteProductDimensGroup(ProductDimensGroup productDimensGroup) {
		getSession().delete(productDimensGroup);
	}

	public void saveProductDimensGroupMapping(
			ProductDimensGroupMapping productDimensGroupMapping) {
		String sql = "insert into tbProductDimensGroupMapping(dimensId,groupId)values("
				+ productDimensGroupMapping.getDimensId()
				+ ","
				+ productDimensGroupMapping.getGroupId() + ")";
		Query sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.executeUpdate();
	}

	public void deleteProductDimensGroupMapping(
			ProductDimensGroupMapping productDimensGroupMapping) {
		getSession().delete(productDimensGroupMapping);
	}

	
	
	public ProductGroup getByName(String name){
		String hql = "from ProductDimensGroup where groupName = '"+name+"'";
	Query query = getSession().createQuery(hql);
	if(query.list().size() == 0 || query.list() == null)return null;
	return (ProductGroup) query.list().get(0);
	}
}
