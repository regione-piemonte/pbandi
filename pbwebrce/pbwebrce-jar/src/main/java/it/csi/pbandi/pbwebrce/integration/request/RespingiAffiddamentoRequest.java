/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

public class RespingiAffiddamentoRequest {
	private Long idAppalto;
	private String note;
	public Long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
