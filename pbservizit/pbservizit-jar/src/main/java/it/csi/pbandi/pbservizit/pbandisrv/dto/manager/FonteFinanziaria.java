/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager;

public class FonteFinanziaria implements java.io.Serializable {

	/// Field [descFonteFinanziaria]
	private java.lang.String _descFonteFinanziaria = null;

	public void setDescFonteFinanziaria(java.lang.String val) {
		_descFonteFinanziaria = val;
	}

	public java.lang.String getDescFonteFinanziaria() {
		return _descFonteFinanziaria;
	}

	/// Field [importoUltimaQuota]
	private java.lang.Double _importoUltimaQuota = null;

	public void setImportoUltimaQuota(java.lang.Double val) {
		_importoUltimaQuota = val;
	}

	public java.lang.Double getImportoUltimaQuota() {
		return _importoUltimaQuota;
	}

	/// Field [percentualeUltimaQuota]
	private java.lang.Double _percentualeUltimaQuota = null;

	public void setPercentualeUltimaQuota(java.lang.Double val) {
		_percentualeUltimaQuota = val;
	}

	public java.lang.Double getPercentualeUltimaQuota() {
		return _percentualeUltimaQuota;
	}

	/// Field [importoQuota]
	private java.lang.Double _importoQuota = null;

	public void setImportoQuota(java.lang.Double val) {
		_importoQuota = val;
	}

	public java.lang.Double getImportoQuota() {
		return _importoQuota;
	}

	/// Field [percentualeQuota]
	private java.lang.Double _percentualeQuota = null;

	public void setPercentualeQuota(java.lang.Double val) {
		_percentualeQuota = val;
	}

	public java.lang.Double getPercentualeQuota() {
		return _percentualeQuota;
	}

	/// Field [modificabile]
	private boolean _modificabile = false;

	public void setModificabile(boolean val) {
		_modificabile = val;
	}

	public boolean getModificabile() {
		return _modificabile;
	}

	/// Field [isSubTotale]
	private boolean _isSubTotale = false;

	public void setIsSubTotale(boolean val) {
		_isSubTotale = val;
	}

	public boolean getIsSubTotale() {
		return _isSubTotale;
	}

	/// Field [isFontePrivata]
	private boolean _isFontePrivata = false;

	public void setIsFontePrivata(boolean val) {
		_isFontePrivata = val;
	}

	public boolean getIsFontePrivata() {
		return _isFontePrivata;
	}

	/// Field [idFonteFinanziaria]
	private java.lang.Long _idFonteFinanziaria = null;

	public void setIdFonteFinanziaria(java.lang.Long val) {
		_idFonteFinanziaria = val;
	}

	public java.lang.Long getIdFonteFinanziaria() {
		return _idFonteFinanziaria;
	}

	/// Field [isFonteTotale]
	private boolean _isFonteTotale = false;

	public void setIsFonteTotale(boolean val) {
		_isFonteTotale = val;
	}

	public boolean getIsFonteTotale() {
		return _isFonteTotale;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public FonteFinanziaria() {
		super();

	}

	public String toString() {
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
	}
}
