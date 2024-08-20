/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CessioneQuotaVO {
	
		private Long idUtente;
		private Long idProgetto;

		private String idCurrentRecord;
		private Long idModalitaAgevolazione;
		private BigDecimal impCessQuotaCap;
		private BigDecimal impCessOneri;
		private BigDecimal impCessInterMora;
		private BigDecimal impTotCess;
		private Date dataCessione;
		private BigDecimal corrispettivoCess;
		private String nominativoCess;
		private String statoCess;
		private String note;
		
		private String stor_dataInizio;
		private String stor_dataFine;
		private String stor_dataInserimento;
		private String stor_istruttore;
		
		private List<String> listStati;

		public CessioneQuotaVO(Long idUtente, Long idProgetto, String idCurrentRecord, Long idModalitaAgevolazione,
				BigDecimal impCessQuotaCap, BigDecimal impCessOneri, BigDecimal impCessInterMora, BigDecimal impTotCess,
				Date dataCessione, BigDecimal corrispettivoCess, String nominativoCess, String statoCess, String note,
				String stor_dataInizio, String stor_dataFine, String stor_dataInserimento, String stor_istruttore,
				List<String> listStati) {
			this.idUtente = idUtente;
			this.idProgetto = idProgetto;
			this.idCurrentRecord = idCurrentRecord;
			this.idModalitaAgevolazione = idModalitaAgevolazione;
			this.impCessQuotaCap = impCessQuotaCap;
			this.impCessOneri = impCessOneri;
			this.impCessInterMora = impCessInterMora;
			this.impTotCess = impTotCess;
			this.dataCessione = dataCessione;
			this.corrispettivoCess = corrispettivoCess;
			this.nominativoCess = nominativoCess;
			this.statoCess = statoCess;
			this.note = note;
			this.stor_dataInizio = stor_dataInizio;
			this.stor_dataFine = stor_dataFine;
			this.stor_dataInserimento = stor_dataInserimento;
			this.stor_istruttore = stor_istruttore;
			this.listStati = listStati;
		}


		public CessioneQuotaVO() {
		}


		public Long getIdUtente() {
			return idUtente;
		}


		public void setIdUtente(Long idUtente) {
			this.idUtente = idUtente;
		}


		public Long getIdProgetto() {
			return idProgetto;
		}


		public void setIdProgetto(Long idProgetto) {
			this.idProgetto = idProgetto;
		}


		public String getIdCurrentRecord() {
			return idCurrentRecord;
		}


		public void setIdCurrentRecord(String idCurrentRecord) {
			this.idCurrentRecord = idCurrentRecord;
		}


		public BigDecimal getImpCessQuotaCap() {
			return impCessQuotaCap;
		}


		public void setImpCessQuotaCap(BigDecimal impCessQuotaCap) {
			this.impCessQuotaCap = impCessQuotaCap;
		}


		public BigDecimal getImpCessOneri() {
			return impCessOneri;
		}


		public void setImpCessOneri(BigDecimal impCessOneri) {
			this.impCessOneri = impCessOneri;
		}


		public BigDecimal getImpCessInterMora() {
			return impCessInterMora;
		}


		public void setImpCessInterMora(BigDecimal impCessInterMora) {
			this.impCessInterMora = impCessInterMora;
		}


		public BigDecimal getImpTotCess() {
			return impTotCess;
		}


		public void setImpTotCess(BigDecimal impTotCess) {
			this.impTotCess = impTotCess;
		}


		public Date getDataCessione() {
			return dataCessione;
		}


		public void setDataCessione(Date dataCessione) {
			this.dataCessione = dataCessione;
		}


		public BigDecimal getCorrispettivoCess() {
			return corrispettivoCess;
		}


		public void setCorrispettivoCess(BigDecimal corrispettivoCess) {
			this.corrispettivoCess = corrispettivoCess;
		}


		public String getNominativoCess() {
			return nominativoCess;
		}


		public void setNominativoCess(String nominativoCess) {
			this.nominativoCess = nominativoCess;
		}


		public String getStatoCess() {
			return statoCess;
		}


		public void setStatoCess(String statoCess) {
			this.statoCess = statoCess;
		}


		public String getNote() {
			return note;
		}


		public void setNote(String note) {
			this.note = note;
		}


		public String getStor_dataInizio() {
			return stor_dataInizio;
		}


		public void setStor_dataInizio(String stor_dataInizio) {
			this.stor_dataInizio = stor_dataInizio;
		}


		public String getStor_dataFine() {
			return stor_dataFine;
		}


		public void setStor_dataFine(String stor_dataFine) {
			this.stor_dataFine = stor_dataFine;
		}


		public String getStor_dataInserimento() {
			return stor_dataInserimento;
		}


		public void setStor_dataInserimento(String stor_dataInserimento) {
			this.stor_dataInserimento = stor_dataInserimento;
		}


		public String getStor_istruttore() {
			return stor_istruttore;
		}


		public void setStor_istruttore(String stor_istruttore) {
			this.stor_istruttore = stor_istruttore;
		}


		public List<String> getListStati() {
			return listStati;
		}


		public void setListStati(List<String> listStati) {
			this.listStati = listStati;
		}


		public Long getIdModalitaAgevolazione() {
			return idModalitaAgevolazione;
		}


		public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
			this.idModalitaAgevolazione = idModalitaAgevolazione;
		}


		@Override
		public String toString() {
			return "CessioneQuotaVO [idUtente=" + idUtente + ", idProgetto=" + idProgetto + ", idCurrentRecord="
					+ idCurrentRecord + ", idModalitaAgevolazione=" + idModalitaAgevolazione + ", impCessQuotaCap="
					+ impCessQuotaCap + ", impCessOneri=" + impCessOneri + ", impCessInterMora=" + impCessInterMora
					+ ", impTotCess=" + impTotCess + ", dataCessione=" + dataCessione + ", corrispettivoCess="
					+ corrispettivoCess + ", nominativoCess=" + nominativoCess + ", statoCess=" + statoCess + ", note="
					+ note + ", stor_dataInizio=" + stor_dataInizio + ", stor_dataFine=" + stor_dataFine
					+ ", stor_dataInserimento=" + stor_dataInserimento + ", stor_istruttore=" + stor_istruttore
					+ ", listStati=" + listStati + "]";
		}

}
