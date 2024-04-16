/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

public class EsitoSchedaProgetto implements java.io.Serializable {

	/// Field [esito]
	private java.lang.Boolean _esito = null;

	public void setEsito(java.lang.Boolean val) {
		_esito = val;
	}

	public java.lang.Boolean getEsito() {
		return _esito;
	}

	/// Field [messaggiiGlobali]
	private java.util.ArrayList<java.lang.String> _messaggiiGlobali = new java.util.ArrayList<java.lang.String>();

	public void setMessaggiiGlobali(java.util.ArrayList<java.lang.String> val) {
		_messaggiiGlobali = val;
	}

	public java.util.ArrayList<java.lang.String> getMessaggiiGlobali() {
		return _messaggiiGlobali;
	}

	/// Field [erroriGlobali]
	private java.util.ArrayList<java.lang.String> _erroriGlobali = new java.util.ArrayList<java.lang.String>();

	public void setErroriGlobali(java.util.ArrayList<java.lang.String> val) {
		_erroriGlobali = val;
	}

	public java.util.ArrayList<java.lang.String> getErroriGlobali() {
		return _erroriGlobali;
	}

	/// Field [erroriCampi]
	private java.util.ArrayList<java.lang.String> _erroriCampi = new java.util.ArrayList<java.lang.String>();

	public void setErroriCampi(java.util.ArrayList<java.lang.String> val) {
		_erroriCampi = val;
	}

	public java.util.ArrayList<java.lang.String> getErroriCampi() {
		return _erroriCampi;
	}

	/// Field [chiaviCampi]
	private java.util.ArrayList<java.lang.String> _chiaviCampi = new java.util.ArrayList<java.lang.String>();

	public void setChiaviCampi(java.util.ArrayList<java.lang.String> val) {
		_chiaviCampi = val;
	}

	public java.util.ArrayList<java.lang.String> getChiaviCampi() {
		return _chiaviCampi;
	}

	/// Field [schedaProgetto]
	private it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto _schedaProgetto = null;

	public void setSchedaProgetto(
			it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto val) {
		_schedaProgetto = val;
	}

	public it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto getSchedaProgetto() {
		return _schedaProgetto;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public EsitoSchedaProgetto() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R1749078416) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
