package com.symbol.app.mantoeye.dto.common;

/**
 * 用于页面select选择
 * 
 * @author rankin
 * 
 */
public class CommonDimension {
	private Long id;
	private String name;
	private Long type;

	public CommonDimension() {

	}

	public CommonDimension(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CommonDimension(Long id, Long type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
