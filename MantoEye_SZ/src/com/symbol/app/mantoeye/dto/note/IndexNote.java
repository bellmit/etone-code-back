package com.symbol.app.mantoeye.dto.note;

/**
 * 首頁公告欄model
 * 
 * @author Jane
 * 
 */
public class IndexNote {

	// nmNoteId,vcTitle,dtDate,vcContent
	private Integer nmNoteId;
	private String vcTitle;
	private String dtDate;
	private String vcContent;

	public Integer getNmNoteId() {
		return nmNoteId;
	}

	public void setNmNoteId(Integer nmNoteId) {
		this.nmNoteId = nmNoteId;
	}

	public String getVcTitle() {
		return vcTitle;
	}

	public void setVcTitle(String vcTitle) {
		this.vcTitle = vcTitle;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getVcContent() {
		return vcContent;
	}

	public void setVcContent(String vcContent) {
		this.vcContent = vcContent;
	}

}
