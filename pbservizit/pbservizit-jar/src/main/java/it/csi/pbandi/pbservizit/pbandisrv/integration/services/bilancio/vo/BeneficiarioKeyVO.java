/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

public class BeneficiarioKeyVO {

	private Integer codBen; // NUMBER(6)
	private Integer progBen; // NUMBER(3)

	public Integer getCodBen() {
		return codBen;
	}

	public void setCodBen(Integer codBen) {
		this.codBen = codBen;
	}

	public Integer getProgBen() {
		return progBen;
	}

	public void setProgBen(Integer progBen) {
		this.progBen = progBen;
	}
}