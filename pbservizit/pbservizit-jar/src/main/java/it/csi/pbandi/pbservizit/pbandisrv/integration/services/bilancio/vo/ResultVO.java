/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;


public class ResultVO  {
	
	private Integer codice = null;
	private String descrizione = null;
	private boolean esito;
	
	public boolean isEsito() {
		return esito;
	}
	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	public Integer getCodice() {
		return codice;
	}
	public void setCodice(Integer codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
