/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDTipoPercettoreVO extends GenericVO {

  	
  	private BigDecimal idTipoPercettore;
  	
  	private Date dtFineValidita;
  	
  	private Date dtInizioValidita;
  	
  	private String descBreveTipoPercettore;
  	
  	private String descTipoPercettore;
  	
  	private String tc40;
  	
	public PbandiDTipoPercettoreVO() {}
  	
	public PbandiDTipoPercettoreVO (BigDecimal idTipoPercettore) {
	
		this. idTipoPercettore =  idTipoPercettore;
	}
  	
	public PbandiDTipoPercettoreVO (BigDecimal idTipoPercettore, Date dtFineValidita, Date dtInizioValidita, String descBreveTipoPercettore, String descTipoPercettore, String tc40) {
	
		this. idTipoPercettore =  idTipoPercettore;
		this. dtFineValidita =  dtFineValidita;
		this. dtInizioValidita =  dtInizioValidita;
		this. descBreveTipoPercettore =  descBreveTipoPercettore;
		this. descTipoPercettore =  descTipoPercettore;
		this. tc40 = tc40;
	}
  	
  	
	public BigDecimal getIdTipoPercettore() {
		return idTipoPercettore;
	}
	
	public void setIdTipoPercettore(BigDecimal idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
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
	
	public String getDescBreveTipoPercettore() {
		return descBreveTipoPercettore;
	}
	
	public void setDescBreveTipoPercettore(String descBreveTipoPercettore) {
		this.descBreveTipoPercettore = descBreveTipoPercettore;
	}
	
	public String getDescTipoPercettore() {
		return descTipoPercettore;
	}
	
	public void setDescTipoPercettore(String descTipoPercettore) {
		this.descTipoPercettore = descTipoPercettore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && descBreveTipoPercettore != null && descTipoPercettore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idTipoPercettore != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoPercettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoPercettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descBreveTipoPercettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descBreveTipoPercettore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descTipoPercettore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descTipoPercettore: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public String getTc40() {
		return tc40;
	}

	public void setTc40(String tc40) {
		this.tc40 = tc40;
	}

	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idTipoPercettore");
		
	    return pk;
	}
	
	
}
