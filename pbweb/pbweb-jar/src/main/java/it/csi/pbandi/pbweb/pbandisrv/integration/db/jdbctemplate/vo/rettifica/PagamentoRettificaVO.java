/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class PagamentoRettificaVO extends GenericVO {
	
	private String descModalitaPagamento;
	private String descBreveModalitaPagamento;
	private Date dtPagamento;
	private Date dtValuta;
	private BigDecimal importoValidato;
	private BigDecimal importoQuietanzato;
	//private String descStatoValidazioneSpesa;
	//private String descBreveStatoValidazSpesa;
	private BigDecimal importoPagamento;
	private String descVoceDiSpesa;
	private BigDecimal idPagamento;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idQuotaParteDocSpesa;
	private BigDecimal progrPagQuotParteDocSp;
	private BigDecimal importoTotaleRettifica;
	private BigDecimal importoValidatoLordo;
	
	public String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}
	public void setDescModalitaPagamento(String descModalitaPagamento) {
		this.descModalitaPagamento = descModalitaPagamento;
	}
	public String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}
	public void setDescBreveModalitaPagamento(String descBreveModalitaPagamento) {
		this.descBreveModalitaPagamento = descBreveModalitaPagamento;
	}
	public Date getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public Date getDtValuta() {
		return dtValuta;
	}
	public void setDtValuta(Date dtValuta) {
		this.dtValuta = dtValuta;
	}
	public BigDecimal getImportoValidato() {
		return importoValidato;
	}
	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}
	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}
	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	/*
	public String getDescStatoValidazioneSpesa() {
		return descStatoValidazioneSpesa;
	}
	public void setDescStatoValidazioneSpesa(String descStatoValidazioneSpesa) {
		this.descStatoValidazioneSpesa = descStatoValidazioneSpesa;
	}
	*/
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	public void setProgrPagQuotParteDocSp(BigDecimal progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}
	public BigDecimal getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}
	
	public void setImportoValidatoLordo(BigDecimal importoValidatoLordo) {
		this.importoValidatoLordo = importoValidatoLordo;
	}
	public BigDecimal getImportoValidatoLordo() {
		return importoValidatoLordo;
	}
	public void setImportoTotaleRettifica(BigDecimal importoTotaleRettifica) {
		this.importoTotaleRettifica = importoTotaleRettifica;
	}
	public BigDecimal getImportoTotaleRettifica() {
		return importoTotaleRettifica;
	}
	/*
	public void setDescBreveStatoValidazSpesa(String descBreveStatoValidazSpesa) {
		this.descBreveStatoValidazSpesa = descBreveStatoValidazSpesa;
	}
	public String getDescBreveStatoValidazSpesa() {
		return descBreveStatoValidazSpesa;
	}
	*/
	
	

}
