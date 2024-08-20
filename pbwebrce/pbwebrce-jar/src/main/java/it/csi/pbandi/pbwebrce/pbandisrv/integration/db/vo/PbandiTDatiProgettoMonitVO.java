/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTDatiProgettoMonitVO extends GenericVO {

  	
  	private String flagInvioMonit;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String statoFas;
  	
  	private String statoFs;
  	
  	private String flagAltroFondo;
  	
  	private String flagLegObiettivo;
  	
  	private String flagCardine;
  	
  	private String flagGeneratoreEntrate;
  	
  	private BigDecimal idSoggTitolareCipe;
  	
  	private BigDecimal idProgetto;
  	
  	private String flagProgettoDiCompletamento;
  	
  	private String flagExtraPor;
  	
  	private String codiceProgettoCipe;
  	
  	private BigDecimal idUtenteIns;
  	
  	private String flagRichiestaCup;
  	
  	private BigDecimal importoEntrate;
  	
	public PbandiTDatiProgettoMonitVO() {}
  	
	public PbandiTDatiProgettoMonitVO (BigDecimal idProgetto) {
	
		this. idProgetto =  idProgetto;
	}
  	
	public PbandiTDatiProgettoMonitVO (String flagInvioMonit, BigDecimal idUtenteAgg, String statoFas, String statoFs, String flagAltroFondo, String flagLegObiettivo, String flagCardine, String flagGeneratoreEntrate, BigDecimal idSoggTitolareCipe, BigDecimal idProgetto, String flagProgettoDiCompletamento, String flagExtraPor, String codiceProgettoCipe, BigDecimal idUtenteIns, BigDecimal importoEntrate) {
	
		this. flagInvioMonit =  flagInvioMonit;
		this. idUtenteAgg =  idUtenteAgg;
		this. statoFas =  statoFas;
		this. statoFs =  statoFs;
		this. flagAltroFondo =  flagAltroFondo;
		this. flagLegObiettivo =  flagLegObiettivo;
		this. flagCardine =  flagCardine;
		this. flagGeneratoreEntrate =  flagGeneratoreEntrate;
		this. idSoggTitolareCipe =  idSoggTitolareCipe;
		this. idProgetto =  idProgetto;
		this. flagProgettoDiCompletamento =  flagProgettoDiCompletamento;
		this. flagExtraPor =  flagExtraPor;
		this. codiceProgettoCipe =  codiceProgettoCipe;
		this. idUtenteIns =  idUtenteIns;
		this. importoEntrate = importoEntrate;
	}
  	
  	
	public String getFlagInvioMonit() {
		return flagInvioMonit;
	}
	
	public void setFlagInvioMonit(String flagInvioMonit) {
		this.flagInvioMonit = flagInvioMonit;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getStatoFas() {
		return statoFas;
	}
	
	public void setStatoFas(String statoFas) {
		this.statoFas = statoFas;
	}
	
	public String getStatoFs() {
		return statoFs;
	}
	
	public void setStatoFs(String statoFs) {
		this.statoFs = statoFs;
	}
	
	public String getFlagAltroFondo() {
		return flagAltroFondo;
	}
	
	public void setFlagAltroFondo(String flagAltroFondo) {
		this.flagAltroFondo = flagAltroFondo;
	}
	
	public String getFlagLegObiettivo() {
		return flagLegObiettivo;
	}
	
	public void setFlagLegObiettivo(String flagLegObiettivo) {
		this.flagLegObiettivo = flagLegObiettivo;
	}
	
	public String getFlagCardine() {
		return flagCardine;
	}
	
	public void setFlagCardine(String flagCardine) {
		this.flagCardine = flagCardine;
	}
	
	public String getFlagGeneratoreEntrate() {
		return flagGeneratoreEntrate;
	}
	
	public void setFlagGeneratoreEntrate(String flagGeneratoreEntrate) {
		this.flagGeneratoreEntrate = flagGeneratoreEntrate;
	}
	
	public BigDecimal getIdSoggTitolareCipe() {
		return idSoggTitolareCipe;
	}
	
	public void setIdSoggTitolareCipe(BigDecimal idSoggTitolareCipe) {
		this.idSoggTitolareCipe = idSoggTitolareCipe;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public String getFlagProgettoDiCompletamento() {
		return flagProgettoDiCompletamento;
	}
	
	public void setFlagProgettoDiCompletamento(String flagProgettoDiCompletamento) {
		this.flagProgettoDiCompletamento = flagProgettoDiCompletamento;
	}
	
	public String getFlagExtraPor() {
		return flagExtraPor;
	}
	
	public void setFlagExtraPor(String flagExtraPor) {
		this.flagExtraPor = flagExtraPor;
	}
	
	public String getCodiceProgettoCipe() {
		return codiceProgettoCipe;
	}
	
	public void setCodiceProgettoCipe(String codiceProgettoCipe) {
		this.codiceProgettoCipe = codiceProgettoCipe;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getFlagRichiestaCup() {
		return flagRichiestaCup;
	}

	public void setFlagRichiestaCup(String flagRichiestaCup) {
		this.flagRichiestaCup = flagRichiestaCup;
	}
	
	public BigDecimal getImportoEntrate() {
		return importoEntrate;
	}

	public void setImportoEntrate(BigDecimal importoEntrate) {
		this.importoEntrate = importoEntrate;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagInvioMonit != null && statoFas != null && statoFs != null && flagAltroFondo != null && flagLegObiettivo != null && flagCardine != null && flagGeneratoreEntrate != null && flagProgettoDiCompletamento != null && flagExtraPor != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( flagInvioMonit);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagInvioMonit: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( statoFas);
	    if (!DataFilter.isEmpty(temp)) sb.append(" statoFas: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( statoFs);
	    if (!DataFilter.isEmpty(temp)) sb.append(" statoFs: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAltroFondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAltroFondo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagLegObiettivo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagLegObiettivo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagCardine);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCardine: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagGeneratoreEntrate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagGeneratoreEntrate: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggTitolareCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggTitolareCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagProgettoDiCompletamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagProgettoDiCompletamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagExtraPor);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagExtraPor: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceProgettoCipe);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceProgettoCipe: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoEntrate);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoEntrate: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idProgetto");
		
	    return pk;
	}
	
	
}
