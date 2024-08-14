/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.request;

import java.math.BigDecimal;
import java.util.List;

public class PropostaCertificazioneAllegatiRequest {
	private BigDecimal idPropostaCertificazione;
	private List<String> codiciTipoDocumento;
	public BigDecimal getIdPropostaCertificazione() {
		return idPropostaCertificazione;
	}
	public void setIdPropostaCertificazione(BigDecimal idPropostaCertificazione) {
		this.idPropostaCertificazione = idPropostaCertificazione;
	}
	public List<String> getCodiciTipoDocumento() {
		return codiciTipoDocumento;
	}
	public void setCodiciTipoDocumento(List<String> codiciTipoDocumento) {
		this.codiciTipoDocumento = codiciTipoDocumento;
	}
	
	
}
