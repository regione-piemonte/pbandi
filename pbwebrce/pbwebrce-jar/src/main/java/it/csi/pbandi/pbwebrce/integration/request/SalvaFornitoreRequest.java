/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

public class SalvaFornitoreRequest {
	private Long idAppalto;
	private Long idFornitore;
	private Long idTipoPercettore;

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Long getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}

	public Long getIdTipoPercettore() {
		return idTipoPercettore;
	}

	public void setIdTipoPercettore(Long idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
	}
	
	
	
}
