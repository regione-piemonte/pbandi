/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.erogazione.DatiCalcolati;
import it.csi.pbandi.pbweberog.dto.erogazione.Erogazione;

public class SalvaErogazioneRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private Erogazione dettaglioErogazione;
	private DatiCalcolati datiCalcolati;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Erogazione getDettaglioErogazione() {
		return dettaglioErogazione;
	}
	public void setDettaglioErogazione(Erogazione dettaglioErogazione) {
		this.dettaglioErogazione = dettaglioErogazione;
	}
	public DatiCalcolati getDatiCalcolati() {
		return datiCalcolati;
	}
	public void setDatiCalcolati(DatiCalcolati datiCalcolati) {
		this.datiCalcolati = datiCalcolati;
	}
	
	
	
}
