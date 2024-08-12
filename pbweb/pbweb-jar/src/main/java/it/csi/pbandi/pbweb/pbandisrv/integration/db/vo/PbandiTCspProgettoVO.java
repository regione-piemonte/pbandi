
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTCspProgettoVO extends GenericVO {

  	
  	private BigDecimal idUtenteIns;
  	
  	private String flagBeneficiarioCup;
  	
  	private BigDecimal idAttivitaAteco;
  	
  	private BigDecimal idIndRisultatoProgram;
  	
  	private String flagGeneratoreEntrate;
  	
  	private BigDecimal idTipoOperazione;
  	
  	private BigDecimal idTemaPrioritario;
  	
  	private String titoloProgetto;
  	
  	private BigDecimal idGrandeProgetto;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idProgettoComplesso;
  	
  	private BigDecimal idClassificazioneRa;
  	
  	private Date dtElaborazione;
  	
  	private String flagInvioMonit;
  	
  	private BigDecimal idObiettivoTem;
  	
  	private BigDecimal idTipoAiuto;
  	
  	private BigDecimal idCspProgetto;
  	
  	private BigDecimal idIstanzaDomanda;
  	
  	private Date dtComitato;
  	
  	private BigDecimal idTipoStrumentoProgr;
  	
  	private BigDecimal idCategoriaCipe;
  	
  	private String flagCardine;
  	
  	private BigDecimal idProgetto;
  	
  	private String flagLeggeObbiettivo;
  	
  	private String cup;
  	
  	private String flagRichiestaCup;
  	
  	private BigDecimal idObiettivoSpecifQsn;
  	
  	private String flagAvviabile;
  	
  	private Date dtConcessione;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idStrumentoAttuativo;
  	
  	private String flagDettaglioCup;
  	
  	private String numeroDomanda;
  	
  	private BigDecimal idDimensioneTerritor;
  	
  	private BigDecimal idSettoreCpt;
  	
  	private String flagAltroFondo;
  	
  	private String statoProgramma;
  	
  	private BigDecimal idIndicatoreQsn;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idTipologiaCipe;
  	
  	private Date dtPresentazioneDomanda;
  	
	public PbandiTCspProgettoVO() {}
  	
	public PbandiTCspProgettoVO (BigDecimal idCspProgetto) {
	
		this. idCspProgetto =  idCspProgetto;
	}
  	
	public PbandiTCspProgettoVO (BigDecimal idUtenteIns, String flagBeneficiarioCup, BigDecimal idAttivitaAteco, BigDecimal idIndRisultatoProgram, String flagGeneratoreEntrate, BigDecimal idTipoOperazione, BigDecimal idTemaPrioritario, String titoloProgetto, BigDecimal idGrandeProgetto, BigDecimal idUtenteAgg, BigDecimal idProgettoComplesso, BigDecimal idClassificazioneRa, Date dtElaborazione, String flagInvioMonit, BigDecimal idObiettivoTem, BigDecimal idTipoAiuto, BigDecimal idCspProgetto, BigDecimal idIstanzaDomanda, Date dtComitato, BigDecimal idTipoStrumentoProgr, BigDecimal idCategoriaCipe, String flagCardine, BigDecimal idProgetto, String flagLeggeObbiettivo, String cup, String flagRichiestaCup, BigDecimal idObiettivoSpecifQsn, String flagAvviabile, Date dtConcessione, Date dtInizioValidita, BigDecimal idStrumentoAttuativo, String flagDettaglioCup, String numeroDomanda, BigDecimal idDimensioneTerritor, BigDecimal idSettoreCpt, String flagAltroFondo, String statoProgramma, BigDecimal idIndicatoreQsn, BigDecimal progrBandoLineaIntervento, BigDecimal idTipologiaCipe, Date dtPresentazioneDomanda) {
	
		this. idUtenteIns =  idUtenteIns;
		this. flagBeneficiarioCup =  flagBeneficiarioCup;
		this. idAttivitaAteco =  idAttivitaAteco;
		this. idIndRisultatoProgram =  idIndRisultatoProgram;
		this. flagGeneratoreEntrate =  flagGeneratoreEntrate;
		this. idTipoOperazione =  idTipoOperazione;
		this. idTemaPrioritario =  idTemaPrioritario;
		this. titoloProgetto =  titoloProgetto;
		this. idGrandeProgetto =  idGrandeProgetto;
		this. idUtenteAgg =  idUtenteAgg;
		this. idProgettoComplesso =  idProgettoComplesso;
		this. idClassificazioneRa =  idClassificazioneRa;
		this. dtElaborazione =  dtElaborazione;
		this. flagInvioMonit =  flagInvioMonit;
		this. idObiettivoTem =  idObiettivoTem;
		this. idTipoAiuto =  idTipoAiuto;
		this. idCspProgetto =  idCspProgetto;
		this. idIstanzaDomanda =  idIstanzaDomanda;
		this. dtComitato =  dtComitato;
		this. idTipoStrumentoProgr =  idTipoStrumentoProgr;
		this. idCategoriaCipe =  idCategoriaCipe;
		this. flagCardine =  flagCardine;
		this. idProgetto =  idProgetto;
		this. flagLeggeObbiettivo =  flagLeggeObbiettivo;
		this. cup =  cup;
		this. flagRichiestaCup =  flagRichiestaCup;
		this. idObiettivoSpecifQsn =  idObiettivoSpecifQsn;
		this. flagAvviabile =  flagAvviabile;
		this. dtConcessione =  dtConcessione;
		this. dtInizioValidita =  dtInizioValidita;
		this. idStrumentoAttuativo =  idStrumentoAttuativo;
		this. flagDettaglioCup =  flagDettaglioCup;
		this. numeroDomanda =  numeroDomanda;
		this. idDimensioneTerritor =  idDimensioneTerritor;
		this. idSettoreCpt =  idSettoreCpt;
		this. flagAltroFondo =  flagAltroFondo;
		this. statoProgramma =  statoProgramma;
		this. idIndicatoreQsn =  idIndicatoreQsn;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. idTipologiaCipe =  idTipologiaCipe;
		this. dtPresentazioneDomanda =  dtPresentazioneDomanda;
	}
  	
  	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getFlagBeneficiarioCup() {
		return flagBeneficiarioCup;
	}
	
	public void setFlagBeneficiarioCup(String flagBeneficiarioCup) {
		this.flagBeneficiarioCup = flagBeneficiarioCup;
	}
	
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	
	public BigDecimal getIdIndRisultatoProgram() {
		return idIndRisultatoProgram;
	}
	
	public void setIdIndRisultatoProgram(BigDecimal idIndRisultatoProgram) {
		this.idIndRisultatoProgram = idIndRisultatoProgram;
	}
	
	public String getFlagGeneratoreEntrate() {
		return flagGeneratoreEntrate;
	}
	
	public void setFlagGeneratoreEntrate(String flagGeneratoreEntrate) {
		this.flagGeneratoreEntrate = flagGeneratoreEntrate;
	}
	
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}
	
	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	
	public BigDecimal getIdTemaPrioritario() {
		return idTemaPrioritario;
	}
	
	public void setIdTemaPrioritario(BigDecimal idTemaPrioritario) {
		this.idTemaPrioritario = idTemaPrioritario;
	}
	
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	
	public BigDecimal getIdGrandeProgetto() {
		return idGrandeProgetto;
	}
	
	public void setIdGrandeProgetto(BigDecimal idGrandeProgetto) {
		this.idGrandeProgetto = idGrandeProgetto;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdProgettoComplesso() {
		return idProgettoComplesso;
	}
	
	public void setIdProgettoComplesso(BigDecimal idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}
	
	public BigDecimal getIdClassificazioneRa() {
		return idClassificazioneRa;
	}
	
	public void setIdClassificazioneRa(BigDecimal idClassificazioneRa) {
		this.idClassificazioneRa = idClassificazioneRa;
	}
	
	public Date getDtElaborazione() {
		return dtElaborazione;
	}
	
	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}
	
	public String getFlagInvioMonit() {
		return flagInvioMonit;
	}
	
	public void setFlagInvioMonit(String flagInvioMonit) {
		this.flagInvioMonit = flagInvioMonit;
	}
	
	public BigDecimal getIdObiettivoTem() {
		return idObiettivoTem;
	}
	
	public void setIdObiettivoTem(BigDecimal idObiettivoTem) {
		this.idObiettivoTem = idObiettivoTem;
	}
	
	public BigDecimal getIdTipoAiuto() {
		return idTipoAiuto;
	}
	
	public void setIdTipoAiuto(BigDecimal idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}
	
	public BigDecimal getIdCspProgetto() {
		return idCspProgetto;
	}
	
	public void setIdCspProgetto(BigDecimal idCspProgetto) {
		this.idCspProgetto = idCspProgetto;
	}
	
	public BigDecimal getIdIstanzaDomanda() {
		return idIstanzaDomanda;
	}
	
	public void setIdIstanzaDomanda(BigDecimal idIstanzaDomanda) {
		this.idIstanzaDomanda = idIstanzaDomanda;
	}
	
	public Date getDtComitato() {
		return dtComitato;
	}
	
	public void setDtComitato(Date dtComitato) {
		this.dtComitato = dtComitato;
	}
	
	public BigDecimal getIdTipoStrumentoProgr() {
		return idTipoStrumentoProgr;
	}
	
	public void setIdTipoStrumentoProgr(BigDecimal idTipoStrumentoProgr) {
		this.idTipoStrumentoProgr = idTipoStrumentoProgr;
	}
	
	public BigDecimal getIdCategoriaCipe() {
		return idCategoriaCipe;
	}
	
	public void setIdCategoriaCipe(BigDecimal idCategoriaCipe) {
		this.idCategoriaCipe = idCategoriaCipe;
	}
	
	public String getFlagCardine() {
		return flagCardine;
	}
	
	public void setFlagCardine(String flagCardine) {
		this.flagCardine = flagCardine;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public String getFlagLeggeObbiettivo() {
		return flagLeggeObbiettivo;
	}
	
	public void setFlagLeggeObbiettivo(String flagLeggeObbiettivo) {
		this.flagLeggeObbiettivo = flagLeggeObbiettivo;
	}
	
	public String getCup() {
		return cup;
	}
	
	public void setCup(String cup) {
		this.cup = cup;
	}
	
	public String getFlagRichiestaCup() {
		return flagRichiestaCup;
	}
	
	public void setFlagRichiestaCup(String flagRichiestaCup) {
		this.flagRichiestaCup = flagRichiestaCup;
	}
	
	public BigDecimal getIdObiettivoSpecifQsn() {
		return idObiettivoSpecifQsn;
	}
	
	public void setIdObiettivoSpecifQsn(BigDecimal idObiettivoSpecifQsn) {
		this.idObiettivoSpecifQsn = idObiettivoSpecifQsn;
	}
	
	public String getFlagAvviabile() {
		return flagAvviabile;
	}
	
	public void setFlagAvviabile(String flagAvviabile) {
		this.flagAvviabile = flagAvviabile;
	}
	
	public Date getDtConcessione() {
		return dtConcessione;
	}
	
	public void setDtConcessione(Date dtConcessione) {
		this.dtConcessione = dtConcessione;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}
	
	public void setIdStrumentoAttuativo(BigDecimal idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}
	
	public String getFlagDettaglioCup() {
		return flagDettaglioCup;
	}
	
	public void setFlagDettaglioCup(String flagDettaglioCup) {
		this.flagDettaglioCup = flagDettaglioCup;
	}
	
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	
	public BigDecimal getIdDimensioneTerritor() {
		return idDimensioneTerritor;
	}
	
	public void setIdDimensioneTerritor(BigDecimal idDimensioneTerritor) {
		this.idDimensioneTerritor = idDimensioneTerritor;
	}
	
	public BigDecimal getIdSettoreCpt() {
		return idSettoreCpt;
	}
	
	public void setIdSettoreCpt(BigDecimal idSettoreCpt) {
		this.idSettoreCpt = idSettoreCpt;
	}
	
	public String getFlagAltroFondo() {
		return flagAltroFondo;
	}
	
	public void setFlagAltroFondo(String flagAltroFondo) {
		this.flagAltroFondo = flagAltroFondo;
	}
	
	public String getStatoProgramma() {
		return statoProgramma;
	}
	
	public void setStatoProgramma(String statoProgramma) {
		this.statoProgramma = statoProgramma;
	}
	
	public BigDecimal getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}
	
	public void setIdIndicatoreQsn(BigDecimal idIndicatoreQsn) {
		this.idIndicatoreQsn = idIndicatoreQsn;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdTipologiaCipe() {
		return idTipologiaCipe;
	}
	
	public void setIdTipologiaCipe(BigDecimal idTipologiaCipe) {
		this.idTipologiaCipe = idTipologiaCipe;
	}
	
	public Date getDtPresentazioneDomanda() {
		return dtPresentazioneDomanda;
	}
	
	public void setDtPresentazioneDomanda(Date dtPresentazioneDomanda) {
		this.dtPresentazioneDomanda = dtPresentazioneDomanda;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null && flagBeneficiarioCup != null && flagGeneratoreEntrate != null && idTipoOperazione != null && titoloProgetto != null && flagInvioMonit != null && flagCardine != null && flagLeggeObbiettivo != null && flagRichiestaCup != null && flagAvviabile != null && dtInizioValidita != null && flagDettaglioCup != null && flagAltroFondo != null && statoProgramma != null && progrBandoLineaIntervento != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCspProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagBeneficiarioCup);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagBeneficiarioCup: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaAteco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndRisultatoProgram);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndRisultatoProgram: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagGeneratoreEntrate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagGeneratoreEntrate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoOperazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOperazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTemaPrioritario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTemaPrioritario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( titoloProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" titoloProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idGrandeProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idGrandeProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoComplesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoComplesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneRa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagInvioMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagInvioMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoTem);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoTem: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAiuto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAiuto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIstanzaDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIstanzaDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtComitato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtComitato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoStrumentoProgr);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoStrumentoProgr: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategoriaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategoriaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagCardine);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCardine: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagLeggeObbiettivo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagLeggeObbiettivo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cup);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cup: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagRichiestaCup);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagRichiestaCup: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoSpecifQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoSpecifQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAvviabile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAvviabile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtConcessione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtConcessione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idStrumentoAttuativo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idStrumentoAttuativo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagDettaglioCup);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagDettaglioCup: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroDomanda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneTerritor);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneTerritor: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSettoreCpt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSettoreCpt: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAltroFondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAltroFondo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( statoProgramma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" statoProgramma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndicatoreQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndicatoreQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtPresentazioneDomanda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtPresentazioneDomanda: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idCspProgetto");
		
	    return pk;
	}
	
	
}
