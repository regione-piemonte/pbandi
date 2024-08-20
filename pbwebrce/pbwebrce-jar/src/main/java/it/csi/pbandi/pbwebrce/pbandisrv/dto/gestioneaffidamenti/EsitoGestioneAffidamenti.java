/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti;


public class EsitoGestioneAffidamenti implements java.io.Serializable {
	
	static final long serialVersionUID = 1;

	private java.lang.Boolean esito = null;

	public void setEsito(java.lang.Boolean val) {
		esito = val;
	}

	public java.lang.Boolean getEsito() {
		return esito;
	}

	private java.lang.String message = null;

	public void setMessage(java.lang.String val) {
		message = val;
	}

	public java.lang.String getMessage() {
		return message;
	}

	private java.lang.String[] params = null;

	public void setParams(java.lang.String[] val) {
		params = val;
	}

	public java.lang.String[] getParams() {
		return params;
	}

	private it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO affidamentoDTO = null;

	public void setAffidamentoDTO(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO val) {
		affidamentoDTO = val;
	}

	public it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO getAffidamentoDTO() {
		return affidamentoDTO;
	}

}
