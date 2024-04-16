/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class SchedaProgettoInfoBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String id = null;
	private java.lang.String idBandoLinea = null;
	private java.lang.String idLineaNormativa = null;
	private java.lang.String idLineaAsse = null;
	private java.lang.String flagSalvaIntermediario = null;
	private java.lang.String flagAllineatoCUP = null;
	
	public SchedaProgettoInfoBase() {
		super();
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(java.lang.String idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public java.lang.String getIdLineaNormativa() {
		return idLineaNormativa;
	}

	public void setIdLineaNormativa(java.lang.String idLineaNormativa) {
		this.idLineaNormativa = idLineaNormativa;
	}

	public java.lang.String getIdLineaAsse() {
		return idLineaAsse;
	}

	public void setIdLineaAsse(java.lang.String idLineaAsse) {
		this.idLineaAsse = idLineaAsse;
	}

	public java.lang.String getFlagSalvaIntermediario() {
		return flagSalvaIntermediario;
	}

	public void setFlagSalvaIntermediario(java.lang.String flagSalvaIntermediario) {
		this.flagSalvaIntermediario = flagSalvaIntermediario;
	}

	public java.lang.String getFlagAllineatoCUP() {
		return flagAllineatoCUP;
	}

	public void setFlagAllineatoCUP(java.lang.String flagAllineatoCUP) {
		this.flagAllineatoCUP = flagAllineatoCUP;
	}

}
