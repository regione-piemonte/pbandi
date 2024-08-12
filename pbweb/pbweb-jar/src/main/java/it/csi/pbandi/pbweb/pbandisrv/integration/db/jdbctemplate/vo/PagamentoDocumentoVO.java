package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;


import java.math.BigDecimal;

public class PagamentoDocumentoVO extends GenericVO {
	private BigDecimal idProgetto;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal importoPagamento;
	private BigDecimal idPagamento;
	//private BigDecimal idStatoValidazioneSpesa;
	//private String descStatoValidazioneSpesa;
	//private String descBreveStatoValidazSpesa;
	
	/**
	 * @return the idProgetto
	 */
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	/**
	 * @param idProgetto the idProgetto to set
	 */
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	/**
	 * @return the idDocumentoDiSpesa
	 */
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	/**
	 * @param idDocumentoDiSpesa the idDocumentoDiSpesa to set
	 */
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	/**
	 * @return the importoPagamento
	 */
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	/**
	 * @param importoPagamento the importoPagamento to set
	 */
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	/*
	public BigDecimal getIdStatoValidazioneSpesa() {
		return idStatoValidazioneSpesa;
	}
	public void setIdStatoValidazioneSpesa(BigDecimal idStatoValidazioneSpesa) {
		this.idStatoValidazioneSpesa = idStatoValidazioneSpesa;
	}
	public String getDescStatoValidazioneSpesa() {
		return descStatoValidazioneSpesa;
	}
	public void setDescStatoValidazioneSpesa(String descStatoValidazioneSpesa) {
		this.descStatoValidazioneSpesa = descStatoValidazioneSpesa;
	}
	public String getDescBreveStatoValidazSpesa() {
		return descBreveStatoValidazSpesa;
	}
	public void setDescBreveStatoValidazSpesa(String descBreveStatoValidazSpesa) {
		this.descBreveStatoValidazSpesa = descBreveStatoValidazSpesa;
	}
	*/

}
