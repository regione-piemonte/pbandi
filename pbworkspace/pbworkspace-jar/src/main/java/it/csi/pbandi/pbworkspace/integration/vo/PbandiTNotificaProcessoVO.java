/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.util.Date;

public class PbandiTNotificaProcessoVO {
	private Long idNotifica;
	private Long idProgetto;
	private Long idRuoloDiProcessoDest;
	private String subjectNotifica;
	private String messageNotifica;
	private String statoNotifica;
	private Long idUtenteMitt;
	private Date dtNotifica;
	private Long idUtenteAgg;
	private Date dtAggStatoNotifica;
	private Long idTemplateNotifica;
	private Long idEntita;
	private Long idTarget;

	public Long getIdNotifica() {
		return idNotifica;
	}

	public void setIdNotifica(Long idNotifica) {
		this.idNotifica = idNotifica;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdRuoloDiProcessoDest() {
		return idRuoloDiProcessoDest;
	}

	public void setIdRuoloDiProcessoDest(Long idRuoloDiProcessoDest) {
		this.idRuoloDiProcessoDest = idRuoloDiProcessoDest;
	}

	public String getSubjectNotifica() {
		return subjectNotifica;
	}

	public void setSubjectNotifica(String subjectNotifica) {
		this.subjectNotifica = subjectNotifica;
	}

	public String getMessageNotifica() {
		return messageNotifica;
	}

	public void setMessageNotifica(String messageNotifica) {
		this.messageNotifica = messageNotifica;
	}

	public String getStatoNotifica() {
		return statoNotifica;
	}

	public void setStatoNotifica(String statoNotifica) {
		this.statoNotifica = statoNotifica;
	}

	public Long getIdUtenteMitt() {
		return idUtenteMitt;
	}

	public void setIdUtenteMitt(Long idUtenteMitt) {
		this.idUtenteMitt = idUtenteMitt;
	}

	public Date getDtNotifica() {
		return dtNotifica;
	}

	public void setDtNotifica(Date dtNotifica) {
		this.dtNotifica = dtNotifica;
	}

	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public Date getDtAggStatoNotifica() {
		return dtAggStatoNotifica;
	}

	public void setDtAggStatoNotifica(Date dtAggStatoNotifica) {
		this.dtAggStatoNotifica = dtAggStatoNotifica;
	}

	public Long getIdTemplateNotifica() {
		return idTemplateNotifica;
	}

	public void setIdTemplateNotifica(Long idTemplateNotifica) {
		this.idTemplateNotifica = idTemplateNotifica;
	}

	public Long getIdEntita() {
		return idEntita;
	}

	public void setIdEntita(Long idEntita) {
		this.idEntita = idEntita;
	}

	public Long getIdTarget() {
		return idTarget;
	}

	public void setIdTarget(Long idTarget) {
		this.idTarget = idTarget;
	}

	@Override
	public String toString() {
		return "PbandiTNotificaProcessoVO [idNotifica=" + idNotifica + ", idProgetto=" + idProgetto
				+ ", idRuoloDiProcessoDest=" + idRuoloDiProcessoDest + ", subjectNotifica=" + subjectNotifica
				+ ", messageNotifica=" + messageNotifica + ", statoNotifica=" + statoNotifica + ", idUtenteMitt="
				+ idUtenteMitt + ", dtNotifica=" + dtNotifica + ", idUtenteAgg=" + idUtenteAgg + ", dtAggStatoNotifica="
				+ dtAggStatoNotifica + ", idTemplateNotifica=" + idTemplateNotifica + ", idEntita=" + idEntita
				+ ", idTarget=" + idTarget + "]";
	}
}
