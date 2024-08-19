/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import it.csi.pbandi.pbweberog.dto.erogazione.DatiCalcolati;
import it.csi.pbandi.pbweberog.dto.erogazione.Erogazione;

public class ModificaErogazioneRequest {
	private Long idProgetto;
	private Erogazione erogazione;
	private DatiCalcolati datiCalcolati;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Erogazione getErogazione() {
		return erogazione;
	}
	public void setErogazione(Erogazione erogazione) {
		this.erogazione = erogazione;
	}
	public DatiCalcolati getDatiCalcolati() {
		return datiCalcolati;
	}
	public void setDatiCalcolati(DatiCalcolati datiCalcolati) {
		this.datiCalcolati = datiCalcolati;
	}
	
	
}
