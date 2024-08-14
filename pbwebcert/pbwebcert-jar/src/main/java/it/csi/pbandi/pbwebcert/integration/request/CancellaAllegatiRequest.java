/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import java.util.List;

public class CancellaAllegatiRequest {
	
	private List<String> idDocumentiSelezionati;

	public List<String> getIdDocumentiSelezionati() {
		return idDocumentiSelezionati;
	}

	public void setIdDocumentiSelezionati(List<String> idDocumentiSelezionati) {
		this.idDocumentiSelezionati = idDocumentiSelezionati;
	}




	
}
