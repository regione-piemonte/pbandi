/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.profilazione;

public class ConsensoInformatoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.String flagConsensoMail = null;
	private java.lang.String emailConsenso = null;
	
	public java.lang.String getFlagConsensoMail() {
		return flagConsensoMail;
	}
	public void setFlagConsensoMail(java.lang.String flagConsensoMail) {
		this.flagConsensoMail = flagConsensoMail;
	}
	public java.lang.String getEmailConsenso() {
		return emailConsenso;
	}
	public void setEmailConsenso(java.lang.String emailConsenso) {
		this.emailConsenso = emailConsenso;
	}
		
}
