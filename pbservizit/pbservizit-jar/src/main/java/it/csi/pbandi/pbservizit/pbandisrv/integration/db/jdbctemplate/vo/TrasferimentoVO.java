/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class TrasferimentoVO extends GenericVO {
	
	private BigDecimal idTrasferimento;
	private String codiceTrasferimento;
	private Date dtTrasferimento;
	private BigDecimal idCausaleTrasferimento;
	private BigDecimal importoTrasferimento;
	private BigDecimal idSoggettoBeneficiario;
	private String flagPubblicoPrivato;
	private BigDecimal idUtenteIns;
	private BigDecimal idUtenteAgg;
	private String descCausaleTrasferimento;
	private String cfBeneficiario;
	private Long idLineaDiIntervento;
	private String denominazioneBeneficiario;

	public BigDecimal getIdTrasferimento() {
		return idTrasferimento;
	}
	public void setIdTrasferimento(BigDecimal idTrasferimento) {
		this.idTrasferimento = idTrasferimento;
	}
	public String getCodiceTrasferimento() {
		return codiceTrasferimento;
	}
	public void setCodiceTrasferimento(String codiceTrasferimento) {
		this.codiceTrasferimento = codiceTrasferimento;
	}
	public Date getDtTrasferimento() {
		return dtTrasferimento;
	}
	public void setDtTrasferimento(Date dtTrasferimento) {
		this.dtTrasferimento = dtTrasferimento;
	}
	public BigDecimal getIdCausaleTrasferimento() {
		return idCausaleTrasferimento;
	}
	public void setIdCausaleTrasferimento(BigDecimal idCausaleTrasferimento) {
		this.idCausaleTrasferimento = idCausaleTrasferimento;
	}
	public BigDecimal getImportoTrasferimento() {
		return importoTrasferimento;
	}
	public void setImportoTrasferimento(BigDecimal importoTrasferimento) {
		this.importoTrasferimento = importoTrasferimento;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}
	public void setFlagPubblicoPrivato(String flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public String getDescCausaleTrasferimento() {
		return descCausaleTrasferimento;
	}
	public void setDescCausaleTrasferimento(String descCausaleTrasferimento) {
		this.descCausaleTrasferimento = descCausaleTrasferimento;
	}
	public String getCfBeneficiario() {
		return cfBeneficiario;
	}
	public void setCfBeneficiario(String cfBeneficiario) {
		this.cfBeneficiario = cfBeneficiario;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	public void setIdLineaDiIntervento(Long idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
}
