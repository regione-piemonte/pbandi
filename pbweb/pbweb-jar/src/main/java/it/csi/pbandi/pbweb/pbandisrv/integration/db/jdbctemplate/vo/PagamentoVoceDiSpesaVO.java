package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class PagamentoVoceDiSpesaVO extends GenericVO {
	
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idProgetto;
	private BigDecimal idRigoContoEconomico;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idQuotaParteDocSpesa;
	private BigDecimal idPagamento;
	private BigDecimal importoQuietanzato;
	private BigDecimal importoQuotaParteDocSpesa;
	
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
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}
	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}
	public BigDecimal getImportoQuotaParteDocSpesa() {
		return importoQuotaParteDocSpesa;
	}
	public void setImportoQuotaParteDocSpesa(BigDecimal importoQuotaParteDocSpesa) {
		this.importoQuotaParteDocSpesa = importoQuotaParteDocSpesa;
	}
	
	

}
