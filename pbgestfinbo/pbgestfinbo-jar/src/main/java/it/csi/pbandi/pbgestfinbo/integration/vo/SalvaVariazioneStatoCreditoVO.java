/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.sql.Date;

public class SalvaVariazioneStatoCreditoVO {
	
	private Long idVariazioneStatoCredito; 
	private Long idUtenteIns;								
	private Long idUtenteAgg;
	private String flagStatoCred;
	private Date dataInizioValidita; 						
	private Date dataFineValidita;							
	private Date dataInserimento; 							
	private Date dataAggiornamento;
	
	
	
	
	
	
	public String getFlagStatoCred() {
		return flagStatoCred;
	}
	public void setFlagStatoCred(String flagStatoCred) {
		this.flagStatoCred = flagStatoCred;
	}
	public Long getIdVariazioneStatoCredito() {
		return idVariazioneStatoCredito;
	}
	public void setIdVariazioneStatoCredito(Long idVariazioneStatoCredito) {
		this.idVariazioneStatoCredito = idVariazioneStatoCredito;
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
