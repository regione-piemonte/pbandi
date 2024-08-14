/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;


public class PbandiTDettPropostaCertifVO {
private BigDecimal idUtenteIns;
  	
  	private String nomeBandoLinea;
  	
  	private BigDecimal importoSoppressioniNetto;
  	
  	private Date dtUltimaChecklistInLoco;
  	
  	private BigDecimal recuperiInMenoPerErrore;
  	
  	private BigDecimal impInteressiRecuperatiNetti;
  	
  	private Date dtUltimaSoppressione;
  	
  	private BigDecimal contributoPubblicoConcesso;
  	
  	private BigDecimal soppressioniInMenoPerError;
  	
  	private BigDecimal idStatoProgetto;
  	
  	private BigDecimal importoSoppressioni;
  	
  	private BigDecimal importoCertificazioneNetto;
  	
  	private BigDecimal idTipoOperazione;
  	
  	private BigDecimal idPersonaFisica;
  	
  	private String titoloProgetto;
  	
  	private BigDecimal prerecuperiInMenoPerError;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idEnteGiuridico;
  	
  	private BigDecimal revocheInMenoPerError;
  	
  	private BigDecimal importoPrerecuperi;
  	
  	private BigDecimal importoDaRecuperare;
  	
  	private String flagCheckListCertificazione;
  	
  	private BigDecimal impCertificazioneNettoPrec;
  	
  	private BigDecimal importoRendicontato;
  	
  	private String flagAttivo;
  	
  	private BigDecimal importoRevoche;
  	
  	private Date dtUltimaRevoca;
  	
  	private BigDecimal idPropostaCertificaz;
  	
  	private Date dtUltimoRecupero;
  	
  	private BigDecimal impCertificabileNettoRevoc;
  	
  	private BigDecimal erogazioniInMenoPerErrore;
  	
  	private BigDecimal spesaCertificabileLorda;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal importoEccendenzeValidazione;
  	
  	private BigDecimal impCertificabileNettoSoppr;
  	
  	private BigDecimal idIndirizzoSedeLegale;
  	
  	private Date dtUltimoPrerecupero;
  	
  	private BigDecimal importoNonRilevanteCertif;
  	
  	private String identificativiIrregolarita;
  	
  	private BigDecimal importoErogazioni;
  	
  	private BigDecimal importoRevocheIntermedio;
  	
  	private BigDecimal importoFideiussioni;
  	
  	private BigDecimal avanzamento;
  	
  	private BigDecimal importoPagamentiValidati;
  	
  	private BigDecimal idDimensioneImpresa;
  	
  	private BigDecimal fideiussioniInMenoPerError;
  	
  	private BigDecimal costoAmmesso;
  	
  	private String flagCheckListInLoco;
  	
  	private BigDecimal importoInteressiRecuperati;
  	
  	private BigDecimal idDettPropostaCertif;
  	
  	private BigDecimal importoRecuperi;
  	
  	private String flagComp;
  	
  	private BigDecimal impRevocheNettoSoppressioni;
  	
  	private BigDecimal importoRecuperiPrerecuperi;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal impCertifNettoPremodifica;
  	
  	private String codiceProgetto;
  	
  	private String denominazioneBeneficiario;
  	
  	private String nota;
  	
  	
	public PbandiTDettPropostaCertifVO() {}
  	
	public PbandiTDettPropostaCertifVO (BigDecimal idDettPropostaCertif) {
	
		this. idDettPropostaCertif =  idDettPropostaCertif;
	}
  	
