/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class EsitoVerificaOperazioneMassivaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;
	private java.lang.String messaggio = null;
	private java.lang.String messaggioImportoAmmissibileSuperato = null;
	private ArrayList<Long> idDocumenti = null;
	
	public java.lang.String getMessaggioImportoAmmissibileSuperato() {
		return messaggioImportoAmmissibileSuperato;
	}
	public void setMessaggioImportoAmmissibileSuperato(java.lang.String messaggioImportoAmmissibileSuperato) {
		this.messaggioImportoAmmissibileSuperato = messaggioImportoAmmissibileSuperato;
	}
	public ArrayList<Long> getIdDocumenti() {
		return idDocumenti;
	}
	public void setIdDocumenti(ArrayList<Long> idDocumenti) {
		this.idDocumenti = idDocumenti;
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
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("idDocumenti".equalsIgnoreCase(propName)) {
					ArrayList<Long> lista = (ArrayList<Long>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\nidDocumenti:");
						for (Long id : lista) {
							sb.append("\n   idDocumento = "+id);
						}
					} else {
						sb.append("\nidDocumenti = null");
					}
				} else {
					sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
				}
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}

}
