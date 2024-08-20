/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.sql.Date;

public class SaveSchedaClienteAllVO {
	
		private String wtfImSaving;
		
		private String idSoggetto;
		private String idUtente;
	
		// SALVA STATO AZIENDA (1)
		private String staAz_idCurrentRecord;
		private String staAz_statoAzienda;
		private String staAz_dataInizio;
		private String staAz_dataFine;
		
		// SALVA STATO CREDITO FINPIEMONTE (2)
		private String staCre_idCurrentRecord;
		private String staCre_stato;
		private String staCre_dataMod;
		private String staCre_PROGR_SOGGETTO_PROGETTO;
		
		// SALVA RATING (3)
		private String rat_idCurrentRecord;
		//private String rat_provider;
		private Long rat_idRating;
		private Date rat_dataClassificazione;
		
		// SALVA BANCA BENEFICIARIO (4)
		private String banBen_idCurrentRecord;
		private String banBen_banca;
		private String banBen_dataModifica;
		private String benBen_motivazione;
		private String banBen_soggettoTerzo;
		private String banBen_PROGR_SOGGETTO_PROGETTO;
		
		// SALVA ESCUSSIONE (5)
		public String esc_dtRichEscussione;
	    public String esc_numProtocolloRich;
	    public String esc_descTipoEscussione;
	    public String esc_descStatoEscussione;
	    public String esc_dtInizioValidita;
	    public String esc_dtNotifica;
	    public String esc_impApprovato;
	    public String esc_impRichiesto;
	    public String esc_causaleBonifico;
	    public String esc_ibanBonifico;
	    public String esc_descBanca;
	    public String esc_note;
	    
		


		public SaveSchedaClienteAllVO(String wtfImSaving, String idSoggetto, String idUtente,
				String staAz_idCurrentRecord, String staAz_statoAzienda, String staAz_dataInizio, String staAz_dataFine,
				String staCre_idCurrentRecord, String staCre_stato, String staCre_dataMod,
				String staCre_PROGR_SOGGETTO_PROGETTO, String rat_idCurrentRecord, Long rat_idRating,
				Date rat_dataClassificazione, String banBen_idCurrentRecord, String banBen_banca,
				String banBen_dataModifica, String benBen_motivazione, String banBen_soggettoTerzo,
				String banBen_PROGR_SOGGETTO_PROGETTO, String esc_dtRichEscussione, String esc_numProtocolloRich,
				String esc_descTipoEscussione, String esc_descStatoEscussione, String esc_dtInizioValidita,
				String esc_dtNotifica, String esc_impApprovato, String esc_impRichiesto, String esc_causaleBonifico,
				String esc_ibanBonifico, String esc_descBanca, String esc_note) {
			this.wtfImSaving = wtfImSaving;
			this.idSoggetto = idSoggetto;
			this.idUtente = idUtente;
			this.staAz_idCurrentRecord = staAz_idCurrentRecord;
			this.staAz_statoAzienda = staAz_statoAzienda;
			this.staAz_dataInizio = staAz_dataInizio;
			this.staAz_dataFine = staAz_dataFine;
			this.staCre_idCurrentRecord = staCre_idCurrentRecord;
			this.staCre_stato = staCre_stato;
			this.staCre_dataMod = staCre_dataMod;
			this.staCre_PROGR_SOGGETTO_PROGETTO = staCre_PROGR_SOGGETTO_PROGETTO;
			this.rat_idCurrentRecord = rat_idCurrentRecord;
			this.rat_idRating = rat_idRating;
			this.rat_dataClassificazione = rat_dataClassificazione;
			this.banBen_idCurrentRecord = banBen_idCurrentRecord;
			this.banBen_banca = banBen_banca;
			this.banBen_dataModifica = banBen_dataModifica;
			this.benBen_motivazione = benBen_motivazione;
			this.banBen_soggettoTerzo = banBen_soggettoTerzo;
			this.banBen_PROGR_SOGGETTO_PROGETTO = banBen_PROGR_SOGGETTO_PROGETTO;
			this.esc_dtRichEscussione = esc_dtRichEscussione;
			this.esc_numProtocolloRich = esc_numProtocolloRich;
			this.esc_descTipoEscussione = esc_descTipoEscussione;
			this.esc_descStatoEscussione = esc_descStatoEscussione;
			this.esc_dtInizioValidita = esc_dtInizioValidita;
			this.esc_dtNotifica = esc_dtNotifica;
			this.esc_impApprovato = esc_impApprovato;
			this.esc_impRichiesto = esc_impRichiesto;
			this.esc_causaleBonifico = esc_causaleBonifico;
			this.esc_ibanBonifico = esc_ibanBonifico;
			this.esc_descBanca = esc_descBanca;
			this.esc_note = esc_note;
		}




		public String getEsc_dtRichEscussione() {
			return esc_dtRichEscussione;
		}




