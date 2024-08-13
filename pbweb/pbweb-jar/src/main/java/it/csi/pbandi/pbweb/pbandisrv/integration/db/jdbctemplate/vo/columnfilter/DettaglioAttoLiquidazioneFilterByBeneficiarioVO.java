/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.columnfilter;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class DettaglioAttoLiquidazioneFilterByBeneficiarioVO extends GenericVO{
	private BigDecimal idSoggetto;
	private String denominazioneBeneficiarioBil;

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getDenominazioneBeneficiarioBil() {
		return denominazioneBeneficiarioBil;
	}
	public void setDenominazioneBeneficiarioBil(String denominazioneBeneficiarioBil) {
		this.denominazioneBeneficiarioBil = denominazioneBeneficiarioBil;
	}
}
