/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

public class LineaDiInterventoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idLineaDiIntervento = null;

	public void setIdLineaDiIntervento(Long val) {
		idLineaDiIntervento = val;
	}

	public Long getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	private String descBreveLinea = null;

	public void setDescBreveLinea(String val) {
		descBreveLinea = val;
	}

	public String getDescBreveLinea() {
		return descBreveLinea;
	}


	private String descLinea = null;

	public void setDescLinea(String val) {
		descLinea = val;
	}

	public String getDescLinea() {
		return descLinea;
	}

}
