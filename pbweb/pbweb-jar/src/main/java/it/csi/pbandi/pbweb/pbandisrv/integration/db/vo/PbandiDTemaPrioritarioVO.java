/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDTemaPrioritarioVO extends GenericVO {

  	
  	private String codIgrueT4;
  	
  	private BigDecimal idTemaPrioritario;
  	
  	private String descTemaPrioritario;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTemaPrioritarioVO() {}
  	
	public PbandiDTemaPrioritarioVO (BigDecimal idTemaPrioritario) {
	
		this. idTemaPrioritario =  idTemaPrioritario;
	}
  	
	public PbandiDTemaPrioritarioVO (String codIgrueT4, BigDecimal idTemaPrioritario, String descTemaPrioritario, Date dtFineValidita, Date dtInizioValidita) {
	
		this. codIgrueT4 =  codIgrueT4;
		this. idTemaPrioritario =  idTemaPrioritario;
		this. descTemaPrioritario =  descTemaPrioritario;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getCodIgrueT4() {
		return codIgrueT4;
	}
	
	public void setCodIgrueT4(String codIgrueT4) {
		this.codIgrueT4 = codIgrueT4;
	}
	
	public BigDecimal getIdTemaPrioritario() {
		return idTemaPrioritario;
	}
	
	public void setIdTemaPrioritario(BigDecimal idTemaPrioritario) {
		this.idTemaPrioritario = idTemaPrioritario;
	}
	
	public String getDescTemaPrioritario() {
		return descTemaPrioritario;
	}
	
	public void setDescTemaPrioritario(String descTemaPrioritario) {
		this.descTemaPrioritario = descTemaPrioritario;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT4 != null && descTemaPrioritario != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTemaPrioritario != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT4);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT4: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTemaPrioritario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTemaPrioritario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTemaPrioritario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTemaPrioritario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTemaPrioritario");
		
	    return pk;
	}
	
	
}
