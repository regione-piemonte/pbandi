/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;


public class EmailBeneficiarioPF implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private String email;
	private String flagEmailConfermata;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFlagEmailConfermata() {
		return flagEmailConfermata;
	}
	public void setFlagEmailConfermata(String flagEmailConfermata) {
		this.flagEmailConfermata = flagEmailConfermata;
	}
	
}
