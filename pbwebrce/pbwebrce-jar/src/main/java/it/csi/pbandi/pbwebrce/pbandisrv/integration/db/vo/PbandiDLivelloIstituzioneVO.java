/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDLivelloIstituzioneVO extends GenericVO {

  	
  	private BigDecimal idLivelloIstituzione;
  	
  	private String descBreveLivelloIstituzione;
  	
  	private String descLivelloIstituzione;
  	
  	private Date dtInizioValidita;
  	
  	private Date dtFineValidita;
  	
  	private String tc09;
  	
	public PbandiDLivelloIstituzioneVO() {}
  	
	public PbandiDLivelloIstituzioneVO (BigDecimal idLivelloIstituzione) {	
		this.idLivelloIstituzione =  idLivelloIstituzione;
	}
  	
	public PbandiDLivelloIstituzioneVO (BigDecimal idLivelloIstituzione, String descBreveLivelloIstituzione, String descLivelloIstituzione,  Date dtInizioValidita, Date dtFineValidita, String tc09) {
		this.idLivelloIstituzione =  idLivelloIstituzione;
		this.descBreveLivelloIstituzione =  descBreveLivelloIstituzione;
		this.descLivelloIstituzione =  descLivelloIstituzione;
		this.dtInizioValidita =  dtInizioValidita;
		this.dtFineValidita =  dtFineValidita;
		this.tc09 =  tc09;		
	}
  	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveLivelloIstituzione != null && descLivelloIstituzione != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idLivelloIstituzione != null ) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idLivelloIstituzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLivelloIstituzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveLivelloIstituzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveLivelloIstituzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descLivelloIstituzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descLivelloIstituzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tc09);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tc09: " + temp + "\t\n");
	   	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();		
			pk.add("idLivelloIstituzione");		
	    return pk;
	}

	public BigDecimal getIdLivelloIstituzione() {
		return idLivelloIstituzione;
	}

	public void setIdLivelloIstituzione(BigDecimal idLivelloIstituzione) {
		this.idLivelloIstituzione = idLivelloIstituzione;
	}

	public String getDescBreveLivelloIstituzione() {
		return descBreveLivelloIstituzione;
	}

	public void setDescBreveLivelloIstituzione(String descBreveLivelloIstituzione) {
		this.descBreveLivelloIstituzione = descBreveLivelloIstituzione;
	}

	public String getDescLivelloIstituzione() {
		return descLivelloIstituzione;
	}

	public void setDescLivelloIstituzione(String descLivelloIstituzione) {
		this.descLivelloIstituzione = descLivelloIstituzione;
	}

	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getTc09() {
		return tc09;
	}

	public void setTc09(String tc09) {
		this.tc09 = tc09;
	}

}
