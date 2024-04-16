/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbworkspace.dto.Progetto;
import it.csi.pbandi.pbworkspace.util.BeanUtil;

public class AvviaProgettiRequest {
	
	private Long progrBandoLineaIntervento;	
	private ArrayList<Progetto> progettiDaAvviare;
	
	private Long idSoggetto;
	private String codiceRuolo;
	
	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public ArrayList<Progetto> getProgettiDaAvviare() {
		return progettiDaAvviare;
	}

	public void setProgettiDaAvviare(ArrayList<Progetto> progettiDaAvviare) {
		this.progettiDaAvviare = progettiDaAvviare;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if (propName.equalsIgnoreCase("idProgetti")) {
					ArrayList<Progetto> lista = (ArrayList<Progetto>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\n"+propName+":");
						for (Progetto item : lista) {
							sb.append("\n   idProgetto = "+item.toString());
						}
					} else {
						sb.append("\ndocumentoAllegato = null");
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
