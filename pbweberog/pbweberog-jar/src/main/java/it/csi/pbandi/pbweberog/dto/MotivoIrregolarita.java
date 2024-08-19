/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto;

public class MotivoIrregolarita implements java.io.Serializable {

	/// Field [idMotivoRevoca]
	private java.lang.Long _idMotivoRevoca = null;

	public void setIdMotivoRevoca(java.lang.Long val) {
		_idMotivoRevoca = val;
	}

	public java.lang.Long getIdMotivoRevoca() {
		return _idMotivoRevoca;
	}

	/// Field [codIgrueT36]
	private java.lang.String _codIgrueT36 = null;

	public void setCodIgrueT36(java.lang.String val) {
		_codIgrueT36 = val;
	}

	public java.lang.String getCodIgrueT36() {
		return _codIgrueT36;
	}

	/// Field [dtInizioValidita]
	private java.lang.String _dtInizioValidita = null;

	public void setDtInizioValidita(java.lang.String val) {
		_dtInizioValidita = val;
	}

	public java.lang.String getDtInizioValidita() {
		return _dtInizioValidita;
	}

	/// Field [dtFineValidita]
	private java.lang.String _dtFineValidita = null;

	public void setDtFineValidita(java.lang.String val) {
		_dtFineValidita = val;
	}

	public java.lang.String getDtFineValidita() {
		return _dtFineValidita;
	}

	/// Field [descMotivoRevoca]
	private java.lang.String _descMotivoRevoca = null;

	public void setDescMotivoRevoca(java.lang.String val) {
		_descMotivoRevoca = val;
	}

	public java.lang.String getDescMotivoRevoca() {
		return _descMotivoRevoca;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public MotivoIrregolarita() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R1251073049) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
