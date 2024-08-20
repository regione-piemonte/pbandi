/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.sql.Date;

public class AzioneRecuperoBancaVO {
	
	
	private Long idAzioneRecupero;		// ID_AZIONI_RECUPERO_BANCA
	private Long idProgetto;			// ID_PROGETTO
	private Long idAttivitaAzione;		// ID_ATTIVITA_AZIONE
	private Date dataAzione;			// DT_AZIONE
	private Long numProtocollo;			// NUM_PROTOCOLLO
	private String note;				// NOTE
	private Date dataInizioValidita;	// DT_INIZIO_VALIDITA
	private Date dataFineValidita;		// DT_FINE_VALIDITA
	private Date dataInserimento;		// DT_INSERIMENTO
	private Date dataAggiornamento;		// DT_AGGIORNAMENTO
	private Long idUtenteIns;			// ID_UTENTE_INS
	private Long idUtenteAgg;			// ID_UTENTE_AGG
	
	
	public Long getIdAzioneRecupero() {
		return idAzioneRecupero;
	}
	public void setIdAzioneRecupero(Long idAzioneRecupero) {
		this.idAzioneRecupero = idAzioneRecupero;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdAttivitaAzione() {
		return idAttivitaAzione;
	}
	public void setIdAttivitaAzione(Long idAttivitaAzione) {
		this.idAttivitaAzione = idAttivitaAzione;
	}
	public Date getDataAzione() {
		return dataAzione;
	}
	public void setDataAzione(Date dataAzione) {
		this.dataAzione = dataAzione;
	}
	public Long getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(Long numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public Long getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	
	
	


}
