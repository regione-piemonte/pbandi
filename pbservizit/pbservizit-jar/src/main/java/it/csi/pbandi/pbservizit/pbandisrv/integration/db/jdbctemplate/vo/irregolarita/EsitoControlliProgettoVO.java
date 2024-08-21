/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class EsitoControlliProgettoVO extends GenericVO {
	
	private BigDecimal idEsitoControllo;
	private String esitoControllo;
	private BigDecimal idPeriodo;
	private String descPeriodoVisualizzata;
	private BigDecimal idCategAnagrafica;
	private String descCategAnagrafica;
	private String tipoControlli;
	private Date dtComunicazione;
	private Date dataInizioControlli;
	private Date dataFineControlli;
	private String note;
	private BigDecimal idProgetto;
	private String codiceVisualizzato;
	private BigDecimal idSoggettoBeneficiario;
	private String denominazioneBeneficiario;
	private Date dataCampione;
	
	// Jira PBANDI-2921.
	private BigDecimal idIrregolaritaCollegata;	

	public BigDecimal getIdIrregolaritaCollegata() {
		return idIrregolaritaCollegata;
	}
	public void setIdIrregolaritaCollegata(BigDecimal idIrregolaritaCollegata) {
		this.idIrregolaritaCollegata = idIrregolaritaCollegata;
	}
	public BigDecimal getIdEsitoControllo() {
		return idEsitoControllo;
	}
	public void setIdEsitoControllo(BigDecimal idEsitoControllo) {
		this.idEsitoControllo = idEsitoControllo;
	}
	public String getEsitoControllo() {
		return esitoControllo;
	}
	public void setEsitoControllo(String esitoControllo) {
		this.esitoControllo = esitoControllo;
	}
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}
	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
	}
	public String getDescCategAnagrafica() {
		return descCategAnagrafica;
	}
	public void setDescCategAnagrafica(String descCategAnagrafica) {
		this.descCategAnagrafica = descCategAnagrafica;
	}
	public String getTipoControlli() {
		return tipoControlli;
	}
	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}
	public Date getDtComunicazione() {
		return dtComunicazione;
	}
	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}
	public Date getDataInizioControlli() {
		return dataInizioControlli;
	}
	public void setDataInizioControlli(Date dataInizioControlli) {
		this.dataInizioControlli = dataInizioControlli;
	}
	public Date getDataFineControlli() {
		return dataFineControlli;
	}
	public void setDataFineControlli(Date dataFineControlli) {
		this.dataFineControlli = dataFineControlli;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	public Date getDataCampione() {
		return dataCampione;
	}
	public void setDataCampione(Date dataCampione) {
		this.dataCampione = dataCampione;
	}
	@Override
	public String toString() {
		return "EsitoControlliProgettoVO [codiceVisualizzato="
				+ codiceVisualizzato + ", dataCampione=" + dataCampione
				+ ", dataFineControlli=" + dataFineControlli
				+ ", dataInizioControlli=" + dataInizioControlli
				+ ", denominazioneBeneficiario=" + denominazioneBeneficiario
				+ ", descCategAnagrafica=" + descCategAnagrafica
				+ ", descPeriodoVisualizzata=" + descPeriodoVisualizzata
				+ ", dtComunicazione=" + dtComunicazione + ", esitoControllo="
				+ esitoControllo + ", idCategAnagrafica=" + idCategAnagrafica
				+ ", idEsitoControllo=" + idEsitoControllo + ", idPeriodo="
				+ idPeriodo + ", idProgetto=" + idProgetto
				+ ", idSoggettoBeneficiario=" + idSoggettoBeneficiario
				+ ", note=" + note + ", tipoControlli=" + tipoControlli + "]";
	}

}
