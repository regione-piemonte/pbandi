/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import it.csi.pbandi.pbwebrce.dto.FiltroProcedureAggiudicazione;

public class GetProcedureAggiudicazioneRequest {
	private FiltroProcedureAggiudicazione filtro;
	private Long idProgetto;
	public FiltroProcedureAggiudicazione getFiltro() {
		return filtro;
	}
	public void setFiltro(FiltroProcedureAggiudicazione filtro) {
		this.filtro = filtro;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
}
