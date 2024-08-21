/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli;


import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class CampioneCertificazioneVO extends GenericVO{
	
	private BigDecimal idPropostaCertificaz;
	private Date dataCampionamento;
	private BigDecimal progrOperazione;
	private BigDecimal avanzamento;
	private String titoloProgetto;
	private String descTipoOperazione;
	private String universo;
	private BigDecimal idCategAnagrafica;
	private BigDecimal idPeriodo;
	private String tipoControlli;
	private String descLinea;
	private BigDecimal idLineaDiIntervento;
	private String descPeriodoVisualizzata;
	private String asse;
	private String codiceVisualizzato;
	private BigDecimal idCampione;
	private BigDecimal idProgetto;
	
	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public Date getDataCampionamento() {
		return dataCampionamento;
	}
	public void setDataCampionamento(Date dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}
	public BigDecimal getProgrOperazione() {
		return progrOperazione;
	}
	public void setProgrOperazione(BigDecimal progrOperazione) {
		this.progrOperazione = progrOperazione;
	}
	public BigDecimal getAvanzamento() {
		return avanzamento;
	}
	public void setAvanzamento(BigDecimal avanzamento) {
		this.avanzamento = avanzamento;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public String getUniverso() {
		return universo;
	}
	public void setUniverso(String universo) {
		this.universo = universo;
	}
	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}
	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public String getTipoControlli() {
		return tipoControlli;
	}
	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}
	public String getDescTipoOperazione() {
		return descTipoOperazione;
	}
	public void setDescTipoOperazione(String descTipoOperazione) {
		this.descTipoOperazione = descTipoOperazione;
	}
	public String getDescLinea() {
		return descLinea;
	}
	public void setDescLinea(String descLinea) {
		this.descLinea = descLinea;
	}
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	public String getAsse() {
		return asse;
	}
	public void setAsse(String asse) {
		this.asse = asse;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public BigDecimal getIdCampione() {
		return idCampione;
	}
	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
}

