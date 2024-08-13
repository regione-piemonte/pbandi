/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.spesaValidata;

import java.io.Serializable;
import java.util.Date;

public class RilievoDocSpesaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rilievoContabile;
	private Date dtRilievoContabile;
	private Date dtChiusuraRilievo;
	private Date dtConfermaValidita;

	public String getRilievoContabile() {
		return rilievoContabile;
	}

	public void setRilievoContabile(String rilievoContabile) {
		this.rilievoContabile = rilievoContabile;
	}

	public Date getDtRilievoContabile() {
		return dtRilievoContabile;
	}

	public void setDtRilievoContabile(Date dtRilievoContabile) {
		this.dtRilievoContabile = dtRilievoContabile;
	}

	public Date getDtChiusuraRilievo() {
		return dtChiusuraRilievo;
	}

	public void setDtChiusuraRilievo(Date dtChiusuraRilievo) {
		this.dtChiusuraRilievo = dtChiusuraRilievo;
	}

	public Date getDtConfermaValidita() {
		return dtConfermaValidita;
	}

	public void setDtConfermaValidita(Date dtConfermaValidita) {
		this.dtConfermaValidita = dtConfermaValidita;
	}

}
