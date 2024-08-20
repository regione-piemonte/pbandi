/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDQualificaVO extends GenericVO {

  	
  	private String descQualifica;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idQualifica;
  	
  	private String descBreveQualifica;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDQualificaVO() {}
  	
	public PbandiDQualificaVO (BigDecimal idQualifica) {
	
		this. idQualifica =  idQualifica;
	}
  	
	public PbandiDQualificaVO (String descQualifica, Date dtFineValidita, BigDecimal idQualifica, String descBreveQualifica, Date dtInizioValidita) {
	
		this. descQualifica =  descQualifica;
		this. dtFineValidita =  dtFineValidita;
		this. idQualifica =  idQualifica;
		this. descBreveQualifica =  descBreveQualifica;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescQualifica() {
		return descQualifica;
	}
	
	public void setDescQualifica(String descQualifica) {
		this.descQualifica = descQualifica;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdQualifica() {
		return idQualifica;
	}
	
	public void setIdQualifica(BigDecimal idQualifica) {
		this.idQualifica = idQualifica;
	}
	
	public String getDescBreveQualifica() {
		return descBreveQualifica;
	}
	
	public void setDescBreveQualifica(String descBreveQualifica) {
		this.descBreveQualifica = descBreveQualifica;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descQualifica != null && descBreveQualifica != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idQualifica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descQualifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descQualifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idQualifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idQualifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveQualifica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveQualifica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idQualifica");
		
	    return pk;
	}
	
	
}
