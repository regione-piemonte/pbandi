/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.request;

public class AssociateFileRequest {
	 private Long idProgetto;
	 private String[] files;
	 private String[] idDocumentoIndexSelezionato;
	 private Long idAppalto;
	 
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String[] getFiles() {
		return files;
	}
	public void setFiles(String[] files) {
		this.files = files;
	}
	public String[] getIdDocumentoIndexSelezionato() {
		return idDocumentoIndexSelezionato;
	}
	public void setIdDocumentoIndexSelezionato(String[] idDocumentoIndexSelezionato) {
		this.idDocumentoIndexSelezionato = idDocumentoIndexSelezionato;
	}
	public Long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}
	 
	 
}
