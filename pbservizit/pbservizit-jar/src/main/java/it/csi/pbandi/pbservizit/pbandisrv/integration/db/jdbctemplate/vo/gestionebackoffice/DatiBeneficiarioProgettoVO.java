/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

import java.math.BigDecimal;
import java.sql.Date;

public class DatiBeneficiarioProgettoVO extends GenericVO {
	
	private String cap;
	private String civico;
	private String codiceFiscale;
	private String codiceVisualizzato;
	private String denominazione;
	private String email;
	private String fax;
	private String indirizzo;
	private BigDecimal idEnteGiuridico;
	private BigDecimal idComune;
	private BigDecimal idComuneEstero;
	private BigDecimal idIndirizzo;
	private BigDecimal idNazione;
	private BigDecimal idProgetto;
	private BigDecimal idProvincia;
	private BigDecimal idRecapiti;
	private BigDecimal idRegione;
	private BigDecimal idSede;
	private BigDecimal idSoggetto;
	private String partitaIva;
	private String telefono;
	
	public String getEmail() {
		return email;
	}
	public String getFax() {
		return fax;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public BigDecimal getIdSede() {
		return idSede;
	}
	public BigDecimal getIdRecapiti() {
		return idRecapiti;
	}
 
	public String getCivico() {
		return civico;
	}
	public String getCap() {
		return cap;
	}
	public BigDecimal getIdComune() {
		return idComune;
	}
	public BigDecimal getIdComuneEstero() {
		return idComuneEstero;
	}
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	public BigDecimal getIdNazione() {
		return idNazione;
	}
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
	}
	public void setIdRecapiti(BigDecimal idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	 
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	public void setIdComuneEstero(BigDecimal idComuneEstero) {
		this.idComuneEstero = idComuneEstero;
	}
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	 
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


}
