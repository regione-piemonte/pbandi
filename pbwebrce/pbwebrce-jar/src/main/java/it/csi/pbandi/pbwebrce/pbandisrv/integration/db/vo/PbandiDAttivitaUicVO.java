/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDAttivitaUicVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descAttivitaUic;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idAttivitaUic;
  	
  	private String codAttivitaUic;
  	
	public PbandiDAttivitaUicVO() {}
  	
	public PbandiDAttivitaUicVO (BigDecimal idAttivitaUic) {
	
		this. idAttivitaUic =  idAttivitaUic;
	}
  	
	public PbandiDAttivitaUicVO (Date dtFineValidita, String descAttivitaUic, Date dtInizioValidita, BigDecimal idAttivitaUic, String codAttivitaUic) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descAttivitaUic =  descAttivitaUic;
		this. dtInizioValidita =  dtInizioValidita;
		this. idAttivitaUic =  idAttivitaUic;
		this. codAttivitaUic =  codAttivitaUic;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescAttivitaUic() {
		return descAttivitaUic;
	}
	
	public void setDescAttivitaUic(String descAttivitaUic) {
		this.descAttivitaUic = descAttivitaUic;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdAttivitaUic() {
		return idAttivitaUic;
	}
	
	public void setIdAttivitaUic(BigDecimal idAttivitaUic) {
		this.idAttivitaUic = idAttivitaUic;
	}
	
	public String getCodAttivitaUic() {
		return codAttivitaUic;
	}
	
	public void setCodAttivitaUic(String codAttivitaUic) {
		this.codAttivitaUic = codAttivitaUic;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descAttivitaUic != null && dtInizioValidita != null && codAttivitaUic != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttivitaUic != null ) {
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
	    
	    temp = DataFilter.removeNull( descAttivitaUic);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descAttivitaUic: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaUic);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaUic: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codAttivitaUic);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codAttivitaUic: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idAttivitaUic");
		
	    return pk;
	}
	
	
}
