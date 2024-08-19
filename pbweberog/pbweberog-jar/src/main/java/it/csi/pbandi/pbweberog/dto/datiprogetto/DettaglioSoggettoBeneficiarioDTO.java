/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

public class DettaglioSoggettoBeneficiarioDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Long idProgetto;
	private Long flagPubblicoPrivato;
	private String codiceFiscale;
	private String descrizioneBeneficiario;
	private SedeProgettoDTO[] sediIntervento;
	private String codUniIpa;
	private Long idSoggettoBeneficiario;
	private Long idEnteGiuridico;
	private String sedeLegale;
	private String email;
	private String fax;
	private String telefono;
	private Long idRecapiti;;
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}
	public void setFlagPubblicoPrivato(Long flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDescrizioneBeneficiario() {
		return descrizioneBeneficiario;
	}
	public void setDescrizioneBeneficiario(String descrizioneBeneficiario) {
		this.descrizioneBeneficiario = descrizioneBeneficiario;
	}
	public SedeProgettoDTO[] getSediIntervento() {
		return sediIntervento;
	}
	public void setSediIntervento(SedeProgettoDTO[] sediIntervento) {
		this.sediIntervento = sediIntervento;
	}
	public String getCodUniIpa() {
		return codUniIpa;
	}
	public void setCodUniIpa(String codUniIpa) {
		this.codUniIpa = codUniIpa;
	}
	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public Long getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	public void setIdEnteGiuridico(Long idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	public String getSedeLegale() {
		return sedeLegale;
	}
	public void setSedeLegale(String sedeLegale) {
		this.sedeLegale = sedeLegale;
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
	public Long getIdRecapiti() {
		return idRecapiti;
	}
	public void setIdRecapiti(Long idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	
	
}
