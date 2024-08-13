/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class FornitoreComboDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idFornitore = null;
	private java.lang.String descrizione = null;	
	private java.lang.Boolean cfValido = null;
	private java.lang.Boolean formaGiuridicaValida = null;
	
	public java.lang.Long getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(java.lang.Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public java.lang.String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}
	public java.lang.Boolean getCfValido() {
		return cfValido;
	}
	public void setCfValido(java.lang.Boolean cfValido) {
		this.cfValido = cfValido;
	}
	public java.lang.Boolean getFormaGiuridicaValida() {
		return formaGiuridicaValida;
	}
	public void setFormaGiuridicaValida(java.lang.Boolean formaGiuridicaValida) {
		this.formaGiuridicaValida = formaGiuridicaValida;
	}
	
}
