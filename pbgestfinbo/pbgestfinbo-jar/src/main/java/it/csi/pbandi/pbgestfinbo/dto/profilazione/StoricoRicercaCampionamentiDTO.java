/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

import java.math.BigDecimal;
import java.sql.Date;

public class StoricoRicercaCampionamentiDTO {
	
	private BigDecimal idCampione; 
	private String  descrizione; 
	private String descTipologiaCamp; 
	private BigDecimal numProgettiSelezionati; 
	private BigDecimal quotaCertEstratta;  
	private Long idTipoCamp; 
	private Long idFaseCamp; 
	private Date dataCampionamento; 
	private BigDecimal impValidato; 
	private Long percEstratta; 
	private BigDecimal impValPercEstratta; 
	private Date dataInizioValidita; 		
	private Date dataFineValidita;			
	private Date dataInserimento;				
	private Date dataAggiornamento;	
	private Long idUtenteIns;				
	private Long idUtenteAgg;
	private String flagAnnullato;
	private String nome;
	private String cognome;
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public BigDecimal getIdCampione() {
		return idCampione;
	}
	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescTipologiaCamp() {
		return descTipologiaCamp;
	}
	public void setDescTipologiaCamp(String descTipologiaCamp) {
		this.descTipologiaCamp = descTipologiaCamp;
	}
	public BigDecimal getNumProgettiSelezionati() {
		return numProgettiSelezionati;
	}
	public void setNumProgettiSelezionati(BigDecimal numProgettiSelezionati) {
		this.numProgettiSelezionati = numProgettiSelezionati;
	}
	public BigDecimal getQuotaCertEstratta() {
		return quotaCertEstratta;
	}
	public void setQuotaCertEstratta(BigDecimal quotaCertEstratta) {
		this.quotaCertEstratta = quotaCertEstratta;
	}
	public Long getIdTipoCamp() {
		return idTipoCamp;
	}
	public void setIdTipoCamp(Long idTipoCamp) {
		this.idTipoCamp = idTipoCamp;
	}
	public Long getIdFaseCamp() {
		return idFaseCamp;
	}
	public void setIdFaseCamp(Long idFaseCamp) {
		this.idFaseCamp = idFaseCamp;
	}
	public Date getDataCampionamento() {
		return dataCampionamento;
	}
	public void setDataCampionamento(Date dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}
	public BigDecimal getImpValidato() {
		return impValidato;
	}
	public void setImpValidato(BigDecimal impValidato) {
		this.impValidato = impValidato;
	}
	public Long getPercEstratta() {
		return percEstratta;
	}
	public void setPercEstratta(Long percEstratta) {
		this.percEstratta = percEstratta;
	}
	public BigDecimal getImpValPercEstratta() {
		return impValPercEstratta;
	}
	public void setImpValPercEstratta(BigDecimal impValPercEstratta) {
		this.impValPercEstratta = impValPercEstratta;
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
	public String getFlagAnnullato() {
		return flagAnnullato;
	}
	public void setFlagAnnullato(String flagAnnullato) {
		this.flagAnnullato = flagAnnullato;
	} 
	
	

}
