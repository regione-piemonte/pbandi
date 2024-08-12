package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.validazionerendicontazione;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class PagamentoDichiarazioneVO extends GenericVO {

	private BigDecimal idDichiarazioneSpesa;
	private BigDecimal idPagamento;
	
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
	
	

}
