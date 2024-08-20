/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

import java.math.BigDecimal;
import java.sql.Date;

public class StoricoSegnalazioneCorteContiDTO {
	
	private String nome; 
	private String cognome; 
	private Long idSegnalazioneCorteConti;  //	ID_SEGNALAZIONE_CORTE_CONTI
	private Long idProgetto; 				//	ID_PROGETTO
	private Date dataSegnalazione;  		//	DT_SEGNALAZIONE
	private String numProtocolloSegn;			//	NUM_PROTOCOLLO_SEGN
	private BigDecimal impCredResCapitale; 		//	IMP_CRED_RES_CAPITALE
	private BigDecimal impOneriAgevolaz;			//	IMP_ONERI_AGEVOLAZ
	private BigDecimal impQuotaSegnalaz; 			//	IMP_QUOTA_SEGNALAZ
	private BigDecimal impGaranzia; 				//	IMP_GARANZIA
	private String flagPianoRientro; 
	private Date dataPianoRientro; 
	private String flagSaldoStralcio; 
	private Date dataSaldoStralcio; 
	private String flagPagamIntegrale; 
	private Date dataPagamento; 
	private String flagDissegnalazione; 
	private Date dataDissegnalazione;
	private String numProtocolloDiss;
	private String flagDecretoArchiv; 
	private Date dataArchiv; 
	private String numProtocolloArchiv; 
	private String flagComunicazRegione;
	private String note; 
	private Date dataInserimento;
	private Date dataAggiornamento;
	
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
	public Long getIdSegnalazioneCorteConti() {
		return idSegnalazioneCorteConti;
	}
	public void setIdSegnalazioneCorteConti(Long idSegnalazioneCorteConti) {
		this.idSegnalazioneCorteConti = idSegnalazioneCorteConti;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Date getDataSegnalazione() {
		return dataSegnalazione;
	}
	public void setDataSegnalazione(Date dataSegnalazione) {
		this.dataSegnalazione = dataSegnalazione;
	}
	public String getNumProtocolloSegn() {
		return numProtocolloSegn;
	}
	public void setNumProtocolloSegn(String numProtocolloSegn) {
		this.numProtocolloSegn = numProtocolloSegn;
	}
	
	public BigDecimal getImpCredResCapitale() {
		return impCredResCapitale;
	}
	public void setImpCredResCapitale(BigDecimal impCredResCapitale) {
		this.impCredResCapitale = impCredResCapitale;
	}
	public BigDecimal getImpOneriAgevolaz() {
		return impOneriAgevolaz;
	}
	public void setImpOneriAgevolaz(BigDecimal impOneriAgevolaz) {
		this.impOneriAgevolaz = impOneriAgevolaz;
	}
	public BigDecimal getImpQuotaSegnalaz() {
		return impQuotaSegnalaz;
	}
	public void setImpQuotaSegnalaz(BigDecimal impQuotaSegnalaz) {
		this.impQuotaSegnalaz = impQuotaSegnalaz;
	}
	public BigDecimal getImpGaranzia() {
		return impGaranzia;
	}
	public void setImpGaranzia(BigDecimal impGaranzia) {
		this.impGaranzia = impGaranzia;
	}
	public String getFlagPianoRientro() {
		return flagPianoRientro;
	}
	public void setFlagPianoRientro(String flagPianoRientro) {
		this.flagPianoRientro = flagPianoRientro;
	}
	public Date getDataPianoRientro() {
		return dataPianoRientro;
	}
	public void setDataPianoRientro(Date dataPianoRientro) {
		this.dataPianoRientro = dataPianoRientro;
	}
	public String getFlagSaldoStralcio() {
		return flagSaldoStralcio;
	}
	public void setFlagSaldoStralcio(String flagSaldoStralcio) {
		this.flagSaldoStralcio = flagSaldoStralcio;
	}
	public Date getDataSaldoStralcio() {
		return dataSaldoStralcio;
	}
	public void setDataSaldoStralcio(Date dataSaldoStralcio) {
		this.dataSaldoStralcio = dataSaldoStralcio;
	}
	public String getFlagPagamIntegrale() {
		return flagPagamIntegrale;
	}
	public void setFlagPagamIntegrale(String flagPagamIntegrale) {
		this.flagPagamIntegrale = flagPagamIntegrale;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getFlagDissegnalazione() {
		return flagDissegnalazione;
	}
	public void setFlagDissegnalazione(String flagDissegnalazione) {
		this.flagDissegnalazione = flagDissegnalazione;
	}
	public Date getDataDissegnalazione() {
		return dataDissegnalazione;
	}
	public void setDataDissegnalazione(Date dataDissegnalazione) {
		this.dataDissegnalazione = dataDissegnalazione;
	}
	public String getNumProtocolloDiss() {
		return numProtocolloDiss;
	}
	public void setNumProtocolloDiss(String numProtocolloDiss) {
		this.numProtocolloDiss = numProtocolloDiss;
	}
	public String getFlagDecretoArchiv() {
		return flagDecretoArchiv;
	}
	public void setFlagDecretoArchiv(String flagDecretoArchiv) {
		this.flagDecretoArchiv = flagDecretoArchiv;
	}
	public Date getDataArchiv() {
		return dataArchiv;
	}
	public void setDataArchiv(Date dataArchiv) {
		this.dataArchiv = dataArchiv;
	}
	public String getNumProtocolloArchiv() {
		return numProtocolloArchiv;
	}
	public void setNumProtocolloArchiv(String numProtocolloArchiv) {
		this.numProtocolloArchiv = numProtocolloArchiv;
	}
	public String getFlagComunicazRegione() {
		return flagComunicazRegione;
	}
	public void setFlagComunicazRegione(String flagComunicazRegione) {
		this.flagComunicazRegione = flagComunicazRegione;
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
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	} 
		

}
