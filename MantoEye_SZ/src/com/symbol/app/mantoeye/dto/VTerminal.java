package com.symbol.app.mantoeye.dto;

/**
 * 终端视图
 * 
 */
public class VTerminal {

	private Long brandId;// 品牌ID
	private String brandName;// 品牌
	private Long modelId;// 型号ID
	private String modelName;// 型号名称
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Long getModelId() {
		return modelId;
	}
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

}
