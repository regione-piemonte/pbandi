/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import java.util.ArrayList;

import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionale;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleComplessivoItem;
import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleItem;



public class ValidazioneDatiQuadroProvisionaleRequest {
	private ArrayList<QuadroPrevisionaleItem> voci;
	private ArrayList<QuadroPrevisionaleComplessivoItem> vociQuadroComplessivo;
	private QuadroPrevisionale quadroPrevisionale;
	private String noteQuadroPrevisionale;
	
	public ArrayList<QuadroPrevisionaleItem> getVoci() {
		return voci;
	}
	public void setVoci(ArrayList<QuadroPrevisionaleItem> voci) {
		this.voci = voci;
	}
	public ArrayList<QuadroPrevisionaleComplessivoItem> getVociQuadroComplessivo() {
		return vociQuadroComplessivo;
	}
	public void setVociQuadroComplessivo(ArrayList<QuadroPrevisionaleComplessivoItem> vociQuadroComplessivo) {
		this.vociQuadroComplessivo = vociQuadroComplessivo;
	}
	public QuadroPrevisionale getQuadroPrevisionale() {
		return quadroPrevisionale;
	}
	public void setQuadroPrevisionale(QuadroPrevisionale quadroPrevisionale) {
		this.quadroPrevisionale = quadroPrevisionale;
	}
	public String getNoteQuadroPrevisionale() {
		return noteQuadroPrevisionale;
	}
	public void setNoteQuadroPrevisionale(String noteQuadroPrevisionale) {
		this.noteQuadroPrevisionale = noteQuadroPrevisionale;
	}
	
	
	
}
