/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDAreaScientificaVO extends GenericVO {

  	
  	private String descAreaScientifica;
  	
  	private Date dtFineValidita;
  	
  	private String codAreaScientifica;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idAreaScientifica;
  	
	public PbandiDAreaScientificaVO() {}
  	
	public PbandiDAreaScientificaVO (BigDecimal idAreaScientifica) {
	
		this. idAreaScientifica =  idAreaScientifica;
	}
  	
	public PbandiDAreaScientificaVO (String descAreaScientifica, Date dtFineValidita, String codAreaScientifica, Date dtInizioValidita, BigDecimal idAreaScientifica) {
	
		this. descAreaScientifica =  descAreaScientifica;
		this. dtFineValidita =  dtFineValidita;
		this. codAreaScientifica =  codAreaScientifica;
		this. dtInizioValidita =  dtInizioValidita;
		this. idAreaScientifica =  idAreaScientifica;
	}
  	
  	
	public String getDescAreaScientifica() {
		return descAreaScientifica;
	}
	
	public void setDescAreaScientifica(String descAreaScientifica) {
		this.descAreaScientifica = descAreaScientifica;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getCodAreaScientifica() {
		return codAreaScientifica;
	}
	
	public void setCodAreaScientifica(String codAreaScientifica) {
		this.codAreaScientifica = codAreaScientifica;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdAreaScientifica() {
		return idAreaScientifica;
	}
	
	public void setIdAreaScientifica(BigDecimal idAreaScientifica) {
		this.idAreaScientifica = idAreaScientifica;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descAreaScientifica != null && codAreaScientifica != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAreaScientifica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descAreaScientifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAreaScientifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codAreaScientifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAreaScientifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAreaScientifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAreaScientifica: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idAreaScientifica");
		
	    return pk;
	}
	
	
}
