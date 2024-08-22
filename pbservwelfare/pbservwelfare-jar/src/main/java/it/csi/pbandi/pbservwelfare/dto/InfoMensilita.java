/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

import java.util.Date;

public class InfoMensilita extends ElencoMensilita {

	private String noteErogazioni;
	private Integer idProgetto;
	private Date dtChiusuraValidazione;

	public String getNoteErogazioni() {
		return noteErogazioni;
	}

	public void setNoteErogazioni(String noteErogazioni) {
		this.noteErogazioni = noteErogazioni;
	}

	public Integer getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Integer idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Date getDtChiusuraValidazione() {
		return dtChiusuraValidazione;
	}

	public void setDtChiusuraValidazione(Date dtChiusuraValidazione) {
		this.dtChiusuraValidazione = dtChiusuraValidazione;
	}

}
