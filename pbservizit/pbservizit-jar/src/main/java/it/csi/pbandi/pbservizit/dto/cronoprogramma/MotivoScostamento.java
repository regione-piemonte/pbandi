/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.cronoprogramma;

public class MotivoScostamento implements java.io.Serializable {

	private Long idMotivoScostamento;
	private String descMotivoScostamento;
	private String codIgrue;
	
	public void setIdMotivoScostamento(Long val) {
		idMotivoScostamento = val;
	}

	public Long getIdMotivoScostamento() {
		return idMotivoScostamento;
	}

	

	public void setDescMotivoScostamento(String val) {
		descMotivoScostamento = val;
	}

	public String getDescMotivoScostamento() {
		return descMotivoScostamento;
	}



	public void setCodIgrue(String val) {
		codIgrue = val;
	}

	public String getCodIgrue() {
		return codIgrue;
	}

	private static final long serialVersionUID = 1L;

	public MotivoScostamento() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R2142132588) ENABLED START*/

		return super.toString();
	}
}
