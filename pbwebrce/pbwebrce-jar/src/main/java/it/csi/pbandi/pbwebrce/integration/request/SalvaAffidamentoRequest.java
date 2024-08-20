/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import it.csi.pbandi.pbwebrce.dto.affidamenti.Affidamento;
import it.csi.pbandi.pbwebrce.dto.affidamenti.ProceduraAggiudicazioneAffidamento;

public class SalvaAffidamentoRequest {
	private Affidamento affidamento;
	private ProceduraAggiudicazioneAffidamento procAgg;
	public Affidamento getAffidamento() {
		return affidamento;
	}
	public void setAffidamento(Affidamento affidamento) {
		this.affidamento = affidamento;
	}
	public ProceduraAggiudicazioneAffidamento getProcAgg() {
		return procAgg;
	}
	public void setProcAgg(ProceduraAggiudicazioneAffidamento procAgg) {
		this.procAgg = procAgg;
	}
	
	
	
}
