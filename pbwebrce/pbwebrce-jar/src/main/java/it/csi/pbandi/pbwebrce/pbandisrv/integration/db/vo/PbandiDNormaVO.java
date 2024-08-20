/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDNormaVO extends GenericVO {

  	
  	private BigDecimal codIgrueT26;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal annoNorma;
  	
  	private String tipologiaNorma;
  	
  	private BigDecimal numeroNorma;
  	
  	private BigDecimal idNorma;
  	
  	private Date dtInizioValidita;
  	
  	private String descNorma;
  	
	public PbandiDNormaVO() {}
  	
	public PbandiDNormaVO (BigDecimal idNorma) {
	
		this. idNorma =  idNorma;
	}
  	
	public PbandiDNormaVO (BigDecimal codIgrueT26, Date dtFineValidita, BigDecimal annoNorma, String tipologiaNorma, BigDecimal numeroNorma, BigDecimal idNorma, Date dtInizioValidita, String descNorma) {
	
		this. codIgrueT26 =  codIgrueT26;
		this. dtFineValidita =  dtFineValidita;
		this. annoNorma =  annoNorma;
		this. tipologiaNorma =  tipologiaNorma;
		this. numeroNorma =  numeroNorma;
		this. idNorma =  idNorma;
		this. dtInizioValidita =  dtInizioValidita;
		this. descNorma =  descNorma;
	}
  	
  	
	public BigDecimal getCodIgrueT26() {
		return codIgrueT26;
	}
	
	public void setCodIgrueT26(BigDecimal codIgrueT26) {
		this.codIgrueT26 = codIgrueT26;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getAnnoNorma() {
		return annoNorma;
	}
	
	public void setAnnoNorma(BigDecimal annoNorma) {
		this.annoNorma = annoNorma;
	}
	
	public String getTipologiaNorma() {
		return tipologiaNorma;
	}
	
	public void setTipologiaNorma(String tipologiaNorma) {
		this.tipologiaNorma = tipologiaNorma;
	}
	
	public BigDecimal getNumeroNorma() {
		return numeroNorma;
	}
	
	public void setNumeroNorma(BigDecimal numeroNorma) {
		this.numeroNorma = numeroNorma;
	}
	
	public BigDecimal getIdNorma() {
		return idNorma;
	}
	
	public void setIdNorma(BigDecimal idNorma) {
		this.idNorma = idNorma;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescNorma() {
		return descNorma;
	}
	
	public void setDescNorma(String descNorma) {
		this.descNorma = descNorma;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT26 != null && annoNorma != null && tipologiaNorma != null && numeroNorma != null && dtInizioValidita != null && descNorma != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idNorma != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT26);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT26: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoNorma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoNorma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipologiaNorma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipologiaNorma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroNorma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroNorma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNorma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNorma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descNorma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descNorma: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idNorma");
		
	    return pk;
	}
	
	
}
