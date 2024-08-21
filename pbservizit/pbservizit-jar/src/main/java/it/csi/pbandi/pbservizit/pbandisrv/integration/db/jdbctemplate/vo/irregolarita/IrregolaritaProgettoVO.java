/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;


public class IrregolaritaProgettoVO extends GenericVO {
	
	private BigDecimal idIrregolarita;
	private BigDecimal idIrregolaritaCollegata;
	private String numeroIms;
	private Date dtIms;
	private Date dtComunicazione;
	private String flagCasoChiuso;
	private String flagBlocco;
	private String flagProvvedimento;
	private String notePraticaUsata;
	private BigDecimal numeroVersione;
	private String soggettoResponsabile;
	private BigDecimal idProgetto;
	private BigDecimal idStatoAmministrativo;
	private BigDecimal idDispComunitaria;
	private BigDecimal idMetodoIndividuazione;
	private BigDecimal idNaturaSanzione;
	private BigDecimal idTipoIrregolarita;
	private BigDecimal idQualificazioneIrreg;
	private BigDecimal idStatoFinanziario;
	private BigDecimal idUtenteIns;
	private BigDecimal idUtenteAgg;
	private String codiceVisualizzato;
	private String descTipoIrregolarita;
	private String descBreveTipoIrregolarita;
	private String descQualificazioneIrreg;
	private String descBreveQualificIrreg;
	private String descDispComunitaria;
	private String descBreveDispComunitaria;
	private String descMetodoInd;
	private String descBreveMetodoInd;
	private String descStatoAmministrativo;
	private String descBreveStatoAmministrativ;
	private String descStatoFinanziario;
	private String descBreveStatoFinanziario;
	private String descNaturaSanzione;
	private String descBreveNaturaSanzione;
	private BigDecimal idSoggettoBeneficiario;
	private String denominazioneBeneficiario;
	private BigDecimal importoIrregolarita;
	private BigDecimal quotaImpIrregCertificato;
	private BigDecimal idIrregolaritaProvv;
	
	private String tipoControlli;
	private BigDecimal importoAgevolazioneIrreg;
	private BigDecimal idMotivoRevoca;
	private String descMotivoRevoca;
	private Date dataInizioControlli;
	private Date dataFineControlli;
	
	private BigDecimal idPeriodo;
	private String descPeriodoVisualizzata;
	private BigDecimal idCategAnagrafica;
	private String descCategAnagrafica;
	private String note;
	
