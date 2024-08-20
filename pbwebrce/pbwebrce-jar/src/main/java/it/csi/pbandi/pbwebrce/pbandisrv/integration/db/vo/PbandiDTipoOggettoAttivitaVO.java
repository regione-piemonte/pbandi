/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoOggettoAttivitaVO extends GenericVO {

  	
  	private BigDecimal idTipoOggettoAttivita;
  	
  	private Date dtFineValidita;
  	
  	private String descTipoOggettoAttivita;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveTipoOggettoAttiv;
  	
	public PbandiDTipoOggettoAttivitaVO() {}
  	
	public PbandiDTipoOggettoAttivitaVO (BigDecimal idTipoOggettoAttivita) {
	
		this. idTipoOggettoAttivita =  idTipoOggettoAttivita;
	}
  	
	public PbandiDTipoOggettoAttivitaVO (BigDecimal idTipoOggettoAttivita, Date dtFineValidita, String descTipoOggettoAttivita, Date dtInizioValidita, String descBreveTipoOggettoAttiv) {
	
		this. idTipoOggettoAttivita =  idTipoOggettoAttivita;
		this. dtFineValidita =  dtFineValidita;
		this. descTipoOggettoAttivita =  descTipoOggettoAttivita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveTipoOggettoAttiv =  descBreveTipoOggettoAttiv;
	}
  	
  	
	public BigDecimal getIdTipoOggettoAttivita() {
		return idTipoOggettoAttivita;
	}
	
	public void setIdTipoOggettoAttivita(BigDecimal idTipoOggettoAttivita) {
		this.idTipoOggettoAttivita = idTipoOggettoAttivita;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescTipoOggettoAttivita() {
		return descTipoOggettoAttivita;
	}
	
	public void setDescTipoOggettoAttivita(String descTipoOggettoAttivita) {
		this.descTipoOggettoAttivita = descTipoOggettoAttivita;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescBreveTipoOggettoAttiv() {
		return descBreveTipoOggettoAttiv;
	}
	
	public void setDescBreveTipoOggettoAttiv(String descBreveTipoOggettoAttiv) {
		this.descBreveTipoOggettoAttiv = descBreveTipoOggettoAttiv;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descTipoOggettoAttivita != null && dtInizioValidita != null && descBreveTipoOggettoAttiv != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoOggettoAttivita != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoOggettoAttivita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoOggettoAttivita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoOggettoAttivita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoOggettoAttivita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoOggettoAttiv);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoOggettoAttiv: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoOggettoAttivita");
		
	    return pk;
	}
	
	
}
