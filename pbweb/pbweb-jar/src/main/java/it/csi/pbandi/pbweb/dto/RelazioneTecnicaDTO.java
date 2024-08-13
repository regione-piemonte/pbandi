/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import java.math.BigDecimal;

public class RelazioneTecnicaDTO {

	
	private BigDecimal idRelazioneTecnica; 
	private BigDecimal idDocumentoIndex; 
	private String nomeFile; 
	private String flagValidato ; 
	private String note; 
	private int tipoRelazioneTecnica;
	private  BigDecimal idProgetto; 
	
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdRelazioneTecnica() {
		return idRelazioneTecnica;
	}
	public void setIdRelazioneTecnica(BigDecimal idRelazioneTecnica) {
		this.idRelazioneTecnica = idRelazioneTecnica;
	}
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getFlagValidato() {
		return flagValidato;
	}
	public void setFlagValidato(String flagValidato) {
		this.flagValidato = flagValidato;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getTipoRelazioneTecnica() {
		return tipoRelazioneTecnica;
	}
	public void setTipoRelazioneTecnica(int tipoRelazioneTecnica) {
		this.tipoRelazioneTecnica = tipoRelazioneTecnica;
	} 
	
	
}
