/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestioneprogetto;

import java.sql.Date;

// PK : se gli faccio estendere GenericVO si spacca
public class RecapitoVO  {  //extends GenericVO{

	static final long serialVersionUID = 1L;
	
	private Long idRecapiti = null;
	private String email = null;
	private String pec = null;
	private String fax = null;
	private String telefono = null;
	private Long idUtenteIns = null;
	private Long idUtenteAgg = null;
	private Date dtInizioValidita = null;
	private Date dtFineValidita = null;
	private String flagEmailConfermata = null;
	private String flagPecConfermata = null;
	private Long progrSoggettoProgetto = null;
	private Long progrSoggettoProgettoSede = null;
	
	public Long getIdRecapiti() {
		return idRecapiti;
	}
	public void setIdRecapiti(Long idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Long getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public String getFlagEmailConfermata() {
		return flagEmailConfermata;
	}
	public void setFlagEmailConfermata(String flagEmailConfermata) {
		this.flagEmailConfermata = flagEmailConfermata;
	}
	public String getFlagPecConfermata() {
		return flagPecConfermata;
	}
	public void setFlagPecConfermata(String flagPecConfermata) {
		this.flagPecConfermata = flagPecConfermata;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	public Long getProgrSoggettoProgettoSede() {
		return progrSoggettoProgettoSede;
	}
	public void setProgrSoggettoProgettoSede(Long progrSoggettoProgettoSede) {
		this.progrSoggettoProgettoSede = progrSoggettoProgettoSede;
	}
	
	@Override
	public String toString() {
		return "RecapitoVO [dtFineValidita=" + dtFineValidita
				+ ", dtInizioValidita=" + dtInizioValidita + ", email=" + email
				+ ", fax=" + fax + ", flagEmailConfermata="
				+ flagEmailConfermata + ", idRecapiti=" + idRecapiti
				+ ", idUtenteAgg=" + idUtenteAgg + ", idUtenteIns="
				+ idUtenteIns + ", telefono=" + telefono 
				+ ", progrSoggettoProgetto=" + progrSoggettoProgetto 
				+ ", progrSoggettoProgettoSede=" + progrSoggettoProgettoSede + "]";
	}
		
}
