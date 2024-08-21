/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiWAttiLiquidazioneDmVO extends GenericVO {

  	
  	private String flagOnline;
  	
  	private BigDecimal nliq;
  	
  	private BigDecimal annoeser;
  	
  	private String cupmand;
  	
  	private Date dataquiet;
  	
  	private String flagpign;
  	
  	private String cigmand;
  	
  	private BigDecimal importomandlordo;
  	
  	private BigDecimal importoquiet;
  	
  	private BigDecimal nromand;
  	
  	private BigDecimal idAttiLiquidazioneDt;
  	
  	private BigDecimal importomandnetto;
  	
  	private BigDecimal importoritenute;
  	
  	private String tiporecord;
  	
  	private BigDecimal idAttiLiquidazioneDm;
  	
	public PbandiWAttiLiquidazioneDmVO() {}
  	
	public PbandiWAttiLiquidazioneDmVO (BigDecimal idAttiLiquidazioneDm) {
	
		this. idAttiLiquidazioneDm =  idAttiLiquidazioneDm;
	}
  	
	public PbandiWAttiLiquidazioneDmVO (String flagOnline, BigDecimal nliq, BigDecimal annoeser, String cupmand, Date dataquiet, String flagpign, String cigmand, BigDecimal importomandlordo, BigDecimal importoquiet, BigDecimal nromand, BigDecimal idAttiLiquidazioneDt, BigDecimal importomandnetto, BigDecimal importoritenute, String tiporecord, BigDecimal idAttiLiquidazioneDm) {
	
		this. flagOnline =  flagOnline;
		this. nliq =  nliq;
		this. annoeser =  annoeser;
		this. cupmand =  cupmand;
		this. dataquiet =  dataquiet;
		this. flagpign =  flagpign;
		this. cigmand =  cigmand;
		this. importomandlordo =  importomandlordo;
		this. importoquiet =  importoquiet;
		this. nromand =  nromand;
		this. idAttiLiquidazioneDt =  idAttiLiquidazioneDt;
		this. importomandnetto =  importomandnetto;
		this. importoritenute =  importoritenute;
		this. tiporecord =  tiporecord;
		this. idAttiLiquidazioneDm =  idAttiLiquidazioneDm;
	}
  	
  	
	public String getFlagOnline() {
		return flagOnline;
	}
	
	public void setFlagOnline(String flagOnline) {
		this.flagOnline = flagOnline;
	}
	
	public BigDecimal getNliq() {
		return nliq;
	}
	
	public void setNliq(BigDecimal nliq) {
		this.nliq = nliq;
	}
	
	public BigDecimal getAnnoeser() {
		return annoeser;
	}
	
	public void setAnnoeser(BigDecimal annoeser) {
		this.annoeser = annoeser;
	}
	
	public String getCupmand() {
		return cupmand;
	}
	
	public void setCupmand(String cupmand) {
		this.cupmand = cupmand;
	}
	
	public Date getDataquiet() {
		return dataquiet;
	}
	
	public void setDataquiet(Date dataquiet) {
		this.dataquiet = dataquiet;
	}
	
	public String getFlagpign() {
		return flagpign;
	}
	
	public void setFlagpign(String flagpign) {
		this.flagpign = flagpign;
	}
	
	public String getCigmand() {
		return cigmand;
	}
	
	public void setCigmand(String cigmand) {
		this.cigmand = cigmand;
	}
	
	public BigDecimal getImportomandlordo() {
		return importomandlordo;
	}
	
	public void setImportomandlordo(BigDecimal importomandlordo) {
		this.importomandlordo = importomandlordo;
	}
	
	public BigDecimal getImportoquiet() {
		return importoquiet;
	}
	
	public void setImportoquiet(BigDecimal importoquiet) {
		this.importoquiet = importoquiet;
	}
	
	public BigDecimal getNromand() {
		return nromand;
	}
	
	public void setNromand(BigDecimal nromand) {
		this.nromand = nromand;
	}
	
	public BigDecimal getIdAttiLiquidazioneDt() {
		return idAttiLiquidazioneDt;
	}
	
	public void setIdAttiLiquidazioneDt(BigDecimal idAttiLiquidazioneDt) {
		this.idAttiLiquidazioneDt = idAttiLiquidazioneDt;
	}
	
	public BigDecimal getImportomandnetto() {
		return importomandnetto;
	}
	
	public void setImportomandnetto(BigDecimal importomandnetto) {
		this.importomandnetto = importomandnetto;
	}
	
	public BigDecimal getImportoritenute() {
		return importoritenute;
	}
	
	public void setImportoritenute(BigDecimal importoritenute) {
		this.importoritenute = importoritenute;
	}
	
	public String getTiporecord() {
		return tiporecord;
	}
	
	public void setTiporecord(String tiporecord) {
		this.tiporecord = tiporecord;
	}
	
	public BigDecimal getIdAttiLiquidazioneDm() {
		return idAttiLiquidazioneDm;
	}
	
	public void setIdAttiLiquidazioneDm(BigDecimal idAttiLiquidazioneDm) {
		this.idAttiLiquidazioneDm = idAttiLiquidazioneDm;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagOnline != null && idAttiLiquidazioneDt != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttiLiquidazioneDm != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( flagOnline);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagOnline: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nliq);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nliq: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoeser);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoeser: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cupmand);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cupmand: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataquiet);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataquiet: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagpign);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagpign: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cigmand);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cigmand: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importomandlordo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importomandlordo: " + importomandlordo + "\t\n");
	    
	    temp = DataFilter.removeNull( importoquiet);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoquiet: " + importoquiet + "\t\n");
	    
	    temp = DataFilter.removeNull( nromand);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nromand: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttiLiquidazioneDt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttiLiquidazioneDt: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importomandnetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importomandnetto: " + importomandnetto + "\t\n");
	    
	    temp = DataFilter.removeNull( importoritenute);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoritenute: " + importoritenute + "\t\n");
	    
	    temp = DataFilter.removeNull( tiporecord);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tiporecord: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttiLiquidazioneDm);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttiLiquidazioneDm: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAttiLiquidazioneDm");
		
	    return pk;
	}
	
	
}
