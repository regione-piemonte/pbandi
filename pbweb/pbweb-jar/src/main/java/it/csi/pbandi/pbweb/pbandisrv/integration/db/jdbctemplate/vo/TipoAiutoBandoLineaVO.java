package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAiutoVO;

import java.math.BigDecimal;

public class TipoAiutoBandoLineaVO extends PbandiDTipoAiutoVO {
	private BigDecimal progrBandoLineaIntervento;

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
}
