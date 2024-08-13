/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import java.util.ArrayList;

public class AffidamentoRendicontazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idAppalto = null;	
	private java.lang.String cigProcAgg = null;
	private java.lang.String codProcAgg = null;
	private java.lang.String oggettoAppalto = null;
	private Boolean selezionabile = null;
	private ArrayList<String> fornitori = null;

	public java.lang.Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(java.lang.Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public java.lang.String getCigProcAgg() {
		return cigProcAgg;
	}

	public void setCigProcAgg(java.lang.String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}

	public java.lang.String getCodProcAgg() {
		return codProcAgg;
	}

	public void setCodProcAgg(java.lang.String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}

	public java.lang.String getOggettoAppalto() {
		return oggettoAppalto;
	}

	public void setOggettoAppalto(java.lang.String oggettoAppalto) {
		this.oggettoAppalto = oggettoAppalto;
	}

	public Boolean getSelezionabile() {
		return selezionabile;
	}

	public void setSelezionabile(Boolean selezionabile) {
		this.selezionabile = selezionabile;
	}

	public ArrayList<String> getFornitori() {
		return fornitori;
	}

	public void setFornitori(ArrayList<String> fornitori) {
		this.fornitori = fornitori;
	}
	
}
