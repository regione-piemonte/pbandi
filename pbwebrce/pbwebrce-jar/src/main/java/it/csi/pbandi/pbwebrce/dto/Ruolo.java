/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto;

public class Ruolo implements java.io.Serializable {

	/// Field [id]
	private java.lang.String _id = null;

	public void setId(java.lang.String val) {
		_id = val;
	}

	public java.lang.String getId() {
		return _id;
	}

	/// Field [descrizione]
	private java.lang.String _descrizione = null;

	public void setDescrizione(java.lang.String val) {
		_descrizione = val;
	}

	public java.lang.String getDescrizione() {
		return _descrizione;
	}

	/// Field [descrizioneBreve]
	private java.lang.String _descrizioneBreve = null;

	public void setDescrizioneBreve(java.lang.String val) {
		_descrizioneBreve = val;
	}

	public java.lang.String getDescrizioneBreve() {
		return _descrizioneBreve;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public Ruolo() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R1745901659) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
