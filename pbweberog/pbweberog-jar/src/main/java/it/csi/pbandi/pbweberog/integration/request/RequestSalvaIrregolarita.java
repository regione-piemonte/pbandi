/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.registrocontrolli.Irregolarita;

public class RequestSalvaIrregolarita implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Irregolarita irregolarita;
	private Long idProgetto;
	private Long idU;
	private Long idBeneficiario;
	private String codiceFiscale;
	
	public Irregolarita getIrregolarita() {
		return irregolarita;
	}
	public void setIrregolarita(Irregolarita irregolarita) {
		this.irregolarita = irregolarita;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdU() {
		return idU;
	}
	public void setIdU(Long idU) {
		this.idU = idU;
	}
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	
	
}
