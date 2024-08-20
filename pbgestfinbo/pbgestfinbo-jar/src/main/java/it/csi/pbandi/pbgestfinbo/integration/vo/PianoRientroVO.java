/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

public class PianoRientroVO {
	
		private String operation;
	
		private BigDecimal idUtente;
		private BigDecimal idProgetto;

		private BigDecimal idCurrentRecord;
		private Long idModalitaAgevolazione;
		private BigDecimal idEsito;
		private String esito;
		private Date dataEsito;
		private String utenteModifica;
		private Date dataProposta;
		private BigDecimal importoCapitale;
		private BigDecimal importoAgevolazione;
		private BigDecimal idRecupero;
		private String recupero;
		private String note;
		private Date dataInserimento;
		
		private String ID_PROGETTO;
		private String DT_INIZIO_VALIDITA;
		private String DT_FINE_VALIDITA;
		private String ID_UTENTE_INS;
		private String ID_UTENTE_AGG;
		private String DT_INSERIMENTO;
		private String DT_AGGIORNAMENTO;

		private List<String> esiti;
		private List<String> recuperi;
		
		
		



		public PianoRientroVO(String operation, BigDecimal idUtente, BigDecimal idProgetto, BigDecimal idCurrentRecord,
				Long idModalitaAgevolazione, BigDecimal idEsito, String esito, Date dataEsito, String utenteModifica,
				Date dataProposta, BigDecimal importoCapitale, BigDecimal importoAgevolazione, BigDecimal idRecupero,
				String recupero, String note, Date dataInserimento, String iD_PROGETTO, String dT_INIZIO_VALIDITA,
				String dT_FINE_VALIDITA, String iD_UTENTE_INS, String iD_UTENTE_AGG, String dT_INSERIMENTO,
				String dT_AGGIORNAMENTO, List<String> esiti, List<String> recuperi) {
			this.operation = operation;
			this.idUtente = idUtente;
			this.idProgetto = idProgetto;
			this.idCurrentRecord = idCurrentRecord;
			this.idModalitaAgevolazione = idModalitaAgevolazione;
			this.idEsito = idEsito;
			this.esito = esito;
			this.dataEsito = dataEsito;
			this.utenteModifica = utenteModifica;
			this.dataProposta = dataProposta;
			this.importoCapitale = importoCapitale;
			this.importoAgevolazione = importoAgevolazione;
			this.idRecupero = idRecupero;
			this.recupero = recupero;
			this.note = note;
			this.dataInserimento = dataInserimento;
			ID_PROGETTO = iD_PROGETTO;
			DT_INIZIO_VALIDITA = dT_INIZIO_VALIDITA;
			DT_FINE_VALIDITA = dT_FINE_VALIDITA;
			ID_UTENTE_INS = iD_UTENTE_INS;
			ID_UTENTE_AGG = iD_UTENTE_AGG;
			DT_INSERIMENTO = dT_INSERIMENTO;
			DT_AGGIORNAMENTO = dT_AGGIORNAMENTO;
			this.esiti = esiti;
			this.recuperi = recuperi;
		}



		public PianoRientroVO() {
		}



		public String getOperation() {
			return operation;
		}



		public void setOperation(String operation) {
			this.operation = operation;
		}



		public BigDecimal getIdUtente() {
			return idUtente;
		}



		public void setIdUtente(BigDecimal idUtente) {
			this.idUtente = idUtente;
		}



		public BigDecimal getIdProgetto() {
			return idProgetto;
		}



		public void setIdProgetto(BigDecimal idProgetto) {
			this.idProgetto = idProgetto;
		}



		public BigDecimal getIdCurrentRecord() {
			return idCurrentRecord;
		}



		public void setIdCurrentRecord(BigDecimal idCurrentRecord) {
			this.idCurrentRecord = idCurrentRecord;
		}



		public BigDecimal getIdEsito() {
			return idEsito;
		}



		public void setIdEsito(BigDecimal idEsito) {
			this.idEsito = idEsito;
		}



		public String getEsito() {
			return esito;
		}



		public void setEsito(String esito) {
			this.esito = esito;
		}



		public Date getDataEsito() {
			return dataEsito;
		}



		public void setDataEsito(Date dataEsito) {
			this.dataEsito = dataEsito;
		}



		public String getUtenteModifica() {
			return utenteModifica;
		}



		public void setUtenteModifica(String utenteModifica) {
			this.utenteModifica = utenteModifica;
		}



		public Date getDataProposta() {
			return dataProposta;
		}



		public void setDataProposta(Date dataProposta) {
			this.dataProposta = dataProposta;
		}



		public BigDecimal getImportoCapitale() {
			return importoCapitale;
		}



