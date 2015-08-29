package com.symbol.app.mantoeye.dao.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.DimensionEnum;
import com.symbol.app.mantoeye.dto.common.CommonDimension;
import com.symbol.app.mantoeye.dto.flush.DimensionBeans;
import com.symbol.wp.core.util.Common;

/**
 * 查询所有的维表数据
 * 
 * @author rankin
 * 
 */
// Spring DAO Bean的标识
@Repository
public class DimensionDAO extends CommonQueryDAO {

	private static final Log logger = LogFactory.getLog(DimensionDAO.class);

	@Autowired
	private DimensionBeans dimensionBeans;

	/**
	 * 更新Map数据
	 * 
	 * @param list
	 * @param type
	 */
	private void buildMap(List<Object[]> list, DimensionEnum type) {

		Map<Long, String> dimensions = new HashMap<Long, String>();
		Long id;
		String name;
		for (int i = 0; i < list.size(); i++) {
			Object[] f = list.get(i);
			id = Common.StringToLong(f[0] + "");
			name = f[1] + "";
			dimensions.put(id, name);
		}
		dimensionBeans.refreshBeans(type, dimensions);
	}

	// private void buildMapList(List<Object[]> list, DimensionEnum type) {
	// Long typeId;
	// Long busiId;
	// Map<Long, List<Long>> map = new HashMap<Long, List<Long>>();
	// if (list != null && !list.isEmpty()) {
	// for (int i = 0; i < list.size(); i++) {
	// Object[] f = list.get(i);
	// typeId = Common.StringToLong(f[0] + "");
	// busiId = Common.StringToLong(f[1] + "");
	// List l = null;
	// l = (List) map.get(typeId);
	// if (l != null) {
	// l.add(busiId);
	// } else {
	// l = new ArrayList<Long>();
	// l.add(busiId);
	// map.put(typeId, l);
	// }
	// }
	// }
	// dimensionBeans.refreshBusinessBeans(type, map);
	// }

	/**
	 * 查询所有的BSC
	 */
	public void initBsc() {
		String sql = "select intBscId,vcName from dtbBsc";
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			buildMap(list, DimensionEnum.BSC);
		}
	}

	/**
	 * 查询所有的Gsn
	 */
	public void initGsn() {
		String sql = "select intSgsnId,vcName from dtbGsn";
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			buildMap(list, DimensionEnum.GSN);
		}
	}

	/**
	 * 查询所有的Province
	 */
	public void initProvince() {
		String sql = "select intProvinceId,vcName from dtbProvince";
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			buildMap(list, DimensionEnum.省份);
		}
	}

	/**
	 * 查询所有的Country
	 */
	public void initCountry() {
		String sql = "select intCountryId,vcName from dtbCountry";
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			buildMap(list, DimensionEnum.国家);
		}
	}

	/**
	 * 查询所有的City
	 */
	public void initCity() {
		String sql = "select intCityId,vcName from dtbCity";
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			buildMap(list, DimensionEnum.城市);
		}
	}

	/**
	 * 查询所有的SaleArea
	 */
	public void initSaleArea() {
		String sql = "select intSaleAreaId,vcSaleAreaName from dtbSaleArea";
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			buildMap(list, DimensionEnum.营销片区);
		}
	}

	/**
	 * 查询所有的Street
	 */
	public void initStreet() {
		String sql = "select intStreetId,vcName from dtbStreet";
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			buildMap(list, DimensionEnum.街道);
		}
	}

	/**
	 * 查询所有的SubsidiaryCompany
	 */
	public void initSubsidiaryCompany() {
		String sql = "select intBranchId,vcBranchName from dtbSubsidiaryCompany";
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			buildMap(list, DimensionEnum.分公司);
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 查询所有的Country
	 */
	public List<CommonDimension> findAllCountry() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intCountryId,vcName from dtbCountry order by vcName";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 查询所有的Brand
	 */
	public List<CommonDimension> findAllBrand() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intBrandId,vcName from dtbBrand order by vcName";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 查询所有的运营商
	 */
	public List<CommonDimension> findAllLdc() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intIdcId,vcIdc from dtbIdc order by vcIdc";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}

	/**
	 * 查询所有的bsc
	 */
	public List<CommonDimension> findAllBsc() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intBscId,vcName from dtbBsc";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}

	/**
	 * 查询所有的Street
	 */
	public List<CommonDimension> findAllStreet() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intStreetId,vcName from dtbStreet";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}

	/**
	 * 查询所有的SaleArea
	 */
	public List<CommonDimension> findAllSaleArea() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intSaleAreaId,vcSaleAreaName from dtbSaleArea";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}

	/**
	 * 查询所有的Sgsn
	 */
	public List<CommonDimension> findAllSgsn() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intSgsnId,vcName from dtbGsn";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}

	/**
	 * 查询所有的Branch
	 */
	public List<CommonDimension> findAllBranch() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intBranchId,vcBranchName from dtbSubsidiaryCompany";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}

	/**
	 * 查询所有的业务
	 */
	public List<CommonDimension> findAllBussiness() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select nmBussinessId,nmBussinessTypeId,vcBussinessName from dtbBusinessSite";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						Common.StringToLong(objs[1] + ""), (objs[2] + "")));
			}
		}
		return list;
	}
	
//	/**
//	 * 查询所有的城村
//	 */
//	public List<CommonDimension> findAllVillage() {
//		List<CommonDimension> list = new ArrayList<CommonDimension>();
//		String sql = "select intVillageId,vcVillageName from dtbVillage";
//		Object[] objs;
//		List rs = this.getSession().createSQLQuery(sql).list();
//		if (rs != null && rs.size() > 0) {
//			for (int i = 0; i < rs.size(); i++) {
//				objs = (Object[]) rs.get(i);
//				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),(objs[1] + "")));
//			}
//		}
//		return list;
//	}
	
	/**
	 * 查询所有的CGI
	 */
	public List<CommonDimension> findAllCGI() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select vcCGI,vcCgiChName from ftbCgi";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension(Long.parseLong(i+ ""),(objs[1] + "")));
			}
		}
		return list;
	}
	
	/**
	 * 查询所有的营业厅
	 */
	public List<CommonDimension> findAllBusinessHall() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intCompanyId,vcBusinessHall from dtbCompany";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),(objs[1] + "")));
			}
		}
		return list;
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 查询所有的
	 */
	public List<CommonDimension> findAllTcp() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intProtocolTypeId,vcProtocolName from dtbProtocolType order by intProtocolTypeId";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 查询所有的
	 */
	public List<CommonDimension> findAllWap() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intWapId,vcWapName from dtbWapType order by intWapId";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}
	/**
	 * 查询所有的
	 */
	public List<CommonDimension> findAllApnType() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intApnTypeId,vcApnTypeName from dtbApnType order by intApnTypeId";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}

	/**
	 * 查询所有的
	 */
	public List<CommonDimension> findAllUserBelong() {
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		String sql = "select intUserBelongId,vcDesc from dtbUserBelong order by intUserBelongId";
		Object[] objs;
		List rs = this.getSession().createSQLQuery(sql).list();
		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				objs = (Object[]) rs.get(i);
				list.add(new CommonDimension((Long.parseLong(objs[0] + "")),
						(objs[1] + "")));
			}
		}
		return list;
	}
}
