package com.symbol.app.mantoeye.dto.business;

import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;

public class FtbMainAssistVetorNew {
	private Integer nmMainAssistVetorId;
	private FtbBusinessAssistVetor ftbBusinessAssistVetor;
	private FtbBusinessMainVetor ftbBusinessMainVetor;

	public Integer getNmMainAssistVetorId() {
		return nmMainAssistVetorId;
	}

	public void setNmMainAssistVetorId(Integer nmMainAssistVetorId) {
		this.nmMainAssistVetorId = nmMainAssistVetorId;
	}

	public FtbBusinessAssistVetor getFtbBusinessAssistVetor() {
		return ftbBusinessAssistVetor;
	}

	public void setFtbBusinessAssistVetor(
			FtbBusinessAssistVetor ftbBusinessAssistVetor) {
		this.ftbBusinessAssistVetor = ftbBusinessAssistVetor;
	}

	public FtbBusinessMainVetor getFtbBusinessMainVetor() {
		return ftbBusinessMainVetor;
	}

	public void setFtbBusinessMainVetor(
			FtbBusinessMainVetor ftbBusinessMainVetor) {
		this.ftbBusinessMainVetor = ftbBusinessMainVetor;
	}

}
