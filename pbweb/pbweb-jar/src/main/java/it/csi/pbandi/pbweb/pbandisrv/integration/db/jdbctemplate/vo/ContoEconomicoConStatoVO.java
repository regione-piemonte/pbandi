/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTContoEconomicoVO;

public class ContoEconomicoConStatoVO extends PbandiTContoEconomicoVO {
	private String descBreveStatoContoEconom;
	private String descBreveTipologiaContoEco;
	private BigDecimal idProgetto;

	public void setDescBreveStatoContoEconom(String descBreveStatoContoEconom) {
		this.descBreveStatoContoEconom = descBreveStatoContoEconom;
	}

	public String getDescBreveStatoContoEconom() {
		return descBreveStatoContoEconom;
	}

	public void setDescBreveTipologiaContoEco(String descBreveTipologiaContoEco) {
		this.descBreveTipologiaContoEco = descBreveTipologiaContoEco;
	}

	public String getDescBreveTipologiaContoEco() {
		return descBreveTipologiaContoEco;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
}
