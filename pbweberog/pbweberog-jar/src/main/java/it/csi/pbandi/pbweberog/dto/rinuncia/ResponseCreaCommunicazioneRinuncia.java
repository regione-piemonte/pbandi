/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.rinuncia;

import it.csi.pbandi.pbweberog.dto.erogazione.DichiarazioneDiRinunciaDTO;

public class ResponseCreaCommunicazioneRinuncia {
	private DichiarazioneDiRinunciaDTO dichiarazioneRinuncia;
	private boolean invioDigitale;
	private String message;
	
	public DichiarazioneDiRinunciaDTO getDichiarazioneRinuncia() {
		return dichiarazioneRinuncia;
	}
	public void setDichiarazioneRinuncia(DichiarazioneDiRinunciaDTO dichiarazioneRinuncia) {
		this.dichiarazioneRinuncia = dichiarazioneRinuncia;
	}
	public boolean isInvioDigitale() {
		return invioDigitale;
	}
	public void setInvioDigitale(boolean invioDigitale) {
		this.invioDigitale = invioDigitale;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
