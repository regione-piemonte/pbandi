/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti;


public class EsitoSalvaTrasferimentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Boolean esito;

	public void setEsito(Boolean val) {
		esito = val;
	}

	public Boolean getEsito() {
		return esito;
	}

	private MessaggioDTO[] msgs;

	public void setMsgs(
			MessaggioDTO[] val) {
		msgs = val;
	}

	public MessaggioDTO[] getMsgs() {
		return msgs;
	}

	private Long idTrasferimento;

	public void setIdTrasferimento(Long val) {
		idTrasferimento = val;
	}

	public Long getIdTrasferimento() {
		return idTrasferimento;
	}

}
