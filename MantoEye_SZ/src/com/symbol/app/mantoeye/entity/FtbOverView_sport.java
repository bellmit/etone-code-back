package com.symbol.app.mantoeye.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//@Entity
//@Table(name = "ftbOverView_sport", schema = "DBA", catalog = "iqwrite")
public class FtbOverView_sport implements java.io.Serializable {
	private Integer intType;// 类型
	private Date statdate;// 时间
	private String context;

	public FtbOverView_sport() {

	}

	//@Column(name = "intType")
	public Integer getIntType() {
		return intType;
	}

	public void setIntType(Integer intType) {
		this.intType = intType;
	}

	//@Column(name = "statdate")
	//@Temporal(TemporalType.DATE)
	public Date getStatdate() {
		return statdate;
	}

	public void setStatdate(Date statdate) {
		this.statdate = statdate;
	}

	//@Column(name = "context")
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
