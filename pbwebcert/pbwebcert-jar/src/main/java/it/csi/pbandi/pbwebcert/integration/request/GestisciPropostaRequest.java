/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import it.csi.pbandi.pbwebcert.dto.FiltroRicercaProgettiNP;

public class GestisciPropostaRequest {
	private FiltroRicercaProgettiNP filtro;

	public FiltroRicercaProgettiNP getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroRicercaProgettiNP filtro) {
		this.filtro = filtro;
	}
}
