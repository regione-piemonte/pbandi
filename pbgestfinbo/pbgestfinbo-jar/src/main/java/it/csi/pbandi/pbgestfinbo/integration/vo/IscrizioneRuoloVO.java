/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;

public class IscrizioneRuoloVO {
	
		private String idUtente;
		private String idProgetto;

		private String idCurrentRecord;
		private Long idModalitaAgevolazione;
		private Date dataRichiestaIscrizione;
		private String numProtocollo;
		private Date dataRichiestaDiscarico;
		private String numProtocolloDiscarico;
		private Date dataIscrizioneRuolo;
		private Date dataDiscarico;
		private String numProtoDiscReg;
		private Date dataRichiestaSospensione;
		private String numProtoSosp;
		private String capitaleRuolo;
		private String agevolazioneRuolo;
		private Date dataIscrizione;
		private String numProtoReg;
		private String tipoPagamento;
		private String note;
		
		private Date stor_dataInizio;
		private Date stor_dataFine;
		private Date stor_dataInserimento;
		private String istruttore;
		
		
		



		public IscrizioneRuoloVO(String idUtente, String idProgetto, String idCurrentRecord,
				Long idModalitaAgevolazione, Date dataRichiestaIscrizione, String numProtocollo,
				Date dataRichiestaDiscarico, String numProtocolloDiscarico, Date dataIscrizioneRuolo,
				Date dataDiscarico, String numProtoDiscReg, Date dataRichiestaSospensione, String numProtoSosp,
				String capitaleRuolo, String agevolazioneRuolo, Date dataIscrizione, String numProtoReg,
				String tipoPagamento, String note, Date stor_dataInizio, Date stor_dataFine, Date stor_dataInserimento,
				String istruttore) {
			this.idUtente = idUtente;
			this.idProgetto = idProgetto;
			this.idCurrentRecord = idCurrentRecord;
			this.idModalitaAgevolazione = idModalitaAgevolazione;
			this.dataRichiestaIscrizione = dataRichiestaIscrizione;
			this.numProtocollo = numProtocollo;
			this.dataRichiestaDiscarico = dataRichiestaDiscarico;
			this.numProtocolloDiscarico = numProtocolloDiscarico;
			this.dataIscrizioneRuolo = dataIscrizioneRuolo;
			this.dataDiscarico = dataDiscarico;
			this.numProtoDiscReg = numProtoDiscReg;
			this.dataRichiestaSospensione = dataRichiestaSospensione;
			this.numProtoSosp = numProtoSosp;
			this.capitaleRuolo = capitaleRuolo;
			this.agevolazioneRuolo = agevolazioneRuolo;
			this.dataIscrizione = dataIscrizione;
			this.numProtoReg = numProtoReg;
			this.tipoPagamento = tipoPagamento;
			this.note = note;
			this.stor_dataInizio = stor_dataInizio;
			this.stor_dataFine = stor_dataFine;
			this.stor_dataInserimento = stor_dataInserimento;
			this.istruttore = istruttore;
		}



		public IscrizioneRuoloVO() {
		}



		public String getIdUtente() {
			return idUtente;
		}



		public void setIdUtente(String idUtente) {
			this.idUtente = idUtente;
		}



		public String getIdProgetto() {
			return idProgetto;
		}



		public void setIdProgetto(String idProgetto) {
			this.idProgetto = idProgetto;
		}



		public String getIdCurrentRecord() {
			return idCurrentRecord;
		}



		public void setIdCurrentRecord(String idCurrentRecord) {
			this.idCurrentRecord = idCurrentRecord;
		}



		public Date getDataRichiestaIscrizione() {
			return dataRichiestaIscrizione;
		}



		public void setDataRichiestaIscrizione(Date dataRichiestaIscrizione) {
			this.dataRichiestaIscrizione = dataRichiestaIscrizione;
		}



		public String getNumProtocollo() {
			return numProtocollo;
		}



		public void setNumProtocollo(String numProtocollo) {
			this.numProtocollo = numProtocollo;
		}



		public Date getDataRichiestaDiscarico() {
			return dataRichiestaDiscarico;
		}



		public void setDataRichiestaDiscarico(Date dataRichiestaDiscarico) {
			this.dataRichiestaDiscarico = dataRichiestaDiscarico;
		}



		public String getNumProtocolloDiscarico() {
			return numProtocolloDiscarico;
		}



		public void setNumProtocolloDiscarico(String numProtocolloDiscarico) {
			this.numProtocolloDiscarico = numProtocolloDiscarico;
		}



