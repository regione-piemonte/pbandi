/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

public class SoggettiCorrelati {
	
	public int progrSoggettoProgettoCorrelato;
	public int idPersonaFisica;
	public String codFiscaleSoggetto;
	public String descTipoSoggettoCorrelato;
	public int idRecapitiPersonaFisica;
	public int idIndirizzoPersonaFisica;
	
	public int getProgrSoggettoProgettoCorrelato() {
		return progrSoggettoProgettoCorrelato;
	}
	public void setProgrSoggettoProgettoCorrelato(int progrSoggettoProgettoCorrelato) {
		this.progrSoggettoProgettoCorrelato = progrSoggettoProgettoCorrelato;
	}
	public int getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public void setIdPersonaFisica(int idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	public String getCodFiscaleSoggetto() {
		return codFiscaleSoggetto;
	}
	public void setCodFiscaleSoggetto(String codFiscaleSoggetto) {
		this.codFiscaleSoggetto = codFiscaleSoggetto;
	}
	public String getDescTipoSoggettoCorrelato() {
		return descTipoSoggettoCorrelato;
	}
	public void setDescTipoSoggettoCorrelato(String descTipoSoggettoCorrelato) {
		this.descTipoSoggettoCorrelato = descTipoSoggettoCorrelato;
	}
	public int getIdRecapitiPersonaFisica() {
		return idRecapitiPersonaFisica;
	}
	public void setIdRecapitiPersonaFisica(int idRecapitiPersonaFisica) {
		this.idRecapitiPersonaFisica = idRecapitiPersonaFisica;
	}
	public int getIdIndirizzoPersonaFisica() {
		return idIndirizzoPersonaFisica;
	}
	public void setIdIndirizzoPersonaFisica(int idIndirizzoPersonaFisica) {
		this.idIndirizzoPersonaFisica = idIndirizzoPersonaFisica;
	}
	
	

}
