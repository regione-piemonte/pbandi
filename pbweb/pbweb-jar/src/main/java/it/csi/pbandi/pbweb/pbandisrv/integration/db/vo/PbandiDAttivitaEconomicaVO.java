/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiDAttivitaEconomicaVO extends GenericVO {

  	
  	private BigDecimal idAttivitaEconomica;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String descAttivitaEconomica;
  	
  	private String codIgrueT6;
  	
	public PbandiDAttivitaEconomicaVO() {}
  	
	public PbandiDAttivitaEconomicaVO (BigDecimal idAttivitaEconomica) {
	
		this. idAttivitaEconomica =  idAttivitaEconomica;
	}
  	
	public PbandiDAttivitaEconomicaVO (BigDecimal idAttivitaEconomica, Date dtFineValidita, Date dtInizioValidita, String descAttivitaEconomica, String codIgrueT6) {
	
		this. idAttivitaEconomica =  idAttivitaEconomica;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descAttivitaEconomica =  descAttivitaEconomica;
		this. codIgrueT6 =  codIgrueT6;
	}
  	
  	
	public BigDecimal getIdAttivitaEconomica() {
		return idAttivitaEconomica;
	}
	
	public void setIdAttivitaEconomica(BigDecimal idAttivitaEconomica) {
		this.idAttivitaEconomica = idAttivitaEconomica;
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
	
	public String getDescAttivitaEconomica() {
		return descAttivitaEconomica;
	}
	
	public void setDescAttivitaEconomica(String descAttivitaEconomica) {
		this.descAttivitaEconomica = descAttivitaEconomica;
	}
	
	public String getCodIgrueT6() {
		return codIgrueT6;
	}
	
	public void setCodIgrueT6(String codIgrueT6) {
		this.codIgrueT6 = codIgrueT6;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descAttivitaEconomica != null && codIgrueT6 != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttivitaEconomica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaEconomica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaEconomica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descAttivitaEconomica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAttivitaEconomica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT6);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT6: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idAttivitaEconomica");
		
	    return pk;
	}
	
	
}
