/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDIntesaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private BigDecimal codIgrueT11;
  	
  	private Date dtInizioValidita;
  	
  	private String descIntesa;
  	
  	private BigDecimal idIntesa;
  	
	public PbandiDIntesaVO() {}
  	
	public PbandiDIntesaVO (BigDecimal idIntesa) {
	
		this. idIntesa =  idIntesa;
	}
  	
	public PbandiDIntesaVO (Date dtFineValidita, BigDecimal codIgrueT11, Date dtInizioValidita, String descIntesa, BigDecimal idIntesa) {
	
		this. dtFineValidita =  dtFineValidita;
		this. codIgrueT11 =  codIgrueT11;
		this. dtInizioValidita =  dtInizioValidita;
		this. descIntesa =  descIntesa;
		this. idIntesa =  idIntesa;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getCodIgrueT11() {
		return codIgrueT11;
	}
	
	public void setCodIgrueT11(BigDecimal codIgrueT11) {
		this.codIgrueT11 = codIgrueT11;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescIntesa() {
		return descIntesa;
	}
	
	public void setDescIntesa(String descIntesa) {
		this.descIntesa = descIntesa;
	}
	
	public BigDecimal getIdIntesa() {
		return idIntesa;
	}
	
	public void setIdIntesa(BigDecimal idIntesa) {
		this.idIntesa = idIntesa;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codIgrueT11 != null && dtInizioValidita != null && descIntesa != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIntesa != null ) {
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
	    
	    temp = DataFilter.removeNull( codIgrueT11);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT11: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descIntesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descIntesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idIntesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIntesa: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idIntesa");
		
	    return pk;
	}
	
	
}
