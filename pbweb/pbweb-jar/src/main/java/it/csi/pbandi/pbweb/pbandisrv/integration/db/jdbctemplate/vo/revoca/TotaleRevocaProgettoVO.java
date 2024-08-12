package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.revoca;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class TotaleRevocaProgettoVO  extends GenericVO {
	private BigDecimal idProgetto;
	private BigDecimal totaleImportoRevocato;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getTotaleImportoRevocato() {
		return totaleImportoRevocato;
	}
	public void setTotaleImportoRevocato(BigDecimal totaleImportoRevocato) {
		this.totaleImportoRevocato = totaleImportoRevocato;
	}

}
