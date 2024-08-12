package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class VociDiSpesaPadreFigliePerBandoVO extends GenericVO {
	
	private BigDecimal idBando;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idVoceDiSpesaPadre;
	private String descVoceDiSpesa;
	
	public BigDecimal getIdBando() {
		return idBando;
	}
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	
	

}
