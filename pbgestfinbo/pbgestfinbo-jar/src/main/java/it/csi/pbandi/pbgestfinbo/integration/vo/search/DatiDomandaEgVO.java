/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;

public class DatiDomandaEgVO {

	//STATO DOMANDA
	private String numeroDomanda;
	private String statoDomanda;
	private String dataPresentazioneDomanda;
	
	//SEDE INTERVENTO
	private String partitaIva;
	private String descIndirizzo;
	private String cap;
	private String descComune;
	private String descProvincia;
	private String descRegione;
	private String descNazione;
	private Long idComune; 
	private Long idProvincia; 
	private Long idRegione; 
	private Long idNazione; 

	//RECAPITI
	private Long idRecapiti; 
	private String telefono;
	private String fax;
	private String email;
	private String pec;
	//CONTO CORRENTE
	private String iban;
	private String banca;
	
	private Long idEstremiBancari; 
	private Long idIndirizzo; 
	private BigDecimal idProgetto; 
	
	private String flagSedeAmm; 
	
	
	public String getFlagSedeAmm() {
		return flagSedeAmm;
	}
	public void setFlagSedeAmm(String flagSedeAmm) {
		this.flagSedeAmm = flagSedeAmm;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdComune() {
		return idComune;
	}
	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}
	public Long getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}
	public Long getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(Long idRegione) {
		this.idRegione = idRegione;
	}
	public Long getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}
	public Long getIdRecapiti() {
		return idRecapiti;
	}
	public void setIdRecapiti(Long idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	public Long getIdEstremiBancari() {
		return idEstremiBancari;
	}
	public void setIdEstremiBancari(Long idEstremiBancari) {
		this.idEstremiBancari = idEstremiBancari;
	}
	public Long getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(Long idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	public String getStatoDomanda() {
		return statoDomanda;
	}
	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
	}
	public String getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}
	public void setDataPresentazioneDomanda(String dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}
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
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public String getDescProvincia() {
		return descProvincia;
	}
	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}
	public String getDescRegione() {
		return descRegione;
	}
	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}
	public String getDescNazione() {
		return descNazione;
	}
	public void setDescNazione(String descNazione) {
		this.descNazione = descNazione;
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
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBanca() {
		return banca;
	}
	public void setBanca(String banca) {
		this.banca = banca;
	}
	
	
	@Override
	public String toString() {
		return "DatiDomandaEgVO [numeroDomanda=" + numeroDomanda + ", statoDomanda=" + statoDomanda
				+ ", dataPresentazioneDomanda=" + dataPresentazioneDomanda + ", partitaIva=" + partitaIva
				+ ", descIndirizzo=" + descIndirizzo + ", cap=" + cap + ", descComune=" + descComune
				+ ", descProvincia=" + descProvincia + ", descRegione=" + descRegione + ", descNazione=" + descNazione
				+ ", telefono=" + telefono + ", fax=" + fax + ", email=" + email + ", pec=" + pec + ", iban=" + iban
				+ ", banca=" + banca + "]";
	}
	
	
	public DatiDomandaEgVO() {
		super();
		
	}
	
	

}
