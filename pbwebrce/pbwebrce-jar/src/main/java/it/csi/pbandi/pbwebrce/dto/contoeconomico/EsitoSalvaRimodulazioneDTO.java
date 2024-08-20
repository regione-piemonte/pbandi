/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.contoeconomico;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbwebrce.dto.ContoEconomicoItem;
import it.csi.pbandi.pbwebrce.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbwebrce.util.BeanUtil;

public class EsitoSalvaRimodulazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private Boolean esito;
	
	private ArrayList<String> messaggi = new ArrayList<String>(); 
	
	private ArrayList<ContoEconomicoItem> righeContoEconomicoAggiornato;

	public ArrayList<ContoEconomicoItem> getRigheContoEconomicoAggiornato() {
		return righeContoEconomicoAggiornato;
	}

	public void setRigheContoEconomicoAggiornato(ArrayList<ContoEconomicoItem> righeContoEconomicoAggiornato) {
		this.righeContoEconomicoAggiornato = righeContoEconomicoAggiornato;
	}

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
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
				if ("messaggi".equalsIgnoreCase(propName)) {
					ArrayList<String> lista = (ArrayList<String>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\nmessaggi:");
						for (String id : lista) {
							sb.append("\n   messaggio = "+id);
						}
					} else {
						sb.append("\nmessaggi = null");
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
