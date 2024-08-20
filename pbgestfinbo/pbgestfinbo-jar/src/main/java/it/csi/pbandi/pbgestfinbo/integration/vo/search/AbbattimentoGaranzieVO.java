/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.sql.Date;

public class AbbattimentoGaranzieVO {
			
	private Long idAbbattimGaranzie; 		//	Id_abbattim_garanzie
	private Long idProgetto;				//	id_progetto
	private Long idAttivitaGaranzie; 		//	id_attivita_garanzie
	private Date dataAbbattimGaranzie; 		//	dt_ abbattim_garanzie
	private Long impIniziale; 				//	imp_iniziale
	private Long impLiberato;				//	imp_liberato
	private Long impNuovo;				//	imp_nuovo
	private String note;					//	note
	private Date dataInizioValidita; 		//	DT_INIZIO_VALIDITA
	private Date dataFineValidita;			//	DT_FINE_VALIDITA
	private Date dataInserimento;			//	DT_INSERIMENTO
	private Date dataAggiornamento;			//	DT_AGGIORNAMENTO
	private Long idUtenteIns;				//	ID_UTENTE_INS
	private Long idUtenteAgg;				//	ID_UTENTE_AGG
	
	
	public Long getIdAbbattimGaranzie() {
		return idAbbattimGaranzie;
	}
	public void setIdAbbattimGaranzie(Long idAbbattimGaranzie) {
		this.idAbbattimGaranzie = idAbbattimGaranzie;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdAttivitaGaranzie() {
		return idAttivitaGaranzie;
	}
	public void setIdAttivitaGaranzie(Long idAttivitaGaranzie) {
		this.idAttivitaGaranzie = idAttivitaGaranzie;
	}
	public Date getDataAbbattimGaranzie() {
		return dataAbbattimGaranzie;
	}
	public void setDataAbbattimGaranzie(Date dataAbbattimGaranzie) {
		this.dataAbbattimGaranzie = dataAbbattimGaranzie;
	}
	public Long getImpIniziale() {
		return impIniziale;
	}
	public void setImpIniziale(Long impIniziale) {
		this.impIniziale = impIniziale;
	}
	public Long getImpLiberato() {
		return impLiberato;
	}
	public void setImpLiberato(Long impLiberato) {
		this.impLiberato = impLiberato;
	}

	public Long getImpNuovo() {
		return impNuovo;
	}
	public void setImpNuovo(Long impNuovo) {
		this.impNuovo = impNuovo;
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
