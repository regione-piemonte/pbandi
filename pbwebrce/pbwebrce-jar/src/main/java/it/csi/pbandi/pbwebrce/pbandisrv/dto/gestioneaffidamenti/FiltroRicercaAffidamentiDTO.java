/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti;

import java.io.Serializable;

public class FiltroRicercaAffidamentiDTO implements Serializable {

	static final long serialVersionUID = 1;

	private Long idProgetto = null;

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	private String codiceRuolo = null;

	public void setCodiceRuolo(String val) {
		codiceRuolo = val;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

}
