/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class UtentePersonaFisicaVO extends GenericVO {

	private BigDecimal idUtente;
	private String cognomeNomeCodice;
	private Date dtInizioValidita;
	private Date dtFineValidita;
	
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	public String getCognomeNomeCodice() {
		return cognomeNomeCodice;
	}
	public void setCognomeNomeCodice(String cognomeNomeCodice) {
		this.cognomeNomeCodice = cognomeNomeCodice;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

}
