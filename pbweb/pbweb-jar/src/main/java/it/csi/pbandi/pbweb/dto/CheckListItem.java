/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

public class CheckListItem implements java.io.Serializable {

	/// Field [idChecklist]
	private java.lang.Long _idChecklist = null;

	public void setIdChecklist(java.lang.Long val) {
		_idChecklist = val;
	}

	public java.lang.Long getIdChecklist() {
		return _idChecklist;
	}

	/// Field [idDichiarazione]
	private java.lang.Long _idDichiarazione = null;

	public void setIdDichiarazione(java.lang.Long val) {
		_idDichiarazione = val;
	}

	public java.lang.Long getIdDichiarazione() {
		return _idDichiarazione;
	}

	/// Field [codiceTipo]
	private java.lang.String _codiceTipo = null;

	public void setCodiceTipo(java.lang.String val) {
		_codiceTipo = val;
	}

	public java.lang.String getCodiceTipo() {
		return _codiceTipo;
	}

	/// Field [codiceStato]
	private java.lang.String _codiceStato = null;

	public void setCodiceStato(java.lang.String val) {
		_codiceStato = val;
	}

	public java.lang.String getCodiceStato() {
		return _codiceStato;
	}

	/// Field [versione]
	private java.lang.String _versione = null;

	public void setVersione(java.lang.String val) {
		_versione = val;
	}

	public java.lang.String getVersione() {
		return _versione;
	}

	/// Field [descTipo]
	private java.lang.String _descTipo = null;

	public void setDescTipo(java.lang.String val) {
		_descTipo = val;
	}

	public java.lang.String getDescTipo() {
		return _descTipo;
	}

	/// Field [descStato]
	private java.lang.String _descStato = null;

	public void setDescStato(java.lang.String val) {
		_descStato = val;
	}

	public java.lang.String getDescStato() {
		return _descStato;
	}

	/// Field [dataControllo]
	private java.lang.String _dataControllo = null;

	public void setDataControllo(java.lang.String val) {
		_dataControllo = val;
	}

	public java.lang.String getDataControllo() {
		return _dataControllo;
	}

	/// Field [flagRilevazioneIrregolarita]
	private java.lang.String _flagRilevazioneIrregolarita = null;

	public void setFlagRilevazioneIrregolarita(java.lang.String val) {
		_flagRilevazioneIrregolarita = val;
	}

	public java.lang.String getFlagRilevazioneIrregolarita() {
		return _flagRilevazioneIrregolarita;
	}

	/// Field [soggettoControllore]
	private java.lang.String _soggettoControllore = null;

	public void setSoggettoControllore(java.lang.String val) {
		_soggettoControllore = val;
	}

	public java.lang.String getSoggettoControllore() {
		return _soggettoControllore;
	}

	/// Field [nome]
	private java.lang.String _nome = null;

	public void setNome(java.lang.String val) {
		_nome = val;
	}

	public java.lang.String getNome() {
		return _nome;
	}

	/// Field [idProgetto]
	private java.lang.Long _idProgetto = null;

	public void setIdProgetto(java.lang.Long val) {
		_idProgetto = val;
	}

	public java.lang.Long getIdProgetto() {
		return _idProgetto;
	}

	public CheckListItem() {

	}
	
	private java.lang.Long idDocumentoIndex = null;

	public java.lang.Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(java.lang.Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	private boolean isModificabile;
    private boolean isEliminabile;
    
	public String toString() {
		/*PROTECTED REGION ID(R1664318277) ENABLED START*/
		/// inserire qui la logica desiderata per la rappresenatazione a stringa
		return super.toString();
		/*PROTECTED REGION END*/
	}

	public boolean isModificabile() {
		return isModificabile;
	}

	public void setModificabile(boolean isModificabile) {
		this.isModificabile = isModificabile;
	}

	public boolean isEliminabile() {
		return isEliminabile;
	}

	public void setEliminabile(boolean isEliminabile) {
		this.isEliminabile = isEliminabile;
	}
}
