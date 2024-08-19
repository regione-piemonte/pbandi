/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

public class EsitoOperazioneGestioneProgetto implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Boolean esito;
	private MessaggioDTO[] msgs;
	private long idSede = 0;
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
	public long getIdSede() {
		return idSede;
	}
	public void setIdSede(long idSede) {
		this.idSede = idSede;
	}

	
}
