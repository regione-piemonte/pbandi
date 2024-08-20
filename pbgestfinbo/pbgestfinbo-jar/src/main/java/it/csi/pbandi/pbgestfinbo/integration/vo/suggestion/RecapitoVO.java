/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.suggestion;

import java.sql.Date;

public class RecapitoVO {

	
	private Long idRecapiti = null;
	private String email = null;
	private String fax = null;
	private String telefono = null;
	private Long idUtenteIns = null;
	private Long idUtenteAgg = null;
	private Date dtInizioValidita = null;
	private Date dtFineValidita = null;
	private String flagEmailConfermata = null;
	private Long progrSoggettoProgetto = null;
	private Long progrSoggettoProgettoSede = null;
	private Long progrSoggettoDomandaSede = null;
	private String pec =null;
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
	public String getFlagEmailConfermata() {
		return flagEmailConfermata;
	}
	public void setFlagEmailConfermata(String flagEmailConfermata) {
		this.flagEmailConfermata = flagEmailConfermata;
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
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public Long getProgrSoggettoDomandaSede() {
		return progrSoggettoDomandaSede;
	}
	public void setProgrSoggettoDomandaSede(Long progrSoggettoDomandaSede) {
		this.progrSoggettoDomandaSede = progrSoggettoDomandaSede;
	} 
	
	
	
	
}
