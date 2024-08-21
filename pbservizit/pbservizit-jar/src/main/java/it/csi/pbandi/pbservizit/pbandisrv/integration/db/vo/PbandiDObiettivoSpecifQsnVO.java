/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDObiettivoSpecifQsnVO extends GenericVO {

  	
  	private String codIgrueT2;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idObiettivoGenQsn;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idObiettivoSpecifQsn;
  	
  	private String descObiettivoSpecificoQsn;
  	
	public PbandiDObiettivoSpecifQsnVO() {}
  	
	public PbandiDObiettivoSpecifQsnVO (BigDecimal idObiettivoSpecifQsn) {
	
		this. idObiettivoSpecifQsn =  idObiettivoSpecifQsn;
	}
  	
	public PbandiDObiettivoSpecifQsnVO (String codIgrueT2, Date dtFineValidita, BigDecimal idObiettivoGenQsn, Date dtInizioValidita, BigDecimal idObiettivoSpecifQsn, String descObiettivoSpecificoQsn) {
	
		this. codIgrueT2 =  codIgrueT2;
		this. dtFineValidita =  dtFineValidita;
		this. idObiettivoGenQsn =  idObiettivoGenQsn;
		this. dtInizioValidita =  dtInizioValidita;
		this. idObiettivoSpecifQsn =  idObiettivoSpecifQsn;
		this. descObiettivoSpecificoQsn =  descObiettivoSpecificoQsn;
	}
  	
  	
	public String getCodIgrueT2() {
		return codIgrueT2;
	}
	
	public void setCodIgrueT2(String codIgrueT2) {
		this.codIgrueT2 = codIgrueT2;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdObiettivoGenQsn() {
		return idObiettivoGenQsn;
	}
	
	public void setIdObiettivoGenQsn(BigDecimal idObiettivoGenQsn) {
		this.idObiettivoGenQsn = idObiettivoGenQsn;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdObiettivoSpecifQsn() {
		return idObiettivoSpecifQsn;
	}
	
	public void setIdObiettivoSpecifQsn(BigDecimal idObiettivoSpecifQsn) {
		this.idObiettivoSpecifQsn = idObiettivoSpecifQsn;
	}
	
	public String getDescObiettivoSpecificoQsn() {
		return descObiettivoSpecificoQsn;
	}
	
	public void setDescObiettivoSpecificoQsn(String descObiettivoSpecificoQsn) {
		this.descObiettivoSpecificoQsn = descObiettivoSpecificoQsn;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT2 != null && idObiettivoGenQsn != null && dtInizioValidita != null && descObiettivoSpecificoQsn != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idObiettivoSpecifQsn != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT2);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT2: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoGenQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoGenQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoSpecifQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoSpecifQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descObiettivoSpecificoQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descObiettivoSpecificoQsn: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idObiettivoSpecifQsn");
		
	    return pk;
	}
	
	
}
