package com.symbol.app.mantoeye.dto.dimenManager;

import java.util.List;

import com.symbol.app.mantoeye.entity.dimenManager.ProductDimen;


public class Product extends ProductDimen {

	private int recordId;
	private String serverIp;
	private int port;
	private String url;
	private String preDimensName;
	private String ip;
	private String checkBox;
	private String operate;
	private int parentID;
	private List<Product> children;
	private boolean autoExec;

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPreDimensName() {
		return preDimensName;
	}

	public void setPreDimensName(String preDimensName) {
		this.preDimensName = preDimensName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public List<Product> getChildren() {
		return children;
	}

	public void setChildren(List<Product> children) {
		this.children = children;
	}

	public boolean isAutoExec() {
		return autoExec;
	}

	public void setAutoExec(boolean autoExec) {
		this.autoExec = autoExec;
	}

}
