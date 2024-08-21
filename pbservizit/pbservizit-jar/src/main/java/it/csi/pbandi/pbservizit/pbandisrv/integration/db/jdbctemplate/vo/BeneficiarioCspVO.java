/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.sql.Date;

//import org.apache.cxf.common.util.StringUtils;

public class BeneficiarioCspVO extends GenericVO {
	
	private String codiceFiscaleSoggetto;	
	private String iban;
	private String denominazioneEnteGiuridico;
	private Date dtCostituzione;
	private Long idFormaGiuridica;
	private String descFormaGiuridica;
	private Long idSettoreAttivita;
	private String descSettore;
	private Long idAttivitaAteco;
	private String descAteco;
	private String partitaIva;
	private String descIndirizzo;
	private String cap;
	private Long idNazione;
	private String descNazione;
	private Long idRegione;
	private String descRegione;
	private Long idProvincia;
	private String descProvincia;
	private Long idComune;
	private String descComune;
	private Long idComuneEstero;
	private String descComuneEstero;
	private String email;
	private String telefono;
	private String fax;

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getDescIndirizzo() {
		return descIndirizzo;
	}

	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public Long getIdNazione() {
		return idNazione;
	}

	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}

	public String getDescNazione() {
		return descNazione;
	}

	public void setDescNazione(String descNazione) {
		this.descNazione = descNazione;
	}

	public Long getIdRegione() {
		return idRegione;
	}

	public void setIdRegione(Long idRegione) {
		this.idRegione = idRegione;
	}

	public String getDescRegione() {
		return descRegione;
	}

	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getDescProvincia() {
		return descProvincia;
	}

	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}

	public Long getIdComune() {
		return idComune;
	}

	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}

	public String getDescComune() {
		return descComune;
	}

	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}

	public Long getIdComuneEstero() {
		return idComuneEstero;
	}

	public void setIdComuneEstero(Long idComuneEstero) {
		this.idComuneEstero = idComuneEstero;
	}

	public String getDescComuneEstero() {
		return descComuneEstero;
	}

	public void setDescComuneEstero(String descComuneEstero) {
		this.descComuneEstero = descComuneEstero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getDenominazioneEnteGiuridico() {
		return denominazioneEnteGiuridico;
	}

	public void setDenominazioneEnteGiuridico(String denominazioneEnteGiuridico) {
		this.denominazioneEnteGiuridico = denominazioneEnteGiuridico;
	}

	public Date getDtCostituzione() {
		return dtCostituzione;
	}

	public void setDtCostituzione(Date dtCostituzione) {
		this.dtCostituzione = dtCostituzione;
	}

	public Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}

	public void setIdFormaGiuridica(Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}

	public String getDescFormaGiuridica() {
		return descFormaGiuridica;
	}

	public void setDescFormaGiuridica(String descFormaGiuridica) {
		this.descFormaGiuridica = descFormaGiuridica;
	}

	public Long getIdSettoreAttivita() {
		return idSettoreAttivita;
	}

	public void setIdSettoreAttivita(Long idSettoreAttivita) {
		this.idSettoreAttivita = idSettoreAttivita;
	}

	public String getDescSettore() {
		return descSettore;
	}

	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}

	public Long getIdAttivitaAteco() {
		return idAttivitaAteco;
	}

	public void setIdAttivitaAteco(Long idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}

	public String getDescAteco() {
		return descAteco;
	}

	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}

	public String toString() {
		String s = this.getCodiceFiscaleSoggetto()+" - "+this.getDenominazioneEnteGiuridico()+" - "+this.getIban()+" - "+this.getDtCostituzione()+" - "+this.getDescFormaGiuridica()+" - "+this.getDescSettore()+" - "+this.getDescAteco()+" - "+this.getEmail()+" - "+this.getTelefono()+" - "+this.getFax();
		return s;
	}
	
}