		public Date getDataIscrizioneRuolo() {
			return dataIscrizioneRuolo;
		}



		public void setDataIscrizioneRuolo(Date dataIscrizioneRuolo) {
			this.dataIscrizioneRuolo = dataIscrizioneRuolo;
		}



		public Date getDataDiscarico() {
			return dataDiscarico;
		}



		public void setDataDiscarico(Date dataDiscarico) {
			this.dataDiscarico = dataDiscarico;
		}



		public String getNumProtoDiscReg() {
			return numProtoDiscReg;
		}



		public void setNumProtoDiscReg(String numProtoDiscReg) {
			this.numProtoDiscReg = numProtoDiscReg;
		}



		public Date getDataRichiestaSospensione() {
			return dataRichiestaSospensione;
		}



		public void setDataRichiestaSospensione(Date dataRichiestaSospensione) {
			this.dataRichiestaSospensione = dataRichiestaSospensione;
		}



		public String getNumProtoSosp() {
			return numProtoSosp;
		}



		public void setNumProtoSosp(String numProtoSosp) {
			this.numProtoSosp = numProtoSosp;
		}



		public String getCapitaleRuolo() {
			return capitaleRuolo;
		}



		public void setCapitaleRuolo(String capitaleRuolo) {
			this.capitaleRuolo = capitaleRuolo;
		}



		public String getAgevolazioneRuolo() {
			return agevolazioneRuolo;
		}



		public void setAgevolazioneRuolo(String agevolazioneRuolo) {
			this.agevolazioneRuolo = agevolazioneRuolo;
		}



		public Date getDataIscrizione() {
			return dataIscrizione;
		}



		public void setDataIscrizione(Date dataIscrizione) {
			this.dataIscrizione = dataIscrizione;
		}



		public String getNumProtoReg() {
			return numProtoReg;
		}



		public void setNumProtoReg(String numProtoReg) {
			this.numProtoReg = numProtoReg;
		}



		public String getTipoPagamento() {
			return tipoPagamento;
		}



		public void setTipoPagamento(String tipoPagamento) {
			this.tipoPagamento = tipoPagamento;
		}



		public String getNote() {
			return note;
		}



		public void setNote(String note) {
			this.note = note;
		}



		public Date getStor_dataInizio() {
			return stor_dataInizio;
		}



		public void setStor_dataInizio(Date stor_dataInizio) {
			this.stor_dataInizio = stor_dataInizio;
		}



		public Date getStor_dataFine() {
			return stor_dataFine;
		}



		public void setStor_dataFine(Date stor_dataFine) {
			this.stor_dataFine = stor_dataFine;
		}



		public Date getStor_dataInserimento() {
			return stor_dataInserimento;
		}



		public void setStor_dataInserimento(Date stor_dataInserimento) {
			this.stor_dataInserimento = stor_dataInserimento;
		}



		public String getIstruttore() {
			return istruttore;
		}



		public void setIstruttore(String istruttore) {
			this.istruttore = istruttore;
		}



		public Long getIdModalitaAgevolazione() {
			return idModalitaAgevolazione;
		}



		public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
			this.idModalitaAgevolazione = idModalitaAgevolazione;
		}



		@Override
		public String toString() {
			return "IscrizioneRuoloVO [idUtente=" + idUtente + ", idProgetto=" + idProgetto + ", idCurrentRecord="
					+ idCurrentRecord + ", idModalitaAgevolazione=" + idModalitaAgevolazione
					+ ", dataRichiestaIscrizione=" + dataRichiestaIscrizione + ", numProtocollo=" + numProtocollo
					+ ", dataRichiestaDiscarico=" + dataRichiestaDiscarico + ", numProtocolloDiscarico="
					+ numProtocolloDiscarico + ", dataIscrizioneRuolo=" + dataIscrizioneRuolo + ", dataDiscarico="
					+ dataDiscarico + ", numProtoDiscReg=" + numProtoDiscReg + ", dataRichiestaSospensione="
					+ dataRichiestaSospensione + ", numProtoSosp=" + numProtoSosp + ", capitaleRuolo=" + capitaleRuolo
					+ ", agevolazioneRuolo=" + agevolazioneRuolo + ", dataIscrizione=" + dataIscrizione
					+ ", numProtoReg=" + numProtoReg + ", tipoPagamento=" + tipoPagamento + ", note=" + note
					+ ", stor_dataInizio=" + stor_dataInizio + ", stor_dataFine=" + stor_dataFine
					+ ", stor_dataInserimento=" + stor_dataInserimento + ", istruttore=" + istruttore + "]";
		}


		

}
