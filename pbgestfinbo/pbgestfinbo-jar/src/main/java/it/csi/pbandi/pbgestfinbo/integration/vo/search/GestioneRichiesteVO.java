/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.util.Date;

public class GestioneRichiesteVO {
	
	private String richiedente;
	
	private String codiceFiscale;
	
	private String tipoRichiesta;
	
	private String statoRichiesta;
	
	private Date dataRichiesta;
	
	private String codiceBando;
	
	private String numeroDomanda;
	
	private String codiceProgetto;
	
	private String nag;
	
	private String denominazione;
	
	private String partitaIva;
	
	private String modalitaRichiesta;
	
	private Date dataChiusura;
	
	private String numeroRichiesta;
	private String ndg ;
	
	

	public String getNdg() {
		return ndg;
	}

	public void setNdg(String ndg) {
		this.ndg = ndg;
	}

	public String getNag() {
		return nag;
	}

	public void setNag(String nag) {
		this.nag = nag;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getModalitaRichiesta() {
		return modalitaRichiesta;
	}

	public void setModalitaRichiesta(String modalitaRichiesta) {
		this.modalitaRichiesta = modalitaRichiesta;
	}

	
	public Date getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getNumeroRichiesta() {
		return numeroRichiesta;
	}

	public void setNumeroRichiesta(String numeroRichiesta) {
		this.numeroRichiesta = numeroRichiesta;
	}

	public String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getTipoRichiesta() {
		return tipoRichiesta;
	}

	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
	}

	public String getStatoRichiesta() {
		return statoRichiesta;
	}

	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public String getCodiceBando() {
		return codiceBando;
	}

	public void setCodiceBando(String codiceBando) {
		this.codiceBando = codiceBando;
	}

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public GestioneRichiesteVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "GestioneRichiesteVO [richiedente=" + richiedente + ", codiceFiscale=" + codiceFiscale
				+ ", tipoRichiesta=" + tipoRichiesta + ", statoRichiesta=" + statoRichiesta + ", dataRichiesta="
				+ dataRichiesta + ", codiceBando=" + codiceBando + ", numeroDomanda=" + numeroDomanda
				+ ", codiceProgetto=" + codiceProgetto + ", nag=" + nag + ", denominazione=" + denominazione
				+ ", partitaIva=" + partitaIva + ", modalitaRichiesta=" + modalitaRichiesta + ", dataChiusura="
				+ dataChiusura + ", numeroRichiesta=" + numeroRichiesta + "]";
	}

	

	
	

}
