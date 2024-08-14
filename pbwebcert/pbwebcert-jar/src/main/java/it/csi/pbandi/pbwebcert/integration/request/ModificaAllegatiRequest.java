/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import java.util.List;

import it.csi.pbandi.pbwebcert.dto.certificazione.DocumentoDescrizione;

public class ModificaAllegatiRequest {
	private List<DocumentoDescrizione> documentiDescrizioni;

	public List<DocumentoDescrizione> getDocumentiDescrizioni() {
		return documentiDescrizioni;
	}

	public void setDocumentiDescrizioni(List<DocumentoDescrizione> documentiDescrizioni) {
		this.documentiDescrizioni = documentiDescrizioni;
	}
}
