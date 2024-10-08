/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.recupero;

public class EsitoSalvaRecuperoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private Boolean esito;
	private MessaggioDTO[] msgs;
	public Boolean getEsito() {
		return esito;
	}
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	public MessaggioDTO[] getMsgs() {
		return msgs;
	}
	public void setMsgs(MessaggioDTO[] msgs) {
		this.msgs = msgs;
	}
	
	

	
	
}
