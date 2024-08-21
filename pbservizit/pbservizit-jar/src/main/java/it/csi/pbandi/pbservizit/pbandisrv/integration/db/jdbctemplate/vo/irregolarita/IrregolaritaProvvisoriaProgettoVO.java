/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita;


import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class IrregolaritaProvvisoriaProgettoVO extends GenericVO {

	private BigDecimal idIrregolaritaProvv;
	private Date dtComunicazione;
	private Date dtFineProvvisoria;
	private BigDecimal idProgetto;
	private BigDecimal idMotivoRevoca;
	private BigDecimal importoIrregolarita;
	private BigDecimal importoAgevolazioneIrreg;
	private BigDecimal importoIrregolareCertificato;
	private Date dtFineValidita;
	private String codiceVisualizzato;
	private String descMotivoRevoca;
	private BigDecimal idSoggettoBeneficiario;
	private String denominazioneBeneficiario;
	private String tipoControlli;
	private Date dataInizioControlli;
	private Date dataFineControlli;
	private String irregolaritaAnnullata;
	
	private BigDecimal idPeriodo;
	private String descPeriodoVisualizzata;
	private BigDecimal idCategAnagrafica;
	private String descCategAnagrafica;
	private String note;	
	
	private Date dataCampione;
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}

	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
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
	
	public BigDecimal getIdIrregolaritaProvv() {
		return idIrregolaritaProvv;
	}

	public void setIdIrregolaritaProvv(BigDecimal idIrregolaritaProvv) {
		this.idIrregolaritaProvv = idIrregolaritaProvv;
	}

	public Date getDtComunicazione() {
		return dtComunicazione;
	}

	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}

	public Date getDtFineProvvisoria() {
		return dtFineProvvisoria;
	}

	public void setDtFineProvvisoria(Date dtFineProvvisoria) {
		this.dtFineProvvisoria = dtFineProvvisoria;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdMotivoRevoca() {
		return idMotivoRevoca;
	}

	public void setIdMotivoRevoca(BigDecimal idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}

	public BigDecimal getImportoIrregolarita() {
		return importoIrregolarita;
	}

	public void setImportoIrregolarita(BigDecimal importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}

	public BigDecimal getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}

	public void setImportoAgevolazioneIrreg(BigDecimal importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}

	public BigDecimal getImportoIrregolareCertificato() {
		return importoIrregolareCertificato;
	}

	public void setImportoIrregolareCertificato(
			BigDecimal importoIrregolareCertificato) {
		this.importoIrregolareCertificato = importoIrregolareCertificato;
	}

	public String getTipoControlli() {
		return tipoControlli;
	}

	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
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

	public String getIrregolaritaAnnullata() {
		return irregolaritaAnnullata;
	}

	public void setIrregolaritaAnnullata(String irregolaritaAnnullata) {
		this.irregolaritaAnnullata = irregolaritaAnnullata;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Date getDataCampione() {
		return dataCampione;
	}

	public void setDataCampione(Date dataCampione) {
		this.dataCampione = dataCampione;
	}

}
