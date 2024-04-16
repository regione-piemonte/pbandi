/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbworkspace.util.BeanUtil;



public class BandoProcesso implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String id = null;
	private java.lang.String nome = null;
	private java.lang.String versione = null;
	private java.lang.String processo = null;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getNome() {
		return nome;
	}
	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}
	public java.lang.String getVersione() {
		return versione;
	}
	public void setVersione(java.lang.String versione) {
		this.versione = versione;
	}
	public java.lang.String getProcesso() {
		return processo;
	}
	public void setProcesso(java.lang.String processo) {
		this.processo = processo;
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
