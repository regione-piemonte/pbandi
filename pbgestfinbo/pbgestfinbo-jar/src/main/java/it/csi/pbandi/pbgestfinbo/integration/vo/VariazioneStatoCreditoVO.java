/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.sql.Date;

public class VariazioneStatoCreditoVO {
	
	
//	ID_VARIAZ_ST_CREDITO
//	ID_SOGG_PROG_STATO_CREDITO_FP
//	ID_STATO_AZIENDA
//	ID_NUOVO_STATO_CREDITO_FP
//	ID_STATO_PROP_VAR_CRE
//	FLAG_CONF_NUOVO_STA_CRE
//	PERC_SCONF
//	GG_SCONF
//	IMP_SCONF_CAPITALE
//	IMP_SCONF_INTERESSI
//	IMP_SCONF_AGEV
//	IMP_EROGATO
//	ID_MODALITA_AGEVOLAZIONE
//	DT_SCADENZA_RATA
//	DT_PAGAMENTO_RATA
//	NUM_RATA
//	DT_INIZIO_VALIDITA
//	DT_FINE_VALIDITA
//	ID_UTENTE_INS
//	ID_UTENTE_AGG
//	DT_INSERIMENTO
//	DT_AGGIORNAMENTO
	
	
	private Long idVariazioneStatoCredito; 
	private Long idSoggProgStatoCredFP; 
	private Long idStatoAzienda; 
	private Long idNuovoStatoCreditoFP; 
	private Long idstatoPropVArzCredito; 
	private char flagConfNuovoStaCred; 
	private Long percSconf; 
	private Long GgSconf; 
	private Long impSconfCapitale; 
	private Long impSconfAInteressi; 
	private Long impSconfAgev; 
	private Long impErogato; 
	private Long idModalitaAgevolazione; 
	private Date dataScadenzaRata;
	private Date pagamScadenzaRata;
	private Long numRata; 
	private Date dataInizioValidita; 						
	private Date dataFineValidita;							
	private Long idUtenteIns;								
	private Long idUtenteAgg;						
	private Date dataInserimento; 							
	private Date dataAggiornamento;
	
	
	
	
	
	
	public Long getIdVariazioneStatoCredito() {
		return idVariazioneStatoCredito;
	}
	public void setIdVariazioneStatoCredito(Long idVariazioneStatoCredito) {
		this.idVariazioneStatoCredito = idVariazioneStatoCredito;
	}
	public Long getIdSoggProgStatoCredFP() {
		return idSoggProgStatoCredFP;
	}
	public void setIdSoggProgStatoCredFP(Long idSoggProgStatoCredFP) {
		this.idSoggProgStatoCredFP = idSoggProgStatoCredFP;
	}
	public Long getIdStatoAzienda() {
		return idStatoAzienda;
	}
	public void setIdStatoAzienda(Long idStatoAzienda) {
		this.idStatoAzienda = idStatoAzienda;
	}
	public Long getIdNuovoStatoCreditoFP() {
		return idNuovoStatoCreditoFP;
	}
	public void setIdNuovoStatoCreditoFP(Long idNuovoStatoCreditoFP) {
		this.idNuovoStatoCreditoFP = idNuovoStatoCreditoFP;
	}
	public Long getIdstatoPropVArzCredito() {
		return idstatoPropVArzCredito;
	}
	public void setIdstatoPropVArzCredito(Long idstatoPropVArzCredito) {
		this.idstatoPropVArzCredito = idstatoPropVArzCredito;
	}
	public char getFlagConfNuovoStaCred() {
		return flagConfNuovoStaCred;
	}
	public void setFlagConfNuovoStaCred(char flagConfNuovoStaCred) {
		this.flagConfNuovoStaCred = flagConfNuovoStaCred;
	}
	public Long getPercSconf() {
		return percSconf;
	}
	public void setPercSconf(Long percSconf) {
		this.percSconf = percSconf;
	}
	public Long getGgSconf() {
		return GgSconf;
	}
	public void setGgSconf(Long ggSconf) {
		GgSconf = ggSconf;
	}
	public Long getImpSconfCapitale() {
		return impSconfCapitale;
	}
	public void setImpSconfCapitale(Long impSconfCapitale) {
		this.impSconfCapitale = impSconfCapitale;
	}
	public Long getImpSconfAInteressi() {
		return impSconfAInteressi;
	}
	public void setImpSconfAInteressi(Long impSconfAInteressi) {
		this.impSconfAInteressi = impSconfAInteressi;
	}
	public Long getImpSconfAgev() {
		return impSconfAgev;
	}
	public void setImpSconfAgev(Long impSconfAgev) {
		this.impSconfAgev = impSconfAgev;
	}
	public Long getImpErogato() {
		return impErogato;
	}
	public void setImpErogato(Long impErogato) {
		this.impErogato = impErogato;
	}
	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public Date getDataScadenzaRata() {
		return dataScadenzaRata;
	}
	public void setDataScadenzaRata(Date dataScadenzaRata) {
		this.dataScadenzaRata = dataScadenzaRata;
	}
	public Date getPagamScadenzaRata() {
		return pagamScadenzaRata;
	}
	public void setPagamScadenzaRata(Date pagamScadenzaRata) {
		this.pagamScadenzaRata = pagamScadenzaRata;
	}
	public Long getNumRata() {
		return numRata;
	}
	public void setNumRata(Long numRata) {
		this.numRata = numRata;
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
	
	

}
