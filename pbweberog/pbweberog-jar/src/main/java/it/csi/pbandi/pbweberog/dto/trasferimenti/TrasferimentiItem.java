/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.trasferimenti;

public class TrasferimentiItem implements java.io.Serializable {

	/// Field [idTrasferimento]
	private java.lang.Long _idTrasferimento = null;

	public void setIdTrasferimento(java.lang.Long val) {
		_idTrasferimento = val;
	}

	public java.lang.Long getIdTrasferimento() {
		return _idTrasferimento;
	}

	/// Field [codiceTrasferimento]
	private java.lang.String _codiceTrasferimento = null;

	public void setCodiceTrasferimento(java.lang.String val) {
		_codiceTrasferimento = val;
	}

	public java.lang.String getCodiceTrasferimento() {
		return _codiceTrasferimento;
	}

	/// Field [dtTrasferimento]
	private java.lang.String _dtTrasferimento = null;

	public void setDtTrasferimento(java.lang.String val) {
		_dtTrasferimento = val;
	}

	public java.lang.String getDtTrasferimento() {
		return _dtTrasferimento;
	}

	/// Field [idCausaleTrasferimento]
	private java.lang.Long _idCausaleTrasferimento = null;

	public void setIdCausaleTrasferimento(java.lang.Long val) {
		_idCausaleTrasferimento = val;
	}

	public java.lang.Long getIdCausaleTrasferimento() {
		return _idCausaleTrasferimento;
	}

	/// Field [idSoggettoBeneficiario]
	private java.lang.Long _idSoggettoBeneficiario = null;

	public void setIdSoggettoBeneficiario(java.lang.Long val) {
		_idSoggettoBeneficiario = val;
	}

	public java.lang.Long getIdSoggettoBeneficiario() {
		return _idSoggettoBeneficiario;
	}

	/// Field [importoTrasferimento]
	private java.lang.Double _importoTrasferimento = null;

	public void setImportoTrasferimento(java.lang.Double val) {
		_importoTrasferimento = val;
	}

	public java.lang.Double getImportoTrasferimento() {
		return _importoTrasferimento;
	}

	/// Field [flagPubblicoPrivato]
	private java.lang.String _flagPubblicoPrivato = null;

	public void setFlagPubblicoPrivato(java.lang.String val) {
		_flagPubblicoPrivato = val;
	}

	public java.lang.String getFlagPubblicoPrivato() {
		return _flagPubblicoPrivato;
	}

	/// Field [descPubblicoPrivato]
	private java.lang.String _descPubblicoPrivato = null;

	public void setDescPubblicoPrivato(java.lang.String val) {
		_descPubblicoPrivato = val;
	}

	public java.lang.String getDescPubblicoPrivato() {
		return _descPubblicoPrivato;
	}

	/// Field [descCausaleTrasferimento]
	private java.lang.String _descCausaleTrasferimento = null;

	public void setDescCausaleTrasferimento(java.lang.String val) {
		_descCausaleTrasferimento = val;
	}

	public java.lang.String getDescCausaleTrasferimento() {
		return _descCausaleTrasferimento;
	}

	/// Field [cfBeneficiario]
	private java.lang.String _cfBeneficiario = null;

	public void setCfBeneficiario(java.lang.String val) {
		_cfBeneficiario = val;
	}

	public java.lang.String getCfBeneficiario() {
		return _cfBeneficiario;
	}

	/// Field [denominazioneBeneficiario]
	private java.lang.String _denominazioneBeneficiario = null;

	public void setDenominazioneBeneficiario(java.lang.String val) {
		_denominazioneBeneficiario = val;
	}

	public java.lang.String getDenominazioneBeneficiario() {
		return _denominazioneBeneficiario;
	}

	/// Field [isEliminabile]
	private java.lang.Boolean _isEliminabile = null;

	public void setIsEliminabile(java.lang.Boolean val) {
		_isEliminabile = val;
	}

	public java.lang.Boolean getIsEliminabile() {
		return _isEliminabile;
	}

	/// Field [isModificabile]
	private java.lang.Boolean _isModificabile = null;

	public void setIsModificabile(java.lang.Boolean val) {
		_isModificabile = val;
	}

	public java.lang.Boolean getIsModificabile() {
		return _isModificabile;
	}

	/// Field [lnkModifica]
	private java.lang.String _lnkModifica = null;

	public void setLnkModifica(java.lang.String val) {
		_lnkModifica = val;
	}

	public java.lang.String getLnkModifica() {
		return _lnkModifica;
	}

	/// Field [lnkElimina]
	private java.lang.String _lnkElimina = null;

	public void setLnkElimina(java.lang.String val) {
		_lnkElimina = val;
	}

	public java.lang.String getLnkElimina() {
		return _lnkElimina;
	}

	/// Field [lnkDettaglio]
	private java.lang.String _lnkDettaglio = null;

	public void setLnkDettaglio(java.lang.String val) {
		_lnkDettaglio = val;
	}

	public java.lang.String getLnkDettaglio() {
		return _lnkDettaglio;
	}
	
	private java.lang.Long _idLineaDiIntervento = null;
	
	public java.lang.Long getIdLineaDiIntervento() {
		return _idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(java.lang.Long _idLineaDiIntervento) {
		this._idLineaDiIntervento = _idLineaDiIntervento;
	}

	// il serial version uid e' fisso in quanto la classe in oggetto e' serializzabile
	// solo per la clusterizzazione della sessione web e non viene scambiata con altre
	// componenti.
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore vuoto del DTO.
	 */
	public TrasferimentiItem() {
		super();

	}

	public String toString() {
		/*PROTECTED REGION ID(R-1580559580) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}

}
