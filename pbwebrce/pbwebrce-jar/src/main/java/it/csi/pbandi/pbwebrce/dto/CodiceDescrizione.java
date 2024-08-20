/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class CodiceDescrizione implements java.io.Serializable {

	private String codice = null;

	public void setCodice(String val) {
		codice = val;
	}

	public String getCodice() {
		return codice;
	}

	private String descrizione = null;

	public void setDescrizione(String val) {
		descrizione = val;
	}

	public String getDescrizione() {
		return descrizione;
	}
	private static final long serialVersionUID = 1L;

	public CodiceDescrizione() {
		super();
	}

	public String toString() {
		return super.toString();
	}
}
