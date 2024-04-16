/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class SedeIntervento implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id = null;
	private java.lang.String partitaIva = null;
	private java.lang.String indirizzo = null;
	private java.lang.String cap = null;
	private java.lang.String email = null;
	private java.lang.String fax = null;
	private java.lang.String telefono = null;
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune comuneSede = null;
	private java.lang.String numeroCivico = null;

	public SedeIntervento() {
		super();
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(java.lang.String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public java.lang.String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(java.lang.String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public java.lang.String getCap() {
		return cap;
	}

	public void setCap(java.lang.String cap) {
		this.cap = cap;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getTelefono() {
		return telefono;
	}

	public void setTelefono(java.lang.String telefono) {
		this.telefono = telefono;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune getComuneSede() {
		return comuneSede;
	}

	public void setComuneSede(it.csi.pbandi.pbworkspace.dto.schedaProgetto.Comune comuneSede) {
		this.comuneSede = comuneSede;
	}

	public java.lang.String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(java.lang.String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

}