	private Date dataCampione;
//	private Date dataCampioneProvv;
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public BigDecimal getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(BigDecimal idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public String getDescPeriodoVisualizzata() {
		return descPeriodoVisualizzata;
	}
	public void setDescPeriodoVisualizzata(String descPeriodoVisualizzata) {
		this.descPeriodoVisualizzata = descPeriodoVisualizzata;
	}
	public BigDecimal getIdCategAnagrafica() {
		return idCategAnagrafica;
	}
	public void setIdCategAnagrafica(BigDecimal idCategAnagrafica) {
		this.idCategAnagrafica = idCategAnagrafica;
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
	public void setTipoControllo(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}
	public BigDecimal getImportoAgevolazioneIrreg() {
		return importoAgevolazioneIrreg;
	}
	public void setImportoAgevolazioneIrreg(BigDecimal importoAgevolazioneIrreg) {
		this.importoAgevolazioneIrreg = importoAgevolazioneIrreg;
	}
	public BigDecimal getIdMotivoRevoca() {
		return idMotivoRevoca;
	}
	public void setIdMotivoRevoca(BigDecimal idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}
	public String getDescMotivoRevoca() {
		return descMotivoRevoca;
	}
	public void setDescMotivoRevoca(String descMotivoRevoca) {
		this.descMotivoRevoca = descMotivoRevoca;
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
	public void setTipoControlli(String tipoControlli) {
		this.tipoControlli = tipoControlli;
	}
	public String getFlagProvvedimento() {
		return flagProvvedimento;
	}
	public void setFlagProvvedimento(String flagProvvedimento) {
		this.flagProvvedimento = flagProvvedimento;
	}
	public BigDecimal getImportoIrregolarita() {
		return importoIrregolarita;
	}
	public void setImportoIrregolarita(BigDecimal importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}
	public BigDecimal getQuotaImpIrregCertificato() {
		return quotaImpIrregCertificato;
	}
	public void setQuotaImpIrregCertificato(BigDecimal quotaImpIrregCertificato) {
		this.quotaImpIrregCertificato = quotaImpIrregCertificato;
	}
	public BigDecimal getIdIrregolarita() {
		return idIrregolarita;
	}
	public void setIdIrregolarita(BigDecimal idIrregolarita) {
		this.idIrregolarita = idIrregolarita;
	}
	public BigDecimal getIdIrregolaritaCollegata() {
		return idIrregolaritaCollegata;
	}
	public void setIdIrregolaritaCollegata(BigDecimal idIrregolaritaCollegata) {
		this.idIrregolaritaCollegata = idIrregolaritaCollegata;
	}
	public String getNumeroIms() {
		return numeroIms;
	}
	public void setNumeroIms(String numeroIms) {
		this.numeroIms = numeroIms;
	}
	public Date getDtIms() {
		return dtIms;
	}
	public void setDtIms(Date dtIms) {
		this.dtIms = dtIms;
	}
	public Date getDtComunicazione() {
		return dtComunicazione;
	}
	public void setDtComunicazione(Date dtComunicazione) {
		this.dtComunicazione = dtComunicazione;
	}
	public String getFlagCasoChiuso() {
		return flagCasoChiuso;
	}
	public void setFlagCasoChiuso(String flagCasoChiuso) {
		this.flagCasoChiuso = flagCasoChiuso;
	}
	public String getFlagBlocco() {
		return flagBlocco;
	}
	public void setFlagBlocco(String flagBlocco) {
		this.flagBlocco = flagBlocco;
	}
	public String getNotePraticaUsata() {
		return notePraticaUsata;
	}
	public void setNotePraticaUsata(String notePraticaUsata) {
		this.notePraticaUsata = notePraticaUsata;
	}
	public BigDecimal getNumeroVersione() {
		return numeroVersione;
	}
	public void setNumeroVersione(BigDecimal numeroVersione) {
		this.numeroVersione = numeroVersione;
	}
	public String getSoggettoResponsabile() {
		return soggettoResponsabile;
	}
	public void setSoggettoResponsabile(String soggettoResponsabile) {
		this.soggettoResponsabile = soggettoResponsabile;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdStatoAmministrativo() {
		return idStatoAmministrativo;
	}
	public void setIdStatoAmministrativo(BigDecimal idStatoAmministrativo) {
		this.idStatoAmministrativo = idStatoAmministrativo;
	}
	public BigDecimal getIdDispComunitaria() {
		return idDispComunitaria;
	}
	public void setIdDispComunitaria(BigDecimal idDispComunitaria) {
		this.idDispComunitaria = idDispComunitaria;
	}
	public BigDecimal getIdMetodoIndividuazione() {
		return idMetodoIndividuazione;
	}
	public void setIdMetodoIndividuazione(BigDecimal idMetodoIndividuazione) {
		this.idMetodoIndividuazione = idMetodoIndividuazione;
	}
	public BigDecimal getIdNaturaSanzione() {
		return idNaturaSanzione;
	}
	public void setIdNaturaSanzione(BigDecimal idNaturaSanzione) {
		this.idNaturaSanzione = idNaturaSanzione;
	}
	public BigDecimal getIdTipoIrregolarita() {
		return idTipoIrregolarita;
	}
	public void setIdTipoIrregolarita(BigDecimal idTipoIrregolarita) {
		this.idTipoIrregolarita = idTipoIrregolarita;
	}
	public BigDecimal getIdQualificazioneIrreg() {
		return idQualificazioneIrreg;
	}
	public void setIdQualificazioneIrreg(BigDecimal idQualificazioneIrreg) {
		this.idQualificazioneIrreg = idQualificazioneIrreg;
	}
	public BigDecimal getIdStatoFinanziario() {
		return idStatoFinanziario;
	}
	public void setIdStatoFinanziario(BigDecimal idStatoFinanziario) {
		this.idStatoFinanziario = idStatoFinanziario;
	}
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getDescTipoIrregolarita() {
		return descTipoIrregolarita;
	}
	public void setDescTipoIrregolarita(String descTipoIrregolarita) {
		this.descTipoIrregolarita = descTipoIrregolarita;
	}
	public String getDescBreveTipoIrregolarita() {
		return descBreveTipoIrregolarita;
	}
	public void setDescBreveTipoIrregolarita(String descBreveTipoIrregolarita) {
		this.descBreveTipoIrregolarita = descBreveTipoIrregolarita;
	}
	public String getDescQualificazioneIrreg() {
		return descQualificazioneIrreg;
	}
	public void setDescQualificazioneIrreg(String descQualificazioneIrreg) {
		this.descQualificazioneIrreg = descQualificazioneIrreg;
	}
	public String getDescBreveQualificIrreg() {
		return descBreveQualificIrreg;
	}
	public void setDescBreveQualificIrreg(String descBreveQualificIrreg) {
		this.descBreveQualificIrreg = descBreveQualificIrreg;
	}
	public String getDescDispComunitaria() {
		return descDispComunitaria;
	}
	public void setDescDispComunitaria(String descDispComunitaria) {
		this.descDispComunitaria = descDispComunitaria;
	}
	public String getDescBreveDispComunitaria() {
		return descBreveDispComunitaria;
	}
	public void setDescBreveDispComunitaria(String descBreveDispComunitaria) {
		this.descBreveDispComunitaria = descBreveDispComunitaria;
	}
	public String getDescMetodoInd() {
		return descMetodoInd;
	}
	public void setDescMetodoInd(String descMetodoInd) {
		this.descMetodoInd = descMetodoInd;
	}
	public String getDescBreveMetodoInd() {
		return descBreveMetodoInd;
	}
	public void setDescBreveMetodoInd(String descBreveMetodoInd) {
		this.descBreveMetodoInd = descBreveMetodoInd;
	}
	public String getDescStatoAmministrativo() {
		return descStatoAmministrativo;
	}
	public void setDescStatoAmministrativo(String descStatoAmministrativo) {
		this.descStatoAmministrativo = descStatoAmministrativo;
	}
	public String getDescBreveStatoAmministrativ() {
		return descBreveStatoAmministrativ;
	}
	public void setDescBreveStatoAmministrativ(String descBreveStatoAmministrativ) {
		this.descBreveStatoAmministrativ = descBreveStatoAmministrativ;
	}
	public String getDescStatoFinanziario() {
		return descStatoFinanziario;
	}
	public void setDescStatoFinanziario(String descStatoFinanziario) {
		this.descStatoFinanziario = descStatoFinanziario;
	}
	public String getDescBreveStatoFinanziario() {
		return descBreveStatoFinanziario;
	}
	public void setDescBreveStatoFinanziario(String descBreveStatoFinanziario) {
		this.descBreveStatoFinanziario = descBreveStatoFinanziario;
	}
	public String getDescNaturaSanzione() {
		return descNaturaSanzione;
	}
	public void setDescNaturaSanzione(String descNaturaSanzione) {
		this.descNaturaSanzione = descNaturaSanzione;
	}
	public String getDescBreveNaturaSanzione() {
		return descBreveNaturaSanzione;
	}
	public void setDescBreveNaturaSanzione(String descBreveNaturaSanzione) {
		this.descBreveNaturaSanzione = descBreveNaturaSanzione;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public BigDecimal getIdIrregolaritaProvv() {
		return idIrregolaritaProvv;
	}
	public void setIdIrregolaritaProvv(BigDecimal idIrregolaritaProvv) {
		this.idIrregolaritaProvv = idIrregolaritaProvv;
	}
	public Date getDataCampione() {
		return dataCampione;
	}
	public void setDataCampione(Date dataCampione) {
		this.dataCampione = dataCampione;
	}
//	public Date getDataCampioneProvv() {
//		return dataCampioneProvv;
//	}
//	public void setDataCampioneProvv(Date dataCampioneProvv) {
//		this.dataCampioneProvv = dataCampioneProvv;
//	}
	@Override
	public String toString() {
		return "IrregolaritaProgettoVO [codiceVisualizzato="
				+ codiceVisualizzato + ", dataCampione=" + dataCampione
				+ ", dataFineControlli=" + dataFineControlli
				+ ", dataInizioControlli=" + dataInizioControlli
				+ ", denominazioneBeneficiario=" + denominazioneBeneficiario
				+ ", descBreveDispComunitaria=" + descBreveDispComunitaria
				+ ", descBreveMetodoInd=" + descBreveMetodoInd
				+ ", descBreveNaturaSanzione=" + descBreveNaturaSanzione
				+ ", descBreveQualificIrreg=" + descBreveQualificIrreg
				+ ", descBreveStatoAmministrativ="
				+ descBreveStatoAmministrativ + ", descBreveStatoFinanziario="
				+ descBreveStatoFinanziario + ", descBreveTipoIrregolarita="
				+ descBreveTipoIrregolarita + ", descCategAnagrafica="
				+ descCategAnagrafica + ", descDispComunitaria="
				+ descDispComunitaria + ", descMetodoInd=" + descMetodoInd
				+ ", descMotivoRevoca=" + descMotivoRevoca
				+ ", descNaturaSanzione=" + descNaturaSanzione
				+ ", descPeriodoVisualizzata=" + descPeriodoVisualizzata
				+ ", descQualificazioneIrreg=" + descQualificazioneIrreg
				+ ", descStatoAmministrativo=" + descStatoAmministrativo
				+ ", descStatoFinanziario=" + descStatoFinanziario
				+ ", descTipoIrregolarita=" + descTipoIrregolarita
				+ ", dtComunicazione=" + dtComunicazione + ", dtIms=" + dtIms
				+ ", flagBlocco=" + flagBlocco + ", flagCasoChiuso="
				+ flagCasoChiuso + ", flagProvvedimento=" + flagProvvedimento
				+ ", idCategAnagrafica=" + idCategAnagrafica
				+ ", idDispComunitaria=" + idDispComunitaria
				+ ", idIrregolarita=" + idIrregolarita
				+ ", idIrregolaritaCollegata=" + idIrregolaritaCollegata
				+ ", idIrregolaritaProvv=" + idIrregolaritaProvv
				+ ", idMetodoIndividuazione=" + idMetodoIndividuazione
				+ ", idMotivoRevoca=" + idMotivoRevoca + ", idNaturaSanzione="
				+ idNaturaSanzione + ", idPeriodo=" + idPeriodo
				+ ", idProgetto=" + idProgetto + ", idQualificazioneIrreg="
				+ idQualificazioneIrreg + ", idSoggettoBeneficiario="
				+ idSoggettoBeneficiario + ", idStatoAmministrativo="
				+ idStatoAmministrativo + ", idStatoFinanziario="
				+ idStatoFinanziario + ", idTipoIrregolarita="
				+ idTipoIrregolarita + ", idUtenteAgg=" + idUtenteAgg
				+ ", idUtenteIns=" + idUtenteIns
				+ ", importoAgevolazioneIrreg=" + importoAgevolazioneIrreg
				+ ", importoIrregolarita=" + importoIrregolarita + ", note="
				+ note + ", notePraticaUsata=" + notePraticaUsata
				+ ", numeroIms=" + numeroIms + ", numeroVersione="
				+ numeroVersione + ", quotaImpIrregCertificato="
				+ quotaImpIrregCertificato + ", soggettoResponsabile="
				+ soggettoResponsabile + ", tipoControlli=" + tipoControlli
				+ "]";
	}

}
