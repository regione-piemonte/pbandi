/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class PersonaFisicaVO {

	private BigDecimal idSoggetto;
	private BigDecimal idPersonaFisica;
	private String cognome;
	private String nome;
	private String codiceFiscale;
	private Date dtNascita;
	
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtNascita() {
		return dtNascita;
	}
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "PersonaFisicaVO [idSoggetto=" + idSoggetto + ", idPersonaFisica=" + idPersonaFisica );
		sb.append(", cognome=" + cognome + ", nome=" + nome + ", codiceFiscale=" + codiceFiscale + ", dtNascita=" + dtNascita + "]");
		return sb.toString();
	}
	
	
}