		public void setEsc_dtRichEscussione(String esc_dtRichEscussione) {
			this.esc_dtRichEscussione = esc_dtRichEscussione;
		}




		public String getEsc_numProtocolloRich() {
			return esc_numProtocolloRich;
		}




		public void setEsc_numProtocolloRich(String esc_numProtocolloRich) {
			this.esc_numProtocolloRich = esc_numProtocolloRich;
		}




		public String getEsc_descTipoEscussione() {
			return esc_descTipoEscussione;
		}




		public void setEsc_descTipoEscussione(String esc_descTipoEscussione) {
			this.esc_descTipoEscussione = esc_descTipoEscussione;
		}




		public String getEsc_descStatoEscussione() {
			return esc_descStatoEscussione;
		}




		public void setEsc_descStatoEscussione(String esc_descStatoEscussione) {
			this.esc_descStatoEscussione = esc_descStatoEscussione;
		}




		public String getEsc_dtInizioValidita() {
			return esc_dtInizioValidita;
		}




		public void setEsc_dtInizioValidita(String esc_dtInizioValidita) {
			this.esc_dtInizioValidita = esc_dtInizioValidita;
		}




		public String getEsc_dtNotifica() {
			return esc_dtNotifica;
		}




		public void setEsc_dtNotifica(String esc_dtNotifica) {
			this.esc_dtNotifica = esc_dtNotifica;
		}




		public String getEsc_impApprovato() {
			return esc_impApprovato;
		}




		public void setEsc_impApprovato(String esc_impApprovato) {
			this.esc_impApprovato = esc_impApprovato;
		}




		public String getEsc_impRichiesto() {
			return esc_impRichiesto;
		}




		public void setEsc_impRichiesto(String esc_impRichiesto) {
			this.esc_impRichiesto = esc_impRichiesto;
		}




		public String getEsc_causaleBonifico() {
			return esc_causaleBonifico;
		}




		public void setEsc_causaleBonifico(String esc_causaleBonifico) {
			this.esc_causaleBonifico = esc_causaleBonifico;
		}




		public String getEsc_ibanBonifico() {
			return esc_ibanBonifico;
		}




		public void setEsc_ibanBonifico(String esc_ibanBonifico) {
			this.esc_ibanBonifico = esc_ibanBonifico;
		}




		public String getEsc_descBanca() {
			return esc_descBanca;
		}




		public void setEsc_descBanca(String esc_descBanca) {
			this.esc_descBanca = esc_descBanca;
		}




		public String getEsc_note() {
			return esc_note;
		}




		public void setEsc_note(String esc_note) {
			this.esc_note = esc_note;
		}




		public SaveSchedaClienteAllVO() {
		}


		public String getWtfImSaving() {
			return wtfImSaving;
		}


		public void setWtfImSaving(String wtfImSaving) {
			this.wtfImSaving = wtfImSaving;
		}


		public String getIdSoggetto() {
			return idSoggetto;
		}


		public void setIdSoggetto(String idSoggetto) {
			this.idSoggetto = idSoggetto;
		}


		public String getIdUtente() {
			return idUtente;
		}


		public void setIdUtente(String idUtente) {
			this.idUtente = idUtente;
		}


		public String getStaAz_idCurrentRecord() {
			return staAz_idCurrentRecord;
		}


		public void setStaAz_idCurrentRecord(String staAz_idCurrentRecord) {
			this.staAz_idCurrentRecord = staAz_idCurrentRecord;
		}


		public String getStaAz_statoAzienda() {
			return staAz_statoAzienda;
		}


		public void setStaAz_statoAzienda(String staAz_statoAzienda) {
			this.staAz_statoAzienda = staAz_statoAzienda;
		}


		public String getStaAz_dataInizio() {
			return staAz_dataInizio;
		}


		public void setStaAz_dataInizio(String staAz_dataInizio) {
			this.staAz_dataInizio = staAz_dataInizio;
		}


		public String getStaAz_dataFine() {
			return staAz_dataFine;
		}


		public void setStaAz_dataFine(String staAz_dataFine) {
			this.staAz_dataFine = staAz_dataFine;
		}


		public String getStaCre_idCurrentRecord() {
			return staCre_idCurrentRecord;
		}


		public void setStaCre_idCurrentRecord(String staCre_idCurrentRecord) {
			this.staCre_idCurrentRecord = staCre_idCurrentRecord;
		}


		public String getStaCre_stato() {
			return staCre_stato;
		}


		public void setStaCre_stato(String staCre_stato) {
			this.staCre_stato = staCre_stato;
		}


		public String getStaCre_dataMod() {
			return staCre_dataMod;
		}


		public void setStaCre_dataMod(String staCre_dataMod) {
			this.staCre_dataMod = staCre_dataMod;
		}


