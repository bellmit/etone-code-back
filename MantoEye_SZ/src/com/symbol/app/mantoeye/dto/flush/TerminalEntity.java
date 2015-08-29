package com.symbol.app.mantoeye.dto.flush;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.symbol.wp.core.entity.IdEntity;


public class TerminalEntity  implements java.io.Serializable{
	private Long nmTerminalTypeId;
	private Long msisdn;
	private String terminalBrand;
	private String terminalType;
	private String date;
	
	private int bradId;
	private int typeId;
	
	public Long getNmTerminalTypeId() {
		return nmTerminalTypeId;
	}
	public void setNmTerminalTypeId(Long nmTerminalTypeId) {
		this.nmTerminalTypeId = nmTerminalTypeId;
	}
	public Long getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(Long msisdn) {
		this.msisdn = msisdn;
	}
	public String getTerminalBrand() {
		return terminalBrand;
	}
	public void setTerminalBrand(String terminalBrand) {
		this.terminalBrand = terminalBrand;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getBradId() {
		return bradId;
	}
	public void setBradId(int bradId) {
		this.bradId = bradId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	
	
	
}
