/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class SoggettiFinanziatoriPerBandoVO extends GenericVO {

	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal idSoggettoFinanziatore;
	private BigDecimal percentualeQuotaSoggFinanz;
	private BigDecimal percQuotaContributoPub;
	private String descBreveSoggFinanziatore;
	private String descSoggFinanziatore;
	private String flagCertificazione;
	private BigDecimal idTipoSoggFinanziat;
	private String flagAgevolato;

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public BigDecimal getIdSoggettoFinanziatore() {
		return idSoggettoFinanziatore;
	}

	public void setIdSoggettoFinanziatore(BigDecimal idSoggettoFinanziatore) {
		this.idSoggettoFinanziatore = idSoggettoFinanziatore;
	}

	public BigDecimal getPercentualeQuotaSoggFinanz() {
		return percentualeQuotaSoggFinanz;
	}

	public void setPercentualeQuotaSoggFinanz(BigDecimal percentualeQuotaSoggFinanz) {
		this.percentualeQuotaSoggFinanz = percentualeQuotaSoggFinanz;
	}

	public BigDecimal getPercQuotaContributoPub() {
		return percQuotaContributoPub;
	}

	public void setPercQuotaContributoPub(BigDecimal percQuotaContributoPub) {
		this.percQuotaContributoPub = percQuotaContributoPub;
	}

	public String getDescBreveSoggFinanziatore() {
		return descBreveSoggFinanziatore;
	}

	public void setDescBreveSoggFinanziatore(String descBreveSoggFinanziatore) {
		this.descBreveSoggFinanziatore = descBreveSoggFinanziatore;
	}

	public String getDescSoggFinanziatore() {
		return descSoggFinanziatore;
	}

	public void setDescSoggFinanziatore(String descSoggFinanziatore) {
		this.descSoggFinanziatore = descSoggFinanziatore;
	}

	public String getFlagCertificazione() {
		return flagCertificazione;
	}

	public void setFlagCertificazione(String flagCertificazione) {
		this.flagCertificazione = flagCertificazione;
	}

	public BigDecimal getIdTipoSoggFinanziat() {
		return idTipoSoggFinanziat;
	}

	public void setIdTipoSoggFinanziat(BigDecimal idTipoSoggFinanziat) {
		this.idTipoSoggFinanziat = idTipoSoggFinanziat;
	}

	public String getFlagAgevolato() {
		return flagAgevolato;
	}

	public void setFlagAgevolato(String flagAgevolato) {
		this.flagAgevolato = flagAgevolato;
	}

}
