/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class IstruttoreVO extends GenericVO{
	
	private String _codiceFiscale = null;
	private String _cognome = null;
	private String _nome = null;
	private Long _totaleProgettiAssociati = null;
	private Long _idBando = null;
	private Long _idProgetto = null;
	private Long _idSoggetto = null;
	private Long _idPersonaFisica = null;
	private String _descBreveTipoAnagrafica = null;
	
	public Long getIdPersonaFisica() {
		return _idPersonaFisica;
	}
	
	public void setIdPersonaFisica(Long idPersonaFisica) {
		this._idPersonaFisica = idPersonaFisica;
	}
	
	public Long getIdSoggetto() {
		return _idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this._idSoggetto = idSoggetto;
	}
	public String getCodiceFiscale() {
		return _codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this._codiceFiscale = codiceFiscale;
	}
	public String getCognome() {
		return _cognome;
	}
	public void setCognome(String cognome) {
		this._cognome = cognome;
	}
	public String getNome() {
		return _nome;
	}
	public void setNome(String nome) {
		this._nome = nome;
	}
	public Long getTotaleProgettiAssociati() {
		return _totaleProgettiAssociati;
	}
	public void setTotaleProgettiAssociati(Long totaleProgettiAssociati) {
		this._totaleProgettiAssociati = totaleProgettiAssociati;
	}
	public Long getIdBando() {
		return _idBando;
	}
	public void setIdBando(Long idBando) {
		this._idBando = idBando;
	}
	public Long getIdProgetto() {
		return _idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this._idProgetto = idProgetto;
	}

	public String getDescBreveTipoAnagrafica() {
		return _descBreveTipoAnagrafica;
	}

	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this._descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	
	

}
