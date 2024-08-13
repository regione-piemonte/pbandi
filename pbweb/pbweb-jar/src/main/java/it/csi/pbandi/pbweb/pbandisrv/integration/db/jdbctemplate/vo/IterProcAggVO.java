/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRIterProcAggVO;

public class IterProcAggVO extends PbandiRIterProcAggVO {

	private String codIgrueT48;
	private String descStep;
	private String idTipologiaAggiudicaz;
	public String getCodIgrueT48() {
		return codIgrueT48;
	}
	public void setCodIgrueT48(String codIgrueT48) {
		this.codIgrueT48 = codIgrueT48;
	}
	public String getDescStep() {
		return descStep;
	}
	public void setDescStep(String descStep) {
		this.descStep = descStep;
	}
	public String getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}
	public void setIdTipologiaAggiudicaz(String idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
	
}