	public PbandiTDettPropostaCertifVO (BigDecimal idUtenteIns, String nomeBandoLinea,
			BigDecimal importoSoppressioniNetto, Date dtUltimaChecklistInLoco, 
			BigDecimal recuperiInMenoPerErrore, BigDecimal impInteressiRecuperatiNetti,
			Date dtUltimaSoppressione, BigDecimal contributoPubblicoConcesso, 
			BigDecimal soppressioniInMenoPerError, BigDecimal idStatoProgetto, 
			BigDecimal importoSoppressioni, BigDecimal importoCertificazioneNetto, 
			BigDecimal idTipoOperazione, BigDecimal idPersonaFisica, String titoloProgetto, 
			BigDecimal prerecuperiInMenoPerError, BigDecimal idUtenteAgg, BigDecimal idEnteGiuridico, 
			BigDecimal revocheInMenoPerError, BigDecimal importoPrerecuperi, BigDecimal importoDaRecuperare, 
			String flagCheckListCertificazione, BigDecimal impCertificazioneNettoPrec, BigDecimal importoRendicontato, 
			String flagAttivo, BigDecimal importoRevoche, Date dtUltimaRevoca, BigDecimal idPropostaCertificaz, 
			Date dtUltimoRecupero, BigDecimal impCertificabileNettoRevoc, BigDecimal erogazioniInMenoPerErrore, 
			BigDecimal spesaCertificabileLorda, BigDecimal idProgetto, BigDecimal importoEccendenzeValidazione, 
			BigDecimal impCertificabileNettoSoppr, BigDecimal idIndirizzoSedeLegale, Date dtUltimoPrerecupero, 
			BigDecimal importoNonRilevanteCertif, String identificativiIrregolarita, BigDecimal importoErogazioni, 
			BigDecimal importoRevocheIntermedio, BigDecimal importoFideiussioni, BigDecimal avanzamento, 
			BigDecimal importoPagamentiValidati, BigDecimal idDimensioneImpresa, BigDecimal fideiussioniInMenoPerError, 
			BigDecimal costoAmmesso, String flagCheckListInLoco, BigDecimal importoInteressiRecuperati, 
			BigDecimal idDettPropostaCertif, BigDecimal importoRecuperi, String flagComp, BigDecimal impRevocheNettoSoppressioni, 
			BigDecimal importoRecuperiPrerecuperi, String codiceProgetto, String denominazioneBeneficiario, BigDecimal impCertifNettoPremodifica, 
			BigDecimal idLineaDiIntervento, String nota) {
	
		this. idUtenteIns 					=  idUtenteIns;
		this. nomeBandoLinea 				=  nomeBandoLinea;
		this. importoSoppressioniNetto 		=  importoSoppressioniNetto;
		this. dtUltimaChecklistInLoco 		=  dtUltimaChecklistInLoco;
		this. recuperiInMenoPerErrore 		=  recuperiInMenoPerErrore;
		this. impInteressiRecuperatiNetti 	=  impInteressiRecuperatiNetti;
		this. dtUltimaSoppressione 			=  dtUltimaSoppressione;
		this. contributoPubblicoConcesso 	=  contributoPubblicoConcesso;
		this. soppressioniInMenoPerError 	=  soppressioniInMenoPerError;
		this. idStatoProgetto 				=  idStatoProgetto;
		this. importoSoppressioni 			=  importoSoppressioni;
		this. importoCertificazioneNetto	=  importoCertificazioneNetto;
		this. idTipoOperazione 				=  idTipoOperazione;
		this. idPersonaFisica 				=  idPersonaFisica;
		this. titoloProgetto 				=  titoloProgetto;
		this. prerecuperiInMenoPerError 	=  prerecuperiInMenoPerError;
		this. idUtenteAgg 					=  idUtenteAgg;
		this. idEnteGiuridico 				=  idEnteGiuridico;
		this. revocheInMenoPerError 		=  revocheInMenoPerError;
		this. importoPrerecuperi 			=  importoPrerecuperi;
		this. importoDaRecuperare 			=  importoDaRecuperare;
		this. flagCheckListCertificazione 	=  flagCheckListCertificazione;
		this. impCertificazioneNettoPrec 	=  impCertificazioneNettoPrec;
		this. importoRendicontato 			=  importoRendicontato;
		this. flagAttivo 					=  flagAttivo;
		this. importoRevoche 				=  importoRevoche;
		this. dtUltimaRevoca 				=  dtUltimaRevoca;
		this. idPropostaCertificaz 			=  idPropostaCertificaz;
		this. dtUltimoRecupero 				=  dtUltimoRecupero;
		this. impCertificabileNettoRevoc 	=  impCertificabileNettoRevoc;
		this. erogazioniInMenoPerErrore 	=  erogazioniInMenoPerErrore;
		this. spesaCertificabileLorda	 	=  spesaCertificabileLorda;
		this. idProgetto 					=  idProgetto;
		this. importoEccendenzeValidazione 	=  importoEccendenzeValidazione;
		this. impCertificabileNettoSoppr 	=  impCertificabileNettoSoppr;
		this. idIndirizzoSedeLegale 		=  idIndirizzoSedeLegale;
		this. dtUltimoPrerecupero 			=  dtUltimoPrerecupero;
		this. importoNonRilevanteCertif 	=  importoNonRilevanteCertif;
		this. identificativiIrregolarita 	=  identificativiIrregolarita;
		this. importoErogazioni 			=  importoErogazioni;
		this. importoRevocheIntermedio 		=  importoRevocheIntermedio;
		this. importoFideiussioni 			=  importoFideiussioni;
		this. avanzamento 					=  avanzamento;
		this. importoPagamentiValidati 		=  importoPagamentiValidati;
		this. idDimensioneImpresa 			=  idDimensioneImpresa;
		this. fideiussioniInMenoPerError 	=  fideiussioniInMenoPerError;
		this. costoAmmesso 					=  costoAmmesso;
		this. flagCheckListInLoco 			=  flagCheckListInLoco;
		this. importoInteressiRecuperati 	=  importoInteressiRecuperati;
		this. idDettPropostaCertif 			=  idDettPropostaCertif;
		this. importoRecuperi 				=  importoRecuperi;
		this. flagComp 						=  flagComp;
		this. impRevocheNettoSoppressioni 	=  impRevocheNettoSoppressioni;
		this. importoRecuperiPrerecuperi 	=  importoRecuperiPrerecuperi;
		this. denominazioneBeneficiario 	=  denominazioneBeneficiario;
		this. codiceProgetto 				=  codiceProgetto;
		this. idLineaDiIntervento			=  idLineaDiIntervento;
		this. impCertifNettoPremodifica 	=  impCertifNettoPremodifica;
		this. nota 							=  nota;
	}
  	
  	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	
	public BigDecimal getImportoSoppressioniNetto() {
		return importoSoppressioniNetto;
	}
	
