/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto;

public class ProgettoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long id = null;
	private java.lang.String titolo = null;
	private java.lang.String codice = null;
	private java.lang.String beneficiario = null;
	private java.lang.String idBandoLinea = null;
	private java.lang.String cup = null;
	
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getTitolo() {
		return titolo;
	}
	public void setTitolo(java.lang.String titolo) {
		this.titolo = titolo;
	}
	public java.lang.String getCodice() {
		return codice;
	}
	public void setCodice(java.lang.String codice) {
		this.codice = codice;
	}
	public java.lang.String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(java.lang.String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public java.lang.String getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setIdBandoLinea(java.lang.String idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public java.lang.String getCup() {
		return cup;
	}
	public void setCup(java.lang.String cup) {
		this.cup = cup;
	}

	
}
