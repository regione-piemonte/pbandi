/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO;

public class AccodaPropostaRequest {
	private PropostaCertificazioneDTO proposta;

	public PropostaCertificazioneDTO getProposta() {
		return proposta;
	}

	public void setProposta(PropostaCertificazioneDTO proposta) {
		this.proposta = proposta;
	}
}
