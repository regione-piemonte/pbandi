/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.indicatori;

public class EsitoSaveIndicatori implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private boolean successo = true;
	private String[] messaggi = null;
	
	public void setSuccesso(boolean val) {
		successo = val;
	}

	public boolean getSuccesso() {
		return successo;
	}

	

	public void setMessaggi(String[] val) {
		messaggi = val;
	}

	public String[] getMessaggi() {
		return messaggi;
	}
}
