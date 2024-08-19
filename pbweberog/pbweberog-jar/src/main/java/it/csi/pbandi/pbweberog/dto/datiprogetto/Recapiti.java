/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;


public class Recapiti implements java.io.Serializable {

	private Long idRecapiti;
	private String email;
	private String pec;
	private String fax;
	private String telefono;
	private Long idUtenteIns;
	private Long idUtenteAgg;
	private String dtInizioValidita;
	private String dtFineValidita;
	private String flagEmailConfermata;
	private String flagPecConfermata;
	private Long progrSoggettoProgetto;
	private Long progrSoggettoProgettoSede;
	private static final long serialVersionUID = 1L;
	
	
	
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

	public String getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(String dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public String getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(String dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
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

	public Recapiti() {
		super();

	}

	public String toString() {
		return "Recapiti [dtFineValidita=" + dtFineValidita
				+ ", dtInizioValidita=" + dtInizioValidita + ", email="
				+ email + ", fax=" + fax + ", flagEmailConfermata="
				+ flagEmailConfermata + ", idRecapiti=" + idRecapiti
				+ ", idUtenteAgg=" + idUtenteAgg + ", idUtenteIns="
				+ idUtenteIns + ", telefono=" + telefono
				+ ", progrSoggettoProgetto=" + progrSoggettoProgetto 
				+ ", progrSoggettoProgettoSede=" + progrSoggettoProgettoSede + "]";
	}
}
