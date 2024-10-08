/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;


public class ModificaProcedureAggiudicazioneRequest {
	private ProceduraAggiudicazione proceduraAggiudicazione;
	private Long idProgetto;
	
	
	
	public ProceduraAggiudicazione getProceduraAggiudicazione() {
		return proceduraAggiudicazione;
	}
	public void setProceduraAggiudicazione(ProceduraAggiudicazione proceduraAggiudicazione) {
		this.proceduraAggiudicazione = proceduraAggiudicazione;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
}
