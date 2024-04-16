/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.request;

import java.util.List;

import it.csi.pbandi.pbworkspace.integration.vo.ProgettoVO;

public class NotificaRequest {
	public Long progrBandoLineaIntervento;
	public Long idProgetto;
	public List<ProgettoVO> elencoPrj;

	public Long getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(Long progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public List<ProgettoVO> getElencoPrj() {
		return elencoPrj;
	}

	public void setElencoPrj(List<ProgettoVO> elencoPrj) {
		this.elencoPrj = elencoPrj;
	}

}
