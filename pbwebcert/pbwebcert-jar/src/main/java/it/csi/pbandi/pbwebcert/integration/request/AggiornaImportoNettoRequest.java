/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import java.util.List;

import it.csi.pbandi.pbwebcert.integration.vo.ProgettoNuovaCertificazione;

public class AggiornaImportoNettoRequest {

	private List<ProgettoNuovaCertificazione> progetti;

	public List<ProgettoNuovaCertificazione> getProgetti() {
		return progetti;
	}

	public void setProgetti(List<ProgettoNuovaCertificazione> progetti) {
		this.progetti = progetti;
	}
	
	
}
