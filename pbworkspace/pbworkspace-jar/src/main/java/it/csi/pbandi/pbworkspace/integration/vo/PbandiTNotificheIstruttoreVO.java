/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.vo;

import java.util.Date;

public class PbandiTNotificheIstruttoreVO {

	private Long idSoggettoNotifica;
	private Date dtFineValidita;
	private Long idNotificaAlert;
	private Long idSoggetto;
	private Date dtInizioValidita;
	private Long idFrequenza;

	public PbandiTNotificheIstruttoreVO() {
	}

	public PbandiTNotificheIstruttoreVO(Long idSoggettoNotifica) {
		this.idSoggettoNotifica = idSoggettoNotifica;
	}

	public PbandiTNotificheIstruttoreVO(Long idSoggettoNotifica, Date dtFineValidita, Long idNotificaAlert,
			Long idSoggetto, Date dtInizioValidita, Long idFrequenza) {
		this.idSoggettoNotifica = idSoggettoNotifica;
		this.dtFineValidita = dtFineValidita;
		this.idNotificaAlert = idNotificaAlert;
		this.idSoggetto = idSoggetto;
		this.dtInizioValidita = dtInizioValidita;
		this.idFrequenza = idFrequenza;
	}

	public Long getIdSoggettoNotifica() {
		return idSoggettoNotifica;
	}

	public void setIdSoggettoNotifica(Long idSoggettoNotifica) {
		this.idSoggettoNotifica = idSoggettoNotifica;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public Long getIdNotificaAlert() {
		return idNotificaAlert;
	}

	public void setIdNotificaAlert(Long idNotificaAlert) {
		this.idNotificaAlert = idNotificaAlert;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Long getIdFrequenza() {
		return idFrequenza;
	}

	public void setIdFrequenza(Long idFrequenza) {
		this.idFrequenza = idFrequenza;
	}
}
