/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class FornitoreVO extends GenericVO{


		static final long serialVersionUID = 1;
		private java.lang.String _codiceFiscaleFornitore = null;
		public void setCodiceFiscaleFornitore(java.lang.String val) {
			_codiceFiscaleFornitore = val;
		}


		public java.lang.String getCodiceFiscaleFornitore() {
			return _codiceFiscaleFornitore;
		}
		private java.lang.String _cognomeFornitore = null;

		public void setCognomeFornitore(java.lang.String val) {
			_cognomeFornitore = val;
		}

		
		public java.lang.String getCognomeFornitore() {
			return _cognomeFornitore;
		}

		private java.lang.String _denominazioneFornitore = null;

		public void setDenominazioneFornitore(java.lang.String val) {
			_denominazioneFornitore = val;
		}

		
		public java.lang.String getDenominazioneFornitore() {
			return _denominazioneFornitore;
		}

		private java.lang.Long _idFornitore = null;

		public void setIdFornitore(java.lang.Long val) {
			_idFornitore = val;
		}

		
		public java.lang.Long getIdFornitore() {
			return _idFornitore;
		}

		private java.lang.Long _idTipoSoggetto = null;

		public void setIdTipoSoggetto(java.lang.Long val) {
			_idTipoSoggetto = val;
		}

		
		public java.lang.Long getIdTipoSoggetto() {
			return _idTipoSoggetto;
		}

		private java.lang.String _nomeFornitore = null;

		public void setNomeFornitore(java.lang.String val) {
			_nomeFornitore = val;
		}

		
		public java.lang.String getNomeFornitore() {
			return _nomeFornitore;
		}

		private java.lang.String _partitaIvaFornitore = null;

		public void setPartitaIvaFornitore(java.lang.String val) {
			_partitaIvaFornitore = val;
		}

		
		public java.lang.String getPartitaIvaFornitore() {
			return _partitaIvaFornitore;
		}

		private java.lang.Long _idSoggettoFornitore = null;

		public void setIdSoggettoFornitore(java.lang.Long val) {
			_idSoggettoFornitore = val;
		}

		
		public java.lang.Long getIdSoggettoFornitore() {
			return _idSoggettoFornitore;
		}

		private java.lang.String _descTipoSoggetto = null;

		public void setDescTipoSoggetto(java.lang.String val) {
			_descTipoSoggetto = val;
		}

		
		public java.lang.String getDescTipoSoggetto() {
			return _descTipoSoggetto;
		}

		private java.lang.Long _idQualifica = null;

		public void setIdQualifica(java.lang.Long val) {
			_idQualifica = val;
		}

		
		public java.lang.Long getIdQualifica() {
			return _idQualifica;
		}

		private java.lang.String _descQualifica = null;

		public void setDescQualifica(java.lang.String val) {
			_descQualifica = val;
		}

		
		public java.lang.String getDescQualifica() {
			return _descQualifica;
		}

		
		private java.lang.Double _costoOrario = null;

		/**
		 * @generated
		 */
		public void setCostoOrario(java.lang.Double val) {
			_costoOrario = val;
		}


		public java.lang.Double getCostoOrario() {
			return _costoOrario;
		}
		

		

		private java.util.Date _dtFineValidita= null;
	
		public void setDtFineValidita(java.util.Date _dtFineValidita) {
			this._dtFineValidita = _dtFineValidita;
		}

		public java.util.Date getDtFineValidita() {
			return _dtFineValidita;
		}
		private java.lang.Long idFormaGiuridica= null;
		private java.lang.Long idAttivitaAteco= null;
		private java.lang.Long idNazione= null;
		private String altroCodice = null;
		private String codUniIpa = null;
		private Long flagPubblicoPrivato = null;
		
		public java.lang.Long getIdFormaGiuridica() {
			return idFormaGiuridica;
		}

		public void setIdFormaGiuridica(java.lang.Long idFormaGiuridica) {
			this.idFormaGiuridica = idFormaGiuridica;
		}

		public java.lang.Long getIdNazione() {
			return idNazione;
		}


		public void setIdNazione(java.lang.Long idNazione) {
			this.idNazione = idNazione;
		}


		public java.lang.Long getIdAttivitaAteco() {
			return idAttivitaAteco;
		}


		public void setIdAttivitaAteco(java.lang.Long idAttivitaAteco) {
			this.idAttivitaAteco = idAttivitaAteco;
		}


		public String getAltroCodice() {
			return altroCodice;
		}


		public void setAltroCodice(String altroCodice) {
			this.altroCodice = altroCodice;
		}


		public String getCodUniIpa() {
			return codUniIpa;
		}


		public void setCodUniIpa(String codUniIpa) {
			this.codUniIpa = codUniIpa;
		}


		public Long getFlagPubblicoPrivato() {
			return flagPubblicoPrivato;
		}


		public void setFlagPubblicoPrivato(Long flagPubblicoPrivato) {
			this.flagPubblicoPrivato = flagPubblicoPrivato;
		}
}
