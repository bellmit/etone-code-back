package com.symbol.app.mantoeye.web.action.dimenManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import com.symbol.app.mantoeye.dto.dimenManager.Product;
import com.symbol.app.mantoeye.dto.dimenManager.ProductGroup;
import com.symbol.app.mantoeye.entity.dimenManager.Def;
import com.symbol.app.mantoeye.entity.dimenManager.Dimen;
import com.symbol.app.mantoeye.entity.dimenManager.DimenGroup;
import com.symbol.app.mantoeye.entity.dimenManager.ProductDimensGroup;
import com.symbol.app.mantoeye.service.dimenManager.DimenManager;
import com.symbol.app.mantoeye.util.FlexiGridDataContainer;
import com.symbol.app.mantoeye.util.FlexiGridRow;


@Component
@Namespace("/sysdata/dimenManage")
@Results( {
		@Result(name = "productDimen", location = "/sysdata/dimenManage/dimenManage_product.jsp"),
		@Result(name = "productDimenGroup", location = "/sysdata/dimenManage/dimenManage_productGroup.jsp"),
		@Result(name = "productIndex", location = "/sysdata/dimenManage/dimenManage_index.jsp")

//		@Result(name = "terminalDimen", location = "/modules/dimenManage/dimenManage_terminal.jsp"),
//		@Result(name = "terminalDimenGroup", location = "/modules/dimenManage/dimenManage_terminalGroup.jsp"),
//		@Result(name = "areaDimen", location = "/modules/dimenManage/dimenManage_area.jsp"),
//		@Result(name = "areaDimenGroup", location = "/modules/dimenManage/dimenManage_areaGroup.jsp"),
//		@Result(name = "timeDimen", location = "/modules/dimenManage/dimenManage_time.jsp"),
//		@Result(name = "timeDimenGroup", location = "/modules/dimenManage/dimenManage_timeGroup.jsp"),


})
public class DimenManageAction extends ActionSupport {

	protected final Logger logger = LoggerFactory.getLogger(DimenManageAction.class);

//	private DimenManager areaDimenManager;
	private DimenManager productDimenManager;
//	private DimenManager terminalDimenManager;
//	private DimenManager timeDimenManager;
	
	private File myFile;
//	private String fileName;



	

	private int dimensId;
	private String dimensIds;
	private int groupId;
	private String groupIds;
	private int page;
	private Product product;
	private ProductGroup productGroup;
	private String query;
	private int recordId;
	private int rp;

	private int typeId;

	
	
	
	public int getDimensId() {
		return dimensId;
	}
	public String getDimensIds() {
		return dimensIds;
	}

	public int getGroupId() {
		return groupId;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public int getPage() {
		return page;
	}

	public Product getProduct() {
		return product;
	}

	public DimenManager getProductDimenManager() {
		return productDimenManager;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public String getQuery() {
		return query;
	}

	public int getRecordId() {
		return recordId;
	}

	public int getRp() {
		return rp;
	}



	public int getTypeId() {
		return typeId;
	}


	public void setDimensId(int dimensId) {
		this.dimensId = dimensId;
	}

	public void setDimensIds(String dimensIds) {
		this.dimensIds = dimensIds;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public void setPage(int page) {
		this.page = page;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	
	
	
	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	
//
//	public String getFileName() {
//		return fileName;
//	}
//
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}

	@Resource(name="productDimenManager")
	public void setProductDimenManager(DimenManager productDimenManager) {
		this.productDimenManager = productDimenManager;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}




	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	


	public void deleteProductDef() {
		productDimenManager.deleteDimenDef(recordId);
	}

	public void deleteProductDimen() {
		for (String dimensId : dimensIds.split(",")) {
			productDimenManager.deleteDimen(Integer.parseInt(dimensId));
		}
	}
	
	public void deleteProductGroup() {
		for (String groupId : groupIds.split(",")) {
			productDimenManager.deleteDimenGroup(Integer.parseInt(groupId));
		}
	}

	public void deleteProductMap() {
		productDimenManager.deleteDimenMap(groupId, dimensIds);
	}
	
	

	public String dimenAjax() {
		String result = null;
		switch (typeId) {
		case 0:
			result = "productDimen";
			break;
		case 1:
			result = "productDimenGroup";
			break;
		case 2:
			result = "terminalDimen";
			break;
		case 3:
			result = "terminalDimenGroup";
			break;
		case 4:
			result = "areaDimen";
			break;
		case 5:
			result = "areaDimenGroup";
			break;
		case 6:
			result = "timeDimen";
			break;
		case 7:
			result = "timeDimenGroup";
			break;
	}
		return result;
	}


	public void editProductDef() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Def product = productDimenManager.getDimenDef(recordId);
		JSONObject msg = JSONObject.fromObject(product);
		out.print(msg);
	}

	public void editProductDimen() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Dimen product = productDimenManager.getDimen(dimensId);
		JSONObject msg = JSONObject.fromObject(product);
		out.print(msg);
	}

	public void editProductGroup() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		DimenGroup productGroup = productDimenManager
				.getDimenGroup(groupId);
		((ProductDimensGroup) productGroup).setChildren(null);
		JSONObject msg = JSONObject.fromObject(productGroup);
		out.print(msg);
	}

	

