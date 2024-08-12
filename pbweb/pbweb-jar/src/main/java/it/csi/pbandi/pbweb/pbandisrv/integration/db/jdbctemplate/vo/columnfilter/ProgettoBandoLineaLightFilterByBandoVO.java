package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.columnfilter;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoBandoLineaLightFilterByBandoVO extends GenericVO {
	private BigDecimal idBandoLinea;
	private String descrizioneBando;

	public BigDecimal getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(BigDecimal idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public String getDescrizioneBando() {
		return descrizioneBando;
	}

	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}

}
