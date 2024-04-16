/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbworkspace.util.BeanUtil;

public class InizializzaSchedaProgettoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String nomeBandoLinea;
	private Long idProcesso;

	private ArrayList<CodiceDescrizione> comboPrioritaQsn;

	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto schedaProgetto = null;

	private Boolean isProgrammazione2127;

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto getSchedaProgetto() {
		return schedaProgetto;
	}

	public void setSchedaProgetto(it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto schedaProgetto) {
		this.schedaProgetto = schedaProgetto;
	}

	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	public ArrayList<CodiceDescrizione> getComboPrioritaQsn() {
		return comboPrioritaQsn;
	}

	public void setComboPrioritaQsn(ArrayList<CodiceDescrizione> comboPrioritaQsn) {
		this.comboPrioritaQsn = comboPrioritaQsn;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public Boolean getIsProgrammazione2127() {
		return isProgrammazione2127;
	}

	public void setIsProgrammazione2127(Boolean isProgrammazione2127) {
		this.isProgrammazione2127 = isProgrammazione2127;
	}
}
