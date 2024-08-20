/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import java.util.ArrayList;

import it.csi.pbandi.pbwebrce.dto.cronoprogramma.CronoprogrammaItem;


public class SalvaFasiMonitoraggioGestioneRequest {
	private Long idProgetto;
	private Long idIter;
	private Long idTipoOperazione;
	private ArrayList<CronoprogrammaItem> fasi;
	
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdIter() {
		return idIter;
	}
	public void setIdIter(Long idIter) {
		this.idIter = idIter;
	}
	public ArrayList<CronoprogrammaItem> getFasi() {
		return fasi;
	}
	public void setFasi(ArrayList<CronoprogrammaItem> fasi) {
		this.fasi = fasi;
	}
	public Long getIdTipoOperazione() {
		return idTipoOperazione;
	}
	public void setIdTipoOperazione(Long idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	
	
	
	
	
	
}
