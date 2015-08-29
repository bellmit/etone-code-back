package com.symbol.app.mantoeye.web.action;

import java.io.Serializable;

public class Phone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 100000000000000L;
	private String type;
	private String model;
	private String name;
	private String detailModel;
	private String brandId;// 品牌ID
	private String typeId;// 型号ID

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetailModel() {
		return detailModel;
	}

	public void setDetailModel(String detailModel) {
		this.detailModel = detailModel;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
