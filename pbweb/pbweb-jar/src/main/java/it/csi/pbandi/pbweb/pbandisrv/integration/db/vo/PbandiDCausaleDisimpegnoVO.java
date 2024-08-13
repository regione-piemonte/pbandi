/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDCausaleDisimpegnoVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveCausaleDisimpegno;
  	
  	private Date dtInizioValidita;
  	
  	private String descCausaleDisimpegno;
  	
  	private String tc38;
  	
  	private BigDecimal idCausaleDisimpegno;
  	
	public PbandiDCausaleDisimpegnoVO() {}
  	
	public PbandiDCausaleDisimpegnoVO (BigDecimal idCausaleDisimpegno) {
	
		this. idCausaleDisimpegno =  idCausaleDisimpegno;
	}
  	
	public PbandiDCausaleDisimpegnoVO (Date dtFineValidita, String descBreveCausaleDisimpegno, Date dtInizioValidita, String descCausaleDisimpegno, String tc38, BigDecimal idCausaleDisimpegno) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveCausaleDisimpegno =  descBreveCausaleDisimpegno;
		this. dtInizioValidita =  dtInizioValidita;
		this. descCausaleDisimpegno =  descCausaleDisimpegno;
		this. tc38 =  tc38;
		this. idCausaleDisimpegno =  idCausaleDisimpegno;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveCausaleDisimpegno() {
		return descBreveCausaleDisimpegno;
	}
	
	public void setDescBreveCausaleDisimpegno(String descBreveCausaleDisimpegno) {
		this.descBreveCausaleDisimpegno = descBreveCausaleDisimpegno;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescCausaleDisimpegno() {
		return descCausaleDisimpegno;
	}
	
	public void setDescCausaleDisimpegno(String descCausaleDisimpegno) {
		this.descCausaleDisimpegno = descCausaleDisimpegno;
	}
	
	public String getTc38() {
		return tc38;
	}
	
	public void setTc38(String tc38) {
		this.tc38 = tc38;
	}
	
	public BigDecimal getIdCausaleDisimpegno() {
		return idCausaleDisimpegno;
	}
	
	public void setIdCausaleDisimpegno(BigDecimal idCausaleDisimpegno) {
		this.idCausaleDisimpegno = idCausaleDisimpegno;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveCausaleDisimpegno != null && dtInizioValidita != null && descCausaleDisimpegno != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCausaleDisimpegno != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveCausaleDisimpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveCausaleDisimpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descCausaleDisimpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descCausaleDisimpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tc38);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tc38: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCausaleDisimpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleDisimpegno: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idCausaleDisimpegno");
		
	    return pk;
	}
	
	
}