		public String getStaCre_PROGR_SOGGETTO_PROGETTO() {
			return staCre_PROGR_SOGGETTO_PROGETTO;
		}


		public void setStaCre_PROGR_SOGGETTO_PROGETTO(String staCre_PROGR_SOGGETTO_PROGETTO) {
			this.staCre_PROGR_SOGGETTO_PROGETTO = staCre_PROGR_SOGGETTO_PROGETTO;
		}


		public String getRat_idCurrentRecord() {
			return rat_idCurrentRecord;
		}


		public void setRat_idCurrentRecord(String rat_idCurrentRecord) {
			this.rat_idCurrentRecord = rat_idCurrentRecord;
		}




		public Long getRat_idRating() {
			return rat_idRating;
		}




		public void setRat_idRating(Long rat_idRating) {
			this.rat_idRating = rat_idRating;
		}




		public Date getRat_dataClassificazione() {
			return rat_dataClassificazione;
		}


		public void setRat_dataClassificazione(Date rat_dataClassificazione) {
			this.rat_dataClassificazione = rat_dataClassificazione;
		}


		public String getBanBen_idCurrentRecord() {
			return banBen_idCurrentRecord;
		}


		public void setBanBen_idCurrentRecord(String banBen_idCurrentRecord) {
			this.banBen_idCurrentRecord = banBen_idCurrentRecord;
		}


		public String getBanBen_banca() {
			return banBen_banca;
		}


		public void setBanBen_banca(String banBen_banca) {
			this.banBen_banca = banBen_banca;
		}


		public String getBanBen_dataModifica() {
			return banBen_dataModifica;
		}


		public void setBanBen_dataModifica(String banBen_dataModifica) {
			this.banBen_dataModifica = banBen_dataModifica;
		}


		public String getBenBen_motivazione() {
			return benBen_motivazione;
		}


		public void setBenBen_motivazione(String benBen_motivazione) {
			this.benBen_motivazione = benBen_motivazione;
		}


		public String getBanBen_soggettoTerzo() {
			return banBen_soggettoTerzo;
		}


		public void setBanBen_soggettoTerzo(String banBen_soggettoTerzo) {
			this.banBen_soggettoTerzo = banBen_soggettoTerzo;
		}


		public String getBanBen_PROGR_SOGGETTO_PROGETTO() {
			return banBen_PROGR_SOGGETTO_PROGETTO;
		}


		public void setBanBen_PROGR_SOGGETTO_PROGETTO(String banBen_PROGR_SOGGETTO_PROGETTO) {
			this.banBen_PROGR_SOGGETTO_PROGETTO = banBen_PROGR_SOGGETTO_PROGETTO;
		}




		@Override
		public String toString() {
			return "SaveSchedaClienteAllVO [wtfImSaving=" + wtfImSaving + ", idSoggetto=" + idSoggetto + ", idUtente="
					+ idUtente + ", staAz_idCurrentRecord=" + staAz_idCurrentRecord + ", staAz_statoAzienda="
					+ staAz_statoAzienda + ", staAz_dataInizio=" + staAz_dataInizio + ", staAz_dataFine="
					+ staAz_dataFine + ", staCre_idCurrentRecord=" + staCre_idCurrentRecord + ", staCre_stato="
					+ staCre_stato + ", staCre_dataMod=" + staCre_dataMod + ", staCre_PROGR_SOGGETTO_PROGETTO="
					+ staCre_PROGR_SOGGETTO_PROGETTO + ", rat_idCurrentRecord=" + rat_idCurrentRecord
					+ ", rat_idRating=" + rat_idRating + ", rat_dataClassificazione=" + rat_dataClassificazione
					+ ", banBen_idCurrentRecord=" + banBen_idCurrentRecord + ", banBen_banca=" + banBen_banca
					+ ", banBen_dataModifica=" + banBen_dataModifica + ", benBen_motivazione=" + benBen_motivazione
					+ ", banBen_soggettoTerzo=" + banBen_soggettoTerzo + ", banBen_PROGR_SOGGETTO_PROGETTO="
					+ banBen_PROGR_SOGGETTO_PROGETTO + ", esc_dtRichEscussione=" + esc_dtRichEscussione
					+ ", esc_numProtocolloRich=" + esc_numProtocolloRich + ", esc_descTipoEscussione="
					+ esc_descTipoEscussione + ", esc_descStatoEscussione=" + esc_descStatoEscussione
					+ ", esc_dtInizioValidita=" + esc_dtInizioValidita + ", esc_dtNotifica=" + esc_dtNotifica
					+ ", esc_impApprovato=" + esc_impApprovato + ", esc_impRichiesto=" + esc_impRichiesto
					+ ", esc_causaleBonifico=" + esc_causaleBonifico + ", esc_ibanBonifico=" + esc_ibanBonifico
					+ ", esc_descBanca=" + esc_descBanca + ", esc_note=" + esc_note + "]";
		}


		

}