	public void queryProductDimen() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		FlexiGridDataContainer container = new FlexiGridDataContainer();
		List<Product> list = productDimenManager.getDimenList(query, page, rp);
		int count = productDimenManager.getDimenCount(query);
		container.setPage(page);
		container.setTotal(count);
		for (Product data : list) {
			container.addRow(toFlexGridRow(data));
		}
		out.print(container.toJSONString());
	}

	public void queryProductDimen2() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		FlexiGridDataContainer container = new FlexiGridDataContainer();
		List<Product> list = productDimenManager.getDimenList(groupId, query,
				page, rp);
		int count = productDimenManager.getDimenCount(groupId,query);
		container.setPage(page);
		container.setTotal(count);
		for (Product data : list) {
			data.setChildren(null);
			container.addData(data);
		}
		out.print(container.toJSONString());
	}

	public void queryProductGroup() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		FlexiGridDataContainer container = new FlexiGridDataContainer();
		List<ProductGroup> list = productDimenManager.getGroupChildList();
		int count = productDimenManager.getDimenGroupCount();
		container.setPage(page);
		container.setTotal(count);
		for (ProductGroup data : list) {
			container.addRow(toFlexGridRow(data));
		}
		out.print(container.toJSONString());
	}


	public void saveProductDef() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		int msg = 0;
		if (recordId == 0) {
			msg = productDimenManager.saveDimenDef(product);
		} else {
			product.setRecordId(recordId);
			productDimenManager.updateDimenDef(product);
		}
		out.print(msg);
	}

	public void saveProductDimen() {
		if (dimensId == 0) {
			productDimenManager.saveDimen(product);
		} else {
			product.setDimensId(dimensId);
			productDimenManager.updateDimen(product);
		}
	}

	public void saveProductGroup() {
		if (groupId == 0) {
			productDimenManager.saveDimenGroup(productGroup);
		} else {
			productGroup.setGroupId(groupId);
			productDimenManager.updateDimenGroup(productGroup);
		}
	}

	public void saveProductMap() {
		productDimenManager.saveDimenMap(groupId, dimensId);
	}








	private FlexiGridRow toFlexGridRow(Product product) {
		FlexiGridRow row = new FlexiGridRow();
		row.setId(product.getDimensId());
		row.setCell(product);
		if (product.getChildren() != null && product.getChildren().size() > 0) {
			for (Product child : product.getChildren()) {
				row.addChild(toFlexGridRow(child));
			}
			product.setChildren(null);
		}
		return row;
	}

	private FlexiGridRow toFlexGridRow(ProductGroup productGroup) {
		FlexiGridRow row = new FlexiGridRow();
		row.setId(productGroup.getGroupId());
		row.setCell(productGroup);
		if (productGroup.getChildren2() != null
				&& productGroup.getChildren2().size() > 0) {
			for (ProductGroup child : productGroup.getChildren2()) {
				row.addChild(toFlexGridRow(child));
			}
			productGroup.setChildren2(null);
		}
		return row;
	}
	
	public void addProductGroupAjax() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		List<ProductGroup> list = productDimenManager.getDimenGroupList();
		JSONArray msg = JSONArray.fromObject(list);
		out.print(msg);
	}
	public void addProductDimenAjax() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		List<?> list = productDimenManager.getDimenList();
		JSONArray msg = JSONArray.fromObject(list);
		out.print(msg);
	}

	public void addProductDimenAjax2() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		List<?> list = productDimenManager
				.getDimenList(groupId);
		JSONArray msg = JSONArray.fromObject(list);
		out.print(msg);
	}
	
	public String importProductDimen(){
//		readXLSFile(myFile);
		productDimenManager.importDoc(myFile);
		return "productIndex";
	}
	
	
	
}
