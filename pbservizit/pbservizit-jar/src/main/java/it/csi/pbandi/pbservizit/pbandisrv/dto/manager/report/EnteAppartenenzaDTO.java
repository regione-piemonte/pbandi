/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report;

public class EnteAppartenenzaDTO implements java.io.Serializable {
	
	
	static final long serialVersionUID = 1;

	
	private java.lang.Long idEnteCompetenza = null;
	private java.lang.Long idIndirizzo = null;
	private java.lang.String descEnte = null;
	private java.lang.String indirizzo = null;
	private java.lang.String cap = null;
	private java.lang.Long idComune = null;
	private java.lang.String comune = null;
	private java.lang.String siglaProvincia = null;
	private java.lang.Long idProvincia = null;

	
	public void setIdEnteCompetenza(java.lang.Long val) {
		idEnteCompetenza = val;
	}

	
	public java.lang.Long getIdEnteCompetenza() {
		return idEnteCompetenza;
	}

	

	
	public void setIdIndirizzo(java.lang.Long val) {
		idIndirizzo = val;
	}

	
	public java.lang.Long getIdIndirizzo() {
		return idIndirizzo;
	}

	
	public void setDescEnte(java.lang.String val) {
		descEnte = val;
	}

	
	public java.lang.String getDescEnte() {
		return descEnte;
	}

	

	public void setIndirizzo(java.lang.String val) {
		indirizzo = val;
	}

	
	public java.lang.String getIndirizzo() {
		return indirizzo;
	}

	
	public void setCap(java.lang.String val) {
		cap = val;
	}

	
	public java.lang.String getCap() {
		return cap;
	}


	
	public void setIdComune(java.lang.Long val) {
		idComune = val;
	}

	
	public java.lang.Long getIdComune() {
		return idComune;
	}

	

	public void setComune(java.lang.String val) {
		comune = val;
	}

	public java.lang.String getComune() {
		return comune;
	}

	
	
	public void setSiglaProvincia(java.lang.String val) {
		siglaProvincia = val;
	}

	public java.lang.String getSiglaProvincia() {
		return siglaProvincia;
	}

	
	


	public void setIdProvincia(java.lang.Long val) {
		idProvincia = val;
	}

	
	public java.lang.Long getIdProvincia() {
		return idProvincia;
	}

}
