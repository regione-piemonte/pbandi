/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;
import java.util.ArrayList;

import it.csi.pbandi.pbweberog.dto.disimpegni.RigaModificaRevocaItem;


public class RequestFindIrregolarita implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<RigaModificaRevocaItem> revoche;
	private Long idRevoca;
	public ArrayList<RigaModificaRevocaItem> getRevoche() {
		return revoche;
	}
	public void setRevoche(ArrayList<RigaModificaRevocaItem> revoche) {
		this.revoche = revoche;
	}

	public Long getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
	}
	
	
	
	
	
}
