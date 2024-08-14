/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.dto.certificazione;

public class Documento implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String nomeFile;
	private String tipoDocumento;
	private String idDocumentoIndex;
	
	public void setNomeFile(String val) {
		nomeFile = val;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setTipoDocumento(String val) {
		tipoDocumento = val;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setIdDocumentoIndex(String val) {
		idDocumentoIndex = val;
	}

	public String getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public Documento() {
		super();

	}

	public String toString() {
		return super.toString();
	}
}
