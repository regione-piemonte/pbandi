/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class RevocaIrregolaritaVO extends GenericVO {

	private BigDecimal idIrregolarita;
	private BigDecimal idProgetto;
	private BigDecimal idRevoca;
	private String tipoIrregolarita;
	private String notePraticaUsata;
	private BigDecimal quotaIrreg;
	private BigDecimal importoIrregolarita;
	private Date dtFineValidita;
	private String descPeriodoVisualizzata;
	private String motivoRevocaIrregolarita;
	private BigDecimal importoAgevolazioneIrreg;

	public String getMotivoRevocaIrregolarita() {
		return motivoRevocaIrregolarita;
	}

	public void setMotivoRevocaIrregolarita(String motivoRevocaIrregolarita) {
		this.motivoRevocaIrregolarita = motivoRevocaIrregolarita;
	}

	public BigDecimal getImportoIrregolarita() {
		return importoIrregolarita;
	}

	public void setImportoIrregolarita(BigDecimal importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}

	public BigDecimal getIdIrregolarita() {
		return idIrregolarita;
	}

	public BigDecimal getIdRevoca() {
		return idRevoca;
	}

	public String getNotePraticaUsata() {
		return notePraticaUsata;
	}

	public BigDecimal getQuotaIrreg() {
		return quotaIrreg;
	}

	public void setIdIrregolarita(BigDecimal idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}

	public void setIdRevoca(BigDecimal idRevoca) {
		this.idRevoca = idRevoca;
	}

	public void setNotePraticaUsata(String notePraticaUsata) {
		this.notePraticaUsata = notePraticaUsata;
	}

	public void setQuotaIrreg(BigDecimal quotaIrreg) {
		this.quotaIrreg = quotaIrreg;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getTipoIrregolarita() {
		return tipoIrregolarita;
	}

	public void setTipoIrregolarita(String tipoIrregolarita) {
		this.tipoIrregolarita = tipoIrregolarita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}

	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}

	public BigDecimal getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}

	public void setImportoAgevolazioneIrreg(BigDecimal importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}

}
