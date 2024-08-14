/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

public class ProgettoPerCompensazioneVO extends PbandiTDettPropostaCertifVO {

	private String descAsse;
	private String codiceVisualizzato;
	
	public String getDescAsse() {
		return descAsse;
	}

	public void setDescAsse(String descAsse) {
		this.descAsse = descAsse;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

}
