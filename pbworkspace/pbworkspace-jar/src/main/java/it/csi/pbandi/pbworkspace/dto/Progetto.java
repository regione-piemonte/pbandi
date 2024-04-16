/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbworkspace.util.BeanUtil;

public class Progetto implements java.io.Serializable {

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id = null;
	private java.lang.String codice = null;
	private java.lang.String cup = null;
	private java.lang.String titolo = null;
	private java.lang.Double importoAgevolato = null;
	private java.lang.String beneficiario = null;
	private java.lang.String avviabile = null;

	public Progetto() {
		super();
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getCodice() {
		return codice;
	}

	public void setCodice(java.lang.String codice) {
		this.codice = codice;
	}

	public java.lang.String getCup() {
		return cup;
	}

	public void setCup(java.lang.String cup) {
		this.cup = cup;
	}

	public java.lang.String getTitolo() {
		return titolo;
	}

	public void setTitolo(java.lang.String titolo) {
		this.titolo = titolo;
	}

	public java.lang.Double getImportoAgevolato() {
		return importoAgevolato;
	}

	public void setImportoAgevolato(java.lang.Double importoAgevolato) {
		this.importoAgevolato = importoAgevolato;
	}

	public java.lang.String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(java.lang.String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public java.lang.String getAvviabile() {
		return avviabile;
	}

	public void setAvviabile(java.lang.String avviabile) {
		this.avviabile = avviabile;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
