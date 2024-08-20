/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.quadroprevisionale;

import java.util.ArrayList;

public class ResponseGetQuadroPrevisionale {
	private ArrayList<QuadroPrevisionaleItem> vociWeb;
	private QuadroPrevisionale quadroPrevisionale;
	private ArrayList<QuadroPrevisionaleComplessivoItem> quadroComplessivoList;
	private String note;
	private Long idProgetto;
	
	public ArrayList<QuadroPrevisionaleItem> getVociWeb() {
		return vociWeb;
	}
	public void setVociWeb(ArrayList<QuadroPrevisionaleItem> vociWeb) {
		this.vociWeb = vociWeb;
	}
	public QuadroPrevisionale getQuadroPrevisionale() {
		return quadroPrevisionale;
	}
	public void setQuadroPrevisionale(QuadroPrevisionale quadroPrevisionale) {
		this.quadroPrevisionale = quadroPrevisionale;
	}
	public ArrayList<QuadroPrevisionaleComplessivoItem> getQuadroComplessivoList() {
		return quadroComplessivoList;
	}
	public void setQuadroComplessivoList(ArrayList<QuadroPrevisionaleComplessivoItem> quadroComplessivoList) {
		this.quadroComplessivoList = quadroComplessivoList;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	
}
