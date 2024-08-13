/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class NazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idNazione = null;
	private java.lang.String codIstatNazione = null;
	private java.lang.String descNazione = null;
	private java.lang.String flagAppartenenzaUe = null;
	
	public java.lang.Long getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(java.lang.Long idNazione) {
		this.idNazione = idNazione;
	}
	public java.lang.String getCodIstatNazione() {
		return codIstatNazione;
	}
	public void setCodIstatNazione(java.lang.String codIstatNazione) {
		this.codIstatNazione = codIstatNazione;
	}
	public java.lang.String getDescNazione() {
		return descNazione;
	}
	public void setDescNazione(java.lang.String descNazione) {
		this.descNazione = descNazione;
	}
	public java.lang.String getFlagAppartenenzaUe() {
		return flagAppartenenzaUe;
	}
	public void setFlagAppartenenzaUe(java.lang.String flagAppartenenzaUe) {
		this.flagAppartenenzaUe = flagAppartenenzaUe;
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
