/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class DettaglioSoggettoVO extends GenericVO {
	private BigDecimal idSoggetto;
	private BigDecimal idPersonaFisica;
	private BigDecimal idEnteGiuridico;
	private String denominazione;
	private String codiceFiscaleSoggetto;

	// e possibile ottenere molte piu informazioni dalla SELECT principale
	// (indipendentemente se PF o EG)

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}

	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}

	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}

	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
}
