/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;


public class LineaDiInterventoVO extends PbandiDLineaDiInterventoVO{
	private String descBreveCompleta;

	public void setDescBreveCompleta(String descBreveCompleta) {
		this.descBreveCompleta = descBreveCompleta;
	}

	public String getDescBreveCompleta() {
		return descBreveCompleta;
	}
}
