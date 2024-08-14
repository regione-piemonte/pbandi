/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import it.csi.pbandi.pbwebcert.dto.certificazione.PropostaCertificazioneDTO;

public class InviaReportRequest {
	 private PropostaCertificazioneDTO propostaCertificazione;
	public PropostaCertificazioneDTO getPropostaCertificazione() {
		return propostaCertificazione;
	}
	public void setPropostaCertificazione(PropostaCertificazioneDTO propostaCertificazione) {
		this.propostaCertificazione = propostaCertificazione;
	}
	
}
