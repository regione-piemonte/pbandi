/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;
import java.util.Date;

public class ElaboraRichiestaVO {
	
	private String idRichiesta;
	private String idUtenteAgg;
	private String idTracciaturaRichiesta;
	private String idTipoRichiesta;
	private String idStatoRichiesta;
	private String tipoComunicazione;
	private Date dataComunicazione;
	private String destinatarioMittente;
	private String numeroProtocollo;
	private String motivazione;
	private String numeroDomanda;
	private String nag;
	private Date dataEmissione;
	private Date dataScadenza;
	private String esito;
	private String numeroProtocolloInps;
	private String numeroProtocolloRicevuta;
	private Date dataRicezione;
	private String numeroProtocolloPrefettura;
	private String flagUrgenza; 
	private String idDomanda; 
	
	

	public String getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}
	public String getFlagUrgenza() {
		return flagUrgenza;
	}
	public void setFlagUrgenza(String flagUrgenza) {
		this.flagUrgenza = flagUrgenza;
	}
	public String getNumeroProtocolloPrefettura() {
		return numeroProtocolloPrefettura;
	}
	public void setNumeroProtocolloPrefettura(String numeroProtocolloPrefettura) {
		this.numeroProtocolloPrefettura = numeroProtocolloPrefettura;
	}
	public String getNumeroProtocolloRicevuta() {
		return numeroProtocolloRicevuta;
	}
	public void setNumeroProtocolloRicevuta(String numeroProtocolloRicevuta) {
		this.numeroProtocolloRicevuta = numeroProtocolloRicevuta;
	}
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public String getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(String idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	public String getNag() {
		return nag;
	}
	public void setNag(String nag) {
		this.nag = nag;
	}
	public Date getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getNumeroProtocolloInps() {
		return numeroProtocolloInps;
	}
	public void setNumeroProtocolloInps(String numeroProtocolloInps) {
		this.numeroProtocolloInps = numeroProtocolloInps;
	}
	public String getTipoComunicazione() {
		return tipoComunicazione;
	}
	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}
	public String getIdTracciaturaRichiesta() {
		return idTracciaturaRichiesta;
	}
	public void setIdTracciaturaRichiesta(String idTracciaturaRichiesta) {
		this.idTracciaturaRichiesta = idTracciaturaRichiesta;
	}
	public String getIdRichiesta() {
		return idRichiesta;
	}
	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	public String getIdTipoRichiesta() {
		return idTipoRichiesta;
	}
	public void setIdTipoRichiesta(String idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}
	public String getIdStatoRichiesta() {
		return idStatoRichiesta;
	}
	public void setIdStatoRichiesta(String idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}
	public Date getDataComunicazione() {
		return dataComunicazione;
	}
	public void setDataComunicazione(Date dataComunicazione) {
		this.dataComunicazione = dataComunicazione;
	}
	public String getDestinatarioMittente() {
		return destinatarioMittente;
	}
	public void setDestinatarioMittente(String destinatarioMittente) {
		this.destinatarioMittente = destinatarioMittente;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	
	@Override
	public String toString() {
		return "ElaboraRichiestaVO [idRichiesta=" + idRichiesta + ", idUtenteAgg=" + idUtenteAgg
				+ ", idTracciaturaRichiesta=" + idTracciaturaRichiesta + ", idTipoRichiesta=" + idTipoRichiesta
				+ ", idStatoRichiesta=" + idStatoRichiesta + ", tipoComunicazione=" + tipoComunicazione
				+ ", dataComunicazione=" + dataComunicazione + ", destinatarioMittente=" + destinatarioMittente
				+ ", numeroProtocollo=" + numeroProtocollo + ", motivazione=" + motivazione + ", numeroDomanda="
				+ numeroDomanda + ", nag=" + nag + ", dataEmissione=" + dataEmissione + ", dataScadenza=" + dataScadenza
				+ ", esito=" + esito + ", numeroProtocolloInps=" + numeroProtocolloInps + ", numeroProtocolloRicevuta="
				+ numeroProtocolloRicevuta + ", dataRicezione=" + dataRicezione + ", numeroProtocolloPrefettura="
				+ numeroProtocolloPrefettura + "]";
	}
	
	
}
