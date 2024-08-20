/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;


import java.util.List;

public class InitDialogEscussioneVO {
	
	private List<String> tipiEscussione;
	private List<String> statiEscussione;
	
	
	public InitDialogEscussioneVO(List<String> tipiEscussione, List<String> statiEscussione) {
		this.tipiEscussione = tipiEscussione;
		this.statiEscussione = statiEscussione;
	}


	public InitDialogEscussioneVO() {
	}


	public List<String> getTipiEscussione() {
		return tipiEscussione;
	}


	public void setTipiEscussione(List<String> tipiEscussione) {
		this.tipiEscussione = tipiEscussione;
	}


	public List<String> getStatiEscussione() {
		return statiEscussione;
	}


	public void setStatiEscussione(List<String> statiEscussione) {
		this.statiEscussione = statiEscussione;
	}
	
	
	
}	