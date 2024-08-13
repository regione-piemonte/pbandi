/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto.configurazionebandolinea;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.SezioneVO;

public class DatiModificaTemplate implements java.io.Serializable {

	/// Field [sezioni]
	private java.util.ArrayList<SezioneVO> _sezioni = new java.util.ArrayList<SezioneVO>();

	public void setSezioni(
			java.util.ArrayList<SezioneVO> val) {
		_sezioni = val;
	}

	public java.util.ArrayList<SezioneVO> getSezioni() {
		return _sezioni;
	}

	/// Field [descTipoDocumento]
	private java.lang.String _descTipoDocumento = null;

	public void setDescTipoDocumento(java.lang.String val) {
		_descTipoDocumento = val;
	}

	public java.lang.String getDescTipoDocumento() {
		return _descTipoDocumento;
	}

	/// Field [placeholders]
	private java.util.ArrayList<java.lang.String> _placeholders = new java.util.ArrayList<java.lang.String>();

	public void setPlaceholders(java.util.ArrayList<java.lang.String> val) {
		_placeholders = val;
	}

	public java.util.ArrayList<java.lang.String> getPlaceholders() {
		return _placeholders;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public DatiModificaTemplate() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R314895744) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}
}
