/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

public class ProgettoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idProgetto = null;

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	private String codiceVisualizzato = null;

	public void setCodiceVisualizzato(String val) {
		codiceVisualizzato = val;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	private String codiceProgetto = null;

	public void setCodiceProgetto(String val) {
		codiceProgetto = val;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	private String titoloProgetto = null;

	public void setTitoloProgetto(String val) {
		titoloProgetto = val;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	private String cup = null;

	public void setCup(String val) {
		cup = val;
	}

	public String getCup() {
		return cup;
	}

}
