/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.gestionenews;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbwebbo.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbwebbo.util.BeanUtil;

public class InizializzaGestioneNewsDTO implements Serializable {
	
	private static final long serialVersionUID = -1;
	
	private ArrayList<AvvisoDTO> avvisi;
	private ArrayList<CodiceDescrizioneDTO> tipiAnagrafica;
	
	public ArrayList<AvvisoDTO> getAvvisi() {
		return avvisi;
	}

	public void setAvvisi(ArrayList<AvvisoDTO> avvisi) {
		this.avvisi = avvisi;
	}

	public ArrayList<CodiceDescrizioneDTO> getTipiAnagrafica() {
		return tipiAnagrafica;
	}

	public void setTipiAnagrafica(ArrayList<CodiceDescrizioneDTO> tipiAnagrafica) {
		this.tipiAnagrafica = tipiAnagrafica;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("class".equalsIgnoreCase(propName))
					continue;
				if ("avvisi".equalsIgnoreCase(propName)) {
					ArrayList<AvvisoDTO> lista = (ArrayList<AvvisoDTO>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\navvisi:");
						for (AvvisoDTO item : lista) {
							sb.append(item.toString());
						}
					} else {
						sb.append("\navvisi = null");
					}
				} else if ("tipiAnagrafica".equalsIgnoreCase(propName)) {
					ArrayList<CodiceDescrizioneDTO> lista1 = (ArrayList<CodiceDescrizioneDTO>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista1 != null) {
						sb.append("\ntipiAnagrafica:");
						for (CodiceDescrizioneDTO item : lista1) {
							sb.append(item.toString());
						}
					} else {
						sb.append("\ntipiAnagrafica = null");
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
