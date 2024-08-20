/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTitoloStudioVO extends GenericVO {

  	
  	private String descTitoloStudio;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idTitoloStudio;
  	
  	private String codIgrueT43;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDTitoloStudioVO() {}
  	
	public PbandiDTitoloStudioVO (BigDecimal idTitoloStudio) {
	
		this. idTitoloStudio =  idTitoloStudio;
	}
  	
	public PbandiDTitoloStudioVO (String descTitoloStudio, Date dtFineValidita, BigDecimal idTitoloStudio, String codIgrueT43, Date dtInizioValidita) {
	
		this. descTitoloStudio =  descTitoloStudio;
		this. dtFineValidita =  dtFineValidita;
		this. idTitoloStudio =  idTitoloStudio;
		this. codIgrueT43 =  codIgrueT43;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public String getDescTitoloStudio() {
		return descTitoloStudio;
	}
	
	public void setDescTitoloStudio(String descTitoloStudio) {
		this.descTitoloStudio = descTitoloStudio;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdTitoloStudio() {
		return idTitoloStudio;
	}
	
	public void setIdTitoloStudio(BigDecimal idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}
	
	public String getCodIgrueT43() {
		return codIgrueT43;
	}
	
	public void setCodIgrueT43(String codIgrueT43) {
		this.codIgrueT43 = codIgrueT43;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTitoloStudio != null && codIgrueT43 != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTitoloStudio != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( descTitoloStudio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTitoloStudio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTitoloStudio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTitoloStudio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT43);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT43: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTitoloStudio");
		
	    return pk;
	}
	
	
}
