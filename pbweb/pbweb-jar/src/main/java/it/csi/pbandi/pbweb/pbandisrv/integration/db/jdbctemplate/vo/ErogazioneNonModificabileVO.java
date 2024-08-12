package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ErogazioneNonModificabileVO extends GenericVO{
	
  	/**
	 * 
	 */
	private static final long serialVersionUID = -1571477062157119817L;
	private BigDecimal idFideiussione;
	
  	public void setIdFideiussione(BigDecimal idFideiussione) {
		this.idFideiussione = idFideiussione;
	}
	public BigDecimal getIdFideiussione() {
		return idFideiussione;
	}
}
