/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;
import java.util.List;

import it.csi.pbandi.pbweberog.dto.recupero.RigaRecuperoItem;

public class RequestSalvaRecuperi implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private String noteRecupero;
	private String estremiRecupero;
	private Long idTipologiaRecupero;
	private Long idAnnoContabile;
	private String dtRecupero;
	
	private List<RigaRecuperoItem>  recuperi;

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getNoteRecupero() {
		return noteRecupero;
	}

	public void setNoteRecupero(String noteRecupero) {
		this.noteRecupero = noteRecupero;
	}

	public String getEstremiRecupero() {
		return estremiRecupero;
	}

	public void setEstremiRecupero(String estremiRecupero) {
		this.estremiRecupero = estremiRecupero;
	}

	public Long getIdTipologiaRecupero() {
		return idTipologiaRecupero;
	}

	public void setIdTipologiaRecupero(Long idTipologiaRecupero) {
		this.idTipologiaRecupero = idTipologiaRecupero;
	}

	public Long getIdAnnoContabile() {
		return idAnnoContabile;
	}

	public void setIdAnnoContabile(Long idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}

	public List<RigaRecuperoItem> getRecuperi() {
		return recuperi;
	}

	public void setRecuperi(List<RigaRecuperoItem> recuperi) {
		this.recuperi = recuperi;
	}

	public String getDtRecupero() {
		return dtRecupero;
	}

	public void setDtRecupero(String dtRecupero) {
		this.dtRecupero = dtRecupero;
	}
	
	
	
}
