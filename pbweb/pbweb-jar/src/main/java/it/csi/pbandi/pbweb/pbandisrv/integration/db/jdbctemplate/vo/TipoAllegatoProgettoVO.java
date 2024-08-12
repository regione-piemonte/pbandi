package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class TipoAllegatoProgettoVO extends TipoAllegatoVO {
	
	//ID_PROGETTO	
	private BigDecimal idProgetto;

	//FLAG_ALLEGATO VARCHAR2(1) Valori possibili 'S','N'
	private String flagAllegato;
	
	public String getFlagAllegato() {
		return flagAllegato;
	}
	public void setFlagAllegato(String flagAllegato) {
		this.flagAllegato = flagAllegato;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
}