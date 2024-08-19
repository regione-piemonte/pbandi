/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.trasferimenti;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroTrasferimento implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Long idTrasferimento;
	private String codiceTrasferimento;
	private String daDataTrasferimento;
	private String aDataTrasferimento;
	private Long idCausaleTrasferimento;
	private Long idSoggettoBeneficiario;
	private String flagPubblicoPrivato;
	private Long idLineaDiIntervento;

	

	public Long getIdTrasferimento() {
		return idTrasferimento;
	}

	public void setIdTrasferimento(Long idTrasferimento) {
		this.idTrasferimento = idTrasferimento;
	}

	public String getCodiceTrasferimento() {
		return codiceTrasferimento;
	}

	public void setCodiceTrasferimento(String codiceTrasferimento) {
		this.codiceTrasferimento = codiceTrasferimento;
	}


	public String getDaDataTrasferimento() {
		return daDataTrasferimento;
	}

	public void setDaDataTrasferimento(String daDataTrasferimento) {
		this.daDataTrasferimento = daDataTrasferimento;
	}

	public String getADataTrasferimento() {
		return aDataTrasferimento;
	}

	public void setADataTrasferimento(String aDataTrasferimento) {
		this.aDataTrasferimento = aDataTrasferimento;
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

	public String getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	public void setFlagPubblicoPrivato(String flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}

	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(Long idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

}
