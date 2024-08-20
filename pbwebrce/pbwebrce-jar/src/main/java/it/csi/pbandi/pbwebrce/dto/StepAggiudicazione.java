/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

import java.sql.Date;

public class StepAggiudicazione implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private String id;
	private Long idStepAggiudicazione;
	private java.lang.String descStepAggiudicazione;
	private Date dtPrevista;
	private Date dtEffettiva;
	private Double importo;
	private Long idMotivoScostamento;
	
	private String label;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getIdStepAggiudicazione() {
		return idStepAggiudicazione;
	}
	public void setIdStepAggiudicazione(Long idStepAggiudicazione) {
		this.idStepAggiudicazione = idStepAggiudicazione;
	}
	public java.lang.String getDescStepAggiudicazione() {
		return descStepAggiudicazione;
	}
	public void setDescStepAggiudicazione(java.lang.String descStepAggiudicazione) {
		this.descStepAggiudicazione = descStepAggiudicazione;
	}
	public Date getDtPrevista() {
		return dtPrevista;
	}
	public void setDtPrevista(Date dtPrevista) {
		this.dtPrevista = dtPrevista;
	}
	public Date getDtEffettiva() {
		return dtEffettiva;
	}
	public void setDtEffettiva(Date dtEffettiva) {
		this.dtEffettiva = dtEffettiva;
	}
	public Long getIdMotivoScostamento() {
		return idMotivoScostamento;
	}
	public void setIdMotivoScostamento(Long idMotivoScostamento) {
		this.idMotivoScostamento = idMotivoScostamento;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}

}
