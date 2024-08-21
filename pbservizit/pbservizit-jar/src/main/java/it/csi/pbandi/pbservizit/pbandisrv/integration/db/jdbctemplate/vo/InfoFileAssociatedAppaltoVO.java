/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.Date;


public class InfoFileAssociatedAppaltoVO extends InfoFileVO {
	
	private BigDecimal idAppalto;
	private String codProcAgg;
	private String oggettoAppalto;
	
	public BigDecimal getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(BigDecimal idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	public String getCodProcAgg() {
		return codProcAgg;
	}
	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}
	public String getOggettoAppalto() {
		return oggettoAppalto;
	}
	public void setOggettoAppalto(String oggettoAppalto) {
		this.oggettoAppalto = oggettoAppalto;
	}

}
