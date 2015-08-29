package com.symbol.app.mantoeye.dto.business;

import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;

public class VStatDayAssistRule{
	
	private Long totalCount;//总数量
	
	private  Integer nmBusinessMainVetorId;//
	
	private FtbBusinessMainVetor mainVetor;
	
//	private FtbBusinessAssistVetor assistVetor;
	
	public FtbBusinessMainVetor getMainVetor() {
		return mainVetor;
	}
	public void setMainVetor(FtbBusinessMainVetor mainVetor) {
		this.mainVetor = mainVetor;
	}
//	public FtbBusinessAssistVetor getAssistVetor() {
//		return assistVetor;
//	}
//	public void setAssistVetor(FtbBusinessAssistVetor assistVetor) {
//		this.assistVetor = assistVetor;
//	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
//	public Integer getNmMainAssistVetorId() {
//		return nmMainAssistVetorId;
//	}
//	public void setNmMainAssistVetorId(Integer nmMainAssistVetorId) {
//		this.nmMainAssistVetorId = nmMainAssistVetorId;
//	}
	public Integer getNmBusinessMainVetorId() {
		return nmBusinessMainVetorId;
	}
	public void setNmBusinessMainVetorId(Integer nmBusinessMainVetorId) {
		this.nmBusinessMainVetorId = nmBusinessMainVetorId;
	}

}
