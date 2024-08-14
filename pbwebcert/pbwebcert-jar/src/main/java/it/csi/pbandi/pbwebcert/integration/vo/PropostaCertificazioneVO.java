/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;


public class PropostaCertificazioneVO extends PbandiTPropostaCertificazVO{
	private String descBreveStatoPropostaCert;
	private String descStatoPropostaCertif;
	
	public void setDescBreveStatoPropostaCert(String descBreveStatoPropostaCert) {
		this.descBreveStatoPropostaCert = descBreveStatoPropostaCert;
	}

	public String getDescBreveStatoPropostaCert() {
		return descBreveStatoPropostaCert;
	}

	public void setDescStatoPropostaCertif(String descStatoPropostaCertif) {
		this.descStatoPropostaCertif = descStatoPropostaCertif;
	}

	public String getDescStatoPropostaCertif() {
		return descStatoPropostaCertif;
	}
}
