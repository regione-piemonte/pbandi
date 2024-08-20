/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;

public class LiberazioneGaranteVO {
	
		private String operation;
		
		private String idProgetto;
		private String idUtente;
	
		private BigDecimal idLibGar;
		private BigDecimal idModalitaAgevolazione;
		private String garanteLiberato;
		private Date dataLiberazione;
		private String utenteModifica;
		private BigDecimal importoLiberazione;
		private String note;
		
		private Date story_dataIns;
		private Date story_dataAgg;
		
		private String fineValidita;

		
		public LiberazioneGaranteVO(String operation, String idProgetto, String idUtente, BigDecimal idLibGar,
				BigDecimal idModalitaAgevolazione, String garanteLiberato, Date dataLiberazione, String utenteModifica,
				BigDecimal importoLiberazione, String note, Date story_dataIns, Date story_dataAgg,
				String fineValidita) {
			this.operation = operation;
			this.idProgetto = idProgetto;
			this.idUtente = idUtente;
			this.idLibGar = idLibGar;
			this.idModalitaAgevolazione = idModalitaAgevolazione;
			this.garanteLiberato = garanteLiberato;
			this.dataLiberazione = dataLiberazione;
			this.utenteModifica = utenteModifica;
			this.importoLiberazione = importoLiberazione;
			this.note = note;
			this.story_dataIns = story_dataIns;
			this.story_dataAgg = story_dataAgg;
			this.fineValidita = fineValidita;
		}

		public LiberazioneGaranteVO() {
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public String getIdProgetto() {
			return idProgetto;
		}

		public void setIdProgetto(String idProgetto) {
			this.idProgetto = idProgetto;
		}

		public String getIdUtente() {
			return idUtente;
		}

		public void setIdUtente(String idUtente) {
			this.idUtente = idUtente;
		}

		public BigDecimal getIdLibGar() {
			return idLibGar;
		}

		public void setIdLibGar(BigDecimal idLibGar) {
			this.idLibGar = idLibGar;
		}

		public String getGaranteLiberato() {
			return garanteLiberato;
		}

		public void setGaranteLiberato(String garanteLiberato) {
			this.garanteLiberato = garanteLiberato;
		}

		public Date getDataLiberazione() {
			return dataLiberazione;
		}

		public void setDataLiberazione(Date dataLiberazione) {
			this.dataLiberazione = dataLiberazione;
		}

		public String getUtenteModifica() {
			return utenteModifica;
		}

		public void setUtenteModifica(String utenteModifica) {
			this.utenteModifica = utenteModifica;
		}

		public BigDecimal getImportoLiberazione() {
			return importoLiberazione;
		}

		public void setImportoLiberazione(BigDecimal importoLiberazione) {
			this.importoLiberazione = importoLiberazione;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public Date getStory_dataIns() {
			return story_dataIns;
		}

		public void setStory_dataIns(Date story_dataIns) {
			this.story_dataIns = story_dataIns;
		}

		public Date getStory_dataAgg() {
			return story_dataAgg;
		}

		public void setStory_dataAgg(Date story_dataAgg) {
			this.story_dataAgg = story_dataAgg;
		}

		public String getFineValidita() {
			return fineValidita;
		}

		public void setFineValidita(String fineValidita) {
			this.fineValidita = fineValidita;
		}

		public BigDecimal getIdModalitaAgevolazione() {
			return idModalitaAgevolazione;
		}

		public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
			this.idModalitaAgevolazione = idModalitaAgevolazione;
		}

		@Override
		public String toString() {
			return "LiberazioneGaranteVO [operation=" + operation + ", idProgetto=" + idProgetto + ", idUtente="
					+ idUtente + ", idLibGar=" + idLibGar + ", idModalitaAgevolazione=" + idModalitaAgevolazione
					+ ", garanteLiberato=" + garanteLiberato + ", dataLiberazione=" + dataLiberazione
					+ ", utenteModifica=" + utenteModifica + ", importoLiberazione=" + importoLiberazione + ", note="
					+ note + ", story_dataIns=" + story_dataIns + ", story_dataAgg=" + story_dataAgg + ", fineValidita="
					+ fineValidita + "]";
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

}
