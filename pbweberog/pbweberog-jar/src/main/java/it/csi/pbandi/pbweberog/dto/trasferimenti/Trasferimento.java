/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.trasferimenti;

public class Trasferimento implements java.io.Serializable {

	private Long idTrasferimento;
	private String dtTrasferimento;
	private String codiceTrasferimento;
	private Long idCausaleTrasferimento;
	private Long idSoggettoBeneficiario;
	private Double importoTrasferimento;
	private String flagPubblicoPrivato;
	private String descCausaleTrasferimento;
	private String cfBeneficiario;
	private String denominazioneBeneficiario;
	private String descPubblicoPrivato;
	private Long idLineaDiIntervento;
	private static final long serialVersionUID = 1L;

	
	
	public Long getIdTrasferimento() {
		return idTrasferimento;
	}

	public void setIdTrasferimento(Long idTrasferimento) {
		this.idTrasferimento = idTrasferimento;
	}

	public String getDtTrasferimento() {
		return dtTrasferimento;
	}

	public void setDtTrasferimento(String dtTrasferimento) {
		this.dtTrasferimento = dtTrasferimento;
	}

	public String getCodiceTrasferimento() {
		return codiceTrasferimento;
	}

	public void setCodiceTrasferimento(String codiceTrasferimento) {
		this.codiceTrasferimento = codiceTrasferimento;
	}

	public Long getIdCausaleTrasferimento() {
		return idCausaleTrasferimento;
	}

	public void setIdCausaleTrasferimento(Long idCausaleTrasferimento) {
		this.idCausaleTrasferimento = idCausaleTrasferimento;
	}

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public Double getImportoTrasferimento() {
		return importoTrasferimento;
	}

	public void setImportoTrasferimento(Double importoTrasferimento) {
		this.importoTrasferimento = importoTrasferimento;
	}

	public String getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	public void setFlagPubblicoPrivato(String flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
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

	public String getDescPubblicoPrivato() {
		return descPubblicoPrivato;
	}

	public void setDescPubblicoPrivato(String descPubblicoPrivato) {
		this.descPubblicoPrivato = descPubblicoPrivato;
	}

	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(Long idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public Trasferimento() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
