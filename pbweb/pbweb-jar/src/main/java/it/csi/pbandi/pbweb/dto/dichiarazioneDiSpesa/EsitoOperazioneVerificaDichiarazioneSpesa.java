/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class EsitoOperazioneVerificaDichiarazioneSpesa implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	private ArrayList<DocumentoDiSpesa> documentiDiSpesa = null;
	private ArrayList<String> messaggi = null;
	
	public java.lang.Boolean getEsito() {
		return esito;
	}

	public void setEsito(java.lang.Boolean esito) {
		this.esito = esito;
	}

	public ArrayList<DocumentoDiSpesa> getDocumentiDiSpesa() {
		return documentiDiSpesa;
	}

	public void setDocumentiDiSpesa(ArrayList<DocumentoDiSpesa> documentiDiSpesa) {
		this.documentiDiSpesa = documentiDiSpesa;
	}

	public ArrayList<String> getMessaggi() {
		return messaggi;
	}

	public void setMessaggi(ArrayList<String> messaggi) {
		this.messaggi = messaggi;
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
