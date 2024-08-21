/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbservizit.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoSoggRitenutaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoSoggRitenuta;
  	
  	private Date dtInizioValidita;
  	
  	private String descTipoSoggRitenuta;
  	
  	private BigDecimal idTipoSoggRitenuta;
  	
	public PbandiDTipoSoggRitenutaVO() {}
  	
	public PbandiDTipoSoggRitenutaVO (BigDecimal idTipoSoggRitenuta) {
	
		this. idTipoSoggRitenuta =  idTipoSoggRitenuta;
	}
  	
	public PbandiDTipoSoggRitenutaVO (Date dtFineValidita, String descBreveTipoSoggRitenuta, Date dtInizioValidita, String descTipoSoggRitenuta, BigDecimal idTipoSoggRitenuta) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoSoggRitenuta =  descBreveTipoSoggRitenuta;
		this. dtInizioValidita =  dtInizioValidita;
		this. descTipoSoggRitenuta =  descTipoSoggRitenuta;
		this. idTipoSoggRitenuta =  idTipoSoggRitenuta;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoSoggRitenuta() {
		return descBreveTipoSoggRitenuta;
	}
	
	public void setDescBreveTipoSoggRitenuta(String descBreveTipoSoggRitenuta) {
		this.descBreveTipoSoggRitenuta = descBreveTipoSoggRitenuta;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public String getDescTipoSoggRitenuta() {
		return descTipoSoggRitenuta;
	}
	
	public void setDescTipoSoggRitenuta(String descTipoSoggRitenuta) {
		this.descTipoSoggRitenuta = descTipoSoggRitenuta;
	}
	
	public BigDecimal getIdTipoSoggRitenuta() {
		return idTipoSoggRitenuta;
	}
	
	public void setIdTipoSoggRitenuta(BigDecimal idTipoSoggRitenuta) {
		this.idTipoSoggRitenuta = idTipoSoggRitenuta;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoSoggRitenuta != null && dtInizioValidita != null && descTipoSoggRitenuta != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoSoggRitenuta != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveTipoSoggRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoSoggRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoSoggRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoSoggRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggRitenuta: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoSoggRitenuta");
		
	    return pk;
	}
	
	
}
