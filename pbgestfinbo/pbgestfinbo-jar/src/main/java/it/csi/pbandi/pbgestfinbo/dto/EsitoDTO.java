/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbgestfinbo.util.BeanUtil;

public class EsitoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	private java.lang.String messaggio = null;
	private java.lang.String exception = null;
	private java.lang.Long id = null;
	
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.Boolean getEsito() {
		return esito;
	}
	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}
	public java.lang.String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(java.lang.String messaggio) {
		this.messaggio = messaggio;
	}	
	public java.lang.String getException() {
		return exception;
	}
	public void setException(java.lang.String exception) {
		this.exception = exception;
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
