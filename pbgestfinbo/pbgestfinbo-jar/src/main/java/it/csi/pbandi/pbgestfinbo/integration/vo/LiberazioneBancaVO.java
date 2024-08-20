/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;
import java.util.List;

public class LiberazioneBancaVO {
	
		private String idUtente;
		private String idProgetto;

		private String idCurrentRecord;
		private Long idModalitaAgevolazione;
		private Date dataLiberazione;
		private String motivazione;
		private String bancaLiberata;
		private String note;
		
		private Date storData;
		private String storIstruttore;
		
		private List<String> motivazioni;
		
		


		public LiberazioneBancaVO(String idUtente, String idProgetto, String idCurrentRecord,
				Long idModalitaAgevolazione, Date dataLiberazione, String motivazione, String bancaLiberata,
				String note, Date storData, String storIstruttore, List<String> motivazioni) {
			this.idUtente = idUtente;
			this.idProgetto = idProgetto;
			this.idCurrentRecord = idCurrentRecord;
			this.idModalitaAgevolazione = idModalitaAgevolazione;
			this.dataLiberazione = dataLiberazione;
			this.motivazione = motivazione;
			this.bancaLiberata = bancaLiberata;
			this.note = note;
			this.storData = storData;
			this.storIstruttore = storIstruttore;
			this.motivazioni = motivazioni;
		}



		public LiberazioneBancaVO() {
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



		public Date getDataLiberazione() {
			return dataLiberazione;
		}



		public void setDataLiberazione(Date dataLiberazione) {
			this.dataLiberazione = dataLiberazione;
		}



		public String getMotivazione() {
			return motivazione;
		}



		public void setMotivazione(String motivazione) {
			this.motivazione = motivazione;
		}



		public String getBancaLiberata() {
			return bancaLiberata;
		}



		public void setBancaLiberata(String bancaLiberata) {
			this.bancaLiberata = bancaLiberata;
		}



		public String getNote() {
			return note;
		}



		public void setNote(String note) {
			this.note = note;
		}



		public Date getStorData() {
			return storData;
		}



		public void setStorData(Date storData) {
			this.storData = storData;
		}



		public String getStorIstruttore() {
			return storIstruttore;
		}



		public void setStorIstruttore(String storIstruttore) {
			this.storIstruttore = storIstruttore;
		}



		public List<String> getMotivazioni() {
			return motivazioni;
		}



		public void setMotivazioni(List<String> motivazioni) {
			this.motivazioni = motivazioni;
		}



		public Long getIdModalitaAgevolazione() {
			return idModalitaAgevolazione;
		}



		public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
			this.idModalitaAgevolazione = idModalitaAgevolazione;
		}



		@Override
		public String toString() {
			return "LiberazioneBancaVO [idUtente=" + idUtente + ", idProgetto=" + idProgetto + ", idCurrentRecord="
					+ idCurrentRecord + ", idModalitaAgevolazione=" + idModalitaAgevolazione + ", dataLiberazione="
					+ dataLiberazione + ", motivazione=" + motivazione + ", bancaLiberata=" + bancaLiberata + ", note="
					+ note + ", storData=" + storData + ", storIstruttore=" + storIstruttore + ", motivazioni="
					+ motivazioni + "]";
		}


		
		

}
