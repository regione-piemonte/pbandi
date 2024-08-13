/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;

public class InizializzaGestioneChecklistDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private String codiceVisualizzatoProgetto;
	private Boolean modificaChecklistAmmessa;;
	private Boolean eliminazioneChecklistAmmessa;
	
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}
	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}
	public Boolean getModificaChecklistAmmessa() {
		return modificaChecklistAmmessa;
	}
	public void setModificaChecklistAmmessa(Boolean modificaChecklistAmmessa) {
		this.modificaChecklistAmmessa = modificaChecklistAmmessa;
	}
	public Boolean getEliminazioneChecklistAmmessa() {
		return eliminazioneChecklistAmmessa;
	}
	public void setEliminazioneChecklistAmmessa(Boolean eliminazioneChecklistAmmessa) {
		this.eliminazioneChecklistAmmessa = eliminazioneChecklistAmmessa;
	}

}
