/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.rilevazionicontrolli;

import java.math.BigDecimal;
import java.sql.Date;

public class RilevazioneProgettoDTO {
	private BigDecimal 	idCampione;
	private String 		descPeriodoVisualizzata;
	private String 		asse;
	private String 		azione;
	private String 		misura;
	private BigDecimal 	idProgetto;
	private String 		codiceVisualizzato;
	private String 		denominazioneEnteGiuridico;
	private String 		descCategAnagrafica;
	private String 		tipoControlli;
	private BigDecimal 	idIrregolarita;
	private Date 		dataInizioControlli;
	private Date 		dataFineControlli;
	private BigDecimal 	idIrregolaritaCollegProvv;
	
	//IRREGOLARITA' PROVVISORIA
	private BigDecimal 	idIrregolaritaProvv;
	private String 		irregolaritaAnnullataProvv;
	private BigDecimal 	importoIrregolaritaProvv;
	private BigDecimal 	importoAgevolIrregProvv;
	private BigDecimal 	importoIrregCertifProvv;
	
	private BigDecimal 	quotaImpIrregCertificato;
	private String 		descMotivoRevocaProvv;
	private Date 		dataInizioControlliProvv;
	private Date 		dataFineControlliProvv;
	
	//IRREGOLARITA' 
	//MANCA ESITO DEFINITIVO
	private BigDecimal 	importoIrregolarita;
	private BigDecimal 	importoAgevolazioneIrreg;
	
	//Desc Motivo Revoca vale sia per la definitiva che la provvisoria
	private String 		descMotivoRevoca;
	private String 		numeroIms;
	private String 		tipoProvvedimento;
	private String 		estremi;
	private BigDecimal 	importo;
	private BigDecimal 	importoRecupero;
	private Date 		dataRecupero;
	private String 		estremiRecupero;
	private String 		descMancatoRecupero;
	private String 		tipoIrregolarita;
	private String		nomeBandoLinea;
	
	private String esitoDefinitivo;
	private String esitoProvvisorio;
	
	//NOTE
	private String noteIrregolarita;
	private String noteIrregolaritaProvvisoria;
	private String noteEsitoRegolare;
	
	private Date dataCampionamento;
	
