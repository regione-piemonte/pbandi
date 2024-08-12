package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class PagamentoAssociatoADichiarazioneVO extends GenericVO {
	private BigDecimal idDichiarazioneSpesa;
	//private String descBreveStatoValidazSpesa;
	private BigDecimal idProgetto;
	
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	/*
	public void setDescBreveStatoValidazSpesa(String descBreveStatoValidazSpesa) {
		this.descBreveStatoValidazSpesa = descBreveStatoValidazSpesa;
	}
	public String getDescBreveStatoValidazSpesa() {
		return descBreveStatoValidazSpesa;
	}
	*/
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

}
