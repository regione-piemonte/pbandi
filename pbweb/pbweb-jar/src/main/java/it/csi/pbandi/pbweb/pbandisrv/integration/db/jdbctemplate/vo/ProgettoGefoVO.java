package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ProgettoGefoVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private String flagGEFO;
	
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getFlagGEFO() {
		return flagGEFO;
	}
	public void setFlagGEFO(String flagGEFO) {
		this.flagGEFO = flagGEFO;
	}
	
	
	

}
