package com.symbol.app.mantoeye.dto.flush;

import com.symbol.app.mantoeye.constants.DimensionEnum;


/**
 * 公共维表信息实体
 * @author rankin
 *
 */
public class BaseDimension {	
	
	private DimensionEnum type;//维表类型
	
	private Long id;//数据所在维表的主键id
	
	private String name;//数据对应的名称或描述

	public BaseDimension(){
		
	}
	public BaseDimension(DimensionEnum type, Long id,String name){
		this.type = type;
		this.id = id;
		this.name = name;
	}
	public DimensionEnum getType() {
		return type;
	}

	public void setType(DimensionEnum type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
