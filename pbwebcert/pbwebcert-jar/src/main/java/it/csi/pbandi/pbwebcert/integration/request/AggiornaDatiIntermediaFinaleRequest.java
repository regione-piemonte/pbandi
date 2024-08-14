/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import java.util.List;

import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoCertificazioneIntermediaFinaleDTO;


public class AggiornaDatiIntermediaFinaleRequest {
	private List<ProgettoCertificazioneIntermediaFinaleDTO> elencoProgettiIntermediaFinale;

	public List<ProgettoCertificazioneIntermediaFinaleDTO> getElencoProgettiIntermediaFinale() {
		return elencoProgettiIntermediaFinale;
	}

	public void setElencoProgettiIntermediaFinale(List<ProgettoCertificazioneIntermediaFinaleDTO> elencoProgettiIntermediaFinale) {
		this.elencoProgettiIntermediaFinale = elencoProgettiIntermediaFinale;
	}
}
