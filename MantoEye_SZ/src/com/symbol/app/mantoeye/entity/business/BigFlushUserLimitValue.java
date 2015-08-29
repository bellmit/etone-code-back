package com.symbol.app.mantoeye.entity.business;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.symbol.wp.core.entity.IdEntity;

@Entity
@Table(name = "ftbOverFlushLimit")
public class BigFlushUserLimitValue  implements Serializable {
	private String id;
	private int intTimeType;
	private String strTimeType;
	private int intLimitValue;
	private String descrite;
	
	@Column(name = "intTimeType")
	public int getIntTimeType() {
		return intTimeType;
	}
	public void setIntTimeType(int intTimeType) {
		this.intTimeType = intTimeType;
	}
	
	@Column(name = "intLimitValue")
	public int getIntLimitValue() {
		return intLimitValue;
	}
	public void setIntLimitValue(int intLimitValue) {
		this.intLimitValue = intLimitValue;
	}
	
	@Column(name = "vcNote")
	public String getDescrite() {
		return descrite;
	}
	public void setDescrite(String descrite) {
		this.descrite = descrite;
	}
	
	public String getStrTimeType() {
		return strTimeType;
	}
	public void setStrTimeType(String strTimeType) {
		this.strTimeType = strTimeType;
	}
	
	
	@Id
	@GeneratedValue
	@Column(name = "vcOverFlushLimitId, unique = true, nullable = false, precision = 5, scale = 0")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
