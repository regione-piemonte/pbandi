/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;


import java.util.List;

import it.csi.pbandi.pbwebcert.dto.certificazione.ProgettoCertificazioneIntermediaFinaleDTO;


public class AggiornaDatiRequest {
	private Long idProposta;
	public Long getIdProposta() {
		return idProposta;
	}
	public void setIdProposta(Long idProposta) {
		this.idProposta = idProposta;
	}
	
	
}
