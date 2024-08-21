/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.rilevazionicontrolli;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class AsseRilevazioniVO extends GenericVO{
	
	private BigDecimal idAsse;
	private String asse;
	private BigDecimal idNormativa;
	public BigDecimal getIdAsse() {
		return idAsse;
	}
	public void setIdAsse(BigDecimal idAsse) {
		this.idAsse = idAsse;
	}
	public String getAsse() {
		return asse;
	}
	public void setAsse(String asse) {
		this.asse = asse;
	}
	public BigDecimal getIdNormativa() {
		return idNormativa;
	}
	public void setIdNormativa(BigDecimal idNormativa) {
		this.idNormativa = idNormativa;
	}

}
