/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class RevocaBancariaVO {
	
	public Long idRevoca; 
	public Long idProgetto; 
	
	
	public Date dataRicezComunicazioneRevoca; 		 		//	DT_RICEZIONE_COMUNICAZ 
	private Date dataRevoca; 				  			    //	DT_REVOCA
	public BigDecimal impDebitoResiduoBanca; 		  			    //	IMP_DEBITO_RESIDUO_BANCA
	public BigDecimal impDebitoResiduoFinpiemonte; 				    //	IMP_DEBITO_RESIDUO_FP
	public BigDecimal perCofinanziamentoFinpiemonte; 		    //	PERC_COFINANZ_FP
	public String numeroProtocollo;							//	NUM_PROTOCOLLO
	public String note;										//	NOTE
	
	public Date dataInizioValidita; 						// data del girono corrente
	public Date dataFineValidita;							// campo di solito vuota sul db 
	public Date dataInserimento; 							// data del girono corrente
	public Date dataAggiornamento; 							// campo di solito vuota sul db
	private Long idUtenteIns;
	private Long idUtenteAgg;
	
	private int idModalitaAgevolazione; 
	
	
	
	public Long getIdRevoca() {
		return idRevoca;
	}
	public void setIdRevoca(Long idRevoca) {
		this.idRevoca = idRevoca;
	}

	public int getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(int idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public Date getDataRicezComunicazioneRevoca() {
		return dataRicezComunicazioneRevoca;
	}
	public void setDataRicezComunicazioneRevoca(Date dataRicezComunicazioneRevoca) {
		this.dataRicezComunicazioneRevoca = dataRicezComunicazioneRevoca;
	}
	public Date getDataRevoca() {
		return dataRevoca;
	}
	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}
	
	public BigDecimal getImpDebitoResiduoBanca() {
		return impDebitoResiduoBanca;
	}
	public void setImpDebitoResiduoBanca(BigDecimal impDebitoResiduoBanca) {
		this.impDebitoResiduoBanca = impDebitoResiduoBanca;
	}
	public BigDecimal getImpDebitoResiduoFinpiemonte() {
		return impDebitoResiduoFinpiemonte;
	}
	public void setImpDebitoResiduoFinpiemonte(BigDecimal impDebitoResiduoFinpiemonte) {
		this.impDebitoResiduoFinpiemonte = impDebitoResiduoFinpiemonte;
	}
	
	public BigDecimal getPerCofinanziamentoFinpiemonte() {
		return perCofinanziamentoFinpiemonte;
	}
	public void setPerCofinanziamentoFinpiemonte(BigDecimal perCofinanziamentoFinpiemonte) {
		this.perCofinanziamentoFinpiemonte = perCofinanziamentoFinpiemonte;
	}
	
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
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

	public Long getIdProgetto() {
		return idProgetto;
	} 
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	

}
