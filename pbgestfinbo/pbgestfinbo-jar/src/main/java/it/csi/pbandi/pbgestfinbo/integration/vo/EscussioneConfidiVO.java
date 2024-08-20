/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EscussioneConfidiVO {
	
		private String idUtente;
		private String idProgetto;

		private String idCurrentRecord;
		private Long idModalitaAgevolazione;
		private String nominativo;
		private Date dataEscussione;
		private Date dataPagamento;
		private BigDecimal idGaranzia;
		private String garanzia;
		private BigDecimal percGaranzia;
		private String note;
		
		private Date dataInserimento;
		private String istruttore;
		
		private List<String> listGaranzie;

		



		public EscussioneConfidiVO(String idUtente, String idProgetto, String idCurrentRecord,
				Long idModalitaAgevolazione, String nominativo, Date dataEscussione, Date dataPagamento,
				BigDecimal idGaranzia, String garanzia, BigDecimal percGaranzia, String note, Date dataInserimento,
				String istruttore, List<String> listGaranzie) {
			this.idUtente = idUtente;
			this.idProgetto = idProgetto;
			this.idCurrentRecord = idCurrentRecord;
			this.idModalitaAgevolazione = idModalitaAgevolazione;
			this.nominativo = nominativo;
			this.dataEscussione = dataEscussione;
			this.dataPagamento = dataPagamento;
			this.idGaranzia = idGaranzia;
			this.garanzia = garanzia;
			this.percGaranzia = percGaranzia;
			this.note = note;
			this.dataInserimento = dataInserimento;
			this.istruttore = istruttore;
			this.listGaranzie = listGaranzie;
		}



		public EscussioneConfidiVO() {
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



		public String getNominativo() {
			return nominativo;
		}



		public void setNominativo(String nominativo) {
			this.nominativo = nominativo;
		}



		public Date getDataEscussione() {
			return dataEscussione;
		}



		public void setDataEscussione(Date dataEscussione) {
			this.dataEscussione = dataEscussione;
		}



		public Date getDataPagamento() {
			return dataPagamento;
		}



		public void setDataPagamento(Date dataPagamento) {
			this.dataPagamento = dataPagamento;
		}



		public BigDecimal getIdGaranzia() {
			return idGaranzia;
		}



		public void setIdGaranzia(BigDecimal idGaranzia) {
			this.idGaranzia = idGaranzia;
		}



		public String getGaranzia() {
			return garanzia;
		}



		public void setGaranzia(String garanzia) {
			this.garanzia = garanzia;
		}



		public BigDecimal getPercGaranzia() {
			return percGaranzia;
		}



		public void setPercGaranzia(BigDecimal percGaranzia) {
			this.percGaranzia = percGaranzia;
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



		public String getIstruttore() {
			return istruttore;
		}



		public void setIstruttore(String istruttore) {
			this.istruttore = istruttore;
		}



		public List<String> getListGaranzie() {
			return listGaranzie;
		}



		public void setListGaranzie(List<String> listGaranzie) {
			this.listGaranzie = listGaranzie;
		}



		public Long getIdModalitaAgevolazione() {
			return idModalitaAgevolazione;
		}



		public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
			this.idModalitaAgevolazione = idModalitaAgevolazione;
		}



		@Override
		public String toString() {
			return "EscussioneConfidiVO [idUtente=" + idUtente + ", idProgetto=" + idProgetto + ", idCurrentRecord="
					+ idCurrentRecord + ", idModalitaAgevolazione=" + idModalitaAgevolazione + ", nominativo="
					+ nominativo + ", dataEscussione=" + dataEscussione + ", dataPagamento=" + dataPagamento
					+ ", idGaranzia=" + idGaranzia + ", garanzia=" + garanzia + ", percGaranzia=" + percGaranzia
					+ ", note=" + note + ", dataInserimento=" + dataInserimento + ", istruttore=" + istruttore
					+ ", listGaranzie=" + listGaranzie + "]";
		}



		
		
		
		
		

		
		
		
		
		
		
		
		

}
