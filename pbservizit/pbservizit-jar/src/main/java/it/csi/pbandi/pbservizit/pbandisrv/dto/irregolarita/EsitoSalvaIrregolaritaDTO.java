/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita;

public class EsitoSalvaIrregolaritaDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private java.lang.Boolean esito;
	private MessaggioDTO[] msgs;
	private Long idIrregolarita;
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
	public Long getIdIrregolarita() {
		return idIrregolarita;
	}
	public void setIdIrregolarita(Long idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}

	
}
