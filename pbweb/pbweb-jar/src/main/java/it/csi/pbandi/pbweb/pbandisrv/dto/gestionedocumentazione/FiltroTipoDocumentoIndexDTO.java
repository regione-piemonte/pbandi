/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione;

public class FiltroTipoDocumentoIndexDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idSoggetto = null;
	private java.lang.Long idProgetto = null;
	private java.lang.String codiceRuolo = null;
	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(java.lang.Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public java.lang.Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(java.lang.Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public java.lang.String getCodiceRuolo() {
		return codiceRuolo;
	}
	public void setCodiceRuolo(java.lang.String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	
}
