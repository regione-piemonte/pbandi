/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ImportiTotaliPerDichiarazioneDiSpesaVO extends GenericVO {

	private BigDecimal totaleImportoValidato;
	private BigDecimal totaleImportoRendicontato;
	private BigDecimal idDichiarazioneSpesa;

	public BigDecimal getTotaleImportoRendicontato() {
		return totaleImportoRendicontato;
	}
	public void setTotaleImportoRendicontato(BigDecimal totaleImportoRendicontato) {
		this.totaleImportoRendicontato = totaleImportoRendicontato;
	}

	public BigDecimal getTotaleImportoValidato() {
		return totaleImportoValidato;
	}
	public void setTotaleImportoValidato(BigDecimal totaleImportoValidato) {
		this.totaleImportoValidato = totaleImportoValidato;
	}
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
}
