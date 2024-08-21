/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto;

public class EsitoOperazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private boolean esito = true;
	private String descBreveStato = null;
	private String codiceMessaggio = null;
	
	public boolean isEsito() {
		return esito;
	}
	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	public String getDescBreveStato() {
		return descBreveStato;
	}
	public void setDescBreveStato(String descBreveStato) {
		this.descBreveStato = descBreveStato;
	}
	public String getCodiceMessaggio() {
		return codiceMessaggio;
	}
	public void setCodiceMessaggio(String codiceMessaggio) {
		this.codiceMessaggio = codiceMessaggio;
	}
	
}

