package com.symbol.app.mantoeye.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ftbOverView", schema = "DBA", catalog = "iqwrite")
public class FtbOverView {
private Long nmOverViewId;
private Integer intType;
private Long nmUsers;
private Long nmFlush;
private Integer intYear;
private Integer intMonth;
private Integer intWeek;
private Integer intDay;
private Integer intHour;
private Date dtStatTime;
private String vcCGI;
public FtbOverView(){
	
}

@Id
@GeneratedValue
@Column(name = "nmOverViewId", unique = true, nullable = false, precision = 3, scale = 0)
public Long getNmOverViewId() {
	return nmOverViewId;
}
public void setNmOverViewId(Long nmOverViewId) {
	this.nmOverViewId = nmOverViewId;
}
@Column(name = "intType")
public Integer getIntType() {
	return intType;
}
public void setIntType(Integer intType) {
	this.intType = intType;
}
@Column(name = "nmUsers")
public Long getNmUsers() {
	return nmUsers;
}
public void setNmUsers(Long nmUsers) {
	this.nmUsers = nmUsers;
}
@Column(name = "nmFlush")
public Long getNmFlush() {
	return nmFlush;
}
public void setNmFlush(Long nmFlush) {
	this.nmFlush = nmFlush;
}
@Column(name = "intYear")
public Integer getIntYear() {
	return intYear;
}
public void setIntYear(Integer intYear) {
	this.intYear = intYear;
}
@Column(name = "intMonth")
public Integer getIntMonth() {
	return intMonth;
}
public void setIntMonth(Integer intMonth) {
	this.intMonth = intMonth;
}
@Column(name = "intWeek")
public Integer getIntWeek() {
	return intWeek;
}
public void setIntWeek(Integer intWeek) {
	this.intWeek = intWeek;
}
@Column(name = "intDay")
public Integer getIntDay() {
	return intDay;
}
public void setIntDay(Integer intDay) {
	this.intDay = intDay;
}
@Column(name = "intHour")
public Integer getIntHour() {
	return intHour;
}
public void setIntHour(Integer intHour) {
	this.intHour = intHour;
}
@Column(name = "dtStatTime")
@Temporal(TemporalType.DATE)
public Date getDtStatTime() {
	return dtStatTime;
}
public void setDtStatTime(Date dtStatTime) {
	this.dtStatTime = dtStatTime;
}
@Column(name = "vcCGI")
public String getVcCGI() {
	return vcCGI;
}
public void setVcCGI(String vcCGI) {
	this.vcCGI = vcCGI;
}


}
