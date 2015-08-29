package com.symbol.app.mantoeye.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "ftbImeiRecord")
public class FtbImeiRecord {
	
	private Long nmImeiRecordId;// 
	private Long nmImei;// 
	private Date dtLastDate;// 
	private int intRaitype;//
	
	@Id
	@Column(name = "nmImeiRecordId", unique = true, nullable = false)
	public Long getNmImeiRecordId() {
		return nmImeiRecordId;
	}
	public void setNmImeiRecordId(Long nmImeiRecordId) {
		this.nmImeiRecordId = nmImeiRecordId;
	}
	@Column(name = "nmImei")
	public Long getNmImei() {
		return nmImei;
	}
	public void setNmImei(Long nmImei) {
		this.nmImei = nmImei;
	}
	@Column(name = "dtLastDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtLastDate() {
		return dtLastDate;
	}
	public void setDtLastDate(Date dtLastDate) {
		this.dtLastDate = dtLastDate;
	}
	@Column(name = "intRaitype")
	public int getIntRaitype() {
		return intRaitype;
	}
	public void setIntRaitype(int intRaitype) {
		this.intRaitype = intRaitype;
	}

}
