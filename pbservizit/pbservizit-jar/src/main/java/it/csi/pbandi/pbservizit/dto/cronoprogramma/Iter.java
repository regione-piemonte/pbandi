/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.cronoprogramma;

public class Iter implements java.io.Serializable {

	/// Field [idIter]
	private java.lang.Long idIter = null;

	public void setIdIter(java.lang.Long val) {
		idIter = val;
	}

	public java.lang.Long getIdIter() {
		return idIter;
	}

	/// Field [descIter]
	private java.lang.String descIter = null;

	public void setDescIter(java.lang.String val) {
		descIter = val;
	}

	public java.lang.String getDescIter() {
		return descIter;
	}

	/// Field [codIgrue]
	private java.lang.String codIgrue = null;

	public void setCodIgrue(java.lang.String val) {
		codIgrue = val;
	}

	public java.lang.String getCodIgrue() {
		return codIgrue;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public Iter() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R-1496151644) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
