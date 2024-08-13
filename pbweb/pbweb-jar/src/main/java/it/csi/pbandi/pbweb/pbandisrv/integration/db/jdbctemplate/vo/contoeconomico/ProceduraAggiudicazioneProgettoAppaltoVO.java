/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.contoeconomico;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProceduraAggiudicazioneProgettoAppaltoVO extends GenericVO {
	
	private BigDecimal idProceduraAggiudicaz;
	private String codProcAgg;
	private String descProcAgg;
	private BigDecimal importo;
	private BigDecimal idTipologiaAggiudicaz;
	private String codIgrueT47;
	private String descTipologiaAggiudicazione;
	private BigDecimal idProgetto;
	private BigDecimal iva;
	private String cigProcAgg;
	private String codice;
	private Date dtAggiudicazione;
	private BigDecimal idMotivoAssenzaCig;

	public BigDecimal getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	public void setIdProceduraAggiudicaz(BigDecimal idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
	}
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getDescProcAgg() {
		return descProcAgg;
	}
	public void setDescProcAgg(String descProcAgg) {
		this.descProcAgg = descProcAgg;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public BigDecimal getIdTipologiaAggiudicaz() {
		return idTipologiaAggiudicaz;
	}
	public void setIdTipologiaAggiudicaz(BigDecimal idTipologiaAggiudicaz) {
		this.idTipologiaAggiudicaz = idTipologiaAggiudicaz;
	}
	public String getCodIgrueT47() {
		return codIgrueT47;
	}
	public void setCodIgrueT47(String codIgrueT47) {
		this.codIgrueT47 = codIgrueT47;
	}
	public String getDescTipologiaAggiudicazione() {
		return descTipologiaAggiudicazione;
	}
	public void setDescTipologiaAggiudicazione(String descTipologiaAggiudicazione) {
		this.descTipologiaAggiudicazione = descTipologiaAggiudicazione;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public String getCigProcAgg() {
		return cigProcAgg;
	}
	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public Date getDtAggiudicazione() {
		return dtAggiudicazione;
	}
	public void setDtAggiudicazione(Date dtAggiudicazione) {
		this.dtAggiudicazione = dtAggiudicazione;
	}
	public BigDecimal getIdMotivoAssenzaCig() {
		return idMotivoAssenzaCig;
	}
	public void setIdMotivoAssenzaCig(BigDecimal idMotivoAssenzaCig) {
		this.idMotivoAssenzaCig = idMotivoAssenzaCig;
	}
}