	public BigDecimal getIdCampione() {
		return idCampione;
	}
	public void setIdCampione(BigDecimal idCampione) {
		this.idCampione = idCampione;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	public String getAsse() {
		return asse;
	}
	public void setAsse(String asse) {
		this.asse = asse;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public String getMisura() {
		return misura;
	}
	public void setMisura(String misura) {
		this.misura = misura;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getDenominazioneEnteGiuridico() {
		return denominazioneEnteGiuridico;
	}
	public void setDenominazioneEnteGiuridico(String denominazioneEnteGiuridico) {
		this.denominazioneEnteGiuridico = denominazioneEnteGiuridico;
	}
	public String getDescCategAnagrafica() {
		return descCategAnagrafica;
	}
	public void setDescCategAnagrafica(String descCategAnagrafica) {
		this.descCategAnagrafica = descCategAnagrafica;
	}
	public String getTipoControlli() {
		return tipoControlli;
	}
	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}
	public BigDecimal getIdIrregolarita() {
		return idIrregolarita;
	}
	public void setIdIrregolarita(BigDecimal idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}
	public Date getDataInizioControlli() {
		return dataInizioControlli;
	}
	public void setDataInizioControlli(Date dataInizioControlli) {
		this.dataInizioControlli = dataInizioControlli;
	}
	public Date getDataFineControlli() {
		return dataFineControlli;
	}
	public void setDataFineControlli(Date dataFineControlli) {
		this.dataFineControlli = dataFineControlli;
	}
	public BigDecimal getIdIrregolaritaCollegProvv() {
		return idIrregolaritaCollegProvv;
	}
	public void setIdIrregolaritaCollegProvv(BigDecimal idIrregolaritaCollegProvv) {
		this.idIrregolaritaCollegProvv = idIrregolaritaCollegProvv;
	}
	public BigDecimal getIdIrregolaritaProvv() {
		return idIrregolaritaProvv;
	}
	public void setIdIrregolaritaProvv(BigDecimal idIrregolaritaProvv) {
		this.idIrregolaritaProvv = idIrregolaritaProvv;
	}
	public String getIrregolaritaAnnullataProvv() {
		return irregolaritaAnnullataProvv;
	}
	public void setIrregolaritaAnnullataProvv(String irregolaritaAnnullataProvv) {
		this.irregolaritaAnnullataProvv = irregolaritaAnnullataProvv;
	}
	public BigDecimal getImportoIrregolaritaProvv() {
		return importoIrregolaritaProvv;
	}
	public void setImportoIrregolaritaProvv(BigDecimal importoIrregolaritaProvv) {
		this.importoIrregolaritaProvv = importoIrregolaritaProvv;
	}
	public BigDecimal getImportoAgevolIrregProvv() {
		return importoAgevolIrregProvv;
	}
	public void setImportoAgevolIrregProvv(BigDecimal importoAgevolIrregProvv) {
		this.importoAgevolIrregProvv = importoAgevolIrregProvv;
	}
	public BigDecimal getImportoIrregCertifProvv() {
		return importoIrregCertifProvv;
	}
	public void setImportoIrregCertifProvv(BigDecimal importoIrregCertifProvv) {
		this.importoIrregCertifProvv = importoIrregCertifProvv;
	}
	public BigDecimal getQuotaImpIrregCertificato() {
		return quotaImpIrregCertificato;
	}
	public void setQuotaImpIrregCertificato(BigDecimal quotaImpIrregCertificato) {
		this.quotaImpIrregCertificato = quotaImpIrregCertificato;
	}
	public String getDescMotivoRevocaProvv() {
		return descMotivoRevocaProvv;
	}
	public void setDescMotivoRevocaProvv(String descMotivoRevocaProvv) {
		this.descMotivoRevocaProvv = descMotivoRevocaProvv;
	}
	public Date getDataInizioControlliProvv() {
		return dataInizioControlliProvv;
	}
	public void setDataInizioControlliProvv(Date dataInizioControlliProvv) {
		this.dataInizioControlliProvv = dataInizioControlliProvv;
	}
	public Date getDataFineControlliProvv() {
		return dataFineControlliProvv;
	}
	public void setDataFineControlliProvv(Date dataFineControlliProvv) {
		this.dataFineControlliProvv = dataFineControlliProvv;
	}
	public BigDecimal getImportoIrregolarita() {
		return importoIrregolarita;
	}
	public void setImportoIrregolarita(BigDecimal importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}
	public BigDecimal getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}
	public void setImportoAgevolazioneIrreg(BigDecimal importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}
	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}
	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
	}
	public String getNumeroIms() {
		return numeroIms;
	}
	public void setNumeroIms(String numeroIms) {
		this.numeroIms = numeroIms;
	}
	public String getTipoProvvedimento() {
		return tipoProvvedimento;
	}
	public void setTipoProvvedimento(String tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}
	public String getEstremi() {
		return estremi;
	}
	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public BigDecimal getImportoRecupero() {
		return importoRecupero;
	}
	public void setImportoRecupero(BigDecimal importoRecupero) {
		this.importoRecupero = importoRecupero;
	}
	public Date getDataRecupero() {
		return dataRecupero;
	}
	public void setDataRecupero(Date dataRecupero) {
		this.dataRecupero = dataRecupero;
	}
	public String getEstremiRecupero() {
		return estremiRecupero;
	}
	public void setEstremiRecupero(String estremiRecupero) {
		this.estremiRecupero = estremiRecupero;
	}
	public String getDescMancatoRecupero() {
		return descMancatoRecupero;
	}
	public void setDescMancatoRecupero(String descMancatoRecupero) {
		this.descMancatoRecupero = descMancatoRecupero;
	}
	public String getTipoIrregolarita() {
		return tipoIrregolarita;
	}
	public void setTipoIrregolarita(String tipoIrregolarita) {
		this.tipoIrregolarita = tipoIrregolarita;
	}
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	public String getEsitoDefinitivo() {
		return esitoDefinitivo;
	}
	public void setEsitoDefinitivo(String esitoDefinitivo) {
		this.esitoDefinitivo = esitoDefinitivo;
	}
	public String getEsitoProvvisorio() {
		return esitoProvvisorio;
	}
	public void setEsitoProvvisorio(String esitoProvvisorio) {
		this.esitoProvvisorio = esitoProvvisorio;
	}
	public String getNoteIrregolarita() {
		return noteIrregolarita;
	}
	public void setNoteIrregolarita(String noteIrregolarita) {
		this.noteIrregolarita = noteIrregolarita;
	}
	public String getNoteIrregolaritaProvvisoria() {
		return noteIrregolaritaProvvisoria;
	}
	public void setNoteIrregolaritaProvvisoria(String noteIrregolaritaProvvisoria) {
		this.noteIrregolaritaProvvisoria = noteIrregolaritaProvvisoria;
	}
	public String getNoteEsitoRegolare() {
		return noteEsitoRegolare;
	}
	public void setNoteEsitoRegolare(String noteEsitoRegolare) {
		this.noteEsitoRegolare = noteEsitoRegolare;
	}
	public Date getDataCampionamento() {
		return dataCampionamento;
	}
	public void setDataCampionamento(Date dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}
}
