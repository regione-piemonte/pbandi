/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class VociDiSpesaPagamentoVO extends GenericVO{
	private String descVoceDiSpesa;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idProgetto;
	private BigDecimal idQuotaParteDocSpesa;
	private BigDecimal idPagamento;
	private BigDecimal idRigoContoEconomico;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal importoValidato;
	private BigDecimal quietanzato;
	private BigDecimal residuoQuietanzabile;
	private BigDecimal rendicontato;
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getQuietanzato() {
		return quietanzato;
	}
	public void setQuietanzato(BigDecimal quietanzato) {
		this.quietanzato = quietanzato;
	}
	public BigDecimal getImportoValidato() {
		return importoValidato;
	}
	public void setImportoValidato(BigDecimal importoValidato) {
		this.importoValidato = importoValidato;
	}
	public BigDecimal getResiduoQuietanzabile() {
		return residuoQuietanzabile;
	}
	public void setResiduoQuietanzabile(BigDecimal residuoQuietanzabile) {
		this.residuoQuietanzabile = residuoQuietanzabile;
	}
	public BigDecimal getRendicontato() {
		return rendicontato;
	}
	public void setRendicontato(BigDecimal rendicontato) {
		this.rendicontato = rendicontato;
	}
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
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
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	
	
}
