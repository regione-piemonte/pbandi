/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

public class CodiceDescrizione implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String codice = null;
	private java.lang.String descrizione = null;
	
	public CodiceDescrizione() {
		super();
	}
	
	public java.lang.String getCodice() {
		return codice;
	}
	public void setCodice(java.lang.String codice) {
		this.codice = codice;
	}
	public java.lang.String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}

}
