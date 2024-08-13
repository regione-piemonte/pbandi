/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

import java.util.Date;

public class RappresentanteLegaleDichiarazionePrivatoCittadinoVO extends RappresentanteLegaleDichiarazioneVO{
	
	
	private Long idSoggetto = null;
	private Long idPersonaFisica= null;
	private Long idProgetto = null;
	//private Long idIndirizzoPersonaFisica = null;
	private String cognome = null;
	private String nome = null;
	private Date dataNascita = null;
	//private Long idComuneItalianoNascita = null;
	//private Long idComuneEsteroNascita = null;
	private String luogoNascita = null;
	private String indirizzoResidenza = null;
	private String capResidenza = null;
	//private Long idComuneResidenza = null;
	//private Long idComuneEsteroResidenza = null;
	private String comuneResidenza = null;
	private String provinciaResidenza = null;
	//private Long idProvinciaResidenza = null;
	//private Long idNazioneResidenza = null;
	private String codiceFiscaleSoggetto = null;
	
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public Long getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public void setIdPersonaFisica(Long idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
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
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
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
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	
	
	
	
	
	

}
