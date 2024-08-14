/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;


import java.util.List;

import it.csi.pbandi.pbwebcert.dto.certificazione.CodiceDescrizione;
import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO;

public class LanciaPropostaRequest {
	private PropostaCertificazioneDTO proposta;
	private List<CodiceDescrizione> lineeDiInterventoDisponibili;

	public PropostaCertificazioneDTO getProposta() {
		return proposta;
	}

	public void setProposta(PropostaCertificazioneDTO proposta) {
		this.proposta = proposta;
	}

	public List<CodiceDescrizione> getLineeDiInterventoDisponibili() {
		return lineeDiInterventoDisponibili;
	}

	public void setLineeDiInterventoDisponibili(List<CodiceDescrizione> lineeDiInterventoDisponibili) {
		this.lineeDiInterventoDisponibili = lineeDiInterventoDisponibili;
	}
}
