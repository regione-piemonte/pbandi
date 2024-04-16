/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.request;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto;
import it.csi.pbandi.pbworkspace.util.BeanUtil;

public class SalvaSchedaProgettoRequest {
	
	private SchedaProgetto schedaProgetto;	
	private Boolean datiCompletiPerAvvio;
	
	public SchedaProgetto getSchedaProgetto() {
		return schedaProgetto;
	}
	public void setSchedaProgetto(SchedaProgetto schedaProgetto) {
		this.schedaProgetto = schedaProgetto;
	}
	public Boolean getDatiCompletiPerAvvio() {
		return datiCompletiPerAvvio;
	}
	public void setDatiCompletiPerAvvio(Boolean datiCompletiPerAvvio) {
		this.datiCompletiPerAvvio = datiCompletiPerAvvio;
	}

}
