/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

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
