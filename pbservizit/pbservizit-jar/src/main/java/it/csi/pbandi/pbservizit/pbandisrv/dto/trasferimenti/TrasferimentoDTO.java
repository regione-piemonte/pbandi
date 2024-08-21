/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti;

import java.util.Date;

public class TrasferimentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idTrasferimento;

	public void setIdTrasferimento(Long val) {
		idTrasferimento = val;
	}

	public Long getIdTrasferimento() {
		return idTrasferimento;
	}

	private String codiceTrasferimento;

	public void setCodiceTrasferimento(String val) {
		codiceTrasferimento = val;
	}

	public String getCodiceTrasferimento() {
		return codiceTrasferimento;
	}

	private Date dtTrasferimento;

	public void setDtTrasferimento(Date val) {
		dtTrasferimento = val;
	}

	public Date getDtTrasferimento() {
		return dtTrasferimento;
	}

	private Long idCausaleTrasferimento;

	public void setIdCausaleTrasferimento(Long val) {
		idCausaleTrasferimento = val;
	}

	public Long getIdCausaleTrasferimento() {
		return idCausaleTrasferimento;
	}

	private Long idSoggettoBeneficiario;

	public void setIdSoggettoBeneficiario(Long val) {
		idSoggettoBeneficiario = val;
	}

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	private Double importoTrasferimento;

	public void setImportoTrasferimento(Double val) {
		importoTrasferimento = val;
	}

	public Double getImportoTrasferimento() {
		return importoTrasferimento;
	}

	private String flagPubblicoPrivato;

	public void setFlagPubblicoPrivato(String val) {
		flagPubblicoPrivato = val;
	}

	public String getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	private String descCausaleTrasferimento;

	public void setDescCausaleTrasferimento(String val) {
		descCausaleTrasferimento = val;
	}

	public String getDescCausaleTrasferimento() {
		return descCausaleTrasferimento;
	}

	private String cfBeneficiario;

	public void setCfBeneficiario(String val) {
		cfBeneficiario = val;
	}

	public String getCfBeneficiario() {
		return cfBeneficiario;
	}

	private String denominazioneBeneficiario;

	public void setDenominazioneBeneficiario(String val) {
		denominazioneBeneficiario = val;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	private Long idLineaDiIntervento;

	public void setIdLineaDiIntervento(Long val) {
		idLineaDiIntervento = val;
	}

	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

}
