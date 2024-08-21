/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.revoca;

public class EsitoSalvaRevocaDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private java.lang.Boolean esito;
	private MessaggioDTO[] msgs;
	public java.lang.Boolean getEsito() {
		return esito;
	}
	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}
	public MessaggioDTO[] getMsgs() {
		return msgs;
	}
	public void setMsgs(MessaggioDTO[] msgs) {
		this.msgs = msgs;
	}

	
	
}