	public void setImportoSoppressioniNetto(BigDecimal importoSoppressioniNetto) {
		this.importoSoppressioniNetto = importoSoppressioniNetto;
	}
	
	public Date getDtUltimaChecklistInLoco() {
		return dtUltimaChecklistInLoco;
	}
	
	public void setDtUltimaChecklistInLoco(Date dtUltimaChecklistInLoco) {
		this.dtUltimaChecklistInLoco = dtUltimaChecklistInLoco;
	}
	
	public BigDecimal getRecuperiInMenoPerErrore() {
		return recuperiInMenoPerErrore;
	}
	
	public void setRecuperiInMenoPerErrore(BigDecimal recuperiInMenoPerErrore) {
		this.recuperiInMenoPerErrore = recuperiInMenoPerErrore;
	}
	
	public BigDecimal getImpInteressiRecuperatiNetti() {
		return impInteressiRecuperatiNetti;
	}
	
	public void setImpInteressiRecuperatiNetti(BigDecimal impInteressiRecuperatiNetti) {
		this.impInteressiRecuperatiNetti = impInteressiRecuperatiNetti;
	}
	
	public Date getDtUltimaSoppressione() {
		return dtUltimaSoppressione;
	}
	
	public void setDtUltimaSoppressione(Date dtUltimaSoppressione) {
		this.dtUltimaSoppressione = dtUltimaSoppressione;
	}
	
	public BigDecimal getContributoPubblicoConcesso() {
		return contributoPubblicoConcesso;
	}
	
	public void setContributoPubblicoConcesso(BigDecimal contributoPubblicoConcesso) {
		this.contributoPubblicoConcesso = contributoPubblicoConcesso;
	}
	
	public BigDecimal getSoppressioniInMenoPerError() {
		return soppressioniInMenoPerError;
	}
	
	public void setSoppressioniInMenoPerError(BigDecimal soppressioniInMenoPerError) {
		this.soppressioniInMenoPerError = soppressioniInMenoPerError;
	}
	
	public BigDecimal getIdStatoProgetto() {
		return idStatoProgetto;
	}
	
	public void setIdStatoProgetto(BigDecimal idStatoProgetto) {
		this.idStatoProgetto = idStatoProgetto;
	}
	
	public BigDecimal getImportoSoppressioni() {
		return importoSoppressioni;
	}
	
	public void setImportoSoppressioni(BigDecimal importoSoppressioni) {
		this.importoSoppressioni = importoSoppressioni;
	}
	
	public BigDecimal getImportoCertificazioneNetto() {
		return importoCertificazioneNetto;
	}
	
