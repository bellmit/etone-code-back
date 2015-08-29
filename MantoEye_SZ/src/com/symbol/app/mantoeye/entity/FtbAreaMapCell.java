package com.symbol.app.mantoeye.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FtbAreaMapCell", schema = "DBA", catalog = "iqwrite")
public class FtbAreaMapCell {
	private long nmAreaMapCellId;
	private long nmAreaId;
	private String vcCgi;

	@Id
	@GeneratedValue
	@Column(name = "nmAreaMapCellId", unique = true, nullable = false, precision = 6, scale = 0)
	public long getNmAreaMapCellId() {
		return nmAreaMapCellId;
	}

	public void setNmAreaMapCellId(long nmAreaMapCellId) {
		this.nmAreaMapCellId = nmAreaMapCellId;
	}

	@Column(name = "nmAreaId", length = 10)
	public long getNmAreaId() {
		return nmAreaId;
	}

	public void setNmAreaId(long nmAreaId) {
		this.nmAreaId = nmAreaId;
	}

	@Column(name = "vcCgi", length = 32)
	public String getVcCgi() {
		return vcCgi;
	}

	public void setVcCgi(String vcCgi) {
		this.vcCgi = vcCgi;
	}
}
