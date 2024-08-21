/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class RipartizioneSpesaValidataParFASVO extends GenericVO {
	private BigDecimal idPropostaCertificaz;
	private BigDecimal spesaValidata;
	private String reportKey;
	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public BigDecimal getSpesaValidata() {
		return spesaValidata;
	}

	public void setSpesaValidata(BigDecimal spesaValidata) {
		this.spesaValidata = spesaValidata;
	}

	public String getReportKey() {
		return reportKey;
	}

	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}

}
