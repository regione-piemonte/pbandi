/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

import java.io.Serializable;

import it.csi.pbandi.pbweberog.dto.ExecResults;

public class EsitoControllaDati implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean esito;
	private Erogazione erogazione;
	private String message;
	
	
	public boolean isEsito() {
		return esito;
	}
	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	public Erogazione getErogazione() {
		return erogazione;
	}
	public void setErogazione(Erogazione erogazione) {
		this.erogazione = erogazione;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	
	
	
}
