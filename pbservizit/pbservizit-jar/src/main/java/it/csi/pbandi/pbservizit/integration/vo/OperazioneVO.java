/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

public class OperazioneVO {

	private Long id; // id custom
	private String descrizione;

	public OperazioneVO(Long id, String descrizione) {
		this.id = id;
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "OperazioneVO [id=" + id + ", descrizione=" + descrizione + "]";
	}

}
