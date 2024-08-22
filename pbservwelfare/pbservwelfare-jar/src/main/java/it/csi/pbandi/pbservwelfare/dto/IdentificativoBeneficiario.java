/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

public class IdentificativoBeneficiario {
	
	private int progrSoggBeneficiario;
	private int idSoggBeneficiario;
	private int idPersonaFisicaBeneficiario;
	private int idProgetto;
	private String codiceFiscaleSoggetto;
	private int idRecapitiPersonaFisica;
	private int idIndirizzoPersonaFisica;
	
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
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
	public int getProgrSoggBeneficiario() {
		return progrSoggBeneficiario;
	}
	public void setProgrSoggBeneficiario(int progrSoggBeneficiario) {
		this.progrSoggBeneficiario = progrSoggBeneficiario;
	}
	public int getIdSoggBeneficiario() {
		return idSoggBeneficiario;
	}
	public void setIdSoggBeneficiario(int idSoggBeneficiario) {
		this.idSoggBeneficiario = idSoggBeneficiario;
	}
	public int getIdPersonaFisicaBeneficiario() {
		return idPersonaFisicaBeneficiario;
	}
	public void setIdPersonaFisicaBeneficiario(int idPersonaFisicaBeneficiario) {
		this.idPersonaFisicaBeneficiario = idPersonaFisicaBeneficiario;
	}
	public int getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

}
