package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class TipoAllegatoDichiarazioneVO extends TipoAllegatoVO {
	
	//ID_DICHIARAZIONE_SPESA
	private BigDecimal idDichiarazioneSpesa;
	
	//FLAG_ALLEGATO VARCHAR2(1) Valori possibili 'S','N'
	private String flagAllegato;
	
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	
	public String getFlagAllegato() {
		return flagAllegato;
	}
	public void setFlagAllegato(String flagAllegato) {
		this.flagAllegato = flagAllegato;
	}
	
}