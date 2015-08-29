package com.symbol.app.mantoeye.entity.dimenManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbProductDef")
public class ProductDef implements Def {
	
	private Integer recordId;
	private Integer dimensId;
	private String serverIp;
	private Integer port;
	private String url;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
	@Column
	public int getDimensId() {
		return dimensId;
	}

	public void setDimensId(Integer dimensId) {
		this.dimensId = dimensId;
	}

	@Column
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	@Column
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