	public void setImportoCertificazioneNetto(BigDecimal importoCertificazioneNetto) {
		this.importoCertificazioneNetto = importoCertificazioneNetto;
	}
	
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}
	
	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	
	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}
	
	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	
	public BigDecimal getPrerecuperiInMenoPerError() {
		return prerecuperiInMenoPerError;
	}
	
	public void setPrerecuperiInMenoPerError(BigDecimal prerecuperiInMenoPerError) {
		this.prerecuperiInMenoPerError = prerecuperiInMenoPerError;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	
	public BigDecimal getRevocheInMenoPerError() {
		return revocheInMenoPerError;
	}
	
	public void setRevocheInMenoPerError(BigDecimal revocheInMenoPerError) {
		this.revocheInMenoPerError = revocheInMenoPerError;
	}
	
	public BigDecimal getImportoPrerecuperi() {
		return importoPrerecuperi;
	}
	
	public void setImportoPrerecuperi(BigDecimal importoPrerecuperi) {
		this.importoPrerecuperi = importoPrerecuperi;
	}
	
	public BigDecimal getImportoDaRecuperare() {
		return importoDaRecuperare;
	}
	
	public void setImportoDaRecuperare(BigDecimal importoDaRecuperare) {
		this.importoDaRecuperare = importoDaRecuperare;
	}
	
	public String getFlagCheckListCertificazione() {
		return flagCheckListCertificazione;
	}
	
	public void setFlagCheckListCertificazione(String flagCheckListCertificazione) {
		this.flagCheckListCertificazione = flagCheckListCertificazione;
	}
	
	public BigDecimal getImpCertificazioneNettoPrec() {
		return impCertificazioneNettoPrec;
	}
	
	public void setImpCertificazioneNettoPrec(BigDecimal impCertificazioneNettoPrec) {
		this.impCertificazioneNettoPrec = impCertificazioneNettoPrec;
	}
	
	public BigDecimal getImportoRendicontato() {
		return importoRendicontato;
	}
	
	public void setImportoRendicontato(BigDecimal importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}
	
	public String getFlagAttivo() {
		return flagAttivo;
	}
	
	public void setFlagAttivo(String flagAttivo) {
		this.flagAttivo = flagAttivo;
	}
	
	public BigDecimal getImportoRevoche() {
		return importoRevoche;
	}
	
	public void setImportoRevoche(BigDecimal importoRevoche) {
		this.importoRevoche = importoRevoche;
	}
	
	public Date getDtUltimaRevoca() {
		return dtUltimaRevoca;
	}
	
	public void setDtUltimaRevoca(Date dtUltimaRevoca) {
		this.dtUltimaRevoca = dtUltimaRevoca;
	}
	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	
	public Date getDtUltimoRecupero() {
		return dtUltimoRecupero;
	}
	
	public void setDtUltimoRecupero(Date dtUltimoRecupero) {
		this.dtUltimoRecupero = dtUltimoRecupero;
	}
	
	public BigDecimal getImpCertificabileNettoRevoc() {
		return impCertificabileNettoRevoc;
	}
	
	public void setImpCertificabileNettoRevoc(BigDecimal impCertificabileNettoRevoc) {
		this.impCertificabileNettoRevoc = impCertificabileNettoRevoc;
	}
	
	public BigDecimal getErogazioniInMenoPerErrore() {
		return erogazioniInMenoPerErrore;
	}
	
	public void setErogazioniInMenoPerErrore(BigDecimal erogazioniInMenoPerErrore) {
		this.erogazioniInMenoPerErrore = erogazioniInMenoPerErrore;
	}
	
	public BigDecimal getSpesaCertificabileLorda() {
		return spesaCertificabileLorda;
	}
	
	public void setSpesaCertificabileLorda(BigDecimal spesaCertificabileLorda) {
		this.spesaCertificabileLorda = spesaCertificabileLorda;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getImportoEccendenzeValidazione() {
		return importoEccendenzeValidazione;
	}
	
	public void setImportoEccendenzeValidazione(BigDecimal importoEccendenzeValidazione) {
		this.importoEccendenzeValidazione = importoEccendenzeValidazione;
	}
	
	public BigDecimal getImpCertificabileNettoSoppr() {
		return impCertificabileNettoSoppr;
	}
	
	public void setImpCertificabileNettoSoppr(BigDecimal impCertificabileNettoSoppr) {
		this.impCertificabileNettoSoppr = impCertificabileNettoSoppr;
	}
	
	public BigDecimal getIdIndirizzoSedeLegale() {
		return idIndirizzoSedeLegale;
	}
	
	public void setIdIndirizzoSedeLegale(BigDecimal idIndirizzoSedeLegale) {
		this.idIndirizzoSedeLegale = idIndirizzoSedeLegale;
	}
	
	public Date getDtUltimoPrerecupero() {
		return dtUltimoPrerecupero;
	}
	
	public void setDtUltimoPrerecupero(Date dtUltimoPrerecupero) {
		this.dtUltimoPrerecupero = dtUltimoPrerecupero;
	}
	
	public BigDecimal getImportoNonRilevanteCertif() {
		return importoNonRilevanteCertif;
	}
	
	public void setImportoNonRilevanteCertif(BigDecimal importoNonRilevanteCertif) {
		this.importoNonRilevanteCertif = importoNonRilevanteCertif;
	}
	
	public String getIdentificativiIrregolarita() {
		return identificativiIrregolarita;
	}
	
	public void setIdentificativiIrregolarita(String identificativiIrregolarita) {
		this.identificativiIrregolarita = identificativiIrregolarita;
	}
	
	public BigDecimal getImportoErogazioni() {
		return importoErogazioni;
	}
	
	public void setImportoErogazioni(BigDecimal importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}
	
	public BigDecimal getImportoRevocheIntermedio() {
		return importoRevocheIntermedio;
	}
	
	public void setImportoRevocheIntermedio(BigDecimal importoRevocheIntermedio) {
		this.importoRevocheIntermedio = importoRevocheIntermedio;
	}
	
	public BigDecimal getImportoFideiussioni() {
		return importoFideiussioni;
	}
	
	public void setImportoFideiussioni(BigDecimal importoFideiussioni) {
		this.importoFideiussioni = importoFideiussioni;
	}
	
	public BigDecimal getAvanzamento() {
		return avanzamento;
	}
	
	public void setAvanzamento(BigDecimal avanzamento) {
		this.avanzamento = avanzamento;
	}
	
	public BigDecimal getImportoPagamentiValidati() {
		return importoPagamentiValidati;
	}
	
	public void setImportoPagamentiValidati(BigDecimal importoPagamentiValidati) {
		this.importoPagamentiValidati = importoPagamentiValidati;
	}
	
	public BigDecimal getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}
	
	public void setIdDimensioneImpresa(BigDecimal idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}
	
	public BigDecimal getFideiussioniInMenoPerError() {
		return fideiussioniInMenoPerError;
	}
	
	public void setFideiussioniInMenoPerError(BigDecimal fideiussioniInMenoPerError) {
		this.fideiussioniInMenoPerError = fideiussioniInMenoPerError;
	}
	
	public BigDecimal getCostoAmmesso() {
		return costoAmmesso;
	}
	
	public void setCostoAmmesso(BigDecimal costoAmmesso) {
		this.costoAmmesso = costoAmmesso;
	}
	
	public String getFlagCheckListInLoco() {
		return flagCheckListInLoco;
	}
	
	public void setFlagCheckListInLoco(String flagCheckListInLoco) {
		this.flagCheckListInLoco = flagCheckListInLoco;
	}
	
	public BigDecimal getImportoInteressiRecuperati() {
		return importoInteressiRecuperati;
	}
	
	public void setImportoInteressiRecuperati(BigDecimal importoInteressiRecuperati) {
		this.importoInteressiRecuperati = importoInteressiRecuperati;
	}
	
	public BigDecimal getIdDettPropostaCertif() {
		return idDettPropostaCertif;
	}
	
	public void setIdDettPropostaCertif(BigDecimal idDettPropostaCertif) {
		this.idDettPropostaCertif = idDettPropostaCertif;
	}
	
	public BigDecimal getImportoRecuperi() {
		return importoRecuperi;
	}
	
	public void setImportoRecuperi(BigDecimal importoRecuperi) {
		this.importoRecuperi = importoRecuperi;
	}
	
	public String getFlagComp() {
		return flagComp;
	}
	
	public void setFlagComp(String flagComp) {
		this.flagComp = flagComp;
	}
	
	public BigDecimal getImpRevocheNettoSoppressioni() {
		return impRevocheNettoSoppressioni;
	}
	
	public void setImpRevocheNettoSoppressioni(BigDecimal impRevocheNettoSoppressioni) {
		this.impRevocheNettoSoppressioni = impRevocheNettoSoppressioni;
	}
	
	public BigDecimal getImportoRecuperiPrerecuperi() {
		return importoRecuperiPrerecuperi;
	}
	
	public void setImportoRecuperiPrerecuperi(BigDecimal importoRecuperiPrerecuperi) {
		this.importoRecuperiPrerecuperi = importoRecuperiPrerecuperi;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null && flagCheckListCertificazione != null && flagAttivo != null && idPropostaCertificaz != null && idProgetto != null && flagCheckListInLoco != null && flagComp != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idDettPropostaCertif != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	//GETTER E SETTER Denominazione Beneficiario;
	
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario){
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	
	public String getDenominazioneBeneficiario(){
		return this.denominazioneBeneficiario;
	}
	
	//GETTER E SETTER Codice Progetto;
	
	public void setCodiceProgetto(String codiceProgetto){
		this.codiceProgetto = codiceProgetto;
	}
	
	public String getCodiceProgetto(){
		return this.codiceProgetto;
	}
	
	//GETTER E SETTER Importo Certificazione Netto Premodifica
	public void setImpCertifNettoPremodifica(BigDecimal impCertifNettoPremodifica){
		this.impCertifNettoPremodifica = impCertifNettoPremodifica;
	}
	
	public BigDecimal getImpCertifNettoPremodifica(){
		return this.impCertifNettoPremodifica;
	}
	
	//GETTER E SETTER id linea di intervento
	public BigDecimal getIdLineaDiIntervento(){
		return this.idLineaDiIntervento;
	}
	
	public String getNota(){
		return nota;
	}
	
	public void setNota(String nota){
		this.nota = nota;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento){
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
//	public String toString() {
//		
//	    String temp = "";
//	    StringBuffer sb = new StringBuffer();
//	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idUtenteIns);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( nomeBandoLinea);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeBandoLinea: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoSoppressioniNetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoSoppressioniNetto: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtUltimaChecklistInLoco);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtUltimaChecklistInLoco: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( recuperiInMenoPerErrore);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" recuperiInMenoPerErrore: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( impInteressiRecuperatiNetti);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" impInteressiRecuperatiNetti: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtUltimaSoppressione);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtUltimaSoppressione: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( contributoPubblicoConcesso);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" contributoPubblicoConcesso: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( soppressioniInMenoPerError);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" soppressioniInMenoPerError: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idStatoProgetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idStatoProgetto: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoSoppressioni);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoSoppressioni: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoCertificazioneNetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoCertificazioneNetto: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idTipoOperazione);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOperazione: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idPersonaFisica);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idPersonaFisica: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( titoloProgetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" titoloProgetto: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( prerecuperiInMenoPerError);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" prerecuperiInMenoPerError: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idUtenteAgg);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idEnteGiuridico);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteGiuridico: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( revocheInMenoPerError);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" revocheInMenoPerError: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoPrerecuperi);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoPrerecuperi: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoDaRecuperare);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoDaRecuperare: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( flagCheckListCertificazione);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCheckListCertificazione: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( impCertificazioneNettoPrec);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" impCertificazioneNettoPrec: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoRendicontato);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRendicontato: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( flagAttivo);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAttivo: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoRevoche);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRevoche: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtUltimaRevoca);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtUltimaRevoca: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idPropostaCertificaz);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtUltimoRecupero);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtUltimoRecupero: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( impCertificabileNettoRevoc);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" impCertificabileNettoRevoc: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( erogazioniInMenoPerErrore);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" erogazioniInMenoPerErrore: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( spesaCertificabileLorda);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" spesaCertificabileLorda: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idProgetto);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoEccendenzeValidazione);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoEccendenzeValidazione: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( impCertificabileNettoSoppr);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" impCertificabileNettoSoppr: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idIndirizzoSedeLegale);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndirizzoSedeLegale: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( dtUltimoPrerecupero);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" dtUltimoPrerecupero: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoNonRilevanteCertif);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoNonRilevanteCertif: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( identificativiIrregolarita);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" identificativiIrregolarita: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoErogazioni);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoErogazioni: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoRevocheIntermedio);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRevocheIntermedio: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoFideiussioni);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoFideiussioni: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( avanzamento);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" avanzamento: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoPagamentiValidati);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoPagamentiValidati: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idDimensioneImpresa);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneImpresa: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( fideiussioniInMenoPerError);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" fideiussioniInMenoPerError: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( costoAmmesso);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" costoAmmesso: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( flagCheckListInLoco);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCheckListInLoco: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoInteressiRecuperati);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoInteressiRecuperati: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( idDettPropostaCertif);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" idDettPropostaCertif: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoRecuperi);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRecuperi: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( flagComp);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" flagComp: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( impRevocheNettoSoppressioni);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" impRevocheNettoSoppressioni: " + temp + "\t\n");
//	    
//	    temp = DataFilter.removeNull( importoRecuperiPrerecuperi);
//	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRecuperiPrerecuperi: " + temp + "\t\n");
//	    
//	    return sb.toString();
//	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idDettPropostaCertif");
		
	    return pk;
	}
}
