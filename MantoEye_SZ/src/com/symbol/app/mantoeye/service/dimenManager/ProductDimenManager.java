package com.symbol.app.mantoeye.service.dimenManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



import com.symbol.app.mantoeye.dao.dimenManager.ProductDAO;
import com.symbol.app.mantoeye.dao.dimenManager.ProductGroupDAO;
import com.symbol.app.mantoeye.dto.dimenManager.Product;
import com.symbol.app.mantoeye.dto.dimenManager.ProductGroup;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDef;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDimen;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDimensGroup;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDimensGroupMapping;
import com.symbol.wp.tools.gtgrid.util.StringUtils;

@Component
public class ProductDimenManager implements DimenManager<Product,ProductGroup>{

	private final Logger logger = LoggerFactory.getLogger(ProductDimenManager.class);

	private ProductDAO productDAO;
	private ProductGroupDAO productGroupDAO;
	private List<ProductDimensGroup> groupList;
	private int pageNum;
	private int pageCount;
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#initPage(int, int)
	 */
	public void initPage(int pageNo, int pageSize) {
		pageCount = pageSize == 0 ? 10 : pageSize;
		pageNum = pageNo == 0 ? 1 : (pageNo - 1) * pageCount;
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#initProduct(com.symbol.modules.common.entity.ProductDimen, int, java.util.List)
	 */
	public List<Product> init(ProductDimen productDimen, int index, List<ProductDef> list) {
		List<Product> productList = new ArrayList<Product>();
		Product product = new Product();
		product.setDimensId(productDimen.getDimensId());
		product.setDimensName(productDimen.getDimensName());
		product.setDimensDesc(productDimen.getDimensDesc());
		int preDimens = productDimen.getPreDimens();
		product.setPreDimens(preDimens);
		String preDimensName = "";
		if (preDimens > 0) {
			preDimensName = productDAO.getProductDimen(preDimens)
					.getDimensName();
		}
		product.setPreDimensName(preDimensName);
		product.setCheckBox("<input id='" + productDimen.getDimensId()
				+ "' name='checkbox' type='checkbox' />");
		product.setOperate("<a href='javascript:addDef("
				+ productDimen.getDimensId() + "," + index + ")'>新增规则</a>");
		List<Product> children = new ArrayList<Product>();
		product.setParentID(0);
		product.setChildren(children);
		product.setAutoExec(true);
		productList.add(product);
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				Product product2 = new Product();
				String serverIp = list.get(i).getServerIp();
				product2.setServerIp(serverIp);
				int port = list.get(i).getPort();
				product2.setPort(port);
				String ip = "";
				if (serverIp != null && !serverIp.equals("")
						&& !serverIp.equals("null")) {
					if (port > 0) {
						ip = serverIp + ":" + port;
					} else {
						ip = serverIp;
					}
				}
				product2.setIp(ip);
				product2.setUrl(list.get(i).getUrl());
				// product2.setCheckBox("<input id='" + productDef.getRecordId()
				// + "' name='checkbox' type='checkbox' />");
				product2.setOperate("<a href='javascript:editDef("
						+ list.get(i).getRecordId() + "," + index 
						+ "," + i + ")'>修改</a>&nbsp;&nbsp;&nbsp;"
						+ "<a href='javascript:delDef(" + list.get(i).getRecordId()
						+ "," + index + "," + i + ")'>删除</a>");
				product2.setParentID(product.getDimensId());
				product2.setAutoExec(false);
				product.getChildren().add(product2);
			}
		}
		return productList;
	}

	// public List<ProductGroup> initProductGroup(ProductDimensGroup
	// productDimensGroup){
	// List<ProductGroup> groupList = new ArrayList<ProductGroup>();
	// ProductGroup productGroup = new ProductGroup();
	// productGroup.setGroupId(productDimensGroup.getGroupId());
	// productGroup.setParent(productDimensGroup.getParent());
	// productGroup.setGroupName(productDimensGroup.getGroupName());
	// productGroup.setCheckBox("<input id='" + productDimensGroup.getGroupId()
	// + "' name='checkbox' type='checkbox' />");
	// productGroup.setOperate("<a href='javascript:editGroup(" +
	// productDimensGroup.getGroupId() + ")'>编辑维度</a>");
	// productGroup.setParentID(0);
	// List<ProductGroup> children = new ArrayList<ProductGroup>();
	// productGroup.setChildren2(children);
	// productGroup.setAutoExec(true);
	// groupList.add(productGroup);
	// productGroup.setChildren2(getProductGroupChild(productDimensGroup.getGroupId()));
	// return groupList;
	// }
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#initProductDimenGroupChild(int)
	 */
	public List<ProductDimensGroup> initProductDimenGroupChild(int groupId){
		List<ProductDimensGroup> childList = new ArrayList<ProductDimensGroup>();
		for (ProductDimensGroup productDimensGroup : groupList) {
			if(productDimensGroup == null)continue;
			if(productDimensGroup.getParent() == groupId){
				childList.add(productDimensGroup);
			}
		}
		return childList;
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductGroupChild(int)
	 */
	public List<ProductGroup> getProductGroupChild(int groupId) {
		List<ProductGroup> productGroupChild = new ArrayList<ProductGroup>();
		List<ProductDimensGroup> list = initProductDimenGroupChild(groupId);
		for (ProductDimensGroup productDimensGroup : list) {
			ProductGroup productGroup = new ProductGroup();
			productGroup.setGroupId(productDimensGroup.getGroupId());
			productGroup.setParent(productDimensGroup.getParent());
			productGroup.setGroupName(productDimensGroup.getGroupName());
			productGroup.setCheckBox("<input id='"
					+ productDimensGroup.getGroupId()
					+ "' name='checkbox2' type='checkbox' />");
			productGroup.setOperate("<a href='javascript:productGrid2("
					+ productDimensGroup.getGroupId() + ")'>编辑维度</a>");
			productGroup.setParentID(productDimensGroup.getParent());
			productGroup.setAutoExec(false);
			productGroup.setChildren2(getProductGroupChild(productDimensGroup
					.getGroupId()));
			productGroupChild.add(productGroup);
		}
		return productGroupChild;
	}
	
	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductChildList()
	 */
	@Transactional(readOnly = true)
	public List<ProductGroup> getGroupChildList() {
		groupList = productGroupDAO.getProductDimenGroupList();
		return getProductGroupChild(0);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductList(java.lang.String, int, int)
	 */
	@Transactional(readOnly = true)
	public List<Product> getDimenList(String query, int pageNo, int pageSize) {
		String dimenIds = "";
		List<ProductDimen> list = null;
		List<Product> productList = new ArrayList<Product>();
		initPage(pageNo, pageSize);
		if(query == null || query.equals("")){
			list = productDAO.getProductDimenList(pageNum, pageCount);
		}else{
			list = productDAO.getProductDimenList(query, pageNum, pageCount);
		}
		
		if(list.size() <=0 || list ==  null) return productList;
		for (ProductDimen productDimen : list) {
			dimenIds += productDimen.getDimensId() + ",";
		}
		List<ProductDef> defList = productDAO.getProductDef(dimenIds.substring(0,dimenIds.length()-1));
		for (int i = 0; i < list.size(); i++) {
			List<ProductDef> tempList = new ArrayList<ProductDef>();
			for (ProductDef productDef : defList) {
				if(list.get(i).getDimensId() == productDef.getDimensId()){
					tempList.add(productDef);
				}
			}
			for (Product product : init(list.get(i),i+1,tempList)) {
				productList.add(product);
			}
		}
		return productList;
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductList(int, java.lang.String, int, int)
	 */
	@Transactional(readOnly = true)
	public List<Product> getDimenList(int groupId, String query, int pageNo, int pageSize) {
		List<ProductDimen> list = null;
		List<Product> productList = new ArrayList<Product>();
		initPage(pageNo, pageSize);
		if(query == null || query.equals("")){
			list = productGroupDAO.getProductDimenList(groupId, pageNum, pageCount);
		}else{
			list = productGroupDAO.getProductDimenList(groupId, query, pageNum, pageCount);
		}
		for (int i = 0; i < list.size(); i++) {
			List<Product> tempList = init(list.get(i),i+1,null);
			for (Product product : tempList) {
				productList.add(product);
			}
		}
		return productList;
	}

	// @Transactional(readOnly = true)
	// public List<ProductGroup> getProductGroupList(int pageNo, int pageSize) {
	// List<ProductGroup> groupList = new ArrayList<ProductGroup>();
	// initPage(pageNo, pageSize);
	// List<ProductDimensGroup> list =
	// productGroupDAO.getProductDimenGroupList(pageNum, pageCount);
	// for (ProductDimensGroup productDimensGroup : list) {
	// List<ProductGroup> tempList = initProductGroup(productDimensGroup);
	// for (ProductGroup productGroup : tempList) {
	// groupList.add(productGroup);
	// }
	// }
	// return groupList;
	// }

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductGroupList()
	 */
	@Transactional(readOnly = true)
	public List<ProductGroup> getDimenGroupList() {
		List<ProductGroup> groupList = new ArrayList<ProductGroup>();
		List<ProductDimensGroup> list = productGroupDAO
				.getProductDimenGroupList();
		for (ProductDimensGroup productDimensGroup : list) {
			if(productDimensGroup == null )continue;
			ProductGroup productGroup = new ProductGroup();
			productGroup.setGroupId(productDimensGroup.getGroupId());
			productGroup.setParent(productDimensGroup.getParent());
			productGroup.setGroupName(productDimensGroup.getGroupName());
			groupList.add(productGroup);
		}
		return groupList;
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDimen(int)
	 */
	@Transactional(readOnly = true)
	public ProductDimen getDimen(int dimenId) {
		return productDAO.getProductDimen(dimenId);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDef(int)
	 */
	@Transactional(readOnly = true)
	public ProductDef getDimenDef(int recordId) {
		return productDAO.getProductDef(recordId);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDimensGroup(int)
	 */
	@Transactional(readOnly = true)
	public ProductDimensGroup getDimenGroup(int groupId) {
		return productGroupDAO.getProductDimensGroup(groupId);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDimenList()
	 */
	@Transactional(readOnly = true)
	public List<?> getDimenList() {
		return productDAO.getProductDimenList();
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDimenList(int)
	 */
	@Transactional(readOnly = true)
	public List<?> getDimenList(int groupId) {
		return productGroupDAO.getProductDimenList(groupId);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDimenGroupList()
	 */
	@Transactional(readOnly = true)
	public List<ProductDimensGroup> getDimensGroupList() {
		return productGroupDAO.getProductDimenGroupList();
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDimenCount(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public int getDimenCount(String query) {
		if(query == null || query.equals("")){
			return productDAO.getProductDimenCount();
		}else{
			return productDAO.getProductDimenCount(query);
		}
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDimenCount(int, java.lang.String)
	 */
	@Transactional(readOnly = true)
	public int getDimenCount(int groupId, String query) {
		if(query == null || query.equals("")){
			return productGroupDAO.getProductDimenCount(groupId);
		}else{
			return productGroupDAO.getProductDimenCount(groupId, query);
		}
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDimenGroupCount()
	 */
	@Transactional(readOnly = true)
	public int getDimenGroupCount() {
		return productGroupDAO.getProductDimenGroupCount();
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#saveProductDimen(com.symbol.modules.dimenManage.dto.Product)
	 */
	@Transactional()
	public void saveDimen(Product product) {
		ProductDimen productDimen = new ProductDimen();
		productDimen.setDimensName(product.getDimensName());
		productDimen.setDimensDesc(product.getDimensDesc());
		productDimen.setPreDimens(product.getPreDimens());
		productDAO.saveProductDimen(productDimen);
//		product.setDimensId(productDimen.getDimensId());
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#updateProductDimen(com.symbol.modules.dimenManage.dto.Product)
	 */
	@Transactional()
	public void updateDimen(Product product) {
		ProductDimen productDimen = new ProductDimen();
		productDimen.setDimensId(product.getDimensId());
		productDimen.setDimensName(product.getDimensName());
		productDimen.setDimensDesc(product.getDimensDesc());
		productDimen.setPreDimens(product.getPreDimens());
		productDAO.updateProductDimen(productDimen);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#deleteProductDimen(int)
	 */
	@Transactional()
	public void deleteDimen(int dimenId) {
		ProductDimen productDimen = productDAO.getProductDimen(dimenId);
		List<ProductDef> list = productDAO.getProductDef(String.valueOf(dimenId));
		for (ProductDef productDef : list) {
			productDAO.deleteProductDef(productDef);
		}
		productDAO.deleteProductDimen(productDimen);
		List<ProductDimen> dimenList = productDAO.getProductDimenList(dimenId);
		for (ProductDimen productDimen2 : dimenList) {
			deleteDimen(productDimen2.getDimensId());
		}
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#saveProductDef(com.symbol.modules.dimenManage.dto.Product)
	 */
	@Transactional()
	public int saveDimenDef(Product product) {
		ProductDef productDef = new ProductDef();
		productDef.setServerIp(product.getServerIp());
		productDef.setPort(product.getPort());
		productDef.setUrl(product.getUrl());
		productDef.setDimensId(product.getDimensId());
		return productDAO.saveProductDef(productDef);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#updateProductDef(com.symbol.modules.dimenManage.dto.Product)
	 */
	@Transactional()
	public void updateDimenDef(Product product) {
		ProductDef productDef = new ProductDef();
		productDef.setRecordId(product.getRecordId());
		productDef.setServerIp(product.getServerIp());
		productDef.setPort(product.getPort());
		productDef.setUrl(product.getUrl());
		productDef.setDimensId(product.getDimensId());
		productDAO.updateProductDef(productDef);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#deleteProductDef(int)
	 */
	@Transactional()
	public void deleteDimenDef(int recordId) {
		productDAO.deleteProductDef(productDAO.getProductDef(recordId));
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#saveProductGroup(com.symbol.modules.dimenManage.dto.ProductGroup)
	 */
	@Transactional()
	public void saveDimenGroup(ProductGroup productGroup) {
		ProductDimensGroup productDimensGroup = new ProductDimensGroup();
		productDimensGroup.setParent(productGroup.getParent());
		productDimensGroup.setGroupName(productGroup.getGroupName());
		productGroupDAO.saveProductDimensGroup(productDimensGroup);
		productGroup.setGroupId(productDimensGroup.getGroupId());
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#updateProductGroup(com.symbol.modules.dimenManage.dto.ProductGroup)
	 */
	@Transactional()
	public void updateDimenGroup(ProductGroup productGroup) {
		ProductDimensGroup productDimensGroup = new ProductDimensGroup();
		productDimensGroup.setGroupId(productGroup.getGroupId());
		productDimensGroup.setParent(productGroup.getParent());
		productDimensGroup.setGroupName(productGroup.getGroupName());
		productGroupDAO.updateProductDimensGroup(productDimensGroup);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#deleteProductGroup(int)
	 */
	@Transactional()
	public void deleteDimenGroup(int groupId) {
		productGroupDAO.deleteProductDimensGroup(productGroupDAO
				.getProductDimensGroup(groupId));
		List<ProductDimensGroupMapping> maplist = productGroupDAO
				.getProductDimenGroupMappingList(groupId);
		for (ProductDimensGroupMapping productDimensGroupMapping : maplist) {
			productGroupDAO
					.deleteProductDimensGroupMapping(productDimensGroupMapping);
		}
		List<ProductDimensGroup> list = initProductDimenGroupChild(groupId);
		for (ProductDimensGroup productDimensGroup : list) {
			deleteDimenGroup(productDimensGroup.getGroupId());
		}
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#saveProductMap(int, int)
	 */
	@Transactional()
	public void saveDimenMap(int groupId, int dimenId) {
		ProductDimensGroupMapping productDimensGroupMapping = new ProductDimensGroupMapping();
		productDimensGroupMapping.setDimensId(dimenId);
		productDimensGroupMapping.setGroupId(groupId);
		productGroupDAO
				.saveProductDimensGroupMapping(productDimensGroupMapping);
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#deleteProductMap(int, java.lang.String)
	 */
	@Transactional()
	public void deleteDimenMap(int groupId, String dimensIds) {
		for (String dimensId : dimensIds.split(",")) {
			productGroupDAO.deleteProductDimensGroupMapping(productGroupDAO
					.getProductDimensGroupMapping(groupId, Integer
							.parseInt(dimensId)));
		}
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductDAO()
	 */
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#setProductDAO(com.symbol.modules.dimenManage.dao.ProductDAO)
	 */
	@Resource
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#getProductGroupDAO()
	 */
	public ProductGroupDAO getProductGroupDAO() {
		return productGroupDAO;
	}

	/* (non-Javadoc)
	 * @see com.symbol.modules.dimenManage.service.DimenManager#setProductGroupDAO(com.symbol.modules.dimenManage.dao.ProductGroupDAO)
	 */
	@Resource
	public void setProductGroupDAO(ProductGroupDAO productGroupDAO) {
		this.productGroupDAO = productGroupDAO;
	}

	public List<ProductGroup> getDimenDefList() {
		// TODO Auto-generated method stub
		return null;
	}

	
//	public void importDoc(File file){
//		
//	}
//	
	
	
	public void importDoc(File file){
		Map<String,List> map = new HashMap<String,List>();
		List list = new ArrayList();
		Map<String,Object> entityMap = new HashMap<String,Object>();
		
		
		jxl.Workbook rwb = null;
		try {
			// 构建Workbook对象 只读Workbook对象
			// 直接从本地文件创建Workbook
			// 从输入流创建Workbook
			InputStream is = new FileInputStream(file);
			rwb = Workbook.getWorkbook(is);
			// Sheet(术语：工作表)就是Excel表格左下角的Sheet1,Sheet2,Sheet3但在程序中
			// Sheet的下标是从0开始的
			// 获取第一张Sheet表
			Sheet rs = rwb.getSheet(0);
			// 获取Sheet表中所包含的总列数
			int rsColumns = rs.getColumns();

			// 获取Sheet表中所包含的总行数
			int rsRows = rs.getRows();
			// 获取指这下单元格的对象引用

			loop:
			for (int i = 1; i < rsRows; i++) {
				ProductGroup group = null;
				ProductDimen p = null;
				for (int j = 2; j < rsColumns; j++) {

					Cell cell = rs.getCell(j, i);
					if (j == 2) {
						p = productDAO.getByName(cell.getContents());
						if(p == null)continue loop;
					} else {
						if(group == null){
							group = productGroupDAO.getByName(cell.getContents());
							if(group == null){
								group = new ProductGroup();
								group.setGroupName(cell.getContents());
								productGroupDAO.save(group);
							}
						}
						else {
							ProductGroup group1 = productGroupDAO.getByName(cell.getContents());
							if(group1 == null){
								group1 = new ProductGroup();
								group1.setGroupName(cell.getContents());
								group1.setParent(group.getGroupId());
								productGroupDAO.save(group1);
							}
							group = group1;
						}
					}
					
				}
				
				saveDimenMap(group.getGroupId(), p.getDimensId());
				
//				list.add(group);

				
				
				
			}
//			map.put("group", list);
			
			
			productDAO.deleteAllDimensDef();
			productDAO.deleteAllDimens();
			
			list = new ArrayList();
			is = new FileInputStream(file);
			rwb = Workbook.getWorkbook(is);
			// Sheet(术语：工作表)就是Excel表格左下角的Sheet1,Sheet2,Sheet3但在程序中
			// Sheet的下标是从0开始的
			// 获取第一张Sheet表
			rs = rwb.getSheet(1);
			// 获取Sheet表中所包含的总列数
			rsColumns = rs.getColumns();

			// 获取Sheet表中所包含的总行数
			rsRows = rs.getRows();
			// 获取指这下单元格的对象引用

		
			for (int i = 1; i < rsRows; i++) {
				Product product = new Product();
				for (int j = 2; j < rsColumns; j++) {

					Cell cell = rs.getCell(j, i);
					if (j == 2) {
						product.setDimensName((null == cell.getContents() ? "" : cell
								.getContents()));
					} else if (j == 3) {
						product.setDimensDesc((null == cell.getContents() ? "" : cell
								.getContents()));
					} else if (j == 4) {
						String ip = (null == cell.getContents() ? "" : cell
								.getContents());
								product.setServerIp(ip);
					} else if (j == 5) {
						product.setUrl((null == cell.getContents() ? "" : cell
								.getContents()));
					}
				}
//					list.add(product);
//					ProductDimen p = productDAO.getByName(product.getDimensName());
					ProductDimen p = (ProductDimen) entityMap.get(product.getDimensName());
					if(p == null){
						saveDimen(product);
						entityMap.put(product.getDimensName(),(ProductDimen)product);
//						product.setDimensId(p.getDimensId());
					}
					else {
						product.setDimensId(p.getDimensId());
					}

					saveDimenDef(product);
					
		
				
			}
			
//			map.put("dimens", list);
		}catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}finally{
				file.delete();
			}
//		return map;
		}

}
