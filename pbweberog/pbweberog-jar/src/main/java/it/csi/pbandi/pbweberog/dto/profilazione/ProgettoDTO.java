/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.profilazione;

public class ProgettoDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private Long idProgetto;

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}


	public Long getIdProgetto() {
		return idProgetto;
	}

	private String codiceVisualizzatoProgetto;

	public void setCodiceVisualizzatoProgetto(String val) {
		codiceVisualizzatoProgetto = val;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	private String cup;

	public void setCup(String val) {
		cup = val;
	}

	public String getCup() {
		return cup;
	}
}
