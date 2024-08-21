/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.request;

import java.util.ArrayList;

import it.csi.pbandi.pbservizit.dto.cronoprogramma.CronoprogrammaItem;
import it.csi.pbandi.pbservizit.dto.cronoprogramma.DatiCronoprogramma;


public class ValidazioneDatiCronoProgrammaRequest {
	private ArrayList<CronoprogrammaItem> fasi;
	private DatiCronoprogramma datiCrono;
	private Long idProgetto;
	
	public DatiCronoprogramma getDatiCrono() {
		return datiCrono;
	}

	public void setDatiCrono(DatiCronoprogramma datiCrono) {
		this.datiCrono = datiCrono;
	}

	public ArrayList<CronoprogrammaItem> getFasi() {
		return fasi;
	}

	public void setFasi(ArrayList<CronoprogrammaItem> fasi) {
		this.fasi = fasi;
	}



	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	
}
