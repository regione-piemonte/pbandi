/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.registrocontrolli;

public class Allegato implements java.io.Serializable {

	/// Field [idDocumentoIndex]
	private java.lang.Long _idDocumentoIndex = null;

	public void setIdDocumentoIndex(java.lang.Long val) {
		_idDocumentoIndex = val;
	}

	public java.lang.Long getIdDocumentoIndex() {
		return _idDocumentoIndex;
	}

	/// Field [nomeFile]
	private java.lang.String _nomeFile = null;

	public void setNomeFile(java.lang.String val) {
		_nomeFile = val;
	}

	public java.lang.String getNomeFile() {
		return _nomeFile;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public Allegato() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R-1509440699) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
