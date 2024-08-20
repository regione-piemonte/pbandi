/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.quadroprevisionale;

import java.io.Serializable;
import java.util.ArrayList;

public class QuadroPrevisionaleModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<QuadroPrevisionaleItem> voci;
	private ArrayList<QuadroPrevisionaleComplessivoItem> vociQuadroComplessivo;
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
	
	
}