		public void setImportoCapitale(BigDecimal importoCapitale) {
			this.importoCapitale = importoCapitale;
		}



		public BigDecimal getImportoAgevolazione() {
			return importoAgevolazione;
		}



		public void setImportoAgevolazione(BigDecimal importoAgevolazione) {
			this.importoAgevolazione = importoAgevolazione;
		}



		public BigDecimal getIdRecupero() {
			return idRecupero;
		}



		public void setIdRecupero(BigDecimal idRecupero) {
			this.idRecupero = idRecupero;
		}



		public String getRecupero() {
			return recupero;
		}



		public void setRecupero(String recupero) {
			this.recupero = recupero;
		}



		public String getNote() {
			return note;
		}



		public void setNote(String note) {
			this.note = note;
		}



		public Date getDataInserimento() {
			return dataInserimento;
		}



		public void setDataInserimento(Date dataInserimento) {
			this.dataInserimento = dataInserimento;
		}



		public String getID_PROGETTO() {
			return ID_PROGETTO;
		}



		public void setID_PROGETTO(String iD_PROGETTO) {
			ID_PROGETTO = iD_PROGETTO;
		}



		public String getDT_INIZIO_VALIDITA() {
			return DT_INIZIO_VALIDITA;
		}



		public void setDT_INIZIO_VALIDITA(String dT_INIZIO_VALIDITA) {
			DT_INIZIO_VALIDITA = dT_INIZIO_VALIDITA;
		}



		public String getDT_FINE_VALIDITA() {
			return DT_FINE_VALIDITA;
		}



		public void setDT_FINE_VALIDITA(String dT_FINE_VALIDITA) {
			DT_FINE_VALIDITA = dT_FINE_VALIDITA;
		}



		public String getID_UTENTE_INS() {
			return ID_UTENTE_INS;
		}



		public void setID_UTENTE_INS(String iD_UTENTE_INS) {
			ID_UTENTE_INS = iD_UTENTE_INS;
		}



		public String getID_UTENTE_AGG() {
			return ID_UTENTE_AGG;
		}



		public void setID_UTENTE_AGG(String iD_UTENTE_AGG) {
			ID_UTENTE_AGG = iD_UTENTE_AGG;
		}



		public String getDT_INSERIMENTO() {
			return DT_INSERIMENTO;
		}



		public void setDT_INSERIMENTO(String dT_INSERIMENTO) {
			DT_INSERIMENTO = dT_INSERIMENTO;
		}



		public String getDT_AGGIORNAMENTO() {
			return DT_AGGIORNAMENTO;
		}



		public void setDT_AGGIORNAMENTO(String dT_AGGIORNAMENTO) {
			DT_AGGIORNAMENTO = dT_AGGIORNAMENTO;
		}



		public List<String> getEsiti() {
			return esiti;
		}



		public void setEsiti(List<String> esiti) {
			this.esiti = esiti;
		}



		public List<String> getRecuperi() {
			return recuperi;
		}



		public void setRecuperi(List<String> recuperi) {
			this.recuperi = recuperi;
		}



		public Long getIdModalitaAgevolazione() {
			return idModalitaAgevolazione;
		}



		public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
			this.idModalitaAgevolazione = idModalitaAgevolazione;
		}



		@Override
		public String toString() {
			return "PianoRientroVO [operation=" + operation + ", idUtente=" + idUtente + ", idProgetto=" + idProgetto
					+ ", idCurrentRecord=" + idCurrentRecord + ", idModalitaAgevolazione=" + idModalitaAgevolazione
					+ ", idEsito=" + idEsito + ", esito=" + esito + ", dataEsito=" + dataEsito + ", utenteModifica="
					+ utenteModifica + ", dataProposta=" + dataProposta + ", importoCapitale=" + importoCapitale
					+ ", importoAgevolazione=" + importoAgevolazione + ", idRecupero=" + idRecupero + ", recupero="
					+ recupero + ", note=" + note + ", dataInserimento=" + dataInserimento + ", ID_PROGETTO="
					+ ID_PROGETTO + ", DT_INIZIO_VALIDITA=" + DT_INIZIO_VALIDITA + ", DT_FINE_VALIDITA="
					+ DT_FINE_VALIDITA + ", ID_UTENTE_INS=" + ID_UTENTE_INS + ", ID_UTENTE_AGG=" + ID_UTENTE_AGG
					+ ", DT_INSERIMENTO=" + DT_INSERIMENTO + ", DT_AGGIORNAMENTO=" + DT_AGGIORNAMENTO + ", esiti="
					+ esiti + ", recuperi=" + recuperi + "]";
		}



		
		
		
		
		
		
		
		
		
		
		

}
