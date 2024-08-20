/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedocumentidispesa.MessaggioDTO;

public class EsitoProceduraAggiudicazioneDTO implements java.io.Serializable  {
	static final long serialVersionUID = 1;

	private Boolean esito;
	private MessaggioDTO[] msgs;
	private ProceduraAggiudicazioneDTO proceduraAggiudicazione;
	public Boolean getEsito() {
		return esito;
	}
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	public MessaggioDTO[] getMsgs() {
		return msgs;
	}
	public void setMsgs(MessaggioDTO[] msgs) {
		this.msgs = msgs;
	}
	public ProceduraAggiudicazioneDTO getProceduraAggiudicazione() {
		return proceduraAggiudicazione;
	}
	public void setProceduraAggiudicazione(ProceduraAggiudicazioneDTO proceduraAggiudicazione) {
		this.proceduraAggiudicazione = proceduraAggiudicazione;
	}
	
	
	
}
