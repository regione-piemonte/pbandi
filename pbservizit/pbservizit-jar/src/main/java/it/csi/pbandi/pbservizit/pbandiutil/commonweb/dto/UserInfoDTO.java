/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb.dto;

import java.io.Serializable;

public class UserInfoDTO implements Serializable{

	/// Field [idIride]
	private java.lang.String _idIride = null;

	public void setIdIride(java.lang.String val) {
		_idIride = val;
	}

	public java.lang.String getIdIride() {
		return _idIride;
	}

	/// Field [codFisc]
	private java.lang.String _codFisc = null;

	public void setCodFisc(java.lang.String val) {
		_codFisc = val;
	}

	public java.lang.String getCodFisc() {
		return _codFisc;
	}

	/// Field [cognome]
	private java.lang.String _cognome = null;

	public void setCognome(java.lang.String val) {
		_cognome = val;
	}

	public java.lang.String getCognome() {
		return _cognome;
	}

	/// Field [nome]
	private java.lang.String _nome = null;

	public void setNome(java.lang.String val) {
		_nome = val;
	}

	public java.lang.String getNome() {
		return _nome;
	}

	/// Field [ente]
	private java.lang.String _ente = null;

	public void setEnte(java.lang.String val) {
		_ente = val;
	}

	public java.lang.String getEnte() {
		return _ente;
	}

	/// Field [ruolo]
	private java.lang.String _ruolo = null;

	public void setRuolo(java.lang.String val) {
		_ruolo = val;
	}

	public java.lang.String getRuolo() {
		return _ruolo;
	}

	/// Field [idUtente]
	private java.lang.Long _idUtente = null;

	public void setIdUtente(java.lang.Long val) {
		_idUtente = val;
	}

	public java.lang.Long getIdUtente() {
		return _idUtente;
	}

	/// Field [tipoBeneficiario]
	private java.lang.String _tipoBeneficiario = null;

	public void setTipoBeneficiario(java.lang.String val) {
		_tipoBeneficiario = val;
	}

	public java.lang.String getTipoBeneficiario() {
		return _tipoBeneficiario;
	}

	/// Field [codiceRuolo]
	private java.lang.String _codiceRuolo = null;

	public void setCodiceRuolo(java.lang.String val) {
		_codiceRuolo = val;
	}

	public java.lang.String getCodiceRuolo() {
		return _codiceRuolo;
	}

	/// Field [idSoggetto]
	private java.lang.Long _idSoggetto = null;

	public void setIdSoggetto(java.lang.Long val) {
		_idSoggetto = val;
	}

	public java.lang.Long getIdSoggetto() {
		return _idSoggetto;
	}
	
	
	/// Field [isIncaricato]
	private java.lang.Boolean _isIncaricato = null;

	public void setIsIncaricato(java.lang.Boolean val) {
		_isIncaricato = val;
	}

	public java.lang.Boolean getIsIncaricato() {
		return _isIncaricato;
	}
	public java.lang.String getRuoloHelp() {
		return ruoloHelp;
	}

	public void setRuoloHelp(java.lang.String ruoloHelp) {
		this.ruoloHelp = ruoloHelp;
	}

	private java.lang.String ruoloHelp = null;
}
