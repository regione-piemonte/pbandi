/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import java.util.ArrayList;

import it.csi.pbandi.pbwebrce.dto.FinanziamentoFonteFinanziaria;
import it.csi.pbandi.pbwebrce.dto.ModalitaAgevolazione;


public class ValidaRimodulazioneConfermataRequest {
	
	ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione;
	ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate;
	Double totaleSpesaAmmessaRimodulazione;
	Long idBando;
	Boolean rimodulazioneInIstruttoria;
	
	public Boolean getRimodulazioneInIstruttoria() {
		return rimodulazioneInIstruttoria;
	}
	public void setRimodulazioneInIstruttoria(Boolean rimodulazioneInIstruttoria) {
		this.rimodulazioneInIstruttoria = rimodulazioneInIstruttoria;
	}
	public ArrayList<ModalitaAgevolazione> getListaModalitaAgevolazione() {
		return listaModalitaAgevolazione;
	}
	public void setListaModalitaAgevolazione(ArrayList<ModalitaAgevolazione> listaModalitaAgevolazione) {
		this.listaModalitaAgevolazione = listaModalitaAgevolazione;
	}
	public ArrayList<FinanziamentoFonteFinanziaria> getFontiFiltrate() {
		return fontiFiltrate;
	}
	public void setFontiFiltrate(ArrayList<FinanziamentoFonteFinanziaria> fontiFiltrate) {
		this.fontiFiltrate = fontiFiltrate;
	}
	public Double getTotaleSpesaAmmessaRimodulazione() {
		return totaleSpesaAmmessaRimodulazione;
	}
	public void setTotaleSpesaAmmessaRimodulazione(Double totaleSpesaAmmessaRimodulazione) {
		this.totaleSpesaAmmessaRimodulazione = totaleSpesaAmmessaRimodulazione;
	}
	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}
	
}
