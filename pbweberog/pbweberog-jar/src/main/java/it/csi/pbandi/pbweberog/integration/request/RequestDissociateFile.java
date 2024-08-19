/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;

public class RequestDissociateFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDocumentoIndex;
	private Long idEntita;
	private Long idProgetto;
	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public Long getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(Long idEntita) {
		this.idEntita = idEntita;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	
	

}
