/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import java.sql.Date;

public class BilancioXMLManagerOutput {
	
	private String numeroAtto;
  	
  	private java.sql.Date dataEmissione;

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public java.sql.Date getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(java.sql.Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

}
