/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class ProseguiChiudiValidazioneRequest {
	
	private String noteChiusura;
	private Boolean dsIntegrativaConsentita;
	private ArrayList<Long> idAppaltiSelezionati;
	private Long idProgetto;
	private Long idDichiarazioneDiSpesa;

	public String getNoteChiusura() {
		return noteChiusura;
	}

	public void setNoteChiusura(String noteChiusura) {
		this.noteChiusura = noteChiusura;
	}

	public Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}

	public void setIdDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}

	public Boolean getDsIntegrativaConsentita() {
		return dsIntegrativaConsentita;
	}

	public void setDsIntegrativaConsentita(Boolean dsIntegrativaConsentita) {
		this.dsIntegrativaConsentita = dsIntegrativaConsentita;
	}

	public ArrayList<Long> getIdAppaltiSelezionati() {
		return idAppaltiSelezionati;
	}

	public void setIdAppaltiSelezionati(ArrayList<Long> idAppaltiSelezionati) {
		this.idAppaltiSelezionati = idAppaltiSelezionati;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("idAppaltiSelezionati".equalsIgnoreCase(propName)) {
					ArrayList<Long> lista = (ArrayList<Long>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\nidAppaltiSelezionati:");
						for (Long item : lista) {
							sb.append("\n   id = "+item);
						}
					} else {
						sb.append("\nidAppaltiSelezionati = null");
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
