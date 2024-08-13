/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.request;

import java.util.ArrayList;

public class AssociaFilesRequest {
	
	// Elenco degli id dei file da associare.
	ArrayList<Long> elencoIdDocumentoIndex;	
	
	// id dell'oggetto a cui associare i file (es: id del documento di spesa)
	Long idTarget;
	
	// Id del progetto.
	Long idProgetto;

	public ArrayList<Long> getElencoIdDocumentoIndex() {
		return elencoIdDocumentoIndex;
	}

	public void setElencoIdDocumentoIndex(ArrayList<Long> elencoIdDocumentoIndex) {
		this.elencoIdDocumentoIndex = elencoIdDocumentoIndex;
	}

	public Long getIdTarget() {
		return idTarget;
	}

	public void setIdTarget(Long idTarget) {
		this.idTarget = idTarget;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

}
