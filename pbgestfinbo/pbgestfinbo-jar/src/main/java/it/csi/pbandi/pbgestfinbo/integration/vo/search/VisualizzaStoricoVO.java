/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.sql.Date;

public class VisualizzaStoricoVO {
	
	private String ragioneSoc;
	private String formaGiuridica;
	private Long flagPubblicoPrivato;
	private String codiceUniIpa;
	private String codiceFiscale;
	private Date dataCostituzione;
	private String pec;
	private String indirizzo;
	private String partitaIva;
	private String comune;
	private String provincia;
	private String cap;
	private String regione;
	private String nazione;
	private String codiceAteco;
	private String descAttivitaPrevalente;
	private String ratingDiLegalita;
	private String statoAttivita;
	private Date dataInizioAttivita;
	private String periodoChiusuraEsercizio;
	private Date dataChiusuraEsercizio;
	private String numeroRea;
	private Date dataIscrizione;
	private String provinciaIscrizione;
	private String sezioneDiAppartenenza;
	
	public String getRagioneSoc() {
		return ragioneSoc;
	}
	public void setRagioneSoc(String ragioneSoc) {
		this.ragioneSoc = ragioneSoc;
	}
	public String getFormaGiuridica() {
		return formaGiuridica;
	}
	public void setFormaGiuridica(String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}
	public Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}
	public void setFlagPubblicoPrivato(Long flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}
	public String getCodiceUniIpa() {
		return codiceUniIpa;
	}
	public void setCodiceUniIpa(String codiceUniIpa) {
		this.codiceUniIpa = codiceUniIpa;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Date getDataCostituzione() {
		return dataCostituzione;
	}
	public void setDataCostituzione(Date dataCostituzione) {
		this.dataCostituzione = dataCostituzione;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
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
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
	}
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	public String getCodiceAteco() {
		return codiceAteco;
	}
	public void setCodiceAteco(String codiceAteco) {
		this.codiceAteco = codiceAteco;
	}
	public String getDescAttivitaPrevalente() {
		return descAttivitaPrevalente;
	}
	public void setDescAttivitaPrevalente(String descAttivitaPrevalente) {
		this.descAttivitaPrevalente = descAttivitaPrevalente;
	}
	public String getRatingDiLegalita() {
		return ratingDiLegalita;
	}
	public void setRatingDiLegalita(String ratingDiLegalita) {
		this.ratingDiLegalita = ratingDiLegalita;
	}
	public String getStatoAttivita() {
		return statoAttivita;
	}
	public void setStatoAttivita(String statoAttivita) {
		this.statoAttivita = statoAttivita;
	}
	public Date getDataInizioAttivita() {
		return dataInizioAttivita;
	}
	public void setDataInizioAttivita(Date dataInizioAttivita) {
		this.dataInizioAttivita = dataInizioAttivita;
	}
	public String getPeriodoChiusuraEsercizio() {
		return periodoChiusuraEsercizio;
	}
	public void setPeriodoChiusuraEsercizio(String periodoChiusuraEsercizio) {
		this.periodoChiusuraEsercizio = periodoChiusuraEsercizio;
	}
	public Date getDataChiusuraEsercizio() {
		return dataChiusuraEsercizio;
	}
	public void setDataChiusuraEsercizio(Date dataChiusuraEsercizio) {
		this.dataChiusuraEsercizio = dataChiusuraEsercizio;
	}
	public String getNumeroRea() {
		return numeroRea;
	}
	public void setNumeroRea(String numeroRea) {
		this.numeroRea = numeroRea;
	}
	public Date getDataIscrizione() {
		return dataIscrizione;
	}
	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}
	public String getProvinciaIscrizione() {
		return provinciaIscrizione;
	}
	public void setProvinciaIscrizione(String provinciaIscrizione) {
		this.provinciaIscrizione = provinciaIscrizione;
	}
	public String getSezioneDiAppartenenza() {
		return sezioneDiAppartenenza;
	}
	public void setSezioneDiAppartenenza(String sezioneDiAppartenenza) {
		this.sezioneDiAppartenenza = sezioneDiAppartenenza;
	}
	@Override
	public String toString() {
		return "VisualizzaStoricoVO [ragioneSoc=" + ragioneSoc + ", formaGiuridica=" + formaGiuridica
				+ ", flagPubblicoPrivato=" + flagPubblicoPrivato + ", codiceUniIpa=" + codiceUniIpa + ", codiceFiscale="
				+ codiceFiscale + ", dataCostituzione=" + dataCostituzione + ", pec=" + pec + ", indirizzo=" + indirizzo
				+ ", partitaIva=" + partitaIva + ", comune=" + comune + ", provincia=" + provincia + ", cap=" + cap
				+ ", regione=" + regione + ", nazione=" + nazione + ", codiceAteco=" + codiceAteco
				+ ", descAttivitaPrevalente=" + descAttivitaPrevalente + ", ratingDiLegalita=" + ratingDiLegalita
				+ ", statoAttivita=" + statoAttivita + ", dataInizioAttivita=" + dataInizioAttivita
				+ ", periodoChiusuraEsercizio=" + periodoChiusuraEsercizio + ", dataChiusuraEsercizio="
				+ dataChiusuraEsercizio + ", numeroRea=" + numeroRea + ", dataIscrizione=" + dataIscrizione
				+ ", provinciaIscrizione=" + provinciaIscrizione + ", sezioneDiAppartenenza=" + sezioneDiAppartenenza
				+ "]";
	}

	

}
