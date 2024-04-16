/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

public class PbandiTEmailSoggettoVO {

	private Long idSoggetto;
	private String emailSoggetto;

	public PbandiTEmailSoggettoVO() {
	}

	public PbandiTEmailSoggettoVO(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public PbandiTEmailSoggettoVO(Long idSoggetto, String emailSoggetto) {
		this.idSoggetto = idSoggetto;
		this.emailSoggetto = emailSoggetto;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getEmailSoggetto() {
		return emailSoggetto;
	}

	public void setEmailSoggetto(String emailSoggetto) {
		this.emailSoggetto = emailSoggetto;
	}
}
