/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.util.List;

public class ProgettoVO {

	private Long id;
	private String acronimo;
	private String codiceVisualizzato;
	private String titolo;
	private List<AttivitaVO> attivita; // utilizzato solo in frontend

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public List<AttivitaVO> getAttivita() {
		return attivita;
	}

	public void setAttivita(List<AttivitaVO> attivita) {
		this.attivita = attivita;
	}

	@Override
	public String toString() {
		return "ProgettoVO [id=" + id + ", acronimo=" + acronimo + ", codiceVisualizzato=" + codiceVisualizzato
				+ ", titolo=" + titolo + "]";
	}

}
