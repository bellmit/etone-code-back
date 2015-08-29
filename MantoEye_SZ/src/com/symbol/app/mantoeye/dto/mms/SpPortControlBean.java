package com.symbol.app.mantoeye.dto.mms;

/**
 * 彩信端口管理BEAN
 * 
 * @author Jane Wu
 * 
 */
public class SpPortControlBean {

	private Long spPortMapId;

	private String spPort;// 彩信端口
	private String beLong;// 彩信归属
	private String companyName;// 所属公司

	public String getSpPort() {
		return spPort;
	}

	public void setSpPort(String spPort) {
		this.spPort = spPort;
	}

	public String getBeLong() {
		return beLong;
	}

	public void setBeLong(String beLong) {
		this.beLong = beLong;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getSpPortMapId() {
		return spPortMapId;
	}

	public void setSpPortMapId(Long spPortMapId) {
		this.spPortMapId = spPortMapId;
	}

}
