/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class ReportPropostaCertificazionePorFesrVO extends GenericVO {
	@Deprecated
	private String descProgramma;
	private String descAsse;
	private String codiceVisualizzato;
	private BigDecimal idPropostaCertificaz;
	private BigDecimal idLineaDiIntervento;
	private BigDecimal idDettPropostaCertif;
	@Deprecated
	private java.sql.Date dtOraCreazione;

	@Deprecated
	private java.sql.Date dtCutOffPagamenti;
	@Deprecated
	private java.sql.Date dtCutOffValidazioni;
	@Deprecated
	private java.sql.Date dtCutOffErogazioni;
	@Deprecated
	private java.sql.Date dtCutOffFideiussioni;
	@Deprecated
	private String descProposta;
	private BigDecimal idProgetto;
	private String flagRa;
	private BigDecimal cpupor1;
	private BigDecimal cpupor2;
	private BigDecimal cpupor3;
	private BigDecimal cofpor;
	private BigDecimal othfin;
	private BigDecimal cpupor1_fesr;
	private BigDecimal cpupor2_fesr;
	private BigDecimal cpupor3_fesr;
	private BigDecimal cpufasSta;
	private BigDecimal cpufasReg;
	private BigDecimal importoFideiussioni;
	private BigDecimal importoFidUtilizzate;
	private BigDecimal importoPagValidatiOrig;
	private BigDecimal importoPagamentiValidati;
	private BigDecimal importoEccendenzeValidazione;
	private BigDecimal importoErogazioni;
	private BigDecimal spesaCertificabileLorda;
	private BigDecimal importoRevoche;
	// Modifica 20.01.2017 M.E.
	private BigDecimal  quotaRevocaPerIrr;
	private java.sql.Date dtUltimaRevoca;
	private BigDecimal importoDaRecuperare;
	private BigDecimal importoPrerecuperi;
	private java.sql.Date dtUltimoPrerecupero;
	private BigDecimal importoRecuperi;
	private java.sql.Date dtUltimoRecupero;
	private BigDecimal importoSoppressioni;
	private java.sql.Date dtUltimaSoppressione;
	private BigDecimal importoNonRilevanteCertif;
	
	private BigDecimal importoCertificazioneNetto;
	//IMP_CERTIF_NETTO_PREMODIFICA
	private BigDecimal impCertifNettoPremodifica;
	private String identificativiIrregolarita;
	private BigDecimal erogazioniInMenoPerErrore;
	private BigDecimal fideiussioniInMenoPerError;
	private BigDecimal recuperiInMenoPerErrore;
	private BigDecimal soppressioniInMenoPerError;
	private BigDecimal prerecuperiInMenoPerError;
	private BigDecimal revocheInMenoPerError;
	private BigDecimal progrBandoLineaIntervento;
	private String flagLc;
	private java.sql.Date dtUltimaChecklistInLoco;
	private String descMisura;
	private String descLinea;
	private String nomeBandoLinea;
	private String tipoOperazione;
	private String statoProgetto;
	private String dimensioneImpresa;
	private String responsabilitaGestionale;
	private String titoloProgetto;
	private String beneficiario;
	private String comuneSedeLegale;
	private String comuneSedeIntervento;
	private String provinciaSedeIntervento;
	private BigDecimal contributoPubblicoConcesso;
	private BigDecimal importoRendicontato;
	private BigDecimal costoAmmesso;
	private String provinciaSedeLegale;
	private BigDecimal importoRecuperiPrerecuperi;
	private BigDecimal impInteressiRecuperatiNetti;
	private BigDecimal impCertificabileNettoSoppr;
	private BigDecimal impRevocheNettoSoppressioni;
	private BigDecimal impCertificabileNettoRevoc;
	private BigDecimal importoRevocheIntermedio;
	private BigDecimal impCertificazioneNettoPrec;
	private BigDecimal avanzamento;
	private BigDecimal importoSoppressioniNetto;
	private BigDecimal importoInteressiRecuperati;
	private BigDecimal importoCertNetAnnoPrec;
	private BigDecimal importoCertNetAnnoInCorso;
	//JIRA aggiunta colonna IMPORTO_CERT_NET_ANNI_PREC
	private BigDecimal importoCertNetAnniPrec;
	private String codLineaAzione;
	
	//Jira PBANDI-2826
	private String cup;
	private String codiceFiscaleSoggetto;
	
	private BigDecimal valoreN;
	private BigDecimal valoreN_1;
	private BigDecimal sommaValoriN;
	
	// Jira PBANDI-2882.
	private BigDecimal idProgettoSif;
	
	public BigDecimal getIdProgettoSif() {
		return idProgettoSif;
	}

	public void setIdProgettoSif(BigDecimal idProgettoSif) {
		this.idProgettoSif = idProgettoSif;
	}

	public BigDecimal getImportoFideiussioni() {
		return importoFideiussioni;
	}

	public void setImportoFideiussioni(BigDecimal importoFideiussioni) {
		this.importoFideiussioni = importoFideiussioni;
	}

	public BigDecimal getImportoPagamentiValidati() {
		return importoPagamentiValidati;
	}

	public void setImportoPagamentiValidati(BigDecimal importoPagamentiValidati) {
		this.importoPagamentiValidati = importoPagamentiValidati;
	}

	public BigDecimal getImportoEccendenzeValidazione() {
		return importoEccendenzeValidazione;
	}

	public void setImportoEccendenzeValidazione(
			BigDecimal importoEccendenzeValidazione) {
		this.importoEccendenzeValidazione = importoEccendenzeValidazione;
	}

	public BigDecimal getImportoErogazioni() {
		return importoErogazioni;
	}

	public void setImportoErogazioni(BigDecimal importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}

	public BigDecimal getSpesaCertificabileLorda() {
		return spesaCertificabileLorda;
	}

	public void setSpesaCertificabileLorda(BigDecimal spesaCertificabileLorda) {
		this.spesaCertificabileLorda = spesaCertificabileLorda;
	}

	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}

	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}

	public java.sql.Date getDtCutOffErogazioni() {
		return dtCutOffErogazioni;
	}

	public void setDtCutOffErogazioni(java.sql.Date dtCutOffErogazioni) {
		this.dtCutOffErogazioni = dtCutOffErogazioni;
	}

	public java.sql.Date getDtCutOffFideiussioni() {
		return dtCutOffFideiussioni;
	}

	public void setDtCutOffFideiussioni(java.sql.Date dtCutOffFideiussioni) {
		this.dtCutOffFideiussioni = dtCutOffFideiussioni;
	}

	public java.sql.Date getDtCutOffPagamenti() {
		return dtCutOffPagamenti;
	}

	public void setDtCutOffPagamenti(java.sql.Date dtCutOffPagamenti) {
		this.dtCutOffPagamenti = dtCutOffPagamenti;
	}

	public java.sql.Date getDtCutOffValidazioni() {
		return dtCutOffValidazioni;
	}

	public void setDtCutOffValidazioni(java.sql.Date dtCutOffValidazioni) {
		this.dtCutOffValidazioni = dtCutOffValidazioni;
	}

	public String getDescProposta() {
		return descProposta;
	}

	public void setDescProposta(String descProposta) {
		this.descProposta = descProposta;
	}

	public String getFlagRa() {
		return flagRa;
	}

	public void setFlagRa(String flagRa) {
		this.flagRa = flagRa;
	}

	public BigDecimal getCpupor1() {
		return cpupor1;
	}

	public void setCpupor1(BigDecimal cpupor1) {
		this.cpupor1 = cpupor1;
	}

	public BigDecimal getCpupor2() {
		return cpupor2;
	}

	public void setCpupor2(BigDecimal cpupor2) {
		this.cpupor2 = cpupor2;
	}

	public BigDecimal getCpupor3() {
		return cpupor3;
	}

	public void setCpupor3(BigDecimal cpupor3) {
		this.cpupor3 = cpupor3;
	}

	public BigDecimal getCofpor() {
		return cofpor;
	}

	public void setCofpor(BigDecimal cofpor) {
		this.cofpor = cofpor;
	}

	public BigDecimal getOthfin() {
		return othfin;
	}

	public void setOthfin(BigDecimal othfin) {
		this.othfin = othfin;
	}

	public BigDecimal getCpupor1_fesr() {
		return cpupor1_fesr;
	}

	public void setCpupor1_fesr(BigDecimal cpupor1Fesr) {
		cpupor1_fesr = cpupor1Fesr;
	}

	public BigDecimal getCpupor2_fesr() {
		return cpupor2_fesr;
	}

	public void setCpupor2_fesr(BigDecimal cpupor2Fesr) {
		cpupor2_fesr = cpupor2Fesr;
	}

	public BigDecimal getCpupor3_fesr() {
		return cpupor3_fesr;
	}

	public void setCpupor3_fesr(BigDecimal cpupor3Fesr) {
		cpupor3_fesr = cpupor3Fesr;
	}

	public void setDtOraCreazione(java.sql.Date dtOraCreazione) {
		this.dtOraCreazione = dtOraCreazione;
	}

	public java.sql.Date getDtOraCreazione() {
		return dtOraCreazione;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setDescProgramma(String descProgramma) {
		this.descProgramma = descProgramma;
	}

	public String getDescProgramma() {
		return descProgramma;
	}

	public void setDescAsse(String descAsse) {
		this.descAsse = descAsse;
	}

	public String getDescAsse() {
		return descAsse;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}

	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}

	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setImportoFidUtilizzate(BigDecimal importoFidUtilizzate) {
		this.importoFidUtilizzate = importoFidUtilizzate;
	}

	public BigDecimal getImportoFidUtilizzate() {
		return importoFidUtilizzate;
	}

	public BigDecimal getImportoRevoche() {
		return importoRevoche;
	}

	public void setImportoRevoche(BigDecimal importoRevoche) {
		this.importoRevoche = importoRevoche;
	}

	public java.sql.Date getDtUltimaRevoca() {
		return dtUltimaRevoca;
	}

	public void setDtUltimaRevoca(java.sql.Date dtUltimaRevoca) {
		this.dtUltimaRevoca = dtUltimaRevoca;
	}

	public BigDecimal getImportoDaRecuperare() {
		return importoDaRecuperare;
	}

	public void setImportoDaRecuperare(BigDecimal importoDaRecuperare) {
		this.importoDaRecuperare = importoDaRecuperare;
	}

	public BigDecimal getImportoPrerecuperi() {
		return importoPrerecuperi;
	}

	public void setImportoPrerecuperi(BigDecimal importoPrerecuperi) {
		this.importoPrerecuperi = importoPrerecuperi;
	}

	public java.sql.Date getDtUltimoPrerecupero() {
		return dtUltimoPrerecupero;
	}

	public void setDtUltimoPrerecupero(java.sql.Date dtUltimoPrerecupero) {
		this.dtUltimoPrerecupero = dtUltimoPrerecupero;
	}

	public BigDecimal getImportoRecuperi() {
		return importoRecuperi;
	}

	public void setImportoRecuperi(BigDecimal importoRecuperi) {
		this.importoRecuperi = importoRecuperi;
	}

	public java.sql.Date getDtUltimoRecupero() {
		return dtUltimoRecupero;
	}

	public void setDtUltimoRecupero(java.sql.Date dtUltimoRecupero) {
		this.dtUltimoRecupero = dtUltimoRecupero;
	}

	public BigDecimal getImportoSoppressioni() {
		return importoSoppressioni;
	}

	public void setImportoSoppressioni(BigDecimal importoSoppressioni) {
		this.importoSoppressioni = importoSoppressioni;
	}

	public java.sql.Date getDtUltimaSoppressione() {
		return dtUltimaSoppressione;
	}

	public void setDtUltimaSoppressione(java.sql.Date dtUltimaSoppressione) {
		this.dtUltimaSoppressione = dtUltimaSoppressione;
	}

	public BigDecimal getImportoNonRilevanteCertif() {
		return importoNonRilevanteCertif;
	}

	public void setImportoNonRilevanteCertif(
			BigDecimal importoNonRilevanteCertif) {
		this.importoNonRilevanteCertif = importoNonRilevanteCertif;
	}

	public BigDecimal getImportoCertificazioneNetto() {
		return importoCertificazioneNetto;
	}

	public void setImportoCertificazioneNetto(
			BigDecimal importoCertificazioneNetto) {
		this.importoCertificazioneNetto = importoCertificazioneNetto;
	}

	public String getIdentificativiIrregolarita() {
		return identificativiIrregolarita;
	}

	public void setIdentificativiIrregolarita(String identificativiIrregolarita) {
		this.identificativiIrregolarita = identificativiIrregolarita;
	}

	public BigDecimal getErogazioniInMenoPerErrore() {
		return erogazioniInMenoPerErrore;
	}

	public void setErogazioniInMenoPerErrore(
			BigDecimal erogazioniInMenoPerErrore) {
		this.erogazioniInMenoPerErrore = erogazioniInMenoPerErrore;
	}

	public BigDecimal getFideiussioniInMenoPerError() {
		return fideiussioniInMenoPerError;
	}

	public void setFideiussioniInMenoPerError(
			BigDecimal fideiussioniInMenoPerError) {
		this.fideiussioniInMenoPerError = fideiussioniInMenoPerError;
	}

	public BigDecimal getRecuperiInMenoPerErrore() {
		return recuperiInMenoPerErrore;
	}

	public void setRecuperiInMenoPerErrore(BigDecimal recuperiInMenoPerErrore) {
		this.recuperiInMenoPerErrore = recuperiInMenoPerErrore;
	}

	public BigDecimal getSoppressioniInMenoPerError() {
		return soppressioniInMenoPerError;
	}

	public void setSoppressioniInMenoPerError(
			BigDecimal soppressioniInMenoPerError) {
		this.soppressioniInMenoPerError = soppressioniInMenoPerError;
	}

	public BigDecimal getPrerecuperiInMenoPerError() {
		return prerecuperiInMenoPerError;
	}

	public void setPrerecuperiInMenoPerError(
			BigDecimal prerecuperiInMenoPerError) {
		this.prerecuperiInMenoPerError = prerecuperiInMenoPerError;
	}

	public BigDecimal getRevocheInMenoPerError() {
		return revocheInMenoPerError;
	}

	public void setRevocheInMenoPerError(BigDecimal revocheInMenoPerError) {
		this.revocheInMenoPerError = revocheInMenoPerError;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setFlagLc(String flagLc) {
		this.flagLc = flagLc;
	}

	public String getFlagLc() {
		return flagLc;
	}

	public void setDtUltimaChecklistInLoco(java.sql.Date dtUltimaChecklistInLoco) {
		this.dtUltimaChecklistInLoco = dtUltimaChecklistInLoco;
	}

	public java.sql.Date getDtUltimaChecklistInLoco() {
		return dtUltimaChecklistInLoco;
	}

	public String getDescMisura() {
		return descMisura;
	}

	public void setDescMisura(String descMisura) {
		this.descMisura = descMisura;
	}

	public String getDescLinea() {
		return descLinea;
	}

	public void setDescLinea(String descLinea) {
		this.descLinea = descLinea;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public String getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public String getStatoProgetto() {
		return statoProgetto;
	}

	public void setStatoProgetto(String statoProgetto) {
		this.statoProgetto = statoProgetto;
	}

	public String getDimensioneImpresa() {
		return dimensioneImpresa;
	}

	public void setDimensioneImpresa(String dimensioneImpresa) {
		this.dimensioneImpresa = dimensioneImpresa;
	}

	public String getResponsabilitaGestionale() {
		return responsabilitaGestionale;
	}

	public void setResponsabilitaGestionale(String responsabilitaGestionale) {
		this.responsabilitaGestionale = responsabilitaGestionale;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getComuneSedeLegale() {
		return comuneSedeLegale;
	}

	public void setComuneSedeLegale(String comuneSedeLegale) {
		this.comuneSedeLegale = comuneSedeLegale;
	}

	public BigDecimal getContributoPubblicoConcesso() {
		return contributoPubblicoConcesso;
	}

	public void setContributoPubblicoConcesso(BigDecimal contributoPubblicoConcesso) {
		this.contributoPubblicoConcesso = contributoPubblicoConcesso;
	}

	public BigDecimal getImportoRendicontato() {
		return importoRendicontato;
	}

	public void setImportoRendicontato(BigDecimal importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}

	public BigDecimal getCostoAmmesso() {
		return costoAmmesso;
	}

	public void setCostoAmmesso(BigDecimal costoAmmesso) {
		this.costoAmmesso = costoAmmesso;
	}

	public void setComuneSedeIntervento(String comuneSedeIntervento) {
		this.comuneSedeIntervento = comuneSedeIntervento;
	}

	public String getComuneSedeIntervento() {
		return comuneSedeIntervento;
	}

	public void setProvinciaSedeIntervento(String provinciaSedeIntervento) {
		this.provinciaSedeIntervento = provinciaSedeIntervento;
	}

	public String getProvinciaSedeIntervento() {
		return provinciaSedeIntervento;
	}

	public String getProvinciaSedeLegale() {
		return provinciaSedeLegale;
	}

	public void setProvinciaSedeLegale(String provinciaSedeLegale) {
		this.provinciaSedeLegale = provinciaSedeLegale;
	}

	public BigDecimal getAvanzamento() {
		return avanzamento;
	}

	public void setAvanzamento(BigDecimal avanzamento) {
		this.avanzamento = avanzamento;
	}

	public BigDecimal getImportoRecuperiPrerecuperi() {
		return importoRecuperiPrerecuperi;
	}

	public void setImportoRecuperiPrerecuperi(BigDecimal importoRecuperiPrerecuperi) {
		this.importoRecuperiPrerecuperi = importoRecuperiPrerecuperi;
	}

	public BigDecimal getImpInteressiRecuperatiNetti() {
		return impInteressiRecuperatiNetti;
	}

	public void setImpInteressiRecuperatiNetti(
			BigDecimal impInteressiRecuperatiNetti) {
		this.impInteressiRecuperatiNetti = impInteressiRecuperatiNetti;
	}

	public BigDecimal getImpCertificabileNettoSoppr() {
		return impCertificabileNettoSoppr;
	}

	public void setImpCertificabileNettoSoppr(BigDecimal impCertificabileNettoSoppr) {
		this.impCertificabileNettoSoppr = impCertificabileNettoSoppr;
	}

	public BigDecimal getImpRevocheNettoSoppressioni() {
		return impRevocheNettoSoppressioni;
	}

	public void setImpRevocheNettoSoppressioni(
			BigDecimal impRevocheNettoSoppressioni) {
		this.impRevocheNettoSoppressioni = impRevocheNettoSoppressioni;
	}

	public BigDecimal getImpCertificabileNettoRevoc() {
		return impCertificabileNettoRevoc;
	}

	public void setImpCertificabileNettoRevoc(BigDecimal impCertificabileNettoRevoc) {
		this.impCertificabileNettoRevoc = impCertificabileNettoRevoc;
	}

	public BigDecimal getImportoRevocheIntermedio() {
		return importoRevocheIntermedio;
	}

	public void setImportoRevocheIntermedio(BigDecimal importoRevocheIntermedio) {
		this.importoRevocheIntermedio = importoRevocheIntermedio;
	}

	public BigDecimal getImpCertificazioneNettoPrec() {
		return impCertificazioneNettoPrec;
	}

	public void setImpCertificazioneNettoPrec(BigDecimal impCertificazioneNettoPrec) {
		this.impCertificazioneNettoPrec = impCertificazioneNettoPrec;
	}

	public void setImportoSoppressioniNetto(BigDecimal importoSoppressioniNetto) {
		this.importoSoppressioniNetto = importoSoppressioniNetto;
	}

	public BigDecimal getImportoSoppressioniNetto() {
		return importoSoppressioniNetto;
	}

	public void setImportoInteressiRecuperati(BigDecimal importoInteressiRecuperati) {
		this.importoInteressiRecuperati = importoInteressiRecuperati;
	}

	public BigDecimal getImportoInteressiRecuperati() {
		return importoInteressiRecuperati;
	}

	public BigDecimal getCpufasSta() {
		return cpufasSta;
	}

	public void setCpufasSta(BigDecimal cpufasSta) {
		this.cpufasSta = cpufasSta;
	}

	public BigDecimal getCpufasReg() {
		return cpufasReg;
	}

	public void setCpufasReg(BigDecimal cpufasReg) {
		this.cpufasReg = cpufasReg;
	}
	
	public BigDecimal getImpCertifNettoPremodifica(){
		return impCertifNettoPremodifica;
	}
	
	public void setImpCertifNettoPremodifica(BigDecimal impCertifNettoPremodifica){
		this.impCertifNettoPremodifica = impCertifNettoPremodifica;
	}
	
	//Modifica 20.01.2017 M.E.
	public void setQuotaRevocaPerIrr(BigDecimal quotaRevocaXIrr){
		this.quotaRevocaPerIrr = quotaRevocaXIrr;
	}
	
	//Modifica 20.01.2017 M.E.
	public BigDecimal getQuotaRevocaPerIrr(){
		return this.quotaRevocaPerIrr;
	}

	public BigDecimal getImportoCertNetAnnoPrec() {
		return importoCertNetAnnoPrec;
	}

	public void setImportoCertNetAnnoPrec(BigDecimal importoCertNetAnnoPrec) {
		this.importoCertNetAnnoPrec = importoCertNetAnnoPrec;
	}

	public BigDecimal getImportoCertNetAnnoInCorso() {
		return importoCertNetAnnoInCorso;
	}

	public void setImportoCertNetAnnoInCorso(BigDecimal importoCertNetAnnoInCorso) {
		this.importoCertNetAnnoInCorso = importoCertNetAnnoInCorso;
	}

	public BigDecimal getImportoCertNetAnniPrec() {
		return importoCertNetAnniPrec;
	}

	public void setImportoCertNetAnniPrec(BigDecimal importoCertNetAnniPrec) {
		this.importoCertNetAnniPrec = importoCertNetAnniPrec;
	}

	public String getCodLineaAzione() {
		return codLineaAzione;
	}

	public void setCodLineaAzione(String codLineaAzione) {
		this.codLineaAzione = codLineaAzione;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public BigDecimal getImportoPagValidatiOrig() {
		return importoPagValidatiOrig;
	}

	public void setImportoPagValidatiOrig(BigDecimal importoPagValidatiOrig) {
		this.importoPagValidatiOrig = importoPagValidatiOrig;
	}

	public BigDecimal getValoreN() {
		return valoreN;
	}

	public void setValoreN(BigDecimal valoreN) {
		this.valoreN = valoreN;
	}


	public BigDecimal getValoreN_1() {
		return valoreN_1;
	}

	public void setValoreN_1(BigDecimal valoreN_1) {
		this.valoreN_1 = valoreN_1;
	}

	public BigDecimal getSommaValoriN() {
		return sommaValoriN;
	}

	public void setSommaValoriN(BigDecimal sommaValoriN) {
		this.sommaValoriN = sommaValoriN;
	}

	@Override
	public String toString() {
		return "ReportPropostaCertificazionePorFesrVO{" +
				"descProgramma='" + descProgramma + '\'' +
				", descAsse='" + descAsse + '\'' +
				", codiceVisualizzato='" + codiceVisualizzato + '\'' +
				", idPropostaCertificaz=" + idPropostaCertificaz +
				", idLineaDiIntervento=" + idLineaDiIntervento +
				", idDettPropostaCertif=" + idDettPropostaCertif +
				", dtOraCreazione=" + dtOraCreazione +
				", dtCutOffPagamenti=" + dtCutOffPagamenti +
				", dtCutOffValidazioni=" + dtCutOffValidazioni +
				", dtCutOffErogazioni=" + dtCutOffErogazioni +
				", dtCutOffFideiussioni=" + dtCutOffFideiussioni +
				", descProposta='" + descProposta + '\'' +
				", idProgetto=" + idProgetto +
				", flagRa='" + flagRa + '\'' +
				", cpupor1=" + cpupor1 +
				", cpupor2=" + cpupor2 +
				", cpupor3=" + cpupor3 +
				", cofpor=" + cofpor +
				", othfin=" + othfin +
				", cpupor1_fesr=" + cpupor1_fesr +
				", cpupor2_fesr=" + cpupor2_fesr +
				", cpupor3_fesr=" + cpupor3_fesr +
				", cpufasSta=" + cpufasSta +
				", cpufasReg=" + cpufasReg +
				", importoFideiussioni=" + importoFideiussioni +
				", importoFidUtilizzate=" + importoFidUtilizzate +
				", importoPagValidatiOrig=" + importoPagValidatiOrig +
				", importoPagamentiValidati=" + importoPagamentiValidati +
				", importoEccendenzeValidazione=" + importoEccendenzeValidazione +
				", importoErogazioni=" + importoErogazioni +
				", spesaCertificabileLorda=" + spesaCertificabileLorda +
				", importoRevoche=" + importoRevoche +
				", quotaRevocaPerIrr=" + quotaRevocaPerIrr +
				", dtUltimaRevoca=" + dtUltimaRevoca +
				", importoDaRecuperare=" + importoDaRecuperare +
				", importoPrerecuperi=" + importoPrerecuperi +
				", dtUltimoPrerecupero=" + dtUltimoPrerecupero +
				", importoRecuperi=" + importoRecuperi +
				", dtUltimoRecupero=" + dtUltimoRecupero +
				", importoSoppressioni=" + importoSoppressioni +
				", dtUltimaSoppressione=" + dtUltimaSoppressione +
				", importoNonRilevanteCertif=" + importoNonRilevanteCertif +
				", importoCertificazioneNetto=" + importoCertificazioneNetto +
				", impCertifNettoPremodifica=" + impCertifNettoPremodifica +
				", identificativiIrregolarita='" + identificativiIrregolarita + '\'' +
				", erogazioniInMenoPerErrore=" + erogazioniInMenoPerErrore +
				", fideiussioniInMenoPerError=" + fideiussioniInMenoPerError +
				", recuperiInMenoPerErrore=" + recuperiInMenoPerErrore +
				", soppressioniInMenoPerError=" + soppressioniInMenoPerError +
				", prerecuperiInMenoPerError=" + prerecuperiInMenoPerError +
				", revocheInMenoPerError=" + revocheInMenoPerError +
				", progrBandoLineaIntervento=" + progrBandoLineaIntervento +
				", flagLc='" + flagLc + '\'' +
				", dtUltimaChecklistInLoco=" + dtUltimaChecklistInLoco +
				", descMisura='" + descMisura + '\'' +
				", descLinea='" + descLinea + '\'' +
				", nomeBandoLinea='" + nomeBandoLinea + '\'' +
				", tipoOperazione='" + tipoOperazione + '\'' +
				", statoProgetto='" + statoProgetto + '\'' +
				", dimensioneImpresa='" + dimensioneImpresa + '\'' +
				", responsabilitaGestionale='" + responsabilitaGestionale + '\'' +
				", titoloProgetto='" + titoloProgetto + '\'' +
				", beneficiario='" + beneficiario + '\'' +
				", comuneSedeLegale='" + comuneSedeLegale + '\'' +
				", comuneSedeIntervento='" + comuneSedeIntervento + '\'' +
				", provinciaSedeIntervento='" + provinciaSedeIntervento + '\'' +
				", contributoPubblicoConcesso=" + contributoPubblicoConcesso +
				", importoRendicontato=" + importoRendicontato +
				", costoAmmesso=" + costoAmmesso +
				", provinciaSedeLegale='" + provinciaSedeLegale + '\'' +
				", importoRecuperiPrerecuperi=" + importoRecuperiPrerecuperi +
				", impInteressiRecuperatiNetti=" + impInteressiRecuperatiNetti +
				", impCertificabileNettoSoppr=" + impCertificabileNettoSoppr +
				", impRevocheNettoSoppressioni=" + impRevocheNettoSoppressioni +
				", impCertificabileNettoRevoc=" + impCertificabileNettoRevoc +
				", importoRevocheIntermedio=" + importoRevocheIntermedio +
				", impCertificazioneNettoPrec=" + impCertificazioneNettoPrec +
				", avanzamento=" + avanzamento +
				", importoSoppressioniNetto=" + importoSoppressioniNetto +
				", importoInteressiRecuperati=" + importoInteressiRecuperati +
				", importoCertNetAnnoPrec=" + importoCertNetAnnoPrec +
				", importoCertNetAnnoInCorso=" + importoCertNetAnnoInCorso +
				", importoCertNetAnniPrec=" + importoCertNetAnniPrec +
				", codLineaAzione='" + codLineaAzione + '\'' +
				", cup='" + cup + '\'' +
				", codiceFiscaleSoggetto='" + codiceFiscaleSoggetto + '\'' +
				", valoreN=" + valoreN +
				", valoreN_1=" + valoreN_1 +
				", sommaValoriN=" + sommaValoriN +
				", idProgettoSif=" + idProgettoSif +
				'}';
	}
}
