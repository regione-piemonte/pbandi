/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;
import java.util.Date;

public class DatiSifRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dtFirmaAccordo;
	private Date dtCompletamentoValutazione;

	public Date getDtFirmaAccordo() {
		return dtFirmaAccordo;
	}

	public void setDtFirmaAccordo(Date dtFirmaAccordo) {
		this.dtFirmaAccordo = dtFirmaAccordo;
	}

	public Date getDtCompletamentoValutazione() {
		return dtCompletamentoValutazione;
	}

	public void setDtCompletamentoValutazione(Date dtCompletamentoValutazione) {
		this.dtCompletamentoValutazione = dtCompletamentoValutazione;
	}

}
