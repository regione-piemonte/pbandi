/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.util.ArrayList;

import it.csi.pbandi.pbweb.dto.QualificaFormDTO;

public class SalvaQualificheRequest {
	
	private ArrayList<QualificaFormDTO> listaQualificaFormDTO;

	public ArrayList<QualificaFormDTO> getListaQualificaFormDTO() {
		return listaQualificaFormDTO;
	}

	public void setListaQualificaFormDTO(ArrayList<QualificaFormDTO> listaQualificaFormDTO) {
		this.listaQualificaFormDTO = listaQualificaFormDTO;
	}

}
