/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.request;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbworkspace.util.BeanUtil;

public class RicercaProgettiRequest {
	
	private Long progrBandoLineaIntervento;	
	private String codiceProgetto;
	private String titoloProgetto;
	private String cup;
	private String beneficiario;
	
	private Long idSoggetto;
	private String codiceRuolo;
	
	/*
	private Long idProgetto;
		public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	*/
	
	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
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
