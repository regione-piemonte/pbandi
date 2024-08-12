package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class RigoContoEconomicoProgettoCulturaVO extends RigoContoEconomicoProgettoVO {
	
	private BigDecimal idVoceDiSpesa;
    private BigDecimal idVoceDiEntrata;
    
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdVoceDiEntrata() {
		return idVoceDiEntrata;
	}
	public void setIdVoceDiEntrata(BigDecimal idVoceDiEntrata) {
		this.idVoceDiEntrata = idVoceDiEntrata;
	}
    
}
