/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.util.Date;
import java.util.List;

public class NotificaAlertVO {
	private String descrNotifica;
	private Date dtFineValidita;
	private String hasProgettiAssociated;
	private Long idFrequenza;
	private Long idNotificaAlert;
	private List<Long> idProgetti;
	private Long idSoggettoNotifica;
	private Boolean selected;

	public String getDescrNotifica() {
		return descrNotifica;
	}

	public void setDescrNotifica(String descrNotifica) {
		this.descrNotifica = descrNotifica;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getHasProgettiAssociated() {
		return hasProgettiAssociated;
	}

	public void setHasProgettiAssociated(String hasProgettiAssociated) {
		this.hasProgettiAssociated = hasProgettiAssociated;
	}

	public Long getIdFrequenza() {
		return idFrequenza;
	}

	public void setIdFrequenza(Long idFrequenza) {
		this.idFrequenza = idFrequenza;
	}

	public Long getIdNotificaAlert() {
		return idNotificaAlert;
	}

	public void setIdNotificaAlert(Long idNotificaAlert) {
		this.idNotificaAlert = idNotificaAlert;
	}

	public List<Long> getIdProgetti() {
		return idProgetti;
	}

	public void setIdProgetti(List<Long> idProgetti) {
		this.idProgetti = idProgetti;
	}

	public Long getIdSoggettoNotifica() {
		return idSoggettoNotifica;
	}

	public void setIdSoggettoNotifica(Long idSoggettoNotifica) {
		this.idSoggettoNotifica = idSoggettoNotifica;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
