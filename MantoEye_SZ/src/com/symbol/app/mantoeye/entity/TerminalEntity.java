package com.symbol.app.mantoeye.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.symbol.wp.core.entity.IdEntity;

@Entity
@Table(name = "terminal")
public class TerminalEntity extends IdEntity implements java.io.Serializable{
	private String vcName;
	private String vcNumber;
	
	
	@Column(name = "vcname")
	public String getVcName() {
		return vcName;
	}
	public void setVcName(String vcName) {
		this.vcName = vcName;
	}
	
	@Column(name = "vcnumber")
	public String getVcNumber() {
		return vcNumber;
	}
	public void setVcNumber(String vcNumber) {
		this.vcNumber = vcNumber;
	}
	
	
	
}
