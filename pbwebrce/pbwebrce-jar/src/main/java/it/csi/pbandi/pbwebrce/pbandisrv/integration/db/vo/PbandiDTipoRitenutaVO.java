/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoRitenutaVO extends GenericVO {

  	
  	private Date dtFineValidita;
  	
  	private String descBreveTipoRitenuta;
  	
  	private String descTipoRitenuta;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idTipoRitenuta;
  	
	public PbandiDTipoRitenutaVO() {}
  	
	public PbandiDTipoRitenutaVO (BigDecimal idTipoRitenuta) {
	
		this. idTipoRitenuta =  idTipoRitenuta;
	}
  	
	public PbandiDTipoRitenutaVO (Date dtFineValidita, String descBreveTipoRitenuta, String descTipoRitenuta, Date dtInizioValidita, BigDecimal idTipoRitenuta) {
	
		this. dtFineValidita =  dtFineValidita;
		this. descBreveTipoRitenuta =  descBreveTipoRitenuta;
		this. descTipoRitenuta =  descTipoRitenuta;
		this. dtInizioValidita =  dtInizioValidita;
		this. idTipoRitenuta =  idTipoRitenuta;
	}
  	
  	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescBreveTipoRitenuta() {
		return descBreveTipoRitenuta;
	}
	
	public void setDescBreveTipoRitenuta(String descBreveTipoRitenuta) {
		this.descBreveTipoRitenuta = descBreveTipoRitenuta;
	}
	
	public String getDescTipoRitenuta() {
		return descTipoRitenuta;
	}
	
	public void setDescTipoRitenuta(String descTipoRitenuta) {
		this.descTipoRitenuta = descTipoRitenuta;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdTipoRitenuta() {
		return idTipoRitenuta;
	}
	
	public void setIdTipoRitenuta(BigDecimal idTipoRitenuta) {
		this.idTipoRitenuta = idTipoRitenuta;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descBreveTipoRitenuta != null && descTipoRitenuta != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoRitenuta != null ) {
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
	    
	    temp = DataFilter.removeNull( descBreveTipoRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoRitenuta: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoRitenuta);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoRitenuta: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idTipoRitenuta");
		
	    return pk;
	}
	
	
}
