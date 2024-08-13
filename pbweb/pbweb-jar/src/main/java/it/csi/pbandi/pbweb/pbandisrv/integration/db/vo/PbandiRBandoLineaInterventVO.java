/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoLineaInterventVO extends GenericVO {

  	
  	private BigDecimal idBando;
  	
  	private BigDecimal idUnitaOrganizzativa;
  	
  	private BigDecimal idCategoriaCipe;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String nomeBandoLinea;
  	
  	private BigDecimal idObiettivoSpecifQsn;
  	
  	private BigDecimal idDefinizioneProcesso;
  	
  	private BigDecimal idAreaScientifica;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal mesiDurataDaDtConcessione;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal idTipologiaCipe;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idProcesso;
  	
  	private String flagSchedin;
  	
 // CDU-13 V08: inizio
  	private String flagSif;
  	private BigDecimal progrBandoLineaIntervSif;
  	private BigDecimal idTipoLocalizzazione;
  	private BigDecimal idLivelloIstituzione;
  	private BigDecimal idProgettoComplesso;
  	private BigDecimal idClassificazioneMet;
  	private String flagFondoDiFondi;
  	private BigDecimal idClassificazioneRa;
  	private String codAiutoRna;
 // CDU-13 V08: fine

	public String getCodAiutoRna() {
		return codAiutoRna;
	}

	public void setCodAiutoRna(String codAiutoRna) {
		this.codAiutoRna = codAiutoRna;
	}

	public BigDecimal getIdClassificazioneRa() {
		return idClassificazioneRa;
	}

	public void setIdClassificazioneRa(BigDecimal idClassificazioneRa) {
		this.idClassificazioneRa = idClassificazioneRa;
	}

	public String getFlagFondoDiFondi() {
		return flagFondoDiFondi;
	}
	
	public void setFlagFondoDiFondi(String flagFondoDiFondi) {
		this.flagFondoDiFondi = flagFondoDiFondi;
	}

	public String getFlagSif() {
		return flagSif;
	}
	
	public void setFlagSif(String flagSif) {
		this.flagSif = flagSif;
	}

	public BigDecimal getProgrBandoLineaIntervSif() {
		return progrBandoLineaIntervSif;
	}

	public void setProgrBandoLineaIntervSif(BigDecimal progrBandoLineaIntervSif) {
		this.progrBandoLineaIntervSif = progrBandoLineaIntervSif;
	}

	public BigDecimal getIdTipoLocalizzazione() {
		return idTipoLocalizzazione;
	}

	public void setIdTipoLocalizzazione(BigDecimal idTipoLocalizzazione) {
		this.idTipoLocalizzazione = idTipoLocalizzazione;
	}

	public BigDecimal getIdLivelloIstituzione() {
		return idLivelloIstituzione;
	}

	public void setIdLivelloIstituzione(BigDecimal idLivelloIstituzione) {
		this.idLivelloIstituzione = idLivelloIstituzione;
	}

	public BigDecimal getIdProgettoComplesso() {
		return idProgettoComplesso;
	}

	public void setIdProgettoComplesso(BigDecimal idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}

	public String getFlagSchedin() {
		return flagSchedin;
	}
	
	public void setFlagSchedin(String flagSchedin) {
		this.flagSchedin = flagSchedin;
	}

	public BigDecimal getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}

	public PbandiRBandoLineaInterventVO() {}
  	
	public PbandiRBandoLineaInterventVO (BigDecimal progrBandoLineaIntervento) {
	
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
	}
  	
	public PbandiRBandoLineaInterventVO (BigDecimal idBando, BigDecimal idUnitaOrganizzativa, BigDecimal idCategoriaCipe, BigDecimal idUtenteAgg, String nomeBandoLinea, BigDecimal idObiettivoSpecifQsn, BigDecimal idDefinizioneProcesso, BigDecimal idAreaScientifica, BigDecimal idLineaDiIntervento, BigDecimal mesiDurataDaDtConcessione, BigDecimal progrBandoLineaIntervento, BigDecimal idTipologiaCipe, BigDecimal idUtenteIns, BigDecimal idProcesso, String flagSchedin,
		  								 String flagSif, BigDecimal progrBandoLineaIntervSif, BigDecimal idTipoLocalizzazione, BigDecimal idLivelloIstituzione, BigDecimal idProgettoComplesso,
		  								 BigDecimal idClassificazioneMet, String flagFondoDiFondi, BigDecimal idClassificazioneRa,
		  								 String codAiutoRna) {	
		this.idBando =  idBando;
		this.idUnitaOrganizzativa =  idUnitaOrganizzativa;
		this.idCategoriaCipe =  idCategoriaCipe;
		this.idUtenteAgg =  idUtenteAgg;
		this.nomeBandoLinea =  nomeBandoLinea;
		this.idObiettivoSpecifQsn =  idObiettivoSpecifQsn;
		this.idDefinizioneProcesso =  idDefinizioneProcesso;
		this.idAreaScientifica =  idAreaScientifica;
		this.idLineaDiIntervento =  idLineaDiIntervento;
		this.mesiDurataDaDtConcessione =  mesiDurataDaDtConcessione;
		this.progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this.idTipologiaCipe =  idTipologiaCipe;
		this.idUtenteIns =  idUtenteIns;
		this.idProcesso =  idProcesso;
		this.flagSchedin =  flagSchedin;
		this.flagSif = flagSif;
		this.progrBandoLineaIntervSif = progrBandoLineaIntervSif;
		this.idTipoLocalizzazione = idTipoLocalizzazione;
		this.idLivelloIstituzione = idLivelloIstituzione;
		this.idProgettoComplesso = idProgettoComplesso;
		this.idClassificazioneMet = idClassificazioneMet;
		this.flagFondoDiFondi = flagFondoDiFondi;
		this.idClassificazioneRa = idClassificazioneRa;
		this.codAiutoRna = codAiutoRna;
	}
  	
	public BigDecimal getIdClassificazioneMet() {
		return idClassificazioneMet;
	}

	public void setIdClassificazioneMet(BigDecimal idClassificazioneMet) {
		this.idClassificazioneMet = idClassificazioneMet;
	}

	public BigDecimal getIdBando() {
		return idBando;
	}
	
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
	public BigDecimal getIdUnitaOrganizzativa() {
		return idUnitaOrganizzativa;
	}
	
	public void setIdUnitaOrganizzativa(BigDecimal idUnitaOrganizzativa) {
		this.idUnitaOrganizzativa = idUnitaOrganizzativa;
	}
	
	public BigDecimal getIdCategoriaCipe() {
		return idCategoriaCipe;
	}
	
	public void setIdCategoriaCipe(BigDecimal idCategoriaCipe) {
		this.idCategoriaCipe = idCategoriaCipe;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	
	public BigDecimal getIdObiettivoSpecifQsn() {
		return idObiettivoSpecifQsn;
	}
	
	public void setIdObiettivoSpecifQsn(BigDecimal idObiettivoSpecifQsn) {
		this.idObiettivoSpecifQsn = idObiettivoSpecifQsn;
	}
	
	public BigDecimal getIdDefinizioneProcesso() {
		return idDefinizioneProcesso;
	}
	
	public void setIdDefinizioneProcesso(BigDecimal idDefinizioneProcesso) {
		this.idDefinizioneProcesso = idDefinizioneProcesso;
	}
	
	public BigDecimal getIdAreaScientifica() {
		return idAreaScientifica;
	}
	
	public void setIdAreaScientifica(BigDecimal idAreaScientifica) {
		this.idAreaScientifica = idAreaScientifica;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public BigDecimal getMesiDurataDaDtConcessione() {
		return mesiDurataDaDtConcessione;
	}
	
	public void setMesiDurataDaDtConcessione(BigDecimal mesiDurataDaDtConcessione) {
		this.mesiDurataDaDtConcessione = mesiDurataDaDtConcessione;
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
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idBando != null && idDefinizioneProcesso != null && idLineaDiIntervento != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrBandoLineaIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUnitaOrganizzativa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUnitaOrganizzativa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCategoriaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCategoriaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeBandoLinea);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeBandoLinea: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoSpecifQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoSpecifQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDefinizioneProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDefinizioneProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAreaScientifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAreaScientifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( mesiDurataDaDtConcessione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" mesiDurataDaDtConcessione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipologiaCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipologiaCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProcesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProcesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagSchedin);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagSchedin: " + temp + "\t\n");	
	    
		temp = DataFilter.removeNull( flagSif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagSif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervSif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervSif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoLocalizzazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoLocalizzazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLivelloIstituzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLivelloIstituzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgettoComplesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgettoComplesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneMet);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneMet: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagFondoDiFondi);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagFondoDiFondi: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneRa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneRa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codAiutoRna);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAiutoRna: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
			pk.add("progrBandoLineaIntervento");
	    return pk;
	}
	
	
}
