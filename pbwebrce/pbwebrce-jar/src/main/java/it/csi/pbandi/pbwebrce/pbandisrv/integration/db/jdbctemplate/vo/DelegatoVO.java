/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.jdbctemplate.vo;


public class DelegatoVO extends GenericVO{
	
	static final long serialVersionUID = 1;
	
	private String capResidenza = null;
	private String codiceFiscaleSoggetto = null;
	private String cognome = null;
	private String comuneResidenza = null;
 	private java.util.Date dataNascita = null;
 	private Long idPersonaFisica = null;
 	private Long idIndirizzoPersonaFisica = null;
	private Long idProgetto = null;
	private Long idSoggetto = null;
	private String indirizzoResidenza = null;
	private String luogoNascita = null;
	private String nome = null;
	private String provinciaResidenza = null;
	
	public String getCapResidenza() {
		return capResidenza;
	}
	public String getComuneResidenza() {
		return comuneResidenza;
	}
	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}
	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}
	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}
	public java.util.Date getDataNascita() {
		return dataNascita;
	}
	public Long getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public Long getIdIndirizzoPersonaFisica() {
		return idIndirizzoPersonaFisica;
	}
	public void setDataNascita(java.util.Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setIdPersonaFisica(Long idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	public void setIdIndirizzoPersonaFisica(Long idIndirizzoPersonaFisica) {
		this.idIndirizzoPersonaFisica = idIndirizzoPersonaFisica;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
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
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	/*	public Long getIdPersonaFisica() {
	return idPersonaFisica;
}
public void setIdPersonaFisica(Long idPersonaFisica) {
	this.idPersonaFisica = idPersonaFisica;
}
public Long getIdIndirizzoPersonaFisica() {
	return idIndirizzoPersonaFisica;
}
public void setIdIndirizzoPersonaFisica(Long idIndirizzoPersonaFisica) {
	this.idIndirizzoPersonaFisica = idIndirizzoPersonaFisica;
}*/
/*	public java.util.Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(java.util.Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Long getIdComuneItalianoNascita() {
		return idComuneItalianoNascita;
	}
	public void setIdComuneItalianoNascita(Long idComuneItalianoNascita) {
		this.idComuneItalianoNascita = idComuneItalianoNascita;
	}
	public Long getIdComuneEsteroNascita() {
		return idComuneEsteroNascita;
	}
	public void setIdComuneEsteroNascita(Long idComuneEsteroNascita) {
		this.idComuneEsteroNascita = idComuneEsteroNascita;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}
	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}
	public String getCapResidenza() {
		return capResidenza;
	}
	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}
	public Long getIdComuneResidenza() {
		return idComuneResidenza;
	}
	public void setIdComuneResidenza(Long idComuneResidenza) {
		this.idComuneResidenza = idComuneResidenza;
	}
	public Long getIdComuneEsteroResidenza() {
		return idComuneEsteroResidenza;
	}
	public void setIdComuneEsteroResidenza(Long idComuneEsteroResidenza) {
		this.idComuneEsteroResidenza = idComuneEsteroResidenza;
	}
	public String getComuneResidenza() {
		return comuneResidenza;
	}
	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}
	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}
	public Long getIdProvinciaResidenza() {
		return idProvinciaResidenza;
	}
	public void setIdProvinciaResidenza(Long idProvinciaResidenza) {
		this.idProvinciaResidenza = idProvinciaResidenza;
	}
	public Long getIdNazioneResidenza() {
		return idNazioneResidenza;
	}
	public void setIdNazioneResidenza(Long idNazioneResidenza) {
		this.idNazioneResidenza = idNazioneResidenza;
	}*/


	

	
}
