/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

import it.csi.pbandi.pbwebrce.dto.quadroprevisionale.QuadroPrevisionaleDTO;

public class SalvaQuadroPrevisionaleRequest {
	private QuadroPrevisionaleDTO quadroPrevisionale;
	private Long idProgetto;
	public QuadroPrevisionaleDTO getQuadroPrevisionale() {
		return quadroPrevisionale;
	}
	public void setQuadroPrevisionale(QuadroPrevisionaleDTO quadroPrevisionale) {
		this.quadroPrevisionale = quadroPrevisionale;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	
}
