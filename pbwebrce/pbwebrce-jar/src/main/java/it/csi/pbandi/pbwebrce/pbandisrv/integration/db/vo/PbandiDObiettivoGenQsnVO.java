/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDObiettivoGenQsnVO extends GenericVO {

  	
  	private BigDecimal idPrioritaQsn;
  	
  	private String codIgrueT2;
  	
  	private Date dtFineValidita;
  	
  	private String descObiettivoGeneraleQsn;
  	
  	private BigDecimal idObiettivoGenQsn;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDObiettivoGenQsnVO() {}
  	
	public PbandiDObiettivoGenQsnVO (BigDecimal idObiettivoGenQsn) {
	
		this. idObiettivoGenQsn =  idObiettivoGenQsn;
	}
  	
	public PbandiDObiettivoGenQsnVO (BigDecimal idPrioritaQsn, String codIgrueT2, Date dtFineValidita, String descObiettivoGeneraleQsn, BigDecimal idObiettivoGenQsn, Date dtInizioValidita) {
	
		this. idPrioritaQsn =  idPrioritaQsn;
		this. codIgrueT2 =  codIgrueT2;
		this. dtFineValidita =  dtFineValidita;
		this. descObiettivoGeneraleQsn =  descObiettivoGeneraleQsn;
		this. idObiettivoGenQsn =  idObiettivoGenQsn;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdPrioritaQsn() {
		return idPrioritaQsn;
	}
	
	public void setIdPrioritaQsn(BigDecimal idPrioritaQsn) {
		this.idPrioritaQsn = idPrioritaQsn;
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
	
	public String getDescObiettivoGeneraleQsn() {
		return descObiettivoGeneraleQsn;
	}
	
	public void setDescObiettivoGeneraleQsn(String descObiettivoGeneraleQsn) {
		this.descObiettivoGeneraleQsn = descObiettivoGeneraleQsn;
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
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idPrioritaQsn != null && codIgrueT2 != null && descObiettivoGeneraleQsn != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idObiettivoGenQsn != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idPrioritaQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPrioritaQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT2);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT2: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descObiettivoGeneraleQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descObiettivoGeneraleQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idObiettivoGenQsn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idObiettivoGenQsn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idObiettivoGenQsn");
		
	    return pk;
	}
	
	
}
