/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.contoeconomico;

import java.util.ArrayList;

import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;

public class InizializzaVisualizzaContoEconomicoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String codiceVisualizzatoProgetto;
	private Boolean ricercaCapofila;
	private ArrayList<CodiceDescrizioneDTO> partnersCapofila;
	private ArrayList<ContoEconomicoItem> contoEconomico;

	public ArrayList<ContoEconomicoItem> getContoEconomico() {
		return contoEconomico;
	}

	public void setContoEconomico(ArrayList<ContoEconomicoItem> contoEconomico) {
		this.contoEconomico = contoEconomico;
	}

	public ArrayList<CodiceDescrizioneDTO> getPartnersCapofila() {
		return partnersCapofila;
	}

	public void setPartnersCapofila(ArrayList<CodiceDescrizioneDTO> partnersCapofila) {
		this.partnersCapofila = partnersCapofila;
	}

	public Boolean getRicercaCapofila() {
		return ricercaCapofila;
	}

	public void setRicercaCapofila(Boolean ricercaCapofila) {
		this.ricercaCapofila = ricercaCapofila;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

}
