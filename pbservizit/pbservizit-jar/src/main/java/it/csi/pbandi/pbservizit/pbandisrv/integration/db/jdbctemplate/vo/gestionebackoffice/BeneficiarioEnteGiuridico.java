/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

import java.math.BigDecimal;
import java.sql.Date;

public class BeneficiarioEnteGiuridico extends GenericVO {
	
	private String codiceFiscaleSoggetto;
	private String denominazioneBeneficiario;
	private Date dtInizioValidita;
	private BigDecimal idEnteGiuridico;
	private BigDecimal idSoggetto;
	
	
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}


}
