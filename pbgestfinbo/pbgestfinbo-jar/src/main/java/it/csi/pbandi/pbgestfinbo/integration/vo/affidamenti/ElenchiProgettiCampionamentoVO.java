/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

import java.util.ArrayList;


public class ElenchiProgettiCampionamentoVO {
	
	private ArrayList<ProgettoCampioneVO> progettiGiaPresenti;
	private ArrayList<ProgettoCampioneVO> progettiDaAggiungere;
	private String progettiScartati;
	private ArrayList<ProgettoCampioneVO> progettiDelCampione;
	
	public ArrayList<ProgettoCampioneVO> getProgettiGiaPresenti() {
		return progettiGiaPresenti;
	}
	public void setProgettiGiaPresenti(ArrayList<ProgettoCampioneVO> progettiGiaPresenti) {
		this.progettiGiaPresenti = progettiGiaPresenti;
	}
	public ArrayList<ProgettoCampioneVO> getProgettiDaAggiungere() {
		return progettiDaAggiungere;
	}
	public void setProgettiDaAggiungere(ArrayList<ProgettoCampioneVO> progettiDaAggiungere) {
		this.progettiDaAggiungere = progettiDaAggiungere;
	}
	public String getProgettiScartati() {
		return progettiScartati;
	}
	public void setProgettiScartati(String progettiScartati) {
		this.progettiScartati = progettiScartati;
	}
	public ArrayList<ProgettoCampioneVO> getProgettiDelCampione() {
		return progettiDelCampione;
	}
	public void setProgettiDelCampione(ArrayList<ProgettoCampioneVO> progettiDelCampione) {
		this.progettiDelCampione = progettiDelCampione;
	}
	
	
	

}
