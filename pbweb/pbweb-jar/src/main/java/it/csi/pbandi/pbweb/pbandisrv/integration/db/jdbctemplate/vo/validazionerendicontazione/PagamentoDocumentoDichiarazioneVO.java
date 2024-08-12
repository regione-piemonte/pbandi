package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class PagamentoDocumentoDichiarazioneVO extends GenericVO {

	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal idPagamento;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idProgetto;
	private BigDecimal importoPagamento;
	
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
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
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	
	
}
