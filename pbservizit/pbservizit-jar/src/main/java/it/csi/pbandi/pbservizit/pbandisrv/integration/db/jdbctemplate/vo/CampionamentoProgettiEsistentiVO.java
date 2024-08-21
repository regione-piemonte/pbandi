/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

// Join tra from PBANDI_T_CAMPIONAMENTO e PBANDI_R_CAMPIONAMENTO.

public class CampionamentoProgettiEsistentiVO extends GenericVO {
		
	private BigDecimal idCampione;
	private Date dataCampionamento;
	private BigDecimal idPropostaCertificaz;
	private BigDecimal idLineaDiIntervento;
	private BigDecimal idCategAnagrafica;
	private BigDecimal idPeriodo;
	private String tipoControlli;	
	private BigDecimal idProgetto;
	public BigDecimal getIdCampione() {
		return idCampione;
	}
	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}
	public Date getDataCampionamento() {
		return dataCampionamento;
	}
	public void setDataCampionamento(Date dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
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
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
}
