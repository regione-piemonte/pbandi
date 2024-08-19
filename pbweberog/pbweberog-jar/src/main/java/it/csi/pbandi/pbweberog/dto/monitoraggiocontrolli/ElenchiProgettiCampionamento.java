/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.monitoraggiocontrolli;

import java.util.ArrayList;

public class ElenchiProgettiCampionamento {

	private ArrayList<ProgettoCampione> progettiGiaPresenti;
	private ArrayList<ProgettoCampione> progettiDaAggiungere;
	private String progettiScartati;
	private ArrayList<ProgettoCampione> progettiDelCampione;
	
	public ArrayList<ProgettoCampione> getProgettiGiaPresenti() {
		return progettiGiaPresenti;
	}
	public void setProgettiGiaPresenti(
			ArrayList<ProgettoCampione> progettiGiaPresenti) {
		this.progettiGiaPresenti = progettiGiaPresenti;
	}
	public ArrayList<ProgettoCampione> getProgettiDaAggiungere() {
		return progettiDaAggiungere;
	}
	public void setProgettiDaAggiungere(
			ArrayList<ProgettoCampione> progettiDaAggiungere) {
		this.progettiDaAggiungere = progettiDaAggiungere;
	}
	public String getProgettiScartati() {
		return progettiScartati;
	}
	public void setProgettiScartati(String progettiScartati) {
		this.progettiScartati = progettiScartati;
	}
	public ArrayList<ProgettoCampione> getProgettiDelCampione() {
		return progettiDelCampione;
	}
	public void setProgettiDelCampione(
			ArrayList<ProgettoCampione> progettiDelCampione) {
		this.progettiDelCampione = progettiDelCampione;
	}
	
}
